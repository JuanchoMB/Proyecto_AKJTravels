package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.dto.commentDTO.CommentDTO;
import co.edu.uniquindio.application.dto.commentDTO.CreateCommentDTO;
import co.edu.uniquindio.application.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.application.model.Place;
import co.edu.uniquindio.application.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final Map<String, Place> commentStore = new ConcurrentHashMap<>();

    //preguntar al profesor
    @Override
    public List<CommentDTO> listComments(String id) throws Exception {

        Place place = commentStore.get(id);
        if(place == null){
            throw new ResourceNotFoundException("No se encontró el alojamiento");
        }



        return List.of();
    }

    @Override
    public void createComment(String id, CreateCommentDTO createCommentDTO) throws Exception {

    }
}
