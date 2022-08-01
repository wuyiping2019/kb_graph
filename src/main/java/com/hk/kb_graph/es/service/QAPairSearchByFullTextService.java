package com.hk.kb_graph.es.service;

import com.alibaba.fastjson2.JSONObject;
import com.hk.kb_graph.es.annotation.InjectFileString;
import com.hk.kb_graph.es.domain.QAPairsFullTextSearchResp;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class QAPairSearchByFullTextService {
    @Autowired
    private RestClient client; //thread safe
    private final String index = "kb_graph";
    private final String type = "qaPairs";
    @InjectFileString(fileName = "es_body/getMaxIdOfQAPairs.json")
    private String maxIdRequestBody;
    @InjectFileString(fileName = "es_body/fullQuestionSearch.json")
    private String fullQuestionSearch;

    /**
     * 对/kb_graph/qaPairs下question字段进行全文检索
     */
    public QAPairsFullTextSearchResp searchQAPairs(String searchStr) throws IOException {
        String method = "GET";
        String endpoint = String.format("/%s/%s/_search", index, type);
        String body = fullQuestionSearch.replaceAll("\\{\\{placeholder\\}\\}", searchStr);
        Request request = new Request(method, endpoint);
        request.setJsonEntity(body);
        Response response = client.performRequest(request);
        String rs = EntityUtils.toString(response.getEntity());
        System.out.println(rs);
        System.out.println(rs);
        return JSONObject.parseObject(rs, QAPairsFullTextSearchResp.class);
    }
}
