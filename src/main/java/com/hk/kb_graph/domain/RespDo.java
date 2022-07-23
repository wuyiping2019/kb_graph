package com.hk.kb_graph.domain;

import lombok.Data;

import java.util.List;

@Data
public class RespDo {
    private String code;
    private String msg;
    private List<Object> data;

    public static RespDo newInstance(){
        return  new RespDo();
    }

    public  RespDo success(String code, String msg, List<Object> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public RespDo fail(String code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

}
