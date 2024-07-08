package mapper.dtomapper;

import dto.request.CommentRequest;
import dto.response.CommentFullResponse;
import model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface CommentMapper extends BaseMapper<Comment,CommentFullResponse, CommentFullResponse, CommentRequest> {
    @Mapping(ignore = true, target = "id")
    Comment dtoToModel(CommentRequest dto);

    CommentFullResponse modelToFullResponse(Comment entity);

    CommentFullResponse modelToListResponse(Comment entity);

}
