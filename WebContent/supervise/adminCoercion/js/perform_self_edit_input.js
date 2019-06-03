/**
 * 
 */
var prePerformType = '';
var isAgreementEnforce = '';
var isEnforceReturn = '';
var isEndEnforce = '';
var isBreakEnforce = '';
// data
var initPerformType = '';
var performId = '';
var srcCaseId = '';
var srcCaseType = '';
var coercionCaseId = '';
var subjectId = '';
var departmentId = '';
var addPower = [];
var powerJson = [];
var gistJson = [];
var enforceStep = null;
var caseCode=''

function performSelfEdit() {
    initPerformType = $('#performType').val();
    performId = $('#performId').val();
    srcCaseId = $('#caseSourceId').val();
    srcCaseType = $('#caseSourceType').val();
    coercionCaseId = $('#coercionCaseId').val();
    subjectId = $('#subjectId').val();
    departmentId = $('#departmentId').val();
    enforceStep = $('#enforceStep').val();
    caseCode = $('#caseCode').val();
  /*  initAllDate();*/
    initComboBoxCommonSentWay();
    initComboBoxEndSendType();
    initComboBoxEnforceEndReason();
    selectTabs();
    InputInfoCtrl();
    elemShowCtrl('2', initPerformType);
    elemShowCtrl('4', initPerformType);
};

function initAllDate(){
/*	dateSysdateValidate(punishDateBeforeStr);
	dateSysdateValidate(originalDateStr);
	dateSysdateValidate(pressSendDateStr);
	dateSysdateValidate(secondPressDateStr);
	dateSysdateValidate(applyDateStr);
	dateSysdateValidate(approveDateStr);
	dateSysdateValidate(performEnforceDateStr);
	dateSysdateValidate(decideSendDateStr);
	dateSysdateValidate(transNoticeDateStr);
	dateSysdateValidate(auctionDateStr);
	dateSysdateValidate(replaceEnforceDateStr);
	dateSysdateValidate(agreeSignDateStr);*/
	/*dateValidate(applyDateStr, approveDateStr);*/
}

/**
 * 初始化送达方式combobox
 */
function initComboBoxCommonSentWay() {
    var params = {
        parentCodeNo : "COMMON_SENT_WAY",
        codeNo : "",
        panelHeight : 'auto'

    };
    var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action",
            params);
    $('.sendType-select').combobox({
        data : result.rtData,
        valueField : 'codeNo',
        textField : 'codeName',
        panelHeight : 'auto',
        prompt : '请选择',
        editable : false
    });
}
function initComboBoxEndSendType() {

    var params = {
        parentCodeNo : "COMMON_SENT_WAY",
        codeNo : "",
        panelHeight : 'auto'

    };
    var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action",
            params);
    $('#endSendType').combobox({
        data : result.rtData,
        valueField : 'codeNo',
        textField : 'codeName',
        panelHeight : 'auto',
        prompt : '请选择',
        onChange: function(){
        	var cdSendType = $('#endSendType').val();
        	if(cdSendType!=null&&cdSendType!=''){
        		$('#endSendType').combobox('setValue', cdSendType);
        	}
        },
        editable : false
    });
}

/**
 * 初始化终结原因combobox
 */
function initComboBoxEnforceEndReason() {

    var params = {
        parentCodeNo : "COERCION_ENFORCE_END_REASON",
        codeNo : "",
        panelHeight : 'auto'
    };
    var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action",
            params);
    $('#enforceEndReason').combobox({
        data : result.rtData,
        valueField : 'codeNo',
        textField : 'codeName',
        panelHeight : 'auto',
        prompt : '请选择',
        onLoadSuccess: function(){
        	var cdSendType = $('#enforceEndReason').val();
        	if(cdSendType!=null&&cdSendType!=''){
        		$('#enforceEndReason').combobox('setValue', cdSendType);
        	}
        },
        editable : false
    });
}
/**
 * 返回强制执行列表
 */
function doOpenListPage() {
    var params = {
        caseSourceId : srcCaseId,
        caseSourceType : srcCaseType,
        subjectId : subjectId,
        id : coercionCaseId
    }
    var url = contextPath + "/coercionCaseCtrl/performsInput.action?";
    url = url + $.param(params);
    window.location.href = url;
}

/**
 * 页面初始化控制
 */
