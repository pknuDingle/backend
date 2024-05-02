package com.example.dingle.keyword.mapper;

import com.example.dingle.keyword.dto.KeywordRequestDto;
import com.example.dingle.keyword.dto.KeywordResponseDto;
import com.example.dingle.keyword.entity.Keyword;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KeywordMapper {

    Keyword keywordRequestDtoPostToKeyword(KeywordRequestDto.Post post);

    Keyword keywordRequestDtoPatchToKeyword(KeywordRequestDto.Patch patch);

    KeywordResponseDto.Response keywordToKeywordResponseDtoResponse(Keyword keyword);

    List<KeywordResponseDto.Response> listKeywordToListKeywordResponseDtoResponse(
            List<Keyword> keyword);
}
