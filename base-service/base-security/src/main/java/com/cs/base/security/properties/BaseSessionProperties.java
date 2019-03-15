package com.cs.base.security.properties;

import lombok.Data;

/**
 * @author wangjiahao
 * @version 1.0
 * @className BaseSessionProperties
 * @since 2019-02-12 13:51
 */
@Data
public class BaseSessionProperties {

    private Boolean enable = true;
    private Integer maxNum = 2;
    private Boolean preventsLogin = false;

}
