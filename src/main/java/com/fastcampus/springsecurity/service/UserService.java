package com.fastcampus.springsecurity.service;

import com.fastcampus.springsecurity.config.EncoderConfiguration;
import com.fastcampus.springsecurity.exception.user.UserAlreadyExistsException;
import com.fastcampus.springsecurity.exception.user.UserNotFoundException;
import com.fastcampus.springsecurity.model.Entity.UserEntity;
import com.fastcampus.springsecurity.model.user.User;
import com.fastcampus.springsecurity.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private EncoderConfiguration encoderConfiguration;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public User signUp(String username, String password) {
        userEntityRepository.findByUsername(username).ifPresent(
                user->{
                    throw new UserAlreadyExistsException();
                });
        var userEntity = UserEntity.of(username, passwordEncoder.encode(password));
        var savedUserEntity = userEntityRepository.save(userEntity);
        return User.from(savedUserEntity);
    }
}
