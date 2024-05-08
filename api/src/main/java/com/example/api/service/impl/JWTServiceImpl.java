package com.example.api.service.impl;


import com.example.api.entity.Account;
import com.example.api.entity.Token;
import com.example.api.exception.GlobalException;
import com.example.api.payload.response.TokenResponse;
import com.example.api.repository.TokenRepository;
import com.example.api.service.AccountService;
import com.example.api.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    @Value("${jwt.token.time.expiration}")
    private Long EXPIRATION_TIME;
    @Value("${jwt.token.secret}")
    private String SECRET;
    @Value("${jwt.token.prefix}")
    private String TOKEN_PREFIX;
    @Value("${jwt.token.header.authorization}")
    private String TOKEN_AUTHORIZATION;
    @Value("${jwt.token.REFRESH_EXPIRATION_TIME}")
    private long REFRESH_EXPIRATION_TIME;
    @Value("${jwt.token.Account_REGISTRATION_TOKEN_EXPIRATION_TIME}")
    private long Account_REGISTRATION_TOKEN_EXPIRATION_TIME;

    private final AccountService accountService;
    private final TokenRepository tokenRepository;
    @Override
    public String generateJWTToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        Claims claims = Jwts.claims().setSubject(username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    @Override
    public Authentication parseTokenToUserInformation(HttpServletRequest request) {
        String tokenValue = request.getHeader(TOKEN_AUTHORIZATION);
        if (tokenValue == null) {
            return null;
        }

        tokenValue = tokenValue.replace(TOKEN_PREFIX, "");
        if (tokenValue.isEmpty()) {
            return null;
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(tokenValue)
                    .getBody();

            String username = claims.getSubject();
            if (username == null) {
                return null;
            }

            Account account = accountService.getAccountByUsername(username);
            if (account == null) {
                return null;
            }

            return new UsernamePasswordAuthenticationToken(
                    account.getUsername(),
                    null,
                    AuthorityUtils.createAuthorityList(account.getRole().toString())
            );
        } catch (JwtException e) {
            return null;
        }
    }

    @Override
    public Token generateRefreshToken(Account account) {
        Token token = Token.builder()
                .account(account)
                .key(UUID.randomUUID().toString())
                .type(Token.Type.REFRESH_TOKEN)
                .expiredDate(new Date(new Date().getTime() + REFRESH_EXPIRATION_TIME))
                .build();


        tokenRepository.deleteTokenByAccount(account);

        return tokenRepository.save(token);
    }






    @Override
    public TokenResponse getNewToken(String refreshToken) {
        Token oldRefreshToken = tokenRepository.findByKeyAndType(refreshToken, Token.Type.REFRESH_TOKEN);
        if (oldRefreshToken == null || oldRefreshToken.getExpiredDate().before(new Date())) {
            throw new GlobalException("Invalid Refresh Token !!!");
        }
        tokenRepository.deleteTokenByAccount(oldRefreshToken.getAccount());

        Token newRefreshToken = generateRefreshToken(oldRefreshToken.getAccount());

        String newToken = generateJWTToken(oldRefreshToken.getAccount().getUsername());


        return TokenResponse.builder()
                .refreshToken(newRefreshToken.getKey())
                .token(newToken)
                .build();
    }
}
