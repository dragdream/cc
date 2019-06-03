package com.beidasoft.xzfy.caseTrial.caseInvestigation.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzfy.caseTrial.caseInvestigation.bean.CaseInvestigation;
import com.beidasoft.xzfy.caseTrial.caseInvestigation.dao.CaseInvestigationDao;
import com.beidasoft.xzfy.caseTrial.caseInvestigation.model.CaseInvestigationModel;
import com.beidasoft.xzfy.caseTrial.common.WebUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;


@Service("CaseInvestigationService")
public class CaseInvestigationService extends TeeBaseService{
	@Autowired
	private CaseInvestigationDao dao;
	
	/**
	 * Description:调查管理新增
	 * @author ZCK
	 * @version 0.1 2019年4月23日
	 * @param request
	 * @param json
	 * @return CaseInvestigation
	 */
	public void save(HttpServletRequest request,TeeJson json) throws Exception{
		//获取当前人
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		CaseInvestigation caseInvestigation=new CaseInvestigation();
		String id = request.getParameter("id");
		caseInvestigation.setId(id);
		//调用共通方法给对象赋值
		WebUtils.requestToBean(request, caseInvestigation);
		WebUtils.setAddInfo(caseInvestigation, loginUser);
		//保存或者更新
		dao.save(caseInvestigation);
		
	}
	public void update(HttpServletRequest request,TeeJson json) throws Exception{
		//获取当前人
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		CaseInvestigation caseInvestigation=new CaseInvestigation();
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			caseInvestigation.setId(id);
			//调用共通方法给对象赋值
			WebUtils.requestToBean(request, caseInvestigation);
			WebUtils.setModifyInfo(caseInvestigation, loginUser);
			//保存或者更新
			dao.update(caseInvestigation);
		}
		
	}
		/**
		 * Description:调查管理查询
		 * @author ZCK
		 * @version 0.1 2019年4月23日
		 * @param caseId
		 * @return
		 * CaseInvestigation
		 */
		public TeeEasyuiDataGridJson getListByCaseId(TeeDataGridModel dm,HttpServletRequest request) {
			TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
			List<Object> param = new ArrayList<Object>();
			//查询出该案件下的所有调查管理
			String hql = " from CaseInvestigation where caseId = ? and isDelete = 0 order by createdTime asc";
			param.add(request.getParameter("caseId"));
			// 设置总记录数
			json.setTotal(dao.countByList("select count(*) " + hql, param));
			//分页查询
			List<CaseInvestigation> list = dao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);
			//model集合
			List<CaseInvestigationModel> modelList = new ArrayList<CaseInvestigationModel>();
			//类型转换、modelList内添加对象
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					CaseInvestigationModel modelTemp = new CaseInvestigationModel();
					CaseInvestigation temp = list.get(i);
					BeanUtils.copyProperties(temp, modelTemp);
					modelList.add(modelTemp);
				}
			}
			json.setRows(modelList);// 设置返回的行
			return json;
		}
		/**
		 * Description:通过安检Id查询调查管理数据
		 * @author zhangchengkun
		 * @version 0.1 2019年5月21日
		 * @param request
		 * @return  TeeJson
		 */
		public TeeJson getListByCaseIdRtJson(HttpServletRequest request) {
			TeeJson json = new TeeJson();
			String caseId = request.getParameter("caseId");
			String[] param = {caseId};
			//查询出该案件下的所有调查管理
			String hql = " from CaseInvestigation where caseId = ? and isDelete = 0 order by createdTime asc";
			// 设置总记录数
			//分页查询
			List<CaseInvestigation> list = null;
			try {
				list = dao.executeQuery(hql, param);
				json.setRtState(true);
				json.setRtData(list);
			} catch (Exception e) {
				json.setRtState(false);
				json.setRtMsg("操作失败,请联系管理员!");
			}
			return json;
		}
		
		/**
		 * Description:删除调查管理
		 * @author ZCK
		 * @version 0.1 2019年4月23日
		 * @param request
		 * void
		 */
		public void delete(HttpServletRequest request) {
			CaseInvestigation caseInvestigation=new CaseInvestigation();
			String id = request.getParameter("id");
			caseInvestigation.setId(id);
			caseInvestigation.setIsDelete(1);
			dao.update(caseInvestigation);
		}
		
		/**
		 * Description:根据单个id获取数据
		 * @author ZCK
		 * @version 0.1 2019年4月23日
		 * @param request
		 * void
		 */
		public CaseInvestigation getById(HttpServletRequest request) {
			String id = request.getParameter("id");
			CaseInvestigation caseInvestigation = (CaseInvestigation)dao.get(id);
			return caseInvestigation;
		}
}
