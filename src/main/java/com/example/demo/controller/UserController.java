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


    /**
     * returns all users from DB
     * */
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * returns one specific user from DB
     * @param id - id from primary key of users table
     * */
    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return new ResponseEntity<>(userRepository.findById(id).get(), HttpStatus.OK);
    }

    /**
     * add a new user to DB
     * */
    @PostMapping()
    public ResponseEntity<User> addNewUser (@RequestBody User user) {
        User n = userRepository.save(user);
        return new ResponseEntity<>(n, HttpStatus.CREATED);
    }

    /**
     * update one user in DB
     * @param id - id from primary key of users table
     * */
    @PutMapping(path = "/{id}")
    public ResponseEntity<User> updateUserById (@RequestBody User user, @PathVariable Integer id) {
        User temp = userRepository.findById(id).get();
        temp.setName(user.getName());
        temp.setContactNumber(user.getContactNumber());
        return new ResponseEntity<>(userRepository.save(temp), HttpStatus.OK);
    }

    /**
     * delete one user from DB
     * @param id - id from primary key of users table
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById (@PathVariable Integer id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * delete all users from DB
     * */
    @DeleteMapping()
    public ResponseEntity<List<User>> deleteAllUsers () {
        userRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
