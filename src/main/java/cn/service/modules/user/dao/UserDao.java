package cn.service.modules.user.dao;

import java.util.List;

import cn.service.modules.user.model.User;

public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /**
     * 按条件查询用户
     * @param bean
     * @return
     */
    List<User> findUser(User bean);
}