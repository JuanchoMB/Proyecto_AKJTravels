package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.model.Favorite;
import co.edu.uniquindio.application.model.Place;
import co.edu.uniquindio.application.model.User;
import co.edu.uniquindio.application.repositories.FavoriteRepository;
import co.edu.uniquindio.application.repositories.PlaceRepository;
import co.edu.uniquindio.application.repositories.UserRepository;
import co.edu.uniquindio.application.services.FavoriteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    @Override
    public void addFavorite(String userId, String placeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Lugar no encontrado"));

        if (favoriteRepository.existsByUserAndPlace(user, place)) {
            throw new IllegalStateException("Ya está en favoritos");
        }

        favoriteRepository.save(
                Favorite.builder().user(user).place(place).build()
        );
    }

    @Override
    public void removeFavorite(String userId, String placeId) {
        User user = userRepository.getReferenceById(userId);
        Place place = placeRepository.getReferenceById(placeId);

        favoriteRepository.findByUserAndPlace(user, place)
                .ifPresent(favoriteRepository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Place> listMyFavorites(String userId, Pageable pageable) {
        User user = userRepository.getReferenceById(userId);
        return favoriteRepository.findByUser(user, pageable)
                .map(Favorite::getPlace);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isMyFavorite(String userId, String placeId) {
        User user = userRepository.getReferenceById(userId);
        Place place = placeRepository.getReferenceById(placeId);
        return favoriteRepository.existsByUserAndPlace(user, place);
    }

    @Override
    @Transactional(readOnly = true)
    public long countFavoritesByPlace(String placeId) {
        Place place = placeRepository.getReferenceById(placeId);
        return favoriteRepository.countByPlace(place);
    }
}
