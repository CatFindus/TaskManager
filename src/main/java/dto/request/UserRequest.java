package dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest implements DtoRequest{
    private String firstName;
    private String middleName;
    private String lastName;
    private String userName;
    private String phone;
    private String passwordHash;
    private String role;
    private String intro;
    private String profile;
}