function InputInfoCtrl() {
    $('#performInput_performType .radiobutton').on('click', function() {
        var value = $(this).find('.radiobutton-value').val();

        elemHideCtrl('2', prePerformType, value);
        elemHideCtrl('4', prePerformType, value);
        elemShowCtrl('2', value);
        elemShowCtrl('4', value);
        prePerformType = value;
    });
    // 是否协议执行
    if (1 == $("#perform_enforceExtra_panel input[name='isAgreementEnforce']:checked").val()) {
        $('.agreementEnforce-info-tr').show();
        $.parser.parse($('.agreementEnforce-info-tr'));
    }
    $('.isAgreementEnforce')
            .checkbox(
                    {
                        onChange : function() {
                            var choiceValue = $(
                                    "#perform_enforceExtra_panel input[name='isAgreementEnforce']:checked")
                                    .val();
                            isAgreementEnforce = choiceValue;
                            if (choiceValue == '1') {
                                $('.agreementEnforce-info-tr').show();
                                $.parser.parse($('.agreementEnforce-info-tr'));
                            } else {
                                $('.agreementEnforce-info-tr').hide();
                            }
                        }
                    });
    // 是否执行回转
    if (1 == $("#perform_enforceExtra_panel input[name='isEnforceReturn']:checked").val()) {
        $('.enforceReturn-info-tr').show();
        $.parser.parse($('.enforceReturn-info-tr'));
    }
    $('.isEnforceReturn')
            .checkbox(
                    {
                        onChange : function() {
                            var choiceValue = $(
                                    "#perform_enforceExtra_panel input[name='isEnforceReturn']:checked")
                                    .val();
                            isEnforceReturn = choiceValue;
                            if (choiceValue == '1') {
                                $('.enforceReturn-info-tr').show();
                                $.parser.parse($('.enforceReturn-info-tr'));
                            } else {
                                $('.enforceReturn-info-tr').hide();
                            }
                        }
                    });
    // 是否终结执行
    if (1 == $("#perform_enforceExtra_panel input[name='isEndEnforce']:checked").val()) {
        $('.endEnforce-info-tr').show();
        $.parser.parse($('.endEnforce-info-tr'));
    }
    $('.isEndEnforce')
            .checkbox(
                    {
                        onChange : function() {
                            var choiceValue = $(
                                    "#perform_enforceExtra_panel input[name='isEndEnforce']:checked")
                                    .val();
                            isEndEnforce = choiceValue;
                            if (choiceValue == '1') {
                                $('.endEnforce-info-tr').show();
                                $.parser.parse($('.endEnforce-info-tr'));
                            } else {
                                $('.endEnforce-info-tr').hide();
                            }
                        }
                    });
    // 是否中止执行
    if (1 == $("#perform_enforceExtra_panel input[name='isBreakEnforce']:checked").val()) {
        $('.breakEnforce-info-tr').show();
        $.parser.parse($('.breakEnforce-info-tr'));
    }
    $('.isBreakEnforce')
            .checkbox(
                    {
                        onChange : function() {
                            var choiceValue = $(
                                    "#perform_enforceExtra_panel input[name='isBreakEnforce']:checked")
                                    .val();
                            isEndEnforce = choiceValue;
                            if (choiceValue == '1') {
                                $('.breakEnforce-info-tr').show();
                                $.parser.parse($('.breakEnforce-info-tr'));
                            } else {
                                $('.breakEnforce-info-tr').hide();
                            }
                        }
                    });
    // 获取强制执行信息，尝试初始化职权依据表
    if (performId != null && performId != '') {
        var json = tools
                .requestJsonRs("/coercionCaseCtrl/getPerformInfo.action?id="
                        + performId);
        var powerJsonStr = json.rtData.powerJsonStr;
        var powerJson;
        if (powerJsonStr != null && powerJsonStr != ''
                && powerJsonStr != 'null') {
            powerJson = powerJsonStr.split(",");
            var params;
            var paramsGist;
            if (powerJson != null && powerJson.length > 0) {
                for (var i = 0; i < powerJson.length; i++) {
                    addPower.push(powerJson[i]);
                }
                params = {
                    ids : addPower.join(","),
                    actSubject : subjectId
                }
                paramsGist = {
                    powerId : addPower.join(",")
                }
                initCoercionPowerTable(params);// 违法行为信息加载
                initCoercionGistTable(paramsGist); // 违法依据信息加载
                var gistJsonStr = json.rtData.gistJsonStr;
                gistJson = [];
                if (gistJsonStr != null && gistJsonStr != ''
                        && gistJsonStr != 'null') {
                    gistJson = gistJsonStr.split(",");
                }
            }
        }else{
            var emptyPowerParams = {
                    ids: 'empty',
                    actSubject: 'empty'
                    
            }
            var emptyGistParams = {
                    powerId: 'empty'
                    
            }
            initCoercionPowerTable(emptyPowerParams);
            initCoercionGistTable(emptyGistParams);
        }
    }else{
        var emptyPowerParams = {
                ids: 'empty',
                actSubject: 'empty'
                
        }
        var emptyGistParams = {
                powerId: 'empty'
                
        }
        initCoercionPowerTable(emptyPowerParams);
        initCoercionGistTable(emptyGistParams);
    }
}
/**
 * 页面元素显示控制
 */
