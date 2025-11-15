package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.exceptions.ResourceNotFoundException;
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
  @Transactional
  public void addFavorite(String userId, String placeId)  {

    User user = userRepository.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario"));

    Place place = placeRepository.findById(placeId)
      .orElseThrow(() -> new ResourceNotFoundException("No se encontró el alojamiento"));

    // Si ya existe el favorito, no hacemos nada (idempotente)
    if (favoriteRepository.existsByUserAndPlace(user, place)) {
      return;
    }

    Favorite favorite = new Favorite();
    favorite.setUser(user);
    favorite.setPlace(place);
    // Si tu entidad tiene más campos (createdAt, id manual, etc.), setéalos aquí

    favoriteRepository.save(favorite);
  }

  @Override
  @Transactional
  public void removeFavorite(String userId, String placeId) {

    User user = userRepository.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario"));

    Place place = placeRepository.findById(placeId)
      .orElseThrow(() -> new ResourceNotFoundException("No se encontró el alojamiento"));

    favoriteRepository.findByUserAndPlace(user, place)
      .ifPresent(favoriteRepository::delete);
    // Si no existe, simplemente no pasa nada (también idempotente)
  }

  @Override
  @Transactional(readOnly = true)
  public boolean isMyFavorite(String userId, String placeId)  {

    User user = userRepository.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario"));

    Place place = placeRepository.findById(placeId)
      .orElseThrow(() -> new ResourceNotFoundException("No se encontró el alojamiento"));

    return favoriteRepository.existsByUserAndPlace(user, place);
  }

  @Override
  @Transactional(readOnly = true)
  public long countFavoritesByPlace(String placeId)  {

    Place place = placeRepository.findById(placeId)
      .orElseThrow(() -> new ResourceNotFoundException("No se encontró el alojamiento"));

    return favoriteRepository.countByPlace(place);
  }

  @Override
  public Page<Place> listMyFavorites(String userId, Pageable pageable) {

    User user = userRepository.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario"));

    Page<Favorite> favPage = favoriteRepository.findByUser(user, pageable);

    // devolvemos solo los Place asociados a cada Favorite
    return favPage.map(Favorite::getPlace);
  }
}
