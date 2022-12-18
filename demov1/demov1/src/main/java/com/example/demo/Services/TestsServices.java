package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.TestsModels;
import com.example.demo.Repositories.TestsRespository;

@Service
public class TestsServices {
    @Autowired
    TestsRespository testRepository;

    public List<TestsModels> obtenerTest(){
        return (List<TestsModels>) testRepository.findAll();
    }

    public Optional<TestsModels> obtenerTestByID(long id){
        return testRepository.findById(id);
        //return test.get();
    }

    public TestsModels guardarTest(TestsModels test){
        return testRepository.save(test);
    }

    public TestsModels actualizarTest(TestsModels test){
        return testRepository.save(test);
    }

    public void eliminarTest(long id){
        testRepository.deleteById(id);
    }
}