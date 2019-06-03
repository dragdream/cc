var caseId = $('#comm_case_add_execute_caseId').val(); // 案件ID
var editFlag = $('#common_case_add_execute_editFlag').val(); // 编辑标识（1新增，2修改，3查看）
var isNext = $('#common_case_add_execute_isNext').val();// tab页签标识（立案0,调查取证1,处罚决定2,处罚执行3,结案4）
var closedState = $('#common_case_add_execute_closedState').val();// 结案标志
var executeModelId = $('#common_case_add_execute_modelId').val();// 弹框modelId
/**
 * 默认加载方法
 */
function doInitExecute() {
	initClosedState();
	ctrlShowHide();
}
/**
 * 初始化结案类型
 */
function initClosedState() {
	var closedCaseReason = '';
	var transferOrgan = '';
	var params = {
		parentCodeNo : "COMMON_CLOSED_STATE",
		codeNo:""
	};
	var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action",params);
	$('#closedState').combobox({
		data : result.rtData,
		valueField : 'codeNo',
		textField : 'codeName',
		panelHeight : 'auto',
		prompt : '请选择',
		onLoadSuccess : function() {
			if (closedState != null && closedState != '') {
				closedCaseReason = $('#closedCaseReason').combobox('getText');
				transferOrgan = $('#transferOrgan').combobox('getText');
				$('#closedState').combobox('setValue', closedState);
				var stateName = $('#closedState').combobox('getText');
				$('#stateName').text(stateName);
				ctrlShowHide(closedState,stateName);
			}
		},
		onChange : function(){
			var stateName = $('#closedState').combobox('getText');
			var stateValue = $('#closedState').combobox('getValue');
			$('#stateName').text(stateName);
			ctrlShowHide(stateValue,stateName);
			if(stateValue != closedState){
				$('#closedCaseReason').textbox('setText','');
				$('#transferOrgan').textbox('setText','');
			} else if(stateValue == closedState){
				$('#closedCaseReason').textbox('setText',closedCaseReason);
				$('#transferOrgan').textbox('setText',transferOrgan);
			}
		},
		editable : false
	});
}

function ctrlShowHide(stateValue,stateName){
	if(stateValue!=null && stateValue!=''){
		if(stateValue=='01'){
			$('#closedCaseInfo_tr').hide();
			$('#closedCaseReason').textbox({disabled:true,required:false });
			$('#transferOrgan_tr').hide();
			$('#transferOrgan').textbox({disabled:true,required:false });
		} else if(stateValue=='05'){
			$('#closedCaseInfo_tr').show();
			$('#closedCaseReason').textbox({disabled:false,validType:'length[0,400]', required:true, missingMessage:'请填写'+stateName+'原因' });
			$('#transferOrgan_tr').show();
			$('#transferOrgan').textbox({disabled:false,required:true, missingMessage:'请选择移送部门' });
		} else {
			$('#closedCaseInfo_tr').show();
			$('#closedCaseReason').textbox({disabled:false,validType:'length[0,400]', required:true, missingMessage:'请填写'+stateName+'原因' });
			$('#transferOrgan_tr').hide();
			$('#transferOrgan').textbox({disabled:true, required:false });
		}
	}
}

/**
 * 保存 结案
 * 
 * @returns
 */
function doExecuteSave(isSubmit) {
	if ($("#common_case_add_5_execute_form").form('enableValidation').form('validate')) {
		var params = tools.formToJson($("#common_case_add_5_execute_form"));
		params.id = caseId;
		params.isNext = 0; // 结案后，tabs页签回到立案阶段
		params.editFlag = parseInt(editFlag);
		params.grading = '05';
		params.currentState = '05';
		if (isSubmit == 0) {
			message = "保存成功";
		} else {
			message = "保存并提交成功";
		}
		var falseMessage = "";
		if (isSubmit == 0) {
			falseMessage = "保存失败";
		} else {
			falseMessage = "保存并提交失败";
		}
		params.isSubmit = isSubmit;
		var json = tools.requestJsonRs("/caseCommonBaseCtrl/saveFilingStage.action", params);
		if (json.rtState) {
			$.MsgBox.Alert_auto(message);
			parent.location.href = contextPath+"/caseCommonBaseCtrl/commonCaseIndex.action";
			// 保存成功 返回首页
			// window.parent.location.href =
			// contextPath+"/caseCommonBaseCtrl/commonCaseIndex.action";
			return json.rtState;
		} else {
			$.MsgBox.Alert_auto(falseMessage);
			return json.rtState;
		}
	} else {
		return false;
	}
}