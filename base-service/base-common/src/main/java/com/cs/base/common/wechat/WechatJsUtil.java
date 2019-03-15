package com.cs.base.common.wechat;

import com.alibaba.fastjson.JSONObject;
import com.cs.base.common.SpringBootContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import static com.cs.base.common.wechat.WechatUrl.SERVER_TOKEN_URL;

/**
 * 微信获取js_api_ticket工具
 * 需要自行实现缓存
 *
 * @author wangjiahao
 * @version 1.0
 * @className WechatJsUtil
 * @since 2019-02-14 10:35
 */
@Slf4j
public class WechatJsUtil {

    private static CacheClient cacheClient;
    private static WechatConfig config;

    static {
        cacheClient = SpringBootContext.getBean(CacheClient.class);
        config = SpringBootContext.getBean(WechatConfig.class);
    }

    /**
     * 获取ticket（缓存或重新请求获取）
     *
     * @return
     */
    public static String getTicket() {
        String redisKey = "wx_js_ticket";
        Object ticket = cacheClient.get(redisKey);
        if (ticket == null || StringUtils.isEmpty(ticket)) {
            ticket = requestTicket();
            cacheClient.set(redisKey, ticket, 7198);
        }
        log.debug("========&&&ticket = " + ticket + "&&&============");
        return ticket.toString();
    }

    /**
     * 向服务器请求ticket
     *
     * @return
     */
    private static String requestTicket() {
        String redisKey = "wx_js_access_token";
        Object token = cacheClient.get(redisKey);
        String accessToken;
        if (StringUtils.isEmpty(token)) {
            accessToken = requestAccessToken();
            cacheClient.set(redisKey, accessToken, 7198);
        } else {
            accessToken = (String) token;
        }
        String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
        return sendGetRequest(ticketUrl).getString("ticket");
    }

    /**
     * 向服务器请求access_token
     *
     * @return
     */
    public static String requestAccessToken() {
        String url = SERVER_TOKEN_URL + "&APP_ID=" + config.getAppId() + "&secret=" + config.getAppSecret();
        JSONObject json = sendGetRequest(url);
        return json.getString("access_token");
    }

    /**
     * 发送GET请求 并获取返回JSON对象
     *
     * @param url
     * @return
     */
    public static JSONObject sendGetRequest(String url) {
        HttpGet method = new HttpGet(url);
        JSONObject jsonObject = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            CloseableHttpResponse result = client.execute(method);
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str;
                str = EntityUtils.toString(result.getEntity());
                jsonObject = JSONObject.parseObject(str);
            }
        } catch (Exception e) {
            log.error("get请求提交失败:" + url);
        }
        return jsonObject;
    }
}

