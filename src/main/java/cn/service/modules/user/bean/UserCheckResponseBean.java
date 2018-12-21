package cn.service.modules.user.bean;

import cn.service.modules.user.model.User;

public class UserCheckResponseBean {

	private String code;  //code符号
	private String info;  //信息
	private User data;  //数据
	


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

	public User getData() {
		return data;
	}

	public void setData(User data) {
		this.data = data;
	}
}
