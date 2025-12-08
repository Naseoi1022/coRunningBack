package com.tjoeun.corunning.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjoeun.corunning.domain.EmailVerification;

public interface EmailVerificationRepository
extends JpaRepository<EmailVerification, String> {
	void deleteByExpiresAtBefore(LocalDateTime now);
}
