package com.cs.base.common.cache.jedis;


import com.cs.base.common.cache.core.ICache;
import com.cs.base.common.cache.core.message.Command;
import com.cs.base.common.cache.core.message.IMessage;

/**
 * @author lijingwen
 * @date 2019/2/21 9:45
 */
public class JedisMessage implements IMessage {


    private JedisCache jedisCache;

    private ICache cache;

    public JedisMessage(JedisCache jedisCache) {
        this.jedisCache = jedisCache;
    }

    @Override
    public void setSyncCache(ICache cache) {
        this.cache = cache;
    }

    @Override
    public void sendMsg(Command cmd) {

    }
}
