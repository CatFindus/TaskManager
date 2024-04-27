package dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest implements DtoRequest {
    private Long id;
    private Long taskId;
    private Long userId;
    private String title;
    private String content;
}
