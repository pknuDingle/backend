package com.example.dingle.personalNotice.service;

import com.example.dingle.notice.entity.Notice;
import com.example.dingle.personalNotice.repository.PersonalNoticeRepository;
import com.example.dingle.userCategory.entity.UserCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalNoticeService {

    private final PersonalNoticeRepository personalNoticeRepository;

    public PersonalNoticeService(PersonalNoticeRepository personalNoticeRepository) {
        this.personalNoticeRepository = personalNoticeRepository;
    }

    public void createPersonalNotices(List<UserCategory> userCategories, Notice notice) {
        userCategories.stream()
                .map(userCategory -> userCategory.toPersonalNotice(notice))
                .forEach(personalNoticeRepository::save);
    }

}
