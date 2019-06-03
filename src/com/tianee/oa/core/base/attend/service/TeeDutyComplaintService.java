package com.tianee.oa.core.base.attend.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.base.attend.bean.TeeDutyComplaint;
import com.tianee.oa.core.base.attend.model.TeeDutyComplaintModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeDutyComplaintService extends TeeBaseService {

	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	@Autowired
	private TeeSmsManager smsManager;
	/**
	 * 添加
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson add(HttpServletRequest request, TeeDutyComplaintModel model) {
		TeePerson  loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//审批人员
		TeePerson approver=(TeePerson) simpleDaoSupport.get(TeePerson.class,model.getApproverId());
		TeeJson json=new TeeJson();
		TeeDutyComplaint comp=new TeeDutyComplaint();
		comp.setApprover(approver);
		comp.setCreateTime(Calendar.getInstance());
		comp.setReason(model.getReason());
		comp.setRegisterNum(model.getRegisterNum());
		comp.setRemarkTimeStr(model.getRemarkTimeStr());
		comp.setStatus(0);
		comp.setUser(loginUser);
		
		simpleDaoSupport.save(comp);
		
		//处理附件
		List<TeeAttachment> list=attachmentService.getAttachmentsByIds(model.getAttachmentSidStr());
		if(list!=null&&list.size()>0){
			for (TeeAttachment teeAttachment : list) {
				teeAttachment.setModelId(comp.getSid()+"");
				simpleDaoSupport.update(teeAttachment);
			}
		}
		
		//给审批人员发送消息提醒
		if(approver!=null){
			Map requestData1 = new HashMap();
	    	requestData1.put("content", "您有一个考勤申诉需要审批，请及时办理");
	    	requestData1.put("userListIds", approver.getUuid());
	    	requestData1.put("moduleNo", "023");
	    	requestData1.put("remindUrl","/system/core/base/attend/complain/index.jsp");
	    	smsManager.sendSms(requestData1, loginUser);
		}
		
		
		json.setRtState(true);
		return json;
	}

	
	/**
	 * 查看申诉
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson view(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String remarkTimeStr=TeeStringUtil.getString(request.getParameter("remarkTimeStr"));
		int registerNum=TeeStringUtil.getInteger(request.getParameter("registerNum"),0);
		int userId=TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		
		List<TeeDutyComplaint> list=simpleDaoSupport.executeQuery(" from TeeDutyComplaint where user.uuid=? and remarkTimeStr=? and registerNum=? ", new Object[]{userId,remarkTimeStr,registerNum});
		if(list!=null&&list.size()>0){
			TeeDutyComplaintModel model=parseToModel(list.get(0));
			json.setRtState(true);
			json.setRtData(model);
		}else{
			json.setRtState(false);
			json.setRtMsg("该申诉已不存在！");
		}
		return json;
	}


	/**
	 * 实体类转换成model
	 * @param teeDutyComplaint
	 * @return
	 */
	private TeeDutyComplaintModel parseToModel(TeeDutyComplaint teeDutyComplaint) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TeeDutyComplaintModel model=new TeeDutyComplaintModel();
		BeanUtils.copyProperties(teeDutyComplaint, model);
		if(teeDutyComplaint.getApprover()!=null){
			model.setApproverId(teeDutyComplaint.getApprover().getUuid());
			model.setApproverName(teeDutyComplaint.getApprover().getUserName());
		}
		
		if(teeDutyComplaint.getUser()!=null){
			model.setUserId(teeDutyComplaint.getUser().getUuid());
			model.setUserName(teeDutyComplaint.getUser().getUserName());
		}
		if(teeDutyComplaint.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(teeDutyComplaint.getCreateTime().getTime()));
		}
		if(teeDutyComplaint.getStatus()==0){
			model.setStatusDesc("待审批");
		}else if(teeDutyComplaint.getStatus()==1){
			model.setStatusDesc("已批准");
		}else if(teeDutyComplaint.getStatus()==2){
			model.setStatusDesc("未批准");
		}
		
		//处理附件
		List<TeeAttachment> attaches =  attachmentDao.getAttaches(TeeAttachmentModelKeys.dutyComplaint, String.valueOf(teeDutyComplaint.getSid()));
		for (TeeAttachment attach : attaches) {
			TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, attachmentModel);
			attachmentModel.setUserId(attach.getUser().getUuid() + "");
			attachmentModel.setUserName(attach.getUser().getUserName());
			attachmentModel.setPriv(1 + 2);// 一共五个权限好像
											// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
			model.getAttachList().add(attachmentModel);
		}
		
		return model;
	}


	
	/**
	 * 根据状态获取列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,
			TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);//审批状态   0待审批    1已批准   2未批准
	
		List param=new ArrayList();
		param.add(loginUser.getUuid());
		param.add(status);
		
		
		long total=simpleDaoSupport.countByList(" select count(*) from TeeDutyComplaint  where approver.uuid=? and status=? ", param);
		json.setTotal(total);
		List<TeeDutyComplaint> list=simpleDaoSupport.pageFind(" from TeeDutyComplaint  where approver.uuid=? and status=? ", (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param.toArray());
		List<TeeDutyComplaintModel> modelList=new ArrayList<TeeDutyComplaintModel>();
		if(list!=null&&list.size()>0){
			TeeDutyComplaintModel model=null;
			for (TeeDutyComplaint teeDutyComplaint : list) {
				model=parseToModel(teeDutyComplaint);
				modelList.add(model);
			}
		}
		
		json.setRows(modelList);
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
		TeeDutyComplaint c=(TeeDutyComplaint) simpleDaoSupport.get(TeeDutyComplaint.class,sid);
		TeeDutyComplaintModel model=null;
		if(c!=null){
			model=parseToModel(c);
			json.setRtState(true);
			json.setRtData(model);
		}else{
			json.setRtState(false);
			json.setRtMsg("该申诉已不存在！");
		}
		return json;
	}


	
	/**
	 * 考勤申诉
	 * @param request
	 * @return
	 */
	public TeeJson approve(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int status=TeeStringUtil.getInteger(request.getParameter("status"),0);
		TeeDutyComplaint c=(TeeDutyComplaint) simpleDaoSupport.get(TeeDutyComplaint.class,sid);
		if(c!=null){
			c.setStatus(status);
			simpleDaoSupport.update(c);
			json.setRtState(true);
			
			
			//status==1  批准   status==2 不批准
			
			String mess="";
			if(status==1){
				mess="【"+loginUser.getUserName()+"】同意了您在考勤日期为"+c.getRemarkTimeStr()+"的第"+c.getRegisterNum()+"次打卡申诉！";
			}else if(status==2){
				mess="【"+loginUser.getUserName()+"】拒绝了您在考勤日期为"+c.getRemarkTimeStr()+"的第"+c.getRegisterNum()+"次打卡申诉！";
			}
			Map requestData1 = new HashMap();
	    	requestData1.put("content", mess);
	    	requestData1.put("userListIds",c.getUser().getUuid());
	    	requestData1.put("moduleNo", "023");
	    	requestData1.put("remindUrl","");
	    	smsManager.sendSms(requestData1, loginUser);
			
		}else{
			json.setRtState(false);
			json.setRtMsg("该申诉已不存在！");
		}
		
		return json;
	}

}
