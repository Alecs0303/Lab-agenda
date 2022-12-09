package com.example.demo.Controllers;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Models.AffiliatesModels;
import com.example.demo.Services.AffiliatesServices;

@RestController
@RequestMapping("/affiliates")
public class AffiliatesControllers {
    
    @Autowired
    AffiliatesServices affiliatesServices;

    @GetMapping()
    public ResponseEntity<ArrayList<AffiliatesModels>> obtenerAffiliate(){
        return new ResponseEntity<>(affiliatesServices.obtenerAffiliate(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AffiliatesModels> findById(@PathVariable("id") Long id) {
        return  new ResponseEntity<>(affiliatesServices.obtenerAffiliateByID(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<AffiliatesModels> guardarAffiliate(@RequestBody AffiliatesModels affiliate){
        return new ResponseEntity<>(affiliatesServices.guardarAffiliate(affiliate), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<AffiliatesModels> actualizarAffiliate(@RequestBody AffiliatesModels affiliate){
        return new ResponseEntity<>(affiliatesServices.actualizarAffiliate(affiliate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarAffiliate(@PathVariable("id") Long id) {
        affiliatesServices.eliminarAffiliate(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
