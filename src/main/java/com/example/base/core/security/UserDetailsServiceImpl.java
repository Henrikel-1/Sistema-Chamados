package com.example.base.core.security;

import com.example.base.dao.AdminRepository;
import com.example.base.model.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = adminRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return User.withUsername(usuario.getId().toString())
                .password(usuario.getSenha())
                .authorities(List.of(new SimpleGrantedAuthority(usuario.getPapel().toString()))).build();
    }
}
