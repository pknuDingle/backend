package com.example.dingle.personalNotice.service;

import com.example.dingle.notice.entity.Notice;
import com.example.dingle.personalNotice.repository.PersonalNoticeRepository;
import com.example.dingle.userKeyword.entity.UserKeyword;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalNoticeService {

    private final PersonalNoticeRepository personalNoticeRepository;

    public PersonalNoticeService(PersonalNoticeRepository personalNoticeRepository) {
        this.personalNoticeRepository = personalNoticeRepository;
    }

    public void createPersonalNotices(List<UserKeyword> userKeywords, Notice notice) {
        userKeywords.stream()
                .map(userKeyword -> userKeyword.toPersonalNotice(notice))
                .forEach(personalNoticeRepository::save);
    }

}
