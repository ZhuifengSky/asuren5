package cn.service.modules.blog.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import cn.service.common.bean.PageBean;
import cn.service.common.interceptor.LoginInterceptor;
import cn.service.common.util.CookieUtils;
import cn.service.common.util.Global;
import cn.service.common.util.HttpUtils;
import cn.service.common.util.ReturnUtil;
import cn.service.modules.blog.bean.BlogBean;
import cn.service.modules.blog.model.Blog;
import cn.service.modules.blog.service.IBlogService;
import cn.service.modules.sys.service.ISysService;
import cn.service.modules.user.bean.UserBean;
import cn.service.modules.user.model.User;
import cn.service.modules.user.service.IUserService;

import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *博文控制类
 *
 * @author pc-zw
 *
 */
@Controller
@RequestMapping("api/blog")
@Api(value = "blog", description = "博客文章控制类")
public class BolgController {

    @Autowired
    private IBlogService blogService;

    @Autowired
	private IUserService userService;
    
    @Autowired
	private ISysService sysService;

    @RequestMapping(value ="/getBlogs", produces ="application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(notes = "getBlogs", value = "获取博客文章", httpMethod = "GET", responseClass = "java.java.lang.String")
    public String getBlogs(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value = "userId",required = false) Integer userId,
                           @RequestParam(value = "categoryId",required = false) Integer categoryId,
                           @RequestParam(value = "sort",required = false) String sort,
                           @RequestParam(value = "pageNum",required = false) Integer pageNum,
                           @RequestParam(value = "pageSize" ,required = false) Integer pageSize){
        pageNum = Global.getDefaultVaule(pageNum, 1);
        pageSize = Global.getDefaultVaule(pageSize, Integer.parseInt(Global.getDefaultPageSize()));
        Blog queryBean = new Blog();
        queryBean.setUserId(userId);
        queryBean.setCategoryId(categoryId);
        PageInfo<BlogBean> page = blogService.getBlogs(queryBean,pageNum,pageSize,sort);
        PageBean<BlogBean> pageBean = Global.generalPageBean(page);
        return ReturnUtil.returnJson("1000","获取成功",pageBean);

    }

    
    
    @RequestMapping(value ="/viewDetail", produces ="application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(notes = "viewDetail", value = "查看博文全文", httpMethod = "GET", responseClass = "java.java.lang.String")
    public String viewDetail(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value = "blogId",required = false) Integer blogId
                           ){        
		try {			
			String ip = HttpUtils.getRequestorIp(request);
			String token = CookieUtils.getCookie(request, "token");
			UserBean userBean = userService.getCurrentUser(token);
			int userId = 0;
			if (userBean!=null) {
				userId = userBean.getId();
			}
			boolean logFlag = sysService.checkTodayIsExist("blog", blogId, ip, userId);			
			BlogBean blog = blogService.getBlogById(blogId);
			if (!logFlag) {
				sysService.saveUserViewLog("blog", blogId, ip, userId);
				blogService.upNum(blogId, "viewNum", blog.getViewNum()+1);
				blog.setViewNum(blog.getViewNum()+1);
			}
			return ReturnUtil.returnJson("1000","获取成功",blog);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return ReturnUtil.returnJson("999", "系统异常!");
		}
        

    }
    
    @RequestMapping(value ="/saveBlog", produces ="application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(notes = "saveBlog", value = "博文保存", httpMethod = "POST", responseClass = "java.java.lang.String")
    public String saveBlog(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam String type,
    		@RequestParam String title,
            @RequestParam(value = "imageUrl",required = false) String imageUrl,
            @RequestParam Integer categoryId,
            @RequestParam String shortDesc,
            @RequestParam String content){        
    	User user = (User) request.getAttribute(LoginInterceptor.CURRENT_USER);
		if (user!=null) {
			if(user.getId()!=1){
				return ReturnUtil.returnJson("1112", "用户暂无权限!");
			}else {
				Blog blog = new Blog();
				blog.setType(type);
				blog.setTitle(title);
				blog.setImageUrl(imageUrl);
				if (shortDesc.length() >= 150) {
					shortDesc = shortDesc.substring(0, 150) + "...";
				}
				blog.setShortDesc(shortDesc);
				blog.setUserId(user.getId());
				blog.setCategoryId(categoryId);
				blog.setCreateDate(new Date());
				blog.setUpdateDate(new Date());
				blog.setContent(content);
				blogService.saveBlog(blog);
				return ReturnUtil.returnJson("1000", "success!", blog.getId());
			}
		}else{
			return ReturnUtil.returnJson("1111", "用户未登录!");
		}
		
    }

}
