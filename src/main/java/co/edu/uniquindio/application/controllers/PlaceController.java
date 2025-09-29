package co.edu.uniquindio.application.controllers;

import co.edu.uniquindio.application.dto.ResponseDTO;
import co.edu.uniquindio.application.dto.placeDTO.AccommodationStatsDTO;
import co.edu.uniquindio.application.dto.placeDTO.CreatePlaceDTO;
import co.edu.uniquindio.application.dto.bookingDTO.BookingDTO;
import co.edu.uniquindio.application.dto.bookingDTO.SearchBookingDTO;
import co.edu.uniquindio.application.dto.placeDTO.EditPlaceDTO;
import co.edu.uniquindio.application.model.enums.Amenities;
import co.edu.uniquindio.application.services.PlaceService;
import co.edu.uniquindio.application.services.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accommodations")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;
    private final BookingService bookingService;

    // Crear alojamiento
    @PostMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> create(@PathVariable String id, @Valid @RequestBody CreatePlaceDTO createPlaceDTO) throws Exception {

        placeService.create(createPlaceDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(false, "alojamiento creado"));
    }

    // Actualizar alojamiento
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> edit(@PathVariable Long id, @Valid @RequestBody EditPlaceDTO editPlaceDTO) throws Exception {

        placeService.edit(id, editPlaceDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(false, "alojamiento actualizado"));
    }

    // Eliminar alojamiento
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable String id) throws Exception {

        placeService.delete(Long.valueOf(id));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(false, "alojamiento eliminado"));
    }

    // Listar amenities del alojamiento
    @GetMapping("/{id}/amenities")
    public ResponseEntity<ResponseDTO<List<Amenities>>> listAmenities(@PathVariable String id) throws Exception {

        List<Amenities> list = placeService.listAllAmenities(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(false, list));
    }

    // Buscar reservas del alojamiento con filtros
    @PostMapping("/{id}/bookings/search")
    public ResponseEntity<ResponseDTO<List<BookingDTO>>> listBookings(@PathVariable String id, @Valid @RequestBody SearchBookingDTO searchBookingDTO) throws Exception {

        List<BookingDTO> list = bookingService.listBookings(id, searchBookingDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(false, list));
    }

    // Estadísticas del alojamiento (si tu servicio las tiene)
    @GetMapping("/{id}/stats")
    public ResponseEntity<ResponseDTO<AccommodationStatsDTO>> stats(@PathVariable String id) throws Exception {

        // Si la interfaz no tiene stats(id), comenta las 2 líneas y devuelve una respuesta fija.
        AccommodationStatsDTO stats = placeService.stats(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(false, stats));
    }
}
