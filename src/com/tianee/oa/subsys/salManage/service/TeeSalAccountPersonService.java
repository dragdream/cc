package com.tianee.oa.subsys.salManage.service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.salManage.bean.TeeHrSalData;
import com.tianee.oa.subsys.salManage.bean.TeeSalAccount;
import com.tianee.oa.subsys.salManage.bean.TeeSalAccountPerson;
import com.tianee.oa.subsys.salManage.bean.TeeSalDataPerson;
import com.tianee.oa.subsys.salManage.bean.TeeSalItem;
import com.tianee.oa.subsys.salManage.dao.TeeSalAccountDao;
import com.tianee.oa.subsys.salManage.dao.TeeSalAccountPersonDao;
import com.tianee.oa.subsys.salManage.model.TeeSalAccountModel;
import com.tianee.oa.subsys.salManage.model.TeeSalAccountPersonModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeSalAccountPersonService  extends TeeBaseService{

	@Autowired
	TeeSalAccountDao accountDao;
	@Autowired
	TeeSalAccountPersonDao accountPersonDao;
	@Autowired
	TeePersonService personService;
	/**
	 * 新增或者更新
	 * @param map
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(Map map ,  TeeSalAccountModel model){
		TeeJson json = new TeeJson();
	
		if(model.getSid() > 0){
			TeeSalAccount account = accountDao.get(model.getSid());
			account.setRemark(model.getRemark());
			account.setAccountSort(model.getAccountSort());
			account.setAccountName(model.getAccountName());
			account.setAccountNo(model.getAccountNo());
			accountDao.update(account);
		}else{
			TeeSalAccount account = new TeeSalAccount();
			BeanUtils.copyProperties(model, account);
			accountDao.save(account);
		}
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 账套人员 通用列表
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getManageAccountPersonList(TeeDataGridModel dm, HttpServletRequest request, TeeSalAccountPersonModel model) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		Map requestDatas = TeeServletUtility.getParamMap(request);
		String userName = TeeStringUtil.getString(requestDatas.get("userName"));

		String queryStr = " 1=1";

		String hql = "from TeeSalAccountPerson sap where sap.account.sid = " + model.getAccountId();
		List param = new ArrayList();
		if (!TeeUtility.isNullorEmpty(userName)) {
			hql += " and sap.user.userId like ? or sap.user.userName like ? or sap.user.dept.deptName like ? or sap.user.userRole.roleName like ?";
			param.add("%" + userName + "%");
			param.add("%" + userName + "%");
			param.add("%" + userName + "%");
			param.add("%" + userName + "%");
		}
	
		j.setTotal(accountPersonDao.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += "";
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeSalAccountPerson> list = accountPersonDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		List<TeeSalAccountPersonModel> modelList = new ArrayList<TeeSalAccountPersonModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeSalAccountPersonModel modeltemp = parseModel(list.get(i),false);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}


	

	/**
	 * 转换对象
	 * @param account
	 * @return
	 */
	public TeeSalAccountPersonModel parseModel(TeeSalAccountPerson account , boolean isSimple){
		TeeSalAccountPersonModel model = new TeeSalAccountPersonModel();
		if(account != null){
			BeanUtils.copyProperties(account, model);
			if(!isSimple){
				TeePerson person = account.getUser();
				TeePersonModel personModel  = new TeePersonModel();
				if(person != null){
					personModel = personService.parseModel(person, true);
				}
				model.setPersonModel(personModel);
			}
		}
		return model;
	}
	
	/**
	 * 添加账套人员
	 * @param map
	 * @param model
	 * @return
	 */
	public TeeJson addPersonByAccount(Map map , TeeSalAccountPersonModel model){
		TeeJson json = new TeeJson();
		String userIds = TeeStringUtil.getString((String)map.get("userIds"));
		TeeSalAccount account = accountDao.get(model.getAccountId());
		if(account == null){
			json.setRtState(false);
			json.setRtMsg("请先选择工资账套后再进行添加人员！");
			return json;
		}
		
		//先查出已经存在的人员
		List<TeeSalAccountPerson> sapList = accountPersonDao.getListByAcountId(account.getSid());
		List<Integer> idList = new ArrayList<Integer>();
		for (int i = 0; i < sapList.size(); i++) {
			idList.add(sapList.get(i).getUser().getUuid());
		}
		//添加人员
		if(!userIds.equals("") ){
			if(userIds.endsWith(",")){
				userIds = userIds.substring(0, userIds.length() - 1);
			}
			List<TeePerson> listPerson = personService.getPersonByUuids(userIds);
			String userIdArray[] = userIds.split(",");
			Map salDataMap = null;
			List<TeeSalItem> salItems = simpleDaoSupport.find("from TeeSalItem where accountId="+model.getAccountId()+" order by sid asc", null);
			for (int i = 0; i < listPerson.size(); i++) {
				if( !idList.contains(listPerson.get(i).getUuid())){//如果不存在
					TeeSalAccountPerson sap = new TeeSalAccountPerson();
					sap.setAccount(account);
					sap.setUser(listPerson.get(i));
					accountPersonDao.save(sap);
					
					//加入个性化基数数据
					TeeHrSalData hrSalData = new TeeHrSalData();
					hrSalData.setUser(listPerson.get(i));
					hrSalData.setAccountId(account.getSid());
					
					salDataMap = TeeJsonUtil.bean2ProtogenesisMap(hrSalData);
					for(TeeSalItem item:salItems){
						salDataMap.put(item.getItemColumn(),TeeStringUtil.getDouble(item.getFormula(), 0));
					}
					
					
					try {
						hrSalData = (TeeHrSalData) TeeJsonUtil.convertMap2Bean(hrSalData, TeeHrSalData.class, salDataMap);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
					simpleDaoSupport.save(hrSalData);
				}	
			}
		}
		json.setRtState(true);
		return json;
	}
	/**
	 * 移除账套人员
	 * @param map
	 * @param model
	 * @return
	 */
	public TeeJson deleteByIdsAndAccountId(Map map , TeeSalAccountPersonModel model){
		TeeJson json = new TeeJson();
		String ids = TeeStringUtil.getString((String)map.get("ids"));
		//TeeSalAccount account = accountDao.get(model.getAccountId());
		if(model.getAccountId() == 0){
			json.setRtState(false);
			json.setRtMsg("请先选择工资账套后再进行添加人员！");
			return json;
		}
		
		List<Integer> list = simpleDaoSupport.find("select user.uuid from TeeSalAccountPerson where "+TeeDbUtility.IN("sid", ids), null);
		accountPersonDao.delByIds(ids , model.getAccountId());
		
		//删除对应个性化设置
		simpleDaoSupport.executeUpdate("delete from TeeHrSalData where "+TeeDbUtility.IN("user.uuid", list)+" and accountId="+model.getAccountId(), null);
		
		json.setRtState(true);
		return json;
	}
	
}
