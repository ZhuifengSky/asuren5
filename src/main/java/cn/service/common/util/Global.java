/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.service.common.util;

import java.util.Map;

import cn.service.common.bean.PageBean;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;


/**
 * 全局配置类
 * @author ThinkGem
 * @version 2014-06-25
 */
public class Global {

	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("asuren.properties");
	
	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}
	
	/**
	 * 获取配置
	 * @see {fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = propertiesLoader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/////////////////////////////////////////////////////////
	
	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}
	
	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}
	
	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}
	
	/**
	 * 获取默认头像地址
	 */
	public static String getDefaultAvator() {
		return getConfig("default.avatar.url");
	}
	
	/**
	 * 获取用户默认分类
	 * @return
	 */
	public static String getDefaultCategory1() throws Exception{
		String categoryName = getConfig("default.categoryName1");
		byte[] tempByte = categoryName.getBytes("ISO-8859-1");
		return new String(tempByte,"utf-8");
	}
	
	/**
	 * 获取用户默认分类2
	 * @return
	 */
	public static String getDefaultCategory2() throws Exception{
		String categoryName = getConfig("default.categoryName2");
		byte[] tempByte = categoryName.getBytes("ISO-8859-1");
		return new String(tempByte,"utf-8");
	}
	
	
	/**
	 * 获取token的加密key
	 */
	public static String getTokenKey() {
		return getConfig("token.key");
	}
	
	/**
	 * 获取登录限制--是否允许一号多登
	 */
	public static String getLoginLimit() {
		return getConfig("login.limit");
	}
	
	/**
	 * 获取token过期时间
	 */
	public static String getTokenExpireTime() {
		return getConfig("token.expireTime");
	}
	
	public static String getSiteHost() {
		return getConfig("site.host");
	}
	
	public static String getHtmlPath() {
		return getConfig("html.path");
	}
	
	/**
	 * 获取默认banner数量
	 */
	public static String getBannerDefaultNum() {
		return getConfig("banner.defaultNum");
	}

	/**
	 * 获取数据库类型
	 */
	public static String getJdbcType() {
		return getConfig("jdbc.type");
	}

	/**
	 * 获取默认展示数
	 */
	public static String getDefaultPageSize() {
		return getConfig("page.pageSize");
	}
	
	/**
	 * 获取首页左侧bolg展示数
	 */
	public static String getIndexBlogNum() {
		return getConfig("index.blogNum");
	}

	/**
	 * 获取首页右侧留言展示数
	 */
	public static String getIndexMessageNum() {
		return getConfig("index.messageNum");
	}
	
	/**
	 * 获取首页左侧有效时间（以当前时间为准）
	 */
	public static String getIndexLimitDay() {
		return getConfig("index.limitDay");
	}
	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */

	/**
	 * 设置默认的页码和每页条数
	 *
	 * @param num
	 * @param defaultV
	 */
	public static Integer getDefaultVaule(Integer num, int defaultV) {
		if (num == null || (int) num == 0 ) {
			num = defaultV == 0 ? 1 : defaultV;
		}
		return num;
	}
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}
	
	/**
	 * 在修改系统用户和角色时是否同步到Activiti
	 */
	public static Boolean isSynActivitiIndetity() {
		String dm = getConfig("activiti.isSynActivitiIndetity");
		return "true".equals(dm) || "1".equals(dm);
	}
	
	/////////////////////////////////////////////////////////
	
	// 显示/隐藏
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	// 是/否
	public static final String YES = "1";
	public static final String NO = "0";
	
	// 对/错
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	public static final String USERFILES_BASE_URL = "/userfiles/";

	/**
	 * 页面获取常量
	 * @see {fns:getConst('YES')}
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	public static <T> PageBean<T> generalPageBean(PageInfo<T> page){
		PageBean<T>  pageBean = new PageBean<T>();
		pageBean.setPageSize(page.getPageSize());
		pageBean.setPageNum(page.getPageNum());
		pageBean.setList(page.getList());
		pageBean.setPages(page.getPages());
		pageBean.setTotal(page.getTotal());
		return pageBean;
	}
	
}
