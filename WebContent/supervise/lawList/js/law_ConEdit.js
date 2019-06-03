function doInit(){
  		//console.log(json.rtData.deptId);
		var json = tools.requestJsonRs("/detailController/get.action", {id:id});
 		bindJsonObj2Easyui(json.rtData , "form1");
 		console.log(json.rtData);
}

function save(){
	if($("#form1").valid()){
		var param = tools.formToJson($("#form1"));
			param.isDelete = 0;
			param.lawId = lawId;
			param.lawName = lawName;
			var json = tools.requestJsonRs("/detailController/update.action",param);
			return json.rtState;
	}
}