package com.cs.micro.eureka.store.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author wangjiahao
 * @version 1.0
 * @className IndexCtrl
 * @since 2019-02-27 10:30
 */
@Controller
@Api(tags = "国际化页面Controller")
public class IndexCtrl {


    @GetMapping("/index")
    @ApiOperation("跳到index页面")
    public String index(Model model) {
        return "index";
    }

}
