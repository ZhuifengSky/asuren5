package cn.service.modules.messageBoard.dao;

import cn.service.modules.messageBoard.bean.MessageBoardBean;
import cn.service.modules.messageBoard.model.MessageBoard;

import java.util.List;

public interface MessageBoardDao {
    int deleteByPrimaryKey(Integer id);

    int insert(MessageBoard record);

    int insertSelective(MessageBoard record);

    MessageBoard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessageBoard record);

    int updateByPrimaryKeyWithBLOBs(MessageBoard record);

    int updateByPrimaryKey(MessageBoard record);

    List<MessageBoardBean> findList(MessageBoardBean record);
}