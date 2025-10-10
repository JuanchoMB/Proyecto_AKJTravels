package co.edu.uniquindio.application.repositories;

import co.edu.uniquindio.application.dto.placeDTO.ListPlaceDTO;
import co.edu.uniquindio.application.model.Place;
import co.edu.uniquindio.application.model.User;
import co.edu.uniquindio.application.model.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, String> {

    @Query("select p from Place p where p.user.id = :idUser")
    Page<Place> getPlaces(String idUser, Pageable pageable);

    List<Place> findByState(State state);


    //para filtrar los alojamientos disponibles
    @Query("""
    SELECT a
    FROM Place a
    WHERE (:#{#dto.city} IS NULL OR LOWER(a.location.city) = LOWER(:#{#dto.city}))
      AND (:#{#dto.guest_number} IS NULL OR a.capacity >= :#{#dto.guest_number})
      AND a.state = co.edu.uniquindio.application.model.enums.State.ACTIVE
      AND (:#{#dto.minimum} IS NULL OR a.price >= :#{#dto.minimum})
      AND (:#{#dto.maximum} IS NULL OR a.price <= :#{#dto.maximum})
      AND (
            :#{#dto.list == null || #dto.list.isEmpty()} = true
            OR (
                SELECT COUNT(s) 
                FROM a.amenities s
                WHERE s IN (:#{#dto.list})
            ) = :#{#dto.list.size()}
          )
      AND (:#{#dto.checkIn} IS NULL OR :#{#dto.checkOut} IS NULL OR 
           NOT EXISTS (
               SELECT b FROM Booking b 
               WHERE b.place = a 
                 AND b.bookingState IN (
                     co.edu.uniquindio.application.model.enums.BookingState.PENDING, 
                     co.edu.uniquindio.application.model.enums.BookingState.COMPLETED
                 )
                 AND (
                       (b.checkIn <= :#{#dto.checkIn} AND b.checkOut > :#{#dto.checkIn})
                    OR (b.checkIn < :#{#dto.checkOut} AND b.checkOut >= :#{#dto.checkOut})
                    OR (b.checkIn >= :#{#dto.checkIn} AND b.checkOut <= :#{#dto.checkOut})
                 )
           )
      )
    """)
    Page<Place> searchPlaces(@Param("dto") ListPlaceDTO listPlaceDTO, Pageable pageable);

    @Query("SELECT a.user FROM Place a WHERE a.id = :placeId")
    Optional<User> findUserByPlaceId(@Param("placeId") String placeId);
}