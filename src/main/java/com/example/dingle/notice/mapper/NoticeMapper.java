package com.example.dingle.notice.mapper;

import com.example.dingle.notice.dto.NoticeRequestDto;
import com.example.dingle.notice.dto.NoticeResponseDto;
import com.example.dingle.notice.entity.Notice;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface NoticeMapper {
    Notice noticeResponseDtoPostToNotice(NoticeRequestDto.Post post);

    Notice noticeResponseDtoPatchToNotice(NoticeRequestDto.Patch patch);

    NoticeResponseDto.Response noticeToNoticeResponseDtoResponse(Notice notice, boolean isFavorite);

    default List<NoticeResponseDto.Response> noticeListToNoticeResponseDtoResponseList(List<Notice> notices, List<Boolean> isFavorites) {
        List<NoticeResponseDto.Response> responses = new ArrayList<>();
        for (int i = 0; i < notices.size(); i++) {
            responses.add(noticeToNoticeResponseDtoResponse(notices.get(i), isFavorites.get(i)));
        }

        return responses;
    }
}