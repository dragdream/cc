var caseId = $('#comm_case_add_present_caseId').val(); //案件ID
var editFlag = $('#common_case_add_present_editFlag').val(); //编辑标识（1新增，2修改，3查看）
var isNext = $('#common_case_add_present_isNext').val();//tab页签标识（立案0,调查取证1,处罚决定2,处罚执行3,结案4）
var subjectId = $('#common_case_add_present_subjectId').val();//主体ID
var departmentId = $('#common_case_add_present_departmentId').val();//部门ID
var presentModelId = $('#common_case_add_present_modelId').val();//弹框modelId
var revokePunishJson = [];//撤销原处罚决定json对象
var endJson = [];//终结案件json对象
var isTrueJson = [
	{codeNo:'1', codeName: '是'},
	{codeNo:'0', codeName: '否'}
];

/**
 * 默认加载方法
 */
function doInitPresent(){
	initiRadiobutton();
	initPunishDecisionExecuteWay();
}

/**
 * 初始化（是否）单选框
 */
function initiRadiobutton(){
	initPunishDecisionExecute();// 初始化是否执行处罚
	initStagedExection();// 初始化是否分期执行
	initDelayedExection();// 初始化是否延期执行
}

/**
 * 初始化是否执行处罚决定
 * @param data
 */
function initPunishDecisionExecute(){
	initJsonListRadio(isTrueJson, 'isPunishDecisionExecute');
	var punishDecisionExecute = $('#common_isPunishDecisionExecute').val();
	if(punishDecisionExecute!=null && punishDecisionExecute!=''){
		$("#isPunishDecisionExecute" + punishDecisionExecute).radiobutton('check');
		if(punishDecisionExecute=="0"){
			$('#punishDecisionExecute_tr').hide();
            $('#stagesExection_tr').hide();
            $('#delayedExection_tr').hide();
//            $('#punishDecisionFinishDateStr').datebox({disabled:true,required:false});
//            $('#punishDecisionExecuteWay').combobox({disabled:true,required:false});
		}else if(punishDecisionExecute=="1"){
			$('#punishDecisionExecute_tr').show();
        	$('#stagesExection_tr').show();
        	$('#delayedExection_tr').show();
        	$('#punishDecisionFinishDateStr').datebox({disabled:false, validType:'date', required:true, missingMessage:'请选择执行完成日期' });
        	$('#punishDecisionExecuteWay').combobox({disabled:false, required:true, missingMessage:'请选择执行方式' });
		}
	}
	
	$('#isPunishDecisionExecute1').radiobutton({
        onChange: function(check){
            if(check){
            	$('#punishDecisionExecute_tr').show();
	        	$('#stagesExection_tr').show();
	        	$('#delayedExection_tr').show();
	        	$('#punishDecisionFinishDateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择执行完成日期' });
	        	$('#punishDecisionExecuteWay').textbox({disabled:false, required:true, missingMessage:'请选择执行方式' });
	            $('#isStagedExection0').radiobutton({disabled:false });
	            $('#isStagedExection1').radiobutton({disabled:false });
	            $('#isDelayedExection0').radiobutton({disabled:false });
	            $('#isDelayedExection1').radiobutton({disabled:false });
            }else{
            	$('#punishDecisionExecute_tr').hide();
	            $('#stagesExection_tr').hide();
	            $('#delayedExection_tr').hide();
	            $('#punishDecisionFinishDateStr').textbox({disabled:true,required:false});
	            $('#punishDecisionExecuteWay').textbox({disabled:true,required:false});
	            $('#isStagedExection0').radiobutton({disabled:true });
	            $('#isStagedExection1').radiobutton({disabled:true });
	            $('#isDelayedExection0').radiobutton({disabled:true });
	            $('#isDelayedExection1').radiobutton({disabled:true });
            }
        }
    });

}

/**
 * 初始话是否分期执行
 */
function initStagedExection(){
	initJsonListRadio(isTrueJson, 'isStagedExection');
	var stagedExection = $('#common_isStagedExection').val();
	if(stagedExection!=null && stagedExection!=''){
		$("#isStagedExection" + stagedExection).radiobutton('check');
	}
	$('#isStagedExection1').radiobutton({
        onChange: function(check){
            if(check && $("#isDelayedExectionTd input[name='isDelayedExection']:checked").val()=='1'){
            	$("#isDelayedExection0").radiobutton('check');
            }
        }
    });
}

/**
 * 初始化是否延期执行
 */
