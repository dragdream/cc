package com.beidasoft.xzzf.punish.document.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.document.bean.DocArticlesDetail;
import com.beidasoft.xzzf.punish.document.bean.DocArticlesMain;
import com.beidasoft.xzzf.punish.document.model.ArticlesMainModel;
import com.beidasoft.xzzf.punish.document.service.ArticlesDetailService;
import com.beidasoft.xzzf.punish.document.service.ArticlesMainService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/articlesMainCtrl")
public class ArticlesMainController {

	@Autowired
	private ArticlesDetailService articlesDetailService;
	
	@Autowired
	private ArticlesMainService articlesMainService;
	
	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private CommonService commonService;
	/**
	 * 保存物品清单
	 * 
	 * @param docArticlesMainModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson save(ArticlesMainModel docArticlesMainModel, HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 实例化实体类对象
		DocArticlesMain docArticlesMain = new DocArticlesMain();
		// 属性值传递
		BeanUtils.copyProperties(docArticlesMainModel, docArticlesMain);
		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(docArticlesMainModel.getSiteLeaderDateStr())) {
			docArticlesMain.setSiteLeaderDate(TeeDateUtil.format(docArticlesMainModel.getSiteLeaderDateStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(docArticlesMainModel.getLawUnitDateStr())) {
			docArticlesMain.setLawUnitDate(TeeDateUtil.format(docArticlesMainModel.getLawUnitDateStr(), "yyyy年MM月dd日"));
		}
		// 设置创建人相关信息
		if (StringUtils.isBlank(docArticlesMainModel.getId())) {
			docArticlesMain.setId(UUID.randomUUID().toString());
			docArticlesMain.setCreateUserId(user.getUserId());
			docArticlesMain.setCreateUserName(user.getUserName());
			docArticlesMain.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建物品清单");
		} else {
			//id不为空删除字表数据
			articlesDetailService.del(docArticlesMain.getId());
		}
		//设置创建人相关信息
		docArticlesMain.setUpdateUserId(user.getUserId());
		docArticlesMain.setUpdateUserName(user.getUserName());
		docArticlesMain.setUpdateTime(Calendar.getInstance().getTime());
		//添加文书操作日志
		commonService.writeLog(request, "修改物品清单");
		
		// 前台传来的子表信息
		JSONArray dataArray = JSONArray.fromObject(docArticlesMainModel.getDataArray());
		
		int sums = 0;
		for (int i = 0; i < dataArray.size(); i++) {
				DocArticlesDetail docArticlesDetail = new DocArticlesDetail();
				JSONObject object = dataArray.getJSONObject(i);
				docArticlesDetail.setGoodsCode(Integer.parseInt(object.getString("goodsCode")));
				docArticlesDetail.setGoodsName(object.getString("goodsName"));
				docArticlesDetail.setGoodsUnit(object.getString("goodsUnit"));
				int sum = 0;
				if (!"".equals(object.getString("goodsSum"))) {
					sum = Integer.parseInt(object.getString("goodsSum"));
				}
				docArticlesDetail.setGoodsSum(sum);
				docArticlesDetail.setGoodsRemark(object.getString("goodsRemark"));
				docArticlesDetail.setId(UUID.randomUUID().toString());
				docArticlesDetail.setMainId(docArticlesMain.getId());
				
				articlesDetailService.save(docArticlesDetail);
				sums += sum;
		}
		docArticlesMain.setGoodsSums(sums);	
		docArticlesMain.setDelFlg("0");
		// 保存 物品清单
		articlesMainService.save(docArticlesMain,request);

		json.setRtData(docArticlesMain);
		json.setRtState(true);

		return json;
	}
	
	/**
	 * 获取物品清单（通过主键ID）
	 * @param inspectionRecord
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson get(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		DocArticlesMain docArticlesMain = articlesMainService.getById(id);
		ArticlesMainModel docArticlesMainModel = new ArticlesMainModel();
		
		BeanUtils.copyProperties(docArticlesMain, docArticlesMainModel);

		// 单独处理时间类型转换
		if (docArticlesMain.getSiteLeaderDate() != null) {
			docArticlesMainModel.setSiteLeaderDateStr(TeeDateUtil.format(docArticlesMain.getSiteLeaderDate(), 
					"yyyy年MM月dd日"));
		}
		if (docArticlesMain.getLawUnitDate() != null) {
			docArticlesMainModel.setLawUnitDateStr(TeeDateUtil.format(docArticlesMain.getLawUnitDate(), 
					"yyyy年MM月dd日"));
		}

		//获取物品清单子表数据
		List<DocArticlesDetail> listDocArticlesDetail = articlesDetailService.getByBaseId(id);
		String joo = "[";
		JSONObject jo = null;
		for(DocArticlesDetail dad:listDocArticlesDetail) {
			jo = new JSONObject();
			jo.put("序号", dad.getGoodsCode());
			jo.put("物品名称", dad.getGoodsName());
			jo.put("计量单位", dad.getGoodsUnit());
			jo.put("数量", dad.getGoodsSum());
			jo.put("备注", dad.getGoodsRemark());
			
			joo += jo.toString()+",";
		}
		joo = joo.substring(0, joo.length()-1);
		joo += "]";
		docArticlesMainModel.setDataArray(joo);
		docArticlesMainModel.setDocArticlesDetails(listDocArticlesDetail);
		
		// 返回物品清单表 json 对象
		json.setRtData(docArticlesMainModel);
		json.setRtState(true);

		return json;
	}
	
	

	/**
	 * 根据环节ID和 baseId查找物品清单
	 * @param articlesMainModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGoodsInfo")
	public TeeJson get(ArticlesMainModel articlesMainModel, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		List<DocArticlesMain> listArticlesMain = articlesMainService.getByBaseId(articlesMainModel);
		json.setRtData(listArticlesMain);
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
	public TeeJson insptnrcdpsnDoc(ArticlesMainModel articlesMainModel, HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = articlesMainModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId, content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/bindDocInfo")
	public TeeJson bind(String id) {
		TeeJson json = new TeeJson();
		DocArticlesMain docArticlesMain = articlesMainService.getById(id);
		json.setRtData(docArticlesMain);
		json.setRtState(true);
		return json;
	}
}
