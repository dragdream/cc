package com.tianee.webframe.controller;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.tianee.webframe.util.date.CustomCalendarEditor;
import com.tianee.webframe.util.date.CustomDateEditor;
import com.tianee.webframe.util.date.CustomIntEditor;




/**
 * 基础控制器，其他控制器需extends此控制器获得initBinder自动转换的功能
 * 
 * 
 */
public class BaseController {

	//private static final Logger logger = Logger.getLogger(BaseController.class);

	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 * 
	 * @param binder
	 * 
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor());
		
		binder.registerCustomEditor(Calendar.class, new CustomCalendarEditor());
		
		binder.registerCustomEditor(int.class, new CustomIntEditor());
		// binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}

}
