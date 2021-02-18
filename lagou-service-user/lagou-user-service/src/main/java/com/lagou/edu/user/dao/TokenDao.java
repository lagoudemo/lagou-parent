package com.lagou.edu.user.dao;

import com.lagou.edu.user.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenDao extends JpaRepository<Token, Integer> {

    Token findByToken(String token);

}
