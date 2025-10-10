package co.edu.uniquindio.application.repositories;

import co.edu.uniquindio.application.model.Favorite;
import co.edu.uniquindio.application.model.Place;
import co.edu.uniquindio.application.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUserAndPlace(User user, Place place);

    Optional<Favorite> findByUserAndPlace(User user, Place place);

    // Para listar favoritos de un usuario con paginación
    Page<Favorite> findByUser(User user, Pageable pageable);

    // Para contar favoritos de un lugar
    long countByPlace(Place place);
}
