package com.cs.micro.eureka.customer.entity;

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
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * @author wangjiahao
 * @version 1.0
 * @className Customer
 * @since 2019-03-07 16:15
 */
@Data
@ApiModel("客户")
@Entity(name = "customer_info")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Byte gender;
    private BigDecimal money;
    private int age;
    private Byte delFlag;
    @JsonSerialize(using = OffsetDateTimeJsonSerialize.class)
    @JsonDeserialize(using = OffsetDateTimeJsonDeserialize.class)
    private OffsetDateTime createDate;

}
