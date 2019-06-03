package com.beidasoft.xzfy.caseTrial.caseHearing.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzfy.caseTrial.caseHearing.bean.Hearing;
import com.beidasoft.xzfy.caseTrial.caseHearing.dao.HearingDao;
import com.beidasoft.xzfy.caseTrial.caseHearing.model.HearingModel;
import com.beidasoft.xzfy.caseTrial.common.WebUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;

@Service("hearingService")
public class HearingService extends TeeBaseService{
	@Autowired
	private HearingDao dao;
	
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
		Hearing hearing=new Hearing();
		String id = request.getParameter("id");
		hearing.setId(id);
		//调用共通方法给对象赋值
		WebUtils.requestToBean(request, hearing);
		WebUtils.setAddInfo(hearing, loginUser);
		//保存或者更新
		dao.save(hearing);
	}
	/**
	 * Description:听证管理更新
	 * @author zhangchengkun
	 * @version 0.1 2019年6月2日
	 * @param request
	 * @param json
	 * @throws Exception  void
	 */
	public void update(HttpServletRequest request,TeeJson json) throws Exception{
		//获取当前人
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Hearing hearing=new Hearing();
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			hearing.setId(id);
			//调用共通方法给对象赋值
			WebUtils.requestToBean(request, hearing);
			WebUtils.setModifyInfo(hearing, loginUser);
			//保存或者更新
			dao.update(hearing);
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
		public TeeEasyuiDataGridJson getListByCaseId(TeeDataGridModel dm,HttpServletRequest request,HearingModel model) {
			TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
			List<Object> param = new ArrayList<Object>();
			//查询出该案件下的所有调查管理
			String hql = " from Hearing where caseId = ? and isDelete = 0 order by createdTime asc";
			param.add(request.getParameter("caseId"));
			// 设置总记录数
			json.setTotal(dao.countByList("select count(*) " + hql, param));
			//分页查询
			List<Hearing> list = dao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);
			//model集合
			List<HearingModel> modelList = new ArrayList<HearingModel>();
			//类型转换、modelList内添加对象
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					HearingModel modelTemp = new HearingModel();
					BeanUtils.copyProperties(list.get(i), modelTemp);
					modelList.add(modelTemp);
				}
			}
			json.setRows(modelList);// 设置返回的行
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
			Hearing hearing=new Hearing();
			String id = request.getParameter("id");
			hearing.setId(id);
			hearing.setIsDelete(1);
			dao.update(hearing);
		}
		
		/**
		 * Description:根据单个id获取数据
		 * @author ZCK
		 * @version 0.1 2019年4月23日
		 * @param request
		 * void
		 */
		public Hearing getById(HttpServletRequest request) {
			String id = request.getParameter("id");
			Hearing hearing = (Hearing) dao.get(id);
			return hearing;
		}
}