function elemShowCtrl(step, curType) {
    if (step == null || step == '') {
        return;
    }
    if (step == '2') {
        if (curType == null || curType == '') {
            return;
        }
        if (curType == '100') {
            $('.imposeFine-info-tr').show();
        }
        if (curType == '500') {
            $('.replace-info-tr').show();
        }
    }
    if (step == '4') {
        if (curType == null || curType == '') {

            return;
        }
        if (curType == '200') {
            $('.trans-info-tr').show();
        }
        if (curType == '300') {
            $('.auction-info-tr').show();
        }
        if (curType == '500') {
            $('.replace-info-tr').show();
        }
    }
}

/**
 * 页面元素隐藏控制
 */
function elemHideCtrl(step, preType, curType) {
    if (step == null || step == '') {
        return;
    }
    if (step == '2') {
        if (preType == null || preType == '') {
            return;
        }
        if (preType != curType) {
            if (preType == '100') {
                $('.imposeFine-info-tr').hide();
            }
            if (preType == '500') {
                $('.replace-info-tr').hide();
            }
        }
    }
    if (step == '4') {
        if (preType == null || preType == '') {
            return;
        }
        if (preType != curType) {
            if (preType == '200') {
                $('.trans-info-tr').hide();
            }

            if (preType == '300') {
                $('.auction-info-tr').hide();
            }
            if (preType == '500') {
                $('.replace-info-tr').hide();
            }
        }
    }
}

/**
 * 查询实施主体下的职权
 * 
 * @returns
 */
function coercionFindPower() {
    var params = {
        actSubject : subjectId,
        powerType : '03'
    }
    var url = contextPath + "/caseCommonPowerCtrl/commonCaseAddPower.action?"
            + $.param(params);
    top.bsWindow(url, "选用强制职权", {
        width : "1000",
        height : "500",
        buttons : [ {
            name : "关闭",
            classStyle : "btn-alert-gray"
        },{
            name : "保存",
            classStyle : "btn-alert-blue"
        }],
        submit : function(v, h) {
            var cw = h[0].contentWindow;
            if (v == "保存") {
                var formalPowers = cw.getSelectedPower();
                if (addPower == null || addPower.length == 0) {
                    for ( var index in formalPowers) {
                        addPower.push(formalPowers[index].id);
                    }
                } else {
                    for ( var index in formalPowers) {
                        if (addPower.indexOf(formalPowers[index].id) == -1) {
                            addPower.push(formalPowers[index].id);
                        }
                    }
                }
                if(addPower != null && addPower.length >0){
                	var params = {
                            ids : addPower.join(","),
                            actSubject : subjectId
                        };
                        
                        var paramsGist = {
                            powerId : addPower.join(","),
                            gistType : '03' // 依据类型（01 违法依据，02处罚依据，03设定依据）
                        }
                        initCoercionPowerTable(params);// 加载违法行为
                        initCoercionGistTable(paramsGist);// 加载违法依据
                }
                
                return true;
            } else if (v == "关闭") {
                return true;
            }
        }
    });
}

/**
 * 初始化强制职权表
 */
