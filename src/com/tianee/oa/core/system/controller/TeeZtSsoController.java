package com.tianee.oa.core.system.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bouncycastle.asn1.dvcs.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.misc.BASE64Encoder;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserOnline;
import com.tianee.oa.core.org.dao.TeeUserOnlineDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.org.service.TeeUserOnlineService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.servlet.TeeSessionListener;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.secure.TeePassBase64;
import com.tianee.webframe.util.secure.TeePassEncryptMD5;

@Controller
@RequestMapping("/sso")
public class TeeZtSsoController {
	@Autowired
	private TeePersonService personService;
	@Autowired
	TeeUserOnlineService userOnlineService; 
	@Autowired
	TeeUserOnlineDao  userOnlineDao;
	
	@RequestMapping("/login")
	public void ssoLogin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//接收页面传来的参数
		String uid = request.getParameter("uid");//获取用户名
		String expire = request.getParameter("expire");//获取时间戳
		expire=TeePassBase64.decodeStr(expire);//解密时间戳
		Date reseriveDate = TeeDateUtil.format(expire);//将时间戳转换成日期格式
		String token = request.getParameter("token");//数字签名
		String pingToken =uid+"ztoa"+expire; //拼接数字签名
		String jmToken= TeePassEncryptMD5.crypt(pingToken);//加密数字签名
		Date date =new Date();//定义当前时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义日期格式
		String currDate=sdf.format(date);
		Date currDate1=TeeDateUtil.format(currDate);
		String url = request.getParameter("url");//获取加密的URL
		String jmUrl=TeePassBase64.decodeStr(url);//对URL进行解密
		
		TeePerson sessionPerson = 
				(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		try{
			if(sessionPerson==null){
				TeePerson person =personService.getPersonByUserName(uid);//根据uid验证用户是否存在
				if(person!=null){
					if(reseriveDate.getTime()<currDate1.getTime()){//判断时间戳是否过期
							response.sendRedirect("/login.jsp");//过期返回登录页面
					}else{
						if(!token.equals(jmToken)){//未过期判断数字签名是否相等
							response.sendRedirect("/login.jsp");
						}else{//相等则将用户的信息存入到session中
							HttpSession session = request.getSession(true);
							TeeSessionListener.data.put(session.getId(), session);
							if(person.getDept() != null){
		    					person.getDept().getDeptName();
		    				}
		        			if(person.getUserRole()!=null){
		        				person.getUserRole().getRoleName();
		        			}
		        			try{
		        				person.getUserRoleOther().size();
		        			}catch(Exception ex){}
		        			try{
		        				person.getDeptIdOther().size();
		        			}catch(Exception ex){}
							
							session.setAttribute(TeeConst.LOGIN_USER, person); 
							
							//往userOnline插入一条数据
							TeeUserOnline userOnline=new TeeUserOnline();
							userOnline.setSessionToken(session.getId());
							userOnline.setLoginTime(new Date());
							userOnline.setUserId(person.getUuid());
							userOnline.setUserStatus(1);
							userOnline.setClient("1");
							userOnline.setIp(request.getRemoteAddr());
//							
							userOnlineDao.addUserOnline(userOnline);	
							
							response.sendRedirect(jmUrl);
						}
					}
					
				}else{//用户不存在返回登陆页面
					response.sendRedirect("/login.jsp");
				}
			}else{
				if(reseriveDate.getTime()<currDate1.getTime()){//判断时间戳是否过期
					response.sendRedirect("/login.jsp");//过期返回登录页面
				}else{
					if(!token.equals(jmToken)){//未过期判断数字签名是否相等
						response.sendRedirect("/login.jsp");
					}else{//相等则将用户的信息存入到session中
						response.sendRedirect(jmUrl);
					}
				}
			}
		}catch(Exception ex){
			throw new TeeOperationException("参数错误");
		}
		
	}
	
	
	//注销
	@RequestMapping("/logout")
	public void ssoLogout(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String userId=request.getParameter("userId");
		TeePerson user=personService.getPersonByUserId(userId);
		if(user!=null){
		   List<TeeUserOnline> list=userOnlineService.getListByUser(user);
		   if(list!=null&&list.size()>0){
			   for (TeeUserOnline teeUserOnline : list) {
				   //清楚缓存
				   TeeSessionListener.data.get(teeUserOnline.getSessionToken()).invalidate();
			   }
		   }
		}	
	}
	
	
		public static void main(String[] args) {
			String uid = "admin";
			String expire = "2018-12-25 00:00:00";
			String url = "/system/frame/classic/index.jsp";
			String token = TeePassEncryptMD5.crypt(uid+"ztoa"+expire);
			
			BASE64Encoder encoder = new BASE64Encoder();
			System.out.println("/sso/login.action?uid=admin&expire="+encoder.encodeBuffer(expire.getBytes()).replace("\r\n", "")+"&token="+token+"&url="+encoder.encodeBuffer(url.getBytes()).replace("\r\n", ""));
		}	

}
