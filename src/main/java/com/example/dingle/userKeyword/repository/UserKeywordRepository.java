package com.example.dingle.userKeyword.repository;

import com.example.dingle.userKeyword.entity.UserKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserKeywordRepository extends JpaRepository<UserKeyword, Long> {

    void deleteAllByUserId(Long userId);

    List<UserKeyword> findAllByKeywordId(Long keywordId);
}
