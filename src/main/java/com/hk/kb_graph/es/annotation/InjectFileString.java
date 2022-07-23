package com.hk.kb_graph.es.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface InjectFileString {
    /**
     * 自定义注解用于在对象中自动注入json字符串
     * 注入字符串的原则是：在类路径下寻找指定fileName的文件 并将其字符串注入到该字段
     * */
    String fileName();

}
