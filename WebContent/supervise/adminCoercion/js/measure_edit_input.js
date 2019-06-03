/**
 * 
 */
var coercionDetails = [];
var coercionMeasureType = '';
var preMeasureType = '';
var optCtrl = [];
var measureId = '';
var measureType = '';
var srcCaseId = '';
var srcCaseType = '';
var coercionCaseId = '';
var departmentId = '';
var subjectId = '';
var addPower = [];
var powerJson = [];
var gistJson = [];
var swfUploadObj = null;
var enforceStep = null;
var caseCode = '';

function measureEditInit() {
	
    srcCaseId = $('#caseSourceId').val();
    srcCaseType = $('#caseSourceType').val();
    coercionCaseId = $('#coercionCaseId').val();
    measureType = $('#measureType').val();
    measureId = $('#measureId').val();
    subjectId = $('#subjectId').val();
    departmentId = $('#departmentId').val();
    enforceStep = $('#enforceStep').val();
    caseCode = $('#caseCode').val();
    //标签卡
    selectTabs();
    //初始化上传文件
    initFiles(measureId);
    //多附件快速上传
	uploadFiles();
	
    elemHideCtrl('2', preMeasureType, measureType);
    elemHideCtrl('3', preMeasureType, measureType);
    elemShowCtrl('2', measureType);
    preMeasureType = measureType;
    initComboBox();//初始化送达方式
    InputInfoCtrl();
}
/**
 * 初始化送达方式
 */
function initComboBox() {

    var params = {
        parentCodeNo : "COMMON_SENT_WAY",
        codeNo : "",
//        panelHeight : 'auto'
    };
    var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action",
            params);
    $('#measureDecision_sendWay').combobox({
        data : result.rtData,
        valueField : 'codeNo',
        textField : 'codeName',
        panelHeight : 'auto',
        prompt : '请选择',
        onLoadSuccess: function(){
        	var cdSendType = $('#measureDecision_sendWay').val();
        	if(cdSendType!=null&&cdSendType!=''){
        		$('#measureDecision_sendWay').combobox('setValue', cdSendType);
        	}
        },
        editable : false
    });
}


function doOpenListPage() {
    var params = {
        caseSourceId : srcCaseId,
        caseSourceType : srcCaseType,
        subjectId: subjectId,
        id : coercionCaseId
    }
    var url = contextPath + "/coercionCaseCtrl/measuresInput.action?";
    url = url + $.param(params);
    window.location.href = url;
}

function doInitCoercionDetailsTableByIds(params) {
    datagrid = $('#coercion_powerDetail_table').datagrid({
        url : contextPath + '/detailController/getLawDetailByIds.action',
        queryParams : params,
        pagination : false,
        singleSelect : false,
        // pageSize : 15,
        // pageList : [ 15, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        // 工具条对象
        checkbox : true,
        border : false,
        /* idField:'formId',//主键列 */
        fitColumns : true,
        // 列是否进行自动宽度适应
        nowrap : true,
        onLoadSuccess : function(data) {
        },
        columns : [ [ {
            field : 'id',
            checkbox : true,
            title : "ID",
            width : 20
        }, {
            field : 'lawName',
            title : '依据名称',
            width : 100
        }, {
            field : 'detailStrip',
            title : '条',
            width : 15
        }, {
            field : 'detailFund',
            title : '款',
            width : 15,
            formatter : function(value, rowData, rowIndex) {
                if (value == 0) {
                    return "";
                } else {
                    return value;
                }
            }
        }, {
            field : 'item',
            title : '项',
            width : 15,
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
            width : 200
        } ] ]
    });
}

/**
 * 添加强制依据弹框
 */
