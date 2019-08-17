package com.boot.security.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "sys_permission")
public class SysPermission{

    @Id
    @Column(name = "perm_id")
    private Long permId;

    @Column(length = 30)
    private String code;
}
