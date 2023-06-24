package com.A2P.dev.service;

import com.A2P.dev.domain.entity.Admin;
import com.A2P.dev.domain.request.*;
import com.A2P.dev.domain.response.AdminLoginResponse;
import com.A2P.dev.encryption.Encrypt;
import com.A2P.dev.exception.*;
import com.A2P.dev.jwt.JwtService;
import com.A2P.dev.mapper.AdminMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminMapper adminMapper;
    private final JwtService jwtService;
    private final Encrypt encrypt;
    private final RedisTemplate<String, String> redisTemplate;

    public AdminService(AdminMapper adminMapper, JwtService jwtService, Encrypt encrypt, RedisTemplate<String, String> redisTemplate) {
        this.adminMapper = adminMapper;
        this.jwtService = jwtService;
        this.encrypt = encrypt;
        this.redisTemplate = redisTemplate;
    }

    // 로그인
    public AdminLoginResponse adminLogin(AdminLoginRequest request) throws AdminLoginException {
        String salt = adminMapper.searchingSalt(request.getAdminId());
        String adminPassword = encrypt.getEncrypt(request.getAdminPassword(), salt);
        Admin admin = adminMapper.login(request.getAdminId(), adminPassword);
        AdminLoginResponse response = new AdminLoginResponse();
        if (admin != null) {
            String accessToken = jwtService.createAccessToken(admin);
            String refreshToken = jwtService.createRefreshToken(admin);
            return new AdminLoginResponse(accessToken, refreshToken);
        } else {
            throw new AdminLoginException();
        }
    }

    // 아이디 중복 체크
    public ResponseEntity<String> idCheck(IdCheckRequest request) throws IdCheckException {
        Admin admin = adminMapper.idCheck(request.getAdminId());
        if (admin == null) {
            return new ResponseEntity<>("사용 가능한 아이디입니다.", HttpStatus.OK);
        } else {
            throw new IdCheckException();
        }
    }

    // 매니저 회원 등록
    public ResponseEntity<String> managerSignUp(ManagerSignUpRequest request) throws SignUpException {
        // 암호화에 사용할 salt를 저장
        String salt = encrypt.getSalt();
        // 패스워드와 salt를 사용하여 암호화
        String encoded = encrypt.getEncrypt(request.getAdminPassword(), salt);
        Admin admin = new Admin(request, salt, encoded);
        int insertRow = adminMapper.managerSignUp(admin);
        if (insertRow != 0) {
            return new ResponseEntity<>("등록이 완료되었습니다.", HttpStatus.OK);
        } else {
            throw new SignUpException();
        }
    }

    // 아이디 찾기
    public String findId(FindIdRequest request) throws FindIdException {
        System.out.println(request);
        String adminId = adminMapper.findId(request.getAdminName(), request.getAdminBirth(), request.getAdminPhoneNumber());
        System.out.println(adminId);
        if (adminId != null) {
            return adminId;
        } else {
            throw new FindIdException();
        }
    }

    // 변경할 비밀번호 계정 찾기
    public Long findPw(FindPwRequest request) throws FindPwException {
        Long findById = adminMapper.findPw(request.getAdminId(), request.getAdminName(), request.getAdminBirth());
        if (findById != null) {
            return findById;
        } else {
            throw new FindPwException();
        }
    }

    // 비밀번호 변경
    public ResponseEntity<String> changePw(ChangePwRequest request, Long id) {
        String salt = adminMapper.findSaltById(id);
        String encoded = encrypt.getEncrypt(request.getAdminPassword(), salt);
        int updateRow = adminMapper.changePw(encoded, id);
        if (updateRow != 0){
            return new ResponseEntity<>("변경이 완료되었습니다.", HttpStatus.OK);
        } else {
            throw new RuntimeException();
        }
    }
}