function doAddCoercionDetail() {
    var url = contextPath + "/supervise/power/addLawDetail.jsp";
    top.bsWindow(url, "添加强制依据",
            {
                width : "1000",
                height : "400",
                buttons : [ {
                    name : "保存",
                    classStyle : "btn-alert-blue "
                }, {
                    name : "关闭",
                    classStyle : "btn-alert-gray"
                } ],
                submit : function(v, h) {
                    var cw = h[0].contentWindow;
                    if (v == "保存") {
                        var lawDetails = cw.saveLawDetails();
                        if (coercionDetails == null
                                || coercionDetails.length == 0) {
                            for ( var index in lawDetails) {
                                coercionDetails.push(lawDetails[index].id);
                            }
                        } else {
                            for ( var index in lawDetails) {
                                if (coercionDetails
                                        .indexOf(lawDetails[index].id) == -1) {
                                    coercionDetails.push(lawDetails[index].id);
                                }
                            }
                        }
                        var params = {
                            ids : coercionDetails.join(",")
                        };
                        doInitCoercionDetailsTableByIds(params);

                        return true;
                    } else if (v == "关闭") {
                        return true;
                    }
                }
            });
}

function doReduceCoercionDetail() {
    var reduceDetails = $('#coercion_powerDetail_table').datagrid(
            'getSelections');
    for ( var index in reduceDetails) {
        var resultIndex = coercionDetails.indexOf(reduceDetails[index].id);
        if (resultIndex != -1) {
            coercionDetails.splice(resultIndex, 1);
        }
    }
    var params = {
        ids : coercionDetails.join(",")
    };
    doInitCoercionDetailsTableByIds(params);
}

