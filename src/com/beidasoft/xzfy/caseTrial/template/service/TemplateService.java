package com.beidasoft.xzfy.caseTrial.template.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzfy.caseTrial.template.bean.Template;
import com.beidasoft.xzfy.caseTrial.template.dao.TemplateDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TemplateService extends TeeBaseService {
	
	@Autowired
	private TemplateDao templateDao;
	
	/**
	 * Description:保存
	 * @author ZCK
	 * @param template
	 * void
	 */
	public void saveTemplate(Template template) {
		templateDao.save(template);
	}
	/**
	 * Description:获取列表页
	 * @author ZCK
	 * @param request
	 * @param gm
	 * @return
	 * @throws SQLException
	 * TeeEasyuiDataGridJson
	 */
	public TeeEasyuiDataGridJson getAll(HttpServletRequest request,TeeDataGridModel gm) throws SQLException {
	 	TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		//创建sql
	 	String hql = "from Template where isDelete = 0 ";
		List<Template> caseList = templateDao.getAll(hql, gm.getFirstResult(), gm.getRows(), null);
		//获得总记录数
		long total = templateDao.countByList("select count(*) "+hql, null);
		json.setRows(caseList);
		json.setTotal(total);
		return json;
	 }
	
	/**
	 * Description:删除
	 * @author ZCK
	 * @param id
	 * void
	 */
	public void del(String id) {
		Template template = templateDao.get(id);
		template.setIsdelete("1");
		templateDao.update(template);
	}
	
	/**
	 * Description:单条查询
	 * @author ZCK
	 * @param id
	 * @return
	 * TeeJson
	 */
	public TeeJson getById(String id) {
		TeeJson json = new TeeJson();
		Template template = templateDao.get(id);
		json.setRtData(template);
		return json;
	}
	/**
	 * Description:查询
	 * @author ZCK
	 * @param wjlxmc
	 * @param typeCode
	 * @param organId
	 * @return
	 * List
	 */
	public List<Template> getByMcAndCodeAndOrganId(String wjlxmc,String typeCode,String organId) {
		List<String> list = new ArrayList<String>();
		list.add(wjlxmc);
		list.add(typeCode);
		list.add(organId);
		return templateDao.getByMcAndCodeAndOrganId(list);
	}
}
