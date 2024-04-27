package mapper.dtomapper;

import dto.request.DtoRequest;
import dto.response.FullResponse;
import dto.response.ShortResponse;
import lombok.experimental.UtilityClass;
import model.*;

@UtilityClass
public final class MapperFabric {
    public static  <E extends BaseEntity, F extends FullResponse, S extends ShortResponse, R extends DtoRequest> BaseMapper<E,F,S,R> getMapper(Class<E> clazz) {
        if (clazz.equals(User.class)) return (BaseMapper<E, F, S, R>) new UserMapperImpl();
        if (clazz.equals(Task.class)) return (BaseMapper<E, F, S, R>) new TaskMapperImpl();
        if (clazz.equals(Tag.class)) return (BaseMapper<E, F, S, R>) new TagMapperImpl();
        if (clazz.equals(Comment.class)) return (BaseMapper<E, F, S, R>) new CommentMapperImpl();
        return null;
    }
}
