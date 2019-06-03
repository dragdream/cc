package com.beidasoft.xzfy.organPerson.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.beidasoft.xzfy.base.controller.FyBaseController;
import com.beidasoft.xzfy.base.exception.ValidateException;
import com.beidasoft.xzfy.base.model.response.Response;
import com.beidasoft.xzfy.organPerson.bean.OrganPersonInfo;
import com.beidasoft.xzfy.organPerson.model.request.OrganPersonAddRequest;
import com.beidasoft.xzfy.organPerson.model.request.OrganPersonDeleteRequest;
import com.beidasoft.xzfy.organPerson.model.request.OrganPersonInfoRequest;
import com.beidasoft.xzfy.organPerson.model.request.OrganPersonListRequest;
import com.beidasoft.xzfy.organPerson.model.request.OrganPersonUpdateRequest;
import com.beidasoft.xzfy.organPerson.service.OrganPersonService;
import com.beidasoft.xzfy.utils.Const;
import com.beidasoft.xzfy.utils.ConstCode;
import com.beidasoft.xzfy.utils.ExportExcelUtils;
import com.beidasoft.xzfy.utils.ImportExcelUtils;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

import common.Logger;

/**
 * 组织机构人员
 * @author fyj
 *
 */
@Controller
@RequestMapping("/xzfy/organPerson")
public class OrganPersonController extends FyBaseController{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	public Logger log = Logger.getLogger(OrganPersonController.class);
	
