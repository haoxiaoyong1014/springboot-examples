package cn.haoxy.interceptor.utils;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * Created by Haoxy on 2019-05-17.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class TokenUtils {

    /**
     * 签名秘钥
     */
    public static final String SECRET = "haoxy";

    /**
     * 生成atoken
     *
     * @param id 一般传入userName
     * @return
     */
    public static String createJwtToken(String id) {
        String issuer = "atoken";
        String subject = "atoken@admin";
        long ttlMillis = System.currentTimeMillis();
        return createJwtToken(id, issuer, subject, ttlMillis);
    }

    /**
     * 生成 rtoken
     *
     * @param id
     * @return
     */
    public static String createJwtrToken(String id) {
        String issuer = "rtoken";
        String subject = "rtoken@admin";
        long ttlMillis = System.currentTimeMillis();
        return createJwtToken(id, issuer, subject, ttlMillis);
    }

    /**
     * 生成Token
     *
     * @param id        编号
     * @param issuer    该JWT的签发者，是否使用是可选的
     * @param subject   该JWT所面向的用户，是否使用是可选的；
     * @param ttlMillis 签发时间
     * @return token String
     */
    public static String createJwtToken(String id, String issuer, String subject, long ttlMillis) {

        // 签名算法 ，将对token进行签名
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成签发时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 通过秘钥签名JWT
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // 设置JWT声明
        long time = now.getTime() + 1000 * 60; //1分钟,用于测试使用,生产环境在 * 60
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setExpiration(new Date(time))
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        // 添加过期时间
        if ("rtoken@admin".equals(subject)) {
            Date exp = new Date(new Date(System.currentTimeMillis()).getTime() + 2500 * 36000 * 7);//7天
            builder.setExpiration(exp);
        }

        // 构建JWT并将其序列化为紧凑的URL安全字符串
        return builder.compact();

    }

    // 验证和读取JWT的示例方法
    public static Claims parseJWT(String jwt) {
        // 如果它不是签名的JWS（如预期的那样），则该行将抛出异常

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static void main(String[] args) {
        //String token = TokenUtils.createJwtToken("admin");
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMiLCJpYXQiOjE1NTkyNzA1NjUsInN1YiI6ImF0b2tlbkBhZG1pbiIsImV4cCI6MTU1OTI3MDYyNSwiaXNzIjoiYXRva2VuIn0.8hFkRfjsNt8WXSG3tnWOfDI72zOZY_Hme9bVbbeXm3s";
        System.out.println(token);
        Claims claims = parseJWT(token);
        System.out.println(claims.getSubject());
        Date expiration = claims.getExpiration();
        System.out.println(claims.getId());
        //在这里不用做判断,在验证 token 的时候 如果token过期会抛出 io.jsonwebtoken.ExpiredJwtException,我们捕获这个异常并做相应的操作就行;
        /*if (expiration.getTime() < new Date().getTime()) {
            System.out.println("token 过期了");
            return;
        }*/
        System.out.println(expiration);
    }


}
