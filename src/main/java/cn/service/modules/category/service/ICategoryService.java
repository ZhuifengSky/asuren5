package cn.service.modules.category.service;

import java.util.List;

import cn.service.modules.category.bean.CategoryBean;
import cn.service.modules.category.model.Category;

public interface ICategoryService {

	/**
	 * 查询用户的分类
	 * @param userId
	 * @return
	 */
	public List<CategoryBean> getMyCategory(Integer userId);
	
	
	/**
	 * 增加分类
	 * @param category
	 * @return
	 */
	public int addCategory(Category category);
	
	
	/**
	 * 删除分类
	 * @param category
	 * @return
	 */
	public int deleteCategory(Category category);
}
