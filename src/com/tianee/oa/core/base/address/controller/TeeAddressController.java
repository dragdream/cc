package com.tianee.oa.core.base.address.controller;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.base.address.bean.TeeAddress;
import com.tianee.oa.core.base.address.service.TeeAddressService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.oaconst.TeeConst;

import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.file.TeeCSVUtil;
import com.tianee.webframe.util.file.TeeVcfUtil;

import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/teeAddressController")
public class TeeAddressController {

	@Autowired
	private TeeAddressService addressService;
	
	/**
	 * groupId 为 0 时候 为默认组
	 * @author zhp
	 * @throws ParseException 
	 * @createTime 2013-12-2
	 * @editTime 下午09:46:07
	 * @desc
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getAddresesByGroupId")
	@ResponseBody
	public TeeEasyuiDataGridJson getAddressByGroupId(TeeDataGridModel dm,HttpServletRequest request) throws ParseException {
			
			String isPub = TeeStringUtil.getString(request.getParameter("isPub"),"0");//0 不是公共通讯薄 1 是公共通讯薄
			TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			int groupId =TeeStringUtil.getInteger(request.getParameter("groupId"), -1) ;
			String seqIds = TeeStringUtil.getString(request.getParameter("seqIds"),"");
			String sex = TeeStringUtil.getString(request.getParameter("sex"),"");
			String nickName = TeeStringUtil.getString(request.getParameter("nickName"),"");
			String telNoDept = TeeStringUtil.getString(request.getParameter("telNoDept"),"");
			String telNoHome = TeeStringUtil.getString(request.getParameter("telNoHome"),"");
			String mobilNo = TeeStringUtil.getString(request.getParameter("mobilNo"),"");
			String psnName = TeeStringUtil.getString(request.getParameter("psnName"),"");
			//生日
			String birthday = TeeStringUtil.getString(request.getParameter("birthday"),"");
			String deptName = TeeStringUtil.getString(request.getParameter("deptName"),"");
			String addDept = TeeStringUtil.getString(request.getParameter("addDept"),"");
			String addHome = TeeStringUtil.getString(request.getParameter("addHome"),"");
			String notes = TeeStringUtil.getString(request.getParameter("notes"),"");
			
			
			Map paraMap = new HashMap<String, String>();
		    paraMap.put("seqIds", seqIds);
		    paraMap.put("sex", sex);
		    paraMap.put("nickName", nickName);
			
		    paraMap.put("telNoDept", telNoDept);
		    paraMap.put("telNoHome", telNoHome);
		    paraMap.put("mobilNo", mobilNo);
		    paraMap.put("psnName", psnName);
		    paraMap.put("deptName", deptName);
		    paraMap.put("addDept", addDept);
		    paraMap.put("addHome", addHome);
		    paraMap.put("notes", notes);
		    paraMap.put("isPub", isPub);
		    paraMap.put("birthday",birthday);
		    
		    
		return addressService.getAddressByGroupId(dm, groupId, paraMap,loginPerson);
		
	}

	/**
	 * 新建通讯录
	 * @param request
	 * @return
	 */
	@RequestMapping("/addAddress")
	@ResponseBody
	public TeeJson addAddress(HttpServletRequest request){
		int isPub =TeeStringUtil.getInteger(request.getParameter("isPub"), 0);//0 不是公共通讯薄 1 是公共通讯薄
		
		int psnNo =TeeStringUtil.getInteger(request.getParameter("psnNo"), 0);
		String psnName = TeeStringUtil.getString(request.getParameter("psnName"),"");
		int groupId =TeeStringUtil.getInteger(request.getParameter("groupId"), 0);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String sex = TeeStringUtil.getString(request.getParameter("sex"),"0");
		Date birthday = TeeStringUtil.getDate(request.getParameter("birthday"),"yyyy-MM-dd");
		String ministration = TeeStringUtil.getString(request.getParameter("ministration"),"");
		String mate = TeeStringUtil.getString(request.getParameter("mate"),"");
		String child = TeeStringUtil.getString(request.getParameter("child"),"");
		String deptName = TeeStringUtil.getString(request.getParameter("deptName"),"");
		String addDept = TeeStringUtil.getString(request.getParameter("addDept"),"");
		String postNoDept = TeeStringUtil.getString(request.getParameter("postNoDept"),"");
		String telNoDept = TeeStringUtil.getString(request.getParameter("telNoDept"),"");
		String faxNoDept = TeeStringUtil.getString(request.getParameter("faxNoDept"),"");
		String addHome = TeeStringUtil.getString(request.getParameter("addHome"),"");
		String postNoHome = TeeStringUtil.getString(request.getParameter("postNoHome"),"");
		String telNoHome = TeeStringUtil.getString(request.getParameter("telNoHome"),"");
		String mobilNo = TeeStringUtil.getString(request.getParameter("mobilNo"),"");
		String bpNo = TeeStringUtil.getString(request.getParameter("bpNo"),"");
		String email = TeeStringUtil.getString(request.getParameter("email"),"");
		String oicqNo = TeeStringUtil.getString(request.getParameter("oicqNo"),"");
		String icqNo = TeeStringUtil.getString(request.getParameter("icqNo"),"");
		String notes = TeeStringUtil.getString(request.getParameter("notes"),"");
		String nickName=TeeStringUtil.getString(request.getParameter("nickName"),"");
		TeeAddress address = new TeeAddress();
		address.setPsnNo(psnNo);
		address.setPsnName(psnName);
		address.setGroupId(groupId);
		address.setSex(sex);
		address.setBirthday(birthday);
		address.setMinistration(ministration);
		address.setMate(mate);
		address.setChild(child);
		address.setDeptName(deptName);
		address.setAddDept(addDept);
		address.setPostNoDept(postNoDept);
		address.setTelNoDept(telNoDept);
		address.setFaxNoDept(faxNoDept);
		address.setAddHome(addHome);
		address.setPostNoHome(postNoHome);
		address.setTelNoHome(telNoHome);
		address.setMobilNo(mobilNo);
		address.setBpNo(bpNo);
		address.setEmail(email);
		address.setOicqNo(oicqNo);
		address.setIcqNo(icqNo);
		address.setNotes(notes);
		address.setNickName(nickName);
		if(isPub == 0){
			address.setUserId(String.valueOf(loginPerson.getUuid()));
		}
		
		addressService.addAddress(address);
		
		
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("添加通讯簿成功!");
		return json;
	}
	
