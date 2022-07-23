package com.hk.kb_graph.es.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QAPair {
    private String question;
    private String answer;
    private String tenantName;

}
