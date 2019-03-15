package com.cs.base.common.cache.core.io;

import java.io.IOException;

/**
 * @author liyuan
 * @date 2019/2/20 16:13
 */
public class HessianSerializer implements ISerializable {
    @Override
    public String name() {
        return "hessian";
    }

    @Override
    public byte[] serialize(Object obj) throws IOException {
        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes) throws IOException {
        return null;
    }
}
