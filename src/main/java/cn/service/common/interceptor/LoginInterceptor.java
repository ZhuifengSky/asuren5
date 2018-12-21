package cn.service.common.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.service.modules.user.bean.UserCheckResponseBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.service.common.util.CookieUtils;
import cn.service.common.util.JsonUtil;
import cn.service.common.util.ReturnUtil;
import cn.service.modules.user.model.User;
import cn.service.modules.user.service.IUserService;

public class LoginInterceptor implements HandlerInterceptor{

	public static final String TOKEN = "token";
	public static final String CURRENT_USER = "user";
	
	@Autowired
	private IUserService userServiceImpl;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String token = CookieUtils.getCookie(request, TOKEN);
		if (!StringUtils.isEmpty(token)) {
			String checkResult = userServiceImpl.tokencheck(token);
			if (checkResult!=null) {
				UserCheckResponseBean bean = JsonUtil.fromJson(checkResult, UserCheckResponseBean.class);
				if (bean.getCode().equals("1000") || bean.getCode().equals("1009")) {
					User user = bean.getData();
					request.setAttribute(CURRENT_USER, user);
					return true;
				}
			}
		}
		preHandle(response, ReturnUtil.returnJson("1111", "用户未登录!"));
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {		
	}
	
	private void preHandle(HttpServletResponse response, String message){
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.write(message);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
