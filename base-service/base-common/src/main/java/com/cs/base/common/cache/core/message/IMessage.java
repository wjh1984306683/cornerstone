package com.cs.base.common.cache.core.message;


import com.cs.base.common.cache.core.ICache;

/**
 * 消息接口
 *
 * @author liyuan
 * @date 2019/2/20 15:22
 */
public interface IMessage {

    void setSyncCache(ICache cache);

    void sendMsg(Command cmd);
}
