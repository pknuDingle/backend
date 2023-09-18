package com.example.dingle.userMajor.repository;

import com.example.dingle.userMajor.entity.UserMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMajorRepository extends JpaRepository<UserMajor, Long> {
}
