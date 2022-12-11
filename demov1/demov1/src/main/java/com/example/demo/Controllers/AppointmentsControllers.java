package com.example.demo.Controllers;

import java.util.*;

import com.example.demo.Exceptions.ModelExceptionNotFound;
import com.example.demo.Models.TestsModels;
import com.example.demo.Repositories.AffiliatesRepository;
import com.example.demo.Services.AffiliatesServices;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Models.AppointmentsModels;
import com.example.demo.Services.AppointmentsServices;
import org.w3c.dom.stylesheets.LinkStyle;

@RestController
@RequestMapping("/appointments")
public class AppointmentsControllers {
    @Autowired
    AppointmentsServices appointmentsServices;

    /*
    @GetMapping(value = "/test")
     public ResponseEntity<String> hi(){
        return new ResponseEntity<>("Hello, world", HttpStatus.OK);
    }
    */

    @GetMapping("/api/controller/get")
    public ResponseEntity<List<AppointmentsModels>> obtenerAppointment(){
        List<AppointmentsModels> data = appointmentsServices.obtenerAppointment();
        if(data == null || data.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
          return ResponseEntity.ok(data);
        }
    }

    @GetMapping("/api/controller/get/{id}")
    public ResponseEntity<?> obtenerAppointmentByID(@PathVariable Long id) {
        Map<String,Object> response = new HashMap<>();
        AppointmentsModels a = null;

        try {
            a = appointmentsServices.obtenerAppointmentByID(id);
            // System.out.println("Soy a en el try");
            // System.out.println(a);
            if (a != null) {
                response.put("Message", a);
            }
        }catch(Exception e){
            // System.out.println("Soy a en el catch");
            // System.out.println(a);
            response.put("Message", "Unable to find appointment".concat(id.toString()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/controller/post")
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

    @PutMapping("/api/controller/put/{id}")
    public ResponseEntity<?> actualizarAppointment(@RequestBody AppointmentsModels appointment, @PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        AppointmentsModels a = null;
        try {
            a = appointmentsServices.obtenerAppointmentByID(id);

            if(a != null){
                a.setDate(appointment.getDate());
                a.setHour(appointment.getHour());
                if(!Objects.equals(a.getId_affiliate(), appointment.getId_affiliate())){
                    response.put("Message", "Error");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
                appointmentsServices.guardarAppointment(a);
                response.put("Message", "Appointment edited");
            }
        }catch (Exception e){
            response.put("Message", "Unable to edit appointment");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/controller/delete/{id}")
    public ResponseEntity<?> eliminarAppointment(@PathVariable("id") Long id) throws Exception{
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
