package com.Hiep.B23DCCN295.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Hiep.B23DCCN295.dto.request.AuthenticationRequest;
import com.Hiep.B23DCCN295.dto.request.IntrospectRequest;
import com.Hiep.B23DCCN295.dto.request.RefreshTokenRequest;
import com.Hiep.B23DCCN295.dto.response.AuthenticationResponse;
import com.Hiep.B23DCCN295.dto.response.IntrospectResponse;
import com.Hiep.B23DCCN295.entity.UserEntity;
import com.Hiep.B23DCCN295.repository.AuthenticationRepository;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class AuthenticationService {
    
    protected static final String SIGNER_KEY = "kF3mW8zQ2YpN7aBcX5tLvR9HsJ4dNeUgPi6rTx0KwEfVqZmCn81oIyAuDbGhLsR0";

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @Autowired
    private AuthenticationRepository authenticationRepository;

    public AuthenticationResponse login(AuthenticationRequest request){

        UserEntity userEntity = authenticationRepository.findByEmail(request.getEmail());
        boolean authentication = encoder.matches(request.getPassword(), userEntity.getPassword());
        if(!authentication){
            throw new RuntimeException("Invalid id or name");
        }
        String token = generateToken(userEntity, 60*60*1000);
        String refreshToken = generateToken(userEntity, 7L * 24 * 60 * 60 * 1000);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAuthentication(true);
        authenticationResponse.setToken(token);
        authenticationResponse.setRefreshToken(refreshToken);
        return authenticationResponse;
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request){
        try {
            SignedJWT signedJWT = SignedJWT.parse(request.getRefreshToken());
            boolean verified = signedJWT.verify(new MACVerifier(SIGNER_KEY.getBytes()));
            Date expireDate = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (!verified || expireDate.before(new Date())) {
                throw new RuntimeException("Refresh token is invalid or expired");
            }

            String email = signedJWT.getJWTClaimsSet().getSubject();
            UserEntity userEntity = authenticationRepository.findByEmail(email);
            if (userEntity == null) {
                throw new RuntimeException("User not found");
            }

            String newAccessToken = generateToken(userEntity, 60*60*1000);
            AuthenticationResponse response = new AuthenticationResponse();
            response.setAuthentication(true);
            response.setToken(newAccessToken);
            response.setRefreshToken(request.getRefreshToken());
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Refresh token failed: " + e.getMessage(), e);
        }
    }

    public String generateToken(UserEntity userEntity, long expirationTimeMillis){
        try {
            Set<String> authorities = new HashSet<>();

            userEntity.getRole().forEach(role -> {
                authorities.add("ROLE_" + role.getName());

                role.getPermission().forEach(permission ->
                    authorities.add(permission.getName())
                );
            });
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet
                    .Builder()
                    .subject(userEntity.getEmail())
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis()+expirationTimeMillis))
                    .claim("scope", authorities)
                    .build();
            Payload payload = new Payload(jwtClaimsSet.toJSONObject());
            JWSObject jwsObject = new JWSObject(header, payload);
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize(); 
        } catch (Exception e) {throw new RuntimeException(
                "Token generation failed: " + e.getMessage(),
                e
            );
        }
    }

    public IntrospectResponse introspect(IntrospectRequest request){
        try {
            String token = request.getToken();
            SignedJWT signedJWT = SignedJWT.parse(token);
            boolean verified = signedJWT.verify(new MACVerifier(SIGNER_KEY.getBytes()));
            Date expireDate = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean isValid = verified && expireDate.after(new Date());
            IntrospectResponse introspectResponse = new IntrospectResponse();
            introspectResponse.setIsvalid(isValid);
            return introspectResponse;
        } catch (Exception e) {
            throw new RuntimeException(
                "Token generation failed: " + e.getMessage(),
                e
            );
        }
    }
}
