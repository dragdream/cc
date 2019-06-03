package com.tianee.oa.core.base.filepassround.controller;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileRolePriv;
import com.tianee.oa.core.base.filepassround.bean.TeeFilePassRound;
import com.tianee.oa.core.base.filepassround.bean.TeeFilePassRoundRecord;
import com.tianee.oa.core.base.filepassround.dao.TeeFilePassRoundRecordDao;
import com.tianee.oa.core.base.filepassround.model.TeeFilePassRoundModel;
import com.tianee.oa.core.base.filepassround.service.TeeFilePassRoundRecordService;
import com.tianee.oa.core.base.filepassround.service.TeeFilePassRoundService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;


@Controller
@RequestMapping("filePassRound")
public class TeeFilePassRoundController {

	@Autowired
	private TeeFilePassRoundService filePassRoundService;
	
	@Autowired
	private TeeFilePassRoundRecordService filePassRoundRecordService;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	/**
	 * 根据主表id查询子表信息
	 * @param id	主键id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findById")
	public TeeEasyuiDataGridJson findById(String id,TeeDataGridModel m) {
		return filePassRoundRecordService.findByPassroundId(id, m);
	}
	
	/**
	 * 添加信息
	 * @param filePassRoundModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("save")
	public TeeJson save(HttpServletRequest request,TeeFilePassRoundModel filePassRoundModel) {
		TeeJson json = new TeeJson();
		try{
			filePassRoundService.save(request,filePassRoundModel);
			json.setRtState(true);
			json.setRtMsg("添加成功");
			return json;
		}catch(Exception e){
			e.printStackTrace();
			json.setRtState(false);
			json.setRtMsg("添加失败");
			return json;
		}
		
	}
	
	/**
	 * 根据发送人查询
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findByProId")
	public TeeEasyuiDataGridJson findByProId(HttpServletRequest request,TeeDataGridModel m,TeeFilePassRoundModel queryModel) {
		return filePassRoundService.findByProId(request,m,queryModel);
	}
	
	
	/**
	 * 根据接收人查询
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findByConId")
	public TeeEasyuiDataGridJson findByConId(HttpServletRequest request,TeeDataGridModel m,TeeFilePassRoundModel queryModel) {
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Integer userId=person.getUuid();
		return filePassRoundRecordService.findByConId(userId,m,queryModel);
	}
	
	/**
	 * 更新签阅状态
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateReadState")
	public TeeJson updateReadState(String id){
		TeeJson json = new TeeJson();
		try{
			filePassRoundRecordService.updateReadState(id);
			json.setRtState(true);
			json.setRtMsg("修改成功");
			return json;
		}catch(Exception e){
			e.printStackTrace();
			json.setRtState(false);
			json.setRtMsg("修改失败");
			return json;
		}
	}
	
	
	

}
