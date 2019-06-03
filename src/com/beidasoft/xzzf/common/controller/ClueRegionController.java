package com.beidasoft.xzzf.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.common.bean.Region;
import com.beidasoft.xzzf.common.service.ClueRegionService;

@Controller
@RequestMapping("clueRegionController")
public class ClueRegionController {

	@Autowired
	private ClueRegionService regionService;
	
	@ResponseBody
	@RequestMapping("provinceList")
	public List<Region> findProviceList(){
		List<Region> proviceList = regionService.findProviceList();
			return proviceList;
	}
	
	@ResponseBody
	@RequestMapping("cityList")
	public List<Region> findCityList(int provinceId){
		List<Region> cityList = regionService.findCityList(provinceId);
			return cityList;
	}
	
	@ResponseBody
	@RequestMapping("districtList")
	public List<Region> findDistrictList(int cityId){
		List<Region> districtList = regionService.findDistrictList(cityId);
			return districtList;
	}
}
