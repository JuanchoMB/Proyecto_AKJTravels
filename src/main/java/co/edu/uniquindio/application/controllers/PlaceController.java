package co.edu.uniquindio.application.controllers;

import co.edu.uniquindio.application.dto.bookingDTO.SearchBookingDTO;
import co.edu.uniquindio.application.dto.commentDTO.CommentDTO;
import co.edu.uniquindio.application.dto.commentDTO.CreateCommentDTO;
import co.edu.uniquindio.application.dto.ResponseDTO;
import co.edu.uniquindio.application.dto.placeDTO.*;
import co.edu.uniquindio.application.dto.bookingDTO.BookingDTO;
import co.edu.uniquindio.application.model.enums.Services;
import co.edu.uniquindio.application.services.BookingService;
import co.edu.uniquindio.application.services.CommentService;
import co.edu.uniquindio.application.services.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;
    private final CommentService commentService;
    private final BookingService bookingService;

    @GetMapping("/{page}")
    public ResponseEntity<ResponseDTO<List<PlaceDTO>>> read(@PathVariable int page, @Valid @RequestBody ListPlaceDTO listPlaceDTO) throws Exception {
        List<PlaceDTO> list = placeService.search(listPlaceDTO, page);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, list));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> create( @Valid @RequestBody CreatePlaceDTO createPlaceDTO) throws Exception {
        String id = getCurrentUserId();
        placeService.create(id, createPlaceDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, "alojamiento creado "));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> edit(@PathVariable String id, @Valid @RequestBody EditPlaceDTO editPlaceDTO) throws Exception {
        placeService.edit(id, editPlaceDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, "alojamiento actualizado "));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable String id) throws Exception {
        placeService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, "alojamiento eliminado "));
    }

    @GetMapping("/{id}/amenities")
    public ResponseEntity<ResponseDTO<List<Services>>> listServices(@PathVariable String id) throws Exception {
        List<Services> list = placeService.listAllAmenities(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, list));
    }

    @GetMapping("/{id}/comments/{page}")
    public ResponseEntity<ResponseDTO<List<CommentDTO>>> listComments(@PathVariable String id, @PathVariable int page) throws Exception {
        List<CommentDTO> list = commentService.listComments(id, page);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, list));
    }

    @PostMapping("/{bookingId}/comments")
    public ResponseEntity<ResponseDTO<String>> createComment(@PathVariable String bookingId, @Valid @RequestBody CreateCommentDTO createCommentDTO) throws Exception {
        String userId = getCurrentUserId();
        commentService.createComment(bookingId, userId, createCommentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, "comentario creado exitosamente"));
    }

    @GetMapping("/{id}/bookings/{page}")
    public ResponseEntity<ResponseDTO<List<BookingDTO>>> listBookings(@PathVariable String id, @PathVariable int page, @Valid @RequestBody SearchBookingDTO searchBookingDTO) throws Exception {
        List<BookingDTO> list = bookingService.listBookings(id, page, searchBookingDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, list));
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<ResponseDTO<PlaceStatsDTO>> stats(@PathVariable String id, @RequestBody StatsDateDTO statsDateDTO) throws Exception {
        PlaceStatsDTO placeStatsDTO = placeService.stats(id, statsDateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, placeStatsDTO));
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<ResponseDTO<PlaceDetailDTO>> get(@PathVariable String id) throws Exception {
        PlaceDetailDTO placeDetailDTO = placeService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, placeDetailDTO));
    }

    private String getCurrentUserId() {
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder
                        .getContext().getAuthentication().getPrincipal();
        System.out.println(user.getUsername());
        System.out.println(user.getAuthorities());
        return user.getUsername();
    }
}