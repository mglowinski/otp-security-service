package com.mglowinski.otp.service;

import com.mglowinski.otp.model.Role;
import com.mglowinski.otp.model.User;
import com.mglowinski.otp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundedUser = userRepository.findByUsername(username);

        return foundedUser
                .map(user -> new org.springframework.security.core.userdetails.User(
                        username, user.getPassword(), mapRolesToAuthorities(user.getRoles())))
                .orElseThrow(() ->
                        new UsernameNotFoundException("User " + username + " not found"));
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<String> getEmailByUsername(String username) {
        return userRepository.findByUsername(username).map(User::getEmail);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
