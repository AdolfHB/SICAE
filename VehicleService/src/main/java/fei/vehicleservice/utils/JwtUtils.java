/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.vehicleservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;

/**
 *
 * @author clemente
 */
public class JwtUtils {
    
    //Obtiene la clave secreta utilizada para firmar y verificar los JWT.
    private static SecretKey getKey() {
        String secret = AppProperties.get("jwt.secret");
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        ); // Genera una clave secreta a partir del valor configurado en jwt.secret
        
    }
    
    //Valida un token JWT verificando su firma e integridad.
    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getKey())// Configura la clave para verificar la firma
                    .build()
                    .parseSignedClaims(token); // Intenta analizar y validar el token
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //Extrae los claims (datos contenidos en el payload) de un JWT.
    public static Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())//Verifica la firma del token
                .build()
                .parseSignedClaims(token)
                .getPayload();// Devuelve el contenido (claims) del token
    }
    
    public static Integer getIdUsuarioFromToken(String token) {
    try {
        Claims claims = getClaims(token);
        Object idUsuario = claims.get("idUsuario");
        if (idUsuario == null) {
            return null;
        }
        if (idUsuario instanceof Integer) {
            return (Integer) idUsuario;
        }
        if (idUsuario instanceof Long) {
            return ((Long) idUsuario).intValue();
        }
        if (idUsuario instanceof Number) {
            return ((Number) idUsuario).intValue();
        }
        return Integer.parseInt(idUsuario.toString());
        } catch (Exception e) {
            System.out.println("Error al obtener idUsuario del token: " + e.getMessage());
            return null;
        }
    }
}
