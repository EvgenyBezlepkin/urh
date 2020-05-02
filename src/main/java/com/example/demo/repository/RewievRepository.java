package com.example.demo.repository;

import com.example.demo.domain.Rewiev;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewievRepository extends JpaRepository<Rewiev,Long> {
    Rewiev findById(int id);
}