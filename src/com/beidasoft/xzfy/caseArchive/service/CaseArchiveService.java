package com.beidasoft.xzfy.caseArchive.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beidasoft.xzfy.caseArchive.bean.CaseArchiveInfo;
import com.beidasoft.xzfy.caseArchive.dao.CaseArchiveDao;
import com.beidasoft.xzfy.caseArchive.model.request.CaseArchiveCommitRequest;
import com.beidasoft.xzfy.caseArchive.model.request.CaseArchiveSaveRequest;
import com.beidasoft.xzfy.caseClose.dao.CaseCloseDao;
import com.beidasoft.xzfy.utils.Const;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;

@Service
public class CaseArchiveService {

	@Autowired
	private CaseArchiveDao archiveDao;
	
	//案件结案
	@Autowired
	private CaseCloseDao closeDao;
	
	
	/**
	 * 案件材料保存
	 * @param req
	 */
	@Transactional
	public void caseMaterialSave(CaseArchiveSaveRequest req){
		
		// 根据案件ID和案件材料类型编码删除案件材料
		//caseFyMaterialDao.deleteByList(req.getListFyMaterial());

		// 案件材料信息入库
		//caseFyMaterialDao.addFyMaterial(req.getListFyMaterial());
	}
	
	/**
	 * 案件结案
	 * @param req
	 * @param request
	 */
	public void caseArchiveCommit(CaseArchiveCommitRequest req,HttpServletRequest request){
		
		CaseArchiveInfo archive = new CaseArchiveInfo();
		//案件完结
		archive.setCaseStatusCode(Const.CASESTATUS.CASE_COMMIT_CODE);
		archive.setCaseStatus(Const.CASESTATUS.CASE_COMMIT_NAME);
		//正卷归档
		if( Const.TYPE.ONE.equals(req.getType())){
			archive.setCaseChildeStatusCode(Const.ARCHIVESTATUS.ARCHIVE_POSITIVE_CODE);
			archive.setCaseChildeStatus(Const.ARCHIVESTATUS.ARCHIVE_POSITIVE_NAME);
		}
		//副卷归档
		else if( Const.TYPE.TWO.equals(req.getType())){
			archive.setCaseChildeStatusCode(Const.ARCHIVESTATUS.ARCHIVE_SECONDARY_CODE);
			archive.setCaseChildeStatus(Const.ARCHIVESTATUS.ARCHIVE_SECONDARY_NAME);
		}
		
		StringBuffer str = new StringBuffer();
		//获取并案的案件ID
		List<Object> list = closeDao.getCaseMergeIds(req.getCaseId());
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
			str.append("'").append(req.getCaseId()).append("'");
		}
		
		//更新人
		TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		StringUtils.setModifyInfo(archive, loginUser);
		//更新
		archiveDao.updateCaseStatus(archive);
	}

}
