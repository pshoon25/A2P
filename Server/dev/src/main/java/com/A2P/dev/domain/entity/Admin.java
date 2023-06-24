package com.A2P.dev.domain.entity;


import com.A2P.dev.domain.enums.AdminStatus;
import com.A2P.dev.domain.request.FindIdRequest;
import com.A2P.dev.domain.request.ManagerSignUpRequest;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private Long id;
    private String adminId;
    private String adminPassword;
    private String salt;
    private String adminName;
    private LocalDate adminBirth;
    private String adminPhoneNumber;
    private String adminPoint;
    private AdminStatus adminPosition;
    private LocalDateTime adminRegistrationDate;

    public Admin(ManagerSignUpRequest request, String salt, String encoded) {
        this.adminId = request.getAdminId();
        this.adminName = request.getAdminName();
        this.adminBirth = request.getAdminBirth();
        this.adminPhoneNumber = request.getAdminPhoneNumber();
        this.adminPoint = request.getAdminPoint();
        this.adminPosition = AdminStatus.MANAGER;
        this.adminRegistrationDate = LocalDateTime.now();
        this.salt = salt;
        this.adminPassword = encoded;
    }
}
