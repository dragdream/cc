package com.beidasoft.xzfy.caseTrial.template.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzfy.caseTrial.common.uploadFile;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workFlowFrame.creator.TeeFlowCreatorInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeService;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.model.TeeBisRunModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeBeanFactory;

import net.sf.json.JSONObject;

@Service
public class CommonCreateFlowService extends TeeBaseService{
	
	@Autowired
	TeePersonService personService;
	@Autowired
	TeeFlowCreatorInterface flowCreator ;
	@Autowired
	TeeFlowTypeService flowTypeService ;
	@Autowired
	TeeAttachmentService attachmentService;
	
	public String newFlowByRecordId(String recordId,String userId,String flow_data,String flow_id){
		TeeJson json = new TeeJson();
		
		/*flow_data = flow_data.replace("\\", "[*]");
		JSONArray jsonArray = JSONArray.fromObject(datas);
		String recordId = TeeStringUtil.getString(jsonArray.get(0));
		String userId = TeeStringUtil.getString(jsonArray.get(1));
		String flow_id = TeeStringUtil.getString(jsonArray.get(4));*/
		//String isHas = jsonArray.get(3).toString();
		//String main_run_id = jsonArray.get(5).toString();
		//判断是否存在当前用户
		TeePerson person = personService.getPersonByUserId(userId);
		//解析附件参数
		TeeFlowType ft	= flowTypeService.get(Integer.parseInt(flow_id));//流程类型对象
		int validation = 0;
		//处理接收流程发起数据相关
		TeeBisRunModel bisRunModel = new TeeBisRunModel();
		Map<String, String> runDatas = new HashMap<String, String>();
		bisRunModel.setRunDatas(runDatas);
		bisRunModel.setBusinessKey("");
		if(null==person){
			json.setRtMsg("创建失败，无此人信息");
			json.setRtState(false);
			return JSONObject.fromObject(json).toString();
		}
		TeeFlowRunPrcs flowRunPrcs = null;
		Connection conn = null;
		int  runId = 0;
		try {
			flowRunPrcs = flowCreator.CreateNewWork(ft, person,bisRunModel,validation==1);
			runId = flowRunPrcs.getFlowRun().getRunId();
			flowRunPrcs.getFlowRun().setIsSave(1);
			conn = TeeDbUtility.getConnection();
			DbUtils dbUtils = new DbUtils(conn);
			dbUtils.executeUpdate("insert into run_record_id (run_id,recordId,frpSid,flowId) values (?,?,?,?) ", new Object[]{runId,recordId,flowRunPrcs.getSid(),flowRunPrcs.getFlowType().getSid()});
			//将附件拷贝到本服务
			String atthment =flow_data;
			try{
				if(null!=atthment&&!"".equals(atthment)){
					uploadFile.copyFile(runId,atthment,attachmentService,person,flowRunPrcs);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			TeeDbUtility.rollback(conn);
			throw new TeeOperationException(e);
		}finally{
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return runId+"";
	}
}
