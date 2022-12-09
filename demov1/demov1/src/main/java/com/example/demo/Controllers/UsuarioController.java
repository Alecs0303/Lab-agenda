package com.example.demo.Controllers;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Models.UsuarioModel;
import com.example.demo.Services.UsuarioServices;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioServices usuarioServices;

    @GetMapping()
    public ResponseEntity<ArrayList<UsuarioModel>> obtenerUsuarios(){
        return new ResponseEntity<>(usuarioServices.obtenerUsuarios(), HttpStatus.OK);
    }
 
    @PostMapping()
    public ResponseEntity<UsuarioModel> guardarUsuario(@RequestBody UsuarioModel usuario){
        return new ResponseEntity<>(usuarioServices.guardarUsuario(usuario), HttpStatus.CREATED);
    }
}
