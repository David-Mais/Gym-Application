package com.davidmaisuradze.gymapplication.security;

import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.entity.Token;
import com.davidmaisuradze.gymapplication.entity.UserEntity;
import com.davidmaisuradze.gymapplication.exception.GymException;
import com.davidmaisuradze.gymapplication.repository.TokenRepository;
import com.davidmaisuradze.gymapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final LoginAttemptService loginAttemptService;


    public JwtTokenResponse login(CredentialsDto credentialsDto) {
        String username = credentialsDto.getUsername();

        if (loginAttemptService.isLockedOut(username)) {
            throw new GymException("Account is locked. Try again later.", "403");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            credentialsDto.getPassword()
                    )
            );
            loginAttemptService.loginSucceeded(username);
        } catch (AuthenticationException e) {
            loginAttemptService.loginFailed(username);
            throw new GymException("Wrong credentials.", "401");
        }

        UserEntity userEntity = userRepository
                .findByUsername(credentialsDto.getUsername())
                .orElseThrow(() -> new GymException("User not found", "404"));

        GymUserDetails user = new GymUserDetails(userEntity);

        String token = jwtService.generateToken(user);

        List<Token> tokens = tokenRepository.findByUserId(userEntity.getId());
        if (!tokens.isEmpty()) {
            tokens.forEach(t -> {
                t.setIsActive(false);
                tokenRepository.save(t);
            });
        }
        Token tokenEntity = Token
                .builder()
                .user(userEntity)
                .jwtToken(token)
                .isActive(true)
                .build();
        tokenRepository.save(tokenEntity);

        return JwtTokenResponse.builder()
                .token(token)
                .build();
    }
}
