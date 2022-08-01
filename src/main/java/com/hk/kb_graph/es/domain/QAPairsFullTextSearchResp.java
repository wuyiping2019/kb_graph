package com.hk.kb_graph.es.domain;

import lombok.Data;

@Data
public class QAPairsFullTextSearchResp extends FullTextSearchResp {
    private QAPairHits hits;
}
