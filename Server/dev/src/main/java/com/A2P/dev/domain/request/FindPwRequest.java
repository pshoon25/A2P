package com.A2P.dev.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class FindPwRequest {
    private String adminId;
    private String adminName;
    private LocalDate adminBirth;
}
