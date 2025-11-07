package co.edu.uniquindio.application.dto.placeDTO;

import co.edu.uniquindio.application.model.enums.Services;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ListPlaceDTO{
    String city;
    LocalDateTime checkIn;
    LocalDateTime checkOut;
    Integer guest_number;
    Double minimum;
    Double maximum;
    List<Services> list;
}
