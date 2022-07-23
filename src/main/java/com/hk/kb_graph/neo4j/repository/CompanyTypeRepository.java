package com.hk.kb_graph.neo4j.repository;

import com.hk.kb_graph.neo4j.node.CompanyTypeNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CompanyTypeRepository extends Neo4jRepository<CompanyTypeNode, Long> {

    List<CompanyTypeNode> getCompanyTypeNodeByName(String name);
}
