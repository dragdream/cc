package com.tianee.oa.core.workflow.flowrun.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import sun.misc.BASE64Encoder;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowProcessDao;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowTypeDao;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFeedBack;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunCtrlFeedback;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.dao.TeeWorkFlowFeedBackDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFeedBackModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeWorkFlowFeedBackService extends TeeBaseService implements TeeWorkFlowFeedBackServiceInterface{

	@Autowired
	private TeeWorkFlowFeedBackDao feedBackDao;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeeFlowTypeDao flowTypeDao;
	
	@Autowired
	private TeeSimpleDaoSupport simpleDao;
	
	@Autowired
	private TeeFlowProcessDao processDao;
	
	
	public TeeWorkFlowFeedBackDao getFeedBackDao() {
		return feedBackDao;
	}
	
	
	@Autowired
	private TeeBaseUpload upload;

	public void setFeedBackDao(TeeWorkFlowFeedBackDao feedBackDao) {
		this.feedBackDao = feedBackDao;
	}
	/**
	 * 增加会签意见 并且处理一下附件 
	 * @param fb
	 * @throws IOException 
	 */
	public void addFeedBack(TeeFeedBack fb) throws IOException{
		
		feedBackDao.save(fb);
	}
	/**
	 * 删除会签附件
	 * @author zhp
	 * @createTime 2013-10-6
	 * @editTime 下午10:21:58
	 * @desc
	 */
	public void deleteFeedBackAttach(int fid,int aid) throws IOException{
		TeeFeedBack fb = feedBackDao.get(fid);
		if(fb == null){
			return ;
		}
		TeeAttachment attach = new TeeAttachment();
		attach.setSid(aid);
		attachmentDao.deleteByObj(attach);
	}
	/**
	 * 删除会签意见
	 * @author zhp
	 * @createTime 2013-10-6
	 * @editTime 下午08:09:47
	 * @desc
	 */
	public void deleteFeedBack(int id) throws IOException{
		TeeFeedBack fb = feedBackDao.get(id);
		if(fb == null){
			return ;
		}
		List<TeeAttachment> list = attachmentDao.getAttaches(TeeAttachmentModelKeys.workFlowFeedBack, String.valueOf(id));
		for(TeeAttachment attach:list){
			attachmentService.deleteAttach(attach);
		}
		//获取该会签意见的回复
		List<TeeFeedBack> replyList=simpleDao.executeQuery(" from TeeFeedBack fb where fb.replayId=? ", new Object[]{id});
		if(replyList!=null&&replyList.size()>0){//有回复数据
			for (TeeFeedBack teeFeedBack : replyList) {
				deleteFeedBack(teeFeedBack.getSid());
			}
		}
		feedBackDao.deleteByObj(fb);
	}
	/**
	 * 获取印章数据
	 * @author zhp
	 * @createTime 2013-11-10
	 * @editTime 上午12:34:58
	 * @desc
	 */
	public String getFeedBackSealDataById (int sid) {
		String sealData = "";
		TeeFeedBack fb = null;
		try {
			fb = feedBackDao.get(sid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(fb != null){
			sealData = fb.getSignData();
		}
		return sealData;
	}
	/**
	 * @author zhp
	 * @createTime 2013-11-27
	 * @editTime 下午08:35:47
	 * @desc
	 */
	public List<TeeFeedBackModel> selectTeeFeedBackByRunId (int runId) {
		return selectTeeFeedBackByRunId(runId,2,null);
	}
	/**
	 * 获取会签意见
	 * @param runId
	 * @return
	 */
	public List<TeeFeedBackModel> selectTeeFeedBackByRunId (int runId,int flowType,TeeFlowProcess flowPrcs) {
		
		List<TeeFeedBackModel>  listModel = new ArrayList<TeeFeedBackModel>();
		try {
		List<TeeFeedBack> list = feedBackDao.selectTeeFeedBackByRunId(runId,flowType,flowPrcs);
		for(int i=0;i<list.size();i++){
			TeeFeedBack back = list.get(i);
			TeeFeedBackModel model = new TeeFeedBackModel();
			BeanUtils.copyProperties(back, model);
			if(back.getUserPerson()!=null){
				//model.setUserName(back.getUserPerson().getUserName());
				model.setUserId(back.getUserPerson().getUuid());
			}
			model.setEditTimeDesc(TeeDateUtil.format(back.getEditTime()));
			model.setAttachList(attachmentService.getAttacheModels(TeeAttachmentModelKeys.workFlowFeedBack, String.valueOf(back.getSid())));
			model.setFlowPrcsId(back.getFlowPrcs()==null?0:back.getFlowPrcs().getSid());
			model.setPrcsName(back.getFlowPrcs()==null?"":back.getFlowPrcs().getPrcsName());
			if(TeeUtility.isNullorEmpty(back.getSignData())){
				model.setHavaSignData(false);
			}else{
				model.setHavaSignData(true);
			}
			listModel.add(model);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listModel;
	}
	
	/**
	 * 获取会签意见，有权限判断
	 * @param runId
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<TeeFeedBackModel> selectPrcsPrivedFeedBackByRunId (int runId,int frpSid,int personId) {
		
		//获取所属流程
		Map map = simpleDao.getMap("select fr.flowType.sid as SID,fr.flowType.type as TYPE from TeeFlowRun fr where fr.runId=?", new Object[]{runId});
		int flowId = TeeStringUtil.getInteger(map.get("SID"), 0);//流程类型ID
		int type = TeeStringUtil.getInteger(map.get("TYPE"), 0);//流程所属类别
		
		List<TeeFeedBackModel> modelList = selectTeeFeedBackByRunId(runId);
		
		if(type==1){//固定流程
			//获取该流程的所有步骤
			List<TeeFlowProcess> processList = processDao.findFlowProcessByFlowType(flowId);
			
			if(frpSid==0){//如果没有步骤信息，则被判断为非经办人查看表单，则除了全部可见之外，过滤掉其他会签可见性
				for(TeeFlowProcess fp:processList){
					if(TeeStringUtil.existInt(new int[]{2,3}, fp.getFeedbackViewType())){
						Iterator<TeeFeedBackModel> it = modelList.iterator();
						while(it!=null && it.hasNext()){
							TeeFeedBackModel fbm = it.next();
							if(fbm.getFlowPrcsId()==fp.getSid()){
								it.remove();
							}
						}
					}
				}
			}else{
				map = simpleDao.getMap("select frp.flowPrcs.sid as SID,frp.flowRun.runId as RUNID from TeeFlowRunPrcs frp where frp.sid=?", new Object[]{frpSid});
				if(map==null){
					throw new TeeOperationException("无权查看此数据");
				}
				int flowPrcsId = TeeStringUtil.getInteger(map.get("SID"), 0);
				int runSid = TeeStringUtil.getInteger(map.get("RUNID"), 0);
				if(runSid!=runId){
					throw new TeeOperationException("无权查看此数据");
				}
				
				for(TeeFlowProcess fp:processList){
					//处理不是当前节点的会签可见性
					if(fp.getSid()!=flowPrcsId){
						//针对其他步骤不可见
						if(fp.getFeedbackViewType()==3){
							Iterator<TeeFeedBackModel> it = modelList.iterator();
							while(it!=null && it.hasNext()){
								TeeFeedBackModel fbm = it.next();
								if(fbm.getFlowPrcsId()==fp.getSid()){
									it.remove();
								}
							}
						}
					}else{//处理是当前节点的会签可见性
						if(fp.getFeedbackViewType()==2){//本步骤经办人之间不可见
							Iterator<TeeFeedBackModel> it = modelList.iterator();
							while(it!=null && it.hasNext()){
								TeeFeedBackModel fbm = it.next();
								if(fbm.getFlowPrcsId()==fp.getSid() && fbm.getUserId()!=personId){
									it.remove();
								}
							}
						}
					}
				}
				
			}
		}
		
		return modelList;
	}
	
	/**
	 * 获取附件map
	 * @author zhp
	 * @createTime 2013-10-6
	 * @editTime 下午02:15:39
	 * @desc
	 */
	public List getFeedBackAttachList(List<TeeAttachment> attachs){
		List list = new ArrayList();
		List aList = attachs;
		for(int i=0;i<aList.size();i++){
			TeeAttachment atta = (TeeAttachment)aList.get(i);
			TeeAttachmentModel attachModel = new TeeAttachmentModel();
			BeanUtils.copyProperties(atta, attachModel);
			attachModel.setPriv(3);
			attachModel.setUserId(atta.getUser().getUuid()+"");
			attachModel.setUserName(atta.getUser().getUserName());
			attachModel.setCreateTimeDesc(TeeDateUtil.format(atta.getCreateTime()));
			list.add(attachModel);
		}
		return list;
	}
	
	
	/**
	 * 判断是否已经会签过
	 * @param runId
	 * @param prcsId
	 * @param flowPrcs
	 * @param personId
	 * @return
	 */
	public boolean hasFeedback(TeeFlowRunPrcs frp) {
		return feedBackDao.hasFeedback(frp);
	}
	
	public TeeBaseUpload getUpload() {
		return upload;
	}
	public void setUpload(TeeBaseUpload upload) {
		this.upload = upload;
	}
	public TeeAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	public void setAttachmentDao(TeeAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	public void setSimpleDao(TeeSimpleDaoSupport simpleDao) {
		this.simpleDao = simpleDao;
	}
	public TeeSimpleDaoSupport getSimpleDao() {
		return simpleDao;
	}
	
	
	
	/**
	 * 根据runId和itemId获取相应的会签意见
	 * @param request
	 * @return
	 */
	public TeeJson getFeedBackByRunIdAndItemId(HttpServletRequest request) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeJson  json=new TeeJson();
		//获取前台页面传来的itemId 和  runId
		int itemId=TeeStringUtil.getInteger(request.getParameter("itemId"),0);
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		
		String hql=" from TeeFlowRunCtrlFeedback fb where itemId=? and flowRun.runId=? ";
		List<TeeFlowRunCtrlFeedback> list=simpleDaoSupport.executeQuery(hql, new Object[]{itemId,runId});
		List<Map> mapList=new ArrayList<Map>();
		Map  m=null;
		if(list!=null&&list.size()>0){
			for (TeeFlowRunCtrlFeedback fb : list) {
				m=new HashMap();
				m.put("content", fb.getContent());
				if(fb.getCreateTime()!=null){
					m.put("createTimeStr", sdf.format(fb.getCreateTime().getTime()));
				}else{
					m.put("createTimeStr", "");
				}
				
				if(fb.getCreateUser()!=null){
					m.put("createUserName", fb.getCreateUser().getUserName());
				}else{
					m.put("createUserName", "");
				}
				
				mapList.add(m);
			}
		}
		json.setRtState(true);
		json.setRtData(mapList);
		return json;
	}
	
	
	/**
	 * /**
	 * 根据runId和  teeFlowProcess的prcsId取出对应的会签数据
	 * @param flowRun
	 * @param flowRunPrcs
	 * @return
	 */
	public List<Map<String, String>> selectFeedBackByRunIdAndFlowProcessPrcsId(
			TeeFlowRun flowRun, String prcsId,int isHasBack) {
        List<Map<String,String>> mapList=new ArrayList<Map<String,String>>();
		List param=new ArrayList();
		String hql=" from TeeFeedBack fb where  flowRun.runId=? and fb.replayId=0 ";
		param.add(flowRun.getRunId());
		if(!TeeUtility.isNullorEmpty(prcsId)){
			hql+=" and flowPrcs.sortNo=?  ";
			param.add(TeeStringUtil.getInteger(prcsId,0));
		}
		
		if(isHasBack==1){
		     hql+=" and backFlag!=1 ";	
		}
		
		List<TeeFeedBack> list=simpleDaoSupport.executeQuery(hql, param.toArray());
		Map m=null;
		if(list!=null&&list.size()>0){
		   for (TeeFeedBack fb : list) {
			  m=new HashMap();
			  m.put("C", fb.getContent());
			  m.put("B", fb.getContent());
			  m.put("U", fb.getUserName());
			  m.put("R", fb.getRoleName());
			  m.put("SD", fb.getDeptName());
			  m.put("LD", fb.getDeptFullPath());
			  if(fb.getFlowPrcs()!=null){
				  m.put("P",fb.getFlowPrcs().getPrcsName());
			  }
			  m.put("IMAGE", fb.getSignature());	  
			  if(fb.getEditTime()!=null){
				  m.put("Y",fb.getEditTime().get(Calendar.YEAR)+"");
				  m.put("M",(fb.getEditTime().get(Calendar.MONTH)+1)+"");
				  m.put("D",fb.getEditTime().get(Calendar.DAY_OF_MONTH)+"");
				  m.put("H", fb.getEditTime().get(Calendar.HOUR_OF_DAY)+"");
				  m.put("I", fb.getEditTime().get(Calendar.MINUTE)+"");
				  m.put("S", fb.getEditTime().get(Calendar.SECOND)+"");
			  }
			  
			  mapList.add(m);
			  
		   }
		}
		return mapList;
	}
	
	
	/**
	 * 根据runId和 prcsId取出对应的会签数据
	 * @param flowRun
	 * @param num
	 * @param isHasBack
	 * @return
	 */
	public List<Map<String, String>> selectFeedBackByRunIdAndPrcsId(
			TeeFlowRun flowRun, String num, int isHasBack) {
        List<Map<String,String>> mapList=new ArrayList<Map<String,String>>();
        List param=new ArrayList();
		String hql=" from TeeFeedBack fb where flowRun.runId=? and fb.replayId=0 ";
		param.add(flowRun.getRunId());
		if(!TeeUtility.isNullorEmpty(num)){
			hql+=" and  prcsId=?  ";
			param.add(TeeStringUtil.getInteger(num,0));
		}
		
		if(isHasBack==1){
		     hql+=" and backFlag!=1 ";	
		}
		
		List<TeeFeedBack> list=simpleDaoSupport.executeQuery(hql, param.toArray());
		Map m=null;
		if(list!=null&&list.size()>0){
		   for (TeeFeedBack fb : list) {
			  m=new HashMap();
			  m.put("C", fb.getContent());
			  m.put("B", fb.getContent());
			  m.put("U", fb.getUserName());
			  m.put("R", fb.getRoleName());
			  m.put("SD", fb.getDeptName());
			  m.put("LD", fb.getDeptFullPath());
			  if(fb.getFlowPrcs()!=null){
				  m.put("P",fb.getFlowPrcs().getPrcsName());
			  }
				  
			  if(fb.getEditTime()!=null){
				  m.put("Y",fb.getEditTime().get(Calendar.YEAR)+"");
				  m.put("M",(fb.getEditTime().get(Calendar.MONTH)+1)+"");
				  m.put("D",fb.getEditTime().get(Calendar.DAY_OF_MONTH)+"");
				  m.put("H", fb.getEditTime().get(Calendar.HOUR_OF_DAY)+"");
				  m.put("I", fb.getEditTime().get(Calendar.MINUTE)+"");
				  m.put("S", fb.getEditTime().get(Calendar.SECOND)+"");
			  }
			  
			  mapList.add(m);
			  
		   }
		}
		return mapList;
	}
	
	
	
	
}
