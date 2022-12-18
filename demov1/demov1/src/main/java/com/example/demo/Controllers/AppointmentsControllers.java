package com.example.demo.Controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Models.AppointmentsModels;
import com.example.demo.Services.AppointmentsServices;

@RestController
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
            }
        }catch(Exception e){
            response.put("Message", "Unable to find appointment".concat(id.toString()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> guardarAppointment(@RequestBody AppointmentsModels appointment){
        Map<String,Object> response = new HashMap<>();
        try {
            appointmentsServices.guardarAppointment(appointment);
            response.put("Message", "Appointment saved");
        }catch (DataAccessException e){
            response.put("Message", "Unable to save appointment");
            response.put("Error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
            }
        }catch (Exception e){
            response.put("Message", "Unable to edit appointment");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarAppointment(@PathVariable("id") long id) {
        Map<String,Object> response = new HashMap<>();
        try {
            appointmentsServices.eliminarAppointment(id);
            response.put("Message", "Appointment deleted");
        }catch (DataAccessException e){
            response.put("Message", "Unable to delete appointment");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}