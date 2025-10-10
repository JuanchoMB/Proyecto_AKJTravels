package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.commentDTO.CommentDTO;
import co.edu.uniquindio.application.dto.userDTO.UserCommentDTO;
import co.edu.uniquindio.application.model.Comment;
import co.edu.uniquindio.application.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ListCommentsMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "comment", source = "comment"),
            @Mapping(target = "rating", source = "rating"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "authorId", source = "user.id"),
            @Mapping(target = "authorName", source = "user.name"),
            @Mapping(target = "authorPhoto", source = "user.photoUrl")
    })
    CommentDTO ToCommentDTO(Comment comment);

    // Método auxiliar para mapear User a UserCommentDTO
    default UserCommentDTO mapUser(User user){
        if(user == null) return null;
        return new UserCommentDTO(user.getName(), user.getPhotoUrl());
    }
}