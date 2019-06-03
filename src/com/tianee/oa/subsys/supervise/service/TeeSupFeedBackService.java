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
import com.tianee.oa.subsys.supervise.bean.TeeSupFeedBack;
import com.tianee.oa.subsys.supervise.bean.TeeSupervision;
import com.tianee.oa.subsys.supervise.model.TeeSupFeedBackModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeSupFeedBackService extends TeeBaseService{

	@Autowired
	private TeeSmsManager smsManager;
	/**
	 * 发表反馈
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    TeeJson json=new TeeJson();
	    //获取页面上传来的参数
	    int supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
	    TeeSupervision sup=(TeeSupervision) simpleDaoSupport.get(TeeSupervision.class,supId);
	    String title=TeeStringUtil.getString(request.getParameter("title"));
	    String content=TeeStringUtil.getString(request.getParameter("content"));
	    int level=TeeStringUtil.getInteger(request.getParameter("level"),0);
	    
	    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	    if(sid>0){//编辑
	    	TeeSupFeedBack fb=(TeeSupFeedBack) simpleDaoSupport.get(TeeSupFeedBack.class,sid);
	    	fb.setTitle(title);
	    	fb.setContent(content);
	    	fb.setLevel(level);
	    	simpleDaoSupport.update(fb);
	    	json.setRtState(true);
	    	json.setRtMsg("编辑成功！");
	    	
	    }else{//新增
	    	TeeSupFeedBack fb=new TeeSupFeedBack();
	 	    fb.setContent(content);
	 	    fb.setCreater(loginUser);
	 	    fb.setCreateTime(new Date());
	 	    fb.setLevel(level);
	 	    fb.setSup(sup);
	 	    fb.setTitle(title);
	 	    
	 	    simpleDaoSupport.save(fb);
	 	    
	 	    if(sup!=null){
	 	    	//获取除了自己其他人员
	 	    	Set<TeePerson> users=sup.getAssists();
	 	    	if(sup.getLeader()!=null){
	 	    		users.add(sup.getLeader());
	 	    	}
	 	    	if(sup.getManager()!=null){
	 	    		users.add(sup.getManager());
	 	    	}
	 	    	String Ids="";
	 	    	for (TeePerson teePerson : users) {
					if(teePerson.getUuid()!=loginUser.getUuid()){
						Ids+=teePerson.getUuid()+",";
					}
				}
	 	    	
	 	    	if(Ids.endsWith(",")){
	 	    		Ids=Ids.substring(0,Ids.length()-1);
	 	    	}
	 	    	
	 	       // 发送消息
	 	 	   Map requestData1 = new HashMap();
	 	 	   requestData1.put("content", "“"+loginUser.getUserName()+"”发表了督办任务反馈，反馈标题："+title+"，反馈内容："+content);
	 	 	   requestData1.put("userListIds", Ids);
	 	 	   requestData1.put("moduleNo", "061");
	 	 	   requestData1.put("remindUrl","/system/subsys/supervise/sms/feedBackRecords.jsp?supId="+sup.getSid());
	 	 	   smsManager.sendSms(requestData1, loginUser);
	 	    }
	 	
	 	    json.setRtState(true);
	 	    json.setRtMsg("发表成功！");
	    }
	    
		return json;
	}

	
	/**
	 * 根据任务主键  获取反馈列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getFeedBackListBySupId(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
		String hql = " from TeeSupFeedBack  where sup.sid=?";
		List param = new ArrayList();
		param.add(supId);
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeSupFeedBack> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeSupFeedBackModel> modelList = new ArrayList<TeeSupFeedBackModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeSupFeedBackModel modeltemp = parseToModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}


	/**
	 * 将实体类  转成model
	 * @param teeSupFeedBack
	 * @return
	 */
	private TeeSupFeedBackModel parseToModel(TeeSupFeedBack teeSupFeedBack) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeSupFeedBackModel model=new TeeSupFeedBackModel();
		BeanUtils.copyProperties(teeSupFeedBack, model);
		if(teeSupFeedBack.getCreater()!=null){
			model.setCreaterId(teeSupFeedBack.getCreater().getUuid());
			model.setCreaterName(teeSupFeedBack.getCreater().getUserName());
		}
		
		if(teeSupFeedBack.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(teeSupFeedBack.getCreateTime()));
		}
		
		if(teeSupFeedBack.getSup()!=null){
			model.setSupId(teeSupFeedBack.getSup().getSid());
			model.setSupName(teeSupFeedBack.getSup().getSupName());
		}
		return model;
	}


	
	/**
	 * 根据主键  获取反馈详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeSupFeedBack fb=(TeeSupFeedBack) simpleDaoSupport.get(TeeSupFeedBack.class,sid);
		TeeSupFeedBackModel model=parseToModel(fb);
		json.setRtState(true);
		json.setRtData(model);
		return json;
	}


	/**
	 * 根据主键删除
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			TeeSupFeedBack fb=(TeeSupFeedBack) simpleDaoSupport.get(TeeSupFeedBack.class,sid);
			simpleDaoSupport.deleteByObj(fb);
			//删除关联的回复
			simpleDaoSupport.executeUpdate("delete from TeeSupFeedBackReply reply where reply.fb.sid=? ", new Object[]{sid});
			json.setRtState(true);
			json.setRtMsg("删除成功");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据不存在！");
		}
		return json;
	}


	

}
