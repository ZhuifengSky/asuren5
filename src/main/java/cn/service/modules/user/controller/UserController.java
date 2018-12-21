package cn.service.modules.user.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import cn.service.common.interceptor.LoginInterceptor;
import cn.service.common.util.CookieUtils;
import cn.service.common.util.DateUtil;
import cn.service.common.util.Encryption;
import cn.service.common.util.FormValidateUtil;
import cn.service.common.util.Global;
import cn.service.common.util.HttpUtils;
import cn.service.common.util.JedisUtils;
import cn.service.common.util.JsonUtil;
import cn.service.common.util.RandomCharacter;
import cn.service.common.util.ReturnUtil;
import cn.service.common.util.SendMailUtil;
import cn.service.common.util.Sha256Util;
import cn.service.common.util.StringUtil;
import cn.service.modules.user.bean.UserBean;
import cn.service.modules.user.model.User;
import cn.service.modules.user.service.IUserService;

/**
 * 用户控制类
 * 
 * @author pc-zw
 *
 */
@Controller
@RequestMapping("api/user")
@Api(value = "User", description = "用户控制")
public class UserController {

	private static Logger logger = LoggerFactory
			.getLogger(UserController.class);
	@Autowired
	private IUserService userService;

	/**
	 * 用户邮箱注册
	 * 
	 * @param request
	 * @param response
	 * @param userName
	 * @param password
	 * @param email
	 * @return
	 */

