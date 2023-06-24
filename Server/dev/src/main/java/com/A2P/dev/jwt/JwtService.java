package com.A2P.dev.jwt;

import com.A2P.dev.domain.entity.Admin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final RedisTemplate<String, String> redisTemplate;
    @Value("${jwt.ACCESS_SECRET_KEY}")
    private String ACCESS_SECRET_KEY;
    @Value("${jwt.REFRESH_SECRET_KEY}")
    private String REFRESH_SECRET_KEY;

    public String createAccessToken(Admin admin){
        byte[] keyBytes = Decoders.BASE64.decode(ACCESS_SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        Date now = new Date();
        Claims claims = Jwts.claims();
        claims.put("id", admin.getId());
        claims.put("adminName", admin.getAdminName());
        claims.put("adminPoint", admin.getAdminPoint());
        claims.put("adminPosition", admin.getAdminPosition());
        String accessToken = Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+ (1000 * 60 * 30))) // 만료기간은 30분으로 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return accessToken;
    }

    public String createRefreshToken(Admin admin) {
        byte[] keyBytes = Decoders.BASE64.decode(REFRESH_SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        Date now = new Date();
        String refreshToken = Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 3600 * 24 * 7))) // 만료기간은 1주일로 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        String adminId = admin.getId().toString();
        // Redis에 Refresh Token 저장
        redisTemplate.opsForValue().set(refreshToken, adminId);
        return refreshToken;
    }

    public String getAccessToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("AccessToken");
    }

    public String getRefreshToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("RefreshToken");
    }

    public TokenInfo tokenToDTO(String accessToken){
        try{
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(ACCESS_SECRET_KEY)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            TokenInfo info = new TokenInfo().tokenToDTO(claims);
            return info;
        }catch (Exception e){
            return null;
        }
    }

    public String isValidTokens(){ //엑세스 토큰과 리프레쉬 토큰의 유효성을 둘다 검사한다
        //check both refresh AND access token
        String accessToken = getAccessToken();
        String refreshToken = getRefreshToken();
        if(!isValidAccessToken(accessToken)){
            return isValidRefreshToken(refreshToken);
        }
        return "OK";
    }

    public boolean isValidAccessToken(String accessToken){
        if(accessToken == null) return false;
        // Access Token이 유효하지 않으면
        // is access token is not valid
        if(tokenToDTO(accessToken) == null) return false;
        return true;
    }

    private String isValidRefreshToken(String refreshToken) {
        try {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
            // MyBatis를 사용하여 Redis에서 RefreshToken을 조회
            Long adminId = Long.parseLong(redisTemplate.opsForValue().get(refreshToken));
            if (adminId != null) {
                // Refresh Token이 있다면 새로운 Access Token을 생성하여 HTTPOnly 쿠키에 설정하고 반환한다
                // if refresh token exists create new access token and set pm HTTPOnly cookie
                return refreshAccessToken(response, adminId);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private String refreshAccessToken(HttpServletResponse response, Long adminId) {
        //새로운 엑세스 토큰 생성
        // create new access token
        String newAccessToken = createAccessToken(new Admin());
        return newAccessToken;
    }
}
