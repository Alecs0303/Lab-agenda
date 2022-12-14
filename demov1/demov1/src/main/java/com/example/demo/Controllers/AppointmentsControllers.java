package com.example.demo.Controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Models.AppointmentsModels;
import com.example.demo.Services.AppointmentsServices;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/controller/appointments")
public class AppointmentsControllers {
    @Autowired
    AppointmentsServices appointmentsServices;

    /*
    @GetMapping(value = "/test")
     public ResponseEntity<String> hi(){
        return new ResponseEntity<>("Hello, world", HttpStatus.OK);
    }
    */

    @GetMapping()
    public ResponseEntity<?> obtenerAppointment(){
        List<AppointmentsModels> data = appointmentsServices.obtenerAppointment();
        if(data == null || data.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(data);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtenerAppointmentByID(@PathVariable("id") Long id) {
        Map<String,Object> response = new HashMap<>();
        Optional<AppointmentsModels> a = Optional.empty();
        try {
            a = appointmentsServices.obtenerAppointmentByID(id);
            if (a.isPresent()) {
                response.put("Message", a);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }catch(Exception e){
            response.put("Message", "Unable to find appointment".concat(id.toString()));
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /*@GetMapping("{id_affiliate}")
    public ResponseEntity<?> obtenerAppointmentByAffiliatesID(@PathVariable("id_affiliate") Long id_affiliate) {
        Map<String,Object> response = new HashMap<>();
        Optional<AppointmentsModels> b = Optional.empty();
        try {
            b = appointmentsServices.obtenerAppointmentByAffiliatesID(id_affiliate);
            if (b.isPresent()) {
                response.put("Message", b);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }catch(Exception e){
            response.put("Message", "Unable to find appointment".concat(id_affiliate.toString()));
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }*/

    @PostMapping()
    public ResponseEntity<?> guardarAppointment(@RequestBody AppointmentsModels appointment){
        Map<String,Object> response = new HashMap<>();
        try {
            appointmentsServices.guardarAppointment(appointment);
            response.put("Message", "Appointment saved");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (DataAccessException e){
            response.put("Message", "Unable to save appointment");
            response.put("Error", e.getMostSpecificCause().getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> actualizarAppointment(@RequestBody AppointmentsModels appointment, @PathVariable("id") Long id){
        Map<String,Object> response = new HashMap<>();
        Optional<AppointmentsModels> a = appointmentsServices.obtenerAppointmentByID(id);
        try {
            if(a.isPresent()){
                a.get().setDate(appointment.getDate());
                a.get().setHour(appointment.getHour());
                if(!Objects.equals(a.get().getId_affiliate(), appointment.getId_affiliate())){
                    response.put("Message", "Error");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
                appointmentsServices.guardarAppointment(a.get());
                response.put("Message", "Appointment edited");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }catch (Exception e){
            response.put("Message", "Unable to edit appointment");
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarAppointment(@PathVariable("id") long id) {
        Map<String,Object> response = new HashMap<>();
        try {
            appointmentsServices.eliminarAppointment(id);
            response.put("Message", "Appointment deleted");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("Message", "Unable to delete appointment");
        }
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}