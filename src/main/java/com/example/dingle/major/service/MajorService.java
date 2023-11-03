package com.example.dingle.major.service;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.major.entity.Major;
import com.example.dingle.major.repository.MajorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MajorService {
    private final MajorRepository majorRepository;

    // Create
    public Major createMajor(Major major) {
        return majorRepository.save(major);
    }

    // Read
    public Major findMajor(long majorId) {
        return verifiedMajor(majorId);
    }

    public List<Major> findAllMajors() {
        return majorRepository.findAll();
    }

    // Update
    public Major updateMajor(Major major) {
        Major findMajor = verifiedMajor(major.getId());
        Optional.ofNullable(major.getName()).ifPresent(findMajor::setName);
        return majorRepository.save(findMajor);
    }

    // Delete
    public void deleteMajor(long majorId) {
        Major major = verifiedMajor(majorId);
        majorRepository.delete(major);
    }

    // 증명
    public Major verifiedMajor(long majorId) {
        Optional<Major> major = majorRepository.findById(majorId);
        return major.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MAJOR_NOT_FOUND));
    }
}
