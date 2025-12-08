package com.tjoeun.corunning.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tjoeun.corunning.domain.User;
import com.tjoeun.corunning.exception.CustomException;
import com.tjoeun.corunning.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 전체 조회
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 특정 회원 조회
    public User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. id=" + userId));
    }

    // 회원 생성(가입)
    public User createUser(User user) {
        //아이디 중복 체크
        if (userRepository.existsByUserId(user.getUserId())) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        //이름 중복 체크
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new RuntimeException("이미 존재하는 이름입니다.");
        }
     // ※ 비밀번호 암호화!
        String encodedPw = passwordEncoder.encode(user.getUserPw());
        user.setUserPw(encodedPw);

        // 저장
        return userRepository.save(user);
    }
    
    //아이디 중복체쿠
    public boolean isIdChecked(String userId) {
    	boolean available = userRepository.existsByUserId(userId);
    	return available;
    	
    }

    // 회원 정보 수정
    public User updateUser(String userId, User update) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. id=" + userId));
        if (update.getUserPw() != null && !update.getUserPw().isEmpty()) {
            String encodedPw = passwordEncoder.encode(update.getUserPw());
            user.setUserPw(encodedPw);
        }
        else {
        	throw new RuntimeException("비밀번호가 입력되지 않았습니다.");
        }
        user.setUserName(update.getUserName());
        user.setBirthDate(update.getBirthDate());
        user.setHireDate(update.getHireDate());
        user.setPhone(update.getPhone());
        user.setUserAddress(update.getUserAddress());

        return userRepository.save(user);
    }

    // 회원 삭제
    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("사용자를 찾을 수 없습니다. id=" + userId);
        }
        userRepository.deleteById(userId);
    }
    
    //로그인
    public User login(String userId, String userPw) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 아이디가 존재하지 않습니다."));

        if (!passwordEncoder.matches(userPw, user.getUserPw())) {
            throw new CustomException("비밀번호가 올바르지 않습니다.");
        }
        return user;
    }

	public boolean existsByEmail(String email) {
		return userRepository.existsByUserId(email);
		
	}
}