	//组织机构人员service
	@Autowired
	private OrganPersonService personService;
	
	
	/**
	 * 查询组织机构人员列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public TeeEasyuiDataGridJson list(OrganPersonListRequest req){
		
		log.info("[xzfy - OrganPersonController - list] enter controller.");
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		try
		{
			//参数校验
			req.validate();
			
			//获取列表
			List<OrganPersonInfo> list = personService.getOrganPersonList(req);
			
			//获取总记录数
			int total = personService.getOrganPersonListTotal(req);
			
			//设置返回参数
			json.setRows(list);
			json.setTotal( new Long(total) );
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - OrganPersonController - list] error=" + e);
		}
		catch(Exception e){
			
			log.info("[xzfy - OrganPersonController - list] error="+e);
		}
		finally{
			
			log.info("[xzfy - OrganPersonController - list] controller end.");
		}
		return json;
	}
	
	
	/**
	 * 获取组织机构人员详情
	 * @param req
	 * @return
	 */
	@RequestMapping("/getDetial")
	@ResponseBody
	public TeeJson getDetial(OrganPersonInfoRequest req){
		
		log.info("[xzfy - OrganPersonController - getDetial] enter controller.");
		
		TeeJson json = new TeeJson();
		
		try
		{
			//参数校验
			req.validate();
			
			//获取详情
			OrganPersonInfo person = personService.getOrganPersonInfo(req.getPersonId());
			
			//设置返回参数
			json.setRtState(true);
			json.setRtMsg("请求成功");
			json.setRtData(person);
			
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - OrganPersonController - getDetial] error=" + e);
			json.setRtState(false);
			json.setRtMsg("校验失败");
		}
		catch(Exception e){
			
			log.info("[xzfy - OrganPersonController - getDetial] error="+e);
			json.setRtState(false);
			json.setRtMsg("请求失败");
		}
		finally{
			
			log.info("[xzfy - OrganPersonController - getDetial] controller end.");
		}
		return json;
	}
	
	
	/**
	 * 新增组织机构人员
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public TeeJson add(OrganPersonAddRequest req){
		
		log.info("[xzfy - OrganPersonController - add] enter controller.");
		TeeJson json = new TeeJson();
		try{
		
			//参数校验
			req.validate();
			
			//新增组织机构人员
			personService.addOrganPersonInfo(req,getRequest());
			
			//设置返回参数
			json.setRtState(true);
			json.setRtMsg("请求成功");
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - OrganPersonController - add] error=" + e);
			json.setRtState(false);
			json.setRtMsg("校验失败");
		}
		catch(Exception e){
			
			log.info("[xzfy - OrganPersonController - add] error="+e);
			json.setRtState(false);
			json.setRtMsg("请求失败");
		}
		finally{
			
			log.info("[xzfy - OrganPersonController - add] controller end.");
		}
		return json;
	}
	
	
	/**
	 * 更新组织机构人员信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public TeeJson update(OrganPersonUpdateRequest req){
		
		log.info("[xzfy - OrganPersonController - update] enter controller.");
		TeeJson json = new TeeJson();
		try{
			//参数校验
			req.validate();
			
			//修改组织机构人员信息
			personService.updateOrganPersonInfo(req,getRequest());
			
			//设置返回参数
			json.setRtState(true);
			json.setRtMsg("请求成功");
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - OrganPersonController - update] error=" + e);
			json.setRtState(false);
			json.setRtMsg("校验失败");
		}
		catch(Exception e){
			
			log.info("[xzfy - OrganPersonController - update] error="+e);
			json.setRtState(false);
			json.setRtMsg("请求失败");
		}
		finally{
			
			log.info("[xzfy - OrganPersonController - update] controller end.");
		}
		return json;
	}
	
	
	/**
	 * 删除组织机构人员信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public TeeJson delete(OrganPersonDeleteRequest req){
		
		log.info("[xzfy - OrganPersonController - delete] enter controller.");
		TeeJson json = new TeeJson();
		
		try{
			//参数校验
			req.validate();
			
			//删除组织机构人员
			personService.deleteOrganPerson(req.getPersonIds());
			
			//设置返回参数
			json.setRtState(true);
			json.setRtMsg("请求成功");
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - OrganPersonController - delete] error=" + e);
			json.setRtState(false);
			json.setRtMsg("校验失败");
		}
		catch(Exception e){
			
			log.info("[xzfy - OrganPersonController - delete] error="+e);
			json.setRtState(false);
			json.setRtMsg("请求失败");
		}
		finally{
			
			log.info("[xzfy - OrganPersonController - delete] controller end.");
		}
		return json;
	}
	
	/**
	 * 导出excel文件
	 * @param req
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportExcel")
	@ResponseBody
	public void exportExcel(OrganPersonListRequest req,HttpServletRequest request,
			HttpServletResponse response){
		
		log.info("[xzfy - OrganPersonController - exportExcel] enter controller.");
		
		try{
			//参数校验
			req.validate();
			
			//获取该组织机构下的子组织机构列表
			req.setPage(1);
			req.setRows(65535);
			List<OrganPersonInfo> list = personService.getOrganPersonList(req);
			
			String sheet = "组织机构人员信息"; 
			//设置导出标题
			String[] titles = new String[12];
			titles[0] = "序号";
			titles[1] = "机关名称";
			titles[2] ="人员姓名";
			titles[3] ="性别";
			titles[4] ="身份证";
			titles[5] ="人员编制";
			titles[6] ="职级";
			titles[7] ="学历";
			titles[8] ="是否获取法律证书";
			titles[9] ="是否党员";
			titles[10] ="电话";
			titles[11] ="邮箱";
			
			//设置内容
			int len = list.size();
			
			List<Object[]> dataList = new ArrayList<Object[]>();
			Object[] object = new Object[12];
			OrganPersonInfo person = null;
			for(int i=0 ;i<len;i++){
				//对象转为数组
				person = list.get(i);
				object[0] = "";
				object[1] = person.getOrgName();
				object[2] = person.getPersonName();
				String sex = "";
				if(person.getSex()==Const.SEX.MAN){
					sex= "男";
				}else if(person.getSex()==Const.SEX.WOMAN){
					sex= "女";
				}else if(person.getSex()==Const.SEX.OTHER){
					sex= "其他";
				}
				object[3] = sex;
				object[4] = person.getIdCard();
				object[5] = person.getStaffingName();
				object[6] = person.getLevelName();
				object[7] = person.getEducationName();
				object[8] = person.getIsLaw() == Const.TYPE.ZERO ? "否":"是";
				object[9] = person.getIsParty()== Const.TYPE.ZERO ? "否":"是";
				object[10] = person.getPhone();
				object[11] = person.getEmail();
				dataList.add(object);
			}

			String name = StringUtils.getExprotTimeName() + ".xls";
			//导出excel
			new ExportExcelUtils().exportExcel(sheet,titles,dataList,name,response);
		}
		catch(Exception e){
			
			log.info("[xzfy - OrganPersonController - exportExcel] error="+e);
		}
		finally{
			
			log.info("[xzfy - OrganPersonController - exportExcel] controller end.");
		}
	}
	
	//TODO
	
	/**
	 * 导入excel解析
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/importExcel")
	@ResponseBody
	public JSONObject importExcel(HttpServletRequest request,HttpServletResponse response){
		
		log.info("[xzfy - OrganPersonController - importExcel] enter controller.");
		Response resp = null;
		JSONObject json = null;
		InputStream in =null; 
		FileInputStream fi = null;
		
		try{
			//转换request，解析出request中的文件
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			
			// 获取文件map集合
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			
			//MultipartFile file = multipartRequest.getFile("upload");
			// 循环遍历，取出单个文件
	        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
	            // 获取单个文件
	            MultipartFile file = entity.getValue();
	            //判空
				if (null != file && !file.isEmpty()){
					in = file.getInputStream();
					//强制类型转化
					if (in instanceof FileInputStream) {
						fi = (FileInputStream) in;
					}
					else{
						resp = new Response(ConstCode.SYSTEM_ERROR, "请求失败");
						return json;
					}
					List<Map<String,Object>> list = ImportExcelUtils.readExcel(fi, 3, 0, 0);
					System.out.println(list.size());
					
					//数据入库
					
					//设置返回参数
					resp = new Response(ConstCode.SUCCESS_CODE, "请求成功");
				}
				else{
					resp = new Response(ConstCode.SYSTEM_ERROR, "上传为空");
				}
	        }

		}
		catch(Exception e){
			
			log.info("[xzfy - OrganPersonController - importExcel] error="+e);
			resp = new Response(ConstCode.SYSTEM_ERROR, "请求失败");
		}
		finally{
			
			json = JSONObject.fromObject(resp);
			log.info("[xzfy - OrganPersonController - importExcel] controller end.");
		}
		return json;
	}
	
}
