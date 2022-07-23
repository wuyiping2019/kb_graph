package com.hk.kb_graph.controller;

import com.alibaba.fastjson2.JSONObject;
import com.hk.kb_graph.domain.ReqDo;
import com.hk.kb_graph.neo4j.node.InsuranceProductNode;
import com.hk.kb_graph.neo4j.repository.InsuranceProductRepository;
import com.hk.kb_graph.neo4j.service.InsuranceProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@ResponseBody
public class InsuranceProductController {
    @Autowired
    private InsuranceProductService insuranceProductService;


    @PostMapping("/kb/getInsuranceProduct")
    public List<InsuranceProductRepository.RelationshipProjection> getInsuranceProductByName(@RequestBody @Validated ReqDo reqDo) {
        return insuranceProductService.getInsuranceProduct(reqDo.getProductName());
    }
}
