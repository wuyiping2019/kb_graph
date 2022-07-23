package com.hk.kb_graph.domain;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@Data
@ToString
public class ReqDo {
    @NotNull
    private String companyName;
    @NotNull
    private String productName;


}