	@RequestMapping("/updateAddress")
	@ResponseBody
	public TeeJson updateAddress(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int aid =TeeStringUtil.getInteger(request.getParameter("id"), 0);
		
		int psnNo =TeeStringUtil.getInteger(request.getParameter("psnNo"), 0);
		String psnName = TeeStringUtil.getString(request.getParameter("psnName"),"");
		int groupId =TeeStringUtil.getInteger(request.getParameter("groupId"), 0);
		
		String sex = TeeStringUtil.getString(request.getParameter("sex"),"0");
		Date birthday = TeeStringUtil.getDate(request.getParameter("birthday"),"yyyy-MM-dd");
		String ministration = TeeStringUtil.getString(request.getParameter("ministration"),"");
		String mate = TeeStringUtil.getString(request.getParameter("mate"),"");
		String child = TeeStringUtil.getString(request.getParameter("child"),"");
		String deptName = TeeStringUtil.getString(request.getParameter("deptName"),"");
		String nickName = TeeStringUtil.getString(request.getParameter("nickName"),"");
		String addDept = TeeStringUtil.getString(request.getParameter("addDept"),"");
		String postNoDept = TeeStringUtil.getString(request.getParameter("postNoDept"),"");
		String telNoDept = TeeStringUtil.getString(request.getParameter("telNoDept"),"");
		String faxNoDept = TeeStringUtil.getString(request.getParameter("faxNoDept"),"");
		String addHome = TeeStringUtil.getString(request.getParameter("addHome"),"");
		String postNoHome = TeeStringUtil.getString(request.getParameter("postNoHome"),"");
		String telNoHome = TeeStringUtil.getString(request.getParameter("telNoHome"),"");
		String mobilNo = TeeStringUtil.getString(request.getParameter("mobilNo"),"");
		String bpNo = TeeStringUtil.getString(request.getParameter("bpNo"),"");
		String email = TeeStringUtil.getString(request.getParameter("email"),"");
		String oicqNo = TeeStringUtil.getString(request.getParameter("oicqNo"),"");
		String icqNo = TeeStringUtil.getString(request.getParameter("icqNo"),"");
		String notes = TeeStringUtil.getString(request.getParameter("notes"),"");
		TeeAddress address = null;
		address = addressService.getAddressById(aid);
		if(address != null){
			address.setPsnNo(psnNo);
			address.setPsnName(psnName);
			address.setGroupId(groupId);
			address.setSex(sex);
			address.setNickName(nickName);
			address.setBirthday(birthday);
			address.setMinistration(ministration);
			address.setMate(mate);
			address.setChild(child);
			address.setDeptName(deptName);
			address.setAddDept(addDept);
			address.setPostNoDept(postNoDept);
			address.setTelNoDept(telNoDept);
			address.setFaxNoDept(faxNoDept);
			address.setAddHome(addHome);
			address.setPostNoHome(postNoHome);
			address.setTelNoHome(telNoHome);
			address.setMobilNo(mobilNo);
			address.setBpNo(bpNo);
			address.setEmail(email);
			address.setOicqNo(oicqNo);
			address.setIcqNo(icqNo);
			address.setNotes(notes);
			try {
				addressService.updateAddress(address);
				json.setRtState(true);
				json.setRtMsg("编辑通讯簿成功!");
			} catch (Exception e) {
				e.printStackTrace();
				json.setRtState(false);
				json.setRtMsg("编辑通讯簿成功!");
			}
			
		}else{
			json.setRtState(false);
			json.setRtMsg("编辑通讯簿成功!");
		}
		return json;
	}
	
