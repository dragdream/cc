package com.beidasoft.xzfy.caseClose.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzfy.caseAcceptence.dao.CaseAcceptDao;
import com.beidasoft.xzfy.caseClose.bean.CaseCloseInfo;
import com.beidasoft.xzfy.caseClose.dao.CaseCloseDao;
import com.beidasoft.xzfy.caseClose.model.request.CaseCloseSaveRequest;
import com.beidasoft.xzfy.caseRegister.dao.CaseFyMaterialDao;
import com.beidasoft.xzfy.utils.Const;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;

@Service
public class CaseCloseService {

	@Autowired
	private CaseCloseDao dao;
	
	//案件受理dao
	@Autowired
	private CaseAcceptDao acceptdao;
	
	//案件材料上传
	@Autowired
	private CaseFyMaterialDao caseFyMaterialDao;
	
	/**
	 * 案件资料保存
	 * @param req
	 */
	public void caseMaterialSave(CaseCloseSaveRequest req){
		
		// 根据案件ID和案件材料类型编码删除案件材料
		//caseFyMaterialDao.deleteByList(req.getListFyMaterial());

		// 案件材料信息入库
		//caseFyMaterialDao.addFyMaterial(req.getListFyMaterial());
	}
	
	/**
	 * 案件结案保存
	 * @param caseId
	 * @param request
	 */
	public void caseCloseCommit(String caseIds,HttpServletRequest request) 
			throws Exception{
		
		StringBuffer str = new StringBuffer();
		
		//不存在的案件ID集合
		//List<String> notExitCaseIds = new ArrayList<String>();
		
		// 以逗号分隔开
		String[] caseIdArr = caseIds.split(",");
		//循环
		for(int i=0;i<caseIdArr.length;i++){
			
			String caseId = caseIdArr[i];
			//校验案件是否存在
			Boolean b = acceptdao.isExitCase(caseId);
			//是否存在
			if(b){
				//获取并案的案件ID
				List<Object> list = dao.getCaseMergeIds(caseId);
				int size = list.size();
				//存在并案的情况
				if( size > 0){
					for(int k=0;k<size;k++){
						str.append("'");
						str.append(list.get(k).toString());
						str.append("'");
						if( k < size-1){
							str.append(",");
						}
					}
				}
				//不存在并案
				else{
					str.append("'").append(caseId).append("'");
				}
			}
			else
			{
				//操作失败的案件ID集合
				//notExitCaseIds.add(caseId);
			}
			
		}
		
		//案件完结，更新案件状态为归档
		CaseCloseInfo caseClose = new CaseCloseInfo();
		//归档
		caseClose.setCaseStatusCode(Const.CASESTATUS.CASE_ARCHIVE_CODE);
		caseClose.setCaseStatus(Const.CASESTATUS.CASE_ARCHIVE_NAME);
		caseClose.setCaseChildeStatusCode("");
		caseClose.setCaseChildeStatus("");
		caseClose.setCaseIds(str.toString());
		
		TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		StringUtils.setModifyInfo(caseClose, loginUser);
		
		//更新状态
		dao.updateCaseStatus(caseClose);
		
	}
}
