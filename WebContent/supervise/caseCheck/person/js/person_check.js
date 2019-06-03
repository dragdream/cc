/*初始化*/
var datagrid;
function doInit() {
	//撤销原因
	revockreasonIdInit();
}
//撤销原因
function revockreasonIdInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "REVOCKREASON"});
    if(json.rtState) {
        $('#revockreasonId').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'100px'
        });
    }
}
function save(){
	if($('#form1').form('enableValidation').form('validate')){
		var param = tools.formToJson($("#form1"));
		param.id = id;
		var json = tools.requestJsonRs("/casecheckPersonCtrl/revoke.action",param);
	    return json.rtState;
	}
}