package se.iths.corkdork.email;

import se.iths.corkdork.validator.UniqueEmail;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Feedback {

    @NotBlank
    private String name;

    @NotBlank
    @UniqueEmail
    private String email;

    @NotBlank
    @Min(10)
    private String feedback;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
