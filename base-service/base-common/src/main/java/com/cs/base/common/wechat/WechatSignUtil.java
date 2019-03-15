package com.cs.base.common.wechat;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 微信获取签名的方法
 *
 * @author wangjiahao
 */
@Slf4j
public class WechatSignUtil {

    public static void main(String[] args) {
        String jsapiTicket = "jsapi_ticket";
        String appid = "asdasdad";
        // 注意 URL 一定要动态获取，不能 hardcode
        String url = "http://example.com";
        Map<String, Object> ret = sign(jsapiTicket, appid, url);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }


    public static Map<String, Object> sign(String jsapiTicket, String appID, String url) {
        Map<String, Object> ret = new HashMap<>(6);
        String nonceStr = createNonceStr();
        String timestamp = createTimestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        log.debug("参数排列:{}", string1);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("appId", appID);
        ret.put("jsapi_ticket", jsapiTicket);
        ret.put("noncestr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        log.debug("ret = " + ret.toString());
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String createNonceStr() {
        return UUID.randomUUID().toString();
    }

    /**
     * 微信的时间戳用秒表示
     *
     * @return
     */
    private static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