function InputInfoCtrl() {
	//是否延期
	$('input[name="isDelayLimit"]').on('click', function() {
        if ('0' == $(this).val()) {
            $('.delay_limit .is_delay_limit').hide();
            $('#measureDelayDateEndStr').datebox({required:false});
        } else {
        	$('.delay_limit .is_delay_limit').show();
        	$('#measureDelayDateEndStr').datebox({disabled:false, validType:'date', required:true, missingMessage:'请选择强制措施延长截止日期' });
        }
    });
    // *** 查封、场所、财物
    // * 当事人是否在场
    // * 变化响应
    $('input[name="isClosePresence"]').on('click', function() {
        if ('2' == $(this).val()) {
            $('.close-isPresence-info').parents('tr').show();
        } else {
            $('.close-isPresence-info').parents('tr').hide();
        }
    });
    // * 查封扣押对象类型
    // * 变化响应
    $('input[name="elemType"]').on('change', function() {
        if ($(this).is(':checked')) {
            if ($(this).val() == '1') {
                $('.close-place-info').parents('tr').show();
            } else {
                $('.close-property-info').parents('tr').show();
            }
        } else {
            if ($(this).val() == '1') {
                $('.close-place-info').parents('tr').hide();
            } else {
                $('.close-property-info').parents('tr').hide();
            }
        }
    });
    // *** 扣押财物
    // * 当事人是否在场
    // * 变化响应
    $('input[name="isDetentionPresence"]').on('click', function() {
        if ('2' == $(this).val()) {
            $('.detention-isPresence-info').parents('tr').show();
        } else {
            $('.detention-isPresence-info').parents('tr').hide();
        }
    });
    // *** 限制人身自由
    // * 是否通知家属或所在单位
    // * 变化响应
    $('input[name="isNotifyFamily"]').on('click', function() {
        if ('2' == $(this).val()) {
            $('.decisionSend-info-tr').hide();
            $('.limit-reason-tr').show();
        } else {
            $('.limit-reason-tr').hide();
            $('.decisionSend-info-tr').show();
        }
    });
    $('#measureInput_measureType .radiobutton').on('click', function() {
        var value = $(this).find('.radiobutton-value').val();
        if (value == '100') {
            elemHideCtrl('2', preMeasureType, value);
            elemHideCtrl('3', preMeasureType, value);
            elemShowCtrl('2', value);
            preMeasureType = value;
            $('.close-info-tr').show();
        }
        if (value == '200') {
            elemHideCtrl('2', preMeasureType, value);
            elemHideCtrl('3', preMeasureType, value);
            elemShowCtrl('2', value);
            preMeasureType = value;
            $('.detention-info-tr').show();

        }
        if (value == '300') {
            elemHideCtrl('2', preMeasureType, value);
            elemHideCtrl('3', preMeasureType, value);
            elemShowCtrl('2', value);
            preMeasureType = value;
            $('.freeze-info-tr').show();
        }
        if (value == '400') {
            elemHideCtrl('2', preMeasureType, value);
            elemHideCtrl('3', preMeasureType, value);
            elemShowCtrl('2', value);
            preMeasureType = value;
            $('.limit-info-tr').show();

        }
        if (value == '900') {
            elemHideCtrl('2', preMeasureType, value);
            elemHideCtrl('3', preMeasureType, value);
            elemShowCtrl('2', value);
            preMeasureType = value;
        }
    });
    // 获取强制措施信息，尝试初始化职权依据表
    if (measureId != null && measureId != '') {
        var json = tools.requestJsonRs(contextPath +"/coercionCaseCtrl/getMeasureInfo.action",{id:measureId});
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

function elemShowCtrl(step, curType) {
	
	if ('0' == $('input[name="isDelayLimit"]:checked').val()) {
        $('.delay_limit .is_delay_limit').hide();
    } else if ('1' == $('input[name="isDelayLimit"]:checked').val()) {
        $('.delay_limit .is_delay_limit').show();
    }
    if (step == null || step == '') {
        return;
    }
    if (step == '2') {
        if (curType == null || curType == '') {
            return;
        }
        if (curType == '100') {
            $('.close-info-tr').show();
            // 当事人是否在场
            // 初始化
            $('input[name="isClosePresence"]').each(function() {
                if ($(this).is(':checked')) {
                    if ('2' == $(this).val()) {
                        $('.close-isPresence-info').parents('tr').show();
                    } else {
                        $('.close-isPresence-info').parents('tr').hide();
                    }
                }
            });
            // 查封扣押对象类型
            // 初始化
            $('input[name="elemType"]').each(function() {
                if ($(this).is(':checked')) {
                    if ($(this).val() == '1') {
                        $('.close-place-info').parents('tr').show();
                    } else {
                        $('.close-property-info').parents('tr').show();
                    }
                }
            });
        }
        if (curType == '200') {
            $('.detention-info-tr').show();
            // 当事人是否在场
            // 初始化
            $('input[name="isDetentionPresence"]').each(function() {
                if ($(this).is(':checked')) {
                    if ('2' == $(this).val()) {
                        $('.detention-isPresence-info').parents('tr').show();
                    } else {
                        $('.detention-isPresence-info').parents('tr').hide();
                    }
                }
            });
        }
        if (curType == '300') {
            $('.freeze-info-tr').show();
        }
        if (curType == '400') {
            $('.limit-info-tr').show();
            // 是否通知家属或所在单位
            // 初始化
            $('input[name="isNotifyFamily"]').each(function() {
                if ($(this).is(':checked')) {
                    if ('2' == $(this).val()) {
                        $('.decisionSend-info-tr').hide();
                        $('.limit-reason-tr').show();
                    } else {
                        $('.limit-reason-tr').hide();
                        $('.decisionSend-info-tr').show();
                    }
                }
            });
        }
    }
}

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
                $('.close-info-tr').hide();
                $('.close-isPresence-info').parents('tr').hide();
                $('.close-place-info').parents('tr').hide();
                $('.close-property-info').parents('tr').hide();
            }
            if (preType == '200') {
                $('.detention-info-tr').hide();
                $('.detention-isPresence-info').parents('tr').hide();
            }
            if (preType == '300') {
                $('.freeze-info-tr').hide();
            }
            if (preType == '400') {
                $('.limit-info-tr').hide();
                $('.limit-reason-tr').hide();
                $('.decisionSend-info-tr').show();
            }
        }
    } else if (step == '3') {
        if (curType == null || curType == '') {
            return;
        }
        if (curType == '300') {
            $('#measureDealWay_destory').parents('li').hide();
        }
        if (curType == '400') {
            $('#measureDealWay_destory').parents('li').hide();
            $('#measureDealWay_auction').parents('li').hide();
        }

        if (preType != null && preType != '') {
            if (preType != curType) {
                if (preType == '300' && curType != '400') {
                    $('#measureDealWay_destory').parents('li').show();
                }
                if (preType == '300' && curType == '400') {
                    // do nothing.
                }
                if (preType == '400' && curType != '300') {
                    $('#measureDealWay_destory').parents('li').show();
                    $('#measureDealWay_auction').parents('li').show();
                }
                if (preType == '400' && curType == '300') {
                    $('#measureDealWay_auction').parents('li').show();
                }
            }
        }
    }
}

