package com.examw.test.front.support;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author fengwei.
 * @since 2014年9月23日 上午11:08:28.
 */
public class DateUtil {
	public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    /** 
     * 以友好的方式显示时间 
     * @param time 
     * @return 
     */  
    public static String friendlyTime(Date time) {  
        //获取time距离当前的秒数  
        int ct = (int)((System.currentTimeMillis() - time.getTime())/1000);  
        if(ct == 0) {  
            return "刚刚";  
        }  
        if(ct > 0 && ct < 60) {  
            return ct + "秒前";  
        }  
        if(ct >= 60 && ct < 3600) {  
            return Math.max(ct / 60,1) + "分钟前";  
        }  
        if(ct >= 3600 && ct < 86400)  
            return ct / 3600 + "小时前";  
        if(ct >= 86400 && ct < 2592000){ //86400 * 30  
            int day = ct / 86400 ;             
            return day + "天前";  
        }  
        /*if(ct >= 2592000 && ct < 31104000) { //86400 * 30  
            return ct / 2592000 + "月前";  
        }  
        return ct / 31104000 + "年前";  */
        return formatter.format(time);	//直接显示日期
    }  
}
