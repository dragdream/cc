package com.beidasoft.xzzf.clue.controller;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.clue.bean.Clue;
import com.beidasoft.xzzf.clue.bean.ClueCodeDetail;
import com.beidasoft.xzzf.clue.bean.ClueInformer;
import com.beidasoft.xzzf.clue.bean.ClueLeaderOpinion;
import com.beidasoft.xzzf.clue.bean.ClueReply;
import com.beidasoft.xzzf.clue.model.ClueLeaderOpinionModel;
import com.beidasoft.xzzf.clue.model.ClueModel;
import com.beidasoft.xzzf.clue.model.ClueReplyModel;
import com.beidasoft.xzzf.clue.service.ClueCodeDetailService;
import com.beidasoft.xzzf.clue.service.ClueInformerService;
import com.beidasoft.xzzf.clue.service.ClueLeaderOpinionService;
import com.beidasoft.xzzf.clue.service.ClueReplyService;
import com.beidasoft.xzzf.clue.service.ClueService;
import com.beidasoft.xzzf.common.service.ClueRegionService;
import com.beidasoft.xzzf.common.service.CodeService;
import com.beidasoft.xzzf.task.taskAppointed.bean.CaseAppointedInfo;
import com.beidasoft.xzzf.task.taskAppointed.service.CaseAppointedInfoService;
import com.google.gson.JsonArray;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/turnSendController")
public class turnSendController {

	@Autowired
	private ClueService clueService;
	
	@Autowired
	private ClueInformerService informerService;
	
	@Autowired
	private TeeAttachmentService attachmentService;

	@Autowired
	private ClueCodeDetailService codeDetailService;
	
	@Autowired
	private ClueRegionService regionService;
	
	@Autowired
	private ClueLeaderOpinionService opinionService;

	@Autowired
	private CaseAppointedInfoService appointedService;
	
	@Autowired
	private ClueReplyService clueReplyService;
	
	@Autowired
	private CodeService codeService;
	
	/**
	 * 个人界面的分页方法
	 * 分页获取总队机关纪委举报信访移交处理登记表数据信息详情
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listByPage")
	public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,ClueModel model,HttpServletRequest request) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson user = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String url = TeeSysProps.getString("XF_URL");	//"http://192.168.199.168";
		String searchProcessState = TeeStringUtil.getString(request.getParameter("searchProcessState"), "");
		String searchSignReplyStatic = TeeStringUtil.getString(request.getParameter("searchSignReplyStatic"), "");
		HttpGet httpGet = new HttpGet(url);
		@SuppressWarnings({ "deprecation", "resource" })
		HttpClient client = new DefaultHttpClient();
		String strUrl = "";
		int currentPage = dataGridModel.getPage();
		int pageSize = dataGridModel.getRows();
		if (!"-1".equals(searchProcessState) && !"".equals(searchProcessState)) {
			strUrl = "/module/petletter/xjzx/findPetletterList.json?searchProcessState="+searchProcessState+"&searchSignReplyStatic="+searchSignReplyStatic+"&currentPage="+currentPage+"&pageSize="+pageSize;
		} else {
			strUrl = "/module/petletter/xjzx/findPetletterList.json?searchSignReplyStatic="+searchSignReplyStatic;
		}
		HttpPost post = new HttpPost(url+strUrl);
		
		post.addHeader("Content-type", "application/json; charset=utf-8");
		post.setHeader("Accept", "application/json");
		
		String encodeStr = new String(JSONObject.fromObject(model).toString());
		StringEntity s = new StringEntity(encodeStr,Charset.forName("UTF-8"));
		s.setContentEncoding("UTF-8");
		s.setContentType("application/json;charset=utf-8");// 发送json数据需要设置contentType
		post.setEntity(s);
		List rows = new ArrayList<>();
		long total = 0;
		try {
			HttpResponse res = client.execute(post);
			
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				String result = EntityUtils.toString(entity);// 返回json格式：
				//将json字符串里面的数据转成对象DocumentRecord对象
				JSONObject object=JSONObject.fromObject(result);
				Object resultData = object.get("data");
				Object resultMsg = object.get("message");
				Object resultFlg = object.get("success");
				
				if ("true".equals(resultFlg.toString())) {
					JSONObject fromObject = JSONObject.fromObject(resultData);
					rows = (List)fromObject.get("resultList");
					total = TeeStringUtil.getLong(fromObject.get("count"), 0);
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		dataGridJson.setTotal(total);
		dataGridJson.setRows(rows);
		return dataGridJson;
	}
	
	/**
	 * 获取总队机关纪委举报信访移交处理登记表数据信息详情
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/turnSendInfo")
	public TeeJson turnSendInfo(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String reportId = request.getParameter("reportId");
		String url = TeeSysProps.getString("XF_URL");	
		HttpGet httpGet = new HttpGet(url);
		@SuppressWarnings({ "deprecation", "resource" })
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url+"/module/petletter/xjzx/viewInfo.json?reportId="+reportId);
		post.addHeader("Content-type", "application/json; charset=utf-8");
		post.setHeader("Accept", "application/json");
		
		StringEntity s = new StringEntity("",Charset.forName("UTF-8"));
		s.setContentEncoding("UTF-8");
		s.setContentType("application/json;charset=utf-8");// 发送json数据需要设置contentType
		post.setEntity(s);
		JSONObject fromObject = null;
		try {
			HttpResponse res = client.execute(post);
			
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				String result = EntityUtils.toString(entity);// 返回json格式：
				//将json字符串里面的数据转成对象DocumentRecord对象
				JSONObject object=JSONObject.fromObject(result);
				Object resultData = object.get("data");
				Object resultMsg = object.get("message");
				Object resultFlg = object.get("success");
				if ("true".equals(resultFlg.toString())) {
					fromObject = JSONObject.fromObject(resultData);
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		json.setRtData(fromObject);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 签收总队机关即为举报信访移交处理登记表数据
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveTurnSend")
	public TeeJson saveTurnSend(HttpServletRequest request){
		TeeJson saveTurnSend = clueService.saveTurnSend(request);
		return saveTurnSend;
	 }
	
	
}