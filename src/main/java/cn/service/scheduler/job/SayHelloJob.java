package cn.service.scheduler.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 注解定时任务
 * Created by zw on 18/2/4.
 */

//@Component
public class SayHelloJob {

    //@Scheduled(cron="0/10 * *  * * ? ")   //每10秒执行一次
    public void sayHello(){
        System.out.printf("Hello");
    }
}
