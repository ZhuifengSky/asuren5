package cn.service.scheduler.job;

import cn.service.common.util.DateUtil;
import cn.service.modules.dailyWords.dao.ChickenSoupWordsDao;
import cn.service.modules.dailyWords.dao.DailyWordsDao;
import cn.service.modules.dailyWords.model.ChickenSoupWords;
import cn.service.modules.dailyWords.model.DailyWords;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * xml配置定时任务
 * Created by zw on 18/2/4.
 */
public class GeneDailyWordsJob {

    @Autowired
    private ChickenSoupWordsDao chickenSoupWordsDao;

    @Autowired
    private DailyWordsDao dailyWordsDao;

    /**
     * 生成每日一语
     */
    public void geneDailyWords(){
        System.out.println("开始生成每日一语!");
        ChickenSoupWords chickenSoupWords = chickenSoupWordsDao.getRandomWords();
        if (chickenSoupWords!=null){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE);
                String tomorrow=DateUtil.addDay(DateUtil.format(new Date(),DateUtil.DATE),1,DateUtil.DATE);
                DailyWords dailyWords = new DailyWords();
                dailyWords.setWordsId(chickenSoupWords.getId());
                dailyWords.setWords(chickenSoupWords.getContent());
                dailyWords.setShowTime(sdf.parse(tomorrow));
                dailyWords.setCreateDate(new Date());
                dailyWords.setCreateBy(1);
                dailyWordsDao.insertSelective(dailyWords);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        System.out.println("生成每日一语结束!");
    }
}
