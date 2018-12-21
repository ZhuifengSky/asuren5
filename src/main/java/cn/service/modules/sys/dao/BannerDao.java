package cn.service.modules.sys.dao;

import java.util.List;

import cn.service.modules.sys.bean.BannerBean;

public interface BannerDao {
    
	/**
	 * 获取指定数量的banner
	 * @param num
	 * @return
	 */
	public List<BannerBean> getBanners(Integer num);
}