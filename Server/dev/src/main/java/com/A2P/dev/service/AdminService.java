package com.A2P.dev.service;

import com.A2P.dev.domain.entity.Admin;
import com.A2P.dev.domain.request.AdminLoginRequest;
import com.A2P.dev.domain.response.AdminLoginResponse;
import com.A2P.dev.exception.AdminLoginException;
import com.A2P.dev.mapper.AdminMapper;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminMapper adminMapper;

    public AdminService(AdminMapper adminMapper){
        this.adminMapper = adminMapper;
    }

    public AdminLoginResponse adminLogin(AdminLoginRequest request) throws AdminLoginException {
        Admin admin = adminMapper.loginAdmin(request.getAdminId(), request.getAdminPassword());
        AdminLoginResponse response = new AdminLoginResponse();
        if( admin != null){
            response.setId(admin.getId());
            response.setAdminName(admin.getAdminName());
            response.setAdminPoint(admin.getAdminPoint());
            response.setAdminPosition(admin.getAdminPosition());
        } else {
            throw new AdminLoginException();
        }
        return response;
    }
}
