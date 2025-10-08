package co.edu.uniquindio.application.services;

import co.edu.uniquindio.application.dto.commentDTO.ReplyDTO;
import co.edu.uniquindio.application.exceptions.ValueConflictException;

public interface ReplyService {

    //idUser es temporal porque se sacará del token
    void create(String idUser, String commentId, ReplyDTO replyDTO) throws ValueConflictException;
}
