package com.cs.micro.eureka.store.entity;

import com.cs.base.common.serialize.OffsetDateTimeJsonDeserialize;
import com.cs.base.common.serialize.OffsetDateTimeJsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
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
@Entity(name = "store_sku")
public class SkuEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private BigDecimal price;
    private int num;

    @JsonDeserialize(using = OffsetDateTimeJsonDeserialize.class)
    @JsonSerialize(using = OffsetDateTimeJsonSerialize.class)
    private OffsetDateTime createDate;
    private int sort = 10;

}
