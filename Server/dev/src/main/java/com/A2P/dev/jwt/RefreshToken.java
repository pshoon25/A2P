package com.A2P.dev.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RefreshToken {
    private String refreshToken;
    private Long adminIdx;

    public RefreshToken(String refreshToken, Long userIdx) {
        this.refreshToken = refreshToken;
        this.adminIdx = userIdx;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getAdminIdx() {
        return adminIdx;
    }
}