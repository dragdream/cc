package com.beidasoft.xzzf.punish.common.service;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class DocWenhaoService extends TeeBaseService {

	/**
	 * 获取最大文号
	 * 
	 * @param request
	 * @return
	 */
	public synchronized Map<String, String> getMaxNum(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 获取文书文字和年
		String unitCode = user.getDept().getSubordinateUnitsCode();
		String docType = "";
		
		if ("1".equals(request.getParameter("isCreateAcc"))) { //如果是来自于线索 取受理单文号
			docType += "京";
		} else {
			docType += TeeSysCodeManager.getChildSysCodeNameCodeNo("DOC_WORD", unitCode);
		}
		// 文件字(中文)
		map.put("DocArea", docType);
		// 文号年
		String docYear = TeeDateUtil.format(new Date(), "yyyy");
		map.put("DocYear", docYear);
		// 文书号
		map.put("DocNum", "0");
		
		String sql = "";
		// 流程号
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		String docId = "";
		// 文书类型ID
		if ("1".equals(request.getParameter("isCreateAcc"))) { //如果是来自于线索 取受理单文号
			docId += "ZF_CLUE_ACCEPT";
		} else {
			docId += TeeStringUtil.getString(request.getParameter("docId"));
		}
		
		if (StringUtils.isBlank(docType) || StringUtils.isBlank(docId)) {
			return map;
		}
		// 获取最大文书号
		sql = "SELECT MAX(DOC_NUM) AS DOC_NUM FROM ZF_DOC_WENHAO WHERE DOC_TYPE=? AND DOC_ID=? AND DOC_YEAR=?";
		Map<?, ?> data = simpleDaoSupport.executeNativeUnique(sql, new Object[] {docType, docId, docYear});
		int docNum = TeeStringUtil.getInteger(data.get("DOC_NUM"), 0);
		if (docNum == 0) {
			// 如果没有生成文号，则追加当前文种和年份的初始信息
			sql = "INSERT INTO ZF_DOC_WENHAO(DOC_NUM,DOC_TYPE,DOC_YEAR,DOC_ID) VALUES(?,?,?,?)";
		} else {
			// 如果生成过文号，则更新最大编号
			sql = "UPDATE ZF_DOC_WENHAO SET DOC_NUM=? WHERE DOC_TYPE=? AND DOC_YEAR=? AND DOC_ID=?";
		}
		docNum += 1;
		// 保存数据
		simpleDaoSupport.executeNativeUpdate(sql, new Object[] { docNum, docType, docYear, docId });
		
		//流水号为5位，不足时左补零
		if ("1".equals(request.getParameter("isCreateAcc"))) { //如果是来自于线索 ，流水号为4位
			map.put("DocNum", StringUtils.leftPad(String.valueOf(docNum), 4, '0'));
		} else {
			map.put("DocNum", StringUtils.leftPad(String.valueOf(docNum), 5, '0'));
		}
		
		return map;
	}
}