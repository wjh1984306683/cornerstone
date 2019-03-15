package com.cs.micro.oauth2.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author wangjiahao
 * @version 1.0
 * @className UserCtrl
 * @since 2019-03-01 11:48
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserCtrl {

    @GetMapping("/current")
    public Principal userInfo(Principal principal) {
        log.info(principal.toString());
        return principal;
    }

}
