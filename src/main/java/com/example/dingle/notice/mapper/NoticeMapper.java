package com.example.dingle.notice.mapper;

import com.example.dingle.notice.dto.NoticeRequestDto;
import com.example.dingle.notice.dto.NoticeResponseDto;
import com.example.dingle.notice.entity.Notice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoticeMapper {
    Notice noticeResponseDtoPostToNotice(NoticeRequestDto.Post post);
    Notice noticeResponseDtoPatchToNotice(NoticeRequestDto.Patch patch);
    NoticeResponseDto.Response noticeToNoticeResponseDtoResponse(Notice notice);
}