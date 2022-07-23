package com.hk.kb_graph.es.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESConfiguration {
    @Value("${es.host}")
    private String host;
    @Value("${es.port}")
    private int port;

    /**
     * 获取RestClient客户端对象
     * 操作es
     * @Return:RestClient
     * */
    @Bean
    public RestClient getClien(){
        return RestClient.builder(new HttpHost(this.host, this.port)).build();
    }
}
