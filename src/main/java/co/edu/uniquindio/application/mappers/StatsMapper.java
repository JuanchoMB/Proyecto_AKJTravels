package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.placeDTO.PlaceStatsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)public interface StatsMapper {

    PlaceStatsDTO toPlaceStatsDTO(double averageRating,
                                                  long totalComments,
                                                  long totalReservations,
                                                  double occupancyRate,
                                                  int cancellations,
                                                  double totalRevenue);
}