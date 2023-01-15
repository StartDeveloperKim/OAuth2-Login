package oauth.login.api.repository;

import oauth.login.api.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Long, User> {

    @Query("select user from User user where user.userId=:userId")
    User findByUserId(@Param("userId") String userId);
}
