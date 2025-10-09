package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.commentDTO.CreateCommentDTO;
import co.edu.uniquindio.application.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    // Estas referencias se setean en el servicio usando los repos:
    @Mapping(target = "place", ignore = true)
    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "user", ignore = true)
    Comment toEntity(CreateCommentDTO dto);
}
