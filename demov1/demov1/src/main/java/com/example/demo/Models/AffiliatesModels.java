package com.example.demo.Models;


import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "affiliates")
//@Data
public class AffiliatesModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_affiliate", unique = true, nullable = false)
    private Long id;

    private String name;
    private int age;
    private String mail;

}