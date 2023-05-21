package com.codein.requestservicedto.article;

import com.codein.domain.image.ProfileImage;
import com.codein.responsedto.article.GetArticleResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetArticleServiceDto {
    private final Long id;
    private final String category;
    private final String title;
    private final String content;
    private final Integer viewNum;
    private final Integer commentNum;
    private final Integer likeNum;
    private final String createdAt;
    private final Long authorId;
    private final String nickname;
    private final String imagePath;
    private final boolean deleted;

    @Builder
    public GetArticleServiceDto(Long id, String category, String title, String content, LocalDateTime createdAt, Long authorId, String nickname, ProfileImage profileImage, boolean deleted) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.content = content;
        this.viewNum = 0;
        this.commentNum = 0;
        this.likeNum = 0;
        this.createdAt = compareDate(createdAt);
        this.authorId = authorId;
        this.nickname = nickname;
        if (profileImage != null) {
            this.imagePath = "/my-backend-api/images/profile/" + profileImage.getImgFileName();
        } else {
            this.imagePath = null;
        }
        this.deleted = deleted;
    }

    public GetArticleResponseDto toGetArticleResponseDto () {
        return GetArticleResponseDto.builder()
                .id(this.id)
                .category(this.category)
                .title(this.title)
                .content(this.content)
                .viewNum(this.viewNum)
                .commentNum(this.commentNum)
                .likeNum(this.likeNum)
                .createdAt(this.createdAt)
                .authorId(this.authorId)
                .nickname(this.nickname)
                .imagePath(this.imagePath)
                .deleted(this.deleted)
                .build();
    }

    public String compareDate(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        if (now.getYear() == createdAt.getYear()) {
            if (now.getMonth() == createdAt.getMonth()) {
                if (now.getDayOfMonth() == createdAt.getDayOfMonth()) {
                    if (now.getHour() == createdAt.getHour()) {
                        if (now.getMinute() == createdAt.getMinute()) {
                            return "0분 전";
                        } else {
                            return now.compareTo(createdAt)+"분 전";
                        }
                    } else {
                        return now.compareTo(createdAt)+"시간 전";
                    }
                } else {
                    return now.compareTo(createdAt)+"일 전";
                }
            } else {
                return now.compareTo(createdAt)+"달 전";
            }
        } else {
            return now.compareTo(createdAt)+"년 전";
        }
    }
}
