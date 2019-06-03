
/**
 * 获取省
 */
function getProvince(){
	var url = contextPath + "/chinaCityController/getProvinceList.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var province = document.getElementById('province');
		var prcList = json.rtData;
		if(prcList.length){
			$.each(prcList,function(i,prc){
				var option = new Option(prc.cityName,prc.cityCode);
				province.options.add(option);
			});
			
		}
	}
}

/**
 * 获取市
 */
function getCity(){
	var province = document.getElementById('province').value;
	var city = document.getElementById('city');
	city.length = 1;
	var county = document.getElementById('county');
	county.length = 1;
	var url = contextPath + "/chinaCityController/getCityListByCode.action";
	var param={cityCode:province};
	var json = tools.requestJsonRs(url,param);
	if(json.rtState){
		var prcList = json.rtData;
		if(prcList.length){
			$.each(prcList,function(i,prc){
				var option = new Option(prc.cityName,prc.cityCode);
				city.options.add(option);
			});
			
		}
	}
	
}


/**
 * 获取县
 */
function getCounty(){
	var city = document.getElementById('city').value;
	var county = document.getElementById('county');
	county.length = 1;
	var url = contextPath + "/chinaCityController/getCountyListByCode.action";
	var param={cityCode:city};
	var json = tools.requestJsonRs(url,param);
	if(json.rtState){
		var prcList = json.rtData;
		if(prcList.length){
			$.each(prcList,function(i,prc){
				var option = new Option(prc.cityName,prc.cityCode);
				county.options.add(option);
			});
		}
	}
}



/**
 * 根据城市编号获取数据对象
 * @param cityCode
 * @returns {String}
 */
function getInfoByCityCode(cityCode){
	var url = contextPath + "/chinaCityController/getInfoByCityCode.action";
	var param = {cityCode:cityCode};
	var json = tools.requestJsonRs(url,param);
	var returnObj = "";
	if(json.rtState){
		returnObj = json.rtData;
	}
	return returnObj;
}



/**
 * 根据城市编号自动获取下一个
 * cityCode 城市编号
 * cityFlag 城市标识，1-省；2-市；3-县
 * inputObjId 绑定输入框id
 */
function getAutoNumber(cityCode,cityFlag,inputObjId){
	var url = contextPath + "/chinaCityController/getAutoNumberByCityCode.action";
	var para = {cityCode : cityCode,cityFlag:cityFlag};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		var autoNumber = prc.autoNumber;
		$("#" + inputObjId).val(autoNumber);
	} else {
		alert(jsonObj.rtMsg);
	}
}




