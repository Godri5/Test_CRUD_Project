package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.repository.BooksRepository;
import com.example.demo.repository.GenreRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BooksController {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenreRepository genreRepository;

    /**
     * returns all books from DB
     */
    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = new ArrayList<>();
        booksRepository.findAll().forEach(books::add);

        if (books.isEmpty())
            return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    /**
     * returns one book by it ID
     * @param id - id from primary key of books table
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        return new ResponseEntity<>(booksRepository.findById(id).get(), HttpStatus.OK);
    }

    /**
     * returns all books of one specific user
     * @param userId - id from primary key of users table
     */
    @GetMapping(path = "/{userId}/books")
    public ResponseEntity<List<Book>> getAllBooksByUserId(@PathVariable Integer userId) {
        List<Book> books = booksRepository.findByUserId(userId);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    /**
     * returns all books of one specific user
     * @param genreId - id from primary key of genres table
     */
    @GetMapping(path = "{genreId}/books")
    public ResponseEntity<List<Book>> getAllBooksByGenreId(@PathVariable Integer genreId) {
        List<Book> books = booksRepository.findByGenreId(genreId);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    /**
     * returns all books of one specific user
     * @param userId - id from primary key of users table
     * @param genreId - id from primary key of genres table
     */
    @GetMapping(path = "{userId}/{genreId}/books")
    public ResponseEntity<List<Book>> getAllBooksByGenreAndUserId(@PathVariable(value = "userId") Integer userId,
                                                                  @PathVariable(value = "genreId") Integer genreId) {
        List<Book> books = booksRepository.findByGenreIdAndUserId(genreId, userId);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    /**
     * add new book to DB and connect it with the specific user
     *
     * @param userId - id from primary key of users table
     * @param genreId - id from primary key of genres table
     */
    @PostMapping(path = "/{userId}/{genreId}/books")
    public ResponseEntity<Book> addNewBookByUserId(@RequestBody Book book,
                                                   @PathVariable(value = "userId") Integer userId,
                                                   @PathVariable(value = "genreId") Integer genreId) {
        Book temp = userRepository.findById(userId).map(user -> {
            book.setUser(user);
            book.setGenre(genreRepository.findById(genreId).get());
            return booksRepository.save(book);
        }).get();
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    /**
     * rewrite specific book in DB
     * @param id - id from primary key of books table
     * @param genreId - id from primary key of genres table
     */
    @PutMapping(path = "/{genreId}/{id}")
    public ResponseEntity<Book> updateBookById(@RequestBody Book book, @PathVariable(value = "id") Integer id,
                                               @PathVariable(value = "genreId") Integer genreId) {
        Book temp = booksRepository.findById(id).get();
        temp.setTitle(book.getTitle());
        temp.setAuthor(book.getAuthor());
        temp.setReturningDate(book.getReturningDate());
        temp.setGenre(genreRepository.findById(genreId).get());
        return new ResponseEntity<>(booksRepository.save(temp), HttpStatus.OK);
    }

    /**
     * delete all books of one specific user from DB
     * @param userId - id from primary key of users table
     */
    @DeleteMapping(path = "/{userId}/books")
    public ResponseEntity<List<Book>> deleteAllBooksByUserId(@PathVariable Integer userId) {
        booksRepository.deleteAll(getAllBooksByUserId(userId).getBody());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * delete one specific book from DB
     * @param id - id from primary key of books table
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Integer id) {
        booksRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * delete all books from DB
     */
    @DeleteMapping()
    public ResponseEntity<List<Book>> deleteAllBooks() {
        booksRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
