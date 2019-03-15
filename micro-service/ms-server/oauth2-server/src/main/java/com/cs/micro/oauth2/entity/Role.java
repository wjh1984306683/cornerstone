package com.cs.micro.oauth2.entity;

import com.cs.micro.oauth2.support.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.Set;

/**
 * @author wangjiahao
 * @version 1.0
 * @className Role
 * @since 2019-02-27 14:38
 */
@Data
@Entity(name = "sys_role")
public class Role extends BaseEntity implements Serializable {

    private String name;
    private String code;
    private Byte status;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Permission> permissions;

}
