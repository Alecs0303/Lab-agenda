package com.example.demo.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Models.AppointmentsModels;

@Repository
public interface AppointmentsRepository extends CrudRepository<AppointmentsModels, Long>{

}
