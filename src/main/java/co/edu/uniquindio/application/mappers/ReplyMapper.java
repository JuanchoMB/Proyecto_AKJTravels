package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.commentDTO.ReplyDTO;
import co.edu.uniquindio.application.model.Reply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReplyMapper {

    @Mappings({
            @Mapping(target = "id",        expression = "java(UUID.randomUUID().toString())"),
            @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())"),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "comment", ignore = true)
    })
    Reply toEntity(ReplyDTO replyDTO);
}