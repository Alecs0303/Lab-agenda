package com.example.demo.Models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tests")
@Data
public class TestsModels {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_test", unique = true, nullable = false)
    private int id;

    private String name;
    private String description;


}
