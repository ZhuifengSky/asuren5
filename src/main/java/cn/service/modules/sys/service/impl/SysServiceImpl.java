package cn.service.modules.sys.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import cn.service.modules.blog.bean.BlogIndexBean;
import cn.service.modules.blog.bean.BlogListBean;
import cn.service.modules.blog.bean.BlogQueryBean;
import cn.service.modules.blog.dao.BlogDao;
import cn.service.modules.dailyWords.bean.DailyWordsBean;
import cn.service.modules.dailyWords.dao.DailyWordsDao;
import cn.service.modules.dailyWords.model.DailyWords;
import cn.service.modules.messageBoard.bean.MessageBoardBean;
import cn.service.modules.messageBoard.service.IMessageBoardService;

import com.github.pagehelper.PageInfo;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.service.common.util.DateUtil;
import cn.service.common.util.EhCacheUtils;
import cn.service.common.util.Global;
import cn.service.modules.sys.bean.BannerBean;
import cn.service.modules.sys.bean.SiteMenuBean;
import cn.service.modules.sys.bean.SiteSecondMenuBean;
import cn.service.modules.sys.bean.SiteThirdMenuBean;
import cn.service.modules.sys.dao.BannerDao;
import cn.service.modules.sys.dao.SiteMenuDao;
import cn.service.modules.sys.dao.ViewLogDao;
import cn.service.modules.sys.model.ViewLog;
import cn.service.modules.sys.service.ISysService;

@Service
public class SysServiceImpl implements ISysService{

	@Autowired
	private BannerDao bannerDao;
	
	@Autowired
	private SiteMenuDao siteMenuDao;
	
	@Autowired
	private ViewLogDao viewLogDao;

	@Autowired
	private BlogDao blogDao;

	@Autowired
	private IMessageBoardService messageBoardService;

	@Autowired
	private DailyWordsDao dailyWordsDao;

	@Override
	public List<BannerBean> getBanners(Integer num) {
		if (num==null || num<=0) {
			num= Integer.parseInt(Global.getBannerDefaultNum());
		}
		return bannerDao.getBanners(num);
	}

	@Override
	public List<SiteMenuBean> getSiteMenu() throws IllegalAccessException, InvocationTargetException{
		List<SiteMenuBean> siteMenus = siteMenuDao.getSiteMenus(0l);
		if (siteMenus!=null && siteMenus.size()>0) {
			for (SiteMenuBean siteMenuBean : siteMenus) {
			 	List<SiteMenuBean> secondTempMenus = siteMenuDao.getSiteMenus(siteMenuBean.getId());
			 	List<SiteSecondMenuBean> secondMenus = convertSiteMenuBean(secondTempMenus);
			    if (secondMenus!=null && secondMenus.size()>0) {
					for (SiteSecondMenuBean siteSecondMenuBean : secondMenus) {
						List<SiteMenuBean> thirdTempMenus = siteMenuDao.getSiteMenus(siteSecondMenuBean.getId());
						List<SiteThirdMenuBean> thirdMenuBeans = convertSiteMenuBean2(thirdTempMenus);
						siteSecondMenuBean.setThirdMenus(thirdMenuBeans);
					}
				}
			    siteMenuBean.setSecondMenus(secondMenus);
			}
		}
		return siteMenus;		
	}
	
	private List<SiteSecondMenuBean> convertSiteMenuBean(List<SiteMenuBean> secondMenus) throws IllegalAccessException, InvocationTargetException{
		if (secondMenus!=null && secondMenus.size()>0) {
			List<SiteSecondMenuBean> secondMenuBeans = new ArrayList<SiteSecondMenuBean>();
			for (SiteMenuBean siteMenuBean : secondMenus) {
				SiteSecondMenuBean secondMenuBean = new SiteSecondMenuBean();
				BeanUtils.copyProperties(secondMenuBean, siteMenuBean);
				secondMenuBeans.add(secondMenuBean);
			}
			return secondMenuBeans;
		}
		return null;
	}
	
