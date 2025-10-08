package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.placeDTO.PlaceStatsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StatsMapper {

    PlaceStatsDTO toPlaceStatsDTO(double averageRating,
                                  long totalComments,
                                  long totalReservations,
                                  double occupancyRate,
                                  int cancellations,
                                  double totalRevenue
    );
}
