package com.api.PixelPower.security;

import com.api.PixelPower.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Getter
public class CustomUserDetails extends org.springframework.security.core.userdetails.User{
    private final Long userId;
    private final String email;

    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities, String email) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.userId = user.getId();
        this.email = email;
    }
}
