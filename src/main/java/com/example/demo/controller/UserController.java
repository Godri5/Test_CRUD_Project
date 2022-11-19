package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;


    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return new ResponseEntity<>(userRepository.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> addNewUser (@RequestBody User user) {
        User n = userRepository.save(new User(user.getName(), user.getContactNumber()));
        return new ResponseEntity<>(n, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<User> updateUserById (@RequestBody User user, @PathVariable Integer id) {
        User temp = userRepository.findById(id).get();
        temp.setName(user.getName());
        temp.setContactNumber(user.getContactNumber());
        return new ResponseEntity<>(userRepository.save(temp), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById (@PathVariable Integer id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<List<User>> deleteUserByName () {
        userRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
