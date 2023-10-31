package com.example.dingle.user.repository;

import com.example.dingle.user.dto.UserRequestDto;
import com.example.dingle.user.dto.UserResponseDto;
import com.example.dingle.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKakakoId(Long kakaoId);
    Optional<User> findByToken(String token);
    Optional<User> findByEmail(String email);
}
