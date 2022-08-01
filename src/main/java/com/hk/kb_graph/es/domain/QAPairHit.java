package com.hk.kb_graph.es.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class QAPairHit {
    private String _index;
    private String _type;
    private String _id;
    private QAPair _source;

}
