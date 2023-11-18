package com.example.dingle.jjim.service;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.jjim.entity.Jjim;
import com.example.dingle.jjim.repository.JjimRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class JjimService {
    private final JjimRepository jjimRepository;

    // Create
    public Jjim createJjim(Jjim jjim) {
        return jjimRepository.save(jjim);
    }

    // Read
    public Jjim findJjim(long jjimId) {
        return verifiedJjim(jjimId);
    }

    // Update
    public Jjim updateJjim(Jjim jjim) {
        Jjim findJjim = verifiedJjim(jjim.getId());

        Optional.ofNullable(jjim.getUser()).ifPresent(findJjim::setUser);
        Optional.ofNullable(jjim.getNotice()).ifPresent(findJjim::setNotice);

        return jjimRepository.save(findJjim);
    }

    // Delete
    public void deleteJjim(long jjimId) {
        Jjim jjim = verifiedJjim(jjimId);
        jjimRepository.delete(jjim);
    }

    // 증명
    public Jjim verifiedJjim(long jjimId) {
        Optional<Jjim> jjim = jjimRepository.findById(jjimId);
        return jjim.orElseThrow(() -> new BusinessLogicException(ExceptionCode.JJIM_NOT_FOUND));
    }
}
