
var customerInfoArray = null;
/**
 * 查询客户信息 --绑定对象
 * @return
 * retArray:表单文本框数组 
 * @param callBackFunc 回调方法参数 
 */
function selectCustomerInfo(retArray ,callBackFunc) {
	customerInfoArray = retArray;
	var url = contextPath + "/system/subsys/crm/core/customer/query.jsp";
    if(callBackFunc){
	   url += "?callBackFunc=" + callBackFunc;
    }
    dialogChangesize(url, 900, 500);
}

var contractInfoArray = null;
/**
 * 查询合同信息 --绑定对象
 * @return
 * retArray:表单文本框数组 
 * @param callBackPara 回调方法参数 
 */
function selectContractInfo(retArray ,callBackFunc) {
	contractInfoArray = retArray;
	var url = contextPath + "/system/subsys/crm/core/contact/manage/query.jsp";
    if(callBackFunc){
	   url += "?callBackFunc=" + callBackFunc;
    }
    dialogChangesize(url, 850, 500);
}

var productInfoArray = null;
/**
 * 查询产品信息 --绑定对象
 * @return
 * retArray:表单文本框数组 
 * @param callBackPara 回调方法参数 
 */
function selectProductInfo(retArray ,callBackFunc) {
	productInfoArray = retArray;
	var url = contextPath + "/system/subsys/crm/core/product/manager/getProductList.jsp";
    if(callBackFunc){
	   url += "?callBackFunc=" + callBackFunc;
    }
    dialogChangesize(url, 800, 500);
}

/**
 * 执行回掉函数
 */
function trigger_callback(type, args ){
    if(typeof xparent == 'object' && typeof xparent[type] == 'function'){
    	xparent[type].apply(this, args );
        CloseWindow();
    }
}

/**
 * 获取省分
 */
function getCRMProvince( provinceId){
	var url = contextPath + "/chinaCityController/getProvinceList.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		if(!provinceId){
			provinceId = "provinceId";
		}
		var province = document.getElementById(provinceId);
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
function getCRMCity(province , city){
	
	if(!province){
		province = "province";
	}
	var province = document.getElementById('' + province).value;
	if(!city){
		city = "city";
	}
	var city = document.getElementById(city + '');
	city.length = 1;
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
