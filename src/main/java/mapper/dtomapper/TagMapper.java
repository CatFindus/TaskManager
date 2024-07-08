package mapper.dtomapper;

import dto.request.TagRequest;
import dto.response.TagResponse;
import model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface TagMapper extends BaseMapper<Tag, TagResponse, TagResponse, TagRequest> {
    @Mapping(ignore = true, target = "id")
    Tag dtoToModel(TagRequest tag);

    TagResponse modelToDto(Tag tag);

    TagResponse modelToListResponse(Tag entity);
}
