package dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentListResponse implements ShortResponse {
    private Long id;
    private UserDefaultResponse user;
    private String title;
    private LocalDateTime createdAt;
}
