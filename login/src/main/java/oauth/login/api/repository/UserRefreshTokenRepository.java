package oauth.login.api.repository;

import oauth.login.api.entity.user.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findByUserId(String userId);

    UserRefreshToken findByUserIdAndRefreshToken(String userId, String refreshToken);
}
