package com.hk.kb_graph.exception;

import com.hk.kb_graph.domain.RespDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice(basePackages = "com.hk.kb_graph.controller")
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RespDo respDo(HttpMessageNotReadableException e) {
        return RespDo.newInstance().fail("400", e.getMessage());
    }

}
