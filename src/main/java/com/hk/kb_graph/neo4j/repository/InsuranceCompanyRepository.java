package com.hk.kb_graph.neo4j.repository;
import com.hk.kb_graph.neo4j.node.CompanyTypeNode;
import com.hk.kb_graph.neo4j.node.InsuranceCompanyNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface InsuranceCompanyRepository extends Neo4jRepository<InsuranceCompanyNode, Long> {
    List<RelationshipProjection> findByName(String companyName);

    interface RelationshipProjection{
        Long getId();
        String getName();
        List<CompanyTypeNode> getCompanyTypeNodes();
        List<InsuranceProductNodeWithoutRelation> getinsuranceProductNodes();

        interface InsuranceProductNodeWithoutRelation{
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
        }
    }

}
