package com.example.dingle.homepage.service;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.homepage.repository.HomepageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HomepageService {
    private final HomepageRepository homepageRepository;

    // Create
    public Homepage createHomepage(Homepage homepage) {
        return homepageRepository.save(homepage);
    }

    // Read
    public Homepage findHomepage(long homepageId) {
        return verifiedHomepage(homepageId);
    }

    public List<Homepage> findAllHomepages() {
        return homepageRepository.findAll();
    }

    // Update
    public Homepage updateHomepage(Homepage homepage) {
        Homepage findHomepage = verifiedHomepage(homepage.getId());
        Optional.ofNullable(homepage.getName()).ifPresent(findHomepage::setName);
        return homepageRepository.save(findHomepage);
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
