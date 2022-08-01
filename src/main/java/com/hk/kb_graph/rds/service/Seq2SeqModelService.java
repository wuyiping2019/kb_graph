package com.hk.kb_graph.rds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Seq2SeqModelService {
    private static final String url = "http://localhost:5000/chat/%s";
    @Autowired
    RestTemplateBuilder restTemplateBuilder;
    public String response(String inputStr){
        RestTemplate build = restTemplateBuilder.build();
        String format = String.format(url, inputStr);
        String reply = build.getForObject(format, String.class);
        return reply;
    }
}
