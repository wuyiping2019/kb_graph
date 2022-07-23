package com.hk.kb_graph.es.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class QAPair {
    private BigDecimal id;
    private String question;
    private String answer;
    private String tenantName;
    private Date createTime;

}
