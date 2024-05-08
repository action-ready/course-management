package com.example.api.repository;

import com.example.api.entity.Account;
import com.example.api.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    void deleteByTypeAndAccount(Token.Type type, Account account);

    void deleteTokenByAccount(Account account);

    Token findByKeyAndType(String refreshToken, Token.Type type);
}
