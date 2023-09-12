package com.example.dingle.user.controller;

import com.example.dingle.user.dto.UserRequestDto;
import com.example.dingle.user.dto.UserResponseDto;
import com.example.dingle.user.entity.User;
import com.example.dingle.user.mapper.UserMapper;
import com.example.dingle.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/user")
@Validated
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    // Create
    @PostMapping
    public ResponseEntity postUser(@Valid @RequestBody UserRequestDto.Post post) {
        User user = userService.createUser(userMapper.userResponseDtoPostToUser(post));
        UserResponseDto.Response response = userMapper.userToUserResponseDtoResponse(user);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/{user-id}")
    public ResponseEntity getUser(@Positive @PathVariable("user-id") long userId) {
        User user = userService.findUser(userId);
        UserResponseDto.Response response = userMapper.userToUserResponseDtoResponse(user);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update
    @PutMapping("/{user-id}")
    public ResponseEntity patchUser(@Positive @PathVariable("user-id") long userId,
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
}
