package cn.service.modules.user.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.service.common.util.Encryption;
import cn.service.common.util.Global;
import cn.service.common.util.JedisUtils;
import cn.service.common.util.JsonUtil;
import cn.service.common.util.NullJudgeUtil;
import cn.service.common.util.ReturnUtil;
import cn.service.common.util.StringUtil;
import cn.service.modules.category.dao.CategoryDao;
import cn.service.modules.category.model.Category;
import cn.service.modules.user.bean.UserBean;
import cn.service.modules.user.dao.UserDao;
import cn.service.modules.user.model.User;
import cn.service.modules.user.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CategoryDao categoryDao;

	
	public User regByEmail(User user) throws Exception{
		setDefaultInfo(user);
		userDao.insertSelective(user);
		Category category = geneCategory(user);
		category.setCategoryName(Global.getDefaultCategory1());
		categoryDao.insert(category);
		Category category2 = geneCategory(user);
		category2.setCategoryName(Global.getDefaultCategory2());
		categoryDao.insert(category2);
		return user;
	}
	
	
	private Category geneCategory(User user){
		Category category = new Category();
		category.setUserId(user.getId());
		category.setCreateDate(new Date());
		category.setUpdateDate(new Date());
		return category;
	}
	
	
	public User loginCheck(String loginName) {
		User queyBean = new User();
		List<User> users = null;
		queyBean.setUserName(loginName);
		users = userDao.findUser(queyBean);
		if (users.isEmpty()) {
			queyBean.setUserName(null);
			queyBean.setMobile(loginName);
			users = userDao.findUser(queyBean);
		}
		if (users.isEmpty()) {
			queyBean.setMobile(null);
			queyBean.setEmail(loginName);
			users = userDao.findUser(queyBean);
		}
		if (!users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}

	public int updateUser(User user) {
		return userDao.updateByPrimaryKeySelective(user);
	}


	public boolean regCheck(String type, String str) {
		User queyBean = new User();
		List<User> users = null;
		if (type.equals("userName")) {			
			queyBean.setUserName(str);
			users = userDao.findUser(queyBean);
		}
		if (type.equals("email")) {			
			queyBean.setEmail(str);
			users = userDao.findUser(queyBean);
		}
		if (type.equals("mobile")) {			
			queyBean.setMobile(str);
			users = userDao.findUser(queyBean);
		}
		if (users.isEmpty()) {
			return true;
		}
		return false;
	}

	
	/**
	 * 补充信息
	 * @param user
	 */
	private void setDefaultInfo(User user){
		if (NullJudgeUtil.isNull(user.getGender())) {
			user.setGender("secret");
		}
		if (NullJudgeUtil.isNull(user.getAvatar())) {
			user.setAvatar(Global.getDefaultAvator());
		}
		if (NullJudgeUtil.isNull(user.getCreateDate())) {
			user.setCreateDate(new Date());
		}
	}


	@Override
	public User getUserById(Integer userId) {
		return userDao.selectByPrimaryKey(userId);
	}

	@Override
	public String tokencheck(String token) {
		if (StringUtil.isEmpty(token) || "null".equals(token)) {
			return ReturnUtil.returnJson("1111", "用户未登录");
		}
		String id = "";
		try {
			id =Encryption.decrypt(token,Global.getTokenKey());
		} catch (Exception e1) {
			e1.printStackTrace();
			return ReturnUtil.returnJson("1111", "验证失败,请重新登录");
		}
		String tokenKey = "token@" + id + "@" + token;
		String tokenVal = JedisUtils.get(tokenKey);
		if (StringUtil.isEmpty(tokenVal)) {
			return ReturnUtil.returnJson("1111", "验证失败,请重新登录");
		}
		String uid = "uid@" + id;
		String uidVal = JedisUtils.get(uid);
		if (null == uidVal) {
			return ReturnUtil.returnJson("1111", "验证失败,请重新登录");
		}
		UserBean userBean = (UserBean) JsonUtil.fromObj(uidVal, UserBean.class,
				null);
		if (!Boolean.parseBoolean(Global.getLoginLimit())) {
			if (!token.equals(userBean.getLoginSessionId())) {
				JedisUtils.del(tokenKey);
				return ReturnUtil.returnJson("1111", "此账号已在其他地点登录!");
			}
		}
		if (!userBean.isActivated()) {
			return ReturnUtil.returnJson("1009", "用户未验证!",userBean);
		}
		JedisUtils.set(tokenKey, userBean.getId().toString(),Integer.parseInt(Global.getTokenExpireTime()));
		return ReturnUtil.returnJson("1000", "success!", userBean);
	}


	@Override
	public UserBean getCurrentUser(String token) {
		if (StringUtil.isEmpty(token) || "null".equals(token)) {
			return null;
		}
		String id = "";
		try {
			id =Encryption.decrypt(token,Global.getTokenKey());
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		String tokenKey = "token@" + id + "@" + token;
		String tokenVal = JedisUtils.get(tokenKey);
		if (StringUtil.isEmpty(tokenVal)) {
			return null;
		}
		String uid = "uid@" + id;
		String uidVal = JedisUtils.get(uid);
		if (null == uidVal) {
			return null;
		}
		UserBean userBean = (UserBean) JsonUtil.fromObj(uidVal, UserBean.class,
				null);
		if (!Boolean.parseBoolean(Global.getLoginLimit())) {
			if (!token.equals(userBean.getLoginSessionId())) {
				JedisUtils.del(tokenKey);
				return null;
			}
		}
		return userBean;
	}




	


	
	
}
