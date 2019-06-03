var deptId = "";
//初始化方法
function doInit(){
	var json = tools.requestJsonRs("/casecheckPersonCtrl/getUserDept.action");
	$('#organizationName').textbox('setValue',json.rtData.organizationName);
	deptId = json.rtData.organizationId;
	personIdToSex();
	if(id!=0){//编辑
		console.log(id);
		var json = tools.requestJsonRs("/casecheckPersonCtrl/get.action",{id:id});
 		bindJsonObj2Easyui(json.rtData , "form1");
	}
}
//保存
function save(){
	if($('#form1').form('enableValidation').form('validate')){	
		var param = tools.formToJson($("#form1"));
		//校验身份证
		var text = /^(([1][1-5])|([2][1-3])|([3][1-7])|([4][1-6])|([5][0-4])|([6][1-5])|([7][1])|([8][1-2]))\d{4}(([1][9]\d{2})|([2]\d{3}))(([0][1-9])|([1][0-2]))(([0][1-9])|([1-2][0-9])|([3][0-1]))\d{3}[0-9xX]$/;
		var flag = text.test(param.personIdcard);
		if(!flag){
			$.MsgBox.Alert_auto("身份证号输入不正确");
			return false;
		}
		//校验联系电话
		if($("#phoneNumber").val()!=""){
			var text = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$|(^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$)/;
			var flag = text.test(param.phoneNumber);
			if(!flag){
				$.MsgBox.Alert_auto("联系电话输入不正确");
				return false;
			}
		}
		if($("#sex").val()=="男"){
			param.sex = '01';
		}else{
			param.sex = '02';
		}
		param.isRevock = 0;
		param.checkcount = 0;
		param.sourceType = '03';
		param.organizationId = deptId;
		if(id!=0){//编辑
			param.id = id;
			var json = tools.requestJsonRs("/casecheckPersonCtrl/update.action",param);
		    return json.rtState;
		}else{
			var json = tools.requestJsonRs("/casecheckPersonCtrl/saveOther.action",param);
		    return json.rtState;
		}
	}
}
//根据身份证获取性别和出生日期
function personIdToSex(){
	$('#personIdcard').textbox({   
		"onChange":function(){
			var age = document.getElementById("personIdcard").value;
			if (age.length == 18){
				if (parseInt(age.substr(16, 1)) % 2 == 0) {     
			        $('#sex').textbox('setValue','女');
				}else{                               
			        $('#sex').textbox('setValue','男');
				}
			}
			else if (age.length == 15){
				if (parseInt(age.substr(14, 1)) % 2 == 0) {     
			        $('#sex').textbox('setValue','女');
				}else{                               
			        $('#sex').textbox('setValue','男');
				}
			}else{
				$.MsgBox.Alert_auto("身份证号码位数错误");
			}
		}
	});
}