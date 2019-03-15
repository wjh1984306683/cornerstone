package com.cs.micro.oauth2.entity;

import com.cs.micro.oauth2.support.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.Set;

/**
 * @author wangjiahao
 * @version 1.0
 * @className Permission
 * @since 2019-02-27 14:39
 */
@Data
@Entity(name = "sys_permission")
public class Permission extends BaseEntity implements Serializable {

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
