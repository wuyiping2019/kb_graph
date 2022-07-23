package com.hk.kb_graph.es;

import com.alibaba.fastjson2.JSONObject;
import com.hk.kb_graph.es.domain.QAPair;
import org.apache.http.HttpEntity;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ESUtils {
    @Autowired
    private RestClient restClient;

    /**
     * 存储es数据库中所有的索引
     */
    private static List<String> indices;

    /**
     * 添加document
     * */
    public boolean addDocument(QAPair qaPair, String index, String type) throws IOException {
        Long id = qaPair.getId();
        String json = JSONObject.toJSONString(qaPair);
        Request request = new Request("PUT", "/" + index + "/"+type + "/" + id);
        request.setJsonEntity(json);
        Response response = restClient.performRequest(request);
        String result = EntityUtils.toString(response.getEntity());
        System.out.println(result);
        return true;

    }

}
