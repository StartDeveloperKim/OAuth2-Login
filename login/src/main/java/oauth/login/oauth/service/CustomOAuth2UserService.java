package oauth.login.oauth.service;

import lombok.RequiredArgsConstructor;
import oauth.login.api.entity.user.User;
import oauth.login.api.repository.UserRepository;
import oauth.login.oauth.entity.ProviderType;
import oauth.login.oauth.exception.OAuthProviderMissMatchException;
import oauth.login.oauth.info.OAuth2UserInfo;
import oauth.login.oauth.info.OAuth2UserInfoFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>DefualtOAuth2UserService를 상속받아 확장시킨 클래스</p>
 * */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest
                .getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        User savedUser = userRepository.findByUserId(userInfo.getId());

        if (savedUser != null) {
            if (providerType != savedUser.getProviederType()) {
                throw new OAuthProviderMissMatchException("잘못된 Provider 접근입니다.");
            }
            updateUser(savedUser, userInfo);
        } else {
            savedUser = createUser(userInfo, providerType);
        }

        return null;
    }

    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        LocalDateTime now = LocalDateTime.now();

    }

    private User updateUser(User user, OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !user.getUsername().equals(userInfo.getName())) {
            user.setUsername(userInfo.getName());
        }

        if (userInfo.getImageUrl() != null && !user.getProfileImageUrl().equals(userInfo.getImageUrl())) {
            user.setProfileImageUrl(userInfo.getImageUrl());
        }

        return user;
    }
}
