package com.survey.SurveyApp.service;

import com.survey.SurveyApp.dao.UserDAO;
import com.survey.SurveyApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserDAO userRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            //set roles for the user
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
            if (user.isAdmin()) grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()),
                    grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("User not found.");

        }
    }
}
