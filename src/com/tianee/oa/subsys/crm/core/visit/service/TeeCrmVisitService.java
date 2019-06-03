package com.tianee.oa.subsys.crm.core.visit.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.order.bean.TeeCrmOrder;
import com.tianee.oa.subsys.crm.core.order.model.TeeCrmOrderModel;
import com.tianee.oa.subsys.crm.core.visit.bean.TeeCrmVisit;
import com.tianee.oa.subsys.crm.core.visit.model.TeeCrmVisitModel;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmVisitService extends TeeBaseService{
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeePersonDao personDao;

    /**
     * 添加拜访记录
     * @param request
     * @param model
     * @return
     */
	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmVisitModel model) {
		TeeJson json = new TeeJson();
		TeeCrmVisit visit = new TeeCrmVisit();
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cl = Calendar.getInstance();
		Calendar ncl = Calendar.getInstance();
		//编辑
		if(model.getSid() > 0){
			visit  = (TeeCrmVisit) simpleDaoSupport.get(TeeCrmVisit.class, model.getSid());
			if(visit != null){
				BeanUtils.copyProperties(model, visit);
				visit.setCreateUser(person);//创建人
				if(!TeeUtility.isNullorEmpty(model.getManagePersonId())){//负责人
					TeePerson managePerson = (TeePerson)personDao.get(model.getManagePersonId());
					visit.setManagePerson(managePerson);
				}	
				if(!TeeUtility.isNullorEmpty(model.getVisitTimeDesc())){//计划日期
					try {
						cl.setTime(sdf.parse(model.getVisitTimeDesc()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				visit.setVisitTime(cl);
				if(!TeeUtility.isNullorEmpty(model.getCustomerId())){//客户
					TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
					visit.setCustomer(customer);
				}
				if(!TeeUtility.isNullorEmpty(model.getCustomerId())&&!TeeUtility.isNullorEmpty(model.getVisitTimeDesc())){//拜访名称
					TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
					visit.setVisitName(customer.getCustomerName()+"("+model.getVisitTimeDesc()+")");
				}
				visit.setCreateTime(visit.getCreateTime());// 创建时间
				visit.setLastEditTime(ncl);//订单的最后一次修改日期
				visit.setVisitEndTime(null);  //  拜访完成时间
				visit.setVisitStatus(1); //   拜访状态 1--未拜访      2--已完成
				simpleDaoSupport.update(visit);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关拜访信息！");
				return json;
			}
		}else{
			BeanUtils.copyProperties(model, visit);
			visit.setCreateUser(person);//创建人
			if(!TeeUtility.isNullorEmpty(model.getManagePersonId())){//负责人
				TeePerson managePerson = (TeePerson)personDao.get(model.getManagePersonId());
				visit.setManagePerson(managePerson);
			}	
			if(!TeeUtility.isNullorEmpty(model.getVisitTimeDesc())){//计划日期
				try {
					cl.setTime(sdf.parse(model.getVisitTimeDesc()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			visit.setVisitTime(cl);
			if(!TeeUtility.isNullorEmpty(model.getCustomerId())){//客户
				TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
				visit.setCustomer(customer);
			}
			if(!TeeUtility.isNullorEmpty(model.getCustomerId())&&!TeeUtility.isNullorEmpty(model.getVisitTimeDesc())){//拜访名称
				TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
				visit.setVisitName(customer.getCustomerName()+"("+model.getVisitTimeDesc()+")");
			}
			visit.setCreateTime(ncl);// 创建时间
			visit.setLastEditTime(ncl);//订单的最后一次修改日期
			visit.setVisitEndTime(null);  //  拜访完成时间
			visit.setVisitStatus(1); //   拜访状态 1--未拜访      2--已完成
			simpleDaoSupport.save(visit);
		}
		
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * 拜访记录列表
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson person =(TeePerson)requestDatas.get("LOGIN_USER");
		String cusId = (String) requestDatas.get("cusId"); //某客户下的拜访记录传来的客户id
		String customerName = (String) requestDatas.get("customerName");//客户名称
		String customerId = (String) requestDatas.get("customerId"); //客户id
		String visitTopic = (String) requestDatas.get("visitTopic");//拜访主题
		String visitTimeDesc =(String) requestDatas.get("visitTimeDesc"); //计划日期
		String managePersonId =(String) requestDatas.get("managePersonId"); 
		String managePersonName =(String) requestDatas.get("managePersonName"); //负责人
		String visitStatusDesc =(String) requestDatas.get("visitStatusDesc");  //拜访状态
		String visitName =(String) requestDatas.get("visitName"); //拜访名称
		String type = (String) requestDatas.get("type");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List param = new ArrayList();
		String hql = " from TeeCrmVisit visit where 1=1";
		if(!TeeUtility.isNullorEmpty(customerId)&& !"0".equals(customerId)){
			TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, Integer.parseInt(customerId));
			if(!TeeUtility.isNullorEmpty(customer)){//客户
				hql+=" and visit.customer= ?";
				param.add(customer);
			}
		}
		if(!TeeUtility.isNullorEmpty(visitTopic)&& !"0".equals(visitTopic)){//拜访主题
			hql += " and visit.visitTopic =?";
			param.add(visitTopic);
		}
		if(!TeeUtility.isNullorEmpty(visitName)){//拜访名称
			hql += " and visit.visitName like ?";
			param.add("%"+visitName+"%");
		}
		if(!TeeUtility.isNullorEmpty(visitTimeDesc)){//计划日期
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sf.parse(visitTimeDesc));
				hql+=" and visit.visitTime = ?";
				param.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(visitStatusDesc)){//拜访状态
			if(!"0".equals(visitStatusDesc)){
				hql+=" and visit.visitStatus = ?";
				param.add(Integer.parseInt(visitStatusDesc));
			}else{
				hql +=" ";
			}
		}
		if(!TeeUtility.isNullorEmpty(managePersonId)&& !"0".equals(managePersonId)){//负责人
			TeePerson managePerson = (TeePerson) simpleDaoSupport.get(TeePerson.class, Integer.parseInt(managePersonId));
			if(!TeeUtility.isNullorEmpty(managePerson)){
				hql+=" and visit.managePerson= ?";
				param.add(managePerson);
			}
		}
		if(!TeeUtility.isNullorEmpty(type)){
			if("2".equals(type)){//type =2我负责的
				hql +=" and visit.managePerson.uuid = "+person.getUuid() ;
			}else if("1".equals(type)){//type=1 代表全部
				hql +="";
			}else if("3".equals(type)){//type=3代表我下属负责的
			    List<TeePerson> underPersonList = personService.getUnderlines(person.getUuid());
			    String underPersonIds = "";
			    if(!TeeUtility.isNullorEmpty(underPersonList)){
			    	for (TeePerson teePerson : underPersonList) {
			    		underPersonIds+=teePerson.getUuid()+",";
					}
			    	if(!TeeUtility.isNullorEmpty(underPersonIds)){
			    		if(underPersonIds.endsWith(",")){
			    			underPersonIds=underPersonIds.substring(0, underPersonIds.length()-1);
			    		}
			    		hql+=" and visit.managePerson.uuid in ("+ underPersonIds+")";
			    		
			    	}else{
				    	hql +=" and visit.managePerson.uuid is null";
				    }
			    }
			}
		}else{//  没有type代表所属客户下的订单
			hql ="from TeeCrmVisit visit,TeeCrmCustomer customer where visit.customer=customer and visit.customer.sid=? and ( visit.managePerson.uuid="+person.getUuid()+" or visit.createUser.uuid="+person.getUuid()+") order by visit.lastEditTime desc";
			param.add(Integer.parseInt(cusId));
		}
		List<TeeCrmVisit> visitList = simpleDaoSupport.pageFindByList("select visit "+hql+" order by visit.lastEditTime desc", (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = simpleDaoSupport.countByList("select count(*) "+hql, param);
		List<TeeCrmVisitModel> models = new ArrayList<TeeCrmVisitModel>();
		for (TeeCrmVisit visit : visitList) {
			TeeCrmVisitModel m = new TeeCrmVisitModel();
			m = parseModel(visit);
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param contactUser
	 * @return
	 */
	public TeeCrmVisitModel parseModel(TeeCrmVisit visit){
		TeeCrmVisitModel model = new TeeCrmVisitModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(visit == null){
			return null;
		}
		BeanUtils.copyProperties(visit, model);
		if(visit.getVisitTime()!=null){
			model.setVisitTimeDesc(sf.format(visit.getVisitTime().getTime()));//计划日期
		}
		if(visit.getVisitEndTime()!=null){
			model.setVisitEndTimeDesc(sf.format(visit.getVisitEndTime().getTime())); //拜访完成日期
		} else{
			model.setVisitEndTimeDesc("--");
		}
		if(visit.getVisitStatus()==1){//拜访状态
			model.setVisitStatusDesc("未拜访");
		}else if(visit.getVisitStatus()==2){
			model.setVisitStatusDesc("已完成");
		}
		if(visit.getVisitTopic()!=null){
			model.setVisitTopic(visit.getVisitTopic());
			String visitTopicDesc=TeeCrmCodeManager.getChildSysCodeNameCodeNo("VISIT_TOPIC", model.getVisitTopic());//拜访主题
			model.setVisitTopicDesc(visitTopicDesc);
		}
		if(visit.getCreateTime()!=null){
			model.setCreateTimeDesc(sdf.format(visit.getCreateTime().getTime())); //创建时间
		}
		if(visit.getCustomer()!=null){//客户
			model.setCustomerId(visit.getCustomer().getSid());
			model.setCustomerName(visit.getCustomer().getCustomerName());
		}
		if(visit.getCreateUser()!=null){//创建人
			model.setCreateUserId(visit.getCreateUser().getUuid());
			model.setCreateUserName(visit.getCreateUser().getUserName());
		}
		if(visit.getManagePerson()!=null){//负责人
			model.setManagePersonId(visit.getManagePerson().getUuid());
			model.setManagePersonName(visit.getManagePerson().getUserName());
		}
		if(visit.getLastEditTime()!=null){
			model.setLastEditTimeDesc(sdf.format(visit.getLastEditTime().getTime()));  //最后修改日期
		}
		
		return model;
	}

	/**
	 * 拜访记录详情
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request,TeeCrmVisitModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeCrmVisit visit = (TeeCrmVisit) simpleDaoSupport.get(TeeCrmVisit.class, model.getSid());
			if(visit !=null){
				model = parseModel(visit);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关拜访记录！");
		return json;
	}

	/**
	 * 完成拜访
	 * @param request
	 * @return
	 */
	public TeeJson finishVisit(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String uuid=TeeStringUtil.getString(request.getParameter("sid"));
		if(!TeeUtility.isNullorEmpty(uuid)){
			int status=TeeStringUtil.getInteger(request.getParameter("visitStatus"), 0);
			Calendar cl = Calendar.getInstance();
			simpleDaoSupport.executeUpdate("update TeeCrmVisit set visitStatus = ?, lastEditTime = ?,visitEndTime =? where sid =? ", new Object[]{status,cl,cl,Integer.parseInt(uuid)});
			json.setRtState(true);
			json.setRtMsg("操作成功！");
		}
		return json;
	}

	/**
	 * 删除拜访记录
	 * @param request
	 * @return
	 */
	public TeeJson delById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sid = TeeStringUtil.getString(request.getParameter("sid"),"0");
		if(!TeeUtility.isNullorEmpty(sid)){
			TeeCrmVisit visit = new TeeCrmVisit();
			visit.setSid(Integer.parseInt(sid));
			simpleDaoSupport.deleteByObj(visit);
			json.setRtState(true);
			json.setRtMsg("删除成功！");
		}
		return json;
	
	}
	

}