	/**
	 * 删除 通讯薄
	 * @author zhp
	 * @createTime 2013-12-3
	 * @editTime 上午12:27:17
	 * @desc
	 */
	@RequestMapping("/delAddress")
	@ResponseBody
	public TeeJson delAddress(HttpServletRequest request){
		int sid =TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeJson json = new TeeJson();
		try {
			addressService.delAddress(sid);
			json.setRtState(true);
			json.setRtMsg("删除成功");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("删除失败");
			e.printStackTrace();
		}
		return json;
	}


	/**
	 * 批量删除
	 * @author syl
	 * @date 2014-3-16
	 * @param request
	 * @return
	 */
	@RequestMapping("/delAddressByIds")
	@ResponseBody
	public TeeJson delAddressByIds(HttpServletRequest request){
		String sid =TeeStringUtil.getString(request.getParameter("sid"), "");
		TeeJson json = addressService.delAddressByIds(sid);
		return json;
	}
	
	
	
	
	/**
	 * 个人通讯簿 按照姓氏索引 
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午08:27:42
	 * @desc
	 */
	@RequestMapping("/getAddressLastName")
	@ResponseBody
	public TeeJson getAddressLastName(HttpServletRequest request){
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List rtData = null;
		TeeJson json = new TeeJson();
		try {
			rtData = addressService.getAddressLastName(loginPerson);
			json.setRtState(true);
			json.setRtData(rtData);
			json.setRtMsg("查询（按照姓氏索引 ）通讯簿成功!");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("查询（按照姓氏索引 ）通讯簿失败!");
		}
		return json;
	}
	
	
	/**
	 * 个人通讯簿 按照姓氏索引 
	 * @author syl
	 * @createTime 2014-1-4
	 * @editTime 上午08:27:42
	 * @desc
	 */
	@RequestMapping("/getAddressFullNamList")
	@ResponseBody
	public TeeJson getAddressFullNamList(HttpServletRequest request){
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TreeMap rtData = null;
		TeeJson json = new TeeJson();
		int groupId =TeeStringUtil.getInteger(request.getParameter("groupId"), -1) ;
		String userName = TeeStringUtil.getString(request.getParameter("userName"));
		
		try {
			rtData = addressService.getAddressFullNamList(loginPerson , groupId , userName);
			json.setRtState(true);
			json.setRtData(rtData);
			json.setRtMsg("查询（按照姓氏索引 ）通讯簿成功!");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("查询（按照姓氏索引 ）通讯簿失败!");
		}
		return json;
	}
	
