package com.cs.base.security.support.session;

import com.cs.base.security.properties.BaseProperties;
import com.cs.base.security.properties.LoginType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cs.base.security.properties.BaseSecurityConstants.APPLICATION_JSON_UTF8;
import static com.cs.base.security.support.ErrorStatus.SESSION_EXPIRE;
import static com.cs.base.security.support.GenericResult.build;

/**
 * session处理方式，这里不管是无效session还是过期session，都用同样的方法处理
 *
 * @author wangjiahao
 * @version 1.0
 * @className AbstractSessionStrategy
 * @since 2019-02-04 18:16
 */
@Slf4j
public abstract class AbstractSessionStrategy {

    private DefaultRedirectStrategy strategy = new DefaultRedirectStrategy();
    private ObjectMapper mapper = new ObjectMapper();

    void processSession(BaseProperties properties,
                        HttpServletRequest request,
                        HttpServletResponse response) throws IOException {
        if (properties.getLoginType() == LoginType.JSON) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(APPLICATION_JSON_UTF8);
            response.getWriter().print(mapper.writeValueAsString(build(SESSION_EXPIRE)));
        } else {
            strategy.sendRedirect(request, response, properties.getLoginUrl());
        }
    }

}
