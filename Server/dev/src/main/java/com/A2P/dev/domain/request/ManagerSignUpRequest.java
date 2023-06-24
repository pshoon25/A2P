package com.A2P.dev.domain.request;

import com.A2P.dev.domain.enums.AdminStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ManagerSignUpRequest {
    private String adminId;
    private String adminPassword;
    private String adminName;
    private LocalDate adminBirth;
    private String adminPhoneNumber;
    private String adminPoint;
}
