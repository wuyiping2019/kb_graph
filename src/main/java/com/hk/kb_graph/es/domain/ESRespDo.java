package com.hk.kb_graph.es.domain;

import lombok.Data;

/**
 * ES对外输出的统一对象
 * @msg:封装自定义消息
 * @code:封装自定义code 也可以使用es返回的code
 * @data:封装数据 无论是否异常都会有数据返回
 * */
@Data
public class ESRespDo {
    private String msg;
    private int code;
    private Object data;
}