	@RequestMapping("/getAddressFullNamListByMobile")
	@ResponseBody
	public TeeJson getAddressFullNamListByMobile(HttpServletRequest request){
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		
		try {
			json.setRtState(true);
			json.setRtData(addressService.getAddressFullNamListByMobile(loginPerson));
		} catch (Exception e) {
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * 用户列表
	 * @author syl
	 * @createTime 2014-1-4
	 * @editTime 上午08:27:42
	 * @desc
	 */
	@RequestMapping("/getPersonAddress")
	@ResponseBody
	public TeeJson getPersonAddress(HttpServletRequest request){
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TreeMap rtData = null;
		TeeJson json = new TeeJson();
		String userName = TeeStringUtil.getString(request.getParameter("userName"));
		try {
			rtData = addressService.getPersonAddress(loginPerson , userName );
			json.setRtState(true);
			json.setRtData(rtData);
			json.setRtMsg("查询（按照姓氏索引 ）通讯簿成功!");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("查询（按照姓氏索引 ）通讯簿失败!");
		}
		return json;
	}
	
	
	
	
	/**
	 * 
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午08:28:11
	 * @desc
	 */
	@RequestMapping("/getPublicAddressLastName")
	@ResponseBody
	public TeeJson getPublicAddressLastName(HttpServletRequest request){
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List rtData = null;
		TeeJson json = new TeeJson();
		try {
			rtData = addressService.getPublicAddressLastName(loginPerson);
			json.setRtState(true);
			json.setRtData(rtData);
			json.setRtMsg("查询（按照姓氏索引 ）通讯簿成功!");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("查询（按照姓氏索引 ）通讯簿失败!");
		}
		return json;
	}
	
	@RequestMapping("/getAddressById")
	@ResponseBody
	public TeeJson getAddressById(HttpServletRequest request){
		int aid =TeeStringUtil.getInteger(request.getParameter("id"), 0);
		TeeJson json = new TeeJson();
		TeeAddress addre = null;
		try {
			addre = addressService.getAddressById(aid);
			json.setRtState(true);
			json.setRtData(addre);
			json.setRtMsg("查询通讯簿成功!");
		} catch (Exception e) {
			e.printStackTrace();
			json.setRtState(false);
			json.setRtMsg("查询通讯簿失败!");
		}
		return json;
	}
	
	/**
	 * 导出xls 
	 * @author zhp
	 * @createTime 2014-2-12
	 * @editTime 下午11:11:32
	 * @desc
	 */
	@RequestMapping("/exportAddressXls.action")
	public String exportAddressXls(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//获取当前登录人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		response.setCharacterEncoding("GBK");
		String isPub = TeeStringUtil.getString(request.getParameter("isPub"),"0");//0 不是公共通讯薄 1 是公共通讯薄
		boolean pub = false;
		if("1".equals(isPub)){
			pub = true;
		}
		int groupId =TeeStringUtil.getInteger(request.getParameter("groupId"), -1) ;
		try {
			String fileName = URLEncoder.encode("通讯簿组导出.csv", "UTF-8");
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ fileName + "\"");
			ArrayList<TeeDataRecord> addresses = addressService.exportAddressInfo(pub,groupId,loginUser);
			TeeCSVUtil.CVSWrite(response.getWriter(), addresses);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}
	
	
	/**
	 * 导出VCF
	 * @author zhp
	 * @createTime 2014-2-12
	 * @editTime 下午11:11:32
	 * @desc
	 */
	@RequestMapping("/exportAddressVcf.action")
	public String exportAddressVcf(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		response.setCharacterEncoding("GBK");
		String isPub = TeeStringUtil.getString(request.getParameter("isPub"),"0");//0 不是公共通讯薄 1 是公共通讯薄
		boolean pub = false;
		if("1".equals(isPub)){
			pub = true;
		}
		int groupId =TeeStringUtil.getInteger(request.getParameter("groupId"), -1) ;
		try {
			String fileName = URLEncoder.encode("通讯簿组导出.vcf", "UTF-8");
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setContentType("text/x-vcard");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ fileName + "\"");
			ArrayList<TeeDataRecord> addresses = addressService.exportAddressInfo(pub,groupId,loginUser);
			
			
			StringBuffer content = new StringBuffer();
			for(TeeDataRecord dr:addresses){
				content.append("BEGIN:VCARD\n");
				content.append("VERSION:2.1\n");
				content.append("N;CHARSET=UTF-8;ENCODING=QUOTED-PRINTABLE:;"+TeeVcfUtil.qpEncodeingUTF8(dr.getValueByName("姓名").toString())+";;;\n");
				content.append("FN;CHARSET=UTF-8;ENCODING=QUOTED-PRINTABLE:"+TeeVcfUtil.qpEncodeingUTF8(dr.getValueByName("姓名").toString())+"\n");
				content.append("TEL;CELL;PREF:"+dr.getValueByName("手机").toString()+"\n");
				content.append("END:VCARD\n");
			}
			
			response.getWriter().write(content.toString());
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}
	
	/**
	 * 导入通讯簿
	 * @param request
	 * @param response
	 */
	@RequestMapping("/importAddress")
	@ResponseBody
	public void importAddress(HttpServletRequest request,HttpServletResponse response){
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String isPub = multipartRequest.getParameter("isPub");
		String groupId = multipartRequest.getParameter("groupId");
		String ext = null;
		
		//获取文件输入流
		MultipartFile file = multipartRequest.getFile("file");
		ext = file.getOriginalFilename().split("\\.")[1];
		try {
			addressService.importAddress("0".equals(isPub)?false:true, Integer.parseInt(groupId), file.getInputStream(), ext,loginPerson);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			response.getWriter().write("<script>parent.uploadSuccess();</script>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 用于首页 对 通讯簿 模块的搜索 
	 * 在首页搜索 通讯薄 按照名字 匹配
	 * @author zhp
	 * @createTime 2014-2-19
	 * @editTime 下午08:58:49
	 * @desc
	 */
	@RequestMapping("/queryAddress2PageIndex")
	@ResponseBody
	public TeeJson queryAddress2PageIndex(HttpServletRequest request,HttpServletResponse response){
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		String psnName = TeeStringUtil.getString(request.getParameter("psnName"),"");//0 不是公共通讯薄 1 是公共通讯薄
		List list =null;
		try {
			list = addressService.queryAddress2PageIndex(loginPerson,psnName);
			json.setRtState(true);
			json.setRtData(list);
			json.setRtMsg("查询通讯簿成功!");
		} catch (Exception e) {
			e.printStackTrace();
			json.setRtState(false);
			json.setRtMsg("查询通讯簿失败!");
		}
		return json;
	}
	public TeeAddressService getAddressService() {
		return addressService;
	}

	public void setAddressService(TeeAddressService addressService) {
		this.addressService = addressService;
	}
	
	/**
	 * leiqisheng
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getColleagueList")
	@ResponseBody
	public TeeEasyuiDataGridJson getColleagueList(TeeDataGridModel dm,HttpServletRequest request) throws ParseException {
		TeeJson json = new TeeJson();
	   return addressService.getColleagueList(dm,request);
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getPersonById")
	@ResponseBody
	public TeeJson getPersonById(HttpServletRequest request){
		TeeJson json = new TeeJson();
	     String sid = TeeStringUtil.getString(request.getParameter("sid"));
		json.setRtData(addressService.getPersonById(Integer.parseInt(sid)));
		json.setRtState(true);
		return json;
	}
	

}
