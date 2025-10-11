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
)public interface ListCommentsMapper {

    @Mapping(source = "user", target = "user")

    CommentDTO ToCommentDTO(Comment comment);

    // Método auxiliar para mapear User a UserCommentDTO
    default UserCommentDTO mapUser(User user){
        if(user == null) return null;
        return new UserCommentDTO(user.getName(), user.getPhotoUrl());
    }
}