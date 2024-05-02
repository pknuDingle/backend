package com.example.dingle.homepage.mapper;

import com.example.dingle.homepage.dto.HomepageRequestDto;
import com.example.dingle.homepage.dto.HomepageResponseDto;
import com.example.dingle.homepage.entity.Homepage;
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
public class HomepageMapperImpl implements HomepageMapper {

    @Override
    public Homepage homepageRequestDtoPostToHomepage(HomepageRequestDto.Post post) {
        if ( post == null ) {
            return null;
        }

        Homepage homepage = new Homepage();

        homepage.setId( post.getId() );
        homepage.setName( post.getName() );
        homepage.setUrl( post.getUrl() );

        return homepage;
    }

    @Override
    public Homepage homepageRequestDtoPatchToHomepage(HomepageRequestDto.Patch patch) {
        if ( patch == null ) {
            return null;
        }

        Homepage homepage = new Homepage();

        homepage.setId( patch.getId() );
        homepage.setName( patch.getName() );
        homepage.setUrl( patch.getUrl() );

        return homepage;
    }

    @Override
    public HomepageResponseDto.Response homepageToHomepageResponseDtoResponse(Homepage homepage) {
        if ( homepage == null ) {
            return null;
        }

        long id = 0L;
        String name = null;
        String url = null;

        id = homepage.getId();
        name = homepage.getName();
        url = homepage.getUrl();

        HomepageResponseDto.Response response = new HomepageResponseDto.Response( id, name, url );

        return response;
    }

    @Override
    public List<HomepageResponseDto.Response> ListhomepageToListHomepageResponseDtoResponse(List<Homepage> homepage) {
        if ( homepage == null ) {
            return null;
        }

        List<HomepageResponseDto.Response> list = new ArrayList<HomepageResponseDto.Response>( homepage.size() );
        for ( Homepage homepage1 : homepage ) {
            list.add( homepageToHomepageResponseDtoResponse( homepage1 ) );
        }

        return list;
    }
}
