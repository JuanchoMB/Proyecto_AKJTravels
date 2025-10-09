// src/main/java/co/edu/uniquindio/application/model/FavoriteId.java
package co.edu.uniquindio.application.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoriteId implements Serializable {
    private String userId;
    private String placeId;

    public FavoriteId() {}
    public FavoriteId(String userId, String placeId) {
        this.userId = userId;
        this.placeId = placeId;
    }

    public String getUserId() { return userId; }
    public String getPlaceId() { return placeId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setPlaceId(String placeId) { this.placeId = placeId; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteId that)) return false;
        return java.util.Objects.equals(userId, that.userId) &&
                java.util.Objects.equals(placeId, that.placeId);
    }

    @Override public int hashCode() {
        return java.util.Objects.hash(userId, placeId);
    }
}
