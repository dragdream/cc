package com.tianee.webframe.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeUtility;


public class TeeResPrivServlet {
	  /**
	   * 判断是否存在此资源权限---判断jsp页面
	   * @param menuResPrivNo ：页面的菜单资源权限编号---没有页面都有
	   * @param isCheck : 是否需要校验此页面
	   * @param extend ： 预留扩展字段
	   * @param  request : 页面请求
	   * @param  response ： 页面response
	   * @return
	   * @throws Exception
	   */
	  public static void checkResPriv(String menuResPrivNo,boolean isCheck,HttpServletRequest request,HttpServletResponse response,String extend) throws Exception {
		  String IS_CHECK_RES_PRIV = TeeSysProps.getString("IS_CHECK_RES_PRIV") == null ? "0" : TeeSysProps.getString("IS_CHECK_RES_PRIV")  ;//是否需要判断菜单资源权限
		  if(!IS_CHECK_RES_PRIV.equals("1")){//需要校验
			  if(isCheck && !TeeUtility.isNullorEmpty(menuResPrivNo)){//需要校验 
				  if(menuResPrivNo.indexOf(",") > 0){//如果存爱多个资源权限编号，已逗号分隔
					  String[] menuResPrivNoArry = menuResPrivNo.split(",");
				  }else{
					  
				  }
				  request.getRequestDispatcher("/inc/sessionerror.jsp").forward(request, response);

			  }

			  
		  }
		 
	  }
}
