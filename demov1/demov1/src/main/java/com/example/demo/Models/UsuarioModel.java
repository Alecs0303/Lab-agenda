package com.example.demo.Models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
@Data
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String nombre;
    private String email;
    private Integer prioridad;


}
