package com.boot.security.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.boot.security.pojo.SysUser;

@Repository
public interface UserMapper extends JpaRepository<SysUser, Long> {
    SysUser findByUserName(String userName);
    
	
	@Query(value="SELECT DISTINCT  role_id FROM sys_user_role_map WHERE user_id=?1 " ,nativeQuery=true)
	List<Long> findRoles(Long user_id);
}

