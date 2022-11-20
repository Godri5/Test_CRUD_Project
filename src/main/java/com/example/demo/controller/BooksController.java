package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.repository.BooksRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BooksController {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * returns all books from DB
     * */
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
    * */
    @GetMapping(path = "/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        return new ResponseEntity<>(booksRepository.findById(id).get(), HttpStatus.OK);
    }

    /**
     * returns all books of one specific user
     * @param userId - id from primary key of users table
     * */
    @GetMapping(path = "/{userId}/books")
    public ResponseEntity<List<Book>> getAllBooksByUserId(@PathVariable Integer userId) {
        List<Book> books = new ArrayList<>(userRepository.findById(userId).get().getBook());
        return new ResponseEntity<>(books, HttpStatus.OK);

    }

    /**
     * add new book to DB and connect it with the specific user
     * @param userId - id from primary key of users table
     * */
    @PostMapping(path = "/{userId}/books")
    public ResponseEntity<Book> addNewBookByUserId(@RequestBody Book book, @PathVariable Integer userId) {
        Book temp = userRepository.findById(userId).map(user -> {
            user.getBook().add(book);
            return booksRepository.save(book);
        }).get();
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    /**
     * rewrite specific book in DB
     * @param id - id from primary key of books table
     * */
    @PutMapping(path = "/{id}")
    public ResponseEntity<Book> updateBookById(@RequestBody Book book, @PathVariable Integer id) {
        Book temp = booksRepository.findById(id).get();
        temp.setTitle(book.getTitle());
        temp.setAuthor(book.getAuthor());
        temp.setReturningTime(book.getReturningTime());
        return new ResponseEntity<>(booksRepository.save(temp), HttpStatus.OK);
    }

    /**
     * delete all books of one specific user from DB
     * @param userId - id from primary key of users table
     * */
    @DeleteMapping(path = "/{userId}/books")
    public ResponseEntity<List<Book>> deleteAllBooksByUserId(@PathVariable Integer userId) {
        booksRepository.deleteAll(getAllBooksByUserId(userId).getBody());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * delete one specific book from DB
     * @param id - id from primary key of books table
     * */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Integer id) {
        booksRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * delete all books from DB
     * */
    @DeleteMapping()
    public ResponseEntity<List<Book>> deleteAllBooks() {
        booksRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
