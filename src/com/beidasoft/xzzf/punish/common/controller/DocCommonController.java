package com.beidasoft.xzzf.punish.common.controller;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.bean.PunishDocPdf;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/docComCtrl")
public class DocCommonController {
	
	@Autowired
	private TeeAttachmentService attachmentService;

	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private PunishFlowService flowService;
	
	/**
	 * 生成文号
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/genWenHao")
	@ResponseBody
	public TeeJson genWenHao(HttpServletRequest request) {

		TeeJson json = new TeeJson();
		String sql = "";
		// 流程号
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		// 文件字(中文)
		String docType = TeeStringUtil.getString(request.getParameter("docType"));
		// 文号年
		String docYear = TeeStringUtil.getString(request.getParameter("docYear"));
		docYear = TeeDateUtil.format(new Date(), "yyyy");
		// 文书类型ID
		String docId = TeeStringUtil.getString(request.getParameter("docId"));

		Connection conn = null;
		try {
			conn = TeeDbUtility.getConnection();
			DbUtils dbUtils = new DbUtils(conn);
			// 获取最大文书号
			sql = "SELECT MAX(DOC_NUM) AS DOC_NUM FROM ZF_DOC_WENHAO WHERE DOC_TYPE='"
					+ docType
					+ "' AND DOC_ID='"
					+ docId
					+ "' AND DOC_YEAR='"
					+ docYear
					+ "'";
			Map data = dbUtils.queryToMap(sql);
			// 获取当前流程下已经生成过的文号
			int docNum = TeeStringUtil.getInteger(data.get("DOC_NUM"), 0);
			if (docNum == 0) {
				// 如果没有生成文号，则追加当前文种和年份的初始信息
				sql = "INSERT INTO ZF_DOC_WENHAO(DOC_NUM,DOC_TYPE,DOC_YEAR,DOC_ID,RUN_ID) VALUES(?,?,?,?,?)";
			} else {
				// 如果生成过文号，则更新最大编号
				sql = "UPDATE ZF_DOC_WENHAO SET DOC_NUM=? WHERE DOC_TYPE=? AND DOC_YEAR=? AND DOC_ID=? AND RUN_ID=?";
			}
			docNum += 1;
			dbUtils.executeUpdate(sql, new Object[] { docNum, docType, docYear, docId, runId });
			// 保存数据
			conn.commit();
			//流水号为5位，不足时左补零
			json.setRtData(StringUtils.leftPad(String.valueOf(docNum), 5, '0'));
			json.setRtState(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			json.setRtData(new Object[] {});
			json.setRtState(false);
		} finally {
			TeeDbUtility.closeConn(conn);
		}

		return json;
	}
	
	// 获取主办人，协办人
	@SuppressWarnings("rawtypes")
	@RequestMapping("/minorInfo")
	@ResponseBody
	public TeeJson getMinorInfo(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String primaryKey = request.getParameter("PRIMARY_ID");
		String baseId = request.getParameter("BASE_ID");
		String sql = "";
		StringBuilder sb = new StringBuilder("");
		Connection conn = null;
		try {
			conn = TeeDbUtility.getConnection();
			DbUtils dbUtils = new DbUtils(conn);
			// 取得协办人信息
			if (StringUtils.isBlank(primaryKey)) {
				sql = "SELECT MINOR_PERSON_ID AS MINOR_ID, MINOR_PERSON_NAME AS MINOR_NAME FROM ZF_PUNISH_BASE WHERE BASE_ID='"
						+ baseId + "'";
			} else {
				sql = "SELECT MINOR_ID, MINOR_NAME FROM ZF_PUNISH_FLOW WHERE PUNISH_DOC_ID='"
						+ primaryKey + "'";
			}
			Map data = dbUtils.queryToMap(sql);
			if (data != null) {
				sb.append("{NextUserId:");
				sb.append(data.get("MINOR_ID"));
				sb.append(",NextUserName:\"");
				sb.append(data.get("MINOR_NAME"));
				sb.append("\"}");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			json.setRtData(new Object[] {});
			json.setRtState(false);
		} finally {
			TeeDbUtility.closeConn(conn);
		}
		json.setRtData(sb.toString());
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取附件列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/attachmentList")
	@ResponseBody
	public TeeJson getAttachmentList(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String model = TeeStringUtil.getString(request.getParameter("model"), "");
		String modelId = TeeStringUtil.getString(request.getParameter("modelId"), "");
		// 获取附件列表
		List<TeeAttachmentModel> attachmt = attachmentService.getAttacheModels(model, modelId);
		
		json.setRtData(attachmt);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 预览文书
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/viewDocInfo")
	@ResponseBody
	public TeeJson viewDocInfo(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeAttachment pdfAttach = null;
		// 文书ID
		String docId = TeeStringUtil.getString(request.getParameter("docId"));
		// 公章是否显示
		String isShow = TeeStringUtil.getString(request.getParameter("isShow"));
		String docNum = TeeStringUtil.getString(request.getParameter("docNum"));
		// 文书编号
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		// 获取文书内容
		String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"));
		Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
		// 根据文书ID获取PDFID
		int attachId = 0;
		if (StringUtils.isNotBlank(docNum)) {
			PunishDocPdf docPdf = commonService.getPdfId(docId);
			if (docPdf != null) {
				attachId = docPdf.getPdfId();
			}
		}
		if (attachId == 0) {
			// PDFID不存在的场合
			pdfAttach = wenShuService.initDocTemplate(templateId, content);
		} else {
			// PDFID存在的场合
			pdfAttach = attachmentService.getById(attachId);
		}
		if ("true".equals(isShow)) {
			pdfAttach = wenShuService.gZPdfSign(docId, pdfAttach, "", request);
		}
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
	
	
	
	/**
	 * 移动端预览文书
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/viewDocInfo4Mobile")
	@ResponseBody
	public TeeJson viewDocInfo4Mobile(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeAttachment pdfAttach = null;
		// 文书编号
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		// 获取文书内容
		String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
		Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
		// 根据文书ID获取PDFID
		PunishDocPdf docPdf = commonService.getPdfId(request.getParameter("docId"));
		int attachId = 0;
		if (docPdf != null) {
			attachId = docPdf.getPdfId();
		}
		if (attachId == 0) {
			// PDFID不存在的场合
			pdfAttach = wenShuService.initDocTemplate(templateId, content);
		} else {
			// PDFID存在的场合
			pdfAttach = attachmentService.getById(attachId);
		}
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 通过baseId、lawLinkId、confFlowName、beanName 获取文书List
	 * 
	 * @param baseId
	 * @param lawLinkId
	 * @param confFlowName
	 * @param beanName
	 * @return
	 */
	@RequestMapping("/getAnotherDocInfo")
	@ResponseBody
	public TeeJson getAnotherDocInfo(String baseId, String lawLinkId, String confFlowName, String beanName) {
		TeeJson json = new TeeJson();
		
		//如果当前环节与欲查询文书的环节不是同一环节
		if (lawLinkId.equals("")) { 
			List<PunishFLow> list = flowService.getFlowcase(baseId, confFlowName, "");//通过baseId 和 流程名  查流程 信息
			lawLinkId = list.get(0).getTacheId();
		}
		@SuppressWarnings("rawtypes")
		List list = commonService.getDocByBaseId(beanName, baseId, lawLinkId);
		if (list.size() == 0) {
			list = null;
		}
		
//		String modelName = beanName.substring(2, beanName.length()) + "Model";
//		
//		list = (List) Class.forName("List<"+beanName+">").newInstance();
		json.setRtData(list);
		
		return json;
	}
	
	/**
	 * 获取文书文字值
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/docWordInfo")
	@ResponseBody
	public TeeJson getDocWordInfo(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map<String, String> map = new HashMap<String, String>();
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String unitCode = user.getDept().getSubordinateUnitsCode();
		String wordVal = "";
		if (StringUtils.isNotBlank(unitCode)) {
			wordVal = TeeSysCodeManager.getChildSysCodeNameCodeNo("DOC_WORD", unitCode);
		}
		map.put("DocArea", wordVal);
		map.put("DocYear", TeeDateUtil.format(new Date(), "yyyy"));
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	/**
	 * 根据baseId查询文书
	 * @param baseId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getById")
	public TeeJson getById(String baseId) {
		TeeJson json = new TeeJson();
		List<PunishFLow> list = flowService.getByBaseId(baseId);
		json.setRtData(list);
		json.setRtState(true);
		return json;
  }
}
