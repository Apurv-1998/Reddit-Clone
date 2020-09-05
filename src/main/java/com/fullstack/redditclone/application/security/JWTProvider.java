package com.fullstack.redditclone.application.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.fullstack.redditclone.application.exception.SpringRedditException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import static java.util.Date.from;
import static io.jsonwebtoken.Jwts.parser;

@Service
public class JWTProvider {
	
	private KeyStore keyStore;
	
	@Value("${jwt.expiration.time}") // getting the value from application.properties
	
	private Long jwtExpirationInMillis;
	
	
	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream input = getClass().getResourceAsStream("/springblog.jks");
			keyStore.load(input, "secret".toCharArray());
			
		}catch(KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
			
			throw new SpringRedditException("Exception Occured While Loading KeyStore", e);
			
		}
	}
	
	
	//Generating Token
	
	public String generateToken(Authentication authentication) {
		
		User principal = (User)authentication.getPrincipal();
		
		return Jwts.builder()
				   .setSubject(principal.getUsername())
				   .signWith(getPrivateKey())
				   .compact();
	}
	
	
	String generateTokenWithUserName(String userName) {
		
		return Jwts.builder()
				   .setSubject(userName)
				   .setIssuedAt(from(Instant.now()))
				   .signWith(getPrivateKey())
				   .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
				   .compact();
				   
	}
	
	private PrivateKey getPrivateKey() {
		
		try {
			
			return (PrivateKey)keyStore.getKey("springblog", "secret".toCharArray());
			
		}catch(KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
			
			throw new SpringRedditException("Exception occured while retrieving public key from keystore", e);
		}
	}
	
	
	public boolean validateToken(String jwt) {
		
		parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
		
		return true;
	}
	
	
	private PublicKey getPublicKey() {
		try {
			
			return keyStore.getCertificate("springblog").getPublicKey();
			
		}catch(KeyStoreException e) {
			
			throw new SpringRedditException("Exception occured while " +
                    "retrieving public key from keystore", e);
		}
	}
	
	public String getUserNameFromJWT(String token) {
		Claims claims = parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody();
		
		return claims.getSubject();
	}
	
	public Long getExpirationTimeInMillis() {
		return jwtExpirationInMillis;
	}

}
