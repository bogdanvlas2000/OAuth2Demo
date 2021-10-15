package ittep.restapidemo.model;

import java.util.Map;

public class GitHubUserInfo extends UserInfo {

    public GitHubUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getLogin() {
        return (String) attributes.get("login");
    }
}