package com.example.demo.repository;

import com.example.demo.domain.ServiceTemporal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTemporalRepository extends JpaRepository<ServiceTemporal, Integer>{
    ServiceTemporal findById(int id);

}
