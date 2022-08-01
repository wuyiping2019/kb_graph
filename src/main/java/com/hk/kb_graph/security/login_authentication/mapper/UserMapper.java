package com.hk.kb_graph.security.login_authentication.mapper;

import com.hk.kb_graph.security.login_authentication.domain.Role;
import com.hk.kb_graph.security.login_authentication.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User getUser(String username);
    List<Role> getRoles(Long uid);
}
