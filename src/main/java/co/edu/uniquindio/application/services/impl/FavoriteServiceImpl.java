package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.model.Favorite;
import co.edu.uniquindio.application.model.FavoriteId;
import co.edu.uniquindio.application.model.Place;
import co.edu.uniquindio.application.model.User;
import co.edu.uniquindio.application.repositories.FavoriteRepository;
import co.edu.uniquindio.application.repositories.PlaceRepository;
import co.edu.uniquindio.application.repositories.UserRepository;
import co.edu.uniquindio.application.services.FavoriteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository,
                               UserRepository userRepository,
                               PlaceRepository placeRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
    }

    @Override
    public void addFavorite(String userEmail, String placeId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + userEmail));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Lugar no encontrado: " + placeId));

        FavoriteId id = new FavoriteId(user.getId(), place.getId());
        if (favoriteRepository.existsById(id)) {
            return; // idempotente
        }
        favoriteRepository.save(new Favorite(user, place));
    }

    @Override
    public void removeFavorite(String userEmail, String placeId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + userEmail));

        // idempotente
        favoriteRepository.deleteByUser_IdAndPlace_Id(user.getId(), placeId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Place> listMyFavorites(String userEmail, Pageable pageable) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + userEmail));

        // Este método devuelve directamente los Place favoritos ordenados por fecha de marcado (desc).
        // Asegúrate de tenerlo en FavoriteRepository (te lo indico abajo).
        return favoriteRepository.findByUser_IdOrderByCreatedAtDesc(user.getId(), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public long countFavoritesByPlace(String placeId) {
        return favoriteRepository.countByPlace_Id(placeId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isMyFavorite(String userEmail, String placeId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + userEmail));
        return favoriteRepository.existsByUser_IdAndPlace_Id(user.getId(), placeId);
    }
}
