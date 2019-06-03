package com.beidasoft.xzzf.punish.document.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.document.bean.DocDiscussionPersonSignature;
import com.beidasoft.xzzf.punish.document.bean.DocGroupDiscussion;
import com.beidasoft.xzzf.punish.document.model.GroupDiscussionModel;
import com.beidasoft.xzzf.punish.document.service.DiscussionPersonSignatureService;
import com.beidasoft.xzzf.punish.document.service.GroupDiscussionService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/groupDiscussionCtrl")

public class GroupDiscussionController {

	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private DiscussionPersonSignatureService discussionPersonSignatureService;
	
	@Autowired
	private GroupDiscussionService groupDiscussionService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 *保存 案件集体讨论记录
	 * 
	 * @param groupDiscussionModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson save(GroupDiscussionModel groupDiscussionModel, HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		// 实例化实体类对象
		DocGroupDiscussion docGroupDiscussion = new DocGroupDiscussion();
		// 属性值传递
		BeanUtils.copyProperties(groupDiscussionModel, docGroupDiscussion);
		
		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(groupDiscussionModel.getDiscussionTimeStartStr())) {
			docGroupDiscussion.setDiscussionTimeStart(TeeDateUtil.format(groupDiscussionModel.getDiscussionTimeStartStr(), "yyyy年MM月dd日HH时mm分"));
		}
		if (StringUtils.isNotBlank(groupDiscussionModel.getDiscussionTimeEndStr())) {
			docGroupDiscussion.setDiscussionTimeEnd(TeeDateUtil.format(groupDiscussionModel.getDiscussionTimeEndStr(), "yyyy年MM月dd日HH时mm分"));
		}
				
		// 设置创建人相关信息
		if (StringUtils.isBlank(groupDiscussionModel.getId())) {
			docGroupDiscussion.setId(UUID.randomUUID().toString());
			docGroupDiscussion.setCreateUserId(user.getUserId());
			docGroupDiscussion.setCreateUserName(user.getUserName());
			docGroupDiscussion.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建案件集体讨论记录");
		} else {
			//设置创建人相关信息
			docGroupDiscussion.setUpdateUserId(user.getUserId());
			docGroupDiscussion.setUpdateUserName(user.getUserName());
			docGroupDiscussion.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改案件集体讨论记录");
		}
		docGroupDiscussion.setDelFlg("0");
		
		
		//保存案件集体讨论记录参与人签名
		discussionPersonSignatureService.save(groupDiscussionModel, user.getUuid(), docGroupDiscussion.getId());
		
		groupDiscussionService.save(docGroupDiscussion , request);

		json.setRtData(docGroupDiscussion);
		json.setRtState(true);

		return json;
	}
	
	/**
	 * 获取案件集体讨论记录（通过主键ID）
	 * @param inspectionRecord
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson get(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		DocGroupDiscussion docGroupDiscussion = groupDiscussionService.getById(id);
		
		GroupDiscussionModel groupDiscussionModel = new GroupDiscussionModel();
		
		BeanUtils.copyProperties(docGroupDiscussion, groupDiscussionModel);

		// 单独处理时间类型转换
		if (docGroupDiscussion.getDiscussionTimeStart() != null) {
			groupDiscussionModel.setDiscussionTimeStartStr(TeeDateUtil.format(docGroupDiscussion.getDiscussionTimeStart(), 
					"yyyy年MM月dd日HH时mm分"));
		}
		if (docGroupDiscussion.getDiscussionTimeEnd() != null) {
			groupDiscussionModel.setDiscussionTimeEndStr(TeeDateUtil.format(docGroupDiscussion.getDiscussionTimeEnd(), 
					"yyyy年MM月dd日HH时mm分"));
		}

		//获取案件集体讨论记录子表数据
		List<DocDiscussionPersonSignature> personSignList = discussionPersonSignatureService.getByGroupId(id);
		if (personSignList.size() > 0) {
			groupDiscussionModel.setListDiscussionPersonSignature(personSignList);
		}
		// 返回物品清单表 json 对象
		json.setRtData(groupDiscussionModel);
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
	public TeeJson insptnrcdpsnDoc(GroupDiscussionModel groupDiscussionModel, HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = groupDiscussionModel.getDocInfo(caseCode);
		System.out.println(content);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId, content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
}
