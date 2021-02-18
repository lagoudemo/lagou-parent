package com.lagou.edu.code.dao;


import com.lagou.edu.code.entity.AuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthCodeDao extends JpaRepository<AuthCode, Integer> {

    /**
     * 根据邮箱查询最近一条验证码记录
     *
     * @param email 邮箱
     * @return
     */
    AuthCode findFirstByEmailOrderByIdDesc(String email);

}
