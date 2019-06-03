package com.beidasoft.xzzf.clue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.clue.bean.ClueInformer;
import com.beidasoft.xzzf.clue.service.ClueInformerService;

@Controller
@RequestMapping("clueInformerController")
public class ClueInformerController {

	@Autowired
	private ClueInformerService informerService;
	
	/**
	 * 根据线索id（clueId）获取举报人List
	 * @param clueId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getByClueId")
	public List<ClueInformer> getByClueId(String clueId){
		
		List<ClueInformer> informerList = informerService.getByClueId(clueId);
		
		return informerList;
	}
}
