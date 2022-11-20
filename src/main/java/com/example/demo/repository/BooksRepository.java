package com.example.demo.repository;

import com.example.demo.entity.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BooksRepository extends CrudRepository<Book, Integer> {
    List<Book> findByUserId(Integer userId);

    List<Book> findByGenreId(Integer genreId);

    List<Book> findByGenreIdAndUserId(Integer userId, Integer genreId);
}
