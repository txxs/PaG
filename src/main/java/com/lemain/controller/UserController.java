package com.lemain.controller;

import com.lemain.domain.User;
import com.lemain.iservice.IUserService;
import com.lemain.util.HttpClientUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * Created by jianghuimin on 2017/1/18.
 */
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    private static final Logger log = getLogger(UserController.class);

    private String serverGetUrl = "http://localhost:8080/serverGet";

    private String serverPostUrl = "http://localhost:8080/serverPost";

    @RequestMapping(value = "/getInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String handGet(@PathVariable final int id){
        log.debug("postman进入FrontController的get,参数是{}",id);
        User user = new User();
        user = userService.findUserById(id);
        return user.toString();
    }

    @RequestMapping(value = "/postInfo", method = RequestMethod.POST)
    @ResponseBody
    public String handlPost(@RequestParam String name,@RequestBody User info)throws Exception{
        log.debug("postman进入FrontControllerd的post，参数是{}和{}",name,info);
        final JSONObject jsonObject = JSONObject.fromObject(info);
        String result= HttpClientUtil.post(serverPostUrl,null,null,null,1000);
        return result;
    }
}
