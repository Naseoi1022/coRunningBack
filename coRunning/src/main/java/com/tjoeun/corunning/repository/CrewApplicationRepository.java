package com.tjoeun.corunning.repository;

import com.tjoeun.corunning.domain.CrewApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrewApplicationRepository extends JpaRepository<CrewApplication, Long> {

    // 중복 신청 확인
    boolean existsByCrewBoardIdAndApplicantId(Long crewBoardId, String applicantId);

    //신청한 모든 사람 목록
    List<CrewApplication> findByCrewBoardId(Long crewBoardId);
}
