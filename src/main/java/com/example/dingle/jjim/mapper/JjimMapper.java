package com.example.dingle.jjim.mapper;

import com.example.dingle.jjim.dto.JjimRequestDto;
import com.example.dingle.jjim.dto.JjimResponseDto;
import com.example.dingle.jjim.entity.Jjim;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JjimMapper {
    Jjim jjimResponseDtoPostToJjim(JjimRequestDto.Post post);
    Jjim jjimResponseDtoPatchToJjim(JjimRequestDto.Patch patch);
    JjimResponseDto.Response jjimToJjimResponseDtoResponse(Jjim jjim);
}
