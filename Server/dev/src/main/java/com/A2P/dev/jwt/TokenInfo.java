package com.A2P.dev.jwt;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo {
    private Long id;
    private String adminName;
    private String adminPoint;
    private String adminPosition;
    public TokenInfo tokenToDTO(Claims claims) {
        Long id = (Long) claims.get("id");
        String adminName = (String) claims.get("adminName");
        String adminPoint = (String) claims.get("adminPoint");
        String adminPosition = (String) claims.get("adminPosition");
        return new TokenInfo(id, adminName, adminPoint, adminPosition);
    }
}
