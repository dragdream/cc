package com.beidasoft.xzfy.organ.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beidasoft.xzfy.organ.bean.OrganInfo;
import com.beidasoft.xzfy.organ.dao.OrganDao;
import com.beidasoft.xzfy.organ.model.request.OrganAddRequest;
import com.beidasoft.xzfy.organ.model.request.OrganListRequest;
import com.beidasoft.xzfy.organ.model.request.OrganUpdateRequest;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;

@Service
public class OrganService {
	
	@Autowired
	public OrganDao organDao;
	
	/**
	 * 获取组织机构列表
	 * @param req
	 * @return
	 */
	public List<OrganInfo> getOrganList(OrganListRequest req,List<TeeZTreeModel> rtData){
		
		StringBuffer str = new StringBuffer();
		String treeIds = "";
		if(rtData.size()>0){
			for(TeeZTreeModel tee : rtData){
				str.append("'");
				str.append(tee.getId().split(";")[0]);
				str.append("',");
			}
			treeIds = str.toString().substring(0, str.toString().length()-1);
		}
		else{
			treeIds = str.append("'").append(req.getTreeID().split(";")[0])
					.append("'").toString();
		}
		
		List<OrganInfo> list = organDao.getOrganList(req,treeIds);
		return list;
	}
	
	/**
	 * 获取组织机构列表总记录数
	 * @return
	 */
	public int getOrganListTotal(OrganListRequest req,List<TeeZTreeModel> rtData){
		
		StringBuffer str = new StringBuffer();
		String treeIds = "";
		if(rtData.size()>0){
			for(TeeZTreeModel tee : rtData){
				str.append("'");
				str.append(tee.getId().split(";")[0]);
				str.append("',");
			}
			treeIds = str.toString().substring(0, str.toString().length()-1);
		}
		else{
			treeIds = str.append("'").append(req.getTreeID().split(";")[0])
					.append("'").toString();
		}
		int total = organDao.getOrganListTotal(req,treeIds);
		return total;
	}
	
	/**
	 * 根据组件机构部门ID获取组织机构信息
	 * @param orgId
	 * @return
	 */
	public OrganInfo getOrganInfo(String deptId){
		
		OrganInfo org = organDao.getOrganInfo(deptId);
		return org;
	}
	
	/**
	 * 批量新增组织机构信息
	 */
	@Transactional
	public void addBatchInfo(OrganAddRequest req,HttpServletRequest request){
		
		OrganListRequest re = new OrganListRequest();
		re.setPage(1);
		re.setRows(100000);
		//所有的组织机构
		List<OrganInfo> all = organDao.getOrganList(re,"");
		
		//创建对象
		OrganInfo org = null;
		List<OrganInfo> list = new ArrayList<OrganInfo>();
		//传入的组织机构
		String[] ids = req.getOrganIds().split(",");
		String[] names = req.getOrganNames().split(",");
		int idsLen = ids.length;
		int namesLen = ids.length;
		for(int i=0;i<idsLen;i++){
			
			//判别传入组织机构是否存在所有的组织机构中
			int k=0;
			for(;k<all.size();k++){
				//存在，放在批量新增列表中
				if( ids[i].equals(all.get(k).getDeptId()) ){
					list.add(all.get(k));
					break;
				}
			}
			//不存在，列表添加一个新增的
			if(k>=all.size()){
				org = new OrganInfo();
				org.setOrgId(StringUtils.getUUId());
				if(namesLen < i){
					org.setOrgName("未知");
				}
				else{
					org.setOrgName(names[i]);
				}
				org.setDeptId(ids[i]);
				//新增人
				TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
				StringUtils.setAddInfo(org, loginUser);
				list.add(org);
			}
			
			
		}
		//删除所有记录
		organDao.deleteAllOrgan();
		//批量新增
		organDao.addOrganList(list);
	}
	
	/**
	 * 更新组织机构信息
	 */
	@Transactional
	public void updateOrganInfo(OrganUpdateRequest req,HttpServletRequest request){
		
		//创建对象
		OrganInfo org = new OrganInfo();
		org.setOrgId(req.getOrgId());
		org.setOrgName(req.getOrgName());
		org.setOrgCode(req.getOrgCode());
		
		org.setOrgLevelCode(req.getOrgLevelCode());
		org.setOrgLevelName(req.getOrgLevelName());
		org.setLegalRepresentative(req.getLegalRepresentative());
		org.setCompilersNum(req.getCompilersNum());
		org.setContacts(req.getContacts());
		org.setContactsPhone(req.getContactsPhone());
		
		org.setFax(req.getFax());
		org.setAreaCode(req.getAreaCode());
		org.setAddress(req.getAddress());
		org.setRemark(req.getRemark());
		
		org.setDeptId(req.getDeptId());
		
		//更新人
		TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		StringUtils.setModifyInfo(org, loginUser);
		
		organDao.updateOrgan(org);
	}
	
	/**
	 * 删除组织机构
	 * @param orgId
	 */
	public void deleteOrgan(String orgIds){
		
		organDao.deleteOrgan(orgIds);
	}
	
}
