function saveSurveyInfo(){
	if($("#common_case_survey_info").form('enableValidation').form('validate')){
		var params = tools.formToJson($("#common_case_survey_info"));
		console.log(params);
		return params;
	}
    return false;
}

function initSurveyInfo(){
	var index = $("#index").val();
	var json = top.params;
	delete top.params;
	bindJsonObj2Easyui(json, 'common_case_survey_info');
}


/*function saveSurveyInfo(){
	var id = $('#id').val();
	var caseId = $('#caseId').val();
	if($("#common_case_survey_info").form('enableValidation').form('validate')){
		var params = tools.formToJson($("#common_case_survey_info"));
		params.id = id;
		params.caseId = caseId;
		params.surveyDateStr = $('#surveyDateStr').datebox('getValue');
		var status = tools.requestJsonRs(contextPath + "/caseCommonBaseCtrl/saveSurveyInfo.action", params);
		if(status.rtState == true){
			$.MsgBox.Alert_auto("保存成功");
		} else {
			$.MsgBox.Alert_auto("保存失败");
		}
		return status.rtState;
	}else{
		return false;
	}
}*/