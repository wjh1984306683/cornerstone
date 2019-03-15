package com.cs.base.security.support.session;

import com.cs.base.security.properties.BaseProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangjiahao
 * @version 1.0
 * @className BaseSessionExpiredStrategy
 * @since 2019-02-04 17:52
 */
@Slf4j
public class BaseSessionExpiredStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    private BaseProperties properties;

    public BaseSessionExpiredStrategy(BaseProperties properties) {
        this.properties = properties;
    }

    /**
     * 跳转到登录页面
     *
     * @param event
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event)
            throws IOException, ServletException {
        log.debug("session过期");
        HttpServletRequest request = event.getRequest();
        HttpServletResponse response = event.getResponse();
        processSession(properties, request, response);
    }
}
