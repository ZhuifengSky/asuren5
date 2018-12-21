package cn.service.modules.category.controller;

import java.util.List;

import cn.service.common.interceptor.LoginInterceptor;
import cn.service.common.util.ReturnUtil;
import cn.service.modules.category.bean.CategoryBean;
import cn.service.modules.category.model.Category;
import cn.service.modules.category.service.ICategoryService;
import cn.service.modules.user.model.User;

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
@RequestMapping("api/category")
@Api(value = "category", description = "博客分类控制类")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;


    @RequestMapping(value ="/getMyCategory", produces ="application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(notes = "getMyCategory", value = "我的分类", httpMethod = "GET", responseClass = "java.java.lang.String")
    public String getMyCategory(HttpServletRequest request, HttpServletResponse response){
    	User user = (User) request.getAttribute(LoginInterceptor.CURRENT_USER);
    	if (user!=null) {
	    	List<CategoryBean> categoryBeans = categoryService.getMyCategory(user.getId());
			return ReturnUtil.returnJson("1000","获取成功",categoryBeans);
    	}else{
			return ReturnUtil.returnJson("1111", "用户未登录!");
		}
    }
    
    @RequestMapping(value ="/saveCategory", produces ="application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(notes = "saveCategory", value = "分类新增", httpMethod = "POST", responseClass = "java.java.lang.String")
    public String saveBlog(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam String categoryName){        
    	User user = (User) request.getAttribute(LoginInterceptor.CURRENT_USER);
		if (user!=null) {
			Category category = new Category();
			category.setCategoryName(categoryName);
			categoryService.addCategory(category);
			return ReturnUtil.returnJson("1000", "success!",category);
		}else{
			return ReturnUtil.returnJson("1111", "用户未登录!");
		}
		
    }
}
