package com.tianee.oa.core.system.attachCenter.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.bean.TeeAttachmentSpace;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.system.attachCenter.service.TeeAttachCenterService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/attachCenterController")
public class TeeAttachCenterController {
	@Autowired
	private TeeAttachCenterService service;
	
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * @function: 获取各模块所占空间比例
	 * @author: wyw
	 * @data: 2014年10月9日
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 *             TeeJson
	 */
	@RequestMapping("/getAttachSeries")
	@ResponseBody
	public TeeJson getAttachSeries(HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.getAttachSeries(requestMap, loginPerson);
		return json;
	}

	/**
	 * @function: 获取附件模块列表
	 * @author: wyw
	 * @data: 2014年10月12日
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 *             TeeJson
	 */
	@RequestMapping("/getAttachSeriesList")
	@ResponseBody
	public TeeJson getAttachSeriesList(HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.getAttachSeriesList(requestMap, loginPerson);
		return json;
	}
	
	/**
	 * 获取附件空间列表
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getAttachSpaces")
	@ResponseBody
	public TeeJson getAttachSpaces(HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeJson json = new TeeJson();
		json = service.getAttachSpaces();
		return json;
	}
	/**
	 * 获取附件空间信息
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getAttachSpace")
	@ResponseBody
	public TeeJson getAttachSpace(HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeJson json = new TeeJson();
		int sid  = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json = service.getAttachSpace(sid);
		return json;
	}
	/**
	 *添加附件空间
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/addAttachSpace")
	@ResponseBody
	public TeeJson addAttachSpace(HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeJson json = new TeeJson();
		TeeAttachmentSpace space = new TeeAttachmentSpace();
		String spacePath = TeeStringUtil.getString(request.getParameter("spacePath"),"");
		space.setSpacePath(spacePath);
		space.setStatus(1);
		space.setWeight(1);
		json = service.addAttachSpace(space);
		return json;
	}
	/**
	 * 更新
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/updateAttachSpace")
	@ResponseBody
	public TeeJson updateAttachSpace(HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeJson json = new TeeJson();
		int sid  = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String spacePath = TeeStringUtil.getString(request.getParameter("spacePath"),"");
		TeeAttachmentSpace space = new TeeAttachmentSpace();
		space.setSid(sid);
		space.setSpacePath(spacePath);
		space.setStatus(1);
		space.setWeight(1);
		json = service.updateAttachSpace(space);
		return json;
	}
	
	/**
	 * 设置为默认附件空间
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/setDefault")
	@ResponseBody
	public TeeJson setDefault(HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeJson json = new TeeJson();
		int sid  = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json = service.setDefault(sid);
		return json;
	}
	
	
	/**
	 * 清理没用的附件
	 * @param attachIds
	 * @return
	 */
	@RequestMapping("/clearUnusefulAttachment")
	@ResponseBody
	public TeeJson clearUnusefulAttachment(HttpServletRequest request){
		
		Session session = null;
		try{
			session = sessionFactory.openSession();
			
			Query query = session.createQuery("select count(sid) from TeeAttachment attach where attach.model='email' and attach.modelId IS NULL");
			
			//查询出垃圾邮件附件的数量
			int count = TeeStringUtil.getInteger(query.uniqueResult(), 0);
			List<TeeAttachment> emailAttaches =null;
			File file = null;//删除附件，每1000条清理
			for(int i=0;i<count;i+=1000){
				query = session.createQuery("from TeeAttachment attach where attach.model='email' and attach.modelId IS NULL");
				query.setFirstResult(i);
				query.setMaxResults(i+1000);
				
				emailAttaches = query.list();
				for(TeeAttachment attach:emailAttaches){
					file = new File(attach.getAttachSpace().getSpacePath()+"/"+attach.getModel()+"/"+attach.getAttachmentPath()+"/"+attach.getAttachmentName());
					if(file.exists()){
						file.delete();
					}
					file = null;
				}
				session.clear();
			}
			
			query = session.createQuery("from TeeAttachment attach where attach.model='email' and attach.modelId IS NULL");
			query.setFirstResult(0);
			query.setMaxResults(1000);
			emailAttaches = query.list();
			for(TeeAttachment attach:emailAttaches){
				file = new File(attach.getAttachSpace().getSpacePath()+"/"+attach.getModel()+"/"+attach.getAttachmentPath()+"/"+attach.getAttachmentName());
				if(file.exists()){
					file.delete();
				}
				file = null;
			}
			session.clear();
			
			//删除附件记录
			session.beginTransaction();
			query = session.createQuery("delete from TeeAttachment attach where attach.model='email' and attach.modelId IS NULL");
			query.executeUpdate();
			session.getTransaction().commit();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			session.close();
		}
		
		
		return new TeeJson();
	}

	
	
	/**
	 * 清理无效的附件 
	 * @param attachIds
	 * @return
	 */
	@RequestMapping("/clearUnusefulAttaches")
	@ResponseBody
	public void clearUnusefulAttaches(HttpServletRequest request){
		while(true){
			int maxResult=500;
			Session session = null;
			try{
				session = sessionFactory.openSession();
				String hql="from TeeAttachment attach where attach.model='email' and attach.modelId IS NULL";
				Query query = session.createQuery(hql);
				query.setFirstResult(0);
				query.setMaxResults(maxResult);
				//查询出垃圾邮件附件的数量
				int count = TeeStringUtil.getInteger(query.list().size(), 0);
				List<TeeAttachment> emailAttaches =null;
				File file = null;//删除附件
				
				//即将删除的id字符串
				String ids="(";
				if(count>0){
					System.out.println("删除的数量"+count);
					
						emailAttaches = query.list();
						for(TeeAttachment attach:emailAttaches){
							ids+=attach.getSid()+",";
							file = new File(attach.getAttachSpace().getSpacePath()+"/"+attach.getModel()+"/"+attach.getAttachmentPath()+"/"+attach.getAttachmentName());
							if(file.exists()){
								file.delete();
							}
							file = null;
						}
//						session.flush();
//						session.clear();
					
					
					if(ids.endsWith(",")){
						ids=ids.substring(0, ids.length()-1);
					}
					ids=ids+")";
					//删除附件记录
					session.beginTransaction();
					query = session.createQuery("delete from TeeAttachment attach where attach.sid in "+ids );
					query.executeUpdate();
					session.getTransaction().commit();	
					
					//关闭session	
					session.close();
					
				}else{	
					System.out.println("删除完");
					break;
				}
				
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				//session.close();
			}
			
		}
	}
	
	
}
