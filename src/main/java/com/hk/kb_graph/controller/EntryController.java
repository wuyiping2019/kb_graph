package com.hk.kb_graph.controller;


import com.alibaba.fastjson2.JSONObject;
import com.hk.kb_graph.domain.KBNode;
import com.hk.kb_graph.domain.RespDo;
import com.hk.kb_graph.neo4j.node.GraphLink;
import com.hk.kb_graph.neo4j.node.GraphNode;
import com.hk.kb_graph.neo4j.node.GraphResponse;
import com.hk.kb_graph.neo4j.node.NodeEntity;
import com.hk.kb_graph.neo4j.service.InsuranceCompanyService;
import com.hk.kb_graph.neo4j.service.InsuranceProductService;
import com.hk.kb_graph.neo4j.service.NodeEntityService;
import com.hk.kb_graph.service.KBNodeService;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Controller
@ResponseBody
public class EntryController {
    @Autowired
    private KBNodeService kbNodeService;
    @Autowired
    private InsuranceCompanyService insuranceCompanyService;
    @Autowired
    private InsuranceProductService insuranceProductService;

    @Autowired
    private NodeEntityService nodeEntityService;

    @Autowired
    private Driver driver;

    @GetMapping("/search/{name}")
    public RespDo search(@PathVariable("name") String inputStr) {
        List<KBNode> nodes = kbNodeService.getNodeByName(inputStr);
        KBNode node = nodes.get(0);
        String name = node.getName();
        String type = node.getType();
        List<Object> data = null;
        switch (type) {
            case "InsuranceProduct":
                data = Collections.singletonList(insuranceProductService.getInsuranceProduct(name));
                break;
            case "InsuranceCompany":
                data = Collections.singletonList(insuranceCompanyService.findByName(name));
                break;
        }
        return RespDo.newInstance().success("200", "成功", data);
    }

    /**
     * 该接口供前端展示动态节点环绕
     */
    @GetMapping("/getHomeNodes")
    public List<NodeEntity> getHomeNodes() {
        return nodeEntityService.getHomeNodes();
    }

    /**
     * 该接口供前端查询实体并返回实体信息 以键值对的形式返回
     */
    @GetMapping("/getNodeInfo")
    public GraphResponse getNodeInfo(@RequestParam("word") String name) {
        GraphResponse graphResponse = new GraphResponse(new ArrayList<GraphNode>(),new ArrayList<GraphLink>(),0L);
        List<Long> ids = nodeEntityService.getNodeIdByName(name);
        if (ids == null || ids.size() == 0){
            ids = nodeEntityService.getNodeIdByName("弘康人寿保险股份有限公司");
        }
        Long id = ids.get(0);
        GraphResponse response = nodeEntityService.getNodeProperties(graphResponse, id);
        List<Long> relationNodeIds = nodeEntityService.getRelationNodeIds(graphResponse,id);
        return response;
    }


}
