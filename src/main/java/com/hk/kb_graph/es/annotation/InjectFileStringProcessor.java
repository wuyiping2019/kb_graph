package com.hk.kb_graph.es.annotation;

import com.hk.kb_graph.es.service.QAPairService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;

@Slf4j
@Component
public class InjectFileStringProcessor implements BeanPostProcessor {

    /**
     * 该Bean后处理器 用于处理InjectFileString注解
     * 为被该注解修改的字段注入json字符串
     * */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //找到代理对应的父类 注解在父类上
        Class<?> cls = bean.getClass();
        Field[] fields = cls.getDeclaredFields();
        if(bean instanceof QAPairService){
            System.out.println(bean);
        }
        for (Field field : fields) {
            //如果存在自定义的注入文件内容的注解
            if (field.isAnnotationPresent(InjectFileString.class)) {
                field.setAccessible(true);
                InjectFileString[] annotations = field.getAnnotationsByType(InjectFileString.class);
                //存储读取结果
                StringBuilder sb = new StringBuilder();
                //获取注解的值 读取文件文件并赋值
                for (InjectFileString annotation : annotations) {
                    //获取文件名称
                    String fileName = annotation.fileName();
                    File file = null;
                    BufferedReader br = null;
                    String line;
                    try {
                        file = new ClassPathResource(fileName).getFile();
                        br = new BufferedReader(new FileReader(file));
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        //执行赋值操作
                        field.set(bean, sb.toString());
                        br.close();
                    } catch (IOException | IllegalAccessException e) {
                        if (br != null) {
                            try {
                                br.close();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        log.warn(e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
            }

        }
        return bean;
    }
}
