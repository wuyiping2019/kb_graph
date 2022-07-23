package com.hk.kb_graph.neo4j.node;


import lombok.Data;
import lombok.NoArgsConstructor;

@NodeEntity
@Data
@NoArgsConstructor
public class InsuranceCompany {

    @Id
    private Long id;

    private String name;

}
