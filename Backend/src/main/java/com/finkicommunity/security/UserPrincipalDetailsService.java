package com.finkicommunity.security;

import com.finkicommunity.domain.User;
import com.finkicommunity.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Username " + username + " not found.");
        }
        UserPrincipal userPrincipal = new UserPrincipal(user);
        return userPrincipal;
    }
}
