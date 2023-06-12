package com.A2P.dev.domain.entity;


import com.A2P.dev.domain.enums.AdminStatus;
import lombok.*;

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
    private String adminName;
    private LocalDate adminBirth;
    private String adminPhoneNumber;
    private String adminPoint;
    private AdminStatus adminPosition;
    private LocalDateTime adminRegistrationDate;
}
