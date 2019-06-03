package com.tianee.oa.core.base.dam.service;

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

import com.tianee.oa.core.base.dam.bean.TeeFileBorrow;
import com.tianee.oa.core.base.dam.bean.TeeFiles;
import com.tianee.oa.core.base.dam.dao.TeeFileBorrowDao;
import com.tianee.oa.core.base.dam.model.TeeFileBorrowModel;
import com.tianee.oa.core.base.dam.model.TeeFilesModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;


@Service
public class TeeFileBorrowService extends TeeBaseService{
	@Autowired
	TeeFileBorrowDao borrowDao;
	
	@Autowired
	private TeeSmsManager smsManager;

	/**
	 * 档案借阅
	 * @param request
	 * @return
	 */
	public TeeJson borrow(HttpServletRequest request) {
		//获取当前登录人
		TeePerson  loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson  json=new TeeJson();
		int fileId=TeeStringUtil.getInteger(request.getParameter("fileId"),0);//档案主键
		if(fileId!=0){
			TeeFiles file=(TeeFiles) simpleDaoSupport.get(TeeFiles.class,fileId);
		    if(file!=null){
		    	TeeFileBorrow b=new TeeFileBorrow();
		    	b.setApproveFlag(0);
		    	b.setFile(file);
		    	b.setReturnFlag(0);
		    	b.setViewTime(Calendar.getInstance());
		    	b.setViewUser(loginUser);
		    	if(file.getStoreHouse()!=null){
		    		b.setApprover(file.getStoreHouse().getBorrowManager());
		    	}
		    	simpleDaoSupport.save(b);
		    	
		    	//发送消息给卷库借阅管理员
                if(b.getApprover()!=null){
                	Map requestData1 = new HashMap();
    				requestData1.put("content", loginUser.getUserName()+"发起了对档案《"+file.getTitle()+"》的借阅申请，请及时处理！");
    				requestData1.put("userListIds", b.getApprover().getUuid());
    				requestData1.put("moduleNo", "036");
    				requestData1.put("remindUrl","/system/core/base/dam/borrowManage/index.jsp");
    				smsManager.sendSms(requestData1, loginUser);      	
                }
                
		    	json.setRtState(true);
				json.setRtMsg("申请成功！");
		    }else{
		    	json.setRtState(false);
				json.setRtMsg("档案信息获取失败！");
		    }
		}else{
			json.setRtState(false);
			json.setRtMsg("档案信息获取失败！");
		}
		return json;
	}

	
	
