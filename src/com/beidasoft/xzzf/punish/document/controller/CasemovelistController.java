package com.beidasoft.xzzf.punish.document.controller;

import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.document.bean.DocCasemovelist;
import com.beidasoft.xzzf.punish.document.model.CasemovelistModel;
import com.beidasoft.xzzf.punish.document.service.CasemovelistService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;

@Controller
@RequestMapping("/casemovelistCtrl")
public class CasemovelistController {
	@Autowired
	private CasemovelistService casemovelistService;
	
	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存案件移送单信息
	 * @param casemovelistModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(CasemovelistModel casemovelistModel, HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		// 实例化实体类对象
		DocCasemovelist docCasemovelist = new DocCasemovelist();
		
		// 属性值传递
		BeanUtils.copyProperties(casemovelistModel, docCasemovelist);

		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(casemovelistModel.getMoveLawUnitDateStr())) {
			docCasemovelist.setMoveLawUnitDate(TeeDateUtil.format(casemovelistModel.getMoveLawUnitDateStr(),
					"yyyy年MM月dd日"));
		}
		
		// 设置创建人相关信息
		if (StringUtils.isBlank(casemovelistModel.getId())) {
			docCasemovelist.setId(UUID.randomUUID().toString());
			docCasemovelist.setCreateUserName(user.getUserName());
			docCasemovelist.setCreateUserId(user.getUuid()+"");
			docCasemovelist.setCreateTime(Calendar.getInstance().getTime());
			docCasemovelist.setDelFlg("0");
			//添加文书操作日志
			commonService.writeLog(request, "新建案件移送单");
		} else {
			//设置修改人相关信息
			docCasemovelist.setUpdateUserId(user.getUserId());
			docCasemovelist.setUpdateUserName(user.getUserName());
			docCasemovelist.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改案件移送单");
		}
		
		
		// 保存 案件移送单记录表
		casemovelistService.saveDocInfo(docCasemovelist , request);
		
		json.setRtData(docCasemovelist);
		json.setRtState(true);

		return json;
	}
	/**
	 * 查询案件移送单信息（根据ID）
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		DocCasemovelist docCasemovelist = casemovelistService.getDocInfo(id);
		CasemovelistModel casemovelistModel = new CasemovelistModel();
		BeanUtils.copyProperties(docCasemovelist, casemovelistModel);
		// 单独处理时间类型转换
		if (docCasemovelist.getMoveLawUnitDate() != null) {
			casemovelistModel.setMoveLawUnitDateStr(TeeDateUtil.format(docCasemovelist.getMoveLawUnitDate(),
					"yyyy年MM月dd日"));
		}
		// 返回 案件移送单记录表 json 对象
		json.setRtData(casemovelistModel);
		json.setRtState(true);

		return json;
	}
}
