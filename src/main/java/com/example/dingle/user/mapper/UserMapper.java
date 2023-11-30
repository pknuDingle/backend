package com.example.dingle.user.mapper;

import com.example.dingle.oauth.kakao.KakaoResponseDto;
import com.example.dingle.user.dto.UserRequestDto;
import com.example.dingle.user.dto.UserResponseDto;
import com.example.dingle.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userResponseDtoPostToUser(UserRequestDto.Post post);

    User userResponseDtoPatchToUser(UserRequestDto.Patch patch);

    UserResponseDto.Response userToUserResponseDtoResponse(User user);

    KakaoResponseDto.Response userToKakaoResponseDtoResponse(User user, String jwt);
}
