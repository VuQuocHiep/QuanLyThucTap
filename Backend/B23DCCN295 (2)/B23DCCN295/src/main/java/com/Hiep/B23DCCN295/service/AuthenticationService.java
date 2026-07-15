package com.Hiep.B23DCCN295.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.stereotype.Service;

import com.Hiep.B23DCCN295.dto.request.AuthenticationRequest;
import com.Hiep.B23DCCN295.dto.request.IntrospectRequest;
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
        String token = genarateToken(userEntity);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAuthentication(true);
        authenticationResponse.setToken(token);
        return authenticationResponse;
    }

    public String genarateToken(UserEntity userEntity){
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet
                    .Builder()
                    .subject(userEntity.getEmail())
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis()+60*60*1000))
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
