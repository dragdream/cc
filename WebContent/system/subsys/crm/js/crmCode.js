
/**
 * 根据CRM主类编号  获取子集代码列表
 * 
 * @param codeNo 系统代码编号  主类编码
 * @param codeSelectId 对象Id
 * @returns 返回人员数组 对象 [{codeNo:'' , codeName:''}]
 */
function getCrmCodeByParentCodeNo(codeNo , codeSelectId ){
	var url =   contextPath + "/crmCode/getSysCodeByParentCodeNo.action";
	var para = {codeNo:codeNo};
	var jsonObj = tools.requestJsonRs(url ,para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		if(codeSelectId && $("#" + codeSelectId)[0]){//存在此对象
			var options = "";
			for ( var i = 0; i < prcs.length; i++) {
				options = options + "<option value='"+prcs[i].codeNo+"'>" + prcs[i].codeName + "</option>";
			}
			$("#" + codeSelectId).append(options);
		}
		return prcs;
	}else{
		alert(jsonObj.rtMsg);
	}
}
