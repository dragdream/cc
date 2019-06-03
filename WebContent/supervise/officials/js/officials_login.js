//页面初始化
function doInit(){
	var json = tools.requestJsonRs("/OfficialsCtrl/get.action",{id:id});
	$('#username').textbox('setValue',json.rtData.name);
}
//保存
function save(){
	if($('#form1').form('enableValidation').form('validate')){	
		var param = tools.formToJson($("#form1"));
		//校验账号只能为字母或者数字
		var text = /^[0-9a-zA-Z]*$/g;
		var flag = text.test(param.name);
		if(!flag){
			$.MsgBox.Alert_auto("账号只能由30位以内的数字和字母组成");
			return false;
		}
		var userJson = tools.requestJsonRs("/SuperviseController/doubleUser.action",{name:param.name});
		if(userJson.rtData != 0){
			$.MsgBox.Alert_auto("该账号已存在，请选择其他账号");
			return false;
		}
		param.id = id;
		param.isDelete = 0;
			var json = tools.requestJsonRs("/OfficialsCtrl/saveUser.action",param);
		    return json.rtState;
	}else{
		return false;
	}
}
