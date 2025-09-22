package co.edu.uniquindio.application.repositories;

import co.edu.uniquindio.application.model.entity.Booking;
import co.edu.uniquindio.application.model.entity.Place;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByGuest(User guest);
    List<Booking> findByPlace(Place place);
    boolean existsByPlaceAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
            Place place, LocalDate end, LocalDate start);
}
