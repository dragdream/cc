function doInit(){
		console.log(id);
		initsubmitlawLevel();
 		var json = tools.requestJsonRs("/lawInfoController/get.action",{id:id});
 		console.log(json.rtData);
 		bindJsonObj2Easyui(json.rtData , "form1");
 		
 		//处理附带的附件信息
 		var attachModels = json.rtData.attachModels;
 		for(var i=0;i<attachModels.length;i++){
 			attachModels[i].priv = 1+2+4+8+16;
 			var attachElement = tools.getAttachElement(attachModels[i]);
 			$("#attachDiv").append(attachElement);
 		}
}
function initsubmitlawLevel(){
		var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "LAW_TYPE"});
	    if(json.rtState) {
	        json.rtData.unshift({codeNo: -1, codeName: "请选择"});
	        $('#submitlawLevel').combobox({
	            data: json.rtData,
	            valueField: 'codeNo',
	            textField: 'codeName',
	            onLoadSuccess:function(){
	                $('#submitlawLevel').combobox('setValue',-1);
	            },
	        });
	    }
	}
function save(){
	if($("#form1").valid()){
		var param = tools.formToJson($("#form1"));
		param.isDelete = 0;
		var json = tools.requestJsonRs("/lawInfoController/update.action",param);
		return json.rtState;
	}
}
	