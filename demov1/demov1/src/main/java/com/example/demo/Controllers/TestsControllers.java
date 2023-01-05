package com.example.demo.Controllers;

import java.io.Serializable;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Models.TestsModels;
import com.example.demo.Services.TestsServices;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/controller/test")
public class TestsControllers {
    @Autowired
    TestsServices testsServices;

    @GetMapping()
    public ResponseEntity<?> obtenerTest(){
        List<TestsModels> data = testsServices.obtenerTest();
        if(data == null || data.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(data);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtenerTestByID(@PathVariable("id") Long id) {
        Map<String,Object> response = new HashMap<>();
        Optional<TestsModels> a = Optional.empty();
        try {
            a = testsServices.obtenerTestByID(id);
            if (a.isPresent()) {
                response.put("Message", a);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }catch(Exception e){
            response.put("Message", "Unable to find test ".concat(id.toString()));
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /*@GetMapping("{id}")
    public Optional<ResponseEntity<TestsModels>> obtenerTestByID(@PathVariable("id") long id) {
        Map<String,Object> response = new HashMap<>();
        return Optional.of(testsServices.obtenerTestByID(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()));
    }*/

    @PostMapping()
    public ResponseEntity<?> guardarTest(@RequestBody TestsModels test){
        Map<String,Object> response = new HashMap<>();
        try {
            testsServices.guardarTest(test);
            response.put("Message", "Test saved");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        }catch (DataAccessException e){
            response.put("Message", "Unable to save test");
            response.put("Error", e.getMostSpecificCause().getMessage());
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> actualizarTest(@RequestBody TestsModels test, @PathVariable("id") long id){
        Map<String,Object> response = new HashMap<>();
        Optional<TestsModels> a = testsServices.obtenerTestByID(id);
        try {
            if(a.isPresent()){
                a.get().setName(test.getName());
                a.get().setDescription(test.getDescription());

                testsServices.guardarTest(a.get());
                response.put("Message", "Test edited");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
            }
        }catch (Exception e){
            response.put("Message", "Unable to edit test");
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarTest(@PathVariable("id") Long id) {
        Map<String,Object> response = new HashMap<>();
        try {
            testsServices.eliminarTest(id);
            response.put("Message", "Test deleted");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("Message", "Unable to delete test");
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
    }

}