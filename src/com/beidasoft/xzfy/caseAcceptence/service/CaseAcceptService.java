package com.beidasoft.xzfy.caseAcceptence.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beidasoft.xzfy.caseAcceptence.bean.CaseAcceptInfo;
import com.beidasoft.xzfy.caseAcceptence.bean.CaseListInfo;
import com.beidasoft.xzfy.caseAcceptence.bean.CaseSearchInfo;
import com.beidasoft.xzfy.caseAcceptence.bean.CaseProcessLogInfo;
import com.beidasoft.xzfy.caseAcceptence.dao.CaseAcceptDao;
import com.beidasoft.xzfy.caseAcceptence.dao.CaseProcessLogDao;
import com.beidasoft.xzfy.caseAcceptence.model.request.CaseListRequest;
import com.beidasoft.xzfy.caseAcceptence.model.request.CaseAcceptRequest;
import com.beidasoft.xzfy.caseAcceptence.model.request.CaseMergeListRequest;
import com.beidasoft.xzfy.caseAcceptence.model.request.CaseMergeRequest;
import com.beidasoft.xzfy.caseAcceptence.model.request.CaseAcceptCommitRequest;
import com.beidasoft.xzfy.utils.Const;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;

import dm.jdbc.util.StringUtil;

@Service
public class CaseAcceptService {

	@Autowired
	private CaseAcceptDao dao;
	
	//流程记录
	@Autowired
	private CaseProcessLogDao procDao;
	
	/**
	 * 查询案件是否存在
	 * @param caseId
	 * @return
	 */
	public boolean isExitCase(String caseId){
		return dao.isExitCase(caseId);
	}
	
	/**
	 * 案件受理
	 * @param req
	 */
	@Transactional
	public void caseAccept(CaseAcceptRequest req,HttpServletRequest request){
		
		//第一步更新主表状态
		CaseAcceptInfo caseAccept = new CaseAcceptInfo();
		caseAccept.setAcceptTime(req.getAcceptTime());
		caseAccept.setReason(req.getReason());
		caseAccept.setRemark(req.getRemark());
		caseAccept.setCaseStatus(Const.CASESTATUS.CASE_ACCEPTENCE_NAME);
		caseAccept.setCaseStatusCode(Const.CASESTATUS.CASE_ACCEPTENCE_CODE);
		// 立案受理
		if(Const.TYPE.ONE.equals(req.getType())){
			caseAccept.setCaseChildeStatus(Const.ACCEPTENCESTATUS.ACCEPTENCE_ACCEPT_NAME);
			caseAccept.setCaseChildeStatusCode(Const.ACCEPTENCESTATUS.ACCEPTENCE_ACCEPT_CODE);
		}
		// 不予受理
		else if(Const.TYPE.TWO.equals(req.getType())){
			caseAccept.setCaseChildeStatus(Const.ACCEPTENCESTATUS.ACCEPTENCE_REFUSE_NAME);
			caseAccept.setCaseChildeStatusCode(Const.ACCEPTENCESTATUS.ACCEPTENCE_REFUSE_CODE);
		}
		// 补正
		else if(Const.TYPE.THIRD.equals(req.getType())){
			caseAccept.setCaseChildeStatus(Const.ACCEPTENCESTATUS.ACCEPTENCE_CORRECTION_NAME);
			caseAccept.setCaseChildeStatusCode(Const.ACCEPTENCESTATUS.ACCEPTENCE_CORRECTION_CODE);
		}
		// 告知
		else if(Const.TYPE.FOUR.equals(req.getType())){
			caseAccept.setCaseChildeStatus(Const.ACCEPTENCESTATUS.ACCEPTENCE_INFORM_NAME);
			caseAccept.setCaseChildeStatusCode(Const.ACCEPTENCESTATUS.ACCEPTENCE_INFORM_CODE);
		}
		// 转送
		else if(Const.TYPE.FIVE.equals(req.getType())){
			caseAccept.setCaseChildeStatus(Const.ACCEPTENCESTATUS.ACCEPTENCE_FORWARD_NAME);
			caseAccept.setCaseChildeStatusCode(Const.ACCEPTENCESTATUS.ACCEPTENCE_FORWARD_CODE);
		}
		// 其他
		else if(Const.TYPE.SIX.equals(req.getType())){
			caseAccept.setCaseChildeStatus(Const.ACCEPTENCESTATUS.ACCEPTENCE_OTHER_NAME);
			caseAccept.setCaseChildeStatusCode(Const.ACCEPTENCESTATUS.ACCEPTENCE_OTHER_CODE);
		}
		// 案件受理前撤回
		else if(Const.TYPE.SEVEN.equals(req.getType())){
			caseAccept.setCaseChildeStatus(Const.ACCEPTENCESTATUS.ACCEPTENCE_RECALL_NAME);
			caseAccept.setCaseChildeStatusCode(Const.ACCEPTENCESTATUS.ACCEPTENCE_RECALL_CODE);
		}
		//更新人
		TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		StringUtils.setModifyInfo(caseAccept, loginUser);
		//更新案件状态
		dao.updateCaseStatus(caseAccept);
		
		//第二步流程表添加记录
		CaseProcessLogInfo process = new CaseProcessLogInfo();
		process.setId(StringUtils.getUUId());
		process.setCaseId(req.getCaseId());
		//处理环节:01  案件登记 02 任务分配 03 受理 04 案件审理 05结案  06 归档
		process.setProcessItem("03");
		//处理时间
		process.setStartTime(req.getAcceptTime());
		process.setEndTime("");
		//处理结果
		process.setDealResult(caseAccept.getCaseChildeStatusCode());
		//处理原因
		process.setReason(req.getReason());
		//备注
		process.setRemark(req.getRemark());
		process.setIdDelete(Const.TYPE.ZERO);
		//新增人
		StringUtils.setAddInfo(process, loginUser);
		
		procDao.addCaseProcessLog(process);
		
	}
	
