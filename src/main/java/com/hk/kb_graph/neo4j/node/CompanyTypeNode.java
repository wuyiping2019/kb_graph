package com.hk.kb_graph.neo4j.node;


import lombok.Data;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@ToString
@Node("CompanyType")
@Data
public class CompanyTypeNode {
    @Id
    @GeneratedValue
    private Long id;
    @Property
    private String name;

}
