package com.hk.kb_graph;

import com.hk.kb_graph.es.domain.QAPairsFullTextSearchResp;
import com.hk.kb_graph.es.service.QAPairSearchByFullTextService;
import com.hk.kb_graph.rds.service.Seq2SeqModelService;
import com.hk.kb_graph.es.domain.ESDocumentRespEntity;
import com.hk.kb_graph.es.service.QAPairService;
import org.elasticsearch.client.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.math.BigDecimal;

@SpringBootTest
public class ESTest {

    @Autowired
    private RestClient client;
    @Autowired
    private QAPairService qaPairService;
    @Autowired
    private Seq2SeqModelService seq2SeqModelService;
    @Autowired
    private QAPairSearchByFullTextService qaPairSearchByFullTextService;

    @Test
    public void testQAPair() throws IOException {
        ESDocumentRespEntity qaPairById = qaPairService.getQAPairById(new BigDecimal(1));
        System.out.println(qaPairById);
    }

    @Test
    public void testGetMaxId() throws IOException {
        BigDecimal maxId = qaPairService.getMaxId();
        System.out.println("maxId:"+maxId);
    }
    @Test
    public void searchQAPairs() throws IOException {
        QAPairsFullTextSearchResp rs = qaPairSearchByFullTextService.searchQAPairs("理赔");
        System.out.println(rs);
    }
    @Test
    public void testModel(){
        String response = seq2SeqModelService.response("开心点哈,一切都会好起来");
        System.out.println(response);
    }
}
