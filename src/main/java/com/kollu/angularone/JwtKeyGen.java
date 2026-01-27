package com.kollu.angularone;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.crypto.SecretKey;

public class JwtKeyGen {


	    public static void main(String[] args) {
	        // Generates a secure key specifically for HS256
	        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
	        
	        System.out.println("Secure JWT Key: " + encodedKey);
	    }
	}
