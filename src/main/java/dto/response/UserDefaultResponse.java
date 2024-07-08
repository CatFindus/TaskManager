package dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDefaultResponse implements  ShortResponse{
    private Long id;
    private String role;
    private String firstName;
    private String middleName;
    private String lastName;
    private String userName;
}
