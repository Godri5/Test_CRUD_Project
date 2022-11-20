package com.example.demo.controller;

import com.example.demo.entity.Genre;
import com.example.demo.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/genres")
public class GenreController {

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping()
    public ResponseEntity<Set<Genre>> getAllGenres() {
        Set<Genre> genres = new HashSet<>();
        genreRepository.findAll().forEach(genres::add);

        if (genres.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Integer id) {
        return new ResponseEntity<>(genreRepository.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Genre> addNewGenre(@RequestBody Genre genre) {
        Genre addedGenre = genreRepository.save(genre);
        return new ResponseEntity<>(addedGenre, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Genre> updateGenreById(@RequestBody Genre genre, @PathVariable Integer id) {
        Genre updatedGenre = genreRepository.findById(id).get();
        updatedGenre.setGenre(genre.getGenre());
        return new ResponseEntity<>(genreRepository.save(genre), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<List<Genre>> deleteAllGenres() {
        genreRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteGenreById(@PathVariable Integer id) {
        genreRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
