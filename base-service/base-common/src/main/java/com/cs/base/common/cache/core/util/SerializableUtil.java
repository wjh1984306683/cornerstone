package com.cs.base.common.cache.core.util;

import com.cs.base.common.cache.core.io.ISerializable;
import com.cs.base.common.cache.core.io.JavaSerializer;

import java.io.IOException;

/**
 * @author liyuan
 * @date 2019/2/20 15:34
 */
public class SerializableUtil {
    /**
     * 默认是java序列化
     */
    private static ISerializable javaSerializer = new JavaSerializer();

    public static byte[] serialize(Object obj) throws IOException {
        return javaSerializer.serialize(obj);
    }

    public static Object deserialize(byte[] bytes) throws IOException {
        return javaSerializer.deserialize(bytes);
    }
}
