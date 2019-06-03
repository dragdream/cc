package com.tianee.oa.core.partthree.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeePartThreeService extends TeeBaseService{

	@Autowired
	private TeeSysParaService sysParaService;
	
	@Autowired
	private TeePersonService personService;
	
	/**
	 * 保存
	 * @param request
	 * @return
	 */
	public TeeJson doSave(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		
		//是否开启三员设置
    	String IS_OPEN_PART_THREE = request.getParameter("IS_OPEN_PART_THREE");
    	if(IS_OPEN_PART_THREE != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("IS_OPEN_PART_THREE");
    		sysPara.setParaValue(IS_OPEN_PART_THREE);
    		sysParaService.addUpdatePara(sysPara);
    	}
    	
    	//系统管理员权限
    	String ADMIN_PRIV = request.getParameter("ADMIN_PRIV");
    	if(ADMIN_PRIV != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("ADMIN_PRIV");
    		sysPara.setParaValue(ADMIN_PRIV);
    		sysParaService.addUpdatePara(sysPara);
    	}
    	
    	
    	//系统安全员权限
    	String SAFER_PRIV = request.getParameter("SAFER_PRIV");
    	if(SAFER_PRIV != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("SAFER_PRIV");
    		sysPara.setParaValue(SAFER_PRIV);
    		sysParaService.addUpdatePara(sysPara);
    	}
    	
    	
    	//安全审计员权限
    	String AUDITOR_PRIV = request.getParameter("AUDITOR_PRIV");
    	if(AUDITOR_PRIV != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("AUDITOR_PRIV");
    		sysPara.setParaValue(AUDITOR_PRIV);
    		sysParaService.addUpdatePara(sysPara);
    	}
    	
    	
    	//新建用户默认的菜单权限组
    	//安全审计员权限
    	String PART_THREE_DEFAULT_PRIV = request.getParameter("PART_THREE_DEFAULT_PRIV");
    	if(PART_THREE_DEFAULT_PRIV != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("PART_THREE_DEFAULT_PRIV");
    		sysPara.setParaValue(PART_THREE_DEFAULT_PRIV);
    		sysParaService.addUpdatePara(sysPara);
    	}
    	
    	json.setRtState(true);
		return json;
	}

	
	
	/**
	 * 获取初始化的数据
	 * @param request
	 * @return
	 */
	public TeeJson getInitData(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		Map<String,Object> data=new HashMap<String,Object>();
		String paraNames="'IS_OPEN_PART_THREE','ADMIN_PRIV','SAFER_PRIV','AUDITOR_PRIV','PART_THREE_DEFAULT_PRIV'";
		String hql=" from TeeSysPara where paraName in (" + paraNames + ") ";
		List<TeeSysPara> list=simpleDaoSupport.executeQuery(hql,null);
		if(list!=null&&list.size()>0){
			for (TeeSysPara teeSysPara : list) {
				data.put(teeSysPara.getParaName(), teeSysPara.getParaValue());
			}
		}
		
		//处理人员信息
		String ADMIN_PRIV=TeeStringUtil.getString(data.get("ADMIN_PRIV"));
		String ADMIN_PRIV_STR=personService.getPersonNameAndUuidByUuids(ADMIN_PRIV)[1];
		
		String SAFER_PRIV=TeeStringUtil.getString(data.get("SAFER_PRIV"));
		String SAFER_PRIV_STR=personService.getPersonNameAndUuidByUuids(SAFER_PRIV)[1];
		
		
		String AUDITOR_PRIV=TeeStringUtil.getString(data.get("AUDITOR_PRIV"));
		String AUDITOR_PRIV_STR=personService.getPersonNameAndUuidByUuids(AUDITOR_PRIV)[1];
		
		data.put("ADMIN_PRIV_STR", ADMIN_PRIV_STR);
		data.put("SAFER_PRIV_STR", SAFER_PRIV_STR);
		data.put("AUDITOR_PRIV_STR", AUDITOR_PRIV_STR);
		json.setRtState(true);
		json.setRtData(data);
		return  json;
	}



	/**
	 * 获取超级密码
	 * @param request
	 * @return
	 */
	public TeeJson getPartThreePwd(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String hql=" from TeeSysPara where paraName = 'PART_THREE_PWD'";
		TeeSysPara sysPara=(TeeSysPara) simpleDaoSupport.unique(hql, null);
		if(sysPara!=null){
			json.setRtData(sysPara.getParaValue());
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}



	/**
	 * 修改超级密码
	 * @param request
	 * @return
	 */
	public TeeJson updatePartThreePwd(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取新密码
		String newPwd=TeeStringUtil.getString(request.getParameter("newPwd"));
		String hql=" update TeeSysPara set paraValue=? where paraName='PART_THREE_PWD' ";
		simpleDaoSupport.executeUpdate(hql, new Object[]{newPwd});
		json.setRtState(true);
		return json;
	}



	/**
	 * 验证超级密码
	 * @param request
	 * @return
	 */
	public TeeJson checkPwd(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的pwd
		String pwd=TeeStringUtil.getString(request.getParameter("pwd"));
		String hql=" from TeeSysPara where paraName = 'PART_THREE_PWD'";
		TeeSysPara sysPara=(TeeSysPara) simpleDaoSupport.unique(hql, null);
		if(sysPara!=null){
			if(pwd.equals(sysPara.getParaValue())){
				request.getSession().setAttribute("pwdFlag", "1");
				json.setRtState(true);
			}else{
				json.setRtState(false);
			}
		}else{
			json.setRtState(false);
		}
		
		
		return json;
	}

}
