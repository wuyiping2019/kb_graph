package com.hk.kb_graph.es.domain;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;


/**
 * 该类用于封装根据id查询document的结果 因此仅封装了一个document
 * */
@Data
public class ESDocumentRespEntity {
    /**
     * 当查询的type或id不存在时,会封装_index、_type、_id、found字段
     * 正常查询结果会封装进_index、_type、_id、_version、found、_source、document字段
     * */
    private String _index;
    private String _type;
    private Long _id;
    private Integer _version;
    private Boolean found;
    private String _source;
    private Object document;

    /**
     * 当查询的index时 报错信息会封装进这两个字段
     * */
    private String error;
    private int status;

    public <T> T getDocument(Class<T> clazz) {
        T t = (T) JSONObject.parseObject(_source, clazz);
        return t;
    }
}
