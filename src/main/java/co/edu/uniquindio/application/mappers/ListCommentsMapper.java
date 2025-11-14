package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.commentDTO.CommentDTO;
import co.edu.uniquindio.application.dto.userDTO.UserCommentDTO;
import co.edu.uniquindio.application.model.Comment;
import co.edu.uniquindio.application.model.Reply;
import co.edu.uniquindio.application.model.User;
import co.edu.uniquindio.application.repositories.ReplyRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
  componentModel = MappingConstants.ComponentModel.SPRING,
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ListCommentsMapper {

  @Autowired
  protected ReplyRepository replyRepository;   // 👈 se inyecta solo

  @Mapping(target = "id",          source = "id")
  @Mapping(target = "comment",     source = "comment")
  @Mapping(target = "commentDate", source = "createdAt")
  @Mapping(target = "rating",      source = "rating")
  @Mapping(target = "user",        expression = "java(mapUser(comment.getUser()))")
  @Mapping(target = "reply",       expression = "java(loadReply(comment.getId()))")


  public abstract CommentDTO ToCommentDTO(Comment comment);

  protected UserCommentDTO mapUser(User user){
    if(user == null) return null;
    return new UserCommentDTO(user.getName(), user.getPhotoUrl());
  }

  // 👇 aquí consultamos la tabla de replies SIN tocar la entidad Comment
  protected String loadReply(String commentId) {
    return replyRepository.findByCommentId(commentId)
      .map(Reply::getReply)
      .orElse(null);
  }
}
