package com.A2P.dev.controller;

import com.A2P.dev.domain.entity.Admin;
import com.A2P.dev.domain.request.*;
import com.A2P.dev.domain.response.AdminLoginResponse;
import com.A2P.dev.exception.*;
import com.A2P.dev.jwt.RefreshToken;
import com.A2P.dev.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    // 로그인
    @PostMapping("/login")
    public AdminLoginResponse adminLogin(@RequestBody AdminLoginRequest request) throws AdminLoginException {
        return adminService.adminLogin(request);
    }

    // 아이디 중복 체크
    @PostMapping("/idcheck")
    public ResponseEntity<String> idCheck(@RequestBody IdCheckRequest request) throws IdCheckException {
        return adminService.idCheck(request);
    }

    // 회원가입
    @PostMapping("/managersignup")
    public ResponseEntity<String> managerSignUp(@RequestBody ManagerSignUpRequest request) throws SignUpException {
        return adminService.managerSignUp(request);
    }

    // 아이디 찾기
    @PostMapping("/findid")
    public String findId(@RequestBody FindIdRequest request) throws FindIdException {
        return adminService.findId(request);
    }

    // 비밀번호 변경할 계정 찾기
    @PostMapping("/findpw")
    public Long findPw(@RequestBody FindPwRequest request) throws FindPwException {
        return adminService.findPw(request);
    }

    // 비밀번호 변경
    @PutMapping("/changepw/{id}")
    public ResponseEntity<String> changePw(@RequestBody ChangePwRequest request, @PathVariable("id") Long id) {
        return adminService.changePw(request, id);
    }

}



