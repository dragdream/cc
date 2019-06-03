package com.beidasoft.xzfy.organPerson.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.util.StringUtils;
import com.beidasoft.xzfy.organPerson.bean.OrganPersonInfo;
import com.beidasoft.xzfy.organPerson.model.request.OrganPersonListRequest;
import com.beidasoft.xzfy.utils.Const;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class OrganPersonDao extends TeeBaseDao<OrganPersonInfo>{

	/**
	 * 分页获取组织机构人员列表
	 * @param req
	 * @return
	 */
	public List<OrganPersonInfo> getOrganPersonList(OrganPersonListRequest req){
		
		StringBuffer str = new StringBuffer();
		str.append(" from OrganPersonInfo where is_delete = 0 ");
		
		// 姓名
		if( !StringUtils.isEmpty(req.getPersonName()) ){
			str.append(" and PEOPLE_NAME like '%").append(req.getPersonName()+"%'");
		}
		// 人员编制
		if( !StringUtils.isEmpty(req.getStaffing()) ){
			str.append(" and STAFFING = '").append(req.getStaffing()).append("'");
		}
		//是否显示全部
		if( Const.TYPE.ZERO.equals(req.getIsShowAll())){
			// 组织机构
			if( !StringUtils.isEmpty(req.getOrgId()) ){
				str.append(" and org_id = '").append(req.getOrgId()).append("'");
			}
		}
		
		int start = (req.getPage()-1)*req.getRows();
		List<OrganPersonInfo> list = pageFind(str.toString(), 
				start,req.getRows(), null);
		return list;
	}
	
	/**
	 * 查询组织机构人员总数
	 * @param req
	 * @return
	 */
	public int getOrganPersonListTotal(OrganPersonListRequest req){
			
		StringBuffer str = new StringBuffer();
		str.append(" select count(*) from FY_PEOPLE where is_delete = 0 ");
	
		// 姓名
		if( !StringUtils.isEmpty(req.getPersonName()) ){
			str.append(" and PEOPLE_NAME like '%").append(req.getPersonName()+"%'");
		}
		// 人员编制
		if( !StringUtils.isEmpty(req.getStaffing()) ){
			str.append(" and STAFFING = '").append(req.getStaffing()).append("'");
		}
		// 组织机构
		if( !StringUtils.isEmpty(req.getOrgId()) ){
			str.append(" and org_id = '").append(req.getOrgId()).append("'");
		}
		//是否显示全部
		if( Const.TYPE.ZERO.equals(req.getIsShowAll())){
			// 组织机构
			if( !StringUtils.isEmpty(req.getOrgId()) ){
				str.append(" and org_id = '").append(req.getOrgId()).append("'");
			}
		}
		Long total = countSQLByList(str.toString(), null);
		int num = total.intValue();
		return num;
	}
	
	/**
	 * 根据组件机构人员ID获取组织机构人员信息
	 * @param personId
	 * @return
	 */
	public OrganPersonInfo getOrganPersonInfo(String personId){
		
		StringBuffer str = new StringBuffer();
		str.append(" from OrganPersonInfo where is_delete = 0 ");
		str.append(" and PEOPLE_ID='");
		str.append(personId);
		str.append("'");
		List<OrganPersonInfo> list = pageFind(str.toString(), 
				0,10, null);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 新增组织机构人员
	 * @param person
	 */
	public void addOrganPerson(OrganPersonInfo person){
		save(person);
	}
	
	/**
	 * 更新组织机构人员信息
	 * @param person
	 */
	public void updateOrganPerson(OrganPersonInfo person){
		update(person);
	}
	
	/**
	 * 逻辑删除,更改状态
	 * @param personId
	 */
	public void deleteOrganPerson(String personIds){
		
		
		String[] ids = personIds.split(",");
		StringBuffer batchIds = new StringBuffer();
		
		for(int i=0; i<ids.length-1; i++){
			batchIds.append("'").append(ids[i]).append("'").append(",");
		}
		batchIds.append("'").append(ids[ids.length-1]).append("'");
			
		StringBuffer str = new StringBuffer();
		str.append("update FY_PEOPLE set is_delete = 1 where people_id in (");
		str.append(batchIds);
		str.append(")");
		executeNativeUpdate(str.toString(), null);
		
	}
}
