package com.serverapp.serverapp.service;

import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.serverapp.serverapp.entity.User;
import com.serverapp.serverapp.model.AppUserDetail;
import com.serverapp.serverapp.repository.UserRepository;

@Service
@AllArgsConstructor
public class AppUserDetailService implements UserDetailsService {
    private UserRepository userRepository;

    // @Override
    // public UserDetails loadUserByUsername(String username) throws
    // UsernameNotFoundException {
    // User user = userRepository.findByUsernameOrEmployeeEmail(username, username)
    // .orElseThrow(() -> new UsernameNotFoundException("User not found: " +
    // username));

    // List<GrantedAuthority> authorities = user.getRoles().stream()
    // .flatMap(role -> role.getPrivileges().stream())
    // .map(Privilege::getName)
    // .map(SimpleGrantedAuthority::new)
    // .collect(Collectors.toList());

    // return new org.springframework.security.core.userdetails.User(
    // user.getUsername(),
    // user.getPassword(),
    // authorities);
    // }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmployeeEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new AppUserDetail(user);
    }
}
