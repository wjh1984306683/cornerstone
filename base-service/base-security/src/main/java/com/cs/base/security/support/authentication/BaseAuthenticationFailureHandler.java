package com.cs.base.security.support.authentication;

import com.cs.base.security.properties.BaseProperties;
import com.cs.base.security.properties.LoginType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cs.base.security.properties.BaseSecurityConstants.APPLICATION_JSON_UTF8;
import static com.cs.base.security.properties.BaseSecurityConstants.EXCEPTION_CODE;
import static com.cs.base.security.support.GenericResult.build;

/**
 * @author SYSTEM
 */
@Slf4j
public class BaseAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private ObjectMapper mapper = new ObjectMapper();

    private BaseProperties properties;

    public BaseAuthenticationFailureHandler(BaseProperties baseProperties) {
        this.properties = baseProperties;
    }


    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        log.info("系统提示：登录失败！{}", e.getMessage());
        if (LoginType.JSON.equals(properties.getLoginType())) {

            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(APPLICATION_JSON_UTF8);
            response.getWriter().write(mapper.writeValueAsString(build(EXCEPTION_CODE, e.getMessage(), null)));
        } else {
            super.onAuthenticationFailure(request, response, e);
        }
    }
}
