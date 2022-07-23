package com.hk.kb_graph.neo4j.node;

import com.sun.javafx.collections.MappingChange;
import lombok.Data;
import java.util.*;
import java.util.HashMap;

/**
 * 存储节点信息 包含节点id,节点name和节点的类型
 * */
@Data
public class NodeEntity {
    private Long id;
    private String name;
    private String type;

    public NodeEntity(Map<String,Object> map){
        this.id = (Long) map.get("id");
        this.name = (String) map.get("name");
        List types = (List) map.get("type");
        this.type = (String) types.get(0);
    }
}
