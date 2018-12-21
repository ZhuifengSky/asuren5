package cn.service.modules.blog.service.impl;

import cn.service.common.util.DateUtil;
import cn.service.modules.blog.bean.BlogBean;
import cn.service.modules.blog.bean.BlogIndexBean;
import cn.service.modules.blog.bean.BlogListBean;
import cn.service.modules.blog.bean.BlogQueryBean;
import cn.service.modules.blog.dao.BlogDao;
import cn.service.modules.blog.model.Blog;
import cn.service.modules.blog.service.IBlogService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * Created by zw on 18/1/1.
 */
@Service
public class BlogServiceImpl implements IBlogService {

    @Autowired
    private BlogDao blogDao;

    @Override
    public PageInfo<BlogBean> getBlogs(Blog queryBean, int pageNum, int pageSize, String sort){
        if (sort==null || sort.equals("")) {
            sort="b.top desc,b.create_date desc, b.view_num desc";
        }
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy(sort);
        List<BlogBean> list = blogDao.findList(queryBean);
        return new PageInfo<>(list);
    }

	@Override
	public BlogBean getBlogById(Integer blogId) throws UnsupportedEncodingException {
		return blogDao.selectByPrimaryKey(blogId);			 
	}

	@Override
	public Blog saveBlog(Blog blog) {
		blog.setCreateDate(new Date());
		blogDao.insertSelective(blog);
		return blog;
	}

	@Override
	public void upNum(Integer id,String type, Integer num) {
		Blog blog = new Blog();
		blog.setId(id);
		if (type.equals("viewNum")) {			
			blog.setViewNum(num);
		}
        if (type.equals("commentNum")) {
        	blog.setCommentNum(num);
		}
        blogDao.upNum(blog);		
	}


}
