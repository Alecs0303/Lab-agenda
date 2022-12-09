package com.example.demo.Controllers;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Models.TestsModels;
import com.example.demo.Services.TestsServices;

@RestController
@RequestMapping("/test")
public class TestsControllers {
    @Autowired
    TestsServices testServices;

    @GetMapping()
    public ResponseEntity<ArrayList<TestsModels>> obtenerTest(){
        return new ResponseEntity<>(testServices.obtenerTest(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestsModels> findById(@PathVariable("id") Long id) {
        
        return  new ResponseEntity<>(testServices.obtenerTestByID(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<TestsModels> guardarTest(@RequestBody TestsModels test){
        return new ResponseEntity<>(testServices.guardarTest(test), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<TestsModels> actualizarTest(@RequestBody TestsModels test){
        return new ResponseEntity<>(testServices.actualizarTest(test), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarTest(@PathVariable("id") Long id) {
        testServices.eliminarTest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

