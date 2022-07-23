package com.hk.kb_graph.controller;

import com.hk.kb_graph.domain.ReqDo;
import com.hk.kb_graph.neo4j.node.InsuranceCompanyNode;
import com.hk.kb_graph.neo4j.node.InsuranceProductNode;
import com.hk.kb_graph.neo4j.repository.InsuranceCompanyRepository;
import com.hk.kb_graph.neo4j.service.InsuranceCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
public class InsuranceCompanyController {
    @Autowired
    private InsuranceCompanyService insuranceCompanyService;

    @Autowired
    private InsuranceCompanyRepository insuranceCompanyRepository;

    @PostMapping("/kb/getInsuranceCompany")
    public List<InsuranceCompanyRepository.RelationshipProjection> getInsuranceCompanyAndProduct(@RequestBody @Validated ReqDo reqDo) {
        return insuranceCompanyRepository.findByName(reqDo.getCompanyName());
    }
}
