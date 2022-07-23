package com.hk.kb_graph.es.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.hk.kb_graph.es.annotation.InjectFileString;
import com.hk.kb_graph.es.domain.ESDocumentRespEntity;
import com.hk.kb_graph.es.domain.QAPair;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class QAPairService {
    @Autowired
    private RestClient client;
    private String index = "kb_graph";
    private String type = "qaPairs";
    @InjectFileString(fileName = "getMaxIdOfQAPairs.json")
    private String maxIdRequestBody;
    /**
     * 该service操作的是指定的index和type
     * 在该类中不需要直接访问该属性 避免出现数据安全问题
     */
    private Long maxId;


    /**
     * 使用同步方法 只允许一个线程访问maxId属性,每次访问maxId都会+1并返回这个maxId,用于存储文档
     */
    public synchronized Long getMaxId() throws IOException {
        System.out.println(this.maxIdRequestBody);
        //当前存储的最大id
        Long id = this.maxId;
        //判断当前ID是否为空 如果为空 则需要查询es最大的id
        if (id == null) {
            String method = "GET";
            String endpoint = String.format("/%s/%s", this.index, this.type);
            Request request = new Request(method, endpoint);
            request.setJsonEntity(maxIdRequestBody);
            Response response = client.performRequest(request);
            String body = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSONObject.parseObject(body);
            Object object = jsonObject.get("aggregations");
            System.out.println(object);
            return null;
        }
        //当前ID + 1
        this.maxId += 1;
        return this.maxId;
    }


    public ESDocumentRespEntity getQAPairById(long id) throws IOException {
        String method = "GET";
        String endpoint = String.format("/%s/%s/%d", index, type + 1, id);
        Request request = new Request(method, endpoint);
        Response response = client.performRequest(request);
        String entity = EntityUtils.toString(response.getEntity());
        ESDocumentRespEntity respEntity = JSONObject.parseObject(entity, ESDocumentRespEntity.class);
        QAPair document = respEntity.getDocument(QAPair.class);
        respEntity.setDocument(document);
        return respEntity;
    }


    public boolean addQAPair(QAPair qaPair) throws IOException {
        String method = "POST";
        String endpoint = String.format("/%s/%s/%d", index, type, qaPair.getId());
        Request request = new Request(method, endpoint);
        String body = JSONObject.toJSONString(qaPair);
        request.setJsonEntity(body);
        Response response = client.performRequest(request);
        String rs = EntityUtils.toString(request.getEntity());
        System.out.println(rs);
        return true;
    }

    public String toString() {
        return "QAPairServiceImpl{" +
                "client=" + client +
                ", maxIdRequestBody=" + maxIdRequestBody +
                ", maxId=" + maxId +
                '}';
    }
}
