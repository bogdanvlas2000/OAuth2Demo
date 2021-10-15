package ittep.restapidemo.security;

import ittep.restapidemo.model.User;
import ittep.restapidemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomOidcUserService extends OidcUserService {
    private UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest request) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(request);
        try {
            processOidcUser(request, oidcUser);
            return oidcUser;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private void processOidcUser(OidcUserRequest request, OidcUser oidcUser) {
        //github or google
        String provider = request.getClientRegistration().getRegistrationId();
        UserInfo userInfo = null;
        if (provider.equalsIgnoreCase("google")) {
            userInfo = new GoogleUserInfo(oidcUser.getAttributes());
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
