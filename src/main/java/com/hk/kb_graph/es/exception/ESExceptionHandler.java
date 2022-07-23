package com.hk.kb_graph.es.exception;

import com.alibaba.fastjson2.JSONObject;
import com.hk.kb_graph.es.domain.ESDocumentRespEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Aspect
@Slf4j
public class ESExceptionHandler {
    @Pointcut("execution(* com.hk.kb_graph.es.service.QAPairService.getQAPairById(..))")
    public void pointCut() {
    }

    @Around(value = "pointCut()")
    public ESDocumentRespEntity afterThrowing(ProceedingJoinPoint proceedingJoinPoint) throws IOException {
        ESDocumentRespEntity o = null;
        try {
            o = (ESDocumentRespEntity)proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            if (e instanceof ResponseException) {
                ResponseException re = (ResponseException) e;
                Response response = re.getResponse();
                String rs = EntityUtils.toString(response.getEntity());
                ESDocumentRespEntity resp = JSONObject.parseObject(rs, ESDocumentRespEntity.class);
                System.out.println(e);
                return resp;
            }
        }
        return  o;

    }
}
