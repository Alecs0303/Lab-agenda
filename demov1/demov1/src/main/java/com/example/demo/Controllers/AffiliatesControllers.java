package com.example.demo.Controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Models.AffiliatesModels;
import com.example.demo.Services.AffiliatesServices;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/controller/affiliates")
public class AffiliatesControllers {

    @Autowired
    AffiliatesServices affiliatesServices;

    @GetMapping()
    public ResponseEntity<?> obtenerAffiliate(){
        List<AffiliatesModels> data = affiliatesServices.obtenerAffiliate();
        if(data == null || data.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(data);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtenerAffiliateByID(@PathVariable("id") Long id) {
        Map<String,Object> response = new HashMap<>();
        Optional<AffiliatesModels> a = Optional.empty();
        try {
            a = affiliatesServices.obtenerAffiliateByID(id);
            if (a.isPresent()) {
                response.put("Message", a);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }catch(Exception e){

            response.put("Message", "Unable to find affiliate ".concat(id.toString()));
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> guardarAffiliate(@RequestBody AffiliatesModels affiliate){
        Map<String,Object> response = new HashMap<>();
        try {
            affiliatesServices.guardarAffiliate(affiliate);
            response.put("Message", "Affiliate saved");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (DataAccessException e){
            response.put("Message", "Unable to save affiliate");
            response.put("Error", e.getMostSpecificCause().getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> actualizarAffiliate(@RequestBody AffiliatesModels affiliate, @PathVariable("id") long id){
        Map<String,Object> response = new HashMap<>();
        Optional<AffiliatesModels> a = affiliatesServices.obtenerAffiliateByID(id);
        try {
            if(a.isPresent()){
                a.get().setName(affiliate.getName());
                a.get().setAge(affiliate.getAge());
                a.get().setMail(affiliate.getMail());
                affiliatesServices.guardarAffiliate(a.get());
                response.put("Message", "Affiliate edited");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
            }
        }catch (Exception e){
            response.put("Message", "Unable to edit affiliate");
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarAffiliate(@PathVariable Long id) throws Exception{
        Map<String,Object> response = new HashMap<>();
        try {
            affiliatesServices.eliminarAffiliate(id);
            response.put("Message", "Affiliate deleted");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("Message", "Unable to delete affiliate");
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
    }

}