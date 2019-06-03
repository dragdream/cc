var deptId = "";
var areaToLevelId = "";
var areaId = "";
function doInit(){
	//主体性质
	natureInit();
	//主体层级
	//subLevelInit();
	//职权类型
//	subjectPowerInit();
	if(id!=0){//编辑
		var json = tools.requestJsonRs("/subjectCtrl/get.action",{id:id});
 		bindJsonObj2Easyui(json.rtData , "form1");
 		//获取所属地区
    	var jsonArea = tools.requestJsonRs("/subjectCtrl/getOrgCtrl.action");
    	$('#area').textbox('setValue',jsonArea.rtData.name);
    	areaId = jsonArea.rtData.id;
    	var isAdmin = jsonArea.rtData.isAdmin;
 		//部门地区获取层级
 		var jsonlevel = tools.requestJsonRs("/subjectCtrl/areaToLevel.action",{id:json.rtData.area});
    	$('#subLevel').textbox('setValue',jsonlevel.rtData.name);
    	areaToLevelId = jsonlevel.rtData.id;
 		//checkbox 赋值
// 		var arr = JSON.parse(json.rtData.subjectPower);
// 		if(arr != null){
// 			for(var i=0;i<arr.length;i++){
// 	 			$("input[name=subjectPower][value="+arr[i]+"]").prop("checked",true);
// 	 		}
// 		}
 		if(isAdmin){
 			//所属部门
 	 		$('#departmentCode').combobox({
 	 			prompt:'输入关键字后自动搜索',
 	 			mode:'remote',
 	 			url:contextPath + '/subjectCtrl/getSysCodeTempById.action?id='+json.rtData.departmentCode,
 	 			valueField:'id',
 	 			textField:'name',
 	 			multiple:false,
 	 			method:'post',
 	 			panelHeight:'100px',
 	 			labelPosition: 'top',
 	 			onClick:function(row){
 	 				//执法系统
 	 				$('#orgSys').textbox('setValue',"");
 	 				$('#orgSys').combobox({
 	 					prompt:'请选择',
 	 					mode:'remote',
 	 					url:contextPath + '/subjectCtrl/getSysCodeOrg.action?id=' + row.id,
 	 					valueField:'id',
 	 					textField:'name',
 	 					multiple:false,
 	 					method:'POST',
 	 					panelHeight:'100px',
 	 					labelPosition: 'top',
 	 					onClick:function(row){
 	 						ComboboxCommonProcess($(this));
 	 					}
 	 				});
 	 				ComboboxCommonProcess($(this));
 	 			},
 	 			onHidePanel:function(){
 	 				$('#orgSys').textbox('setValue',"");
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
 	 		//执法系统
 	 		$('#orgSys').combobox({
 					prompt:'请选择',
 					mode:'remote',
 					url:contextPath + '/subjectCtrl/getSysCodeOrgById.action?id=' + json.rtData.orgSys,
 					valueField:'id',
 					textField:'name',
 					multiple:false,
 					method:'POST',
 					panelHeight:'100px',
 					labelPosition: 'top',
 					onClick:function(row){
 						ComboboxCommonProcess($(this));
 					}
 				});
 		}else{
 			var deptJson = tools.requestJsonRs("/subjectCtrl/getDeptName.action");
    		$('#departmentCode').textbox('setValue',deptJson.rtData.name);
    		$('#departmentCode').textbox('textbox').attr('readonly',true); 
        	deptId = deptJson.rtData.id;
        	//执法系统
			$('#orgSys').combobox({
				prompt:'请选择',
				mode:'remote',
				url:contextPath + '/subjectCtrl/getSysCodeOrg.action?id=' + deptJson.rtData.id,
				valueField:'id',
				textField:'name',
				multiple:false,
				method:'POST',
				panelHeight:'100px',
				//label: 'Language:',
				labelPosition: 'top',
			});
			ComboboxCommonProcess($(this));
 		}
 		
 		
	}else{//新增
		//获取所属地区
    	var jsonArea = tools.requestJsonRs("/subjectCtrl/getOrgCtrl.action");
    	$('#area').textbox('setValue',jsonArea.rtData.name);
    	areaId = jsonArea.rtData.id;
    	//根据所属地区获取层级
    	var areaToLevel = tools.requestJsonRs("/subjectCtrl/areaToLevel.action",{id:jsonArea.rtData.id});
    	$('#subLevel').textbox('setValue',areaToLevel.rtData.name);
    	areaToLevelId = areaToLevel.rtData.id;
		
    	var isAdmin = jsonArea.rtData.isAdmin
    	if (isAdmin){//管理员
    		//获取所属部门
    		$('#departmentCode').combobox({
    			prompt:'输入关键字后自动搜索',
    			mode:'remote',
    			url:contextPath + '/subjectCtrl/getSysCodeTempById.action',
    			valueField:'id',
    			textField:'name',
    			multiple:false,
    			method:'post',
    			panelHeight:'100px',
    			//label: 'Language:',
    			labelPosition: 'top',
    			onClick:function(row){
    				//执法系统
    				$('#orgSys').textbox('setValue',"");
    				$('#orgSys').combobox({
    					prompt:'请选择',
    					mode:'remote',
    					url:contextPath + '/subjectCtrl/getSysCodeOrg.action?id=' + row.id,
    					valueField:'id',
    					textField:'name',
    					multiple:false,
    					method:'POST',
    					panelHeight:'100px',
    					//label: 'Language:',
    					labelPosition: 'top',
    				});
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
    	}else{//执法部门
    		//获取部门名称和统一社会信用代码
    		var deptJson = tools.requestJsonRs("/subjectCtrl/getDeptName.action");
    		$('#departmentCode').textbox('setValue',deptJson.rtData.name);
    		$('#departmentCode').textbox('textbox').attr('readonly',true); 
        	deptId = deptJson.rtData.id;
        	$('#code').textbox('setValue',deptJson.rtData.code);
        	//执法系统
			$('#orgSys').textbox('setValue',"");
			$('#orgSys').combobox({
				prompt:'请选择',
				mode:'remote',
				url:contextPath + '/subjectCtrl/getSysCodeOrg.action?id=' + deptJson.rtData.id,
				valueField:'id',
				textField:'name',
				multiple:false,
				method:'POST',
				panelHeight:'100px',
				//label: 'Language:',
				labelPosition: 'top',
			});
			ComboboxCommonProcess($(this));
    	}
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

function natureInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "SYSTEM_SUBJECT_NATURE"});
    if(json.rtState) {
        $('#nature').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'auto',
        });
    }
}

function areaInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "ADMINISTRAIVE_DIVISION"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: -1, codeName: "请选择"});
        $('#area').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
    		panelHeight:'150px',
            onLoadSuccess:function(){
                $('#area').combobox('setValue',-1);
            },
        });
    }
}

function subLevelInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "DEPT_LEVEL"});
    if(json.rtState) {
        $('#subLevel').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
    		panelHeight:'90px',
        });
    }
}

function save(){
	if($('#form1').form('enableValidation').form('validate')){
		var param = tools.formToJson($("#form1"));
		
		//校验编制数
		if(param.innerSupOrgPostNum !="" && param.innerSupOrgPostNum !=null){
			var text = /^[0-9]*$/;
			var flag = text.test(param.innerSupOrgPostNum);
			if(!flag){
				$.MsgBox.Alert_auto("编制数只能输入数字");
				return false;
			}
		}
		
		if(deptId.length > 30){
			param.departmentCode = deptId;
		}
		param.isDelete = 0;
		param.examine = 0;
		param.isDepute = 0;
		param.subLevel = areaToLevelId;
		param.area = areaId;
		//将职权类别存到数组之后转json
//		var arr = [];
//		$("input[name=subjectPower]:checked").each(function(index,obj){
//			arr.push(obj.value);
//		});
//		param.subjectPower = JSON.stringify(arr);
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

function orgSysInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "LAW_ENFORCEMENT_FIELD"});
	if(json.rtState) {
        $('#orgSys').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
    		panelHeight:'150px',
        });
    }
}
