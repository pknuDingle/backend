package com.example.dingle.userHomepage.repository;

import com.example.dingle.userHomepage.entity.UserHomepage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHomepageRepository extends JpaRepository<UserHomepage, Long> {

    void deleteAllByUserId(Long userId);
}
