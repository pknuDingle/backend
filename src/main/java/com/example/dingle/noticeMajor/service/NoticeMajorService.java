package com.example.dingle.noticeMajor.service;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.noticeMajor.entity.NoticeMajor;
import com.example.dingle.noticeMajor.repository.NoticeMajorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class NoticeMajorService {
    private final NoticeMajorRepository noticeMajorRepository;

    // Create
    public NoticeMajor createNoticeMajor(NoticeMajor noticeMajor) {
        return noticeMajorRepository.save(noticeMajor);
    }

    // Read
    public NoticeMajor findNoticeMajor(long noticeMajorId) {
        return verifiedNoticeMajor(noticeMajorId);
    }


    // Delete
    public void deleteNoticeMajor(long noticeMajorId) {
        NoticeMajor noticeMajor = verifiedNoticeMajor(noticeMajorId);
        noticeMajorRepository.delete(noticeMajor);
    }

    // 증명
    public NoticeMajor verifiedNoticeMajor(long noticeMajorId) {
        Optional<NoticeMajor> noticeMajor = noticeMajorRepository.findById(noticeMajorId);
        return noticeMajor.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOTICEMAJOR_NOT_FOUND));
    }
}
