package com.example.dingle.notice.controller;

import com.example.dingle.notice.dto.NoticeRequestDto;
import com.example.dingle.notice.dto.NoticeResponseDto;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.notice.mapper.NoticeMapper;
import com.example.dingle.notice.service.NoticeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/notice")
@Validated
@AllArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
    private final NoticeMapper noticeMapper;

    // Create
    @PostMapping
    public ResponseEntity postNotice(@Valid @RequestBody NoticeRequestDto.Post post) {
        Notice notice = noticeService.createNotice(noticeMapper.noticeResponseDtoPostToNotice(post));
        NoticeResponseDto.Response response = noticeMapper.noticeToNoticeResponseDtoResponse(notice);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/{notice-id}")
    public ResponseEntity getNotice(@Positive @PathVariable("notice-id") long noticeId) {
        Notice notice = noticeService.findNotice(noticeId);
        NoticeResponseDto.Response response = noticeMapper.noticeToNoticeResponseDtoResponse(notice);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update
    @PutMapping("/{notice-id}")
    public ResponseEntity patchNotice(@Positive @PathVariable("notice-id") long noticeId,
                                      @Valid @RequestBody NoticeRequestDto.Patch patch) {
        patch.setId(noticeId);
        Notice notice = noticeService.updateNotice(noticeMapper.noticeResponseDtoPatchToNotice(patch));
        NoticeResponseDto.Response response = noticeMapper.noticeToNoticeResponseDtoResponse(notice);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Delete
    @DeleteMapping("/{notice-id}")
    public ResponseEntity deleteNotice(@Positive @PathVariable("notice-id") long noticeId) {
        noticeService.deleteNotice(noticeId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
