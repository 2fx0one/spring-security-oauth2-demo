package com.tfx0one.oauth2demo.config.service;

import com.tfx0one.oauth2demo.config.SecurityConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 2fx0one
 * 2019-09-24 10:16
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    @Cacheable(value = "user_details", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("sys_add");
        return new User("admin", SecurityConstants.BCRYPT + "$2a$10$kjHLcUnTXhmzys0OyoF0bOJmJtjY4KsGIH8JO3yfMAD/M9plLlXw2", authorities);
//        return new User("admin", "123456", new ArrayList<>());
    }
}
