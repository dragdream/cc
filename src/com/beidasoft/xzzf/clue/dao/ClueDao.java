package com.beidasoft.xzzf.clue.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.clue.bean.Clue;
import com.beidasoft.xzzf.clue.model.ClueModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class ClueDao extends TeeBaseDao<Clue>{

	String strStart = " '";
	String strFina = "' ";
	String temp = "' and '";
	
	/**
	 * 线索信息集合(线索受理)
	 * @return
	 */
	public List<Clue> findWaitClues(int firstResult,int rows,ClueModel queryModel){
		String hql = "from Clue where staus= 10 ";
		String fina =" order by staus desc, createTime desc ";
		
		if(!TeeUtility.isNullorEmpty(queryModel.getClueSource())&&!queryModel.getClueSource().equals("0")){
			hql += "and clueSource = "+strStart+queryModel.getClueSource()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getAnotherProvince())&&queryModel.getAnotherProvince()!=0){
			hql += "and anotherProvince = "+queryModel.getAnotherProvince()+" ";
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getReportForm())&&!queryModel.getReportForm().equals("0")){
			hql += "and reportForm = "+strStart+queryModel.getReportForm()+strFina;
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getCreateTimeStrStart())){
			if(!TeeUtility.isNullorEmpty(queryModel.getCreateTimeStrEnd())){
				hql += "and createTime between "+strStart+queryModel.getCreateTimeStrStart()+temp+queryModel.getCreateTimeStrEnd()+strFina;
			}
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getReportDateStrStart())){
			if(!TeeUtility.isNullorEmpty(queryModel.getReportDateStrEnd())){
				hql += "and reportDate between "+strStart+queryModel.getReportDateStrStart()+temp+queryModel.getReportDateStrEnd()+strFina;
			}
		}
		
		hql += fina; 
		
		return super.pageFind(hql, firstResult, rows, null);
	}
	
	/**
	 * 返回总记录数(线索受理)
	 * @return
	 */
	public long getWaitTotals(ClueModel queryModel){
		String hql = "select count(id) from Clue where staus=10 ";
		
		if(!TeeUtility.isNullorEmpty(queryModel.getClueSource())&&!queryModel.getClueSource().equals("0")){
			hql += "and clueSource = "+strStart+queryModel.getClueSource()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getAnotherProvince())&&queryModel.getAnotherProvince()!=0){
			hql += "and anotherProvince = "+queryModel.getAnotherProvince()+" ";
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getReportForm())&&!queryModel.getReportForm().equals("0")){
			hql += "and reportForm = "+strStart+queryModel.getReportForm()+strFina;
		}

		if(!TeeUtility.isNullorEmpty(queryModel.getCreateTimeStrStart())){
			hql += "and createTime >= "+strStart+queryModel.getCreateTimeStrStart()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getCreateTimeStrEnd())){
			hql += "and createTime <= "+strStart+queryModel.getCreateTimeStrEnd()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getReportDateStrStart())){
			hql += "and reportDate >= "+strStart+queryModel.getReportDateStrStart()+strFina;
		} 
		if(!TeeUtility.isNullorEmpty(queryModel.getReportDateStrEnd())){
			hql += "and reportDate <= "+strStart+queryModel.getReportDateStrEnd()+strFina;
		}
		
		return super.count(hql, null);
	}
	
	/**
	 * 线索信息集合(线索确认)
	 * @return
	 */
	public List<Clue> findAdmitClues(int firstResult,int rows,ClueModel queryModel){
		String hql = "from Clue where 1=1 ";
		String fina =" order by createTime desc ";
		
		String isWaitStr_1 = " and staus = 20 ";
		String isWaitStr_2 = " and staus > 20 ";
		
		if(!TeeUtility.isNullorEmpty(queryModel.getClueSource())&&!queryModel.getClueSource().equals("0")){
			hql += "and clueSource = "+strStart+queryModel.getClueSource()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getAnotherProvince())&&queryModel.getAnotherProvince()!=0){
			hql += "and anotherProvince = "+queryModel.getAnotherProvince()+" ";
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getReportForm())&&!queryModel.getReportForm().equals("0")){
			hql += "and reportForm = "+strStart+queryModel.getReportForm()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getCreateTimeStrStart())){
			hql += "and createTime >= "+strStart+queryModel.getCreateTimeStrStart()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getCreateTimeStrEnd())){
			hql += "and createTime <= "+strStart+queryModel.getCreateTimeStrEnd()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getReportDateStrStart())){
			hql += "and reportDate >= "+strStart+queryModel.getReportDateStrStart()+strFina;
		} 
		if(!TeeUtility.isNullorEmpty(queryModel.getReportDateStrEnd())){
			hql += "and reportDate <= "+strStart+queryModel.getReportDateStrEnd()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getIsWait())){
			if(queryModel.getIsWait().equals("1")) {
				hql += isWaitStr_1;
			}else if(queryModel.getIsWait().equals("2")) {
				hql += isWaitStr_2;
			}
		}else {
			hql += isWaitStr_1;
		}
		hql += fina;
		
		return super.pageFind(hql, firstResult, rows, null);
	}
	
	/**
	 * 返回总记录数(线索确认)
	 * @return
	 */
	public long getAdmitTotals(ClueModel queryModel){
		String hql = "select count(id) from Clue where 1=1";
		
		String isWaitStr_1 = " and staus = 20 ";
		String isWaitStr_2 = " and staus > 20 ";
		
		if(!TeeUtility.isNullorEmpty(queryModel.getClueSource())&&!queryModel.getClueSource().equals("0")){
			hql += "and clueSource = "+strStart+queryModel.getClueSource()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getAnotherProvince())&&queryModel.getAnotherProvince()!=0){
			hql += "and anotherProvince = "+queryModel.getAnotherProvince()+" ";
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getReportForm())&&!queryModel.getReportForm().equals("0")){
			hql += "and reportForm = "+strStart+queryModel.getReportForm()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getCreateTimeStrStart())){
			hql += "and createTime >= "+strStart+queryModel.getCreateTimeStrStart()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getCreateTimeStrEnd())){
			hql += "and createTime <= "+strStart+queryModel.getCreateTimeStrEnd()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getReportDateStrStart())){
			hql += "and reportDate >= "+strStart+queryModel.getReportDateStrStart()+strFina;
		} 
		if(!TeeUtility.isNullorEmpty(queryModel.getReportDateStrEnd())){
			hql += "and reportDate <= "+strStart+queryModel.getReportDateStrEnd()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getIsWait())){
			if(queryModel.getIsWait().equals("1")) {
				hql += isWaitStr_1;
			}else if(queryModel.getIsWait().equals("2")) {
				hql += isWaitStr_2;
			}
		}else {
			hql += isWaitStr_1;
		}
		
		return super.count(hql, null);
	}
	
	
	/**
	 * 根据分页进行查询(个人)
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<Clue> listByPage(int firstResult,int rows,ClueModel queryModel, TeePerson user){
		String hql = "from Clue where 1=1 ";
		String fina =" order by staus desc, createTime desc ";
		
		String isCreateUser_1 = " and ((createUserId =" + user.getUuid() + " and createUserName =" + strStart + 
				user.getUserName() + strFina +") or (acceptUserId =" + user.getUuid() + " and acceptUserName =" + 
				strStart + user.getUserName() + strFina +"))";
		String isCreateUser_2 = " and createUserId =" + user.getUuid() + " and createUserName =" + strStart + 
				user.getUserName() + strFina;
		
		String isWaitStr_1 = " and staus in (0,11) ";
		String isWaitStr_2 = " and staus not in (0,11) ";
		
		if(!TeeUtility.isNullorEmpty(queryModel.getClueSource())&&!queryModel.getClueSource().equals("0")){
			hql += "and clueSource = "+strStart+queryModel.getClueSource()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getAnotherProvince())&&queryModel.getAnotherProvince()!=0){
			hql += "and anotherProvince = "+queryModel.getAnotherProvince()+" ";
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getReportForm())&&!queryModel.getReportForm().equals("0")){
			hql += "and reportForm = "+strStart+queryModel.getReportForm()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getCreateTimeStrStart())){
			hql += "and createTime >= "+strStart+queryModel.getCreateTimeStrStart()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getCreateTimeStrEnd())){
			hql += "and createTime <= "+strStart+queryModel.getCreateTimeStrEnd()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getReportDateStrStart())){
			hql += "and reportDate >= "+strStart+queryModel.getReportDateStrStart()+strFina;
		} 
		if(!TeeUtility.isNullorEmpty(queryModel.getReportDateStrEnd())){
			hql += "and reportDate <= "+strStart+queryModel.getReportDateStrEnd()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getIsWait())){
			if(queryModel.getIsWait().equals("1")) {
				hql += isWaitStr_1;
				hql += isCreateUser_1;
			}else if(queryModel.getIsWait().equals("2")) {
				hql += isWaitStr_2;
				hql += isCreateUser_2;
			}
		}else {
			hql += isWaitStr_1;
			hql += isCreateUser_1;
		}
		hql += fina;
		
		return super.pageFind(hql, firstResult, rows, null);
	}
	
	
	/**
	 * 返回总记录数
	 * @return
	 */
	public long getTotal(ClueModel queryModel, TeePerson user){
		String hql = "select count(id) from Clue where 1=1 ";
		String isCreateUser_1 = " and ((createUserId =" + user.getUuid() + " and createUserName =" + strStart + 
				user.getUserName() + strFina +") or (acceptUserId =" + user.getUuid() + " and acceptUserName =" + 
				strStart + user.getUserName() + strFina +"))";
		String isCreateUser_2 = " and createUserId =" + user.getUuid() + " and createUserName =" + strStart + 
				user.getUserName() + strFina;
		String isWaitStr_1 = " and staus in (0,11) ";
		String isWaitStr_2 = " and staus not in (0,11) ";
		
		if(!TeeUtility.isNullorEmpty(queryModel.getClueSource())&&!queryModel.getClueSource().equals("0")){
			hql += "and clueSource = "+strStart+queryModel.getClueSource()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getAnotherProvince())&&queryModel.getAnotherProvince()!=0){
			hql += "and anotherProvince = "+queryModel.getAnotherProvince()+" ";
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getReportForm())&&!queryModel.getReportForm().equals("0")){
			hql += "and reportForm = "+strStart+queryModel.getReportForm()+strFina;
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getCreateTimeStrStart())){
			hql += "and createTime >= "+strStart+queryModel.getCreateTimeStrStart()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getCreateTimeStrEnd())){
			hql += "and createTime <= "+strStart+queryModel.getCreateTimeStrEnd()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getReportDateStrStart())){
			hql += "and reportDate >= "+strStart+queryModel.getReportDateStrStart()+strFina;
		} 
		if(!TeeUtility.isNullorEmpty(queryModel.getReportDateStrEnd())){
			hql += "and reportDate <= "+strStart+queryModel.getReportDateStrEnd()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getIsWait())){
			if(queryModel.getIsWait().equals("1")) {
				hql += isWaitStr_1;
				hql += isCreateUser_1;
			}else if(queryModel.getIsWait().equals("2")) {
				hql += isWaitStr_2;
				hql += isCreateUser_2;
			}
		}else {
			hql += isWaitStr_1;
			hql += isCreateUser_1;
		}
		
		return super.count(hql, null);
	}
	
	/**
	 * 分页查询回复列表
	 * @param firstResult
	 * @param rows
	 * @param queryModel
	 * @param user
	 * @return
	 */
	public List<Clue> replyListByPage(int firstResult, int rows, ClueModel queryModel, TeePerson user) {
		String hql = " from Clue where isDelete = 0 and staus >= 30 and staus < 40 ";
		
		if(!TeeUtility.isNullorEmpty(queryModel.getClueSource())&&!queryModel.getClueSource().equals("0")){
			hql += "and clueSource = "+strStart+queryModel.getClueSource()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getAnotherProvince())&&queryModel.getAnotherProvince()!=0){
			hql += "and anotherProvince = "+queryModel.getAnotherProvince()+" ";
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getReportForm())&&!queryModel.getReportForm().equals("9999")){
			hql += "and reportForm = "+strStart+queryModel.getReportForm()+strFina;
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getCreateTimeStrStart())){
			hql += "and createTime >= "+strStart+queryModel.getCreateTimeStrStart()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getCreateTimeStrEnd())){
			hql += "and createTime <= "+strStart+queryModel.getCreateTimeStrEnd()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getReportDateStrStart())){
			hql += "and reportDate >= "+strStart+queryModel.getReportDateStrStart()+strFina;
		} 
		if(!TeeUtility.isNullorEmpty(queryModel.getReportDateStrEnd())){
			hql += "and reportDate <= "+strStart+queryModel.getReportDateStrEnd()+strFina;
		}
		
		hql += " AND acceptUserId = " + user.getUuid() + " order by staus desc,createTime desc";
		return super.pageFind(hql, firstResult, rows, null);
	}
	
	/**
	 * 查询回复列表总数
	 * @param queryModel
	 * @param user
	 * @return
	 */
	public long replylistCount(ClueModel queryModel, TeePerson user) {
		String hql = " select count(id) from Clue where isDelete = 0  and staus >= 30 and staus < 40 ";
		
		if(!TeeUtility.isNullorEmpty(queryModel.getClueSource())&&!queryModel.getClueSource().equals("0")){
			hql += "and clueSource = "+strStart+queryModel.getClueSource()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getAnotherProvince())&&queryModel.getAnotherProvince()!=0){
			hql += "and anotherProvince = "+queryModel.getAnotherProvince()+" ";
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getReportForm())&&!queryModel.getReportForm().equals("0")){
			hql += "and reportForm = "+strStart+queryModel.getReportForm()+strFina;
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getCreateTimeStrStart())){
			hql += "and createTime >= "+strStart+queryModel.getCreateTimeStrStart()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getCreateTimeStrEnd())){
			hql += "and createTime <= "+strStart+queryModel.getCreateTimeStrEnd()+strFina;
		}
		
		if(!TeeUtility.isNullorEmpty(queryModel.getReportDateStrStart())){
			hql += "and reportDate >= "+strStart+queryModel.getReportDateStrStart()+strFina;
		} 
		if(!TeeUtility.isNullorEmpty(queryModel.getReportDateStrEnd())){
			hql += "and reportDate <= "+strStart+queryModel.getReportDateStrEnd()+strFina;
		}
		
		hql += " AND acceptUserId = " + user.getUuid();
		return super.count(hql, null);
	}
}

