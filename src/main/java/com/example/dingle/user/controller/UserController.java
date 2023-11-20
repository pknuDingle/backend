package com.example.dingle.user.controller;

import com.example.dingle.homepage.dto.HomepageRequestDto;
import com.example.dingle.homepage.entity.Homepage;
import com.example.dingle.homepage.mapper.HomepageMapper;
import com.example.dingle.keyword.dto.KeywordRequestDto;
import com.example.dingle.keyword.dto.KeywordResponseDto;
import com.example.dingle.keyword.entity.Keyword;
import com.example.dingle.keyword.mapper.KeywordMapper;
import com.example.dingle.keyword.service.KeywordService;
import com.example.dingle.homepage.dto.HomepageResponseDto;
import com.example.dingle.homepage.service.HomepageService;
import com.example.dingle.user.dto.UserRequestDto;
import com.example.dingle.user.dto.UserResponseDto;
import com.example.dingle.user.entity.User;
import com.example.dingle.user.mapper.UserMapper;
import com.example.dingle.user.service.UserService;
import com.example.dingle.userKeyword.service.UserKeywordService;
import com.example.dingle.userHomepage.service.UserHomepageService;
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
@RequestMapping("/user")
@Validated
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final KeywordService keywordService;
    private final KeywordMapper keywordMapper;
    private final UserKeywordService userKeywordService;
    private final UserHomepageService userHomepageService;
    private final HomepageMapper homepageMapper;
    private final HomepageService homepageService;


    // 언니가 준 토큰으로 로그인 -> JWT 발급 받으면 -> API 요청
    // 카테고리 enum 값 달기

    // Create
    @PostMapping
    public ResponseEntity<UserResponseDto.Response> postUser(@Valid @RequestBody UserRequestDto.Post post) {
        User user = userService.createUser(userMapper.userResponseDtoPostToUser(post));
        UserResponseDto.Response response = userMapper.userToUserResponseDtoResponse(user);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/{user-id}")
    public ResponseEntity<UserResponseDto.Response> getUser(@Positive @PathVariable("user-id") long userId) {
        User user = userService.findUser(userId);
        UserResponseDto.Response response = userMapper.userToUserResponseDtoResponse(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getUser() {
        User user = userService.findUser();
        UserResponseDto.Response response = userMapper.userToUserResponseDtoResponse(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update
    @PutMapping("/{user-id}")
    public ResponseEntity<UserResponseDto.Response> patchUser(@Positive @PathVariable("user-id") long userId,
                                                              @Valid @RequestBody UserRequestDto.Patch patch) {
        patch.setId(userId);
        User user = userService.updateUser(userMapper.userResponseDtoPatchToUser(patch));
        UserResponseDto.Response response = userMapper.userToUserResponseDtoResponse(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteUser(@Positive @PathVariable("user-id") long userId) {
        userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/keywords")
    public ResponseEntity<List<KeywordResponseDto.Response>> getAllKeywords() {
        List<Keyword> categories = keywordService.findAllKeywords();
        List<KeywordResponseDto.Response> response = categories.stream()
                .map(keywordMapper::keywordToKeywordResponseDtoResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/keywords")
    public ResponseEntity setKeyword(@Valid @RequestBody List<KeywordRequestDto.Post> posts) {
        User currentUser = userService.findUser();

        userKeywordService.deleteAllUserKeyword(currentUser);
        List<Keyword> keywords = posts.stream()
                .map(post -> keywordMapper.keywordRequestDtoPostToKeyword(post))
                .collect(Collectors.toList());
        keywords.forEach(keyword -> userKeywordService.createUserKeyword(currentUser, keyword));

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/homepages")
    public ResponseEntity<List<HomepageResponseDto.Response>> getAllHomepages() {
        List<Homepage> homepages = homepageService.findAllHomepages();
        List<HomepageResponseDto.Response> response = homepages.stream()
                .map(homepageMapper::homepageToHomepageResponseDtoResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/homepages")
    public ResponseEntity setHomepage(@Valid @RequestBody List<HomepageRequestDto.Post> posts) {
        User currentUser = userService.findUser();

        userHomepageService.deleteAllUserHomepage(currentUser);
        List<Homepage> homepages = posts.stream()
                .map(post -> homepageMapper.homepageRequestDtoPostToHomepage(post))
                .collect(Collectors.toList());
        homepages.forEach(homepage -> userHomepageService.createUserHomepage(currentUser, homepage));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
