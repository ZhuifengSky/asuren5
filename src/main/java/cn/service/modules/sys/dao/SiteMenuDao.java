package cn.service.modules.sys.dao;

import java.util.List;

import cn.service.modules.sys.bean.SiteMenuBean;


public interface SiteMenuDao {
    
	/**
	 * 获取网站菜单项
	 * @param parentId
	 * @return
	 */
	public List<SiteMenuBean> getSiteMenus(Long parentId);
}