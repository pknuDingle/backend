package com.example.dingle.major.mapper;

import com.example.dingle.major.dto.MajorRequestDto;
import com.example.dingle.major.dto.MajorResponseDto;
import com.example.dingle.major.entity.Major;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MajorMapper {
    Major majorRequestDtoPostToMajor(MajorRequestDto.Post post);

    Major majorRequestDtoPatchToMajor(MajorRequestDto.Patch patch);

    MajorResponseDto.Response majorToMajorResponseDtoResponse(Major major);
}
