package com.tjoeun.corunning.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjoeun.corunning.repository.EmailVerificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailVerificationCleaner {

    private final EmailVerificationRepository repo;

	@Transactional
    @Scheduled(fixedRate = 60000)
    public void cleanExpired() {
        repo.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}
