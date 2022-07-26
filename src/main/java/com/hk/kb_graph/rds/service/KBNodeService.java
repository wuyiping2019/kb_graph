package com.hk.kb_graph.rds.service;

import com.hk.kb_graph.domain.KBNode;
import com.hk.kb_graph.rds.mapper.KBNodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KBNodeService {
    @Autowired
    private KBNodeMapper kbNodeMapper;

    public List<KBNode> getNodeByName(String name){
        return kbNodeMapper.getNodeByName(name);
    }
}
