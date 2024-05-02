package com.example.dingle.jjim.repository;

import com.example.dingle.jjim.entity.Jjim;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JjimRepository extends JpaRepository<Jjim, Long> {

    Optional<Jjim> findByUserAndNotice(User user, Notice notice);
}
