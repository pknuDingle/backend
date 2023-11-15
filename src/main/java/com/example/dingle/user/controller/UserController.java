package com.example.dingle.user.controller;

import com.example.dingle.category.dto.CategoryRequestDto;
import com.example.dingle.category.dto.CategoryResponseDto;
import com.example.dingle.category.entity.Category;
import com.example.dingle.category.mapper.CategoryMapper;
import com.example.dingle.category.service.CategoryService;
import com.example.dingle.major.dto.MajorRequestDto;
import com.example.dingle.major.dto.MajorResponseDto;
import com.example.dingle.major.entity.Major;
import com.example.dingle.major.mapper.MajorMapper;
import com.example.dingle.major.service.MajorService;
import com.example.dingle.user.dto.UserRequestDto;
import com.example.dingle.user.dto.UserResponseDto;
import com.example.dingle.user.entity.User;
import com.example.dingle.user.mapper.UserMapper;
import com.example.dingle.user.service.UserService;
import com.example.dingle.userCategory.service.UserCategoryService;
import com.example.dingle.userMajor.service.UserMajorService;
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
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final UserCategoryService userCategoryService;
    private final UserMajorService userMajorService;
    private final MajorMapper majorMapper;
    private final MajorService majorService;


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

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getUser() {
        User user = userService.findUser();
        UserResponseDto.Response response = userMapper.userToUserResponseDtoResponse(user);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update
    @PutMapping("/{user-id}")
    public ResponseEntity<UserResponseDto.Response> patchUser(@Positive @PathVariable("user-id") long userId,
                                                              @Valid @RequestBody UserRequestDto.Patch patch) {
        patch.setId(userId);
        User user = userService.updateUser(userMapper.userResponseDtoPatchToUser(patch));
        UserResponseDto.Response response = userMapper.userToUserResponseDtoResponse(user);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Delete
    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteUser(@Positive @PathVariable("user-id") long userId) {
        userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/keywords")
    public ResponseEntity<List<CategoryResponseDto.Response>> getAllKeywords() {
        List<Category> categories = categoryService.findAllCategories();
        List<CategoryResponseDto.Response> response = categories.stream()
                .map(categoryMapper::categoryToCategoryResponseDtoResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/keywords")
    public ResponseEntity setKeyword(@Valid @RequestBody List<CategoryRequestDto.Post> posts) {
        User currentUser = userService.findUser();

        userCategoryService.deleteAllUserCategory(currentUser);
        List<Category> categories = posts.stream()
                .map(post -> categoryMapper.categoryRequestDtoPostToCategory(post))
                .collect(Collectors.toList());
        categories.forEach(category -> userCategoryService.createUserCategory(currentUser, category));

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/homepages")
    public ResponseEntity<List<MajorResponseDto.Response>> getAllHomepages() {
        List<Major> majors = majorService.findAllMajors();
        List<MajorResponseDto.Response> response = majors.stream()
                .map(majorMapper::majorToMajorResponseDtoResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/homepages")
    public ResponseEntity setHomepage(@Valid @RequestBody List<MajorRequestDto.Post> posts) {
        User currentUser = userService.findUser();

        userMajorService.deleteAllUserMajor(currentUser);
        List<Major> majors = posts.stream()
                .map(post -> majorMapper.majorRequestDtoPostToMajor(post))
                .collect(Collectors.toList());
        majors.forEach(major -> userMajorService.createUserMajor(currentUser, major));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
