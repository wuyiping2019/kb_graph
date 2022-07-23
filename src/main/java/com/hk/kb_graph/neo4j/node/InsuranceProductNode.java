package com.hk.kb_graph.neo4j.node;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Node("InsuranceProduct")
@Data
@AllArgsConstructor
public class InsuranceProductNode {
    @Id
    @GeneratedValue
    private Long id;
    @Property
    private String name;
    @Property
    private String company;
    @Property
    private String productType;
    @Property
    private String designType;
    @Property
    private String specialAttributes;
    @Property
    private String insuredType;
    @Property
    private String insuredPeriodType;
    @Property
    private String payType;
    @Property
    private String productTermsTextCode;
    @Property
    private String saleStatus;
    @Property
    private String stopDate;
    @Relationship(type = "保险公司",direction = Relationship.Direction.OUTGOING)
    private List<InsuranceCompanyNode> insuranceCompanyNodes;
}
