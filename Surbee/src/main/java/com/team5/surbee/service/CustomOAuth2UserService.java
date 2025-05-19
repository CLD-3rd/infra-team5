package com.team5.surbee.service;

import com.team5.surbee.dto.SessionUser;
import com.team5.surbee.entity.User;
import com.team5.surbee.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpServletRequest request;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2User oauth2User;

        if ("google".equals(provider)) {
            // 구글 로그인은 OIDC 지원
            OidcUserService oidcUserService = new OidcUserService();
            oauth2User = oidcUserService.loadUser((OidcUserRequest) userRequest);
        } else {
            // 그 외 (카카오)
            DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();
            oauth2User = defaultOAuth2UserService.loadUser(userRequest);
        }

        Map<String, Object> attributes = oauth2User.getAttributes();

        String email = null;
        String username = null;
        String providerId = null;

        // provider 별로 attribute 키가 다르니 분기 처리
        if ("google".equals(provider)) {
            email = (String) attributes.get("email");
            username = (String) attributes.get("name");
            providerId = (String) attributes.get("sub");
        } else if ("kakao".equals(provider)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            username = (String) profile.get("nickname");
            providerId = attributes.get("id").toString();  // 카카오는 id가 Long 타입일 수 있으니 문자열 변환
        }

        Integer id = saveOrUpdateAndReturnId(email, username, provider, providerId);

        HttpSession session = this.request.getSession();
        session.setAttribute("user", SessionUser.of(username, email, id));

        return oauth2User;
    }

    @Transactional
    protected Integer saveOrUpdateAndReturnId(String email, String username, String provider, String providerId) {
        User user = userRepository.findByProviderAndProviderId(provider, providerId).orElse(null);

        if (user == null) {
            User newUser = User.of(username, email, provider, providerId);
            userRepository.save(newUser);
            return newUser.getId();
        }

        if (email != null && !email.equals(user.getEmail())) {
            user.setEmail(email);
            userRepository.save(user);
        }
        return user.getId();
    }
}
