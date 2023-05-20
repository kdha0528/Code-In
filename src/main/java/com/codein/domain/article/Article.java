package com.codein.domain.article;

import com.codein.domain.member.Member;
import com.codein.requestservicedto.article.GetArticleServiceDto;
import com.codein.responsedto.article.ActivityListItem;
import com.codein.responsedto.article.ArticleListItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @NotNull
    private Category category;
    @NotNull
    private String title;
    @NotNull
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private Integer viewNum;
    private Integer commentNum;
    private Integer likeNum;
    @NotNull
    private LocalDateTime createdAt;
    private boolean deleted;


    @Builder
    public Article(Member member, Category category, String title, String content, Integer viewNum, Integer commentNum, Integer likeNum) {
        this.member = member;
        this.category = category;
        this.title = title;
        this.content = content;
        this.viewNum = (viewNum == null) ? 0 : viewNum;
        this.commentNum = (commentNum == null) ? 0 : commentNum;
        this.likeNum = (likeNum == null) ? 0 : likeNum;
        this.createdAt = LocalDateTime.now();
        this.deleted = false;
    }

    public Article edit(ArticleEditor articleEditor) {
        this.category = Category.valueOf(articleEditor.getCategory());
        this.title = articleEditor.getTitle();
        this.content = articleEditor.getContent();
        return this;
    }

    public ArticleListItem toArticleListItem(){
        return ArticleListItem.builder()
                .id(this.getId())
                .title(this.getTitle())
                .authorId(this.member.getId())
                .profileImage(this.member.getProfileImage())
                .nickname(this.member.getNickname())
                .createdAt(this.getCreatedAt())
                .commentNum(this.getCommentNum())
                .likeNum(this.getLikeNum())
                .viewNum(this.getViewNum())
                .build();
    }

    public ActivityListItem toActivityListItem(){
        return ActivityListItem.builder()
                .id(this.getId())
                .authorId(this.member.getId())
                .category(this.category.getName())
                .title(this.getTitle())
                .nickname(this.member.getNickname())
                .createdAt(this.getCreatedAt())
                .build();
    }

    public GetArticleServiceDto toGetArticleServiceDto(){
        return GetArticleServiceDto.builder()
                .id(this.id)
                .category(this.category.getName())
                .title(this.title)
                .content(this.content)
                .createdAt(this.createdAt)
                .commentNum(this.commentNum)
                .viewNum(this.viewNum)
                .likeNum(this.likeNum)
                .authorId(this.member.getId())
                .nickname(this.member.getNickname())
                .profileImage(this.member.getProfileImage())
                .deleted(this.deleted)
                .build();
    }
}
