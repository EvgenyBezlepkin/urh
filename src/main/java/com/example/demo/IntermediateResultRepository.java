package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IntermediateResultRepository extends JpaRepository<IntermediateResult,Long> {
    IntermediateResult findById(int id);
}
