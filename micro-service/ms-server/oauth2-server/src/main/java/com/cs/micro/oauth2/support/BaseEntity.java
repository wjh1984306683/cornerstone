package com.cs.micro.oauth2.support;

import com.cs.base.common.serialize.OffsetDateTimeJsonDeserialize;
import com.cs.base.common.serialize.OffsetDateTimeJsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.OffsetDateTime;

/**
 * @author wangjiahao
 * @version 1.0
 * @className BaseEntity
 * @since 2019-02-27 14:23
 */
@Data
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * 主键策略，MySQL的自增策略要选择IDENTITY，不写则无策略
     */
    @Id
    @GenericGenerator(strategy = "uuid", name = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @JsonSerialize(using = OffsetDateTimeJsonSerialize.class)
    @JsonDeserialize(using = OffsetDateTimeJsonDeserialize.class)
    private OffsetDateTime createDate;

    @JsonSerialize(using = OffsetDateTimeJsonSerialize.class)
    @JsonDeserialize(using = OffsetDateTimeJsonDeserialize.class)
    private OffsetDateTime updateDate;
    private String createBy;
    private String updateBy;
    @Column(nullable = false)
    private Byte delFlag = 0;

    public void preInsert() {
        this.createDate = OffsetDateTime.now();
        this.createBy = AdminUtils.getCurrentUser().getId();
    }

    public void preUpdate() {
        this.updateDate = OffsetDateTime.now();
        this.updateBy = AdminUtils.getCurrentUser().getId();
    }
}
