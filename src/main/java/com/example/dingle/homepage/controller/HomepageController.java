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
import java.util.List;

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

    @GetMapping("/all")
    public ResponseEntity getHomepagse() {
        List<Homepage> homepages = homepageService.findAllHomepages();
        List<HomepageResponseDto.Response> responses = homepageMapper.ListhomepageToListHomepageResponseDtoResponse(homepages);

        return new ResponseEntity<>(responses, HttpStatus.CREATED);
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

    // user가 관심있는 homepage 등록
    @PutMapping
    public ResponseEntity patchHomepage(@Valid @RequestBody HomepageRequestDto.PatchUser patch) {
        homepageService.updateHomepage(patch.getHomepageIds());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Delete
    @DeleteMapping("/{homepage-id}")
    public ResponseEntity deleteHomepage(@Positive @PathVariable("homepage-id") long homepageId) {
        homepageService.deleteHomepage(homepageId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
