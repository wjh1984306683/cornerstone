package com.cs.base.common.cache.core;

import java.util.Map;
import java.util.Set;

/**
 * @author liyuan
 * @date 2019/2/20 16:06
 */
public interface Resultable<K, V> {

    Map<K, V> result(Map<K, V> foundV, Set<K> notFoundK);
}
