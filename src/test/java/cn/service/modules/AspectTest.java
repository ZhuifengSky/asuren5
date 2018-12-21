package cn.service.modules;

import cn.service.common.util.DateUtil;
import cn.service.common.util.EhCacheUtils;
import cn.service.modules.dailyWords.model.DailyWords;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import cn.service.modules.exercise.service.IHelloService;

import java.util.Date;


public class AspectTest {
	    @Autowired
	    private IHelloService helloService;

	    @Test
	    @Transactional   //标明此方法需使用事务    
	    @Rollback(true)  //标明使用完此方法后事务不回滚,true时为回滚 
	    public void testHelloWord() throws Exception {
	    	helloService.sayHello("world");
	    }

	    public static void main(String[] args){
			DailyWords dailyWords = (DailyWords)  EhCacheUtils.get(DateUtil.format(new Date(), DateUtil.DATE));
			System.out.printf(dailyWords.getWords());
	    }
}
