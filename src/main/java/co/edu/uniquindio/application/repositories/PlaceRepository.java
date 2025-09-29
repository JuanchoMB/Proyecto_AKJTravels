package co.edu.uniquindio.application.repositories;

import co.edu.uniquindio.application.model.Place;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query("select a from Place a where a.host.id = :idUser")
    List<Place> getPlaces(String idUser, Pageable pageable);
}
