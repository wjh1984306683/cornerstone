package com.cs.base.common.wechat;

import com.cs.base.common.SpringBootContext;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author wangjiahao
 * @version 1.0
 * @className WechatAccessTokenUtil
 * @since 2019-02-12 09:37
 */
public class WechatAccessTokenUtil {


    private static WechatConfig config;

    static {
        config = SpringBootContext.getBean(WechatConfig.class);
    }

    /**
     * 校验签名
     *
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return 布尔值
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String checktext = null;
        if (null != signature) {
            // 对ToKen,timestamp,nonce 按字典排序
            String[] paramArr = new String[]{config.getToken(), timestamp, nonce};
            Arrays.sort(paramArr);
            // 将排序后的结果拼成一个字符串
            String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                // 对接后的字符串进行sha1加密
                byte[] digest = md.digest(content.getBytes());
                checktext = byteToStr(digest);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        // 将加密后的字符串与signature进行对比
        return checktext != null && checktext.equals(signature.toUpperCase());
    }

    /**
     * 将字节数组转化为16进制字符串
     *
     * @param byteArrays 字符数组
     * @return 字符串
     */
    private static String byteToStr(byte[] byteArrays) {
        StringBuilder str = new StringBuilder();
        for (byte byteArray : byteArrays) {
            str.append(byteToHexStr(byteArray));
        }
        return str.toString();
    }

    /**
     * 将字节转化为十六进制字符串
     *
     * @param myByte 字节
     * @return 字符串
     */
    private static String byteToHexStr(byte myByte) {
        char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tampArr = new char[2];
        tampArr[0] = digit[(myByte >>> 4) & 0X0F];
        tampArr[1] = digit[myByte & 0X0F];
        return new String(tampArr);
    }


}
