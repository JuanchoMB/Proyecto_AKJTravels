package co.edu.uniquindio.application.dto.placeDTO;

import co.edu.uniquindio.application.model.enums.Services;
import co.edu.uniquindio.application.model.enums.PlaceType;
import jakarta.validation.constraints.*;
import java.util.List;

public record EditPlaceDTO(
  @Size(min = 5, max = 120, message = "El título debe tener entre 5 y 120 caracteres")
  String title,

  @Size(min = 20, max = 2000, message = "La descripción debe tener entre 20 y 2000 caracteres")
  String description,

  @Positive @Max(60) Integer capacity,

  @NotNull @Positive Double price,

  // Ubicación SIN @NotBlank/@NotEmpty (pueden venir null y no se actualiza)
  String country,
  String department,
  String city,
  String neighborhood,
  String street,
  String postalCode,

  // Otros
  List<String> pics_url,                 // si no editas imágenes aquí, puede ser null
  @NotEmpty List<Services> amenities,
  @NotNull PlaceType placeType
) {}
