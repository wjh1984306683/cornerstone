package com.cs.base.security.web.system;

import com.cs.base.security.properties.BaseProperties;
import com.cs.base.security.properties.BaseSecurityConstants;
import com.cs.base.security.support.GenericResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cs.base.security.properties.BaseSecurityConstants.HTML_SUFFIX;
import static com.cs.base.security.support.ErrorStatus.LOGIN_REQUIRED;
import static com.cs.base.security.support.GenericResult.build;

/**
 * @author wangjiahao
 * @version 1.0
 * @className SecurityCtrl
 * @since 2019-02-07 14:18
 */
@Slf4j
@RestController
public class SecurityCtrl {

    @Autowired
    private BaseProperties properties;

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy strategy = new DefaultRedirectStrategy();

    /**
     * 需要身份认证时，跳转到这里
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping(BaseSecurityConstants.AUTHENTICATION)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public GenericResult requireAuthentication(HttpServletRequest request,
                                               HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String target = savedRequest.getRedirectUrl();
            if (StringUtils.endsWithIgnoreCase(target, HTML_SUFFIX)) {
                strategy.sendRedirect(request, response, properties.getLoginUrl());
                return null;
            }
        }
        return build(LOGIN_REQUIRED);
    }
}
