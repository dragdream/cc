package com.beidasoft.xzfy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;

import com.tianee.oa.core.org.bean.TeePerson;

public class StringUtils {
    public final static String EMPTY = "";

    /**
     * Description:判断是否为空
     * 
     * @author zhangchengkun
     * @version 0.1 2019年5月7日
     * @param str
     * @return boolean
     */
    public static boolean isEmptyOrBlank(String str) {
        if ("".equals(str) || str == null)
            return true;
        else
            return false;
    }

    /**
     * 数组转为bean对象
     * 
     * @param obj
     * @param classType
     * @return
     */
    public static <T> T arrayToObject(Object[] obj, Class<T> clazz) {

        T object = null;
        try {
            // 实例化对象
            object = clazz.newInstance();

            int idx = 0;
            for (Field fld : clazz.getDeclaredFields()) {
                // 按规则生成set方法名称
                String setterName = "set" + StringUtils.captureName(fld.getName());
                // 获取方法
                Method setter = clazz.getDeclaredMethod(setterName, fld.getType());
                // 反射调用set方法
                String value = "";
                obj[idx] = obj[idx] == null ? "" : obj[idx];
                if (obj[idx] instanceof BigInteger) {
                    value = String.valueOf(obj[idx]);
                } else {
                    value = String.valueOf(obj[idx]);
                }
                setter.invoke(object, value);
                // setter.invoke(object, obj[idx]);
                idx++;
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }
    /**
     * 数组转为bean对象(专门为统计写的转换方法)
     * 
     * @param obj
     * @param classType
     * @return
     */
    public static <T> T arrayToObject4Stat(Object[] obj, Class<T> clazz) {

        T object = null;
        try {
            // 实例化对象
            object = clazz.newInstance();

            int idx = 0;
            for (Field fld : clazz.getDeclaredFields()) {
                // 按规则生成set方法名称
                String setterName = "set" + StringUtils.captureName(fld.getName());
                // 获取方法
                Method setter = clazz.getDeclaredMethod(setterName, fld.getType());
                // 反射调用set方法
//                String value = "";
//                obj[idx] = obj[idx] == null ? "" : obj[idx];
//                if (obj[idx] instanceof BigInteger) {
//                    value = String.valueOf(obj[idx]);
//                } else {
//                    value = String.valueOf(obj[idx]);
//                }
//                setter.invoke(object, value);
                 setter.invoke(object, obj[idx]);
                idx++;
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }
    /**
     * 将字符串首字母转为大写
     * 
     * @param String
     * @return String
     */
    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    /**
     * 获取UUID
     * 
     * @return
     */
    public synchronized static String getUUId() {
        // 转化为String对象
        String uuid = UUID.randomUUID().toString();
        // 因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        uuid = uuid.replace("-", "");
        return uuid;
    }

    // 设置新增人员、时间等共通信息
    public static <T> void setAddInfo(T bean, TeePerson loginUser) {
        try {
            BeanUtils.setProperty(bean, "createdUser", loginUser.getUserName());
            BeanUtils.setProperty(bean, "createdUserId", String.valueOf(loginUser.getUuid()));
            BeanUtils.setProperty(bean, "createdTime", getCurrentTime());
        } catch (Exception e) {
            // 如果错误 则向上抛运行时异常
            throw new RuntimeException(e);
        }
    }

    // 设置修改人员、时间等共通信息
    public static <T> void setModifyInfo(T bean, TeePerson loginUser) {
        try {
            BeanUtils.setProperty(bean, "modifiedUser", loginUser.getUserName());
            BeanUtils.setProperty(bean, "modifiedUserId", String.valueOf(loginUser.getUuid()));
            BeanUtils.setProperty(bean, "modifiedTime", getCurrentTime());
        } catch (Exception e) {
            // 如果错误 则向上抛运行时异常
            throw new RuntimeException(e);
        }
    }

    /**
     * Description:获取当前时间 yyyy/MM/dd HH:mm:ss
     * 
     * @author ZCK
     * @version 0.1 2019年4月23日
     * @return String
     */
    public static String getCurrentTime() {
        // 获取当前时间
        Date currentTime = new Date();
        // 格式化
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 返回当前日期
        return formatter.format(currentTime).toString();
    }

    /**
     * 获取导出名字
     * 
     * @return
     */
    public static String getExprotTimeName() {
        // 获取当前时间
        Date currentTime = new Date();
        // 格式化
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        // 返回当前日期
        return formatter.format(currentTime).toString();
    }

    /**
     * 获取N年前的日期
     * 
     * @param n
     * @return String ，格式：yyyy/mm/dd
     */
    public static String getNYearsAgo(int n) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return String.format("%s/01/01", currentYear - n);
    }

}
