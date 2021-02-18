package com.lagou.edu.code.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "lagou_auth_code")
public class AuthCode {

    @Id
    private Integer id;

    private String email;

    private String code;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

}
