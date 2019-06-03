package com.beidasoft.zfjd.utils;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JSON字符串通用配置类，java数据类型转换js数据类型，由于精度问题会被
 * 前端自动转型舍弃一定精度，数值会产生变化，此类将long及Long类型自动转化为
 * 字符串类型，同时将日期类型转换为YYYY-MM-DD hh:mm:ss
 * Created by Eric on 2015/9/14.
 */
public class JsonValueProcessorUtil {

    public JsonValueProcessor getJsonDateValueProcessor(Class clazz)throws Exception{
        if(clazz.isAssignableFrom(java.util.Date.class)){
            return new JsonDateValueProcessor();
        }else if(clazz.isAssignableFrom(Long.class)){
            return new JsonLongValueProcessor();
        }else{
            return null;
        }
    }

    private class JsonDateValueProcessor implements JsonValueProcessor{
        public Object processArrayValue(Object o, JsonConfig jsonConfig) {
                return null;
        }

        public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
            if(o != null){
                if (o.getClass().isAssignableFrom(Date.class)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    return sdf.format(o);
                }
            }
            return null;
        }
    }

    private class JsonLongValueProcessor implements JsonValueProcessor{
        public Object processArrayValue(Object o, JsonConfig jsonConfig) {
            return null;
        }

        public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
            if(o != null){
                if (o.getClass().isAssignableFrom(Long.class)){
                    return o.toString();
                }
            }
            return null;
        }
    }
}
