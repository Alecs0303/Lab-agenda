package com.example.demo.Services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.TestsModels;
import com.example.demo.Repositories.TestsRespository;

@Service
public class TestsServices {
    @Autowired
    TestsRespository testRepository;

    public ArrayList<TestsModels> obtenerTest(){
        return (ArrayList<TestsModels>) testRepository.findAll();
    }

    public TestsModels guardarTest(TestsModels test){
        return testRepository.save(test);
    }
    
    public TestsModels actualizarTest(TestsModels test){
        return testRepository.save(test);
    }

    public TestsModels obtenerTestByID(Long id){
        Optional<TestsModels> test = testRepository.findById(id);
        return test.get();
    }

    public void eliminarTest(Long id){
        testRepository.deleteById(id);
    }
}
