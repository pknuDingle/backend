package com.example.dingle.notice.mapper;

import com.example.dingle.notice.dto.NoticeRequestDto;
import com.example.dingle.notice.dto.NoticeResponseDto;
import com.example.dingle.notice.entity.Notice;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-08T04:18:44+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class NoticeMapperImpl implements NoticeMapper {

    @Override
    public Notice noticeResponseDtoPostToNotice(NoticeRequestDto.Post post) {
        if ( post == null ) {
            return null;
        }

        Notice notice = new Notice();

        notice.setId( post.getId() );
        notice.setTitle( post.getTitle() );
        notice.setContent( post.getContent() );
        notice.setImage( post.getImage() );
        notice.setLink( post.getLink() );

        return notice;
    }

    @Override
    public Notice noticeResponseDtoPatchToNotice(NoticeRequestDto.Patch patch) {
        if ( patch == null ) {
            return null;
        }

        Notice notice = new Notice();

        notice.setId( patch.getId() );
        notice.setTitle( patch.getTitle() );
        notice.setContent( patch.getContent() );
        notice.setImage( patch.getImage() );
        notice.setLink( patch.getLink() );

        return notice;
    }

    @Override
    public NoticeResponseDto.Response noticeToNoticeResponseDtoResponse(Notice notice, boolean isFavorite) {
        if ( notice == null ) {
            return null;
        }

        long id = 0L;
        String title = null;
        String content = null;
        String image = null;
        String link = null;
        if ( notice != null ) {
            id = notice.getId();
            title = notice.getTitle();
            content = notice.getContent();
            image = notice.getImage();
            link = notice.getLink();
        }
        boolean isFavorite1 = false;
        isFavorite1 = isFavorite;

        NoticeResponseDto.Response response = new NoticeResponseDto.Response( id, title, content, image, link, isFavorite1 );

        return response;
    }
}
