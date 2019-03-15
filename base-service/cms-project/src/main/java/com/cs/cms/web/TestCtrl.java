package com.cs.cms.web;

import com.cs.cms.entity.User;
import com.cs.cms.util.AdminUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiahao
 * @version 1.0
 * @className TestCtrl
 * @since 2019-02-19 09:01
 */
@Slf4j
@RestController
public class TestCtrl {

    @GetMapping("/html")
    public String test2() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">" +
                "<h1>" +
                "测试" +
                "</h1>" +
                "</html>";
    }

    @GetMapping("/user")
    public String userInfo() throws JsonProcessingException {
        User user = AdminUtils.getCurrentUser();
        return new ObjectMapper().writeValueAsString(user);
    }
}
