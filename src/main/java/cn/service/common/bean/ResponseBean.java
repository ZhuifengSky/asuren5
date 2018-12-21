package cn.service.common.bean;

public class ResponseBean {

	private String code;  //code符号
	private String info;  //信息
	private Object data;  //数据
	
	public ResponseBean() {
		// TODO Auto-generated constructor stub
	}
	
	public ResponseBean(String code, String info, Object data) {
		super();
		this.code = code;
		this.info = info;
		this.data = data;
	}

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
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
