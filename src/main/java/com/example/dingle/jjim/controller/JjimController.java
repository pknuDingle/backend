package com.example.dingle.jjim.controller;

import com.example.dingle.jjim.dto.JjimRequestDto;
import com.example.dingle.jjim.dto.JjimResponseDto;
import com.example.dingle.jjim.entity.Jjim;
import com.example.dingle.jjim.mapper.JjimMapper;
import com.example.dingle.jjim.service.JjimService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/jjim")
@Validated
@AllArgsConstructor
public class JjimController {
    private final JjimService jjimService;
    private final JjimMapper jjimMapper;

    // Create
    @PostMapping
    public ResponseEntity postJjim(@Valid @RequestBody JjimRequestDto.Post post) {
        Jjim jjim = jjimService.createJjim(jjimMapper.jjimResponseDtoPostToJjim(post));
        JjimResponseDto.Response response = jjimMapper.jjimToJjimResponseDtoResponse(jjim);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/{jjim-id}")
    public ResponseEntity getJjim(@Positive @PathVariable("jjim-id") long jjimId) {
        Jjim jjim = jjimService.findJjim(jjimId);
        JjimResponseDto.Response response = jjimMapper.jjimToJjimResponseDtoResponse(jjim);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update
    @PutMapping("/{jjim-id}")
    public ResponseEntity patchJjim(@Positive @PathVariable("jjim-id") long jjimId,
                                      @Valid @RequestBody JjimRequestDto.Patch patch) {
        patch.setId(jjimId);
        Jjim jjim = jjimService.updateJjim(jjimMapper.jjimResponseDtoPatchToJjim(patch));
        JjimResponseDto.Response response = jjimMapper.jjimToJjimResponseDtoResponse(jjim);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Delete
    @DeleteMapping("/{jjim-id}")
    public ResponseEntity deleteJjim(@Positive @PathVariable("jjim-id") long jjimId) {
        jjimService.deleteJjim(jjimId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
