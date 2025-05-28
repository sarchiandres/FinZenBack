package com.FinZenBack.ws.FinZenBack.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys; // Asegúrate de que esta importación esté presente
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // Se recomienda que esta clave sea lo suficientemente larga y segura.
    // Para entornos de producción, considera usar una variable de entorno o un servicio de secretos.
    @Value("${app.jwtSecret:K7gH8mP0vC9dT5rX2bQ1zU6nM4pS3eL0fW8yD2tR5aV7bG1kQ9hX6cJ4mN5yE3pB}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs:3600000}") // 1 hora en milisegundos
    private int jwtExpirationMs;

    /**
     * Genera un token JWT para el usuario autenticado.
     *
     * @param authentication Objeto de autenticación que contiene los detalles del usuario.
     * @return El token JWT generado.
     */
    public String generateJwtToken(Authentication authentication) {
        // Obtiene los detalles del usuario principal de la autenticación
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        // Construye el token JWT
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername()) // Establece el nombre de usuario como el "subject"
                .setIssuedAt(new Date()) // Establece la fecha de emisión del token
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Establece la fecha de expiración
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Firma el token con la clave y el algoritmo HS256
                .compact(); // Compacta el JWT en una cadena URL-safe
    }

    /**
     * Obtiene la clave de firma HMAC SHA-256 a partir del secreto JWT.
     *
     * @return La clave de firma.
     */
    private Key getSigningKey() {
        // Convierte el secreto JWT (cadena) a bytes y genera una clave HMAC
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Extrae el nombre de usuario (subject) de un token JWT.
     *
     * @param token El token JWT del que se extraerá el nombre de usuario.
     * @return El nombre de usuario.
     */
    public String getNombreUsuarioFromJwtToken(String token) {
        // Parsea el token JWT y obtiene el "subject" (nombre de usuario)
        return Jwts.parserBuilder() // Inicia el constructor del parser
                .setSigningKey(getSigningKey()) // Establece la clave de firma para la validación
                .build() // Construye el parser
                .parseClaimsJws(token) // Parsea el token y valida la firma
                .getBody() // Obtiene el cuerpo del token (claims)
                .getSubject(); // Obtiene el "subject" (nombre de usuario)
    }

    /**
     * Valida un token JWT.
     *
     * @param authToken El token JWT a validar.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            // Intenta parsear y validar el token JWT
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(authToken);
            return true; // El token es válido
        } catch (MalformedJwtException e) {
            logger.error("Token JWT inválido: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token JWT expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token JWT no soportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("La cadena claims JWT está vacía: {}", e.getMessage());
        } catch (SignatureException e) {
            logger.error("Fallo en la firma del token JWT: {}", e.getMessage());
        }
        return false; // El token no es válido debido a alguna de las excepciones anteriores
    }
}