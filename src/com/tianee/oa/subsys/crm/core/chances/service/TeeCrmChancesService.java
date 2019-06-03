package com.tianee.oa.subsys.crm.core.chances.service;

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

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.chances.bean.TeeCrmChances;
import com.tianee.oa.subsys.crm.core.chances.model.TeeCrmChancesModel;
import com.tianee.oa.subsys.crm.core.contacts.bean.TeeCrmContacts;
import com.tianee.oa.subsys.crm.core.contacts.model.TeeCrmContactsModel;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.customer.dao.TeeCrmCustomerDao;
import com.tianee.oa.subsys.crm.core.order.bean.TeeCrmOrder;
import com.tianee.oa.subsys.crm.core.order.service.TeeCrmOrderService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmChancesService extends TeeBaseService {
	
	@Autowired
	private TeeCrmCustomerDao customerDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeCrmOrderService orderService;

	/**
	 * 添加/编辑商机
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmChancesModel model) {
		TeeJson json = new TeeJson();
		TeeCrmChances chance = new TeeCrmChances();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		TeePerson person = (TeePerson)personDao.get(model.getManagePersonId());
		TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(model.getSid()>0){//编辑商机
			chance = (TeeCrmChances) simpleDaoSupport.get(TeeCrmChances.class, model.getSid());
		    if(!TeeUtility.isNullorEmpty(chance)){
		    	Calendar crTime = chance.getCreateTime();
		    	BeanUtils.copyProperties(model, chance);
		    	Calendar cl = Calendar.getInstance();
		    	Calendar cl2 = Calendar.getInstance();
		    	chance.setCustomer(customer);  //客户
		    	chance.setChanceManagePerson(person); //负责人
		    	chance.setCrUser(loginPerson);  //创建人
		    	chance.setCreateTime(crTime);  //创建时间
//		    	chance.setProducts(null);
		    	chance.setChanceStatus(1); //商机状态   1--未处理 2--赢单  3--输单   4--无效
		    	if(!TeeUtility.isNullorEmpty(model.getForcastTimeDesc())){ 
					try {
						cl2.setTime(sdf.parse(model.getForcastTimeDesc()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				chance.setForcastTime(cl2);  //预计成交日期
				chance.setLastEditTime(cl);  //最后一次修改时间
				simpleDaoSupport.update(chance);
		    	
		    }else{
		    	json.setRtState(false);
				json.setRtMsg("未查到到相关联系人信息！");
				return json;
		    }
		
		}else{//添加商机
			BeanUtils.copyProperties(model, chance);
		   	Calendar cl = Calendar.getInstance();
	    	Calendar cl2 = Calendar.getInstance();
	    	chance.setCustomer(customer);  //客户
	    	chance.setChanceManagePerson(person); //负责人
	    	chance.setCrUser(loginPerson);  //创建人
	    	chance.setCreateTime(cl);  //创建时间
//	    	chance.setProducts(null);
	    	chance.setChanceStatus(1); //商机状态    1--未处理   2--赢单 3 --输单 4--无效
	    	if(!TeeUtility.isNullorEmpty(model.getForcastTimeDesc())){ 
				try {
					cl2.setTime(sdf.parse(model.getForcastTimeDesc()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			chance.setForcastTime(cl2);  //预计成交日期
			chance.setLastEditTime(cl);  //最后一次修改时间
			simpleDaoSupport.save(chance);  
		}
		
		json.setRtMsg("保存成功！");
		json.setRtState(true);
		return json;
	}

	/**
	 * 商机列表
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson person =(TeePerson)requestDatas.get("LOGIN_USER");
		String cusId = (String) requestDatas.get("cusId");
		String customerName = (String) requestDatas.get("customerName");//客户名称
		String customerId = (String) requestDatas.get("customerId"); //客户id
		String chanceName = (String) requestDatas.get("chanceName");//商机名称
		String forcastTimeDesc =(String) requestDatas.get("forcastTimeDesc"); //预计成交日期
		String minforecost = (String) requestDatas.get("minforecost");//成交金额   
		String maxforecost = (String) requestDatas.get("maxforecost");
		String chanceStatusDesc =(String) requestDatas.get("chanceStatusDesc");  //状态
		String remark  =(String) requestDatas.get("remark");  //备注
		String type = (String) requestDatas.get("type");
		String chanceCustomerId = (String) requestDatas.get("chanceCustomerId");//  某订单对应的客户
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List param = new ArrayList();
		String hql = " from TeeCrmChances chances where 1=1";
		if(!TeeUtility.isNullorEmpty(customerId)&& !"0".equals(customerId)){
			TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, Integer.parseInt(customerId));
			if(!TeeUtility.isNullorEmpty(customer)){
				hql+=" and chances.customer= ?";
				param.add(customer);
			}
		}
		if(!TeeUtility.isNullorEmpty(chanceName)){
			hql += " and chances.chanceName like ?";
			param.add("%"+chanceName+"%");
		}
		if(!TeeUtility.isNullorEmpty(forcastTimeDesc)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sf.parse(forcastTimeDesc));
				hql+=" and chances.forcastTime = ?";
				param.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(minforecost)){
			hql += " and chances.forcastCost >=?";
			param.add(Double.parseDouble(minforecost));
		}
		if(!TeeUtility.isNullorEmpty(maxforecost)){
			hql += " and chances.forcastCost <= ?";
			param.add(Double.parseDouble(maxforecost));
		}
		if(!TeeUtility.isNullorEmpty(chanceStatusDesc)){
			if(!"0".equals(chanceStatusDesc)){
				hql+=" and chances.chanceStatus = ?";
				param.add(Integer.parseInt(chanceStatusDesc));
			}else{
				hql +=" ";
			}
		}
		if(!TeeUtility.isNullorEmpty(remark)){
			hql+=" and chances.remark like ?";
			param.add("%"+remark+"%");
		}
		
		if(!TeeUtility.isNullorEmpty(type)){
			if("2".equals(type)){//type =2我负责的
				hql +=" and chances.chanceManagePerson.uuid = "+person.getUuid();
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
			    		hql+=" and chances.chanceManagePerson.uuid in ("+ underPersonIds+")";
			    		
			    	}else{
				    	hql +=" and chances.chanceManagePerson.uuid is null";
				    }
			    }
			}
		}else{//  没有type代表所属客户下的商机
			hql =" from TeeCrmChances chances,TeeCrmCustomer customer where chances.customer=customer and chances.customer.sid=? and ( chances.chanceManagePerson.uuid="+person.getUuid()+" or chances.crUser.uuid="+person.getUuid()+") ";
			param.add(Integer.parseInt(cusId));
		}
		List<TeeCrmChances> chanceList = simpleDaoSupport.pageFindByList("select chances "+hql+" order by chances.lastEditTime desc", (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = simpleDaoSupport.countByList("select count(*) "+hql, param);
		List<TeeCrmChancesModel> models = new ArrayList<TeeCrmChancesModel>();
		for (TeeCrmChances chances : chanceList) {
			TeeCrmChancesModel m = new TeeCrmChancesModel();
			m = parseModel(chances);
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
	public TeeCrmChancesModel parseModel(TeeCrmChances chance){
		TeeCrmChancesModel model = new TeeCrmChancesModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(chance == null){
			return null;
		}
		BeanUtils.copyProperties(chance, model);
		if(chance.getForcastCost()!=null){
			model.setForcastTimeDesc(sf.format(chance.getForcastTime().getTime())); //预计成交日期
		} 
		if(chance.getChanceStatus()==1){
			model.setChanceStatusDesc("未处理");
		}else if(chance.getChanceStatus()==2){
			model.setChanceStatusDesc("赢单");
		}else if(chance.getChanceStatus()==3){
			model.setChanceStatusDesc("输单");
		}else if(chance.getChanceStatus()==4){
			model.setChanceStatusDesc("无效");
		}
		if(chance.getCreateTime()!=null){
			model.setCreateTimeDesc(sdf.format(chance.getCreateTime().getTime())); //创建时间
		}
		if(chance.getCustomer()!=null){//客户
			model.setCustomerId(chance.getCustomer().getSid());
			model.setCustomerName(chance.getCustomer().getCustomerName());
		}
		if(chance.getCrUser()!=null){//创建人
			model.setAddPersonId(chance.getCrUser().getUuid());
			model.setAddPersonName(chance.getCrUser().getUserName());
		}
		if(chance.getChanceManagePerson()!=null){//负责人
			model.setManagePersonId(chance.getChanceManagePerson().getUuid());
			model.setManagePersonName(chance.getChanceManagePerson().getUserName());
		}
		if(chance.getLastEditTime()!=null){
			model.setLastEditTimeDesc(sdf.format(chance.getLastEditTime().getTime()));  //最后修改日期
		}
		
		return model;
	}

	/**
	 * 获取商机详情
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request,TeeCrmChancesModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeCrmChances chances = (TeeCrmChances) simpleDaoSupport.get(TeeCrmChances.class, model.getSid());
			if(chances !=null){
				model = parseModel(chances);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关商机！");
		return json;
	}

	/**
	 * 输单
	 * @param request
	 * @return
	 */
	public TeeJson lostOrder(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String uuid=TeeStringUtil.getString(request.getParameter("sid"));
		if(!TeeUtility.isNullorEmpty(uuid)){
			int status=TeeStringUtil.getInteger(request.getParameter("chanceStatus"), 0);
			Calendar cl = Calendar.getInstance();
			simpleDaoSupport.executeUpdate("update TeeCrmChances set chanceStatus = ?, lastEditTime = ? where sid =? ", new Object[]{status,cl,Integer.parseInt(uuid)});
			json.setRtState(true);
			json.setRtMsg("操作成功！");
		}
		return json;
	}

	/**
	 * 赢单
	 * @param request
	 * @return
	 */
	public TeeJson winOrder(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String uuid=TeeStringUtil.getString(request.getParameter("sid"));
		if(!TeeUtility.isNullorEmpty(uuid)){
			int status=TeeStringUtil.getInteger(request.getParameter("chanceStatus"), 0);
			Calendar cl = Calendar.getInstance();
			simpleDaoSupport.executeUpdate("update TeeCrmChances set chanceStatus = ?, lastEditTime = ? where sid =? ", new Object[]{status,cl,Integer.parseInt(uuid)});
			json.setRtState(true);
			json.setRtMsg("操作成功！");
		}
		return json;
	}

	/**
	 * 无效
	 * @param request
	 * @return
	 */
	public TeeJson invalid(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String uuid=TeeStringUtil.getString(request.getParameter("sid"));
		if(!TeeUtility.isNullorEmpty(uuid)){
			int status=TeeStringUtil.getInteger(request.getParameter("chanceStatus"), 0);
			Calendar cl = Calendar.getInstance();
			simpleDaoSupport.executeUpdate("update TeeCrmChances set chanceStatus = ?, lastEditTime = ? where sid =? ", new Object[]{status,cl,Integer.parseInt(uuid)});
			json.setRtState(true);
			json.setRtMsg("操作成功！");
		}
		return json;
	}

	/**
	 * 单个 删除商机
	 * @param request
	 * @return
	 */
	public TeeJson delById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sid = TeeStringUtil.getString(request.getParameter("sid"),"0");
		if(!TeeUtility.isNullorEmpty(sid)){
			TeeCrmChances chance = new TeeCrmChances();
			chance.setSid(Integer.parseInt(sid));
			
			//查询出此商机下的所有订单
			List<TeeCrmOrder> listOrders = orderService.selectListByChanceId(chance.getSid());
			String ids = "";
			if(!TeeUtility.isNullorEmpty(listOrders)){
				for (TeeCrmOrder teeCrmOrder : listOrders) {
					ids = teeCrmOrder.getSid()+",";
				}
			}
			if(!TeeUtility.isNullorEmpty(ids)){
				if(ids.endsWith(",")){
					ids= ids.substring(0, ids.length() -1 );
				}
				
				//删除订单下的关联的退货单
				//删除该订单关联的退货单
				simpleDaoSupport.executeNativeUpdate(" delete CRO.*,ROP.* from CRM_RETURN_ORDER CRO INNER JOIN RETURN_ORDER_PRODUCTS ROP ON CRO.SID = ROP.RETURN_ORDER_ID where CRO.ORDER_ID in ("+ids+")", null);
				//simpleDaoSupport.executeUpdate("delete from TeeReturnOrderProducts item where item.returnOrder.order.sid in (" + ids + ")", null);
				//simpleDaoSupport.executeUpdate("delete from TeeCrmReturnOrder where order.sid in (" + ids + ")", null);
				//删除订单下的关联合同
				simpleDaoSupport.executeUpdate("delete from TeeCrmContracts where order.sid in (" + ids + ")", null);
				//删除订单下的关联回款
				simpleDaoSupport.executeUpdate("delete from TeeCrmPayback where order.sid in (" + ids + ")", null);
				//删除订单下的关联退款
				simpleDaoSupport.executeUpdate("delete from TeeCrmDrawback where order.sid in (" + ids + ")", null);
				//删除订单下的关联开票信息
				simpleDaoSupport.executeUpdate("delete from TeeCrmInvoice where order.sid in (" + ids + ")", null);
				//删除商机关联订单
				simpleDaoSupport.executeUpdate("delete from TeeOrderProducts item where item.order.sid in (" + ids + ")", null);
				simpleDaoSupport.executeUpdate("delete from TeeCrmOrder where sid in (" + ids + ")", null);
			}
		
			//删除商机
			simpleDaoSupport.deleteByObj(chance);
			json.setRtState(true);
			json.setRtMsg("删除成功！");
		}
		return json;
	
	}

	
	/**
	 * 根据客户id删除商机
	 * @param ids
	 */
	public void delChanceByCustomerId(int sid){
		List<TeeCrmChances> list = new ArrayList<TeeCrmChances>();
	    if(!TeeUtility.isNullorEmpty(sid)&&0!=sid){
			String hql = " from TeeCrmChances chances where chances.customer.sid="+sid;
			list= simpleDaoSupport.executeQuery(hql,null);
	    }
		if(!TeeUtility.isNullorEmpty(list)){
			for (TeeCrmChances chance : list) {
				delOrderByChanceId(chance.getSid());
			}
	
			String hql = "delete from TeeCrmChances where customer.sid ="+sid;
			simpleDaoSupport.deleteOrUpdateByQuery(hql, null);
		}
		
	}
	
	/**
	 * 删除订单所属商机及相关
	 * @param chanceId
	 */
	public void delOrderByChanceId(int chanceId){
		//查询出此商机下的所有订单
		List<TeeCrmOrder> listOrders = orderService.selectListByChanceId(chanceId);
		String ids = "";
		if(!TeeUtility.isNullorEmpty(listOrders)){
			for (TeeCrmOrder teeCrmOrder : listOrders) {
				ids = teeCrmOrder.getSid()+",";
			}
		}
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			
			//删除订单下的关联的退货单
			simpleDaoSupport.executeNativeUpdate(" delete CRO.*,ROP.* from CRM_RETURN_ORDER CRO INNER JOIN RETURN_ORDER_PRODUCTS ROP ON CRO.SID = ROP.RETURN_ORDER_ID where CRO.ORDER_ID in ("+ids+")", null);
			//删除订单下的关联合同
			simpleDaoSupport.executeUpdate("delete from TeeCrmContracts where order.sid in (" + ids + ")", null);
			//删除订单下的关联回款
			simpleDaoSupport.executeUpdate("delete from TeeCrmPayback where order.sid in (" + ids + ")", null);
			//删除订单下的关联退款
			simpleDaoSupport.executeUpdate("delete from TeeCrmDrawback where order.sid in (" + ids + ")", null);
			//删除订单下的关联开票信息
			simpleDaoSupport.executeUpdate("delete from TeeCrmInvoice where order.sid in (" + ids + ")", null);
			//删除商机关联订单
			simpleDaoSupport.executeUpdate("delete from TeeOrderProducts item where item.order.sid in (" + ids + ")", null);
			simpleDaoSupport.executeUpdate("delete from TeeCrmOrder where sid in (" + ids + ")", null);
		}
		
	}

	/**
	 * 查询某客户下的商机数据
	 * @param request
	 * @return
	 */
	public List<TeeCrmChancesModel> selectChances(HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String cusId = TeeStringUtil.getString(request.getParameter("cusId"), "0");
		String chanceName = TeeStringUtil.getString(request.getParameter("chanceName"), "");//获取商机名称
		String hql1 = "";
		
		if(!TeeUtility.isNullorEmpty(chanceName)){//商机名称
				hql1 = " and chances.chanceName like  %"+chanceName+"%" ;
		}else{
			hql1 += " ";
		}
		
		String hql ="select chances from TeeCrmChances chances,TeeCrmCustomer customer where chances.customer=customer and chances.customer.sid= ?  and ( chances.chanceManagePerson.uuid="+person.getUuid()+" or chances.crUser.uuid="+person.getUuid()+") "+hql1+" ";
		List<TeeCrmChances> list = simpleDaoSupport.executeQuery(hql, new Object[]{Integer.parseInt(cusId)});
	/*	if(!TeeUtility.isNullorEmpty(chanceName)){//商机名称
			list = simpleDaoSupport.executeQuery(hql, new Object[]{Integer.parseInt(cusId)});
		}else{
			list = simpleDaoSupport.executeQuery(hql, new Object[]{Integer.parseInt(cusId)});
		}*/
		List<TeeCrmChancesModel> models = new ArrayList<TeeCrmChancesModel>();
		for(TeeCrmChances chances:list){
			TeeCrmChancesModel m = new TeeCrmChancesModel();
			m=parseModel(chances);	
			models.add(m);
		}

		return models;
	
	}
	
}
