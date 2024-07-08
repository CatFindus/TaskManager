package mapper.dtomapper;

import dto.request.UserRequest;
import dto.response.UserDefaultResponse;
import dto.response.UserFullResponse;
import model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface UserMapper extends BaseMapper<User, UserFullResponse, UserDefaultResponse, UserRequest>{
    @Mapping(ignore = true, target = "id")
    User dtoToModel(UserRequest request);

    UserFullResponse modelToFullResponse(User entity);

    UserDefaultResponse modelToListResponse(User entity);
}