	/**
	 * 案件受理完成
	 * @param req
	 * @param request
	 */
	public void caseAcceptCommit(CaseAcceptCommitRequest req){
		
		CaseAcceptInfo caseAccept = new CaseAcceptInfo();
		//判断类型和判断第一承办人和第二承办人
		if(Const.TYPE.ONE.equals(req.getType()) && !StringUtil.isEmpty(req.getDealManFirstId()) 
				&& !StringUtil.isEmpty(req.getDealManSecondId()) ){
			caseAccept.setCaseStatus(Const.CASESTATUS.CASE_TRIAL_NAME);
			caseAccept.setCaseStatusCode(Const.CASESTATUS.CASE_TRIAL_CODE);
			caseAccept.setDealManFirstId(req.getDealManFirstId());
			caseAccept.setDealManSecondId(req.getDealManSecondId());
		}
		else{
			caseAccept.setCaseStatus(Const.CASESTATUS.CASE_CLOSE_NAME);
			caseAccept.setCaseStatusCode(Const.CASESTATUS.CASE_CLOSE_CODE);
		}
		caseAccept.setCaseChildeStatus("");
		caseAccept.setCaseChildeStatusCode("");
		//更新案件状态
		dao.caseAcceptCommit(caseAccept);
	}
	
	/**
	 * 案件合并
	 * @param req
	 */
	public void caseMarge(CaseMergeRequest req){
		dao.caseMerge(req.getCaseId(), req.getMergeCaseIds());
	}
	
	/**
	 * 获取并案案件列表
	 * @param req
	 * @param request
	 * @return
	 */
	public List<CaseListInfo> getCaseMergeList(CaseMergeListRequest req,
			HttpServletRequest request){
		
		List<CaseListInfo> list = new ArrayList<CaseListInfo>();
		//登录角色
		TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//封装参数
		CaseSearchInfo accept = new CaseSearchInfo();
		accept.setCaseNum(req.getCaseNum());
		accept.setName(req.getName());
		accept.setPostType(req.getPostType());
		accept.setCaseStatus(req.getCaseStatus());;
		accept.setStart(req.getStart());
		accept.setEnd(req.getEnd());
		accept.setPage(req.getPage());
		accept.setRows(req.getRows());
		//状态
		StringBuffer status = new StringBuffer();
		status.append("'");
		status.append(Const.CASESTATUS.CASE_ACCEPTENCE_CODE);//受理中
		status.append("'");
		//查询
		list = dao.getCaseMergeList(loginUser,status.toString(),accept,req.getMainCaseId());
		return list;
	}
	
	
	/**
	 * 获取总记录数
	 * @param req
	 * @param request
	 * @return
	 */
	public long getCaseMergeListSize(CaseMergeListRequest req,
			HttpServletRequest request){
		
		//登录角色
		TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//封装参数
		CaseSearchInfo accept = new CaseSearchInfo();
		accept.setCaseNum(req.getCaseNum());
		accept.setName(req.getName());
		accept.setPostType(req.getPostType());
		accept.setCaseStatus(req.getCaseStatus());;
		accept.setStart(req.getStart());
		accept.setEnd(req.getEnd());
		accept.setPage(req.getPage());
		accept.setRows(req.getRows());
		//状态
		StringBuffer status = new StringBuffer();
		status.append("'");
		status.append(Const.CASESTATUS.CASE_ACCEPTENCE_CODE);//受理中
		status.append("'");
		//查询
		long size = dao.getCaseMergeListSize(loginUser,status.toString(),accept,req.getMainCaseId());
		return size;
	}
	
