package com.cs.base.common.wechat;

import com.cs.base.common.SpringBootContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.cs.base.common.wechat.WechatUrl.GRANT_URL;
import static com.cs.base.common.wechat.WechatUrl.OPENID_URL;
import static com.cs.base.common.wechat.WechatUrl.USERINFO_URL;
import static org.apache.commons.codec.CharEncoding.UTF_8;

/**
 * 微信web开发授权Utils
 *
 * @author wangjiahao
 * @version 1.0
 * @date 2018年11月13日
 */
@Slf4j
public class WechatAuthorizeUtils {

    private static WechatConfig config;

    static {
        config = SpringBootContext.getBean(WechatConfig.class);
    }

    /**
     * 获取授权访问路径
     *
     * @param url       访问地址
     * @param grantType 授权类型
     * @param state     需要微信携带的参数，可选
     * @return
     */
    public static String getGrantUrl(String url, String grantType, String state) {
        state = StringUtils.isEmpty(state) ? "STATE" : state;
        try {
            return String.format(GRANT_URL, config.getAppId(), URLEncoder.encode(url, UTF_8), grantType, state);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取微信OPENID
     *
     * @param code
     * @return
     * @throws IOException
     */
    public static WechatOpenIdResponse getOpenID(String code) throws IOException {
        String url = String.format(OPENID_URL, config.getAppId(), config.getAppSecret(), code);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(new HttpPost(url));

        String json = EntityUtils.toString(response.getEntity());
        return new ObjectMapper().readValue(json, WechatOpenIdResponse.class);
    }

    /**
     * 获取用户信息
     *
     * @param accessCode
     * @param openID
     * @return
     * @throws IOException
     */
    public static WechatUserInfoResponse getUserInfo(String accessCode, String openID) throws IOException {
        String url = String.format(USERINFO_URL, accessCode, openID);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(new HttpPost(url));

        String json = EntityUtils.toString(response.getEntity(), "UTF-8");
        return new ObjectMapper().readValue(json, WechatUserInfoResponse.class);
    }
}