initCoercionPowerTable = function(params) {
    datagrid = $('#perform_power_table')
            .datagrid(
                    {
                        url : contextPath + '/powerCtrl/getPowerByActSubject.action',
                        pagination : false,
                        // pageSize : 5,
                        // pageList : [5, 10, 20],
                        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
                        toolbar : '#toolbar', // 工具条对象
                        checkbox : true,
                        border : false,
                        striped:true,
                        /* idField:'formId',//主键列 */
                        fitColumns : true, // 列是否进行自动宽度适应
                        singleSelect : true, // 为true只能选择一行
                        nowrap : true,
                        queryParams : params,// 查询参数
                        onLoadSuccess : function(data) {
                        },
                        onLoadError : function(data){
                        },
                        columns : [[ 
                                {
                                    field : 'id',
                                    checkbox : true,
                                    title : "ID",
                                    width : 10,
                                    hidden : true,
                                    align : 'center'
                                },
                                {
                                    field : 'name',
                                    title : '违法行为',
                                    width : 200,
                                    halign : 'center',
                                    formatter: function(e, rowData) {
                                        var name = rowData.name;
                                        if(name == null || name == 'null') {
                                        	name = "";
                                        }
                                        var optStr = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+name+"'>"+name+"</lable>"
                                        return optStr;
                                    }
                                },
                                {
                                    field : 'code',
                                    title : '职权编号 ',
                                    width : 80,
                                    align : 'center'
                                },
                                {
                                    field : '___',
                                    title : '操作',
                                    formatter : function(e, rowData) {
                                        var optStr = "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deleteAddPower(\""
                                                + rowData.id
                                                + "\")'><i class='fa fa-trash'></i> </a>&nbsp;&nbsp;&nbsp;&nbsp;";
                                        return optStr;
                                    },
                                    width : 20,
                                    align : 'center'
                                } ] ]
                    });
}

/**
 * 初始化强制依据表
 */
initCoercionGistTable = function(params) {
    datagrid = $('#perform_gist_table').datagrid(
            {
                url : contextPath + '/powerCtrl/findGistsByPowerIds.action',
                queryParams : params,// 查询参数
                pagination : false,
                // pageSize : 5,
                // pageList : [5, 10, 20],
                view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
                toolbar : '#toolbar', // 工具条对象
                checkbox : true,
                border : false,
                striped:true,
                /* idField:'formId',//主键列 */
                fitColumns : true, // 列是否进行自动宽度适应
                singleSelect : false, // 为true只能选择一行
                nowrap : true,
                onLoadSuccess : function(data) {
                    var rowData = data.rows;
                    $.each(rowData, function(index, val) {// 遍历JSON
                        if (gistJson != null && gistJson.length > 0) {
                            for (var i = 0; i < gistJson.length; i++) {
                                if (gistJson[i] == val.id) {
                                    $("#perform_gist_table").datagrid(
                                            "selectRow", index);// 如果数据行为已选中则选中改行
                                    break;
                                }
                            }
                        } else {
                            $("#perform_gist_table").datagrid("selectRow",
                                    index);// 如果数据行为已选中则选中改行
                        }
                    });
                },
                columns : [ [ {
                    field : 'id',
                    checkbox : true,
                    title : "ID",
                    hidden : true,
                    width : 20,
                    align : 'center'
                }, {
                    field : 'lawName',
                    title : '法律名称',
                    width : 120,
                    halign : 'center'
                }, {
                    field : 'gistStrip',
                    title : '条',
                    width : 15,
                    align : 'center'
                }, {
                    field : 'gistFund',
                    title : '款',
                    width : 15,
                    align : 'center',
                    formatter : function(value, rowData, rowIndex) {
                        if (value == 0) {
                            return "";
                        } else {
                            return value;
                        }
                    }
                }, {
                    field : 'gistItem',
                    title : '项',
                    width : 15,
                    align : 'center',
                    formatter : function(value, rowData, rowIndex) {
                        if (value == 0) {
                            return "";
                        } else {
                            return value;
                        }
                    }
                }, {
                    field : 'content',
                    title : '内容',
                    width : 200,
                    halign : 'center',
                    formatter: function(e, rowData) {
                        var content = rowData.content;
                        if(content == null || content == 'null') {
                        	content = "";
                        }
                        var optStr = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+content+"'>"+content+"</lable>"
                        return optStr;
                    }
                } ] ]
            });
}

