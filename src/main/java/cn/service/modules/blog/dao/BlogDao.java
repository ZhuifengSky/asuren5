package cn.service.modules.blog.dao;

import cn.service.modules.blog.bean.BlogBean;
import cn.service.modules.blog.bean.BlogListBean;
import cn.service.modules.blog.bean.BlogQueryBean;
import cn.service.modules.blog.model.Blog;

import java.util.List;

public interface BlogDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Blog record);

    int insertSelective(Blog record);

    BlogBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKeyWithBLOBs(Blog record);

    int updateByPrimaryKey(Blog record);

    List<BlogBean> findList(Blog queryBean);

	void upNum(Blog blog);

	List<BlogListBean> getTypeBlogList(BlogQueryBean blogBean);
}