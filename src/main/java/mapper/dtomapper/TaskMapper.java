package mapper.dtomapper;

import dto.request.TaskRequest;
import dto.response.TaskForListResponse;
import dto.response.TaskFullResponse;
import model.Task;
import model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface TaskMapper extends BaseMapper<Task, TaskFullResponse, TaskForListResponse, TaskRequest>{
    @Mapping(ignore = true, target = "id")
    @Mapping(target = "comments", ignore = true)
    Task dtoToModel(TaskRequest request, User user, User createdBy, User updatedBy);

    TaskFullResponse modelToFullResponse(Task task);

    TaskForListResponse modelToListResponse(Task task);
}
