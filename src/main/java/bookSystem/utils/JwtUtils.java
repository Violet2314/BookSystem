package bookSystem.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtils {
    // 生成一个 SecretKey 用于 HS256 签名
    private static final SecretKey key = Keys.hmacShaKeyFor("my-very-secure-and-long-secret-key-my-very-secure-and-long-secret-key".getBytes()); // 32 字节以上
    private static final Long expire = 43200000L; // 12小时

    // 创建 JWT
    public static String creatJwt(Map<String, Object> claims) {
        log.info("创建token");
        String jwt = Jwts.builder()
                .setClaims(claims) // 添加 claims
                .signWith(key, SignatureAlgorithm.HS256) // 使用 SecretKey 进行签名
                .setExpiration(new Date(System.currentTimeMillis() + expire)) // 设置过期时间
                .compact(); // 生成 JWT
        log.info("创建完成token");
        return jwt;
    }

    // 解析 JWT
    public static Claims analysisJwt(String jwt) {
        Claims claims = Jwts.parserBuilder() // 使用 parserBuilder 代替 parser
                .setSigningKey(key) // 设置签名密钥
                .build() // 构建 JWT 解析器
                .parseClaimsJws(jwt) // 解析 JWT
                .getBody(); // 获取 claims
        return claims;
    }
}
