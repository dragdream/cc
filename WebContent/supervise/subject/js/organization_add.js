var orgSys = "";
function doInit(){
	//areaInit();
	if(id!=0){//编辑
		var json = tools.requestJsonRs("/subjectCtrl/get.action",{id:id});
		bindJsonObj2Easyui(json.rtData , "form1");
		//委托主体
		$('#parentId').combobox({
			prompt:'输入关键字后自动搜索',
			mode:'remote',
			url:contextPath + '/subjectCtrl/getSysCodeSubById.action?id='+json.rtData.parentId,
			valueField:'id',
			textField:'subName',
			multiple:false,
			method:'post',
			panelHeight:'100px',
			//label: 'Language:',
			labelPosition: 'top',
			onClick:function(row){
				ComboboxCommonProcess($(this));
			},
			onHidePanel:function(){
				var _options = $(this).combobox('options');
			    var _data = $(this).combobox('getData');/* 下拉框所有选项 */
			    var _value = $(this).combobox('getValue');/* 用户输入的值 */
			    var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
			    for (var i = 0; i < _data.length; i++) {
			        if (_data[i][_options.valueField] == _value) {
			            _b = true;
			            break;
			        }
			    }
			    if (!_b) {
			        $(this).combobox('setValue', '');
			    }
			}
		});
	}else{
		//委托主体
		$('#parentId').combobox({
			prompt:'输入关键字后自动搜索',
			mode:'remote',
			url:contextPath + '/subjectCtrl/getSysCodeSubById.action',
			valueField:'id',
			textField:'subName',
			multiple:false,
			method:'post',
			panelHeight:'100px',
			//label: 'Language:',
			labelPosition: 'top',
			onClick:function(row){
				ComboboxCommonProcess($(this));
			},
			onHidePanel:function(){
				var _options = $(this).combobox('options');
			    var _data = $(this).combobox('getData');/* 下拉框所有选项 */
			    var _value = $(this).combobox('getValue');/* 用户输入的值 */
			    var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
			    for (var i = 0; i < _data.length; i++) {
			        if (_data[i][_options.valueField] == _value) {
			            _b = true;
			            break;
			        }
			    }
			    if (!_b) {
			        $(this).combobox('setValue', '');
			    }
			}
		});
	}
	entrustNatureInit();
	orgModeInit();
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
//性质
function entrustNatureInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "ORGANIZATION_NATURE"});
    if(json.rtState) {
        $('#entrustNature').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'auto',
        });
    }
}

//委托方式
function orgModeInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "ORGANIZATION_TYPE"});
    if(json.rtState) {
        $('#orgMode').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'auto',
        });
    }
}

function save(){
	if($('#form1').form('enableValidation').form('validate')){	
		var param = tools.formToJson($("#form1"));
		//校验统一社会信用代码
		var text = /[^_IOZSVa-z\W]{2}\d{6}[^_IOZSVa-z\W]{10}/g;
		var flag = text.test(param.code);
		if(!flag){
			$.MsgBox.Alert_auto("统一社会信用代码输入不正确");
			return false;
		}
		//校验日期
		var oDate1 = new Date(param.termBeginStr);
	    var oDate2 = new Date(param.termEndStr);
	    if(oDate1.getTime() > oDate2.getTime()){
			$.MsgBox.Alert_auto("委托期限（起）不能大于委托期限（止）");
			return false;
	    }
		//校验邮编
		if(param.postCode != ""){
			var text = /^[0-9]{6}$/;
			var flag = text.test(param.postCode);
			if(!flag){
				$.MsgBox.Alert_auto("邮编输入不正确");
				return false;
			}
		}
		//校验邮箱
		if(param.mail != ""){
			var text = /(^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$)/;
			var flag = text.test(param.mail);
			if(!flag){
				$.MsgBox.Alert_auto("电子邮箱输入不正确");
				return false;
			}
		}
		//根据委托主体获取执法系统
		if(param.parentId != ""){
			var parId = param.parentId;
			var json = tools.requestJsonRs("/subjectCtrl/subOrgSys.action", {id:parId});
			param.orgSys = json.rtData;
		}
		param.isDelete = 0;
		param.examine = 1;
		param.isDepute = 1;
	    if(id!=0){//编辑
			var json = tools.requestJsonRs("/subjectCtrl/update.action",param);
		    return json.rtState;
		}else{//新增
			var json = tools.requestJsonRs("/subjectCtrl/save.action",param);
		    return json.rtState;
		}
	}else{
		return false;
		
	}
	
}

