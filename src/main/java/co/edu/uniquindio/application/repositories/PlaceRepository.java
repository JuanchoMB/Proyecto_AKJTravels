// PlaceRepository.java
package co.edu.uniquindio.application.repositories;

import co.edu.uniquindio.application.model.entity.Place;
import co.edu.uniquindio.application.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;  // <-- ESTE

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Page<Place> findByDeletedFalse(Pageable pageable);
    Page<Place> findByHostAndDeletedFalse(User host, Pageable pageable);
}
