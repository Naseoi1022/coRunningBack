package com.tjoeun.corunning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjoeun.corunning.domain.RunRecord;

public interface RunRecordRepository extends JpaRepository<RunRecord, Long> {
	List<RunRecord> findByUserId(String userId);
}
