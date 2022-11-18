package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String email) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PutMapping(path = "/put/{id}")
    public @ResponseBody String updateUserById (@RequestParam String name, @RequestParam String email, @PathVariable Integer id) {
        User n = userRepository.findById(id).get();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Updated" + n.toString();
    }

    @DeleteMapping(path = "/del/{id}")
    public @ResponseBody String deleteUserById (@PathVariable Integer id) {
        userRepository.deleteById(id);
        return "Deleted successfully";
    }
}
