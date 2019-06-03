var areaToLevelId = "";
var areaId = "";
function doInit(){
	if(id!=0){//编辑
		var json = tools.requestJsonRs("/SuperviseController/get.action",{id:id});
 		bindJsonObj2Easyui(json.rtData , "form1");
 		//所属地区
 		var jsonArea = tools.requestJsonRs("/subjectCtrl/getOrgCtrl.action");
    	$('#administrativeDivision').textbox('setValue',jsonArea.rtData.name);
    	areaId = jsonArea.rtData.id;
 		//部门地区获取层级
 		var jsonlevel = tools.requestJsonRs("/subjectCtrl/areaToLevel.action",{id:json.rtData.administrativeDivision});
    	$('#deptLevel').textbox('setValue',jsonlevel.rtData.name);
    	areaToLevelId = jsonlevel.rtData.id;
    	
	}else{
		//获取所属地区
    	var jsonArea = tools.requestJsonRs("/subjectCtrl/getOrgCtrl.action");
    	$('#administrativeDivision').textbox('setValue',jsonArea.rtData.name);
    	areaId = jsonArea.rtData.id;
    	//部门地区获取主体层级
    	var jsonLeve = tools.requestJsonRs("/subjectCtrl/areaToLevel.action",{id:jsonArea.rtData.id});
    	$('#deptLevel').textbox('setValue',jsonLeve.rtData.name);
    	areaToLevelId = jsonLeve.rtData.id;
	}
	natureInit();
}

function save(){
	if($('#form1').form('enableValidation').form('validate')){	
		var param = tools.formToJson($("#form1"));
		//校验统一社会信用代码
		var text = /[^_IOZSVa-z\W]{2}\d{6}[^_IOZSVa-z\W]{10}/g;
		var flag = text.test(param.departmentCode);
		if(!flag){
			$.MsgBox.Alert_auto("统一社会信用代码输入不正确");
			return false;
		}
		//校验邮编
		if($("#postCode").val()!=""){
			var text = /^[0-9]{6}$/;
			var flag = text.test(param.postCode);
			if(!flag){
				$.MsgBox.Alert_auto("邮编输入不正确");
				return false;
			}
		}
		//校验电子邮箱
		if(param.mail !="" && param.mail !=null){
			var text = /(^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$)/;
			var flag = text.test(param.mail);
			if(!flag){
				$.MsgBox.Alert_auto("电子邮箱格式输入不正确");
				return false;
			}
		}
		//校验传真
		if($("#fax").val()!=""){
			var text = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
			var flag = text.test(param.fax);
			if(!flag){
				$.MsgBox.Alert_auto("传真输入不正确");
				return false;
			}
		}
		//校验联系电话
		if($("#phone").val()!=""){
			var text = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$|(^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$)/;
			var flag = text.test(param.phone);
			if(!flag){
				$.MsgBox.Alert_auto("联系电话输入不正确");
				return false;
			}
		}
		param.isDelete = 0;
		param.isExamine = 0;
		param.deptLevel = areaToLevelId;
		param.administrativeDivision = areaId;
		if(id!=0){//编辑
			var json = tools.requestJsonRs("/SuperviseController/update.action",param);
		    return json.rtState;
		}else{//新增
			var json = tools.requestJsonRs("/SuperviseController/save.action",param);
		    return json.rtState;
		}
	}else{
		return false;
		
	}
	
}

//通用的combobox处理方法
function ComboboxCommonProcess(obj){
	var values = $(obj).combobox("getValues");
	var getData = $(obj).combobox("getData");
	var valuesT = [];
	
	for(var i=0;i<values.length;i++){
		for(var ii=0;ii<getData.length;ii++){
			if(values[i]==getData[ii].id){
				valuesT.push(values[i]);
				break;
			}
		}
	}
	$(obj).combobox("setValues",valuesT);
}
//监督机关性质
function natureInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "DEPT_NATURE"});
	if(json.rtState) {
        $('#nature').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
    		panelHeight:'100px',
        });
    }
}
