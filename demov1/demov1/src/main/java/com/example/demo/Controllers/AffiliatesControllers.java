package com.example.demo.Controllers;

import java.util.*;

import com.example.demo.Models.AppointmentsModels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Models.AffiliatesModels;
import com.example.demo.Services.AffiliatesServices;

@RestController
@RequestMapping("/affiliates")
public class AffiliatesControllers {
    
    @Autowired
    AffiliatesServices affiliatesServices;

    @GetMapping("/api/controller/get")
    public ResponseEntity<List<AffiliatesModels>> obtenerAffiliate(){
        List<AffiliatesModels> data = affiliatesServices.obtenerAffiliate();
        if(data == null || data.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(data);
        }
    }

    @GetMapping("/api/controller/get/{id}")
    public ResponseEntity<?> obtenerAffiliateByID(@PathVariable Long id) {
        Map<String,Object> response = new HashMap<>();
        AffiliatesModels a = null;

        try {
            a = affiliatesServices.obtenerAffiliateByID(id);
            if (a != null) {
                response.put("Message", a);
            }
        }catch(Exception e){
            response.put("Message", "Unable to find affiliate ".concat(id.toString()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/api/controller/post")
    public ResponseEntity<?> guardarAffiliate(@RequestBody AffiliatesModels affiliate){
        Map<String,Object> response = new HashMap<>();
        try {
            affiliatesServices.guardarAffiliate(affiliate);
            response.put("Message", "Affiliate saved");
        }catch (DataAccessException e){
            response.put("Message", "Unable to save affiliate");
            response.put("Error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/api/controller/put/{id}")
    public ResponseEntity<?> actualizarAffiliate(@RequestBody AffiliatesModels affiliate, @PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        AffiliatesModels a = null;
        try {
            a = affiliatesServices.obtenerAffiliateByID(id);

            if(a != null){
                a.setName(affiliate.getName());
                a.setAge(affiliate.getAge());
                a.setMail(affiliate.getMail());

                affiliatesServices.guardarAffiliate(a);
                response.put("Message", "Affiliate edited");
            }
        }catch (Exception e){
            response.put("Message", "Unable to edit affiliate");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/controller/delete/{id}")
    public ResponseEntity<?> eliminarAffiliate(@PathVariable("id") Long id) throws Exception{
        Map<String,Object> response = new HashMap<>();
        try {
            affiliatesServices.eliminarAffiliate(id);
            response.put("Message", "Affiliate deleted");
        }catch (DataAccessException e){
            response.put("Message", "Unable to delete affiliate");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
    }

}
