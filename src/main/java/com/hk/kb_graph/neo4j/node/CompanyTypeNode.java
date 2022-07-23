package com.hk.kb_graph.neo4j.node;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@ToString
@Node("CompanyType")
@AllArgsConstructor
public class CompanyTypeNode {
    private Long id;
    private String name;
}
