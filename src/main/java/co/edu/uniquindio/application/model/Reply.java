package co.edu.uniquindio.application.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "replies")
public class Reply {

    @Id
    private String id;

    @Column(nullable = false, length = 200)
    private String reply;

    @OneToOne
    @JoinColumn(name = "comment_id",unique = true, nullable = false)
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // quien responde (anfitrión)

    @Column(nullable = false)
    private LocalDateTime createdAt;
}