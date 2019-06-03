package com.beidasoft.xzfy.caseQuery.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzfy.caseQuery.bean.CaseListInfo;
import com.beidasoft.xzfy.caseQuery.bean.CaseSearchInfo;
import com.beidasoft.xzfy.caseQuery.model.request.CaseListRequest;
import com.beidasoft.xzfy.caseQuery.dao.CaseListDao;
import com.beidasoft.xzfy.utils.Const;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
@Service
public class CaseListService {
	@Autowired
	private CaseListDao dao;
	
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
//			list = dao.getCaseRegisterList(loginUser,status.toString(),accept);
		}
		else{
			list = dao.getCaseAcceptList(loginUser, status.toString(), accept);
		}
		
		return list;
	}
}
