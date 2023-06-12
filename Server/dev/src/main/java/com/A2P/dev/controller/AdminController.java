package com.A2P.dev.controller;

import com.A2P.dev.domain.entity.Admin;
import com.A2P.dev.domain.request.AdminLoginRequest;
import com.A2P.dev.domain.response.AdminLoginResponse;
import com.A2P.dev.exception.AdminLoginException;
import com.A2P.dev.service.AdminService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public AdminLoginResponse adminLogin(@RequestBody AdminLoginRequest request) throws AdminLoginException {
        return adminService.adminLogin(request);
    }
}