/**
 * 查询实施主体下的职权
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
        buttons : [{
            name : "关闭",
            classStyle : "btn-alert-gray"
        }, {
            name : "保存",
            classStyle : "btn-alert-blue"
        }  ],
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
                if(addPower != null && addPower.length > 0){
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

initCoercionPowerTable = function(params) {
    datagrid = $('#measure_power_table')
            .datagrid(
                    {
                        url : contextPath
                                + '/powerCtrl/getPowerByActSubject.action',
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
                        columns : [ [
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
                                    halign : 'center'
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
                                                + "\")'><i class='fa fa-trash'></i> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;";
                                        return optStr;
                                    },
                                    width : 30,
                                    align : 'center'
                                } ] ]
                    });
}

initCoercionGistTable = function(params) {
    datagrid = $('#measure_gist_table').datagrid(
            {
                url : contextPath + '/powerCtrl/findGistsByPowerIds.action',
                queryParams : params,// 查询参数
                pagination : false,
                // pageSize : 5,
                // pageList : [5, 10, 20],
                view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
                toolbar : '#toolbar', // 工具条对象
                checkbox : true,
                striped:true,
                border : false,
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
                                    $("#measure_gist_table").datagrid(
                                            "selectRow", index);// 如果数据行为已选中则选中改行
                                    break;
                                }
                            }
                        } else {
                            $("#measure_gist_table").datagrid("selectRow",
                                    index);// 如果数据行为已选中则选中改行
                        }
                    });
                },
                columns : [ [ {
                    field : 'id',
                    checkbox : true,
                    title : "ID",
                    width : 20,
                    hidden : true,
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
                    halign : 'center'
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
function saveMeasureInfo(){
	var tab = $('#measureInput_edit_Div').tabs('getSelected');
    var index = $('#measureInput_edit_Div').tabs('getTabIndex',tab);
    if(index == 0){
    	saveMeasureType();//行政强制措施种类
    }else if(index == 1){
    	saveMeasureApply();//申请与批准保存
    }else if(index == 2){
    	saveMeasureResult();//保存行政强制措施处罚决定
    	return true;
    }
}

/**
 * 行政强制措施种类
 * @returns {Boolean}
 */
function saveMeasureType() {
    var measureType = '';
    $('#measureInput_measureType input[name="measureType"]').each(function() {
        if ($(this).is(':checked')) {
            measureType = $(this).val();
        }
    });
    if (measureType == '') {
        alert("请选择强制措施种类！");
        return false;
    }
    var params = {
        measureType : measureType,
        id : measureId,
        caseSourceId : srcCaseId,
        caseSourceType : srcCaseType,
        coercionCaseId : coercionCaseId,
        subjectId : subjectId,
        departmentId: departmentId,
        caseCode: caseCode
    }
    var resultInfo = tools.requestJsonRs(contextPath + "/coercionCaseCtrl/saveMeasureType.action", params);
    var rtData = resultInfo.rtData;
    if (rtData.flag == 1) {
        coercionCaseId = rtData.coercionCaseId;
        $('#coercionCaseId').val(coercionCaseId);
        measureId = rtData.id;
        $('#measureId').val(measureId);
        
        $.MsgBox.Alert_auto("保存成功");
        switchTab(1);  
//        parent.document.getElementsByTagName('iframe')[1].contentWindow.initMeasureDatagrid();
    } else if (resultInfo.rtData == -1) {
        $.MsgBox.Alert_auto("保存失败！");
    } else {
        $.MsgBox.Alert_auto("发生未知错误！");
    }
}
/**
 * 申请与批准保存
 */
