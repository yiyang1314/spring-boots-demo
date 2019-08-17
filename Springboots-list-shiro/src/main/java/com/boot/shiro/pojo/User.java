package com.boot.shiro.pojo;

import javax.persistence.*;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Slf4j
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	    @GeneratedValue
    private Integer id;
	    @Column(unique =true)
    private String username;//帐号
    private String name;//名称（昵称或者真实姓名，不同系统不同定义）
    private String password; //密码;
    private String salt;//加密密码的盐
    private byte state;//用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.
    //	@ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
    //	@JoinTable(name = "SysUserRole", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns ={@JoinColumn(name = "roleId") })
    
    //private List<SystemRole> roleList;// 一个用户具有多个角色

   
    /**
     * 密码盐.
     * @return
     */
    public String getCredentialsSalt(){
    	log.info("--------------------加盐--------------------------------");
    	log.info("加盐值username: "+this.username);
    	log.info("加盐值salt: "+this.salt);
    	log.info("--------------------加盐结束--------------------------------");
    	log.info("加盐值salt: "+(this.username+this.salt));
    	log.info("--------------------结束--------------------------------");
        return this.username+this.salt;
     }
    //重新对盐重新进行了定义，用户名+salt，这样就更加不容易被破解
}
