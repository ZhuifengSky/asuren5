package cn.service.modules.sys.dao;

import java.util.List;

import cn.service.modules.sys.model.ViewLog;

public interface ViewLogDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ViewLog record);

    int insertSelective(ViewLog record);

    ViewLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ViewLog record);

    int updateByPrimaryKey(ViewLog record);
    
    List<ViewLog> findList(ViewLog record);
}