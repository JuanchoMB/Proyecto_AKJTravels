package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.commentDTO.CommentDTO;
import co.edu.uniquindio.application.dto.userDTO.UserCommentDTO;
import co.edu.uniquindio.application.model.Comment;
import co.edu.uniquindio.application.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ListCommentsMapper {

  @Mapping(source = "id",         target = "id")          // ← Comment.id  → CommentDTO.id
  @Mapping(source = "createdAt",  target = "commentDate") // ← Comment.createdAt → commentDate
  @Mapping(source = "user",       target = "user")        // ← usará mapUser(...)
  CommentDTO toCommentDTO(Comment comment);

  // MapStruct usará este método para convertir User → UserCommentDTO
  default UserCommentDTO mapUser(User user){
    if (user == null) return null;
    return new UserCommentDTO(
      user.getName(),
      user.getPhotoUrl()
    );
  }
}
