package com.beidasoft.xzzf.utility.toolManage.dao;


import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.utility.toolManage.bean.Tools;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 实用工具表DAO类
 */
@Repository
public class ToolsDao extends TeeBaseDao<Tools> {
	   
	/**
	 * 保存
	 */
	public void saveTools(Tools tools, HttpServletRequest request) {
		tools.setId(UUID.randomUUID().toString());//uuid
		Date date = new Date();
		tools.setUploadDate(date);//上传时间
		tools.setUpdateDate(date);//更新时间
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		tools.setUploadPersonId(user.getUuid());//上传人
		tools.setUpdatePersonId(user.getUuid());//更新人
		super.save(tools);
	}
	
	/**
	 * 修改
	 */
	public void updateTools(Tools tools, HttpServletRequest request) {
		Date date = new Date();
		tools.setUpdateDate(date);//更新时间
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		tools.setUpdatePersonId(user.getUuid());//更新人
		super.saveOrUpdate(tools);
	}
	
	
	/**
	 * 根据id查询
	 */
	public Tools loadById(String id) {
		Tools tools = super.get(id);
		return tools;
	}
	
	/**
	 * 删除
	 */
	public void deleteTools(String id) {
		super.delete(id);
	}
		
}