	private List<SiteThirdMenuBean> convertSiteMenuBean2(List<SiteMenuBean> thirdMenus) throws IllegalAccessException, InvocationTargetException{
		if (thirdMenus!=null && thirdMenus.size()>0) {
			List<SiteThirdMenuBean> thirdMenuBeans = new ArrayList<SiteThirdMenuBean>();
			for (SiteMenuBean siteMenuBean : thirdMenus) {
				SiteThirdMenuBean thirdMenuBean = new SiteThirdMenuBean();
				BeanUtils.copyProperties(thirdMenuBean, siteMenuBean);
				thirdMenuBeans.add(thirdMenuBean);
				return thirdMenuBeans;
			}
		}
		return null;
	}


	@Override
	public void saveUserViewLog(String type,Integer mainId, String ip, Integer userId) {
		ViewLog viewLog =new ViewLog();
		viewLog.setIp(ip);
		viewLog.setMainId(mainId);
		viewLog.setIp(ip);
		if (userId!=null && userId>0) {
			viewLog.setUserId(userId);
		}		
		viewLog.setType(type);
		viewLog.setCreateDate(new Date());
		viewLogDao.insertSelective(viewLog);
	}

	@Override
	public boolean checkTodayIsExist(String type, Integer mainId, String ip,
			Integer userId) {
		ViewLog viewLog =new ViewLog();
		viewLog.setIp(ip);
		viewLog.setMainId(mainId);
		viewLog.setIp(ip);
		viewLog.setUserId(userId);
		String today=DateUtil.format(new Date(), DateUtil.DATE);
		viewLog.setCreateDateFrom(today +" 00:00:00");
		viewLog.setCreateDateTo(today + "23:59:59");
		List<ViewLog> logs = viewLogDao.findList(viewLog);
		if (logs!=null && logs.size()>0) {
			return true;
		}
		return false;
	}

	@Override
	public BlogIndexBean getIndexContent(Integer days,Integer blogNum, Integer num) {
		BlogIndexBean blogIndexBean = new BlogIndexBean();
		BlogQueryBean queryBean = new BlogQueryBean();
		String dateFrom = DateUtil.addDay(DateUtil.format(new Date(), DateUtil.BOTH), -days, DateUtil.BOTH);
		String dateTo = DateUtil.format(new Date(), DateUtil.BOTH);
		queryBean.setDateFrom(dateFrom);
		queryBean.setDateTo(dateTo);
		queryBean.setNum(blogNum);
		queryBean.setType("hot");
		List<BlogListBean> hotBlogs = blogDao.getTypeBlogList(queryBean);
		queryBean.setType("new");
		List<BlogListBean> newBlogs = blogDao.getTypeBlogList(queryBean);
		queryBean.setType("recomend");
		List<BlogListBean> recomendBlogs = blogDao.getTypeBlogList(queryBean);
		blogIndexBean.setHotBlogs(hotBlogs);
		blogIndexBean.setNewBlogs(newBlogs);
		blogIndexBean.setRecommendBlogs(recomendBlogs);
		MessageBoardBean messageBean = new MessageBoardBean();
		PageInfo<MessageBoardBean> firstPage= messageBoardService.getMessageBoard(messageBean,1,num,null);
		blogIndexBean.setMessageBoards(firstPage.getList());
		//每日一语
		DailyWordsBean wordsBean = new DailyWordsBean();
		DailyWords dailyWords = dailyWordsDao.getDailyWords(DateUtil.format(new Date(),DateUtil.DATE));
		wordsBean.setDailyWords(dailyWords);
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
		int day = c.get(Calendar.DAY_OF_MONTH);
		if(day%2 == 1){
			wordsBean.setShowType("beatText");
		}else{
			wordsBean.setShowType("rotateText");
		}
		blogIndexBean.setDailyWords(wordsBean);
		return blogIndexBean;
	}
	
	
	@Override
	public DailyWordsBean getDailyWords(){
		String todayDate = DateUtil.format(new Date(),DateUtil.DATE);
		//每日一语
		DailyWordsBean wordsBean = new DailyWordsBean();
		DailyWords dailyWords = (DailyWords) EhCacheUtils.get(todayDate);
		if(dailyWords==null){
			dailyWords = dailyWordsDao.getDailyWords(todayDate);
			if(dailyWords!=null){
				EhCacheUtils.put(todayDate, dailyWords);
			}
		}
		wordsBean.setDailyWords(dailyWords);
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
		int day = c.get(Calendar.DAY_OF_MONTH);
		if(day%2 == 1){
			wordsBean.setShowType("beatText");
		}else{
			wordsBean.setShowType("rotateText");
		}
		return wordsBean;
	}
}
