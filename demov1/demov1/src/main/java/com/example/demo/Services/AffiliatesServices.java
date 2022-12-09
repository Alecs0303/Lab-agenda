package com.example.demo.Services;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Models.AffiliatesModels;
import com.example.demo.Repositories.AffiliatesRepository;

@Service
public class AffiliatesServices {

    @Autowired
    AffiliatesRepository affiliatesRepository;

    public ArrayList<AffiliatesModels> obtenerAffiliate(){
        return (ArrayList<AffiliatesModels>) affiliatesRepository.findAll();
    }

    public AffiliatesModels guardarAffiliate(AffiliatesModels affiliate){
        return affiliatesRepository.save(affiliate);
    }
    
    public AffiliatesModels actualizarAffiliate(AffiliatesModels affiliate){
        return affiliatesRepository.save(affiliate);
    }

    public AffiliatesModels obtenerAffiliateByID(Long id){
        Optional<AffiliatesModels> affiliate = affiliatesRepository.findById(id);
        return affiliate.get();
    }

    public void eliminarAffiliate(Long id){
        affiliatesRepository.deleteById(id);
    }
    
}
