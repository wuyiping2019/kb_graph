package com.hk.kb_graph.es.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class QAPair {
    private BigDecimal id;
    private String question;
    private String answer;
    private String tenantName;
    private Date createTime;
}
