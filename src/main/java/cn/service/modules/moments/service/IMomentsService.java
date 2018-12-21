package cn.service.modules.moments.service;


import cn.service.modules.moments.bean.MomentsBean;
import cn.service.modules.moments.model.Moments;

import com.github.pagehelper.PageInfo;

public interface IMomentsService {

	 /**
	  * 说说列表查询
	  * @param queryBean
	  * @param pageNum
	  * @param pageSize
	  * @param sort
	  * @return
	  */
    public PageInfo<MomentsBean> getMoments(MomentsBean queryBean, int pageNum, int pageSize, String sort) throws Exception;


    /**
     * 说说的保存
     * @param momment
     */
    public void saveMoments(Moments momment) throws Exception;
    
    
    /**
     * 说说的删除
     * @param id
     * @return
     */
    public int deleteMoments(Integer id) throws Exception;
}
