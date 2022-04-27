package mars.mission.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Employee {

    @NotNull(message = "username is mandatory")
    @NotEmpty(message = "username is mandatory")
    public String username;

    @NotNull(message = "password is mandatory")
    @NotEmpty(message = "password is mandatory")
    public String password;
}
