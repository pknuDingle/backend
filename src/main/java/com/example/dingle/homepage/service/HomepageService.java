package com.example.dingle.homepage.service;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.homepage.repository.HomepageRepository;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.user.entity.User;
import com.example.dingle.userHomepage.entity.UserHomepage;
import com.example.dingle.userHomepage.service.UserHomepageService;
import com.example.dingle.util.FindUserByJWT;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HomepageService {
    private final HomepageRepository homepageRepository;
    private final UserHomepageService userHomepageService;
    private final FindUserByJWT findUserByJWT;

    // Create
    public Homepage createHomepage(Homepage homepage) {
        return homepageRepository.save(homepage);
    }

    // Read
    public Homepage findHomepage(long homepageId) {
        return verifiedHomepage(homepageId);
    }

    public List<Notice> findHomepageToNotice(long homepageId) {
        return verifiedHomepage(homepageId).getNotices();
    }

    public List<Homepage> findAllHomepages() {
        return homepageRepository.findAll();
    }

    public List<Homepage> findAllHomepagesByUser() {
        User user = findUserByJWT.getLoginUser();
        List<Homepage> homepages = userHomepageService.findHomepageByUser(user);
        return homepages;
    }

    // Update
    public Homepage updateHomepage(Homepage homepage) {
        Homepage findHomepage = verifiedHomepage(homepage.getId());
        Optional.ofNullable(homepage.getName()).ifPresent(findHomepage::setName);
        return homepageRepository.save(findHomepage);
    }

    public void updateHomepage(List<Long> homepageIds) {
        List<Homepage> homepages = homepageIds
                .stream()
                .map(this::verifiedHomepage)
                .collect(Collectors.toList());

        User user = findUserByJWT.getLoginUser();
        userHomepageService.createUserHomepage(user, homepages);
    }

    // Delete
    public void deleteHomepage(long homepageId) {
        Homepage homepage = verifiedHomepage(homepageId);
        homepageRepository.delete(homepage);
    }

    // 증명
    public Homepage verifiedHomepage(long homepageId) {
        Optional<Homepage> homepage = homepageRepository.findById(homepageId);
        return homepage.orElseThrow(() -> new BusinessLogicException(ExceptionCode.HOMEPAGE_NOT_FOUND));
    }
}
