package com.beidasoft.xzzf.punish.document.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.document.bean.DocArticlesDetail;
import com.beidasoft.xzzf.punish.document.bean.DocArticlesMain;
import com.beidasoft.xzzf.punish.document.service.ArticlesDetailService;
import com.beidasoft.xzzf.punish.manage.bean.PunishSealseizure;
import com.beidasoft.xzzf.punish.manage.service.PunishSealseizureService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/articlesDetailController")
public class ArticlesDetailController {
	
	@Autowired
	ArticlesDetailService articlesDetailService;
	@Autowired
	PunishSealseizureService punishSealseizureService;
	/**
	 * 根据主表id查询附属子表
	 * @param mainId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getByBaseId")
	public TeeJson getByBaseId(String id){
		TeeJson json = new TeeJson();
		PunishSealseizure sealseizure = punishSealseizureService.loadById(id);
		List<DocArticlesDetail> list = new ArrayList<>();
		//物品清单主表
		if (!"".equals(sealseizure.getArticlesMainId())) {
			String[] articlesStr = sealseizure.getArticlesMainId().split(",");
			for(int i = 0; i < articlesStr.length; i++) {
				list.addAll(articlesDetailService.getByBaseId(articlesStr[i]));
			}
		}
		json.setRtData(list);
		json.setRtState(true);
		return json;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/updateADetail")
	public TeeJson updateADetail(DocArticlesDetail newDetail){
		TeeJson json = new TeeJson();
		String id = newDetail.getId();
		DocArticlesDetail oldDetail = articlesDetailService.getObjById(id);
		oldDetail.setGoodsIsAppraisal(newDetail.getGoodsIsAppraisal());
		oldDetail.setGoodsAppraisalSum(newDetail.getGoodsAppraisalSum());
		oldDetail.setGoodsAddress(newDetail.getGoodsAddress());
		articlesDetailService.update(oldDetail);
		json.setRtState(true);
		json.setRtMsg("修改成功");
		return json;
	}
	
}
