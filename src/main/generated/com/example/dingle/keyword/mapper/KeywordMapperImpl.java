package com.example.dingle.keyword.mapper;

import com.example.dingle.keyword.dto.KeywordRequestDto;
import com.example.dingle.keyword.dto.KeywordResponseDto;
import com.example.dingle.keyword.entity.CategoryType;
import com.example.dingle.keyword.entity.Keyword;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-08T04:18:44+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class KeywordMapperImpl implements KeywordMapper {

    @Override
    public Keyword keywordRequestDtoPostToKeyword(KeywordRequestDto.Post post) {
        if ( post == null ) {
            return null;
        }

        Keyword keyword = new Keyword();

        keyword.setId( post.getId() );
        keyword.setName( post.getName() );

        return keyword;
    }

    @Override
    public Keyword keywordRequestDtoPatchToKeyword(KeywordRequestDto.Patch patch) {
        if ( patch == null ) {
            return null;
        }

        Keyword keyword = new Keyword();

        keyword.setId( patch.getId() );
        keyword.setName( patch.getName() );

        return keyword;
    }

    @Override
    public KeywordResponseDto.Response keywordToKeywordResponseDtoResponse(Keyword keyword) {
        if ( keyword == null ) {
            return null;
        }

        long id = 0L;
        String name = null;
        CategoryType categoryType = null;

        id = keyword.getId();
        name = keyword.getName();
        categoryType = keyword.getCategoryType();

        KeywordResponseDto.Response response = new KeywordResponseDto.Response( id, name, categoryType );

        return response;
    }

    @Override
    public List<KeywordResponseDto.Response> listKeywordToListKeywordResponseDtoResponse(List<Keyword> keyword) {
        if ( keyword == null ) {
            return null;
        }

        List<KeywordResponseDto.Response> list = new ArrayList<KeywordResponseDto.Response>( keyword.size() );
        for ( Keyword keyword1 : keyword ) {
            list.add( keywordToKeywordResponseDtoResponse( keyword1 ) );
        }

        return list;
    }
}
