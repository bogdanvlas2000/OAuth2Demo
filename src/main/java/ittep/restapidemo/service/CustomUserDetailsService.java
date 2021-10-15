package ittep.restapidemo.service;

import ittep.restapidemo.model.CustomUserDetails;
import ittep.restapidemo.model.User;
import ittep.restapidemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user with username: " + username);
        }
        UserDetails principal = new CustomUserDetails(user);
        return principal;
    }
}
