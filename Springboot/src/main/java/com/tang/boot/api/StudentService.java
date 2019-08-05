package com.tang.boot.api;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tang.boot.pojo.User;

@RequestMapping("user/api-1.3")
@RestController
public interface StudentService {
	 
		/**
		 * 新增用户
		 * @param role
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="addRole",method = RequestMethod.POST)
		User addUser() throws Exception;
	 
		/**
		 * 根据主键查找用户
		 * @param id
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="findById",method = RequestMethod.GET)
		User findById(@RequestParam(name="id") long id) throws Exception;
	 
		/**
		 * 删除用户
		 * @param id
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="delete",method = RequestMethod.DELETE)
		User delete(@RequestParam(name="id") long id) throws Exception;
	 
		/**
		 * 更新用户
		 * @param role
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="updateRole",method = RequestMethod.PUT)
		User updateRole(@RequestBody User user) throws Exception;
		

}
