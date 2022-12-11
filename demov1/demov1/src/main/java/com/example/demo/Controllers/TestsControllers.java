package com.example.demo.Controllers;

import java.util.*;

import com.example.demo.Models.AppointmentsModels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Models.TestsModels;
import com.example.demo.Services.TestsServices;

@RestController
@RequestMapping("/test")
public class TestsControllers {
    @Autowired
    TestsServices testsServices;

    @GetMapping("/api/controller/get")
    public ResponseEntity<List<TestsModels>> obtenerTest(){
        List<TestsModels> data = testsServices.obtenerTest();
        if(data == null || data.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(data);
        }
    }

    @GetMapping("/api/controller/get/{id}")
    public ResponseEntity<?> obtenerTestByID(@PathVariable Long id) {
        Map<String,Object> response = new HashMap<>();
        TestsModels a = null;

        try {
            a = testsServices.obtenerTestByID(id);
            if (a != null) {
                response.put("Message", a);
            }
        }catch(Exception e){
            response.put("Message", "Unable to find test ".concat(id.toString()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/api/controller/post")
    public ResponseEntity<?> guardarTest(@RequestBody TestsModels test){
        Map<String,Object> response = new HashMap<>();
        try {
            testsServices.guardarTest(test);
            response.put("Message", "Appointment saved");
        }catch (DataAccessException e){
            response.put("Message", "Unable to save test");
            response.put("Error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/api/controller/put/{id}")
    public ResponseEntity<?> actualizarTest(@RequestBody TestsModels test, @PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        TestsModels a = null;
        try {
            a = testsServices.obtenerTestByID(id);

            if(a != null){
                a.setName(test.getName());
                a.setDescription(test.getDescription());

                testsServices.guardarTest(a);
                response.put("Message", "Test edited");
            }
        }catch (Exception e){
            response.put("Message", "Unable to edit test");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/controller/delete/{id}")
    public ResponseEntity<?> eliminarTest(@PathVariable("id") Long id) throws Exception{
        Map<String,Object> response = new HashMap<>();
        try {
            testsServices.eliminarTest(id);
            response.put("Message", "Test deleted");
        }catch (DataAccessException e){
            response.put("Message", "Unable to delete test");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
    }

}

