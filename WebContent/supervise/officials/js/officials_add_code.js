//初始化方法
function doInit(){
	codeTypeInit();
}
//执法证类型
function codeTypeInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "DOCUMENTS_TYPE"});
    if(json.rtState) {
        $('#codeType').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'auto',
        });
    }
}
//保存
function save(){
	if($('#form1').form('enableValidation').form('validate')){
		var param_code = tools.formToJson($("#form1"));
		//校验证件编号
		var text = /^[A-Za-z0-9]+$/;
		var flag = text.test(param_code.code);
		if(!flag){
			$.MsgBox.Alert_auto("证件编号输入不正确"); 
		}else{
			var state = 1;
		}
		//校验日期
		var oDate1 = new Date(param_code.codeBeginStr);
	    var oDate2 = new Date(param_code.codeEndStr);
	    if(oDate1.getTime() > oDate2.getTime()){
			$.MsgBox.Alert_auto("有限期（起）不能大于有限期（止）");
	    }else{
	    	var statee = 1;
	    }
		if(state == 1 && statee == 1){
			return param_code;
		}
	}
}