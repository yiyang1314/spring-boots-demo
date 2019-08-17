package com.boot.security.pojo;

import java.util.List;

import javax.persistence.*;


import lombok.Data;

@Data
@Entity
@Table(name = "sys_user")
public class SysUser {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false,unique = true, length =  50)
    private String userName;

    @Column(nullable = false, length =  200)
    private String password;

    @ManyToMany(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinTable(name="sys_user_role_map",joinColumns={@JoinColumn(name="user_id")},inverseJoinColumns={@JoinColumn(name="role_id")})
    List<SysRole> roleList;
}
