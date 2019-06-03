package com.tianee.oa.core.general.controller;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;

import com.tianee.oa.core.general.bean.TeeSeal;
import com.tianee.oa.core.general.model.TeeSealModel;
import com.tianee.oa.core.general.service.TeeSealService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/sealManage")
public class TeeSealController  {
	@Autowired
	private TeeSealService sealService;
	
	@Autowired 
	TeePersonDao personDao;

	/**
	 * 新增或者更新
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdateSeal")
	@ResponseBody
	public TeeJson addUpdateSeal(HttpServletRequest request, TeeSealModel model)
			throws Exception {
		TeeJson json = new TeeJson();
		sealService.addOrUpdate( request,model);
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 查询
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectById")
	@ResponseBody
	public TeeJson selectById(HttpServletRequest request, TeeSealModel model)
			throws Exception {
		TeeJson json = new TeeJson();
		TeeSeal seal = sealService.getById(model.getSid());
		if(seal != null ){
			String userStr = seal.getUserStr();
			
			BeanUtils.copyProperties(seal, model);
			if(!TeeUtility.isNullorEmpty(userStr)){
				String userIds = "";
				String userNames = "";
				List<TeePerson> list = personDao.getPersonByUuids(userStr);
				for (int i = 0; i < list.size(); i++) {
					userIds = userIds + list.get(i).getUuid() + ",";
					userNames = userNames + list.get(i).getUserName() + ",";
				}
				model.setUserStr(userIds);
				model.setUserStrDesc(userNames);
			}
			json.setRtData(model);
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}
	
	
	/**
	 * 获取最大sealId
	 * @param request
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectMaxSealId")
	@ResponseBody
	public TeeJson selectMaxSealId(HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		String sealIdPre = request.getParameter("sealIdPre");
		String maxSealId  = sealService.selectMaxSealid(sealIdPre);
		json.setRtState(true);
		json.setRtData(maxSealId);
		return json;
	}
	
	
	
	/**
	 * 获取通用列表
	 * @param dm
	 * @param portal
	 * @param response
	 * @return
	 */
	@RequestMapping("/getSealList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm ,HttpServletRequest request, TeeSealModel model) {
		return sealService.datagrid(dm,request,model);
	}

	/**
	 * 删除
	 * @param request
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delSeal")
	@ResponseBody
	public TeeJson delSeal(HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		String sids = request.getParameter("sids");
		sealService.delBySids(sids ,  request);
		json.setRtState(true);
		json.setRtData("");
		return json;
	}
	
	
	/**
	 * 更新 启用或者停用印章
	 *  * @param request
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openOrstopSeal")
	@ResponseBody
	public TeeJson openOrstopSeal(HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
	
		String sids = request.getParameter("sids");
		String isFlag = request.getParameter("isFlag");
		isFlag = TeeStringUtil.getString(isFlag, "0");
		int count  = sealService.openOrstopSeal(sids , isFlag , request);
		json.setRtState(true);
		json.setRtData(count);
		return json;
	}
	
	
	
	/**
	 * 更新印章使用权限
	 *  * @param request
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/setSealPriv")
	@ResponseBody
	public TeeJson setSealPriv(HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		String sid = request.getParameter("sid");
		String userStr = request.getParameter("userStr");
		userStr = TeeStringUtil.getString(userStr, "");
		int count  = sealService.setSealPriv(sid , userStr , request);
		json.setRtState(true);
		json.setRtData(count);
		return json;
	}
	
	/**
	 *获取有盖章权限的印章
	 *  * @param request
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getHavePrivStamp")
	@ResponseBody
	public TeeJson getHavePrivStamp(HttpServletRequest request , HttpServletResponse response)throws Exception {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = sealService.getHavePrivSeal(person);
		return json ;
	}
	
	/**
	 *获取有盖章权限的印章 -- 信息全
	 *  * @param request
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getHavePrivSealInfo")
	@ResponseBody
	public TeeEasyuiDataGridJson getHavePrivSealInfo(HttpServletRequest request , TeeDataGridModel dm)throws Exception {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return sealService.getHavePrivSealInfo(person,dm) ;
	}
	
	
	
	/**
	 *  进行盖章
	 * @param request
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/stampPriv")
	public String stampPriv(HttpServletRequest request , HttpServletResponse response)throws Exception {
		
		TeeJson json = new TeeJson();
		String sid = request.getParameter("sid");
		int id = TeeStringUtil.getInteger(sid, 0);
		 BASE64Decoder de = new BASE64Decoder();
	     response.setContentType("application/octet-stream");      
	     response.setHeader("Content-Disposition", "attachment;filename=seal.sel");
	     TeeSeal seal  = sealService.loadById(id);  
	     if(seal == null ){
	         return null;
	     }
	         
	     byte[] b =  de.decodeBuffer(seal.getSealData());  ;  
	     OutputStream out = response.getOutputStream();  
	     int readLength = 0;
	     out.write(b,0,b.length);
	     out.flush();
		return null ;
	}

	
	public void setSealService(TeeSealService sealService) {
		this.sealService = sealService;
	}


	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}
	
	
}



