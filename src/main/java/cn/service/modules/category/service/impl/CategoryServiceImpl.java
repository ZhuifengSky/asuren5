package cn.service.modules.category.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.service.modules.category.bean.CategoryBean;
import cn.service.modules.category.dao.CategoryDao;
import cn.service.modules.category.model.Category;
import cn.service.modules.category.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService{

	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public List<CategoryBean> getMyCategory(Integer userId) {
		return categoryDao.getCategoryByUserId(userId);
	}

	@Override
	public int addCategory(Category category) {
		category.setCreateDate(new Date());
		category.setUpdateDate(new Date());
		return categoryDao.insert(category);
	}

	@Override
	public int deleteCategory(Category category) {
		return categoryDao.delete(category);
	}

}
