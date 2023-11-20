package com.example.dingle.userHomepage.repository;

import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.user.entity.User;
import com.example.dingle.userHomepage.entity.UserHomepage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserHomepageRepository extends JpaRepository<UserHomepage, Long> {

    void deleteAllByUserId(Long userId);

    List<UserHomepage> findAllByUser(User user);
    Optional<UserHomepage> findByUserAndHomepage(User user, Homepage homepage);
}
