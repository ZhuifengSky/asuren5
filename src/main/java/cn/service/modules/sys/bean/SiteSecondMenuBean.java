package cn.service.modules.sys.bean;

import java.util.List;

public class SiteSecondMenuBean {

	private Long id;   //id
	private String title;      //标题
	private String targetType;   //目标打开方式
	private String targetUrl;     //目标地址
	private List<SiteThirdMenuBean> thirdMenus;  //子菜单

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public List<SiteThirdMenuBean> getThirdMenus() {
		return thirdMenus;
	}

	public void setThirdMenus(List<SiteThirdMenuBean> thirdMenus) {
		this.thirdMenus = thirdMenus;
	}

	

	

	

}
