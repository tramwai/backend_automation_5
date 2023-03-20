package api.pojo_classes.go_rest.comments;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GoRestCommentWithLombok {
    /**
     * {
     *     "code": 408,
     *     "meta": {
     *         "pagination": {
     *             "total": 2000,
     *             "pages": 211,
     *             "page": 1,
     *             "limit": 5,
     *             "links": {
     *                 "previous": null,
     *                 "current": "https://gorest.co.in/public/v1/comments?page=1",
     *                 "next": "https://gorest.co.in/public/v1/comments?page=2"
     *             }
     *         }
     *     },
     *     "data": [
     *         {
     *             "id": 2220,
     *             "post_id": 5585,
     *             "name": "Tech Global",
     *             "email": "kathi.armstrong@hotmail.com",
     *             "body": "com.github.javafaker.Faker@77f905e3"
     *         },
     *         {
     *             "id": 2221,
     *             "post_id": 5586,
     *             "name": "Tech Global",
     *             "email": "kesha.runolfsdottir@hotmail.com",
     *             "body": "com.github.javafaker.Faker@77f905e3"
     *         },
     *         {
     *             "id": 2222,
     *             "post_id": 5587,
     *             "name": "Tech Global",
     *             "email": "brian.fisher@yahoo.com",
     *             "body": "com.github.javafaker.Faker@77f905e3"
     *         }
     *     ]
     * }
     */

    private int code;
    private Meta meta;
    private List<CommentData> data;
}
