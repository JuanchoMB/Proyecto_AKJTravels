package co.edu.uniquindio.application.model;

import co.edu.uniquindio.application.model.enums.BookingState;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    private String id;

    @Column(nullable = false)
    private LocalDateTime checkIn;

    @Column(nullable = false)
    private LocalDateTime checkOut;

    @Column(nullable = false)
    private int guest_number;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingState bookingState;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}