package co.edu.uniquindio.application.repositories;

import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetCodeRepository extends JpaRepository<PasswordResteCode, Long> {
    Optional<PasswordResetCode> findByUser_Email(String email);
}
