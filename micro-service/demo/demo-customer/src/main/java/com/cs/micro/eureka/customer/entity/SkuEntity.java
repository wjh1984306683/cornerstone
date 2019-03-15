package com.cs.micro.eureka.customer.entity;

import com.cs.base.common.serialize.OffsetDateTimeJsonDeserialize;
import com.cs.base.common.serialize.OffsetDateTimeJsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * @author wangjiahao
 * @version 1.0
 * @className SkuEntity
 * @since 2019-03-07 14:14
 */
@Data
@ApiModel(value = "商品")
public class SkuEntity {

    private int id;
    private String name;
    private BigDecimal price;
    private int num;

    @JsonDeserialize(using = OffsetDateTimeJsonDeserialize.class)
    @JsonSerialize(using = OffsetDateTimeJsonSerialize.class)
    private OffsetDateTime createDate;
    private int sort = 10;

}
