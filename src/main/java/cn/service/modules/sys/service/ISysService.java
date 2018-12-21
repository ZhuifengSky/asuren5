package cn.service.modules.sys.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import cn.service.modules.blog.bean.BlogIndexBean;
import cn.service.modules.dailyWords.bean.DailyWordsBean;
import cn.service.modules.sys.bean.BannerBean;
import cn.service.modules.sys.bean.SiteMenuBean;

public interface ISysService {

	/**
	 * 获取首页banner
	 * @param num
	 * @return
	 */
	List<BannerBean> getBanners(Integer num);

	
	/**
	 * 获取网站菜单
	 * @return
	 */
	List<SiteMenuBean> getSiteMenu() throws IllegalAccessException, InvocationTargetException;
	
	
	/**
	 * 保存用户浏览日志
	 * @param type
	 * @param ip
	 * @param userId
	 */
	void saveUserViewLog(String type,Integer mainId,String ip,Integer userId);
	
	
	/**
	 * 检查当日是否存在日志
	 * @param type
	 * @param mainId
	 * @param ip
	 * @param userId
	 * @return
	 */
	boolean checkTodayIsExist(String type,Integer mainId,String ip,Integer userId);

	/**
	 * 获取首页展示博客
	 * @param days 天数
	 * @param num  数量
	 * @return
	 */
	BlogIndexBean getIndexContent(Integer days,Integer blogNum, Integer num);


	/**
	 * 获取每日一语
	 * @return
	 */
	DailyWordsBean getDailyWords();
	
}
