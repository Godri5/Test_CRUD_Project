package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String contactNumber;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<Book> book = new HashSet<>();

    public User(String name, String contactNumber) {
        this.name = name;
        this.contactNumber = contactNumber;
    }
}
