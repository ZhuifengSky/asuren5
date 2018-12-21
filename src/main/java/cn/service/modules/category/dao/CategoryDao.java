package cn.service.modules.category.dao;

import java.util.List;

import cn.service.modules.category.bean.CategoryBean;
import cn.service.modules.category.model.Category;

public interface CategoryDao {
    int delete(Category category);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

	List<CategoryBean> getCategoryByUserId(Integer userId);
}