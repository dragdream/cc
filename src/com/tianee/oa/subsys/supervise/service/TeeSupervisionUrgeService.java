package com.tianee.oa.subsys.supervise.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.supervise.bean.TeeSupervision;
import com.tianee.oa.subsys.supervise.bean.TeeSupervisionUrge;
import com.tianee.oa.subsys.supervise.model.TeeSupervisionUrgeModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeSupervisionUrgeService extends TeeBaseService {

	@Autowired
	private TeeSmsManager smsManager;
	
	@Autowired
	private TeeSupervisionService supService;
	/**
	 * 新建催办
	 * @param request
	 * @return
	 */
	public TeeJson add(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		//获取页面上传来的数据
		int supId=TeeStringUtil.getInteger(request.getParameter("supId"), 0);
		String content=TeeStringUtil.getString(request.getParameter("content"));
		int isIncludeChildren=TeeStringUtil.getInteger(request.getParameter("isIncludeChildren"), 0);
		TeeSupervision sup=(TeeSupervision) simpleDaoSupport.get(TeeSupervision.class,supId);
		
		TeeSupervisionUrge urge=new TeeSupervisionUrge();
		urge.setContent(content);
		urge.setCreater(loginUser);
		urge.setCreateTime(new Date());
		urge.setIsIncludeChildren(isIncludeChildren);
		urge.setSup(sup);
		
		simpleDaoSupport.save(urge);
		
		if(isIncludeChildren==0){//不包含子级任务
			// 发送消息   给主办人和协办人
			String Ids="";
			if(sup!=null){
				Set<TeePerson> users=sup.getAssists();
				if(sup.getManager()!=null){
					users.add(sup.getManager());
				}
				for (TeePerson teePerson : users) {
					Ids+=teePerson.getUuid()+",";
				}
				if(Ids.endsWith(",")){
					Ids=Ids.substring(0, Ids.length()-1);
				}
				
				
				Map requestData1 = new HashMap();
				requestData1.put("content", "“"+loginUser.getUserName()+"”发起了任务催办《"+sup.getSupName()+"》，催办内容："+content);
				requestData1.put("userListIds", Ids);
				requestData1.put("moduleNo", "061");
				requestData1.put("remindUrl","/system/subsys/supervise/handle/index.jsp?sid="+sup.getSid());
				smsManager.sendSms(requestData1, loginUser);
			}
		}else{//包含子级任务
			List<TeeSupervision> result=new ArrayList<TeeSupervision>();
			supService.getAllChildrenAndSelfList(supId, result);
			for (TeeSupervision s : result) {
				String Ids="";
				if(s!=null){
					Set<TeePerson> users=s.getAssists();
					if(s.getManager()!=null){
						users.add(s.getManager());
					}
					for (TeePerson teePerson : users) {
						Ids+=teePerson.getUuid()+",";
					}
					if(Ids.endsWith(",")){
						Ids=Ids.substring(0, Ids.length()-1);
					}
					
					
					Map requestData1 = new HashMap();
					requestData1.put("content", "“"+loginUser.getUserName()+"”发起了任务催办《"+s.getSupName()+"》，催办内容："+content);
					requestData1.put("userListIds", Ids);
					requestData1.put("moduleNo", "061");
					requestData1.put("remindUrl","/system/subsys/supervise/handle/index.jsp?sid="+s.getSid());
					smsManager.sendSms(requestData1, loginUser);	
					
				}
			}
			
			
		}
		

		json.setRtState(true);
		json.setRtMsg("催办成功 ！");
		return json;
	}

	
	
	/**
	 * 根据督办任务主键 获取催办列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getUrgeListBySupId(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
		
		
		String hql = " from TeeSupervisionUrge where sup.sid=? ";
		List param = new ArrayList();
		param.add(supId);
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeSupervisionUrge> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeSupervisionUrgeModel> modelList = new ArrayList<TeeSupervisionUrgeModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeSupervisionUrgeModel modeltemp = parseToModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}



	
	/**
	 * 将实体类转换成model
	 * @param teeSupervisionUrge
	 * @return
	 */
	private TeeSupervisionUrgeModel parseToModel(
			TeeSupervisionUrge teeSupervisionUrge) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeSupervisionUrgeModel model=new TeeSupervisionUrgeModel();
		BeanUtils.copyProperties(teeSupervisionUrge, model);
		if(teeSupervisionUrge.getCreater()!=null){
			model.setCreaterId(teeSupervisionUrge.getCreater().getUuid());
			model.setCreaterName(teeSupervisionUrge.getCreater().getUserName());
		}
		
		if(teeSupervisionUrge.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(teeSupervisionUrge.getCreateTime()));
		}
		if(teeSupervisionUrge.getSup()!=null){
			model.setSupId(teeSupervisionUrge.getSup().getSid());
			model.setSupName(teeSupervisionUrge.getSup().getSupName());
		}
		return model;
	}



	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			TeeSupervisionUrge urge=(TeeSupervisionUrge) simpleDaoSupport.get(TeeSupervisionUrge.class,sid);
			TeeSupervisionUrgeModel model=parseToModel(urge);
			json.setRtData(model);
            json.setRtState(true);		
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}

	
	
}