function saveMeasureApply() {
	if($("#meaSureApply_form").form('enableValidation').form('validate')){
		var params = tools.formToJson($('#meaSureApply_form'));
	    // 获取强制措施类型
	    var measureType = '';
	    $('#measureInput_measureType input[name="measureType"]').each(function() {
	        if ($(this).is(':checked')) {
	            measureType = $(this).val();
	        }
	    });
	    params.id = $('#measureId').val();
	    if (measureType == '') {
	        alert("无法获取强制措施种类，请刷新页面尝试重新保存！");
	        return false;
	    }
	    var finalParams = {
	        measureType : measureType,
	        id : measureId,
	        caseSourceId : srcCaseId,
	        caseSourceType : srcCaseType,
	        coercionCaseId : coercionCaseId,
	        
	    }
	    // baseInfo
	    finalParams.applyDateStr = params.applyDateStr;
	    finalParams.approveDateStr = params.approveDateStr;
	    finalParams.approvePerson = params.approvePerson;
	    finalParams.coercionCode = params.coercionCode;
	    finalParams.cdSendDateStr = params.cdSendDateStr;
	    finalParams.cdSendType = params.cdSendType;
	    finalParams.measureDateLimitStartStr = params.measureDateLimitStartStr;
	    finalParams.measureDateLimitEndStr = params.measureDateLimitEndStr;
	    finalParams.measureEnforceDateStr = params.measureEnforceDateStr;
	    finalParams.isDelayLimit = params.isDelayLimit;
	    finalParams.measureDelayDateEndStr = params.measureDelayDateEndStr;
	    // *各种类强制措施字段校验与清理
	    // 查封
	    if (measureType == '100') {
	        // 当事人是否在场
	        finalParams.isPartyPresent = $('input[name="isClosePresence"]:checked')
	                .val();
	        if (finalParams.isClosePresence == '2') {
	            finalParams.witnessName = params.closeWitnessName;
	            finalParams.witnessContactWay = params.closeWitnessContactWay;
	        }
	        // 强制对象类型
	    }
	    // 扣押
	    if (measureType == '200') {
	        // 当事人是否在场
	        finalParams.isDetentionPartyPresent = $(
	                'input[name="isDetentionPresence"]:checked').val();
	        if (finalParams.isDetentionPartyPresent == '2') {
	            finalParams.detentionWitnessName = params.detentionWitnessName;
	            finalParams.detentionWitnessContactWay = params.detentionWitnessContactWay;
	        }
	        finalParams.detentionPropertyInfo = params.detentionProperty;
	        finalParams.detentionReason = params.detentionReason;
	    }
	    // 冻结
	    if (measureType == '300') {
	        finalParams.freezeNumber = params.freezeNumber;
	        finalParams.freezeOrganization = params.freezeOrganization;
	        finalParams.freezeNoticeDateStr = params.freezeNoticeDateStr;
	    }
	    // 限制人身自由
	    if (measureType == '400') {
	        // 是否通知家属或所在单位
	        finalParams.isNoticeFamily = $('input[name="isNoticeFamily"]:checked')
	                .val();
	        if (finalParams.isNoticeFamily == '2') {
	            finalParams.notNoticeReason = params.notNoticeReason;
	            finalParams.cdSendDateStr = '';
	            finalParams.cdSendType = '';
	        }
	    }
	    // 强制职权
	    var powerList = $("#measure_power_table").datagrid("getRows");
	    
	    if (powerList == null || powerList.length < 1) {
	        alert("请添加至少1条强制职权");
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

	    var gistList = $("#measure_gist_table").datagrid("getRows");
	    if (gistList == null || gistList.length < 1) {
	        alert("请选择至少1条强制依据");
	        return false;
	    }
	    gistJson = []// 违法依据对象
	    for (var i = 0; i < gistList.length; i++) {
	        var obj = {};
	        obj.gistId = gistList[i].id;
	        obj.lawName = gistList[i].lawName;
	        if (gistList[i].gistStrip == null || gistList[i].gistStrip == '') {
	            obj.strip = 0;
	        } else {
	            obj.strip = gistList[i].gistStrip;
	        }
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
	            obj.catalog = 0;
	        } else {
	            obj.catalog = gistList[i].gistCatalog;
	        }
	        obj.content = gistList[i].content;
	        gistJson.push(obj);
	    }
	    /*违法依据json*/
	    finalParams.gistJsonStr = tools.parseJson2String(gistJson);

	    var resultInfo = tools.requestJsonRs(contextPath +
	            "/coercionCaseCtrl/saveMeasureApply.action", finalParams);
	    if (resultInfo.rtState == true) {
	        $.MsgBox.Alert_auto("保存成功");
	        /*回调上级页面的方法（页面在第二个iframe中）*/
//	        parent.document.getElementsByTagName('iframe')[1].contentWindow.initMeasureDatagrid();
	        enforceStep = resultInfo.rtData.enforceStep;
	        switchTab(2);
	    } else if (resultInfo.rtState == false) {
	        $.MsgBox.Alert_auto("保存失败！");
	    } else {
	        $.MsgBox.Alert_auto("发生未知错误！");
	    }
	}
}
/**
 * 保存行政强制措施处罚决定
 */
