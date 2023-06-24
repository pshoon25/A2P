package com.A2P.dev.domain.response;

import com.A2P.dev.domain.entity.Admin;
import com.A2P.dev.domain.enums.AdminStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginResponse {
    private String accessToken;
    private String refreshToken;
}
