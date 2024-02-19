package newtest.newtest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import newtest.newtest.entity.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDto {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long articleId;
    private LocalDateTime commentCreatedTime;

    public static CommentDto toCommentDto(Comment comment, Long id)
}
