package com.cs.base.common.cache.core.io;

import java.io.IOException;

/**
 * @author liyuan
 * @date 2019/2/20 15:20
 */
public interface ISerializable {
    String name();

    byte[] serialize(Object obj) throws IOException;

    Object deserialize(byte[] bytes) throws IOException;
}
