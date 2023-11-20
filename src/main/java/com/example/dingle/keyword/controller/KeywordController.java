package com.example.dingle.keyword.controller;

import com.example.dingle.keyword.dto.KeywordRequestDto;
import com.example.dingle.keyword.dto.KeywordResponseDto;
import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.keyword.mapper.KeywordMapper;
import com.example.dingle.keyword.service.KeywordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;









import java.util.List;

@RestController
@RequestMapping("/keyword")
@Validated
@AllArgsConstructor
public class KeywordController {
    private final KeywordService keywordService;
    private final KeywordMapper keywordMapper;

    // Create
    @PostMapping
    public ResponseEntity postKeyword(@Valid @RequestBody KeywordRequestDto.Post post) {
        Keyword keyword = keywordService.createKeyword(keywordMapper.keywordRequestDtoPostToKeyword(post));
        KeywordResponseDto.Response response = keywordMapper.keywordToKeywordResponseDtoResponse(keyword);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/{keyword-id}")
    public ResponseEntity getKeyword(@Positive @PathVariable("keyword-id") long keywordId) {
        Keyword keyword = keywordService.findKeyword(keywordId);
        KeywordResponseDto.Response response = keywordMapper.keywordToKeywordResponseDtoResponse(keyword);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity getKeywords() {
        List<Keyword> keywords = keywordService.findAllKeywords();
        List<KeywordResponseDto.Response> responses = keywordMapper.listKeywordToListKeywordResponseDtoResponse(keywords);

        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    // Update
    @PutMapping("/{keyword-id}")
    public ResponseEntity patchKeyword(@Positive @PathVariable("keyword-id") long keywordId,
                                       @Valid @RequestBody KeywordRequestDto.Patch patch) {
        patch.setId(keywordId);
        Keyword keyword = keywordService.updateKeyword(keywordMapper.keywordRequestDtoPatchToKeyword(patch));
        KeywordResponseDto.Response response = keywordMapper.keywordToKeywordResponseDtoResponse(keyword);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity patchKeyword(@Valid @RequestBody KeywordRequestDto.PatchUser patch) {
        keywordService.updateKeyword(patch.getKeywords());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Delete
    @DeleteMapping("/{keyword-id}")
    public ResponseEntity deleteKeyword(@Positive @PathVariable("keyword-id") long keywordId) {
        keywordService.deleteKeyword(keywordId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
