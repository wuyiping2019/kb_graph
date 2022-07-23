package com.hk.kb_graph.neo4j.node;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;

@Node("InsuranceCompany")
@ToString
@AllArgsConstructor
@Data
public class InsuranceCompanyNode {

    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String name;

    @Relationship(type = "保险产品",direction = Relationship.Direction.OUTGOING)
    private List<InsuranceProductNode> insuranceProductNodes;

    @Relationship(type = "公司类型", direction = Relationship.Direction.OUTGOING)
    private List<CompanyTypeNode> companyTypeNodes;

}
