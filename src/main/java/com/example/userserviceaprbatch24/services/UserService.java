package com.example.userserviceaprbatch24.services;

import com.example.userserviceaprbatch24.models.Token;
import com.example.userserviceaprbatch24.models.User;
import com.example.userserviceaprbatch24.repos.TokenRepo;
import com.example.userserviceaprbatch24.repos.UserRepo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private BCryptPasswordEncoder  bCryptPasswordEncoder ;
    private UserRepo userRepo;
    private TokenRepo tokenRepo;
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepo userRepo,TokenRepo tokenRepo) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepo = userRepo;
        this.tokenRepo=tokenRepo;
    }
    public User signUp(String email,String name, String password) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        return userRepo.save(user);
    }

    public Token login(String email, String password) {
        Optional<User> user=userRepo.findByEmail(email);
        if(user.isEmpty())
        {
            throw new UsernameNotFoundException("User not found");
        }
        if(!bCryptPasswordEncoder.matches(password,user.get().getHashedPassword()))
        {
            throw new RuntimeException("Password does not matches");
        }
        Token token = generateToken(user.get());
        tokenRepo.save(token);
        return token;
    }
    public User validatetoken(String tokenvalue) {
        Optional<Token> token=tokenRepo.findByValueAndExpiryAtGreaterThan(tokenvalue,new Date());
        if(token.isEmpty())
        {
            throw new RuntimeException("Token not found");
        }
        return token.get().getUser();
    }
    private Token generateToken(User user) {
        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(10));
        LocalDate currentDate = LocalDate.now();
        LocalDate thirtyDaysLater = currentDate.plusDays(30);
        Date expiryDate=Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());
        token.setExpiryAt(expiryDate);
        return token;
    }
}
