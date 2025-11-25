package com.tjoeun.corunning.controller;

import com.tjoeun.corunning.domain.User;
import com.tjoeun.corunning.dto.LoginDTO;
import com.tjoeun.corunning.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //전체 회원 조회
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    //특정 회원 조회
    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }

    //회원 정보 생성(가입)
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    //회원 정보 수정
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable("userId") String userId,
                           @RequestBody User update,
                           HttpSession session) {

        String loginId = (String) session.getAttribute("loginUserId");
        if (loginId == null) {
            throw new RuntimeException("로그인해야 수정할 수 있습니다.");
        }
        if (!loginId.equals(userId)) {
            throw new RuntimeException("본인 계정만 수정할 수 있습니다.");
        }
        return userService.updateUser(userId, update);
    }

    //회원 정보 삭제
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") String userId,
                             HttpSession session) {

        String loginId = (String) session.getAttribute("loginUserId");
        if (loginId == null) {
            throw new RuntimeException("로그인해야 삭제할 수 있습니다.");
        }
        if (!loginId.equals(userId)) {
            throw new RuntimeException("본인 계정만 삭제할 수 있습니다.");
        }
        userService.deleteUser(userId);
        session.invalidate();
        return "회원 탈퇴 완료";
    }
    
    //로그인
    @PostMapping("/login")   
    public String login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        User loginUser = userService.login(loginDTO.getUserId(), loginDTO.getUserPw());
        session.setAttribute("loginUserId", loginUser.getUserId());
        return "로그인 성공!";
    }
    
    //로그인 상태 조회
    @GetMapping("/me")
    public User getCurrentUser(HttpSession session) {
        String userId = (String) session.getAttribute("loginUserId");

        if (userId == null) {
            throw new RuntimeException("로그인 상태가 아닙니다.");
        }
        return userService.getUser(userId);  
        }
    
    //로그아웃
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  
        return "로그아웃 되었습니다.";
    }


}

