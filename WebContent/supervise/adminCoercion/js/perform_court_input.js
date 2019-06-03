
var caseSourceId = '';
var caseSourceType = null;
var subjectId = '';
var coercionCaseId = '';
var departmentId = '';
var courtPerformId ='';
var caseCode='';
//var isSecondPress = null;


//行政强制申请法院执行
function doInit(){
	caseSourceId = $('#caseSourceId').val();
    caseSourceType = $('#caseSourceType').val();
    subjectId = $('#subjectId').val();
    coercionCaseId = $('#coercionCaseId').val(); 
    departmentId = $('#departmentId').val();
    courtPerformId = $('#courtPerformId').val(); 
    caseCode=$('#caseCode').val();
	initCourtPerform();
}

/*
 * 初始化申请法院强制执行
 */
function initCourtPerform(){
	var params = {
	        caseSourceId : caseSourceId,
	        caseSourceType : caseSourceType,
	        subjectId : subjectId,
	        id : coercionCaseId
	    }
	$.ajax({ 
    	type:"post",
    	url: "/coercionCaseCtrl/courtPerformsInput.action",//提交地址 
    	data: params,//参数 
    	dataType: "json",
    	success: function(data){ //回调方法 
    		bindJsonObj2Easyui(data,"court_perform_press_form");
    		bindJsonObj2Easyui(data,"court_perform_Apply_form");
    		initComboBox();
    		if(data.performType!=null && data.performType!=''){
    			$('#performType').combobox('setValue', data.performType);
    		}else{
    			$('#performType').combobox('setValue', '');
    		}
    		if(data.pressSendType!=null && data.pressSendType!=''){
    			$('#pressSendType').combobox('setValue', data.pressSendType);
    		}else{
    			$('#pressSendType').combobox('setValue', '');
    		}
    		if(data.isSecondPress == 1){
    			//初始化二次催告
    			$('#isSecondPress').checkbox({
    				checked:true
    			});
    			$('.secondPress-info-tr').show();
//    			$('#isSecondPressTr').show();
    			$.parser.parse($('.secondPress-info-tr'));
    			if(data.secondPressDateStr!=null && data.secondPressDateStr!=''){
    				$('#secondPressDateStr').datebox('setValue', data.secondPressDateStr);
    			}
    			if(data.secondPressType!=null && data.secondPressType!=''){
        			$('#secondPressType').combobox('setValue', data.secondPressType);
        		}
    		}else{
    			$('#isSecondPress').checkbox({
    				checked:false
    			});
    			$('#secondPressType').combobox('setValue', '');
    			$('.secondPress-info-tr').hide();
    		}
    		
    	},
    });
	/**
	 * 初始化下拉框
	 */
	function initComboBox() {

	    var sendTypeParams = {
	        parentCodeNo : "COMMON_SENT_WAY",
	        codeNo : "",
	        panelHeight : 'auto'

	    };
	    var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action",
	            sendTypeParams);
	    $('.sendType-select').combobox({
	        data : result.rtData,
	        valueField : 'codeNo',
	        textField : 'codeName',
	        panelHeight : 'auto',
	        prompt : '请选择',
	        
	        editable : false
	    });
	    
	    var enfroceTypeParams = {
	            parentCodeNo : "COURT_ENFORCE_TYPE",
	            codeNo : "",
	            panelHeight : 'auto'
	        };
	        var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action",
	                enfroceTypeParams);
	        $('.enforceType-select').combobox({
	            data : result.rtData,
	            valueField : 'codeNo',
	            textField : 'codeName',
	            panelHeight : 'auto',
	            prompt : '请选择',
	            editable : false
	        });
	}
	
    if (1 == $("#court_perform_press_panel input[name='isSecondPress']:checked").val()) {
        $('.secondPress-info-tr').show();
        $.parser.parse($('.secondPress-info-tr'));
    }
    $('#court_perform_press_panel .isSecondPress').checkbox(
		{
		    onChange : function() {
		        var choiceValue = $("#court_perform_press_panel input[name='isSecondPress']:checked").val();
//		        isSecondPress = choiceValue;
		        if (choiceValue == '1') {
		            $('.secondPress-info-tr').show();
		            $.parser.parse($('.secondPress-info-tr'));
		        } else {
		            $('.secondPress-info-tr').hide();
		        }
		    }
		});
}

/*
 * 保存法院强制执行维护信息
 */
function saveCourtPerform() {
	if($("#court_perform_press_form").form('enableValidation').form('validate')&&$("#court_perform_Apply_form").form('enableValidation').form('validate')){
		var pressParams = tools.formToJson($('#court_perform_press_form'));
	    var applyParams = tools.formToJson($('#court_perform_Apply_form'));

	    var finalParams = {
	        id : courtPerformId,
	        caseSourceId : caseSourceId,
	        caseSourceType : caseSourceType,
	        subjectId : subjectId,
	        departmentId: departmentId,
	        coercionCaseId : coercionCaseId,
	        caseCode:caseCode
	    }
	    // 催告
	    finalParams.punishCodeBefore = pressParams.punishCodeBefore;
	    finalParams.punishDateBeforeStr = pressParams.punishDateBeforeStr;
	    finalParams.pressSendDateStr = pressParams.pressSendDateStr;
	    finalParams.pressSendType = pressParams.pressSendType;
	    finalParams.isSecondPress = pressParams.isSecondPress;
	    finalParams.secondPressDateStr = pressParams.secondPressDateStr;
	    finalParams.secondPressType = pressParams.secondPressType;
	    // 申请与批准
	    finalParams.applyDateStr = applyParams.applyDateStr;
	    finalParams.approveDateStr = applyParams.approveDateStr;
	    finalParams.performType = applyParams.performType;
	    finalParams.enforceElementCondition = applyParams.enforceElementCondition;
	    var resultInfo = tools.requestJsonRs(
	            "/coercionCaseCtrl/saveCourtPerforminfo.action", finalParams);
	    
	    if (resultInfo.rtState) {
	        $.MsgBox.Alert_auto("保存成功");
	        var data = resultInfo.rtData;
	        coercionCaseId = data.coercionCaseId;
	        courtPerformId = data.id;
	        return true;
	    } else if (resultInfo.rtData == -1) {
	        $.MsgBox.Alert_auto("保存失败！");
	        return false;
	    } else {
	        $.MsgBox.Alert_auto("发生未知错误！");
	        return false;
	    }
	}
}
