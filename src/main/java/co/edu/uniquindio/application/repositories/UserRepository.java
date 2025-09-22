// UserRepository.java
package co.edu.uniquindio.application.repositories;

import co.edu.uniquindio.application.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;  // <-- ESTE
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
