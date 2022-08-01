package com.hk.kb_graph.controller;

import com.hk.kb_graph.domain.RespDo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class RobotManageController {
    @PostMapping("/robot/login")
    public RespDo login(){
        return RespDo.newInstance().success("200","success",null);
    }
}
