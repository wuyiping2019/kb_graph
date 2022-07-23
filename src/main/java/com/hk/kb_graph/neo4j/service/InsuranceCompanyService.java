package com.hk.kb_graph.neo4j.service;

import com.hk.kb_graph.domain.ReqDo;
import com.hk.kb_graph.neo4j.node.InsuranceCompanyNode;
import com.hk.kb_graph.neo4j.repository.InsuranceCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceCompanyService {
    @Autowired
    private InsuranceCompanyRepository insuranceCompanyRepository;

    public List<InsuranceCompanyRepository.RelationshipProjection> findByName(String name){
        return insuranceCompanyRepository.findByName(name);
    }
}
