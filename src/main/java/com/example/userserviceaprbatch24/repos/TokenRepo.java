package com.example.userserviceaprbatch24.repos;

import com.example.userserviceaprbatch24.models.Token;
import com.example.userserviceaprbatch24.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndExpiryAtGreaterThan(String value, Date date);
}
