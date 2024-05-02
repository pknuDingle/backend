package com.example.dingle.jjim.mapper;

import com.example.dingle.jjim.dto.JjimRequestDto;
import com.example.dingle.jjim.dto.JjimResponseDto;
import com.example.dingle.jjim.entity.Jjim;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-08T04:18:45+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class JjimMapperImpl implements JjimMapper {

    @Override
    public Jjim jjimResponseDtoPostToJjim(JjimRequestDto.Post post) {
        if ( post == null ) {
            return null;
        }

        Jjim jjim = new Jjim();

        jjim.setId( post.getId() );

        return jjim;
    }

    @Override
    public Jjim jjimResponseDtoPatchToJjim(JjimRequestDto.Patch patch) {
        if ( patch == null ) {
            return null;
        }

        Jjim jjim = new Jjim();

        jjim.setId( patch.getId() );

        return jjim;
    }

    @Override
    public JjimResponseDto.Response jjimToJjimResponseDtoResponse(Jjim jjim) {
        if ( jjim == null ) {
            return null;
        }

        long id = 0L;
        LocalDateTime createdAt = null;
        LocalDateTime modifiedAt = null;

        id = jjim.getId();
        createdAt = jjim.getCreatedAt();
        modifiedAt = jjim.getModifiedAt();

        long userId = 0L;
        long noticeId = 0L;

        JjimResponseDto.Response response = new JjimResponseDto.Response( id, userId, noticeId, createdAt, modifiedAt );

        return response;
    }
}
