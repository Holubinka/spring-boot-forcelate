package com.holubinka.services;

import com.holubinka.controller.exception.NotMatchedPasswordsException;
import com.holubinka.controller.model.UserExt;
import com.holubinka.dao.UserDao;
import com.holubinka.model.Color;
import com.holubinka.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.security.core.userdetails.User.builder;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Optional<User> create(UserExt userExt) {
        if (!userExt.getPassword().equals(userExt.getConfirmPassword())) {
            throw new NotMatchedPasswordsException();
        }
        User user = User.of(userExt);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return Optional.ofNullable(userDao.save(user));
    }

    @Override
    public Optional<List<User>> getAll() {
        return Optional.ofNullable(userDao.findAll());
    }

    @Override
    public Optional<List<User>> getUsersByAge(Integer age) {
        return Optional.ofNullable(userDao.findAllByAgeGreaterThanEqual(age));
    }

    @Override
    public Optional<List<User>> getUsersByArticleColor(String color) {
        return Optional.ofNullable(userDao.findAllByArticle_Color(Color.valueOf(color.toUpperCase())));
    }

    @Override
    public Optional<List<User>> getDistinctUsersByName() {
        return Optional.of(userDao.getAllDistinctFirstNameBy().stream()
                .filter(user -> user.getArticle()
                        .stream()
                        .count() >=3)
                .collect(Collectors.toList()));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDao.findByEmail(email)
                .map(this::toUserDetails)
                .orElseGet(builder().disabled(true)::build);
    }

    private UserDetails toUserDetails(User user) {
        return builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }
}
