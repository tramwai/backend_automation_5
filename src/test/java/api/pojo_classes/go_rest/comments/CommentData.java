package api.pojo_classes.go_rest.comments;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CommentData {

    private int id;
    private int postId;
    private String name;
    private String email;
    private String body;
}
