package com.example.demo.repository;

import com.example.demo.domain.IntermediateResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntermediateResultRepository extends JpaRepository<IntermediateResult,Long> {
    IntermediateResult findById(int id);
}
