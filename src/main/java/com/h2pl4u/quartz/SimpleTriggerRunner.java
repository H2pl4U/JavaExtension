package com.h2pl4u.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * Created by Liuwei on 2020/9/15 14:26
 */
public class SimpleTriggerRunner {
    public static void main(String[] args) throws Exception {
        //创建Scheduler的工厂
        SchedulerFactory sf = new StdSchedulerFactory();
        //从工厂中获取调度器实例
        Scheduler scheduler = sf.getScheduler();
        //创建JobDetail
        JobDetail jb = JobBuilder.newJob(SimpleJob.class)
                .withDescription("一个简单的定时任务")
                .withIdentity("simpleJob", "simpleGroup")
                .build();
        //向任务传递数据
        JobDataMap jobDataMap = jb.getJobDataMap();
        jobDataMap.put("username", "俊辉");
        //任务运行时间 SimpleSchedule类型触发器有效
        long time = System.currentTimeMillis() + 3 * 1000L;
        Date statTime = new Date(time);
        //创建Trigger
        //使用SimpleScheduleBuilder 或者 CronScheduleBuilder
        Trigger t = TriggerBuilder.newTrigger()
                .withDescription("")
                .withIdentity("ramTrigger", "ramTriggerGroup")
                .startAt(statTime)
                .withSchedule(CronScheduleBuilder.cronSchedule("32,33,34,35 * * * * ? "))
                .build();
        //注册任务和定时器
        scheduler.scheduleJob(jb, t);
        //启动调度器
        scheduler.start();
    }
}
