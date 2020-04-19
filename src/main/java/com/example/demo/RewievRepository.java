package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RewievRepository extends JpaRepository<Rewiev,Long> {
    Rewiev findById(int id);
}