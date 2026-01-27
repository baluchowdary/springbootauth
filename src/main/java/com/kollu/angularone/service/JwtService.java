package com.kollu.angularone.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	// public static final String secret_key =
	// "SvLPsfEbmQS1/vglqT995D8hu0vGTSfkrCycddruU5Y=";
	@Value("${jwt.secret}")
	private String secret;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class);
    }
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// 1
	/*
	 * public String generateToken(String userName) { Map<String, Object> claims =
	 * new HashMap<>(); return createToken(claims, userName); }
	 */
	
	public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        
        // Convert authorities to a list of strings for the JWT
        List<String> roles = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .toList();
        
        claims.put("roles", roles); 
        return createToken(claims, userDetails.getUsername());
    }

	// 2
	/*
	 * private String createToken(Map<String, Object> claims, String userName) {
	 * return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new
	 * Date(System.currentTimeMillis())) .setExpiration(new
	 * Date(System.currentTimeMillis() + 1000 * 60 * 30)) .signWith(getSignKey(),
	 * SignatureAlgorithm.HS256).compact();
	 * 
	 * }
	 */
	
	private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

	// 3
//Decoding encrypted secret_key
	/*
	 * private Key getSignKey() { System.out.println(secret); byte[] keyBytes =
	 * Decoders.BASE64.decode(secret); System.out.println(keyBytes); return
	 * Keys.hmacShaKeyFor(keyBytes); }
	 */
	private Key getSignKey() {
        // If this prints null or ${jwt.secret}, check your application.properties!
        if (secret == null || secret.contains("{")) {
            throw new RuntimeException("JWT Secret not properly injected!");
        }
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
	

}
