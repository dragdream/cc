package com.tianee.oa.mobile.manage.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.bjca.mssp.msspjce.asn1.ocsp.Request;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.mobile.manage.bean.TeeMobileModule;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeMobileModuleService extends TeeBaseService{
	
	public void addOrUpdate(Map params){
		int type=TeeStringUtil.getInteger(params.get("type"), 0);//所属分类  1=OA类  2=执法类
		int sid = TeeStringUtil.getInteger(params.get("sid"), 0);
		int isEdit = TeeStringUtil.getInteger(params.get("isEdit"), 0);
		String appName = TeeStringUtil.getString(params.get("appName"));
		String url = TeeStringUtil.getString(params.get("url"));
		String viewId = TeeStringUtil.getString(params.get("viewId"));
		String pic = TeeStringUtil.getString(params.get("pic"));
		String desc = TeeStringUtil.getString(params.get("desc"));
		String deptPriv = TeeStringUtil.getString(params.get("deptPriv"));
		String deptPrivDesc = TeeStringUtil.getString(params.get("deptPrivDesc"));
		String userPriv = TeeStringUtil.getString(params.get("userPriv"));
		String userPrivDesc = TeeStringUtil.getString(params.get("userPrivDesc"));
		String rolePriv = TeeStringUtil.getString(params.get("rolePriv"));
		String rolePrivDesc = TeeStringUtil.getString(params.get("rolePrivDesc"));
		String managePriv = TeeStringUtil.getString(params.get("managePriv"));
		String managePrivDesc = TeeStringUtil.getString(params.get("managePrivDesc"));
		
		TeeMobileModule mobileModule = null;
		Long sort = 0L;
		if(isEdit!=0){
			mobileModule = (TeeMobileModule) simpleDaoSupport.get(TeeMobileModule.class, sid);
		}else{
			mobileModule = new TeeMobileModule();
		}
		
		
		sort = simpleDaoSupport.count("select count(sid)+1 from TeeMobileModule", new Object[]{});
		if(isEdit!=0){
			
		}else{
			sort++;
			mobileModule.setSort(TeeStringUtil.getInteger(sort, 0));
		}
		
		mobileModule.setType(type);
		mobileModule.setAppName(appName);
		mobileModule.setViewId(viewId);
		
		
		StringBuffer sb = new StringBuffer();
		String deptPrivSp [] = deptPriv.split(",");
		for(String sp:deptPrivSp){
			if(!sp.equals("")){
				sb.append("/"+sp+".d");
			}
		}
		mobileModule.setDeptPriv(sb.toString());
		sb.delete(0, sb.length());
		
		String rolePrivSp [] = rolePriv.split(",");
		for(String sp:rolePrivSp){
			if(!sp.equals("")){
				sb.append("/"+sp+".r");
			}
		}
		mobileModule.setRolePriv(sb.toString());
		sb.delete(0, sb.length());

		String userPrivSp [] = userPriv.split(",");
		for(String sp:userPrivSp){
			if(!sp.equals("")){
				sb.append("/"+sp+".u");
			}
		}
		mobileModule.setUserPriv(sb.toString());
		
		userPrivSp = managePriv.split(",");
		for(String sp:userPrivSp){
			if(!sp.equals("")){
				sb.append("/"+sp+".u");
			}
		}
		mobileModule.setManagePriv(sb.toString());

		mobileModule.setDeptPrivDesc(deptPrivDesc);
		mobileModule.setDesc(desc);
		mobileModule.setPic(pic);
		mobileModule.setUserPrivDesc(userPrivDesc);
		mobileModule.setRolePrivDesc(rolePrivDesc);
		mobileModule.setManagePrivDesc(managePrivDesc);
		mobileModule.setUrl(url);
		
		
		if(isEdit!=0){
			simpleDaoSupport.update(mobileModule);
		}else{
			mobileModule.setSid(sid);
			simpleDaoSupport.save(mobileModule);
		}
		
	}
	
	public void del(int sid){
		simpleDaoSupport.delete(TeeMobileModule.class, sid);
	}
	
	public void sorting(String ids){
		String sp[] = ids.split(",");
		for(int i=0;i<sp.length;i++){
			simpleDaoSupport.executeUpdate("update TeeMobileModule set sort="+(i+1)+" where sid="+sp[i], null);
		}
	}
	
	@Transactional(readOnly=true)
	public TeeMobileModule get(int sid){
		TeeMobileModule mobileModule = (TeeMobileModule) simpleDaoSupport.get(TeeMobileModule.class, sid);
		if (mobileModule.getDeptPriv() != null) {
			mobileModule.setDeptPriv(mobileModule.getDeptPriv()
					.replace("/", ",").replace(".d", ""));
			if (mobileModule.getDeptPriv().startsWith(",")) {
				mobileModule.setDeptPriv(mobileModule.getDeptPriv()
						.substring(1));
			}
		}
		if (mobileModule.getUserPriv() != null) {
			mobileModule.setUserPriv(mobileModule.getUserPriv()
					.replace("/", ",").replace(".u", ""));
			if (mobileModule.getUserPriv().startsWith(",")) {
				mobileModule.setUserPriv(mobileModule.getUserPriv()
						.substring(1));
			}
		}
		if (mobileModule.getRolePriv() != null) {
			mobileModule.setRolePriv(mobileModule.getRolePriv()
					.replace("/", ",").replace(".r", ""));
			if (mobileModule.getRolePriv().startsWith(",")) {
				mobileModule.setRolePriv(mobileModule.getRolePriv()
						.substring(1));
			}
		}
		if (mobileModule.getManagePriv() != null) {
			mobileModule.setManagePriv(mobileModule.getManagePriv()
					.replace("/", ",").replace(".u", ""));
			if (mobileModule.getManagePriv().startsWith(",")) {
				mobileModule.setManagePriv(mobileModule.getManagePriv()
						.substring(1));
			}
		}
		
		
		return mobileModule;
	}
	
	public List<TeeMobileModule> list(){
		List<TeeMobileModule> list = simpleDaoSupport.find("from TeeMobileModule order by sort asc", new Object[]{});
		return list;
	}
	
	
	@Transactional(readOnly=true)
	public boolean checkManagePriv(int sid,TeePerson person){
		TeeMobileModule mobileModule = get(sid);
		if(mobileModule.getManagePriv()!=null){
			if(TeeStringUtil.existString(mobileModule.getManagePriv().split(","), person.getUuid()+"")){
				return true;
			}
		}
		return false;
	}

	
	/**
	 * 验证id标识是否已经存在
	 * @param request
	 * @return
	 */
	public TeeJson checkIdIsExists(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		
		String hql=" from TeeMobileModule where sid=? ";
		List<TeeMobileModule> list=simpleDaoSupport.executeQuery(hql, new Object[]{sid});
		if(list!=null&&list.size()>0){
			json.setRtData(1);
		}else{
			json.setRtData(0);
		}
		json.setRtState(true);
		return json;
	}
}
