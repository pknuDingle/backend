package com.example.dingle.personalNotice.repository;

import com.example.dingle.personalNotice.entity.PersonalNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalNoticeRepository extends JpaRepository<PersonalNotice, Long> {
}
