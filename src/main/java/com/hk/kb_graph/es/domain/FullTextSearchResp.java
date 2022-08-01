package com.hk.kb_graph.es.domain;

import lombok.Data;

@Data
public class FullTextSearchResp {
    private String took;
    private Boolean timed_out;
    private _Shards _shards;
}

