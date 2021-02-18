package com.lagou.edu.user.dao;


import com.lagou.edu.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {

    /**
     * 根据email查询用户
     *
     * @param email
     * @return
     */
    User findByEmail(String email);
}
