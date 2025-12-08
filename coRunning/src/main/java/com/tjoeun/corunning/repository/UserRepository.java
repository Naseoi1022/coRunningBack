package com.tjoeun.corunning.repository;

import com.tjoeun.corunning.domain.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {
	
	 // userId 중복 여부
    boolean existsByUserId(String userId);

    // userName 중복 여부
    boolean existsByUserName(String userName);
}