function initDelayedExection(){
	initJsonListRadio(isTrueJson, 'isDelayedExection');
	var delayedExection = $('#common_isDelayedExection').val();
	if(delayedExection!=null && delayedExection!=''){
		$("#isDelayedExection" + delayedExection).radiobutton('check');
	}
	$('#isDelayedExection1').radiobutton({
        onChange: function(check){
            if(check && $("#isStagedExectionTd input[name='isStagedExection']:checked").val()=='1'){
            	$("#isStagedExection0").radiobutton('check');
            }
        }
    });
}

/**
 * 初始化执行方式
 */
function initPunishDecisionExecuteWay() {
	var json = tools.requestJsonRs(contextPath +"/sysCode/getSysCodeByParentCodeNo.action",{codeNo : 'PUNISH_DECISION_EXECUTE_WAY'});
	if (json.rtState) {
		$('#punishDecisionExecuteWay').combobox({
			data : json.rtData,
			valueField : 'codeNo',
			textField : 'codeName',
			panelHeight : 'auto',
			prompt : '请选择',
			onLoadSuccess : function() {
				var punishDecisionExecuteWay = $('#punishDecisionExecuteWay').combobox('getValue');
				if (punishDecisionExecuteWay != null && punishDecisionExecuteWay != '') {
					$('#punishDecisionExecuteWay').combobox('setValue',punishDecisionExecuteWay);
				}
			},
			editable : false
		});
	}
}

/**
 * 保存 处罚执行
 * @returns
 */
function doPresentSave(){
	var validate = $("#common_case_add_4_present_form").form('enableValidation').form('validate')
			&& radioValidate("isPunishDecisionExecuteTd","isPunishDecisionExecute","请选择是否执行处罚决定")
	if(validate){
        var params = tools.formToJson($("#common_case_add_4_present_form"));
//        var delayedExection = $("#common_case_add_4_present_form input[name='delayedExection']:checked").is(':checked');
//        var stagesExection = $("#common_case_add_4_present_form input[name='stagesExection']").is(':checked');
//        if (!(delayedExection || stagesExection)) {
//            $.MsgBox.Alert_auto("请选择延期执行，或者分期执行");
//            $('html, body').animate({scrollTop: $("#delayedExection_td").offset().top}, 500);
//            $('#btn').linkbutton('enable');
//            return false;
//        }
//        var isPartyExecution = $("#common_case_add_4_present_form input[name='isPartyExecution']").is(':checked');
//        var isOrganEnforce = $("#common_case_add_4_present_form input[name='isOrganEnforce']").is(':checked');
//        if(!(isPartyExecution || isOrganEnforce)){
//            $.MsgBox.Alert_auto("请选择当事人主动履行，或者是否行政强制执行");
//            $('html, body').animate({scrollTop: $("#isPartyExecution_td").offset().top}, 500);
//            $('#btn').linkbutton('enable');
//            return false;
//        }
        
        params.id = caseId;
        params.isNext = 4; //处罚决定保存tabs页签index
        params.editFlag = parseInt(editFlag);
        params.grading = '04';
        params.currentState = '04';
        var json = tools.requestJsonRs("/caseCommonBaseCtrl/saveFilingStage.action", params);
        if(json.rtState){
            //保存成功，进行下一步
            var tabId="common_case_add_tabs";
            var title = "结案";
            var url = '/caseCommonBaseCtrl/commonCaseAddGrading.action';
            var pageUrl = '/supervise/caseManager/commonCase/common_case_add_5_execute.jsp';
            var grading = '05';
            var paramsObj = {
                    pageUrl: pageUrl,
                    grading: grading,
                    caseId: caseId,
                    editFlag: editFlag,
                    saveEvent: '01', //点击保存事件
                    modelId: presentModelId
            }
            url = url + "?"+$.param(paramsObj);
            parent.addTab(tabId, title, url);
            editFlag = '2';
            $('#btn').linkbutton('enable');
            return json.rtState;
        }else{
            $.MsgBox.Alert_auto("保存失败！");
            $('#btn').linkbutton('enable');
            return json.rtState;
        }
    }else{
    	$('#btn').linkbutton('enable');
    	return false;
    }
}








































