package cn.service.common.util;

import cn.service.common.bean.ResponseBean;


public class ReturnUtil {
	
	/**
	 * 返回json对象
	 * @param code
	 * @param info
	 * @return
	 */
	public static String returnJson(String code, String info) {
		ResponseBean response = new ResponseBean();
		response.setCode(code);
		response.setInfo(info);
		return JsonUtil.toJson(response, DateUtil.BOTH);	
	}
  
	/**
	 * 返回json对象
	 * @param code
	 * @param info
	 * @param data
	 * @return
	 */
	public static String returnJson(String code, String info,Object data) {
		ResponseBean response = new ResponseBean();
		response.setCode(code);
		response.setInfo(info);
		response.setData(data);
		return JsonUtil.toJson(response, DateUtil.BOTH);
	}
	
}
