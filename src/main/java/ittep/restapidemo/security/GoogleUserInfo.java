package ittep.restapidemo.security;

import java.util.Map;

public class GoogleUserInfo extends UserInfo {
    public GoogleUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getLogin() {
        return (String) attributes.get("email");
    }
}
