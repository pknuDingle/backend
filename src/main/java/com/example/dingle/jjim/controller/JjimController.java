package com.example.dingle.jjim.controller;

import com.example.dingle.jjim.dto.JjimRequestDto;
import com.example.dingle.jjim.dto.JjimResponseDto;
import com.example.dingle.jjim.entity.Jjim;
import com.example.dingle.jjim.mapper.JjimMapper;
import com.example.dingle.jjim.service.JjimService;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Jjim jjim = jjimService.createJjim(post.getNoticeId());
        JjimResponseDto.Response response = jjimMapper.jjimToJjimResponseDtoResponseCustom(jjim);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/{jjim-id}")
    public ResponseEntity getJjim(@Positive @PathVariable("jjim-id") long jjimId) {
        Jjim jjim = jjimService.findJjim(jjimId);
        JjimResponseDto.Response response = jjimMapper.jjimToJjimResponseDtoResponseCustom(jjim);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update
    @PutMapping("/{jjim-id}")
    public ResponseEntity patchJjim(@Positive @PathVariable("jjim-id") long jjimId,
            @Valid @RequestBody JjimRequestDto.Patch patch) {
        patch.setId(jjimId);
        Jjim jjim = jjimService.updateJjim(jjimMapper.jjimResponseDtoPatchToJjim(patch));
        JjimResponseDto.Response response = jjimMapper.jjimToJjimResponseDtoResponseCustom(jjim);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity patchJjim(@Valid @RequestBody JjimRequestDto.Patch patch) {
        jjimService.updateJjim(patch.getNoticeId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // Delete
    @DeleteMapping("/{jjim-id}")
    public ResponseEntity deleteJjim(@Positive @PathVariable("jjim-id") long jjimId) {
        jjimService.deleteJjim(jjimId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
