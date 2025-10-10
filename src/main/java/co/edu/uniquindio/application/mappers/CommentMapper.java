package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.commentDTO.CreateCommentDTO;
import co.edu.uniquindio.application.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    @Mappings({
            @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())"),
            @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())"),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "place", ignore = true),
            @Mapping(target = "booking", ignore = true)
    })
    Comment toEntity(CreateCommentDTO createCommentDTO);

}