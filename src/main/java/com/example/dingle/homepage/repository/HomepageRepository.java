package com.example.dingle.homepage.repository;

import com.example.dingle.homepage.entity.Homepage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomepageRepository extends JpaRepository<Homepage, Long> {
}
