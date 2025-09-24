package co.edu.uniquindio.application.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter @NoArgsConstructor
public class Location {
    private String country;
    private String department;
    private String city;
    private String neighborhood;
    private String street;
    private String postalCode;
}
