package com.tjoeun.corunning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.corunning.domain.RunRecord;
import com.tjoeun.corunning.repository.RunRecordRepository;

@RestController
@RequestMapping("/api/run")
public class RunRecordController {
    private final RunRecordRepository rRepository;

    public RunRecordController(RunRecordRepository rRepository) {
        this.rRepository = rRepository;
    }

    // 기록 목록 조회
    @GetMapping("/records")
    public ResponseEntity<?> getRecords(@RequestParam("userId") String userId) {
        return ResponseEntity.ok(rRepository.findByUserId(userId));
    }

    // 새 기록 생성 (DTO 제거 → 엔티티 그대로 반환)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody RunRecord record) {
        RunRecord saved = rRepository.save(record);
        return ResponseEntity.ok(saved);  // ★ 엔티티 그대로 반환
    }

    // 기록 수정 (DTO 제거 → 엔티티 그대로 반환)
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RunRecord newRecord) {
        RunRecord record = rRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setTitle(newRecord.getTitle());
        record.setDistance(newRecord.getDistance());
        record.setLocation(newRecord.getLocation());
        record.setTime(newRecord.getTime());
        record.setDate(newRecord.getDate());

        RunRecord updated = rRepository.save(record);

        return ResponseEntity.ok(updated);   // ★ 엔티티 그대로 반환
    }

    // 기록 삭제 (URL을 프론트에 맞게 수정)
    @DeleteMapping("/records/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        rRepository.deleteById(id);
        return ResponseEntity.ok("삭제 완료");
    }
}