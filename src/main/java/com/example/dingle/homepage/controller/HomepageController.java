package com.example.dingle.homepage.controller;

import com.example.dingle.homepage.dto.HomepageRequestDto;
import com.example.dingle.homepage.dto.HomepageResponseDto;
import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.homepage.mapper.HomepageMapper;
import com.example.dingle.homepage.service.HomepageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/homepage")
@Validated
@AllArgsConstructor
public class HomepageController {
    private final HomepageService homepageService;
    private final HomepageMapper homepageMapper;

    // Create
    @PostMapping
    public ResponseEntity postHomepage(@Valid @RequestBody HomepageRequestDto.Post post) {
        Homepage homepage = homepageService.createHomepage(homepageMapper.homepageRequestDtoPostToHomepage(post));
        HomepageResponseDto.Response response = homepageMapper.homepageToHomepageResponseDtoResponse(homepage);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/{homepage-id}")
    public ResponseEntity getHomepage(@Positive @PathVariable("homepage-id") long homepageId) {
        Homepage homepage = homepageService.findHomepage(homepageId);
        HomepageResponseDto.Response response = homepageMapper.homepageToHomepageResponseDtoResponse(homepage);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update
    @PutMapping("/{homepage-id}")
    public ResponseEntity patchHomepage(@Positive @PathVariable("homepage-id") long homepageId,
                                        @Valid @RequestBody HomepageRequestDto.Patch patch) {
        patch.setId(homepageId);
        Homepage homepage = homepageService.updateHomepage(homepageMapper.homepageRequestDtoPatchToHomepage(patch));
        HomepageResponseDto.Response response = homepageMapper.homepageToHomepageResponseDtoResponse(homepage);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Delete
    @DeleteMapping("/{homepage-id}")
    public ResponseEntity deleteHomepage(@Positive @PathVariable("homepage-id") long homepageId) {
        homepageService.deleteHomepage(homepageId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
