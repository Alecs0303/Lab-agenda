package com.example.demo.Models;


import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "appointments")
//@Data
public class AppointmentsModels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String date;
    private String hour;

    @Column(name = "id_test")
    private Long id_test;
    @Column(name = "id_affiliate")
    private Long id_affiliate;



}