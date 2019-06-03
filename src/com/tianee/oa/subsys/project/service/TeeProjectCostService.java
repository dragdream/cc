package com.tianee.oa.subsys.project.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.project.bean.TeeProject;
import com.tianee.oa.subsys.project.bean.TeeProjectCost;
import com.tianee.oa.subsys.project.bean.TeeProjectCostType;
import com.tianee.oa.subsys.project.model.TeeProjectCostModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
@Service
public class TeeProjectCostService extends TeeBaseService{

	@Autowired
	private TeeSmsManager smsManager;
	/**
	 * 增加项目预算
	 * @param request
	 * @return
	 */
	public TeeJson add(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		//获取页面上传来的项目的主键
		String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
		//获取页面上传来的金额
		double amount=TeeStringUtil.getDouble(request.getParameter("amount"),0);
        //获取页面上传来的审批人员主键
		int approverId=TeeStringUtil.getInteger(request.getParameter("approverId"),0);
		//获取页面上传来的描述
		String desc=TeeStringUtil.getString(request.getParameter("desc"));
		//获取页面上传来的类型主键
		int typeId=TeeStringUtil.getInteger(request.getParameter("costTypeId"),0);
	    TeePerson approver=(TeePerson) simpleDaoSupport.get(TeePerson.class,approverId);
	    TeeProjectCostType type=(TeeProjectCostType) simpleDaoSupport.get(TeeProjectCostType.class,typeId);
		
	    TeeProjectCost cost=new TeeProjectCost();
	    cost.setAmount(amount);
	    cost.setApprover(approver);
	    cost.setCostType(type);
	    cost.setCreater(loginUser);
        cost.setCreateTime(new Date());
        cost.setDescription(desc);
        cost.setProjectId(projectId);
        cost.setStatus(0);
		
		simpleDaoSupport.save(cost);
		
		
		// 发送消息
		Map requestData1 = new HashMap();
		requestData1.put("content", loginUser.getUserName()+"提交了费用申请单，请及时处理。");
		requestData1.put("userListIds", approverId);
		requestData1.put("moduleNo", "060");
		requestData1.put("remindUrl","/system/subsys/project/costApprove/index.jsp");
		smsManager.sendSms(requestData1, loginUser);
		
		
		
		
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}

	
	/**
	 * 根据项目主键   获取所有费用类型对应的金额 总数
	 * @param request
	 * @return
	 */
	public TeeJson getCostSumList(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的项目主键
		String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
		List<Map> result=new ArrayList<Map>();
		List<TeeProjectCostType> typeList=simpleDaoSupport.executeQuery(" from TeeProjectCostType ", null);
		if(typeList!=null&&typeList.size()>0){
			Map map=null;
			List <TeeProjectCost> costList=null;
			
			for (TeeProjectCostType type : typeList) {
				map=new HashMap();
				double sum=0;
				map.put("typeName",type.getTypeName());
				map.put("typeId", type.getSid());
				costList=simpleDaoSupport.executeQuery(" from TeeProjectCost where status=1 and costType.sid=? and projectId=?", new Object[]{type.getSid(),projectId});
				for (TeeProjectCost teeProjectCost : costList) {
					sum+=teeProjectCost.getAmount();
				}
				map.put("sum", sum);
				
				result.add(map);
			}
		}
	    json.setRtState(true);
	    json.setRtData(result);
		return json;
	}


	
	/**
	 * 根据项目主键 和 费用类型   获取审批通过的预算
	 * @param request
	 * @return
	 */
	public TeeJson getCostDetail(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的项目主键
		String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
		//获取页面上传来的费用类型主键
		int typeId=TeeStringUtil.getInteger(request.getParameter("typeId"),0);
		List<TeeProjectCost> costList=simpleDaoSupport.executeQuery(" from TeeProjectCost c where c.costType.sid=? and c.status=1 and c.projectId=? ", new Object[]{typeId,projectId});
		List<TeeProjectCostModel>modelList=new ArrayList<TeeProjectCostModel>();
		TeeProjectCostModel model=null;
		for (TeeProjectCost cost : costList) {
			model=parserToModel(cost);
			modelList.add(model);
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}


	
	/**
	 * 将实体类转换成model
	 * @param cost
	 * @return
	 */
	private TeeProjectCostModel parserToModel(TeeProjectCost cost) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeProjectCostModel model=new TeeProjectCostModel();
		BeanUtils.copyProperties(cost, model);
		if(cost.getApprover()!=null){
			model.setApproverId(cost.getApprover().getUuid());
			model.setApproverName(cost.getApprover().getUserName());
		}
		if(cost.getCostType()!=null){
			model.setCostTypeName(cost.getCostType().getTypeName());
			model.setCostTypeId(cost.getCostType().getSid());
		}
		if(cost.getCreater()!=null){
			model.setCreaterId(cost.getCreater().getUuid());
			model.setCreaterName(cost.getCreater().getUserName());
		}
		if(cost.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(cost.getCreateTime()));
		}
		if(!("").equals(cost.getProjectId())){
			Map m=simpleDaoSupport.executeNativeUnique(" select * from project where uuid=? ", new Object[]{cost.getProjectId()});
			model.setProjectName( TeeStringUtil.getString(m.get("PROJECT_NAME")));
		}
		return model;
	}


	
	/**
	 * 根据状态  获取审批列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getApproveListByStatus(
			HttpServletRequest request, TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		
		List param=new ArrayList();
		
		String hql=" from TeeProjectCost c where c.approver=? and status=?  ";
		param.add(loginUser);
		param.add(status);
	    
		long total=simpleDaoSupport.count(" select count(*) "+hql, param.toArray());
		json.setTotal(total);
		List<TeeProjectCost> list=simpleDaoSupport.executeQuery(hql,param.toArray());
		List<TeeProjectCostModel> modelList=new ArrayList<TeeProjectCostModel>();
		TeeProjectCostModel model=null;
		for (TeeProjectCost cost : list) {
			model=parserToModel(cost);
			modelList.add(model);
		}	
		json.setRows(modelList);
		return json;
	}


	
	/**
	 * 费用审批
	 * @param request
	 * @return
	 */
	public TeeJson approve(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		//获取页面上传来的状态 
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		//获取页面上传来的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		
		String refusedReason=TeeStringUtil.getString(request.getParameter("refusedReason"));
		if(sid>0){
			TeeProjectCost cost=(TeeProjectCost) simpleDaoSupport.get(TeeProjectCost.class,sid);
			if(cost!=null){
				cost.setStatus(status);
				cost.setRefusedReason(refusedReason);
				simpleDaoSupport.update(cost);
				
				TeeProject project=(TeeProject) simpleDaoSupport.get(TeeProject.class,cost.getProjectId());
				if(status==1){//同意
					// 发送消息
					Map requestData1 = new HashMap();
					requestData1.put("content", loginUser.getUserName()+"已同意您在项目“"+project.getProjectName()+"”中的费用申请。");
					requestData1.put("userListIds", cost.getCreater().getUuid());
					requestData1.put("moduleNo", "060");
					smsManager.sendSms(requestData1, loginUser);
				}else if(status==2){//拒绝
					// 发送消息
					Map requestData1 = new HashMap();
					requestData1.put("content", loginUser.getUserName()+"拒绝了您在项目“"+project.getProjectName()+"”中的费用申请，拒绝原因："+refusedReason+"。");
					requestData1.put("userListIds", cost.getCreater().getUuid());
					requestData1.put("moduleNo", "060");
					smsManager.sendSms(requestData1, loginUser);
					
					
				}
	
				
				json.setRtState(true);
				json.setRtMsg("操作成功！");
			}
		}else{
			json.setRtState(false);
			json.setRtMsg("数据不存在！");
		}
		return json;
	}


	
	/**
	 * 根据状态获取我的项目的列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getMyCostListByStatus(
			HttpServletRequest request, TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		
		List param=new ArrayList();
		
		String hql=" from TeeProjectCost c where c.creater=? and status=?  ";
		param.add(loginUser);
		param.add(status);
	    
		long total=simpleDaoSupport.count(" select count(*) "+hql, param.toArray());
		json.setTotal(total);
		List<TeeProjectCost> list=simpleDaoSupport.executeQuery(hql,param.toArray());
		List<TeeProjectCostModel> modelList=new ArrayList<TeeProjectCostModel>();
		TeeProjectCostModel model=null;
		for (TeeProjectCost cost : list) {
			model=parserToModel(cost);
			modelList.add(model);
		}	
		json.setRows(modelList);
		return json;
	}


	/**
	 * 根据主键删除费用申请
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeProjectCost cost=(TeeProjectCost) simpleDaoSupport.get(TeeProjectCost.class,sid);
		    simpleDaoSupport.deleteByObj(cost);
		    json.setRtState(true);
		    json.setRtMsg("删除成功！");
		}else{
	       json.setRtState(false);
	       json.setRtMsg("数据获取失败！");
		}
		return json;
	}


	/**
	 * 根据主键  获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeProjectCost cost=(TeeProjectCost) simpleDaoSupport.get(TeeProjectCost.class,sid);
			TeeProjectCostModel model=parserToModel(cost);
			json.setRtState(true);
			json.setRtData(model);
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}


	
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	public TeeJson update(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		//获取页面上传来的费用类型   金额     审批人    描述
		int typeId=TeeStringUtil.getInteger(request.getParameter("costTypeId"),0);
		double amount=TeeStringUtil.getDouble(request.getParameter("amount"), 0);
		int approverId=TeeStringUtil.getInteger(request.getParameter("approverId"),0);
		String description=TeeStringUtil.getString(request.getParameter("description"));
		if(sid>0){
			TeeProjectCost cost=(TeeProjectCost) simpleDaoSupport.get(TeeProjectCost.class,sid);
			cost.setApprover((TeePerson)simpleDaoSupport.get(TeePerson.class,approverId));
		    cost.setCostType((TeeProjectCostType)simpleDaoSupport.get(TeeProjectCostType.class,typeId));
			cost.setAmount(amount);
		    cost.setDescription(description);
		    simpleDaoSupport.update(cost);
		    json.setRtState(true);
		    json.setRtMsg("修改成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		
		return json;
	}

}
