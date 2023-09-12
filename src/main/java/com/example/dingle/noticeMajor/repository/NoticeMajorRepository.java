package com.example.dingle.noticeMajor.repository;

import com.example.dingle.noticeMajor.entity.NoticeMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeMajorRepository extends JpaRepository<NoticeMajor, Long> {
}
