package co.edu.uniquindio.application.controllers;

import co.edu.uniquindio.application.dto.ResponseDTO;
import co.edu.uniquindio.application.dto.accommodationDTO.AccommodationStatsDTO;
import co.edu.uniquindio.application.dto.accommodationDTO.CreateAccommodationDTO;
import co.edu.uniquindio.application.dto.accommodationDTO.UpdateDTO;
import co.edu.uniquindio.application.dto.bookingDTO.BookingDTO;
import co.edu.uniquindio.application.dto.bookingDTO.SearchBookingDTO;
import co.edu.uniquindio.application.model.enums.Amenities;
import co.edu.uniquindio.application.services.AccommodationService;
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
public class AccommodationController {

    private final AccommodationService accommodationService;
    private final BookingService bookingService;

    // Crear alojamiento
    @PostMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> create(@PathVariable String id, @Valid @RequestBody CreateAccommodationDTO createAccommodationDTO) throws Exception {

        accommodationService.create(id, createAccommodationDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(false, "alojamiento creado"));
    }

    // Actualizar alojamiento
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> update(@PathVariable String id, @Valid @RequestBody UpdateDTO updateDTO) throws Exception {

        accommodationService.edit(id, updateDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(false, "alojamiento actualizado"));
    }

    // Eliminar alojamiento
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable String id) throws Exception {

        accommodationService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(false, "alojamiento eliminado"));
    }

    // Listar amenities del alojamiento
    @GetMapping("/{id}/amenities")
    public ResponseEntity<ResponseDTO<List<Amenities>>> listAmenities(@PathVariable String id) throws Exception {

        List<Amenities> list = accommodationService.listAllAmenities(id);
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
        AccommodationStatsDTO stats = accommodationService.stats(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(false, stats));
    }
}
