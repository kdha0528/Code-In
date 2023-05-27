package com.codein.domain.comment;

import com.codein.domain.article.Article;
import com.codein.domain.member.Member;
import com.codein.responsedto.comment.CommentListItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment target;

    @NotNull
    private String content;

    @NotNull
    private LocalDateTime createdAt;

    private Integer likeNum;
    private Integer dislikeNum;

    private boolean deleted;

    @Builder
    public Comment(Article article, Comment target, Member member, String content) {
        this.parentId = 0L; //  초기화
        this.article = article;
        this.target = target;
        this.member = member;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.likeNum = 0;
        this.dislikeNum = 0;
        this.deleted = false;
    }

    public void setParentId() {
        if(this.target == null){
            this.parentId = this.id;
        } else {
            if (this.target.getParentId() == null) {     // terget이 parentId가 없다면(최상위 댓글이라면) parent id는 최상위 댓글의 id
                this.parentId = this.target.getId();
            } else {    // target이 parentId가 있다면(최상위 댓글이 아니라면) 최상위 댓글을 parent id로 설정
                this.parentId = this.target.getParentId();
            }
        }
    }

    public CommentListItem toCommentListItem() {
        Long targetId = null;
        String targetNickname = null;
        if(this.target != null) {
            targetNickname = this.target.getMember().getNickname();
            targetId = this.target.getId();
        }
        return  CommentListItem.builder()
                .id(this.id)
                .content(this.content)
                .createdAt(this.createdAt)
                .articleId(this.article.getId())
                .commenterId(this.member.getId())
                .commenterNickname(this.member.getNickname())
                .commenterProfileImage(this.member.getProfileImage())
                .targetId(targetId)
                .targetNickname(targetNickname)
                .build();
    }

    public Comment edit(CommentEditor commentEditor){
        this.content = commentEditor.getContent();
        return this;
    }

    public void changeLikeNum(Integer changes){
        this.likeNum += changes;
    }
    public void changeDislikeNum(Integer changes) {
        this.dislikeNum += changes;
    }

    public void deleteComment() { this.deleted = true;}
}
