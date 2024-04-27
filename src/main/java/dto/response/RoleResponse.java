package dto.response;

import enums.Role;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@Data
public class RoleResponse implements DtoResponse {
    private final List<String> roles = Arrays.stream(Role.values()).map(Role::name).toList();
}
