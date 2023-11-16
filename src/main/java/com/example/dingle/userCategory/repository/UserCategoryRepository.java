package com.example.dingle.userCategory.repository;

import com.example.dingle.userCategory.entity.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {

    void deleteAllByUserId(Long userId);

    List<UserCategory> findAllByCategoryId(Long categoryId);
}
