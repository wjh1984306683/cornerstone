package com.cs.cms.entity;

import com.cs.cms.support.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * @author wangjiahao
 * @version 1.0
 * @className Permission
 * @since 2019-02-27 14:39
 */
@Data
@Entity(name = "sys_permission")
public class Permission extends BaseEntity {

    private String name;
    private String code;
    private String url;
    private Byte status;

    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    private Set<Role> roles;

    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    private Set<User> users;

}