function saveMeasureResult() {
    var measureType = '';
    $('#measureInput_measureType input[name="measureType"]').each(function() {
        if ($(this).is(':checked')) {
            measureType = $(this).val();
        }
    });
    if (measureType == '') {
        alert("请选择强制措施种类！");
        return false;
    }
    var finalParams = {
        measureType : measureType,
        id : measureId,
        caseSourceId : srcCaseId,
        caseSourceType : srcCaseType,
        coercionCaseId : coercionCaseId
        
    }
    finalParams.attaches = $('#attaches').val();
    if(finalParams.attaches==null||finalParams.attaches==''){
    	$.MsgBox.Alert_auto("请上传强制决定书");
    	return false;
    }
    finalParams.measureDealWay = $('input[name="measureDealWay"]:checked')
            .val();
    var resultInfo = tools.requestJsonRs(contextPath +
            "/coercionCaseCtrl/saveMeasureResult.action", finalParams);
    if (resultInfo.rtState == true) {
        $.MsgBox.Alert_auto("保存成功");
        /*回调上级页面的方法（页面在第二个iframe中）*/
        enforceStep = resultInfo.rtData.enforceStep;
//        parent.document.getElementsByTagName('iframe')[1].contentWindow.initMeasureDatagrid();
    } else if (resultInfo.rtState == false) {
        $.MsgBox.Alert_auto("保存失败！");
    } else {
        $.MsgBox.Alert_auto("发生未知错误！");
    }
}
/**
 * 初始化文件
 */
function initFiles(_id){
	if(_id != null && _id !=''){
		var json = tools.requestJsonRs(contextPath + "/coercionCaseCtrl/getFilelistById.action",{id: _id});
		var attachModels = json.rtData;
		if(attachModels != null){
			var attache = [];
		    for(var i=0;i<attachModels.length;i++){
		        attachModels[i].priv = 1+2+4+8+16;
		        var attachElement = tools.getAttachElement(attachModels[i]);
		        $("#attachDiv").append(attachElement);
		        attache.push(attachModels[i].sid);
		    }
		    attaches = attache.join(",");
		    $('#attaches').val(attaches);
		}
	}
}
/**
 * 上传文件
 * @returns {Boolean}
 */
function uploadFiles(){
	//多附件快速上传	
	swfUploadObj = new TeeSWFUpload({
		fileContainer:"fileContainer2",//文件列表容器
		renderContainer:"renderContainer2",//渲染容器
		uploadHolder:"uploadHolder2",//上传按钮放置容器
		valuesHolder:"attaches",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无	
		},
		renderFiles:true,//渲染附件
		post_params:{model:"coercionMeasure"}//后台传入值，model为模块标志
	});
}


/*
 * 选择标签卡
 */
function selectTabs(){
	if(measureId == null || measureId == ''){
		switchTab(0);
	}else {
		if(enforceStep<3){
			switchTab(parseInt(enforceStep));
//			$('#measureInput_edit_Div').tabs('select',parseInt(enforceStep));
		}else if(enforceStep==3){
			$('#measureInput_edit_Div').tabs('select',2);
		}
	}
}

//切换选项卡
function switchTab(index){
	if(enforceStep>=0 && enforceStep<3){
		$('#measureInput_edit_Div').tabs('enableTab',index);
		if(enforceStep <= index ){
			for(var j=index;j<2;j++){
				$('#measureInput_edit_Div').tabs('disableTab', j+1); 
			}
		}
	} // 获取选择的面
	$('#measureInput_edit_Div').tabs('select', index);
}
