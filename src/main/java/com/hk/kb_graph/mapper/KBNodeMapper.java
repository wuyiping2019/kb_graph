package com.hk.kb_graph.mapper.KBNodeMapper;

import com.hk.kb_graph.domain.KBNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public class KBNodeMapper {

    List<KBNode> getNodeByName(String inputStr);
}
