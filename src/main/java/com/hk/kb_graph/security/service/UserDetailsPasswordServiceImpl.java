package com.hk.kb_graph.security.service;

import com.hk.kb_graph.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsPasswordServiceImpl implements UserDetailsPasswordService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        userMapper.updatePassword(user.getUsername(), newPassword);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        return userDetails;
    }
}
