package com.boot.security.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.boot.security.pojo.SysPermission;


public interface PerminsionMapper extends JpaRepository<SysPermission, Long> {
	   // SysUser findByUserName(String userName);
	public SysPermission findByPermId(Long permId);
	
	@Query(value="SELECT DISTINCT code FROM sys_permission where perm_id IN?1 " ,nativeQuery=true)
	List<String> findPermiss(String perm_ids);
	}
