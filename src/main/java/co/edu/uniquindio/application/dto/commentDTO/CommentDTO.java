package co.edu.uniquindio.application.dto.commentDTO;

import co.edu.uniquindio.application.dto.userDTO.UserCommentDTO;
import java.time.LocalDateTime;

public record CommentDTO(String id,
                         String comment,
                         LocalDateTime commentDate,
                         int rating,
                         UserCommentDTO user,

                         String reply
) {
}
