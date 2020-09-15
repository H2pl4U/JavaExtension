package com.h2pl4u.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Liuwei on 2020/9/15 14:23
 */
public class SimpleJob implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map=context.getMergedJobDataMap();
        System.out.println("username: " + map.get("username") +
                " Triggered time is: " + new SimpleDateFormat("HH:mm:ss").format((new Date())));
    }
}