	@RequestMapping(value = "/regByEmail", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(notes = "regByEmail", value = "用户邮箱注册", httpMethod = "POST", responseClass = "java.lang.String")
	public String regByEmail(
			HttpServletRequest request,
			HttpServletResponse response,
			@ApiParam(value = "用户名") @RequestParam(value = "userName", required = false) String userName,
			@ApiParam(value = "邮箱") @RequestParam("email") String email,
			@ApiParam(value = "密码") @RequestParam("password") String password,
			@ApiParam(value = "验证码uuid") @RequestParam("uuid") String uuid,
			@ApiParam(value = "验证码") @RequestParam("valiCode") String valiCode,
			@ApiParam(value = "IP") @RequestParam(value = "IP", required = false) String ip) {
		try{
			if (StringUtil.isEmpty(ip))
			ip = HttpUtils.getRequestorIp(request);
			if (!FormValidateUtil.checkEmail(email)) {
				return ReturnUtil.returnJson("1001", "邮箱格式不正确!");
			}
			if (StringUtil.isEmpty(password)) {
				return ReturnUtil.returnJson("1002", "密码为空!");
			}
			if (!checkValiCode(uuid, valiCode)) {
				return ReturnUtil.returnJson("1003", "验证码输入不正确或者已过期!");
			}
			if (!StringUtil.isEmpty(userName)) {
				if (!userService.regCheck("userName", userName)) {
					return ReturnUtil.returnJson("1006", "该用户名已被占用!");
				}
			}
			if (!userService.regCheck("email", email)) {
				return ReturnUtil.returnJson("1007", "该邮箱已注册，请直接登录或者更换邮箱!");
			}
			User user = new User();
			if (StringUtil.isEmpty(userName)) {
				user.setUserName("xw_"+RandomCharacter.getCharAndNumr(6));
			}else{
			  user.setUserName(userName);
			}
			String salt = Sha256Util.Salt();
			String saltPassword = Sha256Util.encipher(password, salt);
			user.setSalt(salt);
			user.setPassword(saltPassword);
			user.setEmail(email);
			user.setCreateIp(ip);
			user = userService.regByEmail(user);
			if (user.getId()!=null && user.getId()>0) {
				sendVerifyEmail(request, response);
				return setcache(request, response, user, ip);
			}
			return ReturnUtil.returnJson("999", "注册发生异常!");
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.returnJson("999", "系统异常!");
		}
	}
	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param response
	 * @param loginName
	 * @param password
	 * @param ip
	 * @return
	 */
	@RequestMapping(value = "/login", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(notes = "login", value = "用户登录", httpMethod = "POST", responseClass = "java.lang.String")
	public String login(
			HttpServletRequest request,
			HttpServletResponse response,
			@ApiParam(value = "登录名") @RequestParam("loginName") String loginName,
			@ApiParam(value = "密码") @RequestParam("password") String password,
			@ApiParam(value = "IP地址") @RequestParam(value = "IP", required = false) String ip) {
		if (StringUtil.isEmpty(ip))ip = HttpUtils.getRequestorIp(request);
		User user = userService.loginCheck(loginName);
		if (user != null) {
			String saltPassword = Sha256Util.encipher(password, user.getSalt());
			if (saltPassword.equals(user.getPassword())) {
				return setcache(request, response, user, ip);
			} else {
				return ReturnUtil.returnJson("1004", "密码错误!");
			}
		} else {
			return ReturnUtil.returnJson("1005", "未找到用户!");
		}
	}

	/**
	 * 用户登录检查
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logincheck", produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ApiOperation(notes = "logincheck", value = "用户登录态检查", httpMethod = "POST", responseClass = "java.lang.String")
	public String logincheck(HttpServletRequest request,
			HttpServletResponse response) {
		String token = CookieUtils.getCookie(request, "token");
		return userService.tokencheck(token);
	}

	
	/**
	 * 用户注销退出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/logout", produces="application/json;charset=UTF-8")
	@ResponseBody
	@ApiOperation(notes = "logout", value = "用户退出", responseClass = "java.lang.String")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		try {
			String token = CookieUtils.getCookie(request, "token");
			if (StringUtil.isEmpty(token)||"null".equals(token)) {
				return ReturnUtil.returnJson("1111", "用户未登录!");
			}
			String id = "";
			try {
				id =Encryption.decrypt(token,Global.getTokenKey());
			} catch (Exception e1) {
				return ReturnUtil.returnJson("1111", "未找到登录用户!");
			}
        	String tokenKey = "token@" + id + "@" + token;
        	String tokenVal = JedisUtils.get(tokenKey);
			if (!StringUtil.isEmpty(tokenVal)) {
				JedisUtils.del(tokenKey);
			}
			CookieUtils.delCookie(request, response, "token");
			return ReturnUtil.returnJson("1111", "退出成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.returnJson("999", "系统异常!");
		}
	}
	
	
	/**
	 * 用户邮箱验证
	 * @param request
	 * @param response
	 * @param userId
	 * @param emailCode
	 */
	@RequestMapping(value="/mailVerify",method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	@ResponseBody
	@ApiOperation(notes = "mailVerify", value = "邮箱验证")
	public void mailVerify(HttpServletRequest request,HttpServletResponse response,
			@ApiParam(value = "用户ID") @RequestParam("userId") Integer userId,
			@ApiParam(value = "邮箱验证key") @RequestParam("emailCode") String emailCode){
		String host =Global.getSiteHost();
		String htmlPath =Global.getHtmlPath();
		String uidVal = JedisUtils.get("emailCode@"+emailCode);
		User user = userService.getUserById(userId);
		try {
			if (user!=null) {
				if (user.getEmailVerified()) {					
					String targetUrl= host+htmlPath+"/mailVerify/mailVerifyed.html";
					response.sendRedirect(targetUrl);
				}else{
					if (uidVal.equals(userId.toString())) {
						user.setEmailVerified(true);
						userService.updateUser(user);
						updateCache(request, response, userId);
						String targetUrl= host+htmlPath+"/mailVerify/mailVerifySu.html";
						response.sendRedirect(targetUrl);
					}else{
						String targetUrl= host+htmlPath+"/mailVerify/mailVerifyFai.html";
						response.sendRedirect(targetUrl);
					}	
				}
			}else{
				String targetUrl= host+htmlPath+"/mailVerify/mailVerifyFai.html";
				response.sendRedirect(targetUrl);
			}
			
		} catch (IOException e) {
			logger.error("用户邮箱验证跳转页面异常!");
			e.printStackTrace();
		}
	}
	
	
	

	/**
	 * 登录信息保存redis cookie
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	private String setcache(HttpServletRequest request,
			HttpServletResponse response, User user, String ip) {
		String token =Encryption.encrypt(user.getId().toString(),Global.getTokenKey());
		// 存缓存
		String uid = "uid@" + user.getId();
		String tokenKey = "token@" + user.getId() + "@" + token;
		user.setPassword(null);
		user.setSalt(null);
		if (!StringUtil.isEmpty(ip)) {
			user.setLoginIp(ip);
		}
		user.setLoginDate(DateUtil.format(new Date(),DateUtil.BOTH));
		userService.updateUser(user);
		UserBean userBean = geneUserBean(user);
		userBean.setLoginSessionId(token);
		if (user.getEmailVerified() || user.getMobileBound()) {
			userBean.setActivated(true);
		}
		String jsonuser = JsonUtil.toJson(userBean, DateUtil.BOTH);
		JedisUtils.set(uid, jsonuser);
		JedisUtils.set(tokenKey, user.getId().toString(),Integer.parseInt(Global.getTokenExpireTime()));
		CookieUtils.setCookie(response, "token", token,Integer.parseInt(Global.getTokenExpireTime()));
		if (!userBean.isActivated()) {
			return ReturnUtil.returnJson("1009", "用户未验证!",userBean);
		}
		return ReturnUtil.returnJson("1000", "success!", userBean);
	}
	
	/**
	 * 刷新缓存
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 */
	private boolean updateCache(HttpServletRequest request,
			HttpServletResponse response,Integer userId){
		String uid = "uid@" + userId;
		String jsonuser = JedisUtils.get(uid);
		UserBean userBean = JsonUtil.fromJson(jsonuser, UserBean.class);
		User user = userService.getUserById(userId);
		userBean.setUserName(user.getUserName());
		userBean.setEmail(user.getEmail());
		userBean.setAvatar(user.getAvatar());
		userBean.setLoginDate(user.getLoginDate());
		if (user.getEmailVerified() || user.getMobileBound()) {
			userBean.setActivated(true);
		}
		String newJsonUser = JsonUtil.toJson(userBean, DateUtil.BOTH);
		JedisUtils.set(uid, newJsonUser);
		return true;
	}

	/**
	 * 验证码检查
	 * 
	 * @param uuid
	 * @param valiCode
	 * @return
	 */
	private boolean checkValiCode(String uuid, String valiCode) {
		String serverCode = JedisUtils.get("valicode@" + uuid);
		if (valiCode.equalsIgnoreCase(serverCode)) {
			return true;
		}
		return false;
	}

	/**
	 * 转换User对象
	 * 
	 * @param user
	 * @return
	 */
	private UserBean geneUserBean(User user) {
		UserBean userBean = new UserBean();
		userBean.setId(user.getId());
		userBean.setUserName(user.getUserName());
		userBean.setAvatar(user.getAvatar());
		if (user.getLoginDate()!=null) {
			userBean.setLoginDate(user.getLoginDate());
		}else{
			userBean.setLoginDate("");
		}
		userBean.setEmail(user.getEmail());
		return userBean;

	}
	
	/**
	 * 生成邮箱验证url
	 * @param userId
	 * @param emailCode
	 * @return
	 */
	private String generalEmailVerifyUrl(Integer userId,String emailCode){
		String host =Global.getSiteHost();
		return host+"/api/user/mailVerify?userId="+userId+"&emailCode="+emailCode;
	}
	
	
	/**
	 *  验证邮箱邮件发送
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/sendVerifyEmail",method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	@ResponseBody
	@ApiOperation(notes = "sendVerifyEmail", value = "发送验证邮箱邮件!")
	public String sendVerifyEmail(HttpServletRequest request,
			HttpServletResponse response){
		User user = (User) request.getAttribute(LoginInterceptor.CURRENT_USER);
		if (user!=null) {
			String emailCode = RandomCharacter.getCharAndNumr(20);
			JedisUtils.set("emailCode@"+emailCode, user.getId().toString(), 86400); //1天
			String templatePath = "/mailtemplate/emailVerify.ftl";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userName",  user.getUserName());
			map.put("targetUrl", generalEmailVerifyUrl(user.getId(), emailCode));
			SendMailUtil.sendFtlMail(user.getEmail(), "小武小站账户 验证邮箱", true, templatePath, map);
			return ReturnUtil.returnJson("1000", "邮件已成功发送!");
		}else{
			return ReturnUtil.returnJson("1111", "用户未登录!");
		}
		
	}
}
