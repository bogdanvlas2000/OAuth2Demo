package ittep.restapidemo.security;

import ittep.restapidemo.model.User;
import ittep.restapidemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(request);

        try {
            processOAuth2User(request, oauth2User);
            return oauth2User;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private void processOAuth2User(OAuth2UserRequest request, OAuth2User oauth2User) {
        //github or google
        String provider = request.getClientRegistration().getRegistrationId();
        UserInfo userInfo = null;
        if (provider.equals("github")) {
            userInfo = new GitHubUserInfo(oauth2User.getAttributes());
        }

        boolean exists = userRepository.existsByLoginAndProvider(userInfo.getLogin(), provider);
        if (!exists) {
            User user = new User();
            user.setProvider(provider);
            user.setLogin(userInfo.getLogin());
            userRepository.save(user);
        }
    }
}
