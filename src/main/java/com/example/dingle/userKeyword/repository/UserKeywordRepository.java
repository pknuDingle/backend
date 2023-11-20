package com.example.dingle.userKeyword.repository;

import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.user.entity.User;
import com.example.dingle.userKeyword.entity.UserKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserKeywordRepository extends JpaRepository<UserKeyword, Long> {

    void deleteAllByUserId(Long userId);

    List<UserKeyword> findAllByKeyword(Keyword keyword);

    List<UserKeyword> findAllByUser(User user);

    Optional<UserKeyword> findAllByUserAndKeyword(User user, Keyword keyword);
}
