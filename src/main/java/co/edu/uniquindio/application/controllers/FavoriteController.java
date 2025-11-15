package co.edu.uniquindio.application.controllers;

import co.edu.uniquindio.application.model.Place;
import co.edu.uniquindio.application.services.CurrentUserService;
import co.edu.uniquindio.application.services.FavoriteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

  private final FavoriteService favoriteService;
  private final CurrentUserService currentUserService;

  public FavoriteController(FavoriteService favoriteService,
                            CurrentUserService currentUserService) {
    this.favoriteService = favoriteService;
    this.currentUserService = currentUserService;
  }

  /** Marca un lugar como favorito (idempotente). */
  @PostMapping("/{placeId}")
  @PreAuthorize("hasRole('USER')") // ⚠️ si tu rol de huésped es GUEST, cámbialo a hasRole('GUEST')
  public ResponseEntity<Void> add(@PathVariable String placeId) throws Exception {
    String userId = currentUserService.getCurrentUser(); // ← ID consistente con el resto del proyecto
    favoriteService.addFavorite(userId, placeId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /** Quita un lugar de favoritos (idempotente). */
  @DeleteMapping("/{placeId}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Void> remove(@PathVariable String placeId) throws Exception {
    String userId = currentUserService.getCurrentUser();
    favoriteService.removeFavorite(userId, placeId);
    return ResponseEntity.noContent().build();
  }

  /** Lista paginada de mis favoritos (devuelve Place directamente). */
  @GetMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Page<Place>> listMyFavorites(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ) throws Exception {
    String userId = currentUserService.getCurrentUser();
    Pageable pageable = PageRequest.of(page, size);
    Page<Place> result = favoriteService.listMyFavorites(userId, pageable);
    return ResponseEntity.ok(result);
  }

  /** ¿Es favorito este place para mí? */
  @GetMapping("/me/{placeId}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Boolean> isMyFavorite(@PathVariable String placeId) throws Exception {
    String userId = currentUserService.getCurrentUser();
    return ResponseEntity.ok(favoriteService.isMyFavorite(userId, placeId));
  }

  /** Conteo de favoritos por Place. */
  @GetMapping("/count/{placeId}")
  public ResponseEntity<Long> count(@PathVariable String placeId) {
    return ResponseEntity.ok(favoriteService.countFavoritesByPlace(placeId));
  }
}
