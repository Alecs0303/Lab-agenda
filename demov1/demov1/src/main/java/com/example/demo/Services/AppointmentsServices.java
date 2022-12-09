package com.example.demo.Services;

import java.util.ArrayList;
import java.util.Optional;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Models.AppointmentsModels;
import com.example.demo.Repositories.AppointmentsRepository;

@Service
@Log4j2
public class AppointmentsServices {
    @Autowired
    AppointmentsRepository appointmentsRepository;

    public ArrayList<AppointmentsModels> obtenerAppointment(){
        return (ArrayList<AppointmentsModels>) appointmentsRepository.findAll();
    }

    public AppointmentsModels guardarAppointment(AppointmentsModels appointment){
        return appointmentsRepository.save(appointment);
    }
    
    public AppointmentsModels actualizarAppointment(AppointmentsModels appointment){
        return appointmentsRepository.save(appointment);
    }

    public AppointmentsModels obtenerAppointmentByID(Long id){
        Optional<AppointmentsModels> appointment = appointmentsRepository.findById(id);
        return appointment.get();
    }

    public void eliminarAppointment(Long id){
        appointmentsRepository.deleteById(id);
    }
}
