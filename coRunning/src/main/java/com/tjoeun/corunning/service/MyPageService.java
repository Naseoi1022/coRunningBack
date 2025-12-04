package com.tjoeun.corunning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjoeun.corunning.dto.MyPageDTO;
import com.tjoeun.corunning.repository.DipRepository;

@Service
@Transactional(readOnly = true)
public class MyPageService {
    
    @Autowired
    private DipRepository dipRepository;
    
    public List<MyPageDTO> getUserRunRecords(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return List.of();
        }
        
        List<Object[]> rawRecords = dipRepository.findCompletedRecords(userId);
        
        return rawRecords.stream()
            .map(row -> MyPageDTO.builder()
                .record(toString(row[0]))      // record
                .distance(toDouble(row[1]))    // distance
                .build())
            .toList();
    }

    private String toString(Object obj) { return obj == null ? null : obj.toString(); }
    private Double toDouble(Object obj) { 
        if (obj == null) return 0.0;
        if (obj instanceof Number) return ((Number) obj).doubleValue();
        return Double.parseDouble(obj.toString());
    }

}
