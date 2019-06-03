package com.tianee.oa.subsys.crm.core.target.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.target.bean.TeeCrmCompanyTarget;
import com.tianee.oa.subsys.crm.core.target.dao.TeeCrmCompanyTargetDao;
import com.tianee.oa.subsys.crm.core.target.model.TeeCrmCompanyTargetModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;

@Service
public class TeeCrmCompanyTargetService extends TeeBaseService {
	@Autowired
	private TeeCrmCompanyTargetDao dao;

	/**
	 * 获取公司目标的集合
	 * 
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson getCompanyTargetList(TeeDataGridModel dm,
			HttpServletRequest request, TeeCrmCompanyTargetModel model) {
		// 获取当前登录的用户
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		// hql查询语句
		String hql = "from TeeCrmCompanyTarget";

		// 设置总记录数
		json.setTotal(dao.countByList("select count(*) " + hql, null));

		int firstIndex = 0;
		// 获取开始索引位置
		firstIndex = (dm.getPage() - 1) * dm.getRows();
		List<TeeCrmCompanyTarget> list = dao.pageFindByList(hql,
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);

		List<TeeCrmCompanyTargetModel> modelList = new ArrayList<TeeCrmCompanyTargetModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeCrmCompanyTargetModel modeltemp = parseReturnModel(
						list.get(i), false);
				modelList.add(modeltemp);
			}
		}

		json.setRows(modelList);// 设置返回的行

		return json;
	}

	/**
	 * 转换成返回对象
	 * 
	 * @param obj
	 * @param isEdit
	 * @return
	 */
	public TeeCrmCompanyTargetModel parseReturnModel(TeeCrmCompanyTarget obj,
			boolean isEdit) {

		TeeCrmCompanyTargetModel model = new TeeCrmCompanyTargetModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);

		if (obj.getCrUser() != null && obj.getCrUser().getUuid() != 0) {
			model.setCrUserId(obj.getCrUser().getUuid());
			model.setCrUserName(obj.getCrUser().getUserName());
		}
		String createTime = TeeDateUtil.format(obj.getCreateTime().getTime(),
				"yyyy-MM-dd");
		model.setCreateTimeStr(createTime);

		return model;
	}

	/**
	 * 转换成保存对象
	 * 
	 * @param model
	 * @param obj
	 * @return
	 */
	public TeeCrmCompanyTarget parseObj(TeeCrmCompanyTargetModel model,
			TeeCrmCompanyTarget obj) {
		if (model == null) {
			return obj;
		}
		try {
			BeanUtils.copyProperties(model, obj);

			obj.setCreateTime(TeeDateUtil.parseCalendar(model
					.getCreateTimeStr()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 删除公司目标
	 * 
	 * @param sid
	 * @return
	 */
	public TeeJson deleteCompanyTargetById(int sid) {
		TeeJson json = new TeeJson();
		TeeCrmCompanyTarget target = new TeeCrmCompanyTarget();
		target.setSid(sid);
		dao.deleteByObj(target);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

	/**
	 * 根据id获取详情
	 * 
	 * @param sid
	 * @return
	 */
	public TeeJson getCompanyTargetById(int sid) {
		TeeJson json = new TeeJson();
		TeeCrmCompanyTarget target = dao.get(sid);

		TeeCrmCompanyTargetModel model = parseReturnModel(target, false);
		json.setRtData(model);
		json.setRtMsg(null);
		json.setRtState(true);
		return json;
	}

	/**
	 * 新增或者更新
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request,
			TeeCrmCompanyTargetModel model) {
		TeeJson json = new TeeJson();
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(
					TeeConst.LOGIN_USER);

			int year = model.getYear();
			int sid=model.getSid();
			if (isYearExist(year,sid)) {
                json.setRtState(false);
                json.setRtMsg("该年份的公司目标已经存在，请重新选择！");
			} else {
				if (model.getSid() > 0) {
					TeeCrmCompanyTarget obj = dao.get(model.getSid());
					if (obj != null) {
						this.parseObj(model, obj);
						obj.setCrUser(person);
						Calendar c = Calendar.getInstance();
						obj.setCreateTime(c);
						dao.update(obj);
					}
				} else {
					TeeCrmCompanyTarget obj = new TeeCrmCompanyTarget();
					this.parseObj(model, obj);
					obj.setCrUser(person);
					Calendar c = Calendar.getInstance();
					obj.setCreateTime(c);
					dao.save(obj);
				}

				json.setRtState(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * 新增或者编辑 保存的时候判断该年份的公司目标是否已经存在
	 * 
	 * @param year
	 * @return
	 */
	public boolean isYearExist(int year,int sid) {
		boolean flag = false;
		String hql="";
		if(sid>0){	
			hql = "from TeeCrmCompanyTarget c where c.year=" + year+" and c.sid!="+sid;
		}else{
			hql = "from TeeCrmCompanyTarget c where c.year=" + year;
		}
		

		List<TeeCrmCompanyTarget> list = dao.executeQuery(hql, null);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getYear() == year) {
					flag = true;
					break;
				}
			}
		}
		return flag;

	}
}
