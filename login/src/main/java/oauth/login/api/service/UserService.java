package oauth.login.api.service;

import lombok.RequiredArgsConstructor;
import oauth.login.api.entity.user.User;
import oauth.login.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }
}
