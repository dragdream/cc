package com.tianee.oa.subsys.crm.core.target.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.target.bean.TeeCrmCompanyTarget;
import com.tianee.oa.subsys.crm.core.target.bean.TeeCrmDeptTarget;
import com.tianee.oa.subsys.crm.core.target.dao.TeeCrmDeptTargetDao;
import com.tianee.oa.subsys.crm.core.target.model.TeeCrmDeptTargetModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeCrmDeptTargetService extends TeeBaseService {
	@Autowired
	private TeeCrmDeptTargetDao dao;

	/**
	 * 部门目标列表 按年分
	 * 
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson getDeptTargetList(TeeDataGridModel dm,
			HttpServletRequest request, TeeCrmDeptTargetModel model) {
		// 获取当前登录的用户
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 获取年份
		int year = Integer.parseInt(request.getParameter("year"));

		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		// hql查询语句
		String hql = "from TeeCrmDeptTarget c where c.year=" + year;
		// 设置总记录数
		json.setTotal(dao.countByList("select count(*) " + hql, null));

		int firstIndex = 0;
		// 获取开始索引位置
		firstIndex = (dm.getPage() - 1) * dm.getRows();
		List<TeeCrmDeptTarget> list = dao.pageFindByList(hql,
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);

		List<TeeCrmDeptTargetModel> modelList = new ArrayList<TeeCrmDeptTargetModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeCrmDeptTargetModel modeltemp = parseReturnModel(list.get(i),
						false);
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
	public TeeCrmDeptTargetModel parseReturnModel(TeeCrmDeptTarget obj,
			boolean isEdit) {

		TeeCrmDeptTargetModel model = new TeeCrmDeptTargetModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);

		if (obj.getCrUser() != null && obj.getCrUser().getUuid() != 0) {
			model.setCrUserId(obj.getCrUser().getUuid());
			model.setCrUserName(obj.getCrUser().getUserName());
		}

		if (obj.getDept() != null && obj.getDept().getUuid() != 0) {
			model.setDeptId(obj.getDept().getUuid());
			model.setDeptName(obj.getDept().getDeptName());
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
	public TeeCrmDeptTarget parseObj(TeeCrmDeptTargetModel model,
			TeeCrmDeptTarget obj) {
		if (model == null) {
			return obj;
		}
		try {
			BeanUtils.copyProperties(model, obj);

			obj.setCreateTime(TeeDateUtil.parseCalendar(model
					.getCreateTimeStr()));

			TeeDepartment dept = new TeeDepartment();
			dept.setUuid(model.getDeptId());

			obj.setDept(dept);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 获取某年公司的目标总额 和 部门的目标总额
	 * 
	 * @param year
	 * @return
	 */
	public Object getSumTarget(int year) {
		// 获取公司某年的目标总额
		String hql = "select c.total as total from  TeeCrmCompanyTarget  c where c.year="
				+ year;
		List<Map> list = simpleDaoSupport.getMaps(hql, null);
		List<TeeCrmCompanyTarget> clist = new ArrayList<TeeCrmCompanyTarget>();
		for (Map data : list) {
			TeeCrmCompanyTarget ct = new TeeCrmCompanyTarget();
			ct.setTotal(TeeStringUtil.getDouble(data.get("total"), 0));
			clist.add(ct);
		}
		double companyTotal;
		if (clist.size() > 0) {
			companyTotal = clist.get(0).getTotal();
		} else {
			companyTotal = 0;
		}

		// 获取部门某年的目标总额
		String hql1 = "select d.total as total from  TeeCrmDeptTarget  d where d.year="
				+ year;
		List<Map> list1 = simpleDaoSupport.getMaps(hql1, null);
		List<TeeCrmDeptTarget> dlist = new ArrayList<TeeCrmDeptTarget>();
		for (Map data : list1) {
			TeeCrmDeptTarget dt = new TeeCrmDeptTarget();
			dt.setTotal(TeeStringUtil.getDouble(data.get("total"), 0));
			dlist.add(dt);
		}
		double deptTotal = 0;
		if (dlist.size() > 0) {
			for (TeeCrmDeptTarget teeCrmDeptTarget : dlist) {
				deptTotal += teeCrmDeptTarget.getTotal();
			}
		} else {
			deptTotal = 0;
		}

		Map json = new HashMap();
		json.put("sumCompany", companyTotal);
		json.put("sumDept", deptTotal);

		// String
		// json="[{'sumCompany':"+companyTotal+",'sumDept':"+deptTotal+"}]";

		return json;
	}

	/**
	 * 根据id删除部门目标
	 * 
	 * @param sid
	 * @return
	 */
	public TeeJson deleteTargetById(int sid) {
		TeeJson json = new TeeJson();
		TeeCrmDeptTarget target = new TeeCrmDeptTarget();
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
	public TeeJson getDeptTargetById(int sid) {
		TeeJson json = new TeeJson();
		TeeCrmDeptTarget target = dao.get(sid);

		TeeCrmDeptTargetModel model = parseReturnModel(target, false);
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
			TeeCrmDeptTargetModel model) {
		TeeJson json = new TeeJson();
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(
					TeeConst.LOGIN_USER);
			int year = model.getYear();
			int sid = model.getSid();
			int deptId = model.getDeptId();
            if(deptId==0){
            	json.setRtState(false);
				json.setRtMsg("请选择部门!");
            }else{
            	if (isExist(year, deptId, sid)) {
    				json.setRtState(false);
    				json.setRtMsg("该年份该部门的目标已经存在，请重新选择！");
    			} else {
    				if (model.getSid() > 0) {
    					TeeCrmDeptTarget obj = dao.get(model.getSid());
    					if (obj != null) {
    						this.parseObj(model, obj);
    						obj.setCrUser(person);
    						Calendar c = Calendar.getInstance();
    						obj.setCreateTime(c);
    						dao.update(obj);
    					}
    				} else {
    					TeeCrmDeptTarget obj = new TeeCrmDeptTarget();
    					this.parseObj(model, obj);
    					obj.setCrUser(person);
    					Calendar c = Calendar.getInstance();
    					obj.setCreateTime(c);
    					dao.save(obj);
    				}

    				json.setRtState(true);
    			}
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 新增或者编辑 保存的时候判断该年份的该部门目标是否已经存在
	 * 
	 * @param year
	 * @return
	 */
	public boolean isExist(int year, int deptId, int sid) {
		boolean flag = false;
		String hql = "";
		if (sid > 0) {
			hql = "from TeeCrmDeptTarget d where d.year=" + year
					+ " and d.sid!=" + sid + " and d.dept.uuid=" + deptId;
		} else {
			hql = "from TeeCrmDeptTarget d where d.year=" + year
					+ " and d.dept.uuid=" + deptId;
		}

		List<TeeCrmDeptTarget> list = dao.executeQuery(hql, null);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getYear() == year
						&& list.get(i).getDept().getUuid() == deptId) {
					flag = true;
					break;
				}
			}
		}
		return flag;

	}
}
