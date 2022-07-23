//package com.hk.kb_graph.es;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ESConfiguration {
//    @Value("${es.host}")
//    private String host;
//    @Value("${es.port}")
//    private int port;
//    @Bean
//    public RestClient getRestHighLevelClient(){
//        return RestClient.builder(new HttpHost(host, port)).build();
//    }
//}
