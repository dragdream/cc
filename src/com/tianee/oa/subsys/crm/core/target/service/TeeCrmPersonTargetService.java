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
import com.tianee.oa.subsys.crm.core.target.bean.TeeCrmPersonTarget;
import com.tianee.oa.subsys.crm.core.target.dao.TeeCrmPersonTargetDao;
import com.tianee.oa.subsys.crm.core.target.model.TeeCrmDeptTargetModel;
import com.tianee.oa.subsys.crm.core.target.model.TeeCrmPersonTargetModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeCrmPersonTargetService extends TeeBaseService {
	@Autowired
	private TeeCrmPersonTargetDao dao;

	/**
	 * 部门目标列表 按年分
	 * 
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson getPersonTargetListByDept(TeeDataGridModel dm,
			HttpServletRequest request, TeeCrmPersonTargetModel model) {
		// 获取当前登录的用户
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 获取年份
		int year = Integer.parseInt(request.getParameter("year"));
		// 获取部门id
		int deptId = Integer.parseInt(request.getParameter("deptId"));

		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		// hql查询语句
		String hql = "from TeeCrmPersonTarget p where p.year=" + year
				+ " and p.targetUser.dept.uuid=" + deptId;
		// 设置总记录数
		json.setTotal(dao.countByList("select count(*) " + hql, null));

		int firstIndex = 0;
		// 获取开始索引位置
		firstIndex = (dm.getPage() - 1) * dm.getRows();
		List<TeeCrmPersonTarget> list = dao.pageFindByList(hql,
				(dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);

		List<TeeCrmPersonTargetModel> modelList = new ArrayList<TeeCrmPersonTargetModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeCrmPersonTargetModel modeltemp = parseReturnModel(
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
	public TeeCrmPersonTargetModel parseReturnModel(TeeCrmPersonTarget obj,
			boolean isEdit) {

		TeeCrmPersonTargetModel model = new TeeCrmPersonTargetModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);

		if (obj.getCrUser() != null && obj.getCrUser().getUuid() != 0) {
			model.setCrUserId(String.valueOf(obj.getCrUser().getUuid()));
			model.setCrUserName(obj.getCrUser().getUserName());
		}

		if (obj.getTargetUser() != null && obj.getTargetUser().getUuid() != 0) {
			model.setTargetUserId(obj.getTargetUser().getUuid());
			model.setTargetUserName(obj.getTargetUser().getUserName());
		}

		if (obj.getTargetUser().getDept() != null
				&& obj.getTargetUser().getDept().getUuid() != 0) {
			model.setDeptId(obj.getTargetUser().getDept().getUuid());
			model.setDeptName(obj.getTargetUser().getDept().getDeptName());
		}

		String createTime = TeeDateUtil.format(obj.getCreateTime().getTime(),
				"yyyy-MM-dd");
		model.setCrTimeStr(createTime);

		return model;
	}

	/**
	 * 转换成保存对象
	 * 
	 * @param model
	 * @param obj
	 * @return
	 */
	public TeeCrmPersonTarget parseObj(TeeCrmPersonTargetModel model,
			TeeCrmPersonTarget obj) {
		if (model == null) {
			return obj;
		}
		try {
			BeanUtils.copyProperties(model, obj);

			obj.setCreateTime(TeeDateUtil.parseCalendar(model.getCrTimeStr()));

			TeePerson targetUser = new TeePerson();
			targetUser.setUuid(model.getTargetUserId());

			obj.setTargetUser(targetUser);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 获取某年某部门的目标总额 和 部门下个体的目标总额
	 * 
	 * @param year
	 * @return
	 */
	public Object getSumTarget(int year, int deptId) {
		// 获取某年某部门的目标总额
		String hql = "select d.total as total from  TeeCrmDeptTarget  d where d.year="
				+ year + " and d.dept.uuid=" + deptId;
		List<Map> list = simpleDaoSupport.getMaps(hql, null);
		List<TeeCrmDeptTarget> dlist = new ArrayList<TeeCrmDeptTarget>();
		for (Map data : list) {
			TeeCrmDeptTarget dt = new TeeCrmDeptTarget();
			dt.setTotal(TeeStringUtil.getDouble(data.get("total"), 0));
			dlist.add(dt);
		}
		double deptTotal;
		if (dlist.size() > 0) {
			deptTotal = dlist.get(0).getTotal();
		} else {
			deptTotal = 0;
		}

		// 获取某年某部门的个体目标总额
		String hql1 = "select p.total as total from  TeeCrmPersonTarget  p where p.year="
				+ year + " and p.targetUser.dept.uuid=" + deptId;
		List<Map> list1 = simpleDaoSupport.getMaps(hql1, null);
		List<TeeCrmPersonTarget> plist = new ArrayList<TeeCrmPersonTarget>();
		for (Map data : list1) {
			TeeCrmPersonTarget pt = new TeeCrmPersonTarget();
			pt.setTotal(TeeStringUtil.getDouble(data.get("total"), 0));
			plist.add(pt);
		}
		double personTotal = 0;
		if (plist.size() > 0) {
			for (TeeCrmPersonTarget teeCrmPersonTarget : plist) {
				personTotal += teeCrmPersonTarget.getTotal();
			}
		} else {
			personTotal = 0;
		}

		Map json = new HashMap();
		json.put("sumDept", deptTotal);
		json.put("sumPerson", personTotal);

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
		TeeCrmPersonTarget target = new TeeCrmPersonTarget();
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
	public TeeJson getPersonTargetById(int sid) {
		TeeJson json = new TeeJson();
		TeeCrmPersonTarget target = dao.get(sid);

		TeeCrmPersonTargetModel model = parseReturnModel(target, false);
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
			TeeCrmPersonTargetModel model) {
		TeeJson json = new TeeJson();
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(
					TeeConst.LOGIN_USER);
			int year=model.getYear();
			int sid=model.getSid();
			int targetUserId=model.getTargetUserId();
			
			if(targetUserId==0){
            	json.setRtState(false);
				json.setRtMsg("该部门暂无人员!");
            }else{
            	if(isExist(year, sid, targetUserId)){
    				json.setRtMsg("该年份该人员的目标已存在，请重新选择！");
    				json.setRtState(false);
    			}else{
    			if (model.getSid() > 0) {    
    				TeeCrmPersonTarget obj = dao.get(model.getSid());
    				if (obj != null) {
    					this.parseObj(model, obj);
    					obj.setCrUser(person);
    					Calendar c = Calendar.getInstance();
    					obj.setCreateTime(c);
    					dao.update(obj);
    				}
    			} else {			
    				TeeCrmPersonTarget obj = new TeeCrmPersonTarget();
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
	 * 新增或者编辑 保存的时候判断该年份的该人员目标是否已经存在
	 * 
	 * @param year
	 * @return
	 */
	public boolean isExist(int year, int sid, int targetUserId) {
		boolean flag = false;
		String hql = "";
		if (sid > 0) {
			hql = "from TeeCrmPersonTarget p where p.year=" + year
					+ " and p.sid!=" + sid + " and p.targetUser.uuid="
					+ targetUserId;
		} else {
			hql = "from TeeCrmPersonTarget p where p.year=" + year
					+ " and p.targetUser.uuid=" + targetUserId;
		}

		List<TeeCrmPersonTarget> list = dao.executeQuery(hql, null);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getYear() == year
						&& list.get(i).getTargetUser().getUuid() == targetUserId) {
					flag = true;
					break;
				}
			}
		}
		return flag;

	}
	
	
	public TeeEasyuiDataGridJson listTeeCrmPersonTargets(TeeDataGridModel dm,
			HttpServletRequest request, TeeCrmPersonTargetModel model) {
		//获取当前登录人员
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		//实例化一个Easyui列表返回对象
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		//拼接HQL，获取当前登陆人的个人目标财年数据
		String hql = "from TeeCrmPersonTarget where targetUser.uuid = "+loginPerson.getUuid();
		//通过hql获取到实体对象
		List<TeeCrmPersonTarget> targetList = simpleDaoSupport.pageFindByList(hql, dm.getFirstResult(), dm.getRows(), null);
		//声明modelList
		List<TeeCrmPersonTargetModel> modelList = new ArrayList();
		//循环targetList，将实体转换为model
		for(TeeCrmPersonTarget personTarget:targetList){
			//创建一个新的model实例
			TeeCrmPersonTargetModel modelTarget = new TeeCrmPersonTargetModel();
			//批量给modelTarget赋值
			BeanUtils.copyProperties(personTarget, modelTarget);
			//将个别特殊的变量进行手动赋值
			modelTarget.setDeptName(personTarget.getTargetUser().getDept().getDeptName());
			modelTarget.setDeptId(personTarget.getTargetUser().getDept().getUuid());
			modelTarget.setCrUserName(personTarget.getCrUser().getUserName());
			modelTarget.setCrUserId(personTarget.getCrUser().getUserId());
			//modelTarget.setYear(personTarget.getYear());
			modelList.add(modelTarget);
		}
		json.setRows(modelList);
		
		return json;
	}
}
