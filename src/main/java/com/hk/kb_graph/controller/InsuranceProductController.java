package com.hk.kb_graph.controller;

import com.hk.kb_graph.neo4j.node.InsuranceProductNode;
import com.hk.kb_graph.neo4j.repository.InsuranceProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class Test {
    @Autowired
    private InsuranceProductRepository insuranceProductRepository;

    @RequestMapping("/getInsuranceProductNode")
    @ResponseBody
    public List<InsuranceProductNode> test(){
        List<InsuranceProductNode> insuranceCompanyNode = insuranceProductRepository.getInsuranceCompanyNode(new Long(1));
        System.out.println(insuranceCompanyNode);
        return insuranceCompanyNode;
    }
}
