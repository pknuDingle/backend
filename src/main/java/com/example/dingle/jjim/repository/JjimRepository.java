package com.example.dingle.jjim.repository;

import com.example.dingle.jjim.entity.Jjim;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JjimRepository extends JpaRepository<Jjim, Long> {
    Optional<Jjim> findByUserAndNotice(User user, Notice notice);
}
