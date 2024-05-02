package com.example.dingle.user.mapper;

import com.example.dingle.oauth.kakao.KakaoResponseDto;
import com.example.dingle.user.dto.UserRequestDto;
import com.example.dingle.user.dto.UserResponseDto;
import com.example.dingle.user.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-08T04:18:43+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User userResponseDtoPostToUser(UserRequestDto.Post post) {
        if ( post == null ) {
            return null;
        }

        User user = new User();

        user.setId( post.getId() );
        user.setName( post.getName() );
        user.setEmail( post.getEmail() );
        user.setImageUrl( post.getImageUrl() );
        user.setKakakoId( post.getKakakoId() );
        user.setStatus( post.getStatus() );

        return user;
    }

    @Override
    public User userResponseDtoPatchToUser(UserRequestDto.Patch patch) {
        if ( patch == null ) {
            return null;
        }

        User user = new User();

        user.setId( patch.getId() );
        user.setName( patch.getName() );
        user.setEmail( patch.getEmail() );
        user.setImageUrl( patch.getImageUrl() );
        user.setKakakoId( patch.getKakakoId() );
        user.setStatus( patch.getStatus() );

        return user;
    }

    @Override
    public UserResponseDto.Response userToUserResponseDtoResponse(User user) {
        if ( user == null ) {
            return null;
        }

        long id = 0L;
        String name = null;
        String email = null;
        String imageUrl = null;
        long kakakoId = 0L;
        User.Attendance status = null;

        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        imageUrl = user.getImageUrl();
        kakakoId = user.getKakakoId();
        status = user.getStatus();

        UserResponseDto.Response response = new UserResponseDto.Response( id, name, email, imageUrl, kakakoId, status );

        return response;
    }

    @Override
    public KakaoResponseDto.Response userToKakaoResponseDtoResponse(User user, String jwt) {
        if ( user == null && jwt == null ) {
            return null;
        }

        long id = 0L;
        String name = null;
        String email = null;
        String imageUrl = null;
        long kakakoId = 0L;
        User.Attendance status = null;
        if ( user != null ) {
            id = user.getId();
            name = user.getName();
            email = user.getEmail();
            imageUrl = user.getImageUrl();
            kakakoId = user.getKakakoId();
            status = user.getStatus();
        }
        String jwt1 = null;
        jwt1 = jwt;

        KakaoResponseDto.Response response = new KakaoResponseDto.Response( id, name, email, imageUrl, kakakoId, status, jwt1 );

        return response;
    }
}
