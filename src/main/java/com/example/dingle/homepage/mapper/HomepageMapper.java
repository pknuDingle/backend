package com.example.dingle.homepage.mapper;

import com.example.dingle.homepage.dto.HomepageRequestDto;
import com.example.dingle.homepage.dto.HomepageResponseDto;
import com.example.dingle.homepage.entity.Homepage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HomepageMapper {
    Homepage homepageRequestDtoPostToHomepage(HomepageRequestDto.Post post);

    Homepage homepageRequestDtoPatchToHomepage(HomepageRequestDto.Patch patch);

    HomepageResponseDto.Response homepageToHomepageResponseDtoResponse(Homepage homepage);
}
