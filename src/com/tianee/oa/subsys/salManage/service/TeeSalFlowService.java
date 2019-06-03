package com.tianee.oa.subsys.salManage.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.salManage.bean.TeeSalAccountPerson;
import com.tianee.oa.subsys.salManage.bean.TeeSalFlow;
import com.tianee.oa.subsys.salManage.dao.TeeSalFlowDao;
import com.tianee.oa.subsys.salManage.model.TeeSalAccountPersonModel;
import com.tianee.oa.subsys.salManage.model.TeeSalFlowModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeSalFlowService extends TeeBaseService{

	@Autowired
	TeeSalFlowDao salFlowDao;
	@Autowired
	TeePersonDao personDao;
	/**
	 * 新增或者更新
	 * @param map
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(Map map ,  TeeSalFlowModel model){
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)map.get(TeeConst.LOGIN_USER);
		
		boolean isExists = salFlowDao.checkFlow(model);
		if(isExists){
			json.setRtState(false);
			json.setRtMsg("已经存在此新建的月份工资！");
			return json;
		}
		if(model.getSid() > 0){
			TeeSalFlow flow = salFlowDao.get(model.getSid());
			BeanUtils.copyProperties(model, flow , new String[]{"sid" ,"salCreater", "sendTime"});
			salFlowDao.update(flow);
		}else{
			TeeSalFlow flow = new TeeSalFlow();
			BeanUtils.copyProperties(model, flow);
			flow.setSendTime(new Date());
			flow.setSalCreater(person);
			salFlowDao.save(flow);
		}
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 工资流程 通用列表
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getManageSalFlowList(TeeDataGridModel dm, HttpServletRequest request, TeeSalFlowModel model) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);
		String queryStr = " 1=1";

		String hql = "from TeeSalFlow  where accountId = " + model.getAccountId();
		List param = new ArrayList();
		if(model.getSalMonth() > 0){
			hql = hql + " and salMonth = ?";
			param.add(model.getSalMonth());
		}
		if(model.getSalYear() > 0){
			hql = hql + " and salYear = ?";
			param.add(model.getSalYear());
		}
		j.setTotal(salFlowDao.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by salYear desc , salMonth desc";
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeSalFlow> list = salFlowDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		List<TeeSalFlowModel> modelList = new ArrayList<TeeSalFlowModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeSalFlowModel modeltemp = parseModel(list.get(i),false);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	/**
	 * 根据Id  获取对象
	 * @param model
	 * @return
	 */
	public TeeJson getById(TeeSalFlowModel model){
		TeeJson json = new TeeJson();
		TeeSalFlow account = salFlowDao.get(model.getSid());
		model = parseModel(account, false);
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 删除Id  获取对象
	 * @param model
	 * @return
	 */
	public TeeJson deleteByIds(Map map){
		TeeJson json = new TeeJson();
		String ids = TeeStringUtil.getString((String)map.get("ids"));
		if(!TeeUtility.isNullorEmpty(ids) ){
			salFlowDao.deleteByIds(ids);
		}
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 转换对象
	 * @param account
	 * @return
	 */
	public TeeSalFlowModel parseModel(TeeSalFlow flow , boolean isSimple){
		TeeSalFlowModel model = new TeeSalFlowModel();
		if(flow != null){
			BeanUtils.copyProperties(flow, model);
			TeePerson person = flow.getSalCreater();
			if(person != null){
				model.setSalCreaterName(person.getUserName());
				model.setCreaterId(person.getUuid());
			}
			
		}
		return model;
	}

}