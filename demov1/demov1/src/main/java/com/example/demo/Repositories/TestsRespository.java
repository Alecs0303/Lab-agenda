package com.example.demo.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Models.TestsModels;

@Repository
public interface TestsRespository extends CrudRepository<TestsModels, Long>{
    
}

