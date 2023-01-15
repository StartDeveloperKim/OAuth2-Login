package oauth.login.oauth.info;

import oauth.login.oauth.entity.ProviderType;
import oauth.login.oauth.info.impl.GoogleOAuth2UserInfo;
import oauth.login.oauth.info.impl.NaverOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        return switch (providerType) {
            case GOOGLE -> new GoogleOAuth2UserInfo(attributes);
            case NAVER -> new NaverOAuth2UserInfo(attributes);
            default -> throw new IllegalArgumentException("Invalid Provider Type.");
        };
    }
}
