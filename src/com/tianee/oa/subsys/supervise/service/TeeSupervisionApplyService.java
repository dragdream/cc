package com.tianee.oa.subsys.supervise.service;

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
import com.tianee.oa.subsys.supervise.bean.TeeSupervision;
import com.tianee.oa.subsys.supervise.bean.TeeSupervisionApply;
import com.tianee.oa.subsys.supervise.model.TeeSupervisionApplyModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeSupervisionApplyService extends TeeBaseService{

	@Autowired
	private TeeSmsManager smsManager;
	
	/**
	 * 新增记录
	 * @param request
	 * @return
	 */
	public TeeJson add(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		//获取页面上传来的数据
		String content=TeeStringUtil.getString(request.getParameter("content"));
		int supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
		TeeSupervision sup=(TeeSupervision) simpleDaoSupport.get(TeeSupervision.class,supId);
		int type=TeeStringUtil.getInteger(request.getParameter("type"), 0);
		
		TeeSupervisionApply apply=new TeeSupervisionApply();
	    apply.setContent(content);
	    apply.setCreateTime(new Date());
	    apply.setCreater(loginUser);
        apply.setStatus(0);
        apply.setSup(sup);
        apply.setType(type);
        
        simpleDaoSupport.save(apply);
        
        
        if(sup!=null){
        	//修改对应的任务状态
            if(type==1){//暂停
            	sup.setStatus(2);//暂停申请中
            	
            	if(sup.getLeader()!=null){
            		//发消息
                	Map requestData1 = new HashMap();
                	requestData1.put("content", "“"+loginUser.getUserName()+"”在督办任务《"+sup.getSupName()+"》中“申请暂停”，请进行审批。");
                	requestData1.put("userListIds", sup.getLeader().getUuid());
                	requestData1.put("moduleNo", "061");
                	requestData1.put("remindUrl","/system/subsys/supervise/sms/pauseRecoverApplyRecords.jsp?supId="+sup.getSid());
                	smsManager.sendSms(requestData1, loginUser);
            	}
            	
            }else if(type==2){//恢复        
            	sup.setStatus(4);//恢复申请中
            	
            	if(sup.getLeader()!=null){
            		//发消息
                	Map requestData1 = new HashMap();
                	requestData1.put("content", "“"+loginUser.getUserName()+"”在督办任务《"+sup.getSupName()+"》中“申请恢复”，请进行审批。");
                	requestData1.put("userListIds", sup.getLeader().getUuid());
                	requestData1.put("moduleNo", "061");
                	requestData1.put("remindUrl","/system/subsys/supervise/sms/pauseRecoverApplyRecords.jsp?supId="+sup.getSid());
                	smsManager.sendSms(requestData1, loginUser);
            	}
            	
            }else if(type==3){//办结 
            	sup.setStatus(5);//办结申请中
            	
            	if(sup.getLeader()!=null){
            		//发消息
                	Map requestData1 = new HashMap();
                	requestData1.put("content", "“"+loginUser.getUserName()+"”在督办任务《"+sup.getSupName()+"》中“申请办结”，请进行审批。");
                	requestData1.put("userListIds", sup.getLeader().getUuid());
                	requestData1.put("moduleNo", "061");
                	requestData1.put("remindUrl","/system/subsys/supervise/sms/endApplyRecords.jsp?supId="+sup.getSid());
                	smsManager.sendSms(requestData1, loginUser);
            	}
            	
            }
            
            simpleDaoSupport.update(sup);
        }
        
        
        
        
        json.setRtState(true);
        json.setRtMsg("申请成功！");
		
		return json;
	}

	
	/**
	 * 根据任务主键    获取暂停 恢复申请记录
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getPauseOrRecoverApplyListBySupId(
			TeeDataGridModel dm, HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int supId=TeeStringUtil.getInteger(request.getParameter("supId"), 0);
		String hql = " from TeeSupervisionApply where sup.sid=? and type in(1,2)";
		List param = new ArrayList();
		param.add(supId);
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeSupervisionApply> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeSupervisionApplyModel> modelList = new ArrayList<TeeSupervisionApplyModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeSupervisionApplyModel modeltemp = parseToModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}


	
	/**
	 * 将实体类转换成model
	 * @param teeSupervisionApply
	 * @return
	 */
	private TeeSupervisionApplyModel parseToModel(
			TeeSupervisionApply teeSupervisionApply) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeSupervisionApplyModel model=new TeeSupervisionApplyModel();
		BeanUtils.copyProperties(teeSupervisionApply, model);
		if(teeSupervisionApply.getCreater()!=null){
			model.setCreaterId(teeSupervisionApply.getCreater().getUuid());
			model.setCreaterName(teeSupervisionApply.getCreater().getUserName());
		}
		if(teeSupervisionApply.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(teeSupervisionApply.getCreateTime()));
		}
		
		if(teeSupervisionApply.getSup()!=null){
			model.setSupId(teeSupervisionApply.getSup().getSid());
			model.setSupName(teeSupervisionApply.getSup().getSupName());
		}
		return model;
	}


	
	/**
	 * 获取办结申请记录
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getEndApplyListBySupId(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int supId=TeeStringUtil.getInteger(request.getParameter("supId"), 0);
		String hql = " from TeeSupervisionApply where sup.sid=? and type=3 ";
		List param = new ArrayList();
		param.add(supId);
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeSupervisionApply> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeSupervisionApplyModel> modelList = new ArrayList<TeeSupervisionApplyModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeSupervisionApplyModel modeltemp = parseToModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}


	/**
	 * 审批
	 * @param request
	 * @return
	 */
	public TeeJson approve(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		//获取主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		//获取状态
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		//获取拒绝原因
		String reason=TeeStringUtil.getString(request.getParameter("reason"));
		
		TeeSupervisionApply apply=(TeeSupervisionApply) simpleDaoSupport.get(TeeSupervisionApply.class,sid);
		if(apply!=null&&apply.getSup()!=null){
		    //修改申请的状态
		    apply.setStatus(status);
		    apply.setReason(reason);
		    simpleDaoSupport.update(apply);
		
		     TeeSupervision sup=apply.getSup();
		     //获取当前任务的状态
		     int supStatus=sup.getStatus();
	   
		     String hql="";
		     String mess1="";
		     String mess2="";
		     if(status==1){//同意
		    	  mess1="同意";
			      if(supStatus==2){//暂停申请中
			    	  mess2="暂停";
				      hql=" update TeeSupervision set status=3 where sid=? ";//暂停中
				      simpleDaoSupport.executeUpdate(hql, new Object[]{sup.getSid()});
			      }else if(supStatus==4){//恢复申请中
			    	  mess2="恢复";
				      hql=" update TeeSupervision set status=1 where sid=? ";//进行中
				      simpleDaoSupport.executeUpdate(hql, new Object[]{sup.getSid()});
			      }else if(supStatus==5){//办结申请中
			    	  mess2="办结";
				      hql=" update TeeSupervision set status=6 ,realEndTime=? where sid=? ";//办结中
				      simpleDaoSupport.executeUpdate(hql, new Object[]{new Date(),sup.getSid()});
			      }
		     }else if(status==2){//拒绝
		    	 mess1="拒绝";
			     if(supStatus==2){//暂停申请中
			    	 mess2="暂停";
				     hql=" update TeeSupervision set status=1 where sid=? ";//进行中
			     }else if(supStatus==4){//恢复申请中
			    	 mess2="恢复";
				     hql=" update TeeSupervision set status=3 where sid=? ";//暂停中
			     }else if(supStatus==5){//办结申请中
			    	 mess2="办结";
				     hql=" update TeeSupervision set status=1 where sid=? ";//进行中
			     }
			     simpleDaoSupport.executeUpdate(hql, new Object[]{sup.getSid()});
		     }
		     
		    
		     
		     if(apply.getCreater()!=null){
		    	 if(status==1){//同意
		    		// 发送消息
				     Map requestData1 = new HashMap();
				     requestData1.put("content", "责任领导“"+loginUser.getUserName()+"”"+mess1+"了您的“"+mess2+"”申请。");
				     requestData1.put("userListIds", apply.getCreater().getUuid());
				     requestData1.put("moduleNo", "061");
				     requestData1.put("remindUrl","/system/subsys/supervise/handle/index.jsp?sid="+sup.getSid());
				     smsManager.sendSms(requestData1, loginUser); 
		    	 }else{//拒绝
		    		// 发送消息
				     Map requestData1 = new HashMap();
				     requestData1.put("content", "责任领导“"+loginUser.getUserName()+"”"+mess1+"了您的“"+mess2+"”申请，理由："+reason);
				     requestData1.put("userListIds", apply.getCreater().getUuid());
				     requestData1.put("moduleNo", "061");
				     requestData1.put("remindUrl","/system/subsys/supervise/handle/index.jsp?sid="+sup.getSid());
				     smsManager.sendSms(requestData1, loginUser); 
		    	 }
		    	
		     }
		     
		     json.setRtState(true);
			 json.setRtMsg("操作成功！");
	     }
		
		return json;
	}


	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeSupervisionApply apply=(TeeSupervisionApply) simpleDaoSupport.get(TeeSupervisionApply.class,sid);
			TeeSupervisionApplyModel model=parseToModel(apply);
			json.setRtState(true);
			json.setRtData(model);
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败!");
		}
		return json;
	}


	
	

}
