package com.tianee.oa.core.base.fileNetdisk.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.fileNetdisk.model.TeeFileNetdiskReaderModel;
import com.tianee.oa.core.base.fileNetdisk.service.TeeFileOptRecordService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
/**
 * 操作历史记录
 * @author xsy
 *
 */
@Controller
@RequestMapping("/teeFileOptRecordController")
public class TeeFileOptRecordController {
	@Autowired
	private TeeFileOptRecordService service;
	
	
	/**
	 * 查看历史记录
	 * @param request
	 * @return
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getHistorys")
	@ResponseBody
	public TeeEasyuiDataGridJson getHistorys(HttpServletRequest request){
		//获取页面上传来的网盘的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		return service.getHistorys(sid);
	}
}