	/**
	 * 获取受理案件列表
	 * @param req
	 * @return
	 */
	public List<CaseListInfo> getCaseAcceptList(CaseListRequest req,
			HttpServletRequest request){
		
		List<CaseListInfo> list = new ArrayList<CaseListInfo>();
		//登录角色
		TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//封装参数
		CaseSearchInfo accept = new CaseSearchInfo();
		accept.setCaseNum(req.getCaseNum());
		accept.setName(req.getName());
		accept.setPostType(req.getPostType());
		accept.setCaseStatus(req.getCaseStatus());
		accept.setStart(req.getStart());
		accept.setEnd(req.getEnd());
		accept.setPage(req.getPage());
		accept.setRows(req.getRows());
		accept.setApproveType(req.getApproveType());
		accept.setRespName(req.getRespName());
		
		//状态
		StringBuffer status = new StringBuffer();
		
		if( Const.TYPE.ONE.equals(req.getType())){
			//获取当前用户的登记的所有案件
			status.append("'");
			status.append(Const.CASESTATUS.CASE_DRAFT_CODE);//登记中
			status.append("',");
			status.append("'");
			status.append(Const.CASESTATUS.CASE_REGISTER_CODE);//已登记
			status.append("'");
		}
		//受理
		else if( Const.TYPE.TWO.equals(req.getType())){
			//获取当前用户的登记的所有案件
			status.append("'");
			status.append(Const.CASESTATUS.CASE_ACCEPTENCE_CODE);//受理中
			status.append("'");
		}
		//审理
		else if( Const.TYPE.THIRD.equals(req.getType())){
			//获取当前用户的登记的所有案件
			status.append("'");
			status.append(Const.CASESTATUS.CASE_TRIAL_CODE);//审理中
			status.append("'");
		}
		//结案
		else if( Const.TYPE.FOUR.equals(req.getType())){
			//获取当前用户的登记的所有案件
			status.append("'");
			status.append(Const.CASESTATUS.CASE_CLOSE_CODE);//结案中
			status.append("'");
		}
		//归档
		else if( Const.TYPE.FIVE.equals(req.getType())){
			//获取当前用户的登记的所有案件
			status.append("'");
			status.append(Const.CASESTATUS.CASE_ARCHIVE_CODE);//归档中
			status.append("',");
			status.append("'");
			status.append(Const.CASESTATUS.CASE_COMMIT_CODE);//归档中
			status.append("'");
		}
		
		//查询
		if( Const.TYPE.ONE.equals(req.getType())){
			list = dao.getCaseRegisterList(loginUser,status.toString(),accept);
		}
		else{
			list = dao.getCaseAcceptList(loginUser, status.toString(), accept,req.getIsNeedDeal());
		}
		
		return list;
	}
	
	
	
	/**
	 * 获取总记录数
	 * @param req
	 * @param request
	 * @return
	 */
	public long getCaseAcceptListSize(CaseListRequest req,
			HttpServletRequest request){
		
		//登录角色
		TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//封装参数
		CaseSearchInfo accept = new CaseSearchInfo();
		accept.setCaseNum(req.getCaseNum());
		accept.setName(req.getName());
		accept.setPostType(req.getPostType());
		accept.setCaseStatus(req.getCaseStatus());
		accept.setStart(req.getStart());
		accept.setEnd(req.getEnd());
		accept.setPage(req.getPage());
		accept.setRows(req.getRows());
		accept.setApproveType(req.getApproveType());
		accept.setRespName(req.getRespName());
		
		//状态
		StringBuffer status = new StringBuffer();
		
		if( Const.TYPE.ONE.equals(req.getType())){
			//获取当前用户的登记的所有案件
			status.append("'");
			status.append(Const.CASESTATUS.CASE_DRAFT_CODE);//登记中
			status.append("',");
			status.append("'");
			status.append(Const.CASESTATUS.CASE_REGISTER_CODE);//已登记
			status.append("'");
		}
		//受理
		else if( Const.TYPE.TWO.equals(req.getType())){
			//获取当前用户的登记的所有案件
			status.append("'");
			status.append(Const.CASESTATUS.CASE_ACCEPTENCE_CODE);//受理中
			status.append("'");
		}
		//审理
		else if( Const.TYPE.THIRD.equals(req.getType())){
			//获取当前用户的登记的所有案件
			status.append("'");
			status.append(Const.CASESTATUS.CASE_TRIAL_CODE);//审理中
			status.append("'");
		}
		//结案
		else if( Const.TYPE.FOUR.equals(req.getType())){
			//获取当前用户的登记的所有案件
			status.append("'");
			status.append(Const.CASESTATUS.CASE_CLOSE_CODE);//结案中
			status.append("'");
		}
		//归档
		else if( Const.TYPE.FIVE.equals(req.getType())){
			//获取当前用户的登记的所有案件
			status.append("'");
			status.append(Const.CASESTATUS.CASE_ARCHIVE_CODE);//归档中
			status.append("',");
			status.append("'");
			status.append(Const.CASESTATUS.CASE_COMMIT_CODE);//归档中
			status.append("'");
		}
		
		long size = 0;
		//查询
		if( Const.TYPE.ONE.equals(req.getType())){
			size = dao.getCaseRegisterListSize(loginUser,status.toString(),accept);
		}
		else{
			size = dao.getCaseAcceptListSize(loginUser, status.toString(), accept,req.getIsNeedDeal());
		}
		
		return size;
	}
}
