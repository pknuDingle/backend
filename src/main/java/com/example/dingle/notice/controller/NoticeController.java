package com.example.dingle.notice.controller;

import com.example.dingle.homepage.service.HomepageService;
import com.example.dingle.notice.dto.NoticeRequestDto;
import com.example.dingle.notice.dto.NoticeResponseDto;
import com.example.dingle.notice.entity.Notice;
import com.example.dingle.notice.mapper.NoticeMapper;
import com.example.dingle.notice.service.NoticeService;
import com.example.dingle.noticeKeyword.repository.NoticeKeywordRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notice")
@Validated
@AllArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
    private final NoticeMapper noticeMapper;
    private final NoticeKeywordRepository noticeKeywordRepository;
    private final HomepageService homepageService;

    // Create
    @PostMapping
    public ResponseEntity postNotice(@Valid @RequestBody NoticeRequestDto.Post post) {
        Notice notice = noticeService.createNotice(noticeMapper.noticeResponseDtoPostToNotice(post));
        NoticeResponseDto.Response response = noticeMapper.noticeToNoticeResponseDtoResponse(notice, noticeService.isFavorite(notice));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/{notice-id}")
    public ResponseEntity getNotice(@Positive @PathVariable("notice-id") long noticeId) {
        Notice notice = noticeService.findNotice(noticeId);
        NoticeResponseDto.Response response = noticeMapper.noticeToNoticeResponseDtoResponse(notice, noticeService.isFavorite(notice));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update
    @PutMapping("/{notice-id}")
    public ResponseEntity patchNotice(@Positive @PathVariable("notice-id") long noticeId,
                                      @Valid @RequestBody NoticeRequestDto.Patch patch) {
        patch.setId(noticeId);
        Notice notice = noticeService.updateNotice(noticeMapper.noticeResponseDtoPatchToNotice(patch));
        NoticeResponseDto.Response response = noticeMapper.noticeToNoticeResponseDtoResponse(notice, noticeService.isFavorite(notice));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{notice-id}")
    public ResponseEntity deleteNotice(@Positive @PathVariable("notice-id") long noticeId) {
        noticeService.deleteNotice(noticeId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // keyword별 notice 조회
    @GetMapping("/keyword")
    public ResponseEntity getNoticeByKeywordId(@RequestParam("keywordId") Long keywordId) throws Exception {
        List<NoticeResponseDto.Response> response = noticeKeywordRepository.findByKeywordId(keywordId).stream()
                .map(noticeKeyword -> noticeKeyword.getNotice())
                .map(notice -> noticeMapper.noticeToNoticeResponseDtoResponse(notice, noticeService.isFavorite(notice)))
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // homepage별 notice 조회
    @GetMapping("/homepage")
    public ResponseEntity getNoticeByHomepageId(@RequestParam("homepageId") Long homepageId) {
        List<Notice> notices = homepageService.findHomepageToNotice(homepageId);
        List<Boolean> isFavorites = noticeService.isFavorite(notices);
        List<NoticeResponseDto.Response> responses = noticeMapper.noticeListToNoticeResponseDtoResponseList(notices, isFavorites);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    // user의 keywords별 notice 조회
    @GetMapping("/keywords")
    public ResponseEntity getNoticeAndKeywords() {
        List<Notice> notices = noticeService.findNoticeByKeyword();
        List<Boolean> isFavorites = noticeService.isFavorite(notices);
        List<NoticeResponseDto.Response> responses = noticeMapper.noticeListToNoticeResponseDtoResponseList(notices, isFavorites);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    // user의 homepages별 notice 조회
    @GetMapping("/homepages")
    public ResponseEntity getNoticeAndHomepages() {
        List<Notice> notices = noticeService.findNoticeByHomepage();
        List<Boolean> isFavorites = noticeService.isFavorite(notices);
        List<NoticeResponseDto.Response> responses = noticeMapper.noticeListToNoticeResponseDtoResponseList(notices, isFavorites);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}