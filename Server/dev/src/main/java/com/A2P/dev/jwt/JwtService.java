package com.A2P.dev.jwt;

import com.A2P.dev.domain.entity.Admin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.ACCESS_SECRET_KEY}")
    private String ACCESS_SECRET_KEY;
    @Value("{jwt.REFRESH_SECRET_KEY}")
    private String REFRESH_SECRET_KEY;

    public String createAccessToken(Admin admin){

        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXP_TIME);

        Claims claims = Jwts.claims();
        claims.put("id", admin.getId());
        claims.put("adminName", admin.getAdminName());
        claims.put("adminPoint", admin.getAdminPoint());
        claims.put("adminPosition", admin.getAdminPosition());

        String token = Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
        return token;
    }

    public TokenInfo tokenToDTO(String token){
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            TokenInfo info = new TokenInfo().tokenToDTO(claims);
            return info;
        }catch (Exception e){
            return null;
        }
    }

    public String getToken() {
        // 현재 요청에 대한 ServletRequestAttributes를 가져옵니다.
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        // ServletRequestAttributes에서 HttpServletRequest를 가져옵니다.
        HttpServletRequest request = requestAttributes.getRequest();
        // 요청 헤더에서 "authorization" 헤더 값을 가져와서 반환합니다.
        return request.getHeader("Authorization");
    }


}
