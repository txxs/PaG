package com.lemain.controller;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by jianghuimin on 2017/1/19.
 * 把这个controller当做服务器内部调转的处理，模拟http请求处理和返回
 */
@RestController
public class ServerController {

    private static final Logger log = getLogger(ServerController.class);

    @RequestMapping(value="/serverPost",method = RequestMethod.POST)
    @ResponseBody
    public String serverPost(){
        log.info("进入ServerController服务器");
        return "模拟服务器";
    }
}
