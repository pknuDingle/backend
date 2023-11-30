package com.example.dingle.jjim.service;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.jjim.entity.Jjim;
import com.example.dingle.jjim.repository.JjimRepository;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.notice.service.NoticeService;
import com.example.dingle.user.entity.User;
import com.example.dingle.util.FindUserByJWT;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class JjimService {
    private final JjimRepository jjimRepository;
    private final NoticeService noticeService;
    private final FindUserByJWT findUserByJWT;

    // Create
    public Jjim createJjim(Jjim jjim) {
        return jjimRepository.save(jjim);
    }

    public Jjim createJjim(long noticeId) {
        User user = findUserByJWT.getLoginUser();
        Notice notice = noticeService.verifiedNotice(noticeId);
        Jjim jjim = verifiedJjim(user, notice);

        if (jjim == null) {
            jjim = new Jjim();
            jjim.setNotice(notice);
            jjim.setUser(user);
            jjimRepository.save(jjim);
        }

        return jjim;
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

    public void updateJjim(long noticeId) {
        Jjim jjim = verifiedJjim(findUserByJWT.getLoginUser(), noticeService.verifiedNotice(noticeId));
        jjimRepository.delete(jjim);
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

    public Jjim verifiedJjim(User user, Notice notice) {
        Optional<Jjim> jjim = jjimRepository.findByUserAndNotice(user, notice);
        return jjim.orElse(null);
    }
}
