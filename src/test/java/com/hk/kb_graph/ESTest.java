package com.hk.kb_graph;

import com.hk.kb_graph.es.domain.ESDocumentRespEntity;
import com.hk.kb_graph.es.service.QAPairService;
import org.elasticsearch.client.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.lang.reflect.Field;

@SpringBootTest
public class ESTest {

    @Autowired
    private RestClient client;
    @Autowired
    private QAPairService qaPairService;

    @Test
    public void testQAPair() throws IOException {
        ESDocumentRespEntity qaPairById = qaPairService.getQAPairById(1l);
        System.out.println(qaPairById);
    }

    @Test
    public void testGetMaxId() throws IOException, IllegalAccessException {
        Long maxId = qaPairService.getMaxId();
        System.out.println("maxId:"+maxId);
    }
}
