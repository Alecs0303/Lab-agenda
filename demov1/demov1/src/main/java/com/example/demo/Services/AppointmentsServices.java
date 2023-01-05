package com.example.demo.Services;

import java.util.List;
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

    public List<AppointmentsModels> obtenerAppointment(){
        return (List<AppointmentsModels>) appointmentsRepository.findAll();
    }

    public Optional<AppointmentsModels> obtenerAppointmentByID(long id){
        return appointmentsRepository.findById(id);
    }

    /*public Optional<AppointmentsModels> obtenerAppointmentByDate(String date){
        return appointmentsRepository.find(date);
        // parsear fechas if fecha!=se manda un wrn | peticiones get por query string
    }*/

    public Optional<AppointmentsModels> obtenerAppointmentByAffiliatesID(long id_affiliate){
        return appointmentsRepository.findById(id_affiliate);
    }

    public AppointmentsModels guardarAppointment(AppointmentsModels appointment){
        return appointmentsRepository.save(appointment);
    }

    public AppointmentsModels actualizarAppointment(AppointmentsModels appointment){
        return appointmentsRepository.save(appointment);
    }


    public void eliminarAppointment(long id){
        appointmentsRepository.deleteById(id);
    }
}