package co.edu.uniquindio.application.model;

import co.edu.uniquindio.application.model.enums.State;
import co.edu.uniquindio.application.model.enums.PlaceType;
import co.edu.uniquindio.application.model.enums.Amenities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Place {

    @Id
    private String id;

    @Column(nullable = false)
    @Embedded
    private Location location;

    @Column(nullable = false)
    private double price;

    @ElementCollection
    @CollectionTable(name = "pics_urls", // tabla intermedia
                    joinColumns = @JoinColumn(name = "accommodation_id") // FK a Accommodation
    )
    @Column(name = "pics_url")
    private List<String> pics_url;

    @Lob
    @Column(nullable = false)
    private String description;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "accommodation_id"))
    @Column(name = "amenitie")
    private List<Amenities> amenities;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(nullable = false)
    private int totalRatings;


    @Column(nullable = false)
    private double averageRatings;

    @Column(nullable = false)
    private PlaceType placeType;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "place")
    private List<Comment> comments;
}