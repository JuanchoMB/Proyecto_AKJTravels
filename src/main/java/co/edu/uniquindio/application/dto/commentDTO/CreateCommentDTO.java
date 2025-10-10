package co.edu.uniquindio.application.dto.commentDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

public record CreateCommentDTO(@Length(max = 1000) String comment,
                               @Min(1) @Max(5) int rating

) {
}