package com.hk.kb_graph.service;

import com.hk.kb_graph.neo4j.node.InsuranceProductNode;
import com.hk.kb_graph.neo4j.repository.InsuranceProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Neo4jSearchService {
    @Autowired
    private InsuranceProductRepository insuranceProductRepository;

    /**
     * 根据保险产品名称查询
     *
     * @param productName
     * @return 保险产品列表
     */
    public List<InsuranceProductNode> getInsuranceProduct(String productName) {
        return insuranceProductRepository.getInsuranceProductNodeByName(productName);
    }
}
