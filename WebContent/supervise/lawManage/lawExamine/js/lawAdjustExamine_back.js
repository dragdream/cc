function doInit() {
	
}
function save(){
	if($('#form1').form('enableValidation').form('validate')){
		var param = tools.formToJson($("#form1"));
		param.id = id;
		var json = tools.requestJsonRs("/lawAdjustExamineCtrl/saveBack.action",param);
	    return json.rtState;
	}
}

function examineNotPass(){
	var finalParams = {
			id: id
	};
	var json = tools.requestJsonRs("/lawAdjustExamineCtrl/examineNotPass.action",finalParams);
	if(json.rtState){
		$.MsgBox.Alert_auto("保存成功！");
		return true;
	}else{
		$.MsgBox.Alert_auto("保存失败！");
		return false;
	}
}