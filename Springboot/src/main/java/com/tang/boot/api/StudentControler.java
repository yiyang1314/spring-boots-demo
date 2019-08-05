package com.tang.boot.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tang.boot.pojo.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
//private static Logger logger = LogManager.getLogger(UserController.class.getName());
@RestController
@RequestMapping("user/api-1.3")
@Api(value = "USER", description = "测试用户所有接口")
public class StudentControler {
	@Autowired
	private User user;
	/**
	 * 新增用户
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "注册用户接口",notes = "注册用户接口")
	@RequestMapping(value="addRole",method = RequestMethod.POST)
	User addUser(User u){
		return user;
		
	}
 
	/**
	 * 根据主键查找用户
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="findById",method = RequestMethod.GET)
	@ApiOperation(value = "主键查找用户",notes = "主键查找用户52")
	User findById(@RequestParam(name="id") long id){
		return user;
		
	}
 
	/**
	 * 删除用户
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delete",method = RequestMethod.DELETE)
	@ApiOperation(value = "查询删除接口",notes = "查询删除用户接255口")
//	@ApiImplicitParams({
//    	@ApiImplicitParam(paramType = "header", dataType="string", name = "token", value = "访问凭证", required = true),
//	}) 
	User delete(@RequestParam(name="id") long id){
		return user;
		
	}
 
	/**
	 * 更新用户
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "更新用户接口",notes = "更新用户接255口")
	@RequestMapping(value="updateRole",method = RequestMethod.PUT )
	User updateRole(@RequestBody User user){
		return user;
		
	}
}