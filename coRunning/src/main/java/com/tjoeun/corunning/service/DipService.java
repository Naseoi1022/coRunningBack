package com.tjoeun.corunning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tjoeun.corunning.domain.RouteDip;
import com.tjoeun.corunning.dto.RouteDipDTO;
import com.tjoeun.corunning.repository.DipRepository;

@Service
public class DipService {
    private final DipRepository dipRepository;

    public DipService(DipRepository dipRepository) {
        this.dipRepository = dipRepository;
    }

    // userId + routeId로 중복 검사 후 찜목록 추가
    public boolean addDip(String userId, Long routeId) {
        boolean exists = dipRepository.existsByUserIdAndRouteId(userId, routeId);
        if (exists) {
            return false;
        }

        RouteDip dip = new RouteDip();
        dip.setUserId(userId);
        dip.setRouteId(routeId);
        dipRepository.save(dip);
        
        return true;
    }

    public ArrayList<RouteDipDTO> getDipList(String userId) {
        List<RouteDip> dips = dipRepository.findByUserId(userId);
        ArrayList<RouteDipDTO> dtoList = new ArrayList<>();
        for (RouteDip dip : dips) {
            dtoList.add(new RouteDipDTO(dip.getId(), dip.getRouteId(), dip.getRecord(), dip.isComplete()));
        }
        return dtoList;
    }
    
    public boolean removeDip(Long routeId, String userId) {
        Optional<RouteDip> dipOpt = dipRepository.findByUserIdAndRouteId(userId, routeId);
        if (dipOpt.isPresent()) {
            dipRepository.delete(dipOpt.get());
            return true;
        }
        return false;
    }

    public boolean updateDip(Long id, boolean complete, String record) {
        Optional<RouteDip> dip = dipRepository.findById(id);
        
        if (dip.isPresent()) {
            RouteDip routeDip = dip.get();
            routeDip.setComplete(complete);
            routeDip.setRecord(record);
            dipRepository.save(routeDip);
            return true;
        }
        return false;
    }
}
