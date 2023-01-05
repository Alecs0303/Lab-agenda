package com.example.demo.Models;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "tests")
//@Data
public class TestsModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_test", unique = true, nullable = false)
    private Long id;

    private String name;
    private String description;


}