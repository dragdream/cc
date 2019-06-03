package com.tianee.oa.core.general.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.general.bean.TeeSysCode;
import com.tianee.oa.core.general.service.TeeSysCodeService;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;


@Controller
@RequestMapping("/sysCode")
public class TeeSysCodeController  extends BaseController{
	@Autowired
	TeeSysCodeService sysCodeServ;

	/**
	 * 新增或者更新参数
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdateSysPara")
	@ResponseBody
	public TeeJson addUpdateSysCode(HttpServletRequest request, TeeSysCode para)
			{
		TeeJson json = sysCodeServ.addUpdatePara(para, request);
		return json;
	}

	
	/**
	 * 获取主编码
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSysPara.action")
	@ResponseBody
	public TeeJson getSysPara(HttpServletRequest request)
			throws Exception {
	    TeeJson json = new TeeJson();
    	List<TeeSysCode> para = sysCodeServ.getSysPara();
    	//TeeSysCode code = para.get(6);
    	//System.out.println(para);
    	json.setRtData(para);
    	json.setRtState(true);
		json.setRtMsg("查询成功！");
		
		return json;
	}
	
	/**
	 * 根据主编码的编码  获取所有下级所有 代码数据
	 * @author syl
	 * @date 2014-3-1
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSysParaByParent")
	@ResponseBody
	public TeeJson getSysParaByParent(HttpServletRequest request ) throws Exception {
		TeeJson json = new TeeJson();
		TeeSysCode code = new TeeSysCode();
		int parentId = TeeStringUtil.getInteger(request.getParameter("parentId") , 0);
		code.setParentId(parentId);
		List<TeeSysCode> list = sysCodeServ.getSysParaByParent(code);
		json.setRtData(list);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据主编码的编号  获取所有下级所有 代码数据
	 * @author syl
	 * @date 2014-3-1
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSysCodeByParentCodeNo")
	@ResponseBody
	public TeeJson getSysCodeByParentCodeNo(HttpServletRequest request ) throws Exception {
		TeeJson json = new TeeJson();
		String parentCodeNo = TeeStringUtil.getString(request.getParameter("codeNo"));
		List<Map<String, Object>> list = TeeSysCodeManager.getChildSysCodeListByParentCodeNo(parentCodeNo);
		json.setRtData(list);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据主编号代码和当前编号代码 获取子代码名称
	 * @author syl
	 * @date 2014-3-1
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSysCodeNameByParentCodeNo")
	@ResponseBody
	public TeeJson getSysCodeNameByParentCodeNo(HttpServletRequest request ) throws Exception {
		TeeJson json = new TeeJson();
		String parentCodeNo = TeeStringUtil.getString(request.getParameter("parentCodeNo"));
		String codeNo = TeeStringUtil.getString(request.getParameter("codeNo"));
		List<Map<String, Object>> list = TeeSysCodeManager.getChildSysCodeListByParentCodeNo(parentCodeNo);
		for(Map data:list){
			if(data.get("codeNo").toString().equals(codeNo)){
				json.setRtData(data.get("codeName"));
				json.setRtState(true);
				return json;
			}
		}
		json.setRtData("");
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 查询 byId
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getSysMenuById(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		int  sid = TeeStringUtil.getInteger(request.getParameter("sid") , 0);
		TeeSysCode code = sysCodeServ.getById(sid);
		if(code == null){
			json.setRtState(false);
			json.setRtMsg("此编码已被删除！");
			return json;
		}
		json.setRtData(code);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 删除  子代码
	 * 
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delById")
	@ResponseBody
	public TeeJson delPara(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		int  sid = TeeStringUtil.getInteger(request.getParameter("sid") , 0);
		sysCodeServ.deleteById(sid);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 删除主编码
	 * @author syl
	 * @date 2014-3-1
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delMainCode")
	@ResponseBody
	public TeeJson delMainCode(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		int  sid = TeeStringUtil.getInteger(request.getParameter("sid") , 0);
		sysCodeServ.delByParentId(sid);
		json.setRtState(true);
		return json;
	}

	
	@RequestMapping("/getSysParaByParentCode")
    @ResponseBody
    public TeeJson getSysParaByParentCode(String parentCodeNo, String codeNo) throws Exception {
    	TeeJson json = new TeeJson();
        List<TeeSysCode> sysCodes = sysCodeServ.getSysParaByParentCode(parentCodeNo, codeNo);
        json.setRtState(true);
        json.setRtData(sysCodes);
        return json;
    }
}
