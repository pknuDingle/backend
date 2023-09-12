package com.example.dingle.userCategory.repository;

import com.example.dingle.userCategory.entity.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
}
