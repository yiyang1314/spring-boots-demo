package com.boot.security.pojo;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
@Data
@Entity
@Table(name = "sys_role")
public class SysRole {

    @Id
    @Column(name = "role_id")
    private Long roleId;

    @Column(nullable = false, length =  200)
    private String roleName;

    @ManyToMany(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinTable(name="sys_role_permission_map",joinColumns={@JoinColumn(name="role_id")},inverseJoinColumns={@JoinColumn(name="perm_id")})
    List<SysPermission> permissionList;

}