function checkRadioAndCheckbox(form) {
    var boolean = false;
    var o1 = $(form.delayedExection[0]);
    var o2 = $(form.stagesExection[0]);
    if(o1.validatebox('isValid') || o2.validatebox('isValid')){
        boolean = true;
    }else{
        if (!o1.validatebox('isValid')){
            o1.parent().addClass('validatebox-invalid');
            o1.parent().attr("onmouseover","return showInfo.showLayer(event,'delayedExection_message');");
            o1.parent().attr("onmouseout","return showInfo.hideLayer(event,'delayedExection_message');");
        }else {
            o1.parent().removeClass('validatebox-invalid');
            boolean = true;
        }
        if (!o2.validatebox('isValid')) {
            o2.parent().addClass('validatebox-invalid');
            o2.parent().attr("onmouseover","return showInfo.showLayer(event,'stagesExection_message');");
            o2.parent().attr("onmouseout","return showInfo.hideLayer(event,'stagesExection_message');");
        }else {
            o2.parent().removeClass('validatebox-invalid');
            boolean = true;
        }
    }
    return boolean;
}

var showInfo = new function() {
    this.showLayer = function(e, id) {
        var position_left = e.currentTarget.offsetLeft+event.currentTarget.clientWidth;
        var position_top = e.currentTarget.offsetTop+event.currentTarget.clientHeight;
        with (document.getElementById(id + "").style) {
            display = "block";
            left = position_left + "px";
            top = position_top + "px"; 
        }
        if (window.event) {
            window.event.cancelBubble = true;
        } else {
            if (e) {
                e.preventDefault();
            }
        }
    };
    this.hideLayer = function(e, id) {
        with (document.getElementById(id + "").style) {
            display = "none";
        }
        if (window.event) {
            window.event.cancelBubble = true;
        } else {
            if (e) {
                e.preventDefault();
            }
        }
    };
};

/**
 * 修改 方法
 * @returns
 */
