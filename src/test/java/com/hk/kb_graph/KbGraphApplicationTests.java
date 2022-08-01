//package com.hk.kb_graph;
//
//import com.hk.kb_graph.neo4j.node.NodeEntity;
//import com.hk.kb_graph.neo4j.service.NodeEntityService;
//import org.junit.jupiter.api.Test;
//import org.neo4j.driver.Record;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//
//@SpringBootTest
//class KbGraphApplicationTests {
//
//    //注入数据源
//    @Autowired
//    private NodeEntityService nodeEntityService;
//
//    @Test
//    void contextLoads() throws SQLException {
//        List<Long> ids = nodeEntityService.getNodeIdByName("弘康人寿保险股份有限司");
//        System.out.println(ids);
//    }
//
//}
