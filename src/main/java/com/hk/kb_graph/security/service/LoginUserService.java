package com.hk.kb_graph.security.service;

import com.hk.kb_graph.security.domain.User;
import com.hk.kb_graph.security.domain.Role;
import com.hk.kb_graph.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginUserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUser(username);
        List<Role> roles = userMapper.getRoles(user.getId());
        user.setRoles(roles);
        return user;
    }
}
