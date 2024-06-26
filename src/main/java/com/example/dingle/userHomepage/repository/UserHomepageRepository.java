package com.example.dingle.userHomepage.repository;

import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.user.entity.User;
import com.example.dingle.userHomepage.entity.UserHomepage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHomepageRepository extends JpaRepository<UserHomepage, Long> {

    void deleteAllByUserId(Long userId);

    List<UserHomepage> findAllByUser(User user);

    Optional<UserHomepage> findByUserAndHomepage(User user, Homepage homepage);

    List<UserHomepage> findAllByHomepage(Homepage homepage);
}