/**
 * 删除添加的违法依据
 * 
 * @param id
 *            违法依据ID
 * @returns
 */
function deleteAddPower(id) {
    for ( var index in addPower) {
        if (addPower[index] == id) {
            addPower.splice(index, 1);
            break;
        }
    }
    var params;
    var paramsGist;
    if (addPower == null || addPower.length == 0) {
        params = {
            ids : "empty",
            actSubject : subjectId
        };
        paramsGist = {
            powerId : "empty"
        };
    } else {
        params = {
            ids : addPower.join(","),
            actSubject : subjectId
        };
        paramsGist = {
            powerId : addPower.join(","),
            gistType : '03' // 依据类型（01 违法依据，02处罚依据，02设定依据）
        };
    }
    initCoercionPowerTable(params);
    initCoercionGistTable(paramsGist);// 加载违法依据
}

/**
 * 保存
 */
function savePerformInfo(){
	var tab = $('#performInput_edit_Div').tabs('getSelected');
    var index = $('#performInput_edit_Div').tabs('getTabIndex',tab);
    if(index == 0){
    	savePerformType();//行政强制措施种类
    }else if(index == 1){
    	savePerformPress();//申请与批准保存
    }else if(index == 2){
    	savePerformApply();//保存行政强制措施处罚决定
    }else if(index == 3){
    	savePerformEnforce();//保存行政强制措施处罚决定
    	return true;
    }
}

/**
 * 保存强制执行方式信息
 */
function savePerformType() {
    var performType = '';
    $('#performInput_performType input[name="performType"]').each(function() {
        if ($(this).is(':checked')) {
            performType = $(this).val();
        }
    });
    if (performType == '') {
        alert("请选择强制执行方式！");
        return false;
    }
    var params = {
        performType : performType,
        id : performId,
        subjectId : subjectId,
        departmentId: departmentId,
        caseSourceId : srcCaseId,
        caseSourceType : srcCaseType,
        coercionCaseId : coercionCaseId,
        caseCode:caseCode
    }
    var resultInfo = tools.requestJsonRs(
            "/coercionCaseCtrl/savePerformType.action", params);
    if (resultInfo.rtState == true) {
        $.MsgBox.Alert_auto("保存成功");
//        parent.document.getElementsByTagName('iframe')[1].contentWindow.initPerformDatagrid();
        switchTab(1);
        $('#performId').val(resultInfo.rtData);
        performId = resultInfo.rtData;
    } else if (resultInfo.rtState == false) {
        $.MsgBox.Alert_auto("保存失败！");
    }
}

/**
 * 保存强制执行催告信息
 */
function savePerformPress() {
	
	if($("#performPress_form").form('enableValidation').form('validate')){
		var params = tools.formToJson($('#performPress_form'));
	    // 获取强制执行类型
	    var performType = '';
	    $('#performInput_performType input[name="performType"]').each(function() {
	        if ($(this).is(':checked')) {
	            performType = $(this).val();
	        }
	    });
	    if (performType == '') {
	    	$.MsgBox.Alert_auto("无法获取强制执行方式，请刷新页面尝试重新保存！");
	        return false;
	    }
	    var finalParams = {
	        id : $('#performId').val(),
	        coercionCaseId : coercionCaseId,
	    }
	    // baseInfo
	    finalParams.punishCodeBefore = params.punishCodeBefore;
	    finalParams.punishDateBeforeStr = params.punishDateBeforeStr;
	    finalParams.pressSendDateStr = params.pressSendDateStr;
	    finalParams.pressSendType = params.pressSendType;

	    // *各种类强制措施字段校验与清理
	    // 加处罚款或滞纳金
	    if (performType = '100') {
	        finalParams.originalMoney = params.originalMoney;
	        finalParams.originalDateStr = params.originalDateStr;
	        finalParams.addFindMoney = params.addFindMoney;
	    }
	    // 代履行
	    if (performType = '500') {
	        finalParams.secondPressDateStr = params.secondPressDateStr;
	        finalParams.secondPressType = params.secondPressType;
	    }

	    var resultInfo = tools.requestJsonRs(
	            "/coercionCaseCtrl/savePerformPress.action", finalParams);
	    if (resultInfo.rtState == true) {
	        $.MsgBox.Alert_auto("保存成功");
	        enforceStep = resultInfo.rtData.enforceStep;
//	        parent.document.getElementsByTagName('iframe')[1].contentWindow.initPerformDatagrid();
	        switchTab(2);
	    } else if (resultInfo.rtState == false) {
	        $.MsgBox.Alert_auto("保存失败！");
	    } else {
	        $.MsgBox.Alert_auto("发生未知错误！");
	    }
	}
    
}

