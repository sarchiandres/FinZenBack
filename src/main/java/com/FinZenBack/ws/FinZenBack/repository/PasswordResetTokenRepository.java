package com.FinZenBack.ws.FinZenBack.repository;


import com.FinZenBack.ws.FinZenBack.models.Entities.PasswordResetToken;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUsuario(Usuario usuario);
    void deleteByUsuario(Usuario usuario);
}