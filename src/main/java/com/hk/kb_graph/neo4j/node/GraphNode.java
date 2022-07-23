package com.hk.kb_graph.neo4j.node;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用于存储关于一个节点的键值对信息 包含属性和关系及关系节点名称构成的键值对
 */
@Data
@AllArgsConstructor
public class GraphNode {
    private String id;
    private String name;
    private String category;


}
