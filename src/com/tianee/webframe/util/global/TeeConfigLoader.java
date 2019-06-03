package com.tianee.webframe.util.global;

	import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

import com.tianee.oa.core.ispirit.global.TeeIspiritSysProps;
import com.tianee.oa.mobile.global.TeeMobileAndroidSysProps;
import com.tianee.oa.mobile.global.TeeMobileIosSysProps;



public class TeeConfigLoader {
	  /**
	   * 从系统配置文件中加载系统配置
	   * @param sysPropsFile      系统配置文件
	   * @return
	   */
	  public static Properties loadSysProps(String sysPropsFile) {
	    return loadSysProps(new File(sysPropsFile));
	  }
	  /**
	   * 从系统配置文件中加载系统配置
	   * @param sysPropsFile      系统配置文件
	   * @return
	   */
	  public static Properties loadSysProps(File sysPropsFile) {
	    Properties props = new Properties();
	    
	    if (!sysPropsFile.exists()) {
	      return props;
	    }
	    InputStream inProps = null;
	    try {
	      inProps = new BufferedInputStream(
	          new FileInputStream(sysPropsFile));
	      props.load(inProps);
	    } catch (IOException ex) {
	    } finally {
	      try {
	        if (inProps != null) {
	          inProps.close();
	        }
	      } catch (IOException ex) {
	      }
	    }
	    
	    return props;
	  }
	  
	  /**
	   * 系统初始化

	   * @param installPath      系统的安装路径

	   * @return
	   */
	  public static void loadInit(String rootPath) throws Exception {
	    File rootPathFile = new File(rootPath);
	    String installPath = null;
	    String webRoot = null;
	    String ctx = null;
	    try {
	      String realRootPath = rootPathFile.getCanonicalPath();
	      int p1 = realRootPath.lastIndexOf(File.separator);
	      int p2 = realRootPath.substring(0, p1).lastIndexOf(File.separator);
	      installPath = realRootPath.substring(0, p2);
	      webRoot = realRootPath.substring(p2 + 1, p1);
	      ctx = realRootPath.substring(p1 + 1);
	      System.out.println("安装路径：" + installPath);
	    }catch(Exception ex) {
	    }

	    //从配置文件中加载系统配置
	    ClassPathResource cps = new ClassPathResource("config/sysconfig.properties");
	    TeeSysProps.setProps(TeeConfigLoader.loadSysProps(cps.getFile()));
	    TeeSysProps.getProps().setProperty("rootPath", rootPath);
	    
	    ClassPathResource customerCps = new ClassPathResource("config/syscustomerconfig.properties");
	    TeeSysCustomerProps.setProps(TeeConfigLoader.loadSysProps(customerCps.getFile()));
	    
	    //读取安卓配置文件
	    String androidCPS = rootPath+ "appupdate/android/update.properties";
	    TeeMobileAndroidSysProps.setProps(TeeConfigLoader.loadSysProps(androidCPS));
	    
	    //读取IOS配置文件
	    String iosCPS = rootPath+ "appupdate/ios/update.properties";
	    TeeMobileIosSysProps.setProps(TeeConfigLoader.loadSysProps(iosCPS));
	    
	    //读取PC端配置文件
	    String imCPS = rootPath+ "appupdate/im/update.properties";
	    TeeIspiritSysProps.setProps(TeeConfigLoader.loadSysProps(imCPS));
	    
	  }
}