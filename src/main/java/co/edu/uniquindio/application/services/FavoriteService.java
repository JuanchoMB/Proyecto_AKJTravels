package co.edu.uniquindio.application.services;

import co.edu.uniquindio.application.model.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteService {

    /** Idempotente: si ya es favorito, no duplica. */
    void addFavorite(String userEmail, String placeId);

    /** Idempotente: si no existe, no falla. */
    void removeFavorite(String userEmail, String placeId);

    /** Lista paginada de lugares que el usuario marcó como favoritos. */
    Page<Place> listMyFavorites(String userEmail, Pageable pageable);

    /** Cantidad de usuarios que marcaron el lugar como favorito. */
    long countFavoritesByPlace(String placeId);

    /** ¿El lugar es favorito del usuario autenticado? */
    boolean isMyFavorite(String userEmail, String placeId);
}
