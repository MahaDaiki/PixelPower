package com.api.PixelPower.service.serviceImpl;

import com.api.PixelPower.entity.User;
import com.api.PixelPower.repository.UserRepository;
import com.api.PixelPower.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        System.out.println("get email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        System.out.println("ROLE: " + user.getRole().name());

        return new CustomUserDetails(user, List.of(new SimpleGrantedAuthority(user.getRole().name())),user.getEmail());
    }
}
