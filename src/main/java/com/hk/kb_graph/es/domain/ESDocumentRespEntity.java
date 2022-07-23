package com.hk.kb_graph.es.domain;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;


@Data
public class ESDocumentRespEntity extends ESRespDo {
    private String _index;
    private String _type;
    private Long _id;
    private Integer _version;
    private Boolean found;
    private String _source;
    private Object document;

    public <T> T getDocument(Class<T> clazz) {
        T t = (T) JSONObject.parseObject(_source, clazz);
        return t;
    }
}
