package cn.service.modules.sys.controller;

import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.service.common.util.*;
import cn.service.modules.blog.bean.BlogIndexBean;
import cn.service.modules.dailyWords.bean.DailyWordsBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import cn.service.modules.sys.bean.BannerBean;
import cn.service.modules.sys.bean.SiteMenuBean;
import cn.service.modules.sys.service.ISysService;

/**
 * 系统控制类
 * 
 * @author pc-zw
 *
 */
@Controller
@RequestMapping("api/sys")
@Api(value = "Sys", description = "系统控制")
public class SysController {

	@Autowired
	private ISysService sysService;


	/**
	 * 生成验证码
	 * @param request
	 * @param response
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value ="/createValidateCode/{uuid}", produces ="application/json;charset=UTF-8")
	@ResponseBody
	@ApiOperation(notes = "createValidateCode", value = "生成图片验证码", httpMethod = "GET", responseClass = "java.lang.String")
	public String createValidateCode(HttpServletRequest request,
			HttpServletResponse response,
			@ApiParam(value = "uuid") @PathVariable("uuid") String uuid) {
		try {
			if (StringUtil.isEmpty(uuid)) {
				return ReturnUtil.returnJson("1201", "必需参数不能为空!");
			}
			ValidateCode validateCode = new ValidateCode();
			ServletOutputStream out = response.getOutputStream();
			String valiCode=validateCode.createImage(out);
			JedisUtils.set("valicode@"+uuid, valiCode, 180);
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.returnJson("999", "系统异常!");
		}
	}

	
	/**
	 * 获取banner图片
	 * @param request
	 * @param response
	 * @param num
	 * @return
	 */
	@RequestMapping(value ="/getBanners", produces ="application/json;charset=UTF-8", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(notes = "getBanners", value = "获取Banner图片列表", httpMethod = "GET", responseClass = "java.lang.String")
	public String getBanners(HttpServletRequest request,
			HttpServletResponse response,
			@ApiParam(value = "需要数量") @RequestParam(value="num",required=false) Integer num) {
		try {			
			List<BannerBean> bannerBeans = sysService.getBanners(num);
			return ReturnUtil.returnJson("1000", "success!", bannerBeans);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.returnJson("999", "系统异常!");
		}
	}

	
	/**
	 * 获取网站菜单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/getSiteMenus", produces ="application/json;charset=UTF-8", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(notes = "getSiteMenus", value = "获取网站菜单", httpMethod = "GET", responseClass = "java.lang.String")
	public String getSiteMenus(HttpServletRequest request,
			HttpServletResponse response){
		try {			
			 List<SiteMenuBean> siteMenus = sysService.getSiteMenu();
			 return ReturnUtil.returnJson("1000", "success!", siteMenus);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.returnJson("999", "系统异常!");
		}
	}


	/**
	 * 获取首页内容
	 * @param request
	 * @param response
	 * @param days
	 * @param num
	 * @return
	 */
	@RequestMapping(value ="/index", produces ="application/json;charset=UTF-8")
	@ResponseBody
	@ApiOperation(notes = "index", value = "获取首页内容", httpMethod = "GET", responseClass = "java.java.lang.String")
	public String indexContent(HttpServletRequest request, HttpServletResponse response,
							@RequestParam(value = "days",required = false) Integer days,
							@RequestParam(value = "blogNum",required = false) Integer blogNum,
							@RequestParam(value = "num",required = false) Integer num){
		if (days==null || days==0) {
			days=Integer.parseInt(Global.getIndexLimitDay());
		}
		if (blogNum==null || blogNum==0 || blogNum>20) {
			blogNum=Integer.parseInt(Global.getIndexBlogNum());
		}
		if (num==null || num==0 || num>20) {
			num=Integer.parseInt(Global.getIndexMessageNum());
		}
		BlogIndexBean indexBean = sysService.getIndexContent(days, blogNum,num);
		return ReturnUtil.returnJson("1000", "success!",indexBean);
	}

	
	/**
	 * 获取每日一语
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/getDailyWords", produces ="application/json;charset=UTF-8", method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(notes = "getDailyWords", value = "获取每日一语", httpMethod = "GET", responseClass = "java.lang.String")
	public String getDailyWords(HttpServletRequest request,
			HttpServletResponse response){
		try {			
			 DailyWordsBean dailyWordsBean = sysService.getDailyWords();
			 return ReturnUtil.returnJson("1000", "success!", dailyWordsBean);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.returnJson("999", "系统异常!");
		}
	}
}
