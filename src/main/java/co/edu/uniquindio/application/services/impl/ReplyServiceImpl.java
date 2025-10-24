package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.dto.commentDTO.ReplyDTO;
import co.edu.uniquindio.application.exceptions.ForbiddenException;
import co.edu.uniquindio.application.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.application.exceptions.ValueConflictException;
import co.edu.uniquindio.application.mappers.ReplyMapper;
import co.edu.uniquindio.application.model.Comment;
import co.edu.uniquindio.application.model.Reply;
import co.edu.uniquindio.application.repositories.CommentRepository;
import co.edu.uniquindio.application.repositories.ReplyRepository;
import co.edu.uniquindio.application.services.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final ReplyMapper replyMapper;
    private final CommentRepository commentRepository;

    @Override
    public void create(String idUser, String commentId, ReplyDTO replyDTO) {

        Optional<Comment> auxComment = commentRepository.findById(commentId);
        if (auxComment.isEmpty()) {
            throw new ResourceNotFoundException("No se pudo encontrar el comentario");
        }

        Optional<Reply> auxReply = replyRepository.findByCommentId(commentId);
        if (auxReply.isPresent()) {
            throw new ValueConflictException("no puedes responder dos veces a el mismo comentario");
        }


        if(auxComment.get().getPlace().getUser().getId().equals(idUser)) {
            Reply reply = replyMapper.toEntity(replyDTO);
            reply.setComment(auxComment.get());
            replyRepository.save(reply);
        }
        else{
            throw new ForbiddenException("no puedes responder a un alojamiento que no es tuyo");
        }
    }
}