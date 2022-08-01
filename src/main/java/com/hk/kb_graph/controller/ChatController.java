package com.hk.kb_graph.controller;

import com.hk.kb_graph.domain.RespDo;
import com.hk.kb_graph.es.domain.QAPairHit;
import com.hk.kb_graph.es.domain.QAPairsFullTextSearchResp;
import com.hk.kb_graph.es.service.QAPairSearchByFullTextService;
import com.hk.kb_graph.rds.service.Seq2SeqModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
public class ChatController {
    @Autowired
    private Seq2SeqModelService seq2SeqModelService;
    @Autowired
    private QAPairSearchByFullTextService qaPairSearchByFullTextService;
    @GetMapping("/chat/{inputStr}")
    public RespDo chat(@PathVariable("inputStr") String inputStr) throws IOException {
        List<Object> rs = new ArrayList<>();

        //读取ES中存储的答案
        QAPairsFullTextSearchResp qaPairsFullTextSearchResp = qaPairSearchByFullTextService.searchQAPairs(inputStr);
        List<QAPairHit> hits = qaPairsFullTextSearchResp.getHits().getHits();
        if(hits.size() != 0){
            String answer = hits.get(0).get_source().getAnswer();
            rs.add(answer);
            return RespDo.newInstance().success("200","success",rs);
        }
        return null;
    }
}
