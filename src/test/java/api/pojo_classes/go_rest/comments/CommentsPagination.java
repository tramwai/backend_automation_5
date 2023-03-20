package api.pojo_classes.go_rest.comments;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CommentsPagination {

    private int total;
    private int pages;
    private int page;
    private int limit;
    private Links links;
}
