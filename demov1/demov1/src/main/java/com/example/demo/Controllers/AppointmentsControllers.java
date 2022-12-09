package com.example.demo.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.Exceptions.ModelExceptionNotFound;
import com.example.demo.Repositories.AffiliatesRepository;
import com.example.demo.Services.AffiliatesServices;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Models.AppointmentsModels;
import com.example.demo.Services.AppointmentsServices;

@RestController
@RequestMapping("/appointments")
public class AppointmentsControllers {
    @Autowired
    AppointmentsServices appointmentsServices;

    @PostMapping()
    public ResponseEntity<AppointmentsModels> guardarAppointment(@RequestBody AppointmentsModels appointment){
        return new ResponseEntity<>(appointmentsServices.guardarAppointment(appointment), HttpStatus.CREATED);
    }

    @GetMapping(value = "/test")
     public ResponseEntity<String> hola(){
         return new ResponseEntity<>("Hola mundo", HttpStatus.OK);
     }

    @GetMapping()
    public ResponseEntity<ArrayList<AppointmentsModels>> obtenerAppointment(){
        return new ResponseEntity<>(appointmentsServices.obtenerAppointment(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentsModels> findById(@PathVariable("id") Long id) {
        return  new ResponseEntity<>(appointmentsServices.obtenerAppointmentByID(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarAppointment(@RequestBody AppointmentsModels appointment,@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        AppointmentsModels a = null;
        try {
            a = appointmentsServices.obtenerAppointmentByID(id);

            if(a != null){
                a.setDate(appointment.getDate());
                a.setHour(appointment.getHour());
                appointmentsServices.guardarAppointment(a);
                response.put("Mensaje", "Appointment editado");
            }
        }catch (Exception e){
            response.put("Mensaje", "Appointment a editar no ha sido encontrado");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAppointment(@PathVariable("id") Long id) throws Exception{
        Map<String,Object> response = new HashMap<>();
        try {
            appointmentsServices.eliminarAppointment(id);
            response.put("Mensaje", "Appointment eliminado");
        }catch (DataAccessException e){
            response.put("Mensaje", "Appointment a eliminar no ha sido encontrado");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
    }
}
