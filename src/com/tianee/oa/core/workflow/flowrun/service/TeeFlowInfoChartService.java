package com.tianee.oa.core.workflow.flowrun.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.similarities.Similarity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunPrcsDao;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowInfoCharModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowRunPrcsModel;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;

/**
 * 
 * @author zhp
 * @createTime 2013-10-1
 * @desc
 */
@Service
public class TeeFlowInfoChartService extends TeeBaseService implements TeeFlowInfoChartServiceInterface{


	@Autowired
	private TeeFlowRunPrcsDao flowRunPrcsDao;
	
	@Autowired
	private TeeFlowRunServiceInterface flowRunService;

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowInfoChartServiceInterface#loadAllPrcsInfo(int)
	 */
	@Override
	public List loadAllPrcsInfo(int runId){
		
		List<TeeFlowRunPrcs> list = flowRunPrcsDao.getAllFlowRunPrcsByRunId(runId);
		List modelList = new ArrayList();
		if(list == null){
			return modelList;
		}
		
		int maxPrcs = getMaxPrcsId(list);
		for(int i=0;i<maxPrcs;i++){
			TeeFlowInfoCharModel model = madeModel(list, i+1);
			modelList.add(model);
		}
		
		return modelList;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowInfoChartServiceInterface#getFlowRunChildData(int)
	 */
	@Override
	public List getFlowRunChildData(int runId){
		List<TeeFlowRun> list = flowRunService.getChildFlowRuns(runId);
		List datas = new ArrayList();
		Map flagData = null;
		for(TeeFlowRun fr:list){
			Map dataItem = new HashMap();
			dataItem.put("runName", fr.getRunName());
			dataItem.put("runId", fr.getRunId());
			dataItem.put("flowId", fr.getFlowType().getSid());
			dataItem.put("pFlowPrcsId", fr.getPFlowPrcsId());
			dataItem.put("beginUserName", fr.getBeginPerson().getUserName());
			dataItem.put("beginTime", TeeDateUtil.format(fr.getBeginTime()));
			flagData = simpleDaoSupport.getMap("select flag as FLAG from TeeFlowRunPrcs where flowRun.runId=? and prcsId=1", new Object[]{fr.getRunId()});
			dataItem.put("recType", flagData.get("FLAG"));
			datas.add(dataItem);
		}
		return datas;
	}
	
	/**
	 * 组装model对象
	 * @author zhp
	 * @createTime 2013-10-1
	 * @editTime 下午06:17:06
	 * @desc
	 */
	public static TeeFlowInfoCharModel madeModel(List<TeeFlowRunPrcs> list,int prcsId){
		TeeFlowRunPrcs temPrcs = null;
		TeeFlowRunPrcs temPrcs1 = null;
		int flag = 1;// 办理中 1,2,3 3已经办结 2 办理中 1 未查看
		
		TeeFlowInfoCharModel model = new TeeFlowInfoCharModel(); 
		List childPrcsList = new ArrayList();
		if(list == null){
			return null;
		}
		for(int i=0;i<list.size();i++){
			TeeFlowRunPrcs prcs = list.get(i);
			
			if(prcs.getPrcsId() == prcsId){
				
				if(i == 0){
					temPrcs1 = prcs;
				}
				
				if(prcs.getOpFlag() == 1){
					temPrcs = prcs;
				}
				if(prcs.getFlag() == 2){
					if(flag != 3){
						flag = 2;
					}
				}
				
				if(prcs.getFlag() != 2 && prcs.getFlag() != 1 ){
					flag = 3;
				}
				
				TeeFlowRunPrcsModel mm = new TeeFlowRunPrcsModel();
				BeanUtils.copyProperties(prcs, mm);
				childPrcsList.add(mm);
			}
		}
		/**
		 * 处理无主办会签情况
		 */
		if(temPrcs == null){
			temPrcs = temPrcs1;
		}
		model.setPrcsList(childPrcsList);
		model.setFlowFlag(flag);
		model.setPrcsId(prcsId);
		try {
			if(temPrcs.getFlowRun() != null){
				if(temPrcs.getFlowRun().getFlowType() != null){
					int type = temPrcs.getFlowRun().getFlowType().getType();
					if(type == 2){
						model.setPrcsName("自由流程");
					}else{
						String prcsName = temPrcs.getFlowPrcs().getPrcsName();
						model.setPrcsName(prcsName);
					}
				}
			}
		} catch (Exception e) {
			model.setPrcsName("");
			e.printStackTrace();
		}
	   model.setRunId(temPrcs.getFlowRun().getRunId());
	   model.setUserName(temPrcs.getPrcsUser().getUserName());
	   model.setRunName(temPrcs.getFlowRun().getRunName());
		return model;
	}
	/**
	 * 获取最大的 步骤
	 * @author zhp
	 * @createTime 2013-10-1
	 * @editTime 下午06:09:57
	 * @desc
	 */
	public static int getMaxPrcsId(List<TeeFlowRunPrcs> list){
		int maxPrcsId = 0;
		if(list == null){
			return maxPrcsId;
		}
		for(TeeFlowRunPrcs prcs:list){
			int temPrcsId = prcs.getPrcsId();
			if(maxPrcsId < temPrcsId){
				maxPrcsId = temPrcsId;
			}
		}
		return maxPrcsId;
	}
}
