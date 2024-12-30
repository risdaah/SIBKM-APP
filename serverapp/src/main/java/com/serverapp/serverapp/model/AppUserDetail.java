package com.serverapp.serverapp.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.serverapp.serverapp.entity.User;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class AppUserDetail implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Hanya menambahkan privileges ke otoritas
        user.getRoles().forEach(role -> {
            role.getPrivileges().forEach(privilege -> {
                String privilegeName = privilege.getName().toUpperCase(); // Ubah ke huruf besar
                authorities.add(new SimpleGrantedAuthority(privilegeName));
            });
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
