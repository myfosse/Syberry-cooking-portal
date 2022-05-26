package by.bsu.cookingportal.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationRequest {
    @NotBlank
    @Size(min = 1, max = 60)
    private String title;

    @NotBlank
    @Size(min = 1, max = 255)
    private String text;

    @NotBlank
    @Size(max = 60)
    @Email(message = "Email not valid")
    private String email;
}
