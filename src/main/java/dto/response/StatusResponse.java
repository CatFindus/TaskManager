package dto.response;

import enums.TaskStatus;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@Data
public class StatusResponse implements DtoResponse {
    private final List<String> statuses = Arrays.stream(TaskStatus.values()).map(TaskStatus::name).toList();
}
