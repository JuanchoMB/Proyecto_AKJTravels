package co.edu.uniquindio.application.repositories;

import co.edu.uniquindio.application.model.Favorite;
import co.edu.uniquindio.application.model.FavoriteId;
import co.edu.uniquindio.application.model.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {

    boolean existsById(FavoriteId id);

    boolean existsByUser_IdAndPlace_Id(String userId, String placeId);

    void deleteByUser_IdAndPlace_Id(String userId, String placeId);

    long countByPlace_Id(String placeId);

    Page<Favorite> findByUser_Id(String userId, Pageable pageable);


    Page<Place> findByUser_IdOrderByCreatedAtDesc(String userId, Pageable pageable);
}
