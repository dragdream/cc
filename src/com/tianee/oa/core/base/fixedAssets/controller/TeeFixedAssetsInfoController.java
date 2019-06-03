package com.tianee.oa.core.base.fixedAssets.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.swetake.util.Qrcode;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsInfo;
import com.tianee.oa.core.base.fixedAssets.model.TeeFixedAssetsInfoModel;
import com.tianee.oa.core.base.fixedAssets.service.TeeFixedAssetsInfoService;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeFixedAssetsInfoController")
public class TeeFixedAssetsInfoController {
	@Autowired
	TeeFixedAssetsInfoService assetsInfoService;
	@Autowired
	@Qualifier("teeBaseUpload")
	private TeeBaseUpload upload;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@RequestMapping("/addAssetsInfo")
	@ResponseBody
	public TeeJson addAssetsInfo(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeFixedAssetsInfoModel assetsInfoModel = new TeeFixedAssetsInfoModel();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List attachments = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.assetsInfo);
		TeeServletUtility.requestParamsCopyToObject(request, assetsInfoModel);
		assetsInfoModel.setAttacheModels(attachments);
		assetsInfoService.addAssetsInfoModel(assetsInfoModel);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@RequestMapping("/editAssetsInfo")
	@ResponseBody
	public TeeJson editAssetsInfo(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeFixedAssetsInfoModel assetsInfoModel = new TeeFixedAssetsInfoModel();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List attachments = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.assetsInfo);
		TeeServletUtility.requestParamsCopyToObject(request, assetsInfoModel);
		assetsInfoModel.setAttacheModels(attachments);
		assetsInfoService.updateAssetsInfoModel(assetsInfoModel);
		json.setRtMsg("更新成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/delAssetsInfo")
	@ResponseBody
	public TeeJson delAssetsInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeFixedAssetsInfo assetsInfo = assetsInfoService.getById(sid);
		assetsInfoService.deleteAssetsInfo(assetsInfo);
		
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/getAssetsInfo")
	@ResponseBody
	public TeeJson getAssetsInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeFixedAssetsInfo assetsInfo = assetsInfoService.getById(sid);
		TeeFixedAssetsInfoModel model = new TeeFixedAssetsInfoModel();
		if(assetsInfo != null){
			BeanUtils.copyProperties(assetsInfo, model);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
			BeanUtils.copyProperties(assetsInfo, model);
			if(assetsInfo.getDept()!=null){
				model.setDeptId(assetsInfo.getDept().getUuid());
				model.setDeptName(assetsInfo.getDept().getDeptName());
			}else{
				model.setDeptName("");
			}
			if(assetsInfo.getKeeper()!=null){
				model.setKeeperId(assetsInfo.getKeeper().getUuid());
				model.setKeeperName(assetsInfo.getKeeper().getUserName());
			}else{
				model.setKeeperName("");
			}
			model.setTypeId(assetsInfo.getCategory().getSid());
			model.setTypeName(assetsInfo.getCategory().getName());
			if(assetsInfo.getUseUser() != null){//使用人
				model.setUseDeptName(assetsInfo.getUseUser().getDept().getDeptName());
			}else{
				model.setUseDeptName("");
			}
			int kindId = assetsInfo.getAssetKind();
			int addId = assetsInfo.getAddKind();
			int depreciation=assetsInfo.getDepreciation();
			if(kindId==1){
				model.setAssetKindDesc("资产");
			}else{
				model.setAssetKindDesc("费用");
			}
			if(addId==1){
				model.setAddKindDesc("购入不需安装的固定资产");
			}else if(addId==2){
				model.setAddKindDesc("购入需安装已完工的固定资产");
			}else if(addId==3){
				model.setAddKindDesc("其他单位转入的固定资产(新设备)");
			}else if(addId==4){
				model.setAddKindDesc("其他单位转入的固定资产(旧设备)");
			}else if(addId==5){
				model.setAddKindDesc("捐赠的固定资产");
			}else if(addId==6){
				model.setAddKindDesc("融资租赁的固定资产");
			}else{
				model.setAddKindDesc("固定资产盘盈");
			}
			if(depreciation==1){
				model.setDepreciationDesc("年限平均法（也称直线法）");
			}else if(depreciation==2){
				model.setDepreciationDesc("工作量法");
			}else if(depreciation==3){
				model.setDepreciationDesc("双倍余额递减法（加速折旧法）");
			}else{
				model.setDepreciationDesc("年数总合法（加速折旧法）");
			}
			if(assetsInfo.getValideTime()!=null){
				model.setValideTimeDesc(formatter.format(assetsInfo.getValideTime().getTime()));
			}else{
				model.setValideTimeDesc("");
			}
			if(assetsInfo.getReceiptDate()!=null){//发票日期
				model.setReceiptDateStr(formatter2.format(assetsInfo.getReceiptDate()));
			}else{
				model.setReceiptDateStr("");
			}
			List<TeeAttachment> attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.assetsInfo, String.valueOf(assetsInfo.getSid()));
			List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
			for(TeeAttachment attach:attaches){
				TeeAttachmentModel m = new TeeAttachmentModel();
				BeanUtils.copyProperties(attach, m);
				m.setUserId(attach.getUser().getUuid()+"");
				m.setUserName(attach.getUser().getUserName());
				m.setPriv(1+2+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
				attachmodels.add(m);
			}
			
			//状态
			String useState = TeeStringUtil.getString(model.getUseState() , "0");
			if(useState.equals("0")){
				model.setUseStateDesc("在库");
			}else if(useState.equals("1")){
				model.setUseStateDesc("使用中");
			}else if(useState.equals("2")){
				model.setUseStateDesc("维修中 ");
			}else if(useState.equals("3")){
				model.setUseStateDesc("已报废");
			}else if(useState.equals("4")){
				model.setUseStateDesc("已丢失");
			}
			model.setAttacheModels(attachmodels);
		}else{
			json.setRtMsg("该固定资产已被删除！");
			json.setRtState(false);
			return json;
		}

		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据Id获取固定资产信息
	 * @author syl
	 * @date 2014-6-7
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAssetsInfoById")
	@ResponseBody
	public TeeJson getAssetsInfoById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeFixedAssetsInfo assetsInfo = assetsInfoService.getById(sid);
		TeeFixedAssetsInfoModel model = new TeeFixedAssetsInfoModel();
		BeanUtils.copyProperties(assetsInfo, model);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		BeanUtils.copyProperties(assetsInfo, model);
		if(assetsInfo.getDept()!=null){
			model.setDeptId(assetsInfo.getDept().getUuid());
			model.setDeptName(assetsInfo.getDept().getDeptName());
		}
		if(assetsInfo.getKeeper()!=null){
			model.setKeeperId(assetsInfo.getKeeper().getUuid());
			model.setKeeperName(assetsInfo.getKeeper().getUserName());
		}
		
