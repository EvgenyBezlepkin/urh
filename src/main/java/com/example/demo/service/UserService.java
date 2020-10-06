package com.example.demo.service;



import com.example.demo.domain.Role;
import com.example.demo.domain.Token;
import com.example.demo.domain.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenRepository tokenRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public User saveUser(User userFromForm) {
        if (emailExists(userFromForm.getEmail())) {
            System.out.println("There is an account with that email address:");
            return null;
        }
        User user = new User();
        Set<Role> roles = new HashSet<>();
        Role currentRole = roleRepository.findByName("ROLE_USER");
        roles.add(currentRole);
            user.setFirstName(userFromForm.getFirstName());
            user.setLastName(userFromForm.getLastName());
            user.setPassword(bCryptPasswordEncoder.encode(userFromForm.getPassword()));
            user.setEmail(userFromForm.getEmail());
            user.setRoles(roles);
            userRepository.save(user);
        return user;
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }


    public void createVerificationToken(User user, String token) {
        Token myToken = new Token(token, user, LocalDateTime.now());
        tokenRepository.save(myToken);
    }


}


