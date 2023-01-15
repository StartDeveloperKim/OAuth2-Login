package oauth.login.oauth.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import oauth.login.oauth.exception.TokenValidFailedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * <p>인증토큰 제공 클래스 팩토리 패턴??
 * 전달되는 인자별로 각각의 생성자가 실행된다.</p>
 * <p>AuthToken 클래스에 있는 검증 메서드를 통해 검증한다.</p>
 * */
@Slf4j
public class AuthTokenProvider {

    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    public AuthTokenProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public AuthToken createAuthToken(String id, Date expiry) {
        return new AuthToken(id, key, expiry);
    }

    public AuthToken createAuthToken(String id, String role, Date expiry) {
        return new AuthToken(id, role, expiry, key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public Authentication getAuthentication(AuthToken authToken) {
        if (authToken.validate()) {
            Claims claims = authToken.getTokenClaims(); // 토큰의 claim을 가져온다.
            Collection<? extends GrantedAuthority> authorities = Arrays.stream(
                            new String[]{claims.get(AUTHORITIES_KEY).toString()})
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            log.debug("claims subject := [{}]", claims.getSubject());
            User principal = new User(claims.getSubject(), "", authorities);

            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }
}
