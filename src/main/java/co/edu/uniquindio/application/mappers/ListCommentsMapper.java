package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.commentDTO.CommentDTO;
import co.edu.uniquindio.application.dto.userDTO.UserCommentDTO;
import co.edu.uniquindio.application.model.Comment;
import co.edu.uniquindio.application.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ListCommentsMapper {

    // createdAt -> commentDate; user lo convertimos con el método default
    @Mapping(source = "createdAt", target = "commentDate")
    @Mapping(source = "user", target = "user")
    CommentDTO ToCommentDTO(Comment comment);

    // Auxiliary: User -> UserCommentDTO
    default UserCommentDTO mapUser(User user){
        if (user == null) return null;
        return new UserCommentDTO(user.getName(), user.getPhotoUrl());
    }
}
