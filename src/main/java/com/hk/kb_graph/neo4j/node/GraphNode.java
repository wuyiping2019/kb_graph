package com.hk.kb_graph.neo4j.node;

import lombok.Data;

/**
 * 用于存储关于一个节点的键值对信息 包含属性和关系及关系节点名称构成的键值对
 * */
@Data
public class GraphNode {
    private Long id;
    private String name;
    private String type;

    public NodeKV(String key, String value,String type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }
}
