package com.zywang.myblog.service;

import com.zywang.myblog.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
