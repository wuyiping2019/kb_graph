package com.hk.kb_graph.rds.mapper;

import com.hk.kb_graph.domain.KBNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KBNodeMapper {

    List<KBNode> getNodeByName(String inputStr);
}
