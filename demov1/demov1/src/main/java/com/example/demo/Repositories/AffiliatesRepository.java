package com.example.demo.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Models.AffiliatesModels;

@Repository
public interface AffiliatesRepository extends CrudRepository<AffiliatesModels, Long> {
    
}
