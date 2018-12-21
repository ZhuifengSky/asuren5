package cn.service.modules.user.service;

import cn.service.modules.user.bean.UserBean;
import cn.service.modules.user.model.User;

public interface IUserService {

	/**
	 * 用户邮箱注册
	 * @param user
	 * @return 
	 */
	public User regByEmail(User user) throws  Exception;
	
	
	/**
	 * 用户登录检查
	 * @param loginName
	 * @return
	 */
	public User loginCheck(String loginName);
	
	/**
	 * 用户注册检查
	 * @param type
	 * @param str
	 * @return
	 */
	public boolean regCheck(String type,String str);
	
	/**
	 * 用户信息更新
	 * @param user
	 * @return
	 */
	public int updateUser(User user);
	
	/**
	 * 根据ID查询用户
	 * @param userId
	 * @return
	 */
	public User getUserById(Integer userId);


	/**
	 * 用户token检查
	 * @param token
	 * @return
	 */
	public String tokencheck(String token);
	
	/**
	 * 获取当前登录用户
	 * @param token
	 * @return
	 */
	public UserBean getCurrentUser(String token);
}
