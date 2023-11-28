package com.example.dingle.personalNotice.service;

import com.example.dingle.notice.entity.Notice;
import com.example.dingle.personalNotice.entity.PersonalNotice;
import com.example.dingle.personalNotice.repository.PersonalNoticeRepository;
import com.example.dingle.userHomepage.entity.UserHomepage;
import com.example.dingle.userKeyword.entity.UserKeyword;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonalNoticeService {

    private final PersonalNoticeRepository personalNoticeRepository;

    public PersonalNoticeService(PersonalNoticeRepository personalNoticeRepository) {
        this.personalNoticeRepository = personalNoticeRepository;
    }

    public List<PersonalNotice> createPersonalNoticesWithUserKeywords(List<UserKeyword> userKeywords, Notice notice) {
        return userKeywords.stream()
                .map(userKeyword -> userKeyword.toPersonalNotice(notice))
                .peek(personalNoticeRepository::save) // Optional: Save each personal notice if needed
                .collect(Collectors.toList());
    }

    public List<PersonalNotice> createPersonalNoticesWithUserHomepages(List<UserHomepage> userHomepages, Notice notice) {
        return userHomepages.stream()
                .map(userHomepage -> userHomepage.toPersonalNotice(notice))
                .peek(personalNoticeRepository::save) // Optional: Save each personal notice if needed
                .collect(Collectors.toList());
    }

}
