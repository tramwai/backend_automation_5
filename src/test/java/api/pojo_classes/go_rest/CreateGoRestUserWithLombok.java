package api.pojo_classes.go_rest;

import lombok.Builder;
import lombok.Data;

// With @Data from lombok we can eliminate the getters and setters
@Data

// With @Builder from lombok we are able to assign the value to the attributes
@Builder

public class CreateGoRestUserWithLombok {

    /**
     * {
     * "name": "",
     * "gender": "",
     * "email": "Ophelia.Mraz@hotmail.com",
     * "status": ""
     * }
     */
    private String name;
    private String gender;
    private String email;
    private String status;



}
