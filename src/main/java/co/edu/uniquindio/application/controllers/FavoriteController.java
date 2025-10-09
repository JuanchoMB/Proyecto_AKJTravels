package co.edu.uniquindio.application.controllers;

import co.edu.uniquindio.application.model.Place;
import co.edu.uniquindio.application.services.FavoriteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    /** Marca un lugar como favorito (idempotente). */
    @PostMapping("/{placeId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> add(Authentication auth, @PathVariable String placeId) {
        favoriteService.addFavorite(auth.getName(), placeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /** Quita un lugar de favoritos (idempotente). */
    @DeleteMapping("/{placeId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> remove(Authentication auth, @PathVariable String placeId) {
        favoriteService.removeFavorite(auth.getName(), placeId);
        return ResponseEntity.noContent().build();
    }

    /** Lista paginada de mis favoritos (devuelve Place directamente). */
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<Place>> listMyFavorites(
            Authentication auth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Place> result = favoriteService.listMyFavorites(auth.getName(), pageable);
        return ResponseEntity.ok(result);
    }

    /** ¿Es favorito este place para mí? */
    @GetMapping("/me/{placeId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> isMyFavorite(Authentication auth, @PathVariable String placeId) {
        return ResponseEntity.ok(favoriteService.isMyFavorite(auth.getName(), placeId));
    }

    /** Conteo de favoritos por Place (puedes dejarlo público o restringirlo si quieres). */
    @GetMapping("/count/{placeId}")
    public ResponseEntity<Long> count(@PathVariable String placeId) {
        return ResponseEntity.ok(favoriteService.countFavoritesByPlace(placeId));
    }
}
