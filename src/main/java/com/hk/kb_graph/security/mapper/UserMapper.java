package com.hk.kb_graph.security.mapper;

import com.hk.kb_graph.security.domain.User;
import com.hk.kb_graph.security.domain.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User getUser(String username);
    List<Role> getRoles(Long uid);

    Integer updatePassword(String username,String newPassword);
}
