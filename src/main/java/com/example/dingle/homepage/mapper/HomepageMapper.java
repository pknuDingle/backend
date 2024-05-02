package com.example.dingle.homepage.mapper;

import com.example.dingle.homepage.dto.HomepageRequestDto;
import com.example.dingle.homepage.dto.HomepageResponseDto;
import com.example.dingle.homepage.entity.Homepage;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HomepageMapper {

    Homepage homepageRequestDtoPostToHomepage(HomepageRequestDto.Post post);

    Homepage homepageRequestDtoPatchToHomepage(HomepageRequestDto.Patch patch);

    HomepageResponseDto.Response homepageToHomepageResponseDtoResponse(Homepage homepage);

    List<HomepageResponseDto.Response> ListhomepageToListHomepageResponseDtoResponse(
            List<Homepage> homepage);
}
