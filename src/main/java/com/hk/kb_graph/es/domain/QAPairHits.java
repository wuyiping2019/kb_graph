package com.hk.kb_graph.es.domain;

import lombok.Data;

import java.util.List;

@Data
public class QAPairHits {
    Long total;
    Float max_score;
    List<QAPairHit> hits;
}
