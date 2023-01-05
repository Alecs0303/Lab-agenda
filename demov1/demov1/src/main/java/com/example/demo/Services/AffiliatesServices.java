package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.AffiliatesModels;
import com.example.demo.Repositories.AffiliatesRepository;

@Service
public class AffiliatesServices {
    @Autowired
    AffiliatesRepository affiliatesRepository;

    public List<AffiliatesModels> obtenerAffiliate(){
        return (List<AffiliatesModels>) affiliatesRepository.findAll();
        // SELECT * FROM springboot.appointments;
    }

    public Optional<AffiliatesModels> obtenerAffiliateByID(long id){
        return affiliatesRepository.findById(id);
        // SELECT * FROM springboot.appointments WHERE id = 14;
    }

    public AffiliatesModels guardarAffiliate(AffiliatesModels affiliate){
        return affiliatesRepository.save(affiliate);
    }

    public AffiliatesModels actualizarAffiliate(AffiliatesModels affiliate){
        return affiliatesRepository.save(affiliate);
    }

    public void eliminarAffiliate(long id){
        affiliatesRepository.deleteById(id);
    }

}