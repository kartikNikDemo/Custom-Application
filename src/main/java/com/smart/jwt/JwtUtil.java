package com.smart.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
@Component
public class JwtUtil {
	private static final long serialVersionUID = 234234523523L;
	  @Value("${jwt.expiration}")
	 public  long JWT_TOKEN_VALIDITY ;

	    @Value("${jwt.Secret}")
	    private String secretKey;
	    public static SecretKey generateHS512Key() {
	        // Generate a secure key for HS512
	        byte[] keyBytes = new byte[64]; // Adjust the size as needed
	        // You can use a secure random generator to fill the keyBytes array

	        Key key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());

	        return (SecretKey) key;
	    }
	    SecretKey hs512Key = generateHS512Key();
        String base64EncodedKey = Base64.getEncoder().encodeToString(hs512Key.getEncoded());
	    //retrieve username from jwt token
	    public String getUsernameFromToken(String token) {
	        return getClaimFromToken(token, Claims::getSubject);
	    }

	    //retrieve expiration date from jwt token
	    public Date getExpirationDateFromToken(String token) {
	        return getClaimFromToken(token, Claims::getExpiration);
	    }


	    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = getAllClaimsFromToken(token);
	        return claimsResolver.apply(claims);
	    }


	    //for retrieving any information from token we will need the secret key
	    private Claims getAllClaimsFromToken(String token) {
	    	
	        return Jwts.parser().setSigningKey(base64EncodedKey).setAllowedClockSkewSeconds(60).parseClaimsJws(token).getBody();
	    }


	    //check if the token has expired
	    private Boolean isTokenExpired(String token) {
	        final Date expiration = getExpirationDateFromToken(token);
	        return expiration.before(new Date());
	    }


	    //generate token for user
	    public String generateToken(String userId, UserDetails userDetails) {
	        Map<String, Object> claims = new HashMap<>();
	        claims.put("userId", userId);
	        return doGenerateToken(claims, userDetails.getUsername());
	    }


	    //while creating the token -
	    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	    //2. Sign the JWT using the HS512 algorithm and secret key.
	    private String doGenerateToken(Map<String, Object> claims, String subject) {
	    	//Date now = new Date();
	        //Date validity = new Date(now.getTime() + JWT_TOKEN_VALIDITY);
	    	//System.err.println("VAl: "+JWT_TOKEN_VALIDITY);
	        Date expirationDate = new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY); // Set expiration time to 30 minutes from now
	        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(expirationDate)
	                .signWith(SignatureAlgorithm.HS512,  base64EncodedKey).compact();
	    }


	    //validate token
	    public Boolean validateToken(String token, UserDetails userDetails) {
	        final String username = getUsernameFromToken(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
}
