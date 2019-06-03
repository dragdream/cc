package com.tianee.oa.core.general.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeeMobileSeal;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.dynamic.TeeClassRunner;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeMobileSealService extends TeeBaseService{
	
	public void addOrUpdate(TeeMobileSeal mobileSeal){
		//update
		if(mobileSeal.getUuid()!=null && !"".equals(mobileSeal.getUuid())){
			TeeMobileSeal mobileSeal2 = 
					(TeeMobileSeal) simpleDaoSupport.get(TeeMobileSeal.class, mobileSeal.getUuid());
			
			mobileSeal2.setUserId(mobileSeal.getUserId());
			mobileSeal2.setDeviceNo(mobileSeal.getDeviceNo());
			mobileSeal2.setSealName(mobileSeal.getSealName());
			if(mobileSeal.getSealData()!=null){
				mobileSeal2.setSealData(mobileSeal.getSealData());
			}
			mobileSeal2.setPwd(mobileSeal.getPwd());
			simpleDaoSupport.update(mobileSeal2);
		}else{//save
			
			int mobileSealLimit = 0;
			long mobileSealCount = 0;
			//做手机签章控制限制
			try {
				mobileSealLimit = (Integer) TeeClassRunner.exec("com.tianee.webframe.util.auth.TeeAuthUtil", "getMobilSealLimit", null);
				mobileSealCount = simpleDaoSupport.count("select count(uuid) from TeeMobileSeal", null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(mobileSealCount>=mobileSealLimit){
				throw new TeeOperationException("手机签章数量超过系统限制最大值"+mobileSealLimit);
			}
			
			simpleDaoSupport.save(mobileSeal);
		}
	}
	
	public void delete(String uuid){
		simpleDaoSupport.delete(TeeMobileSeal.class, uuid);
	}
	
	public void setFlag(String uuid,int flag){
		simpleDaoSupport.executeUpdate("update TeeMobileSeal set flag=? where uuid=?",new Object[]{flag,uuid});
	}
	
	public TeeMobileSeal get(String uuid){
		return (TeeMobileSeal) simpleDaoSupport.get(TeeMobileSeal.class, uuid);
	}
	
	/**
	 * 
	 * @param uuid 签章主键
	 * @param userId	用户id
	 * @param deviceNo	设备号
	 */
	public void binding(String uuid,int userId,String deviceNo,String pwd){
		TeeMobileSeal mobileSeal = (TeeMobileSeal) simpleDaoSupport.get(TeeMobileSeal.class, uuid);
		mobileSeal.setDeviceNo(deviceNo);
		mobileSeal.setPwd(pwd);
	}
	
	/**
	 * 将签章密码和设备号清零
	 * @param uuid
	 */
	public void reset(String uuid){
		TeeMobileSeal mobileSeal = (TeeMobileSeal) simpleDaoSupport.get(TeeMobileSeal.class, uuid);
		mobileSeal.setDeviceNo("");
		mobileSeal.setPwd("");
	}
	
	/**
	 * 列出所有签章组件
	 * @param requestData
	 * @return
	 */
	public TeeEasyuiDataGridJson list(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from TeeMobileSeal ";
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		
		dataGridJson.setTotal(total);
		List<TeeMobileSeal> list = simpleDaoSupport.pageFind(hql+" order by crTime asc", dm.getFirstResult(), dm.getRows(), null);
		List<Map> modelList = new ArrayList();
		TeePerson p = null;
		for(TeeMobileSeal mobileSeal:list){
			Map data = new HashMap();
			data.put("uuid", mobileSeal.getUuid());
			p = (TeePerson) simpleDaoSupport.get(TeePerson.class, mobileSeal.getUserId());
			data.put("userId", mobileSeal.getUserId());
			data.put("userName", p.getUserName());
			data.put("sealData", mobileSeal.getSealData());
			data.put("deviceNo", mobileSeal.getDeviceNo());
			data.put("flag", mobileSeal.getFlag());
			data.put("sealName", mobileSeal.getSealName());
			data.put("pwd", mobileSeal.getPwd());
			
			modelList.add(data);
		}
		
		dataGridJson.setRows(modelList);
		
		return dataGridJson;
	}
	
	
	public TeeEasyuiDataGridJson myMobileSeals(Map requestData){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		
		String hql = "from TeeMobileSeal where userId="+loginUser.getUuid();
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		
		dataGridJson.setTotal(total);
		List<TeeMobileSeal> list = simpleDaoSupport.find(hql+" order by crTime asc", null);
		List<Map> modelList = new ArrayList();
		TeePerson p = null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(TeeMobileSeal mobileSeal:list){
			Map data = new HashMap();
			data.put("uuid", mobileSeal.getUuid());
			p = (TeePerson) simpleDaoSupport.get(TeePerson.class, mobileSeal.getUserId());
			data.put("userId", mobileSeal.getUserId());
			data.put("userName", p.getUserName());
//			data.put("sealData", mobileSeal.getSealData());
			data.put("deviceNo", mobileSeal.getDeviceNo());
			data.put("flag", mobileSeal.getFlag());
			data.put("sealName", mobileSeal.getSealName());
			String crTimeDesc=sdf.format(mobileSeal.getCrTime().getTime());
			data.put("crTime", crTimeDesc);
			
			modelList.add(data);
		}
		
		dataGridJson.setRows(modelList);
		
		return dataGridJson;
	}
	
	public TeeEasyuiDataGridJson myMobileSealsWithData(Map requestData){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		
		String hql = "from TeeMobileSeal where userId="+loginUser.getUuid()+" and flag=1";
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		
		dataGridJson.setTotal(total);
		List<TeeMobileSeal> list = simpleDaoSupport.find(hql+" order by crTime asc", null);
		List<Map> modelList = new ArrayList();
		TeePerson p = null;
		for(TeeMobileSeal mobileSeal:list){
			Map data = new HashMap();
			data.put("uuid", mobileSeal.getUuid());
			p = (TeePerson) simpleDaoSupport.get(TeePerson.class, mobileSeal.getUserId());
			data.put("userId", mobileSeal.getUserId());
			data.put("userName", p.getUserName());
			data.put("sealData", mobileSeal.getSealData());
			data.put("deviceNo", mobileSeal.getDeviceNo());
			data.put("flag", mobileSeal.getFlag());
			data.put("sealName", mobileSeal.getSealName());
			data.put("pwd", mobileSeal.getPwd());
			
			modelList.add(data);
		}
		
		dataGridJson.setRows(modelList);
		
		return dataGridJson;
	}
	
	/**
	 * 移动端绑定
	 * @param requestData
	 * @return
	 */
	public TeeEasyuiDataGridJson myMobileSealsWithDataForMobile(Map requestData){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		String deviceNo = TeeStringUtil.getString(requestData.get("deviceNo"));
		
//		String hql = "from TeeMobileSeal where userId="+loginUser.getUuid()+" and deviceNo='"+deviceNo+"'";
		String hql = "from TeeMobileSeal where userId="+loginUser.getUuid()+" ";
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		
		dataGridJson.setTotal(total);
		List<TeeMobileSeal> list = simpleDaoSupport.find(hql+" order by crTime asc", null);
		List<Map> modelList = new ArrayList();
		TeePerson p = null;
		for(TeeMobileSeal mobileSeal:list){
			Map data = new HashMap();
			data.put("uuid", mobileSeal.getUuid());
			p = (TeePerson) simpleDaoSupport.get(TeePerson.class, mobileSeal.getUserId());
			data.put("userId", mobileSeal.getUserId());
			data.put("userName", p.getUserName());
			data.put("sealData", mobileSeal.getSealData());
			data.put("deviceNo", mobileSeal.getDeviceNo());
			data.put("flag", mobileSeal.getFlag());
			data.put("sealName", mobileSeal.getSealName());
			data.put("pwd", mobileSeal.getPwd());
			
			modelList.add(data);
		}
		
		dataGridJson.setRows(modelList);
		
		return dataGridJson;
	}

	
	/**
	 * 
	 * 修改图片签章的密码
	 * @param request
	 * @return
	 */
	public TeeJson updatePwd(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//或企业页面上传来的主键
		String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
		TeeMobileSeal mobileSeal=(TeeMobileSeal) simpleDaoSupport.get(TeeMobileSeal.class,uuid);
		//获取页面上传来的新密码
		String newPwd=TeeStringUtil.getString(request.getParameter("pwd"));
		mobileSeal.setPwd(newPwd);
		simpleDaoSupport.update(mobileSeal);
		json.setRtState(true);
		return json;
	}

	
	/**
	 * 根据签章主键  获取签章详情
	 * @param string
	 * @return
	 */
	public Map getByUuid(String uuid) {
		Map map=new HashMap();
		TeeMobileSeal mobileSeal= (TeeMobileSeal) simpleDaoSupport.get(TeeMobileSeal.class, uuid);
		
		TeePerson user=(TeePerson) simpleDaoSupport.get(TeePerson.class,mobileSeal.getUserId());
	    map.put("userName", user.getUserName());
	    map.put("userId", mobileSeal.getUserId());
	    map.put("sealData", mobileSeal.getSealData());
	    map.put("deviceNo", mobileSeal.getDeviceNo());
	    map.put("flag", mobileSeal.getFlag());
	    map.put("sealName", mobileSeal.getSealName());
	    map.put("pwd", mobileSeal.getPwd());
	    map.put("uuid", mobileSeal.getUuid());
		return map;
	}
}
