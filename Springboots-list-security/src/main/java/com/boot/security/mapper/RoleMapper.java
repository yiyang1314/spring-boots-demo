package com.boot.security.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.boot.security.pojo.SysRole;
import com.boot.security.pojo.SysUser;

public interface RoleMapper extends JpaRepository<SysRole, Long> {
	
	@Query(value="SELECT DISTINCT perm_id FROM sys_role_permission_map where role_id=?1 " ,nativeQuery=true)
	List<Long> findPermiss(String role_ids);
}