/**
 * 保存强制执行申请与批准信息
 */
function savePerformApply() {
	
	if($("#performApply_form").form('enableValidation').form('validate')){
		var params = tools.formToJson($('#performApply_form'));
	    // 获取强制执行类型
	    var performType = '';
	    $('#performInput_performType input[name="performType"]').each(function() {
	        if ($(this).is(':checked')) {
	            performType = $(this).val();
	        }
	    });
	    if (performType == '') {
	    	$.MsgBox.Alert_auto("无法获取强制执行方式，请刷新页面尝试重新保存！");
	        return false;
	    }
	    var finalParams = {
	        id : $('#performId').val(),
	        coercionCaseId : coercionCaseId
	    }
	    // baseInfo
	    finalParams.applyDateStr = params.applyDateStr;
	    finalParams.approveDateStr = params.approveDateStr;
	    finalParams.approvePerson = params.approvePerson;
	    // 依据
	    // 强制职权
	    var powerList = $('#perform_power_table').datagrid("getRows");
	    if (powerList == null || powerList.length < 1) {
	    	$.MsgBox.Alert_auto("请添加至少1条强制职权");
	        return false;
	    }
	    powerJson = []// 违法依据对象
	    for (var i = 0; i < powerList.length; i++) {
	        var obj = {};
	        obj.powerId = powerList[i].id;
	        powerJson.push(obj);
	    }
	    // 违法行为对象转成json字符串
	    finalParams.powerJsonStr = tools.parseJson2String(powerJson);
	    	
	    var gistList = $('#perform_gist_table').datagrid("getRows");
	    if (gistList == null || gistList.length < 1) {
	    	$.MsgBox.Alert_auto("请选择至少1条强制依据");
	        return false;
	    }
	    gistJson = []// 违法依据对象
	    for (var i = 0; i < gistList.length; i++) {
	        var obj = {};
	        obj.gistId = gistList[i].id;
	        obj.lawName = gistList[i].lawName;
	        obj.strip = gistList[i].gistStrip;
	        if (gistList[i].gistFund == null || gistList[i].gistFund == '') {
	            obj.fund = 0;
	        } else {
	            obj.fund = gistList[i].gistFund;
	        }
	        if (gistList[i].gistItem == null || gistList[i].gistItem == '') {
	            obj.item = 0;
	        } else {
	            obj.item = gistList[i].gistItem;
	        }
	        if (gistList[i].gistCatalog == null || gistList[i].gistCatalog == '') {
	            obj.gistCatalog = 0;
	        } else {
	            obj.gistCatalog = gistList[i].gistCatalog;
	        }
	        obj.content = gistList[i].content;
	        gistJson.push(obj);
	    }
	    // 违法依据json
	    finalParams.gistJsonStr = tools.parseJson2String(gistJson);

	    var resultInfo = tools.requestJsonRs(
	            "/coercionCaseCtrl/savePerformApply.action", finalParams);
	    if (resultInfo.rtState == true) {
	        $.MsgBox.Alert_auto("保存成功");
	        enforceStep = resultInfo.rtData.enforceStep;
//	        parent.document.getElementsByTagName('iframe')[1].contentWindow.initPerformDatagrid();
	        switchTab(3);
	    } else if (resultInfo.rtState == false) {
	        $.MsgBox.Alert_auto("保存失败！");
	    } else {
	        $.MsgBox.Alert_auto("发生未知错误！");
	    }
	}
}

/**
 * 保存强制执行事项管理信息
 */
