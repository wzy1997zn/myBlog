package com.zywang.myblog.service;

import com.zywang.myblog.dao.UserRepository;
import com.zywang.myblog.po.User;
import com.zywang.myblog.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Util.getMD5(password)); // avoid store real password
        return user;
    }
}
