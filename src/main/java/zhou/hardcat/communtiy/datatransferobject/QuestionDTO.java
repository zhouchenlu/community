package zhou.hardcat.communtiy.datatransferobject;

import lombok.Data;
import zhou.hardcat.communtiy.model.Comment;
import zhou.hardcat.communtiy.model.User;

import java.util.List;

@Data public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private Integer creator;
    private List<Comment> comment;
}
