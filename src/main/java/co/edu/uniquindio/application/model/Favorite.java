package co.edu.uniquindio.application.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "favorites",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","place_id"}))
public class Favorite {

    @EmbeddedId
    private FavoriteId id;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("placeId")
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(nullable = false)
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();

    public Favorite() {}

    public Favorite(User user, Place place) {
        this.user = user;
        this.place = place;
        this.id = new FavoriteId(user.getId(), place.getId());
    }
}