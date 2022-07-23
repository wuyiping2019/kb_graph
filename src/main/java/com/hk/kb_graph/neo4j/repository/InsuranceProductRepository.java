package com.hk.kb_graph.neo4j.repository;
import com.hk.kb_graph.neo4j.node.CompanyTypeNode;
import com.hk.kb_graph.neo4j.node.InsuranceProductNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface InsuranceProductRepository extends Neo4jRepository<InsuranceProductNode, Long> {
    List<RelationshipProjection > findByName(String productName);

    interface RelationshipProjection {
        String getId();
        String getName();
        String getCompany();
        String getProductType();
        String getDesignType();
        String getSpecialAttributes();
        String getInsuredType();
        String getInsuredPeriodType();
        String getPayType();
        String getProductTermsTextCode();
        String getSaleStatus();
        String getStopDate();
        List<InsuranceCompanyWithoutRelation> getInsuranceCompanyNodes();
        //避免循环嵌套 不再查询InsuranceProduct
        interface InsuranceCompanyWithoutRelation{
            Long getId();
            String getName();
            List<CompanyTypeNode> getCompanyTypeNodes();
        }


    }
}
