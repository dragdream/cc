var batchId = $("#batchId").val();// 批次ID

/**
 * 页面初始化
 */
function doInit(){
	inputValidate();// 数据校验
}

/**
 * 数据校验
 */
function inputValidate(){
	$("#batch_add_auto_form").form({novalidate:true});
	$("#caseNum").textbox({novalidate:true, required:true, validType:['length[0,11]','number'], missingMessage:'请填写抽取数量' });
}

/**
 * 保存
 */
function save(){
	if($("#batch_add_auto_form").form('enableValidation').form('validate')){
		return $("#caseNum").val();
	}
	return false;
}