package com.tianee.oa.core.xt.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.xt.model.TeeXTRunModel;
import com.tianee.oa.core.xt.service.TeeXTRunService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/teeXTRunController")
public class TeeXTRunController {

	@Autowired
	private TeeXTRunService xtRunService;
	
	
	@Autowired
	private TeeBaseUpload baseUpload;
	
	
	@Autowired
	private TeeAttachmentService attachService;
	
	/**
	 * 创建正文  word正文  excel正文  
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/createDoc")
	@ResponseBody
	public TeeJson createDoc(HttpServletRequest request) throws IOException{
		//获取当前登陆人 
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		//获取前台传来的docType
		//2=word正文  3=excel正文
		int docType=TeeStringUtil.getInteger(request.getParameter("docType"),0);
		
		TeeAttachment attach = null;
		if(docType==2){//word正文
			attach = baseUpload.newAttachment("正文", "doc", TeeAttachmentModelKeys.XT_DOC,loginUser);
		}else if(docType==3){//excel正文
			attach = baseUpload.newAttachment("正文", "xls", TeeAttachmentModelKeys.XT_DOC,loginUser);
		}
		//保存到数据库
        attachService.addAttachment(attach);
		
        if(attach!=null){
        	int attachId = attach.getSid();
    		String attachName = attach.getFileName();
    		Map map = new HashMap<String, String>();
    		map.put("attachId", attachId);
    		map.put("attachName", attachName);
    		json.setRtData(map);
    		json.setRtMsg("新建附件成功!");
    		json.setRtState(true);
        }else{
        	json.setRtState(false);
        }
		return  json;
	}	
	
	
	
	/**
	 * 新增或者编辑
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request,TeeXTRunModel model){
		TeeJson json=new TeeJson();
		json=xtRunService.addOrUpdate(request,model);
		return json;
	}
	
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request){
		TeeJson json=new TeeJson();
		json=xtRunService.getInfoBySid(request);
		return json;
	}
	
	
	
	
	/**
	 * 根据状态获取列表   0==待发
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getDaiFaList")
	@ResponseBody
	public TeeEasyuiDataGridJson getDaiFaList(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunService.getDaiFaList(request,dm);
	}
	
	/**
	 * 获取已发列表  1=已发未终止  2=已发已终止
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getYiFaList")
	@ResponseBody
	public TeeEasyuiDataGridJson getYiFaList(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunService.getYiFaList(request,dm);
	}
	
	/**
	 * 批量删除
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public TeeJson delete(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunService.delete(request);
	}
	
	
	
	/**
	 * 发送
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/send")
	@ResponseBody
	public TeeJson send(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunService.send(request);
	}
	
	
	
	/**
	 * 撤销
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/revoke")
	@ResponseBody
	public TeeJson revoke(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunService.revoke(request);
	}
	
	/**
	 * 删除指定附件
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/deleteAttach")
	@ResponseBody
	public TeeJson deleteAttach(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunService.deleteAttach(request);
	}
	
	
	/**
	 * 标准正文  修改正文
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/updateContent")
	@ResponseBody
	public TeeJson updateContent(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunService.updateContent(request);
	}
	
	
	/**
	 * 转发
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/repeat")
	@ResponseBody
	public TeeJson repeat(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunService.repeat(request);
	}
	
	
	/**
	 * 获取原协同的意见
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getParentOpinion")
	@ResponseBody
	public TeeJson getParentOpinion(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunService.getParentOpinion(request);
	}
	
	
	
	/**
	 * 获取绘制流程图的数据
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getFlowData")
	@ResponseBody
	public TeeJson getFlowData(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunService.getFlowData(request);
	}
	
	
	/**
	 * 催办
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/doUrge")
	@ResponseBody
	public TeeJson doUrge(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunService.doUrge(request);
	}
	
	
	/**
	 * 同步数据
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/dataSync")
	@ResponseBody
	public TeeJson dataSync(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		
		return xtRunService.dataSync(request);
	}
}
