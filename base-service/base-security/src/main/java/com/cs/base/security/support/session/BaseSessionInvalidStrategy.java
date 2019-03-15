package com.cs.base.security.support.session;

import com.cs.base.security.properties.BaseProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangjiahao
 * @version 1.0
 * @className BaseSessionInvalidStrategy
 * @since 2019-02-04 17:58
 */
@Slf4j
public class BaseSessionInvalidStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

    private BaseProperties properties;

    public BaseSessionInvalidStrategy(BaseProperties properties) {
        this.properties = properties;
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        log.debug("session无效");
        processSession(properties, request, response);
    }
}
