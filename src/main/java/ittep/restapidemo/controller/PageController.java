package ittep.restapidemo.controller;

import ittep.restapidemo.security.GitHubUserInfo;
import ittep.restapidemo.security.GoogleUserInfo;
import ittep.restapidemo.security.UserInfo;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login/oauth2")
    public String loginPage() {
        return "oauth2login";
    }

    @GetMapping("/secured")
    public String secured(OAuth2AuthenticationToken token, Model model) {
        OAuth2User oauth2User = token.getPrincipal();
        String provider = token.getAuthorizedClientRegistrationId();
        UserInfo userInfo = null;
        if (provider.equalsIgnoreCase("github")) {
            userInfo = new GitHubUserInfo(oauth2User.getAttributes());
        }
        if (provider.equalsIgnoreCase("google")) {
            userInfo = new GoogleUserInfo(oauth2User.getAttributes());
        }
        model.addAttribute("login", userInfo.getLogin());
        model.addAttribute("provider", provider);
        return "secured";
    }

}
