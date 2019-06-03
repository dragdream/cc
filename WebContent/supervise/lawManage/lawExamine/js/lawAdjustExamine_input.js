/**
 * 法律法规调整上报填报js
 */

var _id = '';
var swfUploadObj = null;
function doInit() {
	_id = $('#lawAdjustReportid').val();

 	//多附件快速上传
	if(_id != null && _id !=''){
		var json = tools.requestJsonRs(contextPath + "/lawAdjustReportCtrl/getFilelistById.action",{id: _id});
		var attachModels = json.rtData;
		for(var i=0;i<attachModels.length;i++){
 		attachModels[i].priv = 2;
 		var attachElement = tools.getAttachElement(attachModels[i]);
 		$("#attachDiv").append(attachElement);
		}
	}	
}

function save(){
	if($("#form1").valid()){
		var param = tools.formToJson($("#form1"));
		var finalParams = {};
		if(param.controlType =='01'){
			finalParams = param;
			finalParams.updateLawId = '';
			finalParams.implementationStr = param.implementation;
			finalParams.promulgationStr = param.promulgation;
		}else if (param.controlType == '02'){
			finalParams = param;
			finalParams.implementationStr = param.implementation;
			finalParams.promulgationStr = param.promulgation;
		}else if (param.controlType == '03'){
			finalParams.controlType = param.controlType;
			finalParams.updateLawId = param.updateLawId;
		}
		finalParams.id = _id;
			var json = tools.requestJsonRs("/lawAdjustExamineCtrl/save.action",finalParams);
			if(json.rtState){
				$.MsgBox.Alert_auto("保存成功");
				return true;
			}else{
				$.MsgBox.Alert_auto("保存失败");
				return false;
			}
	}else{
		return false;
	}
}
//审核通过
function examinePass(){
	var finalParams = {
			id: _id
	};
	var json = tools.requestJsonRs("/lawAdjustExamineCtrl/examinePass.action",finalParams);
	if(json.rtState){
		$.MsgBox.Alert_auto("保存成功");
		return true;
	}else{
		$.MsgBox.Alert_auto("保存失败");
		return false;
	}
}

function examineNotPass(){
	var finalParams = {
			id: _id
	};
	var json = tools.requestJsonRs("/lawAdjustExamineCtrl/examineNotPass.action",finalParams);
	if(json.rtState){
		$.MsgBox.Alert_auto("保存成功");
		return true;
	}else{
		$.MsgBox.Alert_auto("保存失败");
		return false;
	}
}
 