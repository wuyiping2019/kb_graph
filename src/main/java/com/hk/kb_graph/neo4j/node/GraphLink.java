package com.hk.kb_graph.neo4j.node;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GraphLink {
    private String source;
    private String target;
    private String label;
}
