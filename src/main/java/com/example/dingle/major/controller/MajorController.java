package com.example.dingle.major.controller;

import com.example.dingle.major.mapper.MajorMapper;
import com.example.dingle.major.dto.MajorRequestDto;
import com.example.dingle.major.dto.MajorResponseDto;
import com.example.dingle.major.entity.Major;
import com.example.dingle.major.service.MajorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/major")
@Validated
@AllArgsConstructor
public class MajorController {
    private final MajorService majorService;
    private final MajorMapper majorMapper;

    // Create
    @PostMapping
    public ResponseEntity postMajor(@Valid @RequestBody MajorRequestDto.Post post) {
        Major major = majorService.createMajor(majorMapper.majorRequestDtoPostToMajor(post));
        MajorResponseDto.Response response = majorMapper.majorToMajorResponseDtoResponse(major);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/{major-id}")
    public ResponseEntity getMajor(@Positive @PathVariable("major-id") long majorId) {
        Major major = majorService.findMajor(majorId);
        MajorResponseDto.Response response = majorMapper.majorToMajorResponseDtoResponse(major);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update
    @PutMapping("/{major-id}")
    public ResponseEntity patchMajor(@Positive @PathVariable("major-id") long majorId,
                                     @Valid @RequestBody MajorRequestDto.Patch patch) {
        patch.setId(majorId);
        Major major = majorService.updateMajor(majorMapper.majorRequestDtoPatchToMajor(patch));
        MajorResponseDto.Response response = majorMapper.majorToMajorResponseDtoResponse(major);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Delete
    @DeleteMapping("/{major-id}")
    public ResponseEntity deleteMajor(@Positive @PathVariable("major-id") long majorId) {
        majorService.deleteMajor(majorId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
