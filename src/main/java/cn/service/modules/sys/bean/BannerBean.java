package cn.service.modules.sys.bean;
/**
 * banner图片
 * @author pc-zw
 *
 */
public class BannerBean {
    private String id;   //ID
    private String title; //标题
    private String subTitle; //副标题
    private String imageUrl;//图片地址
    private String targetType;//目标打开方式
    private String targetUrl; //图片地址
	
    
    public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id =id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
    
    


    
}