package cn.service.modules.moments.dao;

import java.util.List;

import cn.service.modules.moments.bean.MomentsBean;
import cn.service.modules.moments.model.Moments;

public interface MomentsDao {
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(Moments record) throws Exception;

    int insertSelective(Moments record) throws Exception;

    Moments selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(Moments record) throws Exception;

    int updateByPrimaryKey(Moments record) throws Exception;
    
    List<MomentsBean> findList(MomentsBean momentsBean) throws Exception;
    
}