function savePerformEnforce() {
    var params = tools.formToJson($('#performEnforce_form'));
    var paramsExtra = tools.formToJson($('#performEnforceExtra_form'));
    // 获取强制执行类型
    var performType = '';
    $('#performInput_performType input[name="performType"]').each(function() {
        if ($(this).is(':checked')) {
            performType = $(this).val();
        }
    });
    if (performType == '') {
        alert("无法获取强制执行方式，请刷新页面尝试重新保存！");
        return false;
    }
    var finalParams = {
        id : $('#performId').val()
    }
    // baseInfo
    finalParams.coercionPerformCode = params.coercionPerformCode;
    finalParams.performEnforceDateStr = params.performEnforceDateStr;
    finalParams.decideSendDateStr = params.decideSendDateStr;
    finalParams.decideSendType = params.decideSendType;
    // *各种类强制措施字段校验与清理
    // 划拨
    if (performType == '200') {
        finalParams.transDeposit = params.transDeposit;
        finalParams.transOrganization = params.transOrganization;
        finalParams.transNoticeDateStr = params.transNoticeDateStr;
    }
    // 拍卖
    if (performType == '300') {
        finalParams.auctionAddr = params.auctionAddr;
        finalParams.auctionDateStr = params.auctionDateStr;
        finalParams.auctionUse = params.auctionUse;
    }
    // 代履行
    if (performType == '500') {
        finalParams.replaceObject = params.replaceObject;
        finalParams.replaceDeposit = params.replaceDeposit;
        finalParams.replaceSupervise = params.replaceSupervise;
        finalParams.replaceEnforceDateStr = params.replaceEnforceDateStr;
    }
    // extraInfo
    finalParams.isAgreementEnforce = $('input[name="isAgreementEnforce"]:checked').val();
    if (finalParams.isAgreementEnforce == '1') {
        finalParams.agreeSignDateStr = paramsExtra.agreeSignDateStr;
        finalParams.agreeContent = paramsExtra.agreeContent;
        finalParams.agreeEnforceCondition = paramsExtra.agreeEnforceCondition;
    }
    finalParams.isEnforceReturn = $('input[name="isEnforceReturn"]:checked').val();
    if (finalParams.isEnforceReturn == '1') {
        finalParams.enforceReturnDateStr = paramsExtra.enforceReturnDateStr;
        finalParams.enforceReturnReason = paramsExtra.enforceReturnReason;
        finalParams.enforceReturnContent = paramsExtra.enforceReturnContent;
    }
    finalParams.isEndEnforce = $('input[name="isEndEnforce"]:checked').val();
    if (finalParams.isEndEnforce == '1') {
        finalParams.endEnforceSendDateStr = paramsExtra.endEnforceSendDateStr;
        finalParams.endSendType = paramsExtra.endSendType;
        finalParams.enforceEndReason = paramsExtra.enforceEndReason;
    }
    // 中止执行
    finalParams.isBreakEnforce = $('input[name="isBreakEnforce"]:checked').val();
    if (finalParams.isBreakEnforce == '1') {
        finalParams.breakEnforceDateStr = paramsExtra.breakEnforceDateStr;
        finalParams.relwaseEnforceDateStr = paramsExtra.relwaseEnforceDateStr;
        finalParams.enforceBreakReason = paramsExtra.enforceBreakReason;
    }
    var resultInfo = tools.requestJsonRs(
            "/coercionCaseCtrl/savePerformEnforce.action", finalParams);
    if (resultInfo.rtState == true) {
        $.MsgBox.Alert_auto("保存成功");
//        parent.document.getElementsByTagName('iframe')[1].contentWindow.initPerformDatagrid();
        enforceStep = resultInfo.rtData.enforceStep;
        switchTab(4);
    } else if (resultInfo.rtState == false) {
        $.MsgBox.Alert_auto("保存失败！");
    } else {
        $.MsgBox.Alert_auto("发生未知错误！");
    }
}

/*
 * 选择标签卡
 */
function selectTabs(){
	if(performId == null || performId == ''){
		switchTab(0);
	}else {
		if(enforceStep<4){
			switchTab(parseInt(enforceStep));
//			$('#measureInput_edit_Div').tabs('select',parseInt(enforceStep));
		}else if(enforceStep==4){
			$('#performInput_edit_Div').tabs('select',3);
		}
	}
}

//切换选项卡
function switchTab(index){
	if(enforceStep>=0 && enforceStep<4){
		$('#performInput_edit_Div').tabs('enableTab',index);
		if(enforceStep <= index ){
			for(var j=index;j<3;j++){
				$('#performInput_edit_Div').tabs('disableTab', j+1); 
			}
		}
	} // 获取选择的面
	$('#performInput_edit_Div').tabs('select', index);
}