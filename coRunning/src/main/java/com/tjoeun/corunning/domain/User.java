package com.tjoeun.corunning.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "USERS")  // ← 오라클 실제 테이블 이름
public class User {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_PW")
    private String userPw;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_ADDRESS")
    private String userAddress;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "BIRTH_DATE")
    private String birthDate;

    @Column(name = "HIRE_DATE")
    private String hireDate;

}

