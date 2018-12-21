package cn.service.modules.blog.service;

import java.io.UnsupportedEncodingException;

import cn.service.modules.blog.bean.BlogBean;
import cn.service.modules.blog.bean.BlogIndexBean;
import cn.service.modules.blog.model.Blog;

import com.github.pagehelper.PageInfo;

/**
 * Created by zw on 18/1/1.
 */
public interface IBlogService {

	/**
	 * 获取blog列表
	 * @param queryBean
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
    public PageInfo<BlogBean> getBlogs(Blog queryBean, int pageNum, int pageSize, String sort);
    
    /**
     * 预览详情
     * @param blogId
     * @return
     * @throws UnsupportedEncodingException
     */
    public BlogBean getBlogById(Integer blogId) throws UnsupportedEncodingException;
    
    /**
     * 保存博文
     * @param blog
     * @return
     */
    public Blog saveBlog(Blog blog);
    
    /**
     * 更新指定数
     * @param type   viewNum--浏览量  commentNum--评论数
     * @param num
     */
    public void upNum(Integer id,String type,Integer num);


}
