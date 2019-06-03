package com.beidasoft.xzzf.punish.transact.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.transact.bean.TransactBean;
import com.beidasoft.xzzf.punish.transact.model.TransactModel;
import com.beidasoft.xzzf.punish.transact.service.TransactService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;

@Controller
@RequestMapping("/transactController")
public class TransactController {
	
	@Autowired
	TransactService transactService;
	
	@Autowired
	TeePersonService personService;
	
	@RequestMapping("/table")
	@ResponseBody
	public TeeEasyuiDataGridJson table(HttpServletRequest request, TeeDataGridModel dm) {
	
		return transactService.getTranscatOfPage(dm, request);
	}
	
	/**
	 * 获取参与人信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPersonInfo")
	@ResponseBody
	public TeeJson getPersonDeptInfo(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map<String, Object> mapInfo = null;
		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
		// 用户UUID群
		String userIds = request.getParameter("userIds");
		// 获取用户相关信息
		List<TeePerson> psnList = personService.getPersonByUuids(userIds);
		for (TeePerson psnInfo : psnList) {
			mapInfo = new HashMap<String, Object>();
			// 用户UUID
			mapInfo.put("userUuid", psnInfo.getUuid());
			// 用户名
			mapInfo.put("userName", psnInfo.getUserName());
			// 执法证号
			mapInfo.put("userCode", StringUtils.defaultIfBlank(psnInfo.getOicqNo(), ""));
			// 部门UUID
			mapInfo.put("deptUuid", psnInfo.getDept().getUuid());
			// 部门名称
			mapInfo.put("deptName", psnInfo.getDept().getDeptName());
			rtnList.add(mapInfo);
		}
		json.setRtState(true);
		json.setRtData(rtnList);
		return json;
	}
	
	/**
	 * 保存当事人信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/savePartyInfo")
	@ResponseBody
	public TeeJson savePartyInfo(TransactModel transactModel, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TransactBean transactInfo = new TransactBean();
		String uuid = UUID.randomUUID().toString();
		// 属性值传递
		BeanUtils.copyProperties(transactModel, transactInfo);
		if (StringUtils.isBlank(transactModel.getId())) {
			// 主键
			transactInfo.setId(uuid);
		} else {
			uuid = transactModel.getId();
		}
		// 案件编号
		transactInfo.setBaseId(TeeDateUtil.format(new Date(), "yyyyMMddHHmmsssss"));
		// 名称设定
		if ("1".equals(transactModel.getLitigantType())) {
			transactInfo.setLitigantName(transactModel.getUnitName());
		} else {
			transactInfo.setLitigantName(transactModel.getPsnName());
		}
		// 状态
		transactInfo.setStatus("0");
		// 保存当事人信息
		transactService.save(transactInfo);
		// 保存参与人信息
		Map infoMap = request.getParameterMap();
		
		
		json.setRtData(uuid);
		json.setRtState(true);
		return json;
	}
	
}
