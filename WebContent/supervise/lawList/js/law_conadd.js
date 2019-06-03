function doInit(){
	if(id!=0){//编辑
		var json = tools.requestJsonRs("/detailController/get.action", {id:id});
 		bindJsonObj2Easyui(json.rtData , "form1");
	}
}
function save(){
	if($('#form1').form('enableValidation').form('validate')){
		var param = tools.formToJson($("#form1"));
		//章
		var text = /^\d*$/;
		var flag = text.test(param.detailChapter);
		if(!flag){
			$.MsgBox.Alert_auto("章输入不正确！"); 
			return false;
		}
		//条
//		var text = /^\d*$/;
		var flag = text.test(param.detailStrip);
		if(!flag){
			$.MsgBox.Alert_auto("条输入不正确！"); 
			return false;
		}
		//款
//		var text = /^\d*$/;
		var flag = text.test(param.detailFund);
		if(!flag){
			$.MsgBox.Alert_auto("款输入不正确！"); 
			return false;
		}
		//项
//		var text = /^\d*$/;
		var flag = text.test(param.detailItem);
		if(!flag){
			$.MsgBox.Alert_auto("项输入不正确！"); 
			return false;
		}
		//目
//		var text = /^\d*$/;
		var flag = text.test(param.detailCatalog);
		if(!flag){
			$.MsgBox.Alert_auto("目输入不正确！"); 
			return false;
		}
		param.isDelete = 0;
		param.lawId = lawId;
		if(id!=0){//编辑
    		var json = tools.requestJsonRs("/detailController/update.action",param);
		    return json.rtState;
		}else{//新增
			var json = tools.requestJsonRs("/detailController/save.action",param);
			return json.rtState;
		}
			
	}
}