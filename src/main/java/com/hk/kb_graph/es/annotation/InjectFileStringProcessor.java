package com.hk.kb_graph.es.annotation;

import org.springframework.beans.BeansException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class FileStringInjectProcessor implements BeanPostProcessor {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> cls = bean.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field filed:fields){
            if(filed.)

        }
    }
}
