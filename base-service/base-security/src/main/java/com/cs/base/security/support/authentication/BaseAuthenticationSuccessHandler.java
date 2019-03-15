package com.cs.base.security.support.authentication;

import com.cs.base.security.properties.BaseProperties;
import com.cs.base.security.properties.LoginType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cs.base.security.properties.BaseSecurityConstants.APPLICATION_JSON_UTF8;

/**
 * @author SYSTEM
 */
@Slf4j
public class BaseAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private ObjectMapper mapper = new ObjectMapper();

    private BaseProperties properties;

    public BaseAuthenticationSuccessHandler(BaseProperties properties) {
        this.properties = properties;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        log.info("系统提示：用户 {} 登录成功！", user.getUsername());

        if (LoginType.JSON.equals(properties.getLoginType())) {
            response.setContentType(APPLICATION_JSON_UTF8);
//            Todo:成功提示，打印用户登录信息,目前暂时的做法，根据业务可修改或重写此方法
            response.getWriter().write(mapper.writeValueAsString(authentication));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
