package com.beidasoft.xzfy.organPerson.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beidasoft.xzfy.organPerson.bean.OrganPersonInfo;
import com.beidasoft.xzfy.organPerson.dao.OrganPersonDao;
import com.beidasoft.xzfy.organPerson.model.request.OrganPersonAddRequest;
import com.beidasoft.xzfy.organPerson.model.request.OrganPersonListRequest;
import com.beidasoft.xzfy.organPerson.model.request.OrganPersonUpdateRequest;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;

@Service
public class OrganPersonService {
	
	@Autowired
	public OrganPersonDao personDao;
	
	/**
	 * 获取组织机构人员列表
	 * @param req
	 * @return
	 */
	public List<OrganPersonInfo> getOrganPersonList(OrganPersonListRequest req){
		
		List<OrganPersonInfo> list = personDao.getOrganPersonList(req);
		return list;
	}
	
	/**
	 * 查询组织机构人员总数
	 * @param req
	 * @return
	 */
	public int getOrganPersonListTotal(OrganPersonListRequest req){
		return personDao.getOrganPersonListTotal(req);
	}
	
	
	/**
	 * 获取组织机构人员详情
	 * @param personId
	 * @return
	 */
	public OrganPersonInfo getOrganPersonInfo(String personId){
		
		OrganPersonInfo person = personDao.getOrganPersonInfo(personId);
		return person;
	}
	
	/**
	 * 新增组织机构人员信息
	 */
	@Transactional
	public void addOrganPersonInfo(OrganPersonAddRequest req,HttpServletRequest request){
		
		OrganPersonInfo person = new OrganPersonInfo();
		person.setPersonId(StringUtils.getUUId());
		person.setPersonName(req.getPersonName());
		person.setSex(req.getSex());
		person.setIdCard(req.getIdCard());
		
		person.setStaffing(req.getStaffing());
		person.setStaffingName(req.getStaffingName());
		person.setLevelCode(req.getLevelCode());
		person.setLevelName(req.getLevelName());
		person.setEducationCode(req.getEducationCode());
		person.setEducationName(req.getEducationName());
		
		person.setIsLaw(req.getIsLaw());
		person.setIsParty(req.getIsParty());
		
		person.setPhone(req.getPhone());
		person.setEmail(req.getEmail());
		
		person.setOrgId(req.getOrgId());
		person.setOrgName(req.getOrgName());
		
		//新增人
		TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		StringUtils.setAddInfo(person, loginUser);
		
		personDao.addOrganPerson(person);
	}
	
	/**
	 * 更新组织机构人员信息
	 */
	@Transactional
	public void updateOrganPersonInfo(OrganPersonUpdateRequest req,HttpServletRequest request){
		
		OrganPersonInfo person = new OrganPersonInfo();
		person.setPersonId(req.getPersonId());
		person.setPersonName(req.getPersonName());
		person.setSex(req.getSex());
		person.setIdCard(req.getIdCard());
		
		person.setStaffing(req.getStaffing());
		person.setStaffingName(req.getStaffingName());
		person.setLevelCode(req.getLevelCode());
		person.setLevelName(req.getLevelName());
		person.setEducationCode(req.getEducationCode());
		person.setEducationName(req.getEducationName());
		
		person.setIsLaw(req.getIsLaw());
		person.setIsParty(req.getIsParty());
		
		person.setPhone(req.getPhone());
		person.setEmail(req.getEmail());
		
		person.setOrgId(req.getOrgId());
		person.setOrgName(req.getOrgName());
		
		//更新人
		TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		StringUtils.setModifyInfo(person, loginUser);
		
		personDao.updateOrganPerson(person);
	}
	
	/**
	 * 删除组织机构人员
	 * @param orgId
	 */
	public void deleteOrganPerson(String personIds){
		
		personDao.deleteOrganPerson(personIds);
	}
	
}
