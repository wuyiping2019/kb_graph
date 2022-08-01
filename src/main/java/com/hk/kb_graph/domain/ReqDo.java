package com.hk.kb_graph.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class ReqDo {
    @NotNull
    private String companyName;
    @NotNull
    private String productName;


}