function doInitEditPresent(){
    var grading = '04';//处罚执行阶段
    var json = tools.requestJsonRs("/caseCommonBaseCtrl/findCaseCommonBaseById.action?id=" + caseId +"&grading="+grading);
    if(json.rtState){
        //行政处罚决定执行
//        var isPunishDecisionExecute = json.rtData.isPunishDecisionExecute;
//        if(isPunishDecisionExecute == '1'){
//            $('#isPunishDecisionExecut_td .case-correct-date').show();
//            $('#isPunishDecisionExecute').checkbox({checked: true});
//            $('#punishDecisionExecutDateStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'执行日期'});
//        }
        //是否检查整改
        var isOrdercorrectExection = json.rtData.isOrdercorrectExection;
        if(isOrdercorrectExection == '1'){
            $('#isOrdercorrectExection_td .case-correct-date').show();
            $('#isOrdercorrectExection').checkbox({checked: true});
            $('#correctDateStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'请选择整改复查日期'});
            $("#correctResult").textbox({disabled:false});
        }
        //延期
        var delayedExection = json.rtData.delayedExection;
        if(delayedExection == '1'){
            $('#delayedExection_td .case-correct-date').show();
            $('#delayedExection').checkbox({checked: true});
            $('#stagesExection').checkbox('uncheck');
            $('#postponedToDelayStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'请选择延期至'});
            $('#applyDateDelayStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'请选择延期申请日期'});
            $('#approvalDateDelayStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'请选择延期批准日期'});
        }
        //分期
        var stagesExection = json.rtData.stagesExection;
        if(stagesExection == '1'){
            $('#stagesExection_td .case-correct-date').show();
            $('#stagesExection').checkbox({checked: true});
            $('#delayedExection').checkbox('uncheck');
            $('#deadlineStageStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'请选择分期最后期限'});
            $('#applyDateStageStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'请选择分期申请日期'});
            $('#approvalDateStageStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'请选择分期批准日期'});
        }
        //当事人履行
        var isPartyExecution = json.rtData.isPartyExecution;
        if(isPartyExecution == '1'){
            $('#isPartyExecution_td .case-correct-date').show();
            $('#isPartyExecution').checkbox({checked: true});
            $('#isOrganEnforce').checkbox('uncheck');
            $('#partyActivePerforDateStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'请选择当事人执行日期'});
        }
        
    }
}


/**
 * 行政处罚决定是否执行
 * @returns
// */
//function initCommonIsPunishDecisionExecut(){
//    $('#isPunishDecisionExecut').checkbox({
//    	
////        label: '行政处罚决定执行',
////        value: '1',
////        width: 15,
////        height: 15,
////        labelAlign: 'left',
////        labelPosition: 'after',
////        labelWidth: '130',
////        onChange: function(){
////            var isPunishDecisionExecut = $("#common_case_add_4_present_form input[name='isPunishDecisionExecut']").is(':checked');
////            if(isPunishDecisionExecut){
////                $('#isPunishDecisionExecut_td .case-correct-date').show();
////                $('#punishDecisionExecutDateStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'执行日期'});
////            }else{
////                $('#punishDecisionExecutDateStr').datebox({disabled:true});
////                $('#punishDecisionExecutDateStr').form('disableValidation');
////                $('#punishDecisionExecutDateStr').datebox('setValue', "");
////                $('#isPunishDecisionExecut_td .case-correct-date').hide();
////            }
////        }
//    });
//}

/**
 * 是否检查整改
 * @returns
 */
function initCommonIsOrdercorrectExection(){
    $('#isOrdercorrectExection').checkbox({
        label: '检查整改',
        value: '1',
        width: 15,
        height: 15,
        labelAlign: 'left',
        labelPosition: 'after',
        labelWidth: '130',
        onChange: function(){
            var isOrdercorrectExection = $("#common_case_add_4_present_form input[name='isOrdercorrectExection']").is(':checked');
            if(isOrdercorrectExection){
                $('#isOrdercorrectExection_td .case-correct-date').show();
                $('#correctDateStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'请选择整改复查日期'});
                $("#correctResult").textbox({disabled:false});
            }else{
                $('#correctDateStr').datebox({disabled:true});
                $('#correctDateStr').form('disableValidation');
                $('#correctDateStr').datebox('setValue', "");
                
                $('#correctResult').textbox({disabled:true});
//                $('#correctResult').form('disableValidation');
//                $('#correctResult').textbox('setValue', "");
                $('#isOrdercorrectExection_td .case-correct-date').hide();
            }
        }
    });
}

/**
 * 延期执行
 * @returns
 */
function initCommonDelayedExection(){
    $('#delayedExection').checkbox({
        label: '延期执行',
        value: '1',
        width: 15,
        height: 15,
        labelAlign: 'left',
        labelPosition: 'after',
        labelWidth: '130',
        onChange: function(){
            var delayedExection = $("#common_case_add_4_present_form input[name='delayedExection']").is(':checked');
            if(delayedExection){
                $('#delayedExection_td .case-correct-date').show();
                $('#stagesExection').checkbox('uncheck');
                
                $('#postponedToDelayStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'请选择延期至'});
                $('#applyDateDelayStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'请选择延期申请日期'});
                $('#approvalDateDelayStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'请选择延期批准日期'});
            }else{
                $('#postponedToDelayStr').datebox({disabled:true});
                $('#postponedToDelayStr').form('disableValidation');
                $('#postponedToDelayStr').datebox('setValue', "");
                
                $('#applyDateDelayStr').textbox({disabled:true});
                $('#applyDateDelayStr').form('disableValidation');
                $('#applyDateDelayStr').textbox('setValue', "");
                
                $('#approvalDateDelayStr').datebox({disabled:true});
                $('#approvalDateDelayStr').form('disableValidation');
                $('#approvalDateDelayStr').datebox('setValue', "");
                
                $('#delayedExection_td .case-correct-date').hide();
                
            }
        }
    });
}

/**
 * 分期执行
 * @returns
 */
function initCommonStagesExection(){
    $('#stagesExection').checkbox({
        label: '分期执行',
        value: '1',
        width: 15,
        height: 15,
        labelAlign: 'left',
        labelPosition: 'after',
        labelWidth: '130',
        onChange: function(){
            var stagesExection = $("#common_case_add_4_present_form input[name='stagesExection']").is(':checked');
            if(stagesExection){
                $('#stagesExection_td .case-correct-date').show();
                
                $('#delayedExection').checkbox('uncheck');
                
                $('#deadlineStageStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'请选择分期最后期限'});
                $('#applyDateStageStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'请选择分期申请日期'});
                $('#approvalDateStageStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'请选择分期批准日期'});
            }else{
                $('#deadlineStageStr').datebox({disabled:true});
                $('#deadlineStageStr').form('disableValidation');
                $('#deadlineStageStr').datebox('setValue', "");
                
                $('#applyDateStageStr').textbox({disabled:true});
                $('#applyDateStageStr').form('disableValidation');
                $('#applyDateStageStr').textbox('setValue', "");
                
                $('#approvalDateStageStr').datebox({disabled:true});
                $('#approvalDateStageStr').form('disableValidation');
                $('#approvalDateStageStr').datebox('setValue', "");
                
                $('#stagesExection_td .case-correct-date').hide();
            }
        }
    });
}


/**
 * 当事人主动履行
 * @returns
 */
function initCommonIsPartyExecution(){
    $('#isPartyExecution').checkbox({
        label: '当事人主动履行',
        value: '1',
        width: 15,
        height: 15,
        labelAlign: 'left',
        labelPosition: 'after',
        labelWidth: '130',
        onChange: function(){
            var isPartyExecution = $("#common_case_add_4_present_form input[name='isPartyExecution']").is(':checked');
            if(isPartyExecution){
                $('#isPartyExecution_td .case-correct-date').show();
                $('#isOrganEnforce').checkbox('uncheck');
                $('#partyActivePerforDateStr').datebox({required:true, validType:'date', disabled:false, missingMessage:'请选择当事人执行日期'});
            }else{
                $('#partyActivePerforDateStr').datebox({disabled:true});
                $('#partyActivePerforDateStr').form('disableValidation');
                $('#partyActivePerforDateStr').datebox('setValue', "");
                $('#isPartyExecution_td .case-correct-date').hide();
            }
        }
    });
}



/**
 * 是否强制执行
 * @returns
 */
function initCommonIsOrganEnforce(){
    $('#isOrganEnforce').checkbox({
        label: '行政强制执行',
        value: '1',
        width: 15,
        height: 15,
        labelAlign: 'left',
        labelPosition: 'after',
        labelWidth: '130',
        onChange: function(){
            var isOrganEnforce = $("#common_case_add_4_present_form input[name='isOrganEnforce']").is(':checked');
            if(isOrganEnforce){
                $('#isOrganEnforce_td .case-correct-date').show();
                $("#common_case_enforce_link").attr("onclick","doInitEnforce();return false;");
                $('#isPartyExecution').checkbox('uncheck');
            }else{
                $("#common_case_enforce_link").removeAttr("onclick");
                $('#isOrganEnforce_td .case-correct-date').hide();
            }
        }
    });
}

/**
 * 行政强制执行
 * @returns
 */
function doInitEnforce(){
    var params = {
         subjectId: subjectId,
         caseSourceType: 200,   //处罚来源
         caseSourceId: caseId,
         departmentId:departmentId
    }
    var url=contextPath+"/coercionCaseCtrl/performsInput.action?"+$.param(params);
    top.bsWindow(url ,"行政强制行为管理",{width:"1000",height:"500",buttons:
        [
            {name:"关闭",classStyle:"btn-alert-gray"}
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if(v=="关闭"){
                return true;
            }
        }
    });
}


/**
 * 撤销原处罚决定
 * @returns
 */
function doRevokePunishment(){
    var caseName = $('#common_case_add_present_caseName').val();
    var caseCode = $('#common_case_add_present_caseCode').val();
    var params = {
            caseId: caseId,
            subjectId: subjectId,
            isNext: isNext,
            editFlag: editFlag,
            caseName: caseName,
            caseCode: caseCode,
        }
    if('2' == editFlag){
        var revokePunishJsonStr = $('#common_case_add_present_revokePunishJsonStr').val();
        if (revokePunishJsonStr != null && revokePunishJsonStr != ''){
            revokePunishJson = JSON.parse(revokePunishJsonStr);
            params.revokePunishmentDateStr = revokePunishJson.revokePunishmentDateStr;
            params.approvePerson = revokePunishJson.approvePerson;
            params.approveDateStr = revokePunishJson.approveDateStr;
            params.revokePunishmentReason = revokePunishJson.revokePunishmentReason;
            params.revokePunishType = revokePunishJson.revokePunishType;
        }
    }
    var url=contextPath+"/caseCommonRevokePunishCtrl/commonCaseAddRevokePunish.action?"+$.param(params);
    top.bsWindow(url ,"撤销原处罚决定",{width:"650",height:"500",buttons:
        [
            {name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "保存") {
                var params = cw.initReovkePunishForm();
                if(params != null){
                    var obj = {};
                    revokePunishJson = [];
                    obj.revokePunishmentDateStr = params.revokePunishmentDateStr;
                    obj.approvePerson = params.approvePerson;
                    obj.approveDateStr = params.approveDateStr;
                    obj.revokePunishmentReason = params.revokePunishmentReason;
                    obj.revokePunishType = params.revokePunishType;
                    obj.caseName = caseName;
                    obj.caseCode = caseCode;
                    revokePunishJson.push(obj);
                    //撤销原处罚决定json字符串
                    params.revokePunishJsonStr = tools.parseJson2String(revokePunishJson);
                    
                    params.id = caseId;
                    params.isNext = 4; //结案保存tabs页签index
                    params.editFlag = parseInt(editFlag);
                    params.grading = '04';
                    params.currentState = '04';
                    params.closedState = '06';
                    params.closedCaseReason = params.revokePunishmentReason;
                    var json = tools.requestJsonRs("/caseCommonBaseCtrl/saveClosedState.action", params);
                    if(json.rtState){
                        //保存成功，进行下一步
                        var tabId="common_case_add_tabs";
                        var title = "结案";
                        var url = '/caseCommonBaseCtrl/commonCaseAddGrading.action';
                        var pageUrl = '/supervise/caseManager/commonCase/common_case_add_5_execute.jsp';
                        var grading = '05';
                        var paramsObj = {
                                pageUrl: pageUrl,
                                grading: grading,
                                caseId: caseId,
                                editFlag: editFlag,
                                saveEvent: '01', //点击保存事件
                                modelId: presentModelId
                        }
                        url = url + "?"+$.param(paramsObj);
                        parent.addTab(tabId, title, url);
                        return json.rtState;
                    }else{
                        $.MsgBox.Alert_auto("保存失败！");
                        return json.rtState;
                    }
                }
            } else if(v=="关闭"){
                return true;
            }
        }
    });
}

/**
 * 终结 
 * @returns
 */
function doCommonCaseEnd(){
    var caseName = $('#common_case_add_present_caseName').val();
    var caseCode = $('#common_case_add_present_caseCode').val();
    var params = {
            caseId: caseId,
            subjectId: subjectId,
            isNext: isNext,
            editFlag: editFlag,
            caseName: caseName,
            caseCode: caseCode,
        }
    if('2' == editFlag){
        var endJsonStr = $('#common_case_add_present_endJsonStr').val();
        endJson = [];
        if (endJsonStr != null && endJsonStr != ''){
            endJson = JSON.parse(endJsonStr);
            params.endDateStr = endJson.endDateStr;
            params.approvePerson = endJson.approvePerson;
            params.approveDateStr = endJson.approveDateStr;
            params.endReason = endJson.endReason;
        }
    }
    var url=contextPath+"/caseCommonEndCtrl/commonCaseAddEnd.action?"+$.param(params);
    top.bsWindow(url ,"终结",{width:"600",height:"450",buttons:
        [
            {name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "保存") {
                var params = cw.initEndForm();
                if(params != null){
                    var obj = {};
                    endJson = [];
                    obj.endDateStr = params.endDateStr;
                    obj.approvePerson = params.approvePerson;
                    obj.approveDateStr = params.approveDateStr;
                    obj.endReason = params.endReason;
                    obj.caseName = caseName;
                    obj.caseCode = caseCode;
                    endJson.push(obj);
                    //终结案件json字符串
                    params.endJsonStr = tools.parseJson2String(endJson);
                    
                    params.id = caseId;
                    params.isNext = 4; //结案 tabs页签index
                    params.editFlag = parseInt(editFlag);
                    params.grading = '04';
                    params.currentState = '04';
                    params.closedState = '04';
                    params.closedCaseReason = params.endReason;
                    var json = tools.requestJsonRs("/caseCommonBaseCtrl/saveClosedState.action", params);
                    if(json.rtState){
                        //保存成功，进行下一步
                        var tabId="common_case_add_tabs";
                        var title = "结案";
                        var url = '/caseCommonBaseCtrl/commonCaseAddGrading.action';
                        var pageUrl = '/supervise/caseManager/commonCase/common_case_add_5_execute.jsp';
                        var grading = '05';
                        var paramsObj = {
                                pageUrl: pageUrl,
                                grading: grading,
                                caseId: caseId,
                                editFlag: editFlag,
                                saveEvent: '01', //点击保存事件
                                modelId: presentModelId
                        }
                        url = url + "?"+$.param(paramsObj);
                        parent.addTab(tabId, title, url);
                        return json.rtState;
                    }else{
                        $.MsgBox.Alert_auto("保存失败！");
                        return json.rtState;
                    }
                }
            } else if(v=="关闭"){
                return true;
            }
        }
    });
}