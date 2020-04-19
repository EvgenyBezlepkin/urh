package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceTemporalRepository extends JpaRepository<ServiceTemporal, Integer>{
    ServiceTemporal findById(int id);

}
