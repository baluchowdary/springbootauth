package com.kollu.angularone;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;

public class JwtKeyGen {


	/*
	 * public static void main(String[] args) { // Generates a secure key
	 * specifically for HS256 SecretKey key =
	 * Keys.secretKeyFor(SignatureAlgorithm.HS256); String encodedKey =
	 * Base64.getEncoder().encodeToString(key.getEncoded());
	 * 
	 * System.out.println("Secure JWT Key: " + encodedKey); }
	 */
	

	    public static void main(String[] args) {
	        // Generates a secure-random key suitable for HS384
	        SecretKey key = Jwts.SIG.HS384.key().build();
	        
	        // Encode it to Base64 so you can store it in application.properties
	        String base64Key = Encoders.BASE64.encode(key.getEncoded());
	        System.out.println("Your HS384 Key: " + base64Key);
	    }
	}