		if(assetsInfo.getCategory()!=null){
			model.setTypeId(assetsInfo.getCategory().getSid());
			model.setTypeName(assetsInfo.getCategory().getName());
		}
		if(assetsInfo.getValideTime()!=null){
			model.setValideTimeDesc(formatter.format(assetsInfo.getValideTime().getTime()));
		}
		if(assetsInfo.getReceiptDate()!=null){//发票日期
			model.setReceiptDateStr(formatter2.format(assetsInfo.getReceiptDate()));
		}
		List<TeeAttachment> attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.assetsInfo, String.valueOf(assetsInfo.getSid()));
		List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		for(TeeAttachment attach:attaches){
			TeeAttachmentModel m = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, m);
			m.setUserId(attach.getUser().getUuid()+"");
			m.setUserName(attach.getUser().getUserName());
			m.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
			attachmodels.add(m);
		}
		model.setAttacheModels(attachmodels);
		
		//状态
		String useState = TeeStringUtil.getString(model.getUseState() , "0");
		if(useState.equals("0")){
			model.setUseStateDesc("在库");
		}else if(useState.equals("1")){
			model.setUseStateDesc("使用中");
		}else if(useState.equals("2")){
			model.setUseStateDesc("维修中 ");
		}else if(useState.equals("3")){
			model.setUseStateDesc("已报废");
		}else if(useState.equals("4")){
			model.setUseStateDesc("已丢失");
		}

		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return assetsInfoService.datagrid(dm, requestDatas);
	}
	
	/**
	 * 折旧，单资产折旧
	 * @param request
	 * @return
	 */
	@RequestMapping("/depreciate")
	@ResponseBody
	public TeeJson depreciate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int assetId = TeeStringUtil.getInteger(request.getParameter("assetId"), 0);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		assetsInfoService.depreciate(assetId, loginUser);
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 折旧，单资产折旧
	 * @param request
	 * @return
	 */
	@RequestMapping("/depreciateBatch")
	@ResponseBody
	public TeeJson depreciateBatch(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		assetsInfoService.depreciateBatch(loginUser);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/reZhejiu")
	@ResponseBody
	public TeeJson reZhejiu(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		int assetId = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeFixedAssetsInfo  info = assetsInfoService.getById(assetId);
		//将残值设置为原值
		info.setAssetBal(info.getAssetVal());
		//将最后一次折旧时间设置为启用时间
		info.setLastDepreciation((Calendar)info.getValideTime().clone());
		assetsInfoService.updateAssetsInfo(info);
		//根据sid删除之前的折旧记录
		assetsInfoService.deleteAssetDeprecRecords(assetId);
		//调用折旧方法
		assetsInfoService.depreciate(info, loginUser);
		json.setRtMsg("重新折旧成功！");
		json.setRtState(true);
		return json;		
	}
	
	/**
	 * 固定资产  生成二维码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/qrCodeDownload")
	public void qrCodeDownload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//获取当前登录的用户
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    //获取会议主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		Qrcode rcode = new Qrcode();
		rcode.setQrcodeVersion(6); // 这个值最大40，值越大可以容纳的信息越多，够用就行了
		
		String url ="/system/mobile/phone/fixedAssets/detail/index.jsp?sid="+sid;
		byte[] content = null;
		content = url.getBytes("utf-8");
		BufferedImage bufImg = new BufferedImage(127, 127,
				BufferedImage.TYPE_INT_RGB); // 图片的大小
		Graphics2D gs = bufImg.createGraphics();
		gs.setBackground(Color.WHITE);
		gs.clearRect(0, 0, 127, 127);
		gs.setColor(Color.BLACK);

		// 输出内容> 二维码
		if (content.length > 0 && content.length < 800) {
			boolean[][] codeOut = rcode.calQrcode(content);
			for (int i = 0; i < codeOut.length; i++) {
				for (int j = 0; j < codeOut.length; j++) {
					if (codeOut[j][i]) {
						gs.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
					}
				}
			}
		} else {
		}
		gs.dispose();
		bufImg.flush();
		
		response.setHeader("Pragma", "no-cache");         
        response.setHeader("Cache-Control", "no-cache");         
        response.setDateHeader("Expires", 0);         
        response.setContentType("image/jpeg");         
		
        // 将图像输出到Servlet输出流中。         
        ServletOutputStream sos = response.getOutputStream();         
        ImageIO.write(bufImg, "jpeg", sos);         
        sos.close();         
	}	
}
