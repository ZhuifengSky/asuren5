package cn.service.modules.dailyWords.dao;

import cn.service.modules.dailyWords.model.DailyWords;

public interface DailyWordsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DailyWords record);

    int insertSelective(DailyWords record);

    DailyWords selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DailyWords record);

    int updateByPrimaryKey(DailyWords record);

    DailyWords getDailyWords(String showDate);
}