	/**
	 * 我的借阅
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getMyBorrow(TeeDataGridModel dm,
			HttpServletRequest request) {
		String orgCode=TeeStringUtil.getString(request.getParameter("orgCode"));//组织机构代码
		String qzh=TeeStringUtil.getString(request.getParameter("qzh"));//全宗号
		String year=TeeStringUtil.getString(request.getParameter("year"));//年份
		String retentionPeriod=TeeStringUtil.getString(request.getParameter("retentionPeriod"));//保管期限
		String title=TeeStringUtil.getString(request.getParameter("title"));//标题
		String unit=TeeStringUtil.getString(request.getParameter("unit"));//单位
		String number=TeeStringUtil.getString(request.getParameter("number"));//文件编号
		String subject=TeeStringUtil.getString(request.getParameter("subject"));//主题词
		String mj=TeeStringUtil.getString(request.getParameter("mj"));//密级
		String hj=TeeStringUtil.getString(request.getParameter("hj"));//缓急
		String remark=TeeStringUtil.getString(request.getParameter("remark"));//备注
		//借阅状态    1=待审批   2=已批准   3=归还中   4=已归还    5=未批准
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = " from TeeFileBorrow b  left outer  join  b.file  f where   b.viewUser.uuid="+loginUser.getUuid();
		
		
		if(orgCode!=null&&!("").equals(orgCode)){
			hql+=" and f.orgCode like '"+"%"+orgCode+"%"+"'";
		}
		if(qzh!=null&&!("").equals(qzh)){
			hql+=" and f.qzh like '"+ "%"+qzh+"%"+"'";
		}
		if(year!=null&&!("").equals(year)){
			hql+=" and f.year like '"+ "%"+year+"%"+"'";
		}
		if(retentionPeriod!=null&&!("").equals(retentionPeriod)){
			hql+=" and f.retentionPeriod='"+retentionPeriod+"'";
		}
		if(title!=null&&!("").equals(title)){
			hql+=" and f.title like '"+"%"+title+"%"+"'";
		}
		if(unit!=null&&!("").equals(unit)){
			hql+=" and f.unit like '"+"%"+unit+"%"+"'";
		}
		if(number!=null&&!("").equals(number)){
			hql+=" and f.number like '"+ "%"+number+"%"+"'";
		}
		if(subject!=null&&!("").equals(subject)){
			hql+=" and f.subject like'" +"%"+subject+"%"+"'";
		}
		if(mj!=null&&!(" ").equals(mj)){
			hql+=" and f.mj='"+mj+"'";
		}
		if(hj!=null&&!(" ").equals(hj)){
			hql+=" and f.hj='"+hj+"'";
		}
		if(remark!=null&&!("").equals(remark)){
			hql+=" and f.remark like '"+ "%"+remark+"%"+"'";
		}	
		//借阅状态    1=待审批   2=已批准   3=归还中   4=已归还    5=未批准
		if(status!=0){
			if(status==1){
				hql+=" and b.approveFlag=0 ";
			}else if(status==2){
				hql+=" and b.approveFlag=1 and b.returnFlag=0 ";
			}else if(status==3){
				hql+=" and b.approveFlag=1 and b.returnFlag=1 ";
			}else if(status==4){
				hql+=" and b.approveFlag=1 and b.returnFlag=2 ";
			}else if(status==5){
				hql+=" and b.approveFlag=2 ";
			}
		}
		
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));// 设置总记录数
		hql += " order by b.viewTime asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeFileBorrow> list = simpleDaoSupport.pageFindByList("select b  "+hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查
		List<TeeFileBorrowModel>	modelList=new ArrayList<TeeFileBorrowModel>();
		if(list!=null&&list.size()>0){
			TeeFileBorrowModel model=null;
			for (TeeFileBorrow b : list) {
				model=parseToModel(b);
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}



	/**
	 * 实体类转换成model
	 * @param b
	 * @return
	 */
	private TeeFileBorrowModel parseToModel(TeeFileBorrow b) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TeeFileBorrowModel model=new TeeFileBorrowModel();
		BeanUtils.copyProperties(b, model);
		
		//处理借阅人
		if(b.getViewUser()!=null){
			model.setViewUserId(b.getViewUser().getUuid());
			model.setViewUserName(b.getViewUser().getUserName());
		}
		//处理借阅时间
		if(b.getViewTime()!=null){
			model.setViewTimeStr(sdf.format(b.getViewTime().getTime()));
		}
		
		//处理归还时间
		if(b.getReturnTime()!=null){
			model.setReturnTimeStr(sdf.format(b.getReturnTime().getTime()));
		}
		
		//处理审批人
		if(b.getApprover()!=null){
			model.setApproverId(b.getApprover().getUuid());
			model.setApproverName(b.getApprover().getUserName());
		}
		
