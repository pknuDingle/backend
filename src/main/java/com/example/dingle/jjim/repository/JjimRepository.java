package com.example.dingle.jjim.repository;

import com.example.dingle.jjim.entity.Jjim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JjimRepository extends JpaRepository<Jjim, Long> {
}
