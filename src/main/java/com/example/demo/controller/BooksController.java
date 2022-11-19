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

    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = new ArrayList<>();
        booksRepository.findAll().forEach(books::add);

        if (books.isEmpty())
            return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        return new ResponseEntity<>(booksRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}/books")
    public ResponseEntity<List<Book>> getAllBooksByUserId(@PathVariable Integer userId) {
        List<Book> books = new ArrayList<>(userRepository.findById(userId).get().getBook());
        return new ResponseEntity<>(books, HttpStatus.OK);

    }

    @PostMapping(path = "/{user_id}/books")
    public ResponseEntity<Book> addNewBookByUserId(@RequestBody Book book, @PathVariable Integer user_id) {
        Book temp = userRepository.findById(user_id).map(user -> {
            user.getBook().add(book);
            return booksRepository.save(book);
        }).get();
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Book> updateBookById(@RequestBody Book book, @PathVariable Integer id) {
        Book temp = booksRepository.findById(id).get();
        temp.setTitle(book.getTitle());
        temp.setAuthor(book.getAuthor());
        temp.setReturningTime(book.getReturningTime());
        return new ResponseEntity<>(booksRepository.save(temp), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{userId}/books")
    public ResponseEntity<List<Book>> deleteAllBooksByUserId(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).get();
        user.setBook(new HashSet<>());
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Integer id) {
        booksRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<List<Book>> deleteAllBooks() {
        booksRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