		//处理审批时间
		if(b.getApproveTime()!=null){
			model.setApproveTimeStr(sdf.format(b.getApproveTime().getTime()));
		}
		//处理档案信息
		if(b.getFile()!=null){
			model.setFileHj(b.getFile().getHj());
			model.setFileId(b.getFile().getSid());
			model.setFileMj(b.getFile().getMj());
			model.setFileNumber(b.getFile().getNumber());
			model.setFileRt(TeeSysCodeManager.getChildSysCodeNameCodeNo("DAM_RT", b.getFile().getRetentionPeriod()));
			model.setFileTitle(b.getFile().getTitle());
			model.setFileUnit(b.getFile().getUnit());
		}
		return model;
	}



	/**
	 * 删除借阅记录
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson  json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			TeeFileBorrow b=(TeeFileBorrow) simpleDaoSupport.get(TeeFileBorrow.class,sid);
		    if(b!=null){
		    	simpleDaoSupport.deleteByObj(b);
		    	json.setRtState(true);
		    	json.setRtMsg("删除成功！");
		    }else{
		    	json.setRtState(false);
				json.setRtMsg("数据获取失败！");
		    }
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}



	/**
	 * 归还
	 * @param request
	 * @return
	 */
	public TeeJson giveBack(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			TeeFileBorrow b=(TeeFileBorrow) simpleDaoSupport.get(TeeFileBorrow.class,sid);
		    b.setReturnFlag(1);
		    b.setReturnTime(Calendar.getInstance());
		    simpleDaoSupport.update(b);
		    
		    //发送消息
            if(b.getFile()!=null&&b.getApprover()!=null){
            	Map requestData1 = new HashMap();
    			requestData1.put("content", loginUser.getUserName()+"对档案《"+b.getFile().getTitle()+"》发起了归还申请，请及时确认！");
    			requestData1.put("userListIds", b.getApprover().getUuid());
    			requestData1.put("moduleNo", "036");
    			requestData1.put("remindUrl","/system/core/base/dam/returnManage/index.jsp");
    			smsManager.sendSms(requestData1, loginUser);	
            }

		    json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("借阅记录数据获取失败！");
		}
		return json;
	}



	/**
	 * 获取待审批列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getNoApprove(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = " from TeeFileBorrow b  where b.approveFlag=0 and b.approver.uuid="+loginUser.getUuid();
		
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));// 设置总记录数
		hql += " order by b.viewTime asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeFileBorrow> list = simpleDaoSupport.pageFindByList("select b  "+hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查
		List<TeeFileBorrowModel>	modelList=new ArrayList<TeeFileBorrowModel>();
		if(list!=null&&list.size()>0){
			TeeFileBorrowModel model=null;
			for (TeeFileBorrow b : list) {
				model=parseToModel(b);
				modelList.add(model);
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
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		if(sid>0){
			TeeFileBorrow b=(TeeFileBorrow) simpleDaoSupport.get(TeeFileBorrow.class,sid);
			if(b!=null){
				TeeFiles file=b.getFile();
				if(status==1){//批准
					if(file!=null){
						if(file.getViewFlag()==1){//该档案已经借出去了
							json.setRtState(false);
							json.setRtMsg("该档案已借出！");
						}else{
						    b.setApproveFlag(1);
						    b.setApproveTime(Calendar.getInstance());
						    simpleDaoSupport.update(b);
						    file.setViewFlag(1);
						    file.setViewTotal(file.getViewTotal()+1);
						    simpleDaoSupport.update(file);
						    
						    //发送消息
                            if(b.getViewUser()!=null){
                            	Map requestData1 = new HashMap();
    							requestData1.put("content", loginUser.getUserName()+"批准了您对档案《"+file.getTitle()+"》的借阅申请，请及时查看！");
    							requestData1.put("userListIds",b.getViewUser().getUuid());
    							requestData1.put("moduleNo", "036");
    							requestData1.put("remindUrl","/system/core/base/dam/borrow/myBorrow/index.jsp");
    							smsManager.sendSms(requestData1, loginUser);
                            }
                            
                            
						    json.setRtState(true);
						    json.setRtMsg("已批准");
						}
					}
				}else{
					b.setApproveFlag(2);
					b.setApproveTime(Calendar.getInstance());
					simpleDaoSupport.update(b);
					
					
					//发送消息
                    if(b.getViewUser()!=null){
                    	Map requestData1 = new HashMap();
						requestData1.put("content", loginUser.getUserName()+"拒绝了您对档案《"+file.getTitle()+"》的借阅申请，请及时查看！");
						requestData1.put("userListIds",b.getViewUser().getUuid());
						requestData1.put("moduleNo", "036");
						requestData1.put("remindUrl","/system/core/base/dam/borrow/myBorrow/index.jsp");
						smsManager.sendSms(requestData1, loginUser);
                    }
					
					
					json.setRtState(true);
					json.setRtMsg("已拒绝");
				}	
			}else{
				json.setRtState(false);
				json.setRtMsg("借阅申请记录获取失败！");
			}
		}else{
			json.setRtState(false);
			json.setRtMsg("借阅申请记录获取失败！");
		}
		return json;
	}



	/**
	 * 获取已批准列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getApproved(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = " from TeeFileBorrow b  where b.approveFlag=1 and b.approver.uuid="+loginUser.getUuid();
		
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));// 设置总记录数
		hql += " order by b.viewTime asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeFileBorrow> list = simpleDaoSupport.pageFindByList("select b  "+hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查
		List<TeeFileBorrowModel>	modelList=new ArrayList<TeeFileBorrowModel>();
		if(list!=null&&list.size()>0){
			TeeFileBorrowModel model=null;
			for (TeeFileBorrow b : list) {
				model=parseToModel(b);
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}



	/**
	 * 获取未批准的列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getNotApproved(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = " from TeeFileBorrow b  where b.approveFlag=2 and b.approver.uuid="+loginUser.getUuid();
		
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));// 设置总记录数
		hql += " order by b.viewTime asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeFileBorrow> list = simpleDaoSupport.pageFindByList("select b  "+hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查
		List<TeeFileBorrowModel>	modelList=new ArrayList<TeeFileBorrowModel>();
		if(list!=null&&list.size()>0){
			TeeFileBorrowModel model=null;
			for (TeeFileBorrow b : list) {
				model=parseToModel(b);
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}



	/**
	 * 获取归还待确认的记录
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getNoConfirmRecords(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = " from TeeFileBorrow b  where b.approveFlag=1 and b.returnFlag=1 and b.approver.uuid="+loginUser.getUuid();
		
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));// 设置总记录数
		hql += " order by b.viewTime asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeFileBorrow> list = simpleDaoSupport.pageFindByList("select b  "+hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查
		List<TeeFileBorrowModel>	modelList=new ArrayList<TeeFileBorrowModel>();
		if(list!=null&&list.size()>0){
			TeeFileBorrowModel model=null;
			for (TeeFileBorrow b : list) {
				model=parseToModel(b);
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}



	
	/**
	 * 归还确认
	 * @param request
	 * @return
	 */
	public TeeJson confirmReturn(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			TeeFileBorrow b=(TeeFileBorrow) simpleDaoSupport.get(TeeFileBorrow.class,sid);
		    if(b!=null){
		    	b.setReturnFlag(2);
		    	
		    	TeeFiles file=b.getFile();
		    	if(file!=null){
		    		file.setViewFlag(0);
		    		simpleDaoSupport.update(file);
		    	}
		    	simpleDaoSupport.update(b);
		    	
		    	json.setRtState(true);
				json.setRtMsg("已确认归还！");
		    }else{
		    	json.setRtState(false);
				json.setRtMsg("借阅记录信息获取失败！");
		    }
		}else{
			json.setRtState(false);
			json.setRtMsg("借阅记录信息获取失败！");
		}
		return json;
	}



	/**
	 * 获取已经确认的归还记录
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getConfirmedRecords(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = " from TeeFileBorrow b  where b.approveFlag=1 and b.returnFlag=2 and b.approver.uuid="+loginUser.getUuid();
		
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));// 设置总记录数
		hql += " order by b.viewTime asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeFileBorrow> list = simpleDaoSupport.pageFindByList("select b  "+hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查
		List<TeeFileBorrowModel>	modelList=new ArrayList<TeeFileBorrowModel>();
		if(list!=null&&list.size()>0){
			TeeFileBorrowModel model=null;
			for (TeeFileBorrow b : list) {
				model=parseToModel(b);
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
}
