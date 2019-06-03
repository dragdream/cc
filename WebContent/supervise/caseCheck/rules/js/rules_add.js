var id = $("#id").val();

//初始化方法
function doInit(){
	//适用范围
    var checkType = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "RATE_RANGE_TYPE"});
    if(checkType.rtState) {
    	$('#checkType').combobox({
    		data: checkType.rtData,
    		valueField: 'codeNo',
    		textField: 'codeName',
    		panelHeight:'auto',
    		editable:false,
    		multiple:true,
    		required:true,
    		missingMessage:'请选择适用范围',
    		onLoadSuccess:function(){
    			$('#checkType').combobox('setValue',checkType.rtData[0].codeNo);
    		}
    	});
    }
    //评查项类型
    var rateModelType = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "RATE_MODEL_TYPE"});
    if(rateModelType.rtState) {
    	$('#checkSubjectType').combobox({
    		data: rateModelType.rtData,
    		valueField: 'codeNo',
    		textField: 'codeName',
    		panelHeight:'auto',
    		editable:false,
    		required:true,
    		missingMessage:'请选择评查项类型',
    		onLoadSuccess:function(){
    			$('#checkSubjectType').combobox('setValue',rateModelType.rtData[0].codeNo);
    		}
    	});
    }
    //评分细则层级
    var json = tools.requestJsonRs("/jdCasecheckRuleKindCtrl/getRootListByVersionId.action", {versionId: $('#versionId').val()});
    if(json.rtState) {
        $('#classoneId').combobox({
            data: json.rtData,
            valueField: 'id',
            textField: 'describe',
            editable:false,
            panelHeight:'auto',
            onLoadSuccess:function(){
                $('#classoneId').combobox('setValue',json.rtData[0].id);
            },
            onChange: function() {
                var firstLevel = $('#classoneId').combobox('getValue');
                if(firstLevel != -1) {
                    var params = {
                		parentId: firstLevel
                    };
                    var result = tools.requestJsonRs("/jdCasecheckRuleKindCtrl/getListByParentId.action", params);
                    if(result.rtState) {
                        $('#classtwoId').combobox({
                            data: result.rtData,
                            valueField: 'id',
        		            textField: 'describe',
                            editable:false,
                            panelHeight:'auto',
                            onLoadSuccess:function(){
                                $('#classtwoId').combobox('setValue',result.rtData[0].id);
                            },
                        });
                    }
                }
            }
        });
    }
}
//保存
function save(){
	if($('#form1').form('enableValidation').form('validate')){	
		var param = tools.formToJson($("#form1"));
		param.classoneName=$('#classoneId').combobox('getText');
		param.classtwoName=$('#classtwoId').combobox('getText');
		console.log(param);
		if(id!=0){//编辑
			param.id = id;
			var json = tools.requestJsonRs("/jdCasecheckRuleDetailCtrl/addOrUpdate.action",param);
		    return json.rtState;
		}else{
			var json = tools.requestJsonRs("/jdCasecheckRuleDetailCtrl/addOrUpdate.action",param);
		    return json.rtState;
		}
	}
}
