package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.entity.Token;
import com.davidmaisuradze.gymapplication.repository.TokenRepository;
import com.davidmaisuradze.gymapplication.security.GymUserDetails;
import com.davidmaisuradze.gymapplication.security.JwtService;
import com.davidmaisuradze.gymapplication.security.RegistrationTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    public RegistrationTokenDto register(GymUserDetails user, String username, String password) {
        String token = jwtService.generateToken(user);

        Token tokenEntity = Token
                .builder()
                .jwtToken(token)
                .isActive(true)
                .user(user.getUserEntity())
                .build();
        tokenRepository.save(tokenEntity);

        return new RegistrationTokenDto(
                username,
                password,
                tokenEntity
        );
    }
}
