package cn.service.modules.dailyWords.dao;

import cn.service.modules.dailyWords.model.ChickenSoupWords;

public interface ChickenSoupWordsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ChickenSoupWords record);

    int insertSelective(ChickenSoupWords record);

    ChickenSoupWords selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChickenSoupWords record);

    int updateByPrimaryKey(ChickenSoupWords record);

    ChickenSoupWords getRandomWords();
}