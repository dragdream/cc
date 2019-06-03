//定义全局变量
var caseId = $('#caseId').val();//案件ID

function doIndexLookInit(){
	initIndexLook();
}
function initIndexLook(){
	var json = tools.requestJsonRs("/caseCommonBaseCtrl/commonCaseLook.action",{caseId:caseId});
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
		initFiling(json.rtData);// 立案阶段初始化
	    initResearch(json.rtData);// 调查取证初始化
	    initCorrect(json.rtData);// 审查决定初始化
	    initPresent(json.rtData);// 处罚决定初始化
	    initExecute(json.rtData);// 结案初始化
	}
	
}

/******************************  立案阶段  ****************************************************************************
 * 立案阶段初始化
 * @returns
 */
function initFiling(data){
	var filingApprovalDocumentPath = data.filingApprovalDocumentPath;
	if(filingApprovalDocumentPath != null && filingApprovalDocumentPath != ''){
		$("#filingApprovalDocumentName").html("");
		// 修改回显立案呈批表
	    initAttachmentInfo('commonCase', filingApprovalDocumentPath, 'filingApprovalDocumentName');
	}
	var partyType = data.partyType;
	if(partyType == '02' || partyType == "03"){
    	$('#adressTr').show();
    }else{
        $('#adressTr').hide();
    }
    initPerson(data);//人员初始化
}

/**
 * 初始化人员
 * @returns
 */
function initPerson(data){
    var personJsonStr = data.personJsonStr;
    var params;
    if(personJsonStr != null && personJsonStr != '' && personJsonStr != 'null'){
        params = {ids: personJsonStr};
    }else{
        params = {ids: 'empty'};
    }
    initPersonDatagrid(params);
}

/**
 * 人员表格加载函数
 * @returns
 */
function initPersonDatagrid(params){
    datagrid = $('#common_case_add_person_datagrid').datagrid({
        url: contextPath + '/caseCommonStaffCtrl/findListByPersonIds.action',
        queryParams: params,
        pagination: false,
//        pageSize : 15,
//        pageList : [ 15, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar: '#toolbar', // 工具条对象
        checkbox: true,
        border: false,
        striped: true,//隔行变色
        /* idField:'formId',//主键列 */
        fitColumns: true, // 列是否进行自动宽度适应
        singleSelect: true, //为true只能选择一行
        nowrap: true,
        onLoadSuccess: function(data) {
        },
        columns: [[
            {field:'ID',title:'序号',align:'center',
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field: 'subjectName', title: '所属主体', width: 30, halign: 'center', align: 'left',
                formatter: function(value, rowData) {
                    if(value == null || value == 'null') {
                        value = "";
                    }
                    var lins = "<label class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</label>"
                    return lins;
                }
            },
            {
                field: 'name', title: '执法人员 ', width: 12, halign: 'center', align: 'center'
            },
            {
                field: 'code', title: '执法证号 ', width: 15, halign: 'center', align: 'center'
            }
        ]]
    });
}

/**************************调查取证阶段 *******************************************************************************
 * 调查取证初始化
 * @returns
 */
function initResearch(data){
	initIsTrueOrFalse('isForce', data.isForce);// 是否强制
	var params = {caseId:data.id};
	initSurveyInfoTable(params);
	initIllegalTable(params);
	var informingbookDocumentPath = data.informingbookDocumentPath;
	if(informingbookDocumentPath != null && informingbookDocumentPath != ''){
		$("#informingbookDocumentPath").html("");
		// 修改回显立案呈批表
	    initAttachmentInfo('commonCase', informingbookDocumentPath, 'informingbookDocumentPath');
	}
	var backHoldDocumentPath = data.backHoldDocumentPath;
	if(backHoldDocumentPath != null && backHoldDocumentPath != ''){
		$("#backHoldDocumentPath").html("");
		// 修改回显立案呈批表
	    initAttachmentInfo('commonCase', backHoldDocumentPath, 'backHoldDocumentPath');
	}
}

function initSurveyInfoTable(params) {
	datagrid = $('#survey_information').datagrid({
		url : contextPath + '/caseCommonBaseCtrl/findSurveyInfoList.action',
		queryParams : params,// 查询参数
		pagination : false,
		// pageSize : 5,
		// pageList : [5, 10, 20],
		view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar : '#toolbar', // 工具条对象
		checkbox : true,
		border : false,
		striped : true,
		/* idField:'formId',//主键列 */
		fitColumns : true, // 列是否进行自动宽度适应
		singleSelect : true, // 为true只能选择一行
		nowrap : true,
		onLoadSuccess : function(data) {
		},
		columns : [ [
			{
				field : 'ID',
				title : '序号',
				align : 'center',
				width : 7,
				formatter : function(value, rowData,
						rowIndex) {
					return rowIndex + 1;
				}
			},
			{
				field : 'surveyDateStr',
				title : '调查日期',
				width : 20,
				align : 'center'
			},
			{
				field : 'surveyPerson',
				title : '调查人',
				width : 20,
				align : 'center'
			},
			{
				field : 'surveyObject',
				title : '调查对象 ',
				width : 30,
				align : 'center'
			},
			{
				field : 'address',
				title : '调查地址',
				width : 30,
				halign : 'center'
			},
			{
				field : 'surveyRecord',
				title : '调查笔录',
				width : 15,
				align : 'center',
				formatter : function(value, rowData,
						rowIndex) {
					if (rowData.surveyRecord != null
							&& rowData.surveyRecord != '') {
						return '有';
					} else {
						return '无';
					}
				}
			},
			{
				field : 'sceneMeasures',
				title : '采取现场措施',
				width : 40,
				halign : 'center',
			}
		] ]
	});
}

function initIllegalTable(params) {
	datagrid = $('#illegal_information').datagrid({
		url : contextPath + '/caseCommonBaseCtrl/findIllegalList.action',
		queryParams : params,// 查询参数
		pagination : false,
		// pageSize : 5,
		// pageList : [5, 10, 20],
		view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar : '#toolbar', // 工具条对象
		checkbox : true,
		border : false,
		striped : true,
		/* idField:'formId',//主键列 */
		fitColumns : true, // 列是否进行自动宽度适应
		singleSelect : true, // 为true只能选择一行
		nowrap : true,
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
				field : 'ID',
				title : '序号',
				align : 'center',
				width : 7,
				formatter : function(value, rowData, rowIndex) {
					return rowIndex + 1;
				}
			},
			{
				field : 'illegalEvidenceTypeValue',
				title : '证据类型',
				width : 20,
				align : 'center'
			},
			{
				field : 'illegalSourceValue',
				title : '证据来源',
				width : 20,
				align : 'center'
			},
			{
				field : 'illegalPerson',
				title : '取证人',
				width : 30,
				align : 'center'
			},
			{
				field : 'illegalDateStr',
				title : '取证日期',
				width : 30,
				halign : 'center'
			},
			{
				field : 'address',
				title : '取证地点',
				width : 40,
				halign : 'center'
			},
			{
				field : 'illegalDocumentPath',
				title : '证据材料',
				width : 15,
				align : 'center',
				formatter : function(value, rowData, rowIndex) {
					if (rowData.illegalDocumentPath != null && rowData.illegalDocumentPath != '') {
						return '有';
					} else {
						return '无';
					}
				}
			}
		] ]
	});
}


/***********************审查决定阶段  ***********************************************************************************************
 * 审查决定初始化
 * @returns
 */
function initCorrect(data){
	// 是否涉刑案件
	initIsTrueOrFalse('isCriminal', data.isCriminal);
    // 是否重大案件
	initIsTrueOrFalse('isMajorCase',data.isMajorCase);
    // 是否法制审核
    if (initIsTrueOrFalse('isLegalReview',data.isLegalReview)) {
        $('#isLegalReviewTr .case-handle-date').show();
    }
    // 是否集体讨论
    if (initIsTrueOrFalse('isCollectiveDiscussion',data.isCollectiveDiscussion)) {
        $('#isCollectiveDiscussionTr .case-handle-date').show();
    }
    // 是否听证
    if (initIsTrueOrFalse('isHearing',data.isHearing)) {
        $('#isHearingTr').show();
    }
    // 作出处罚种类
    var isPunishment = data.isPunishment;
	if(isPunishment==1){
		$("#isPunishment").html('行政处罚');
		$("#common_case_is_punishment_div").show();
        $('#common_case_is_punishment_punish_div').show();
        $.parser.parse('#common_case_is_punishment_div');
        $.parser.parse('#common_case_is_punishment_punish_div');
        // 加载职权，依据
        initCasePowerAndGist(data);
        
        // 警告
        initIsShowOrHide("isWarn",data.isWarn);
        // 罚款
        initIsShowOrHide("isFine",data.isFine);
        // 没收违法所得、没收非法财物
        initIsConfiscate(data);
        // 责令停产停业
        initIsShowOrHide("isOrderClosure",data.isOrderClosure);
        // 暂扣或者吊销许可证、暂扣或者吊销执照
        initIsTdLicense(data);
        // 行政拘留
        initIsShowOrHide("isDetain",data.isDetain);
        // 其他
        initIsShowOrHide("isOther",data.isOther);
	}else if (isPunishment==2){
		$("#isPunishment").html('不予处罚');
	}else {
		$("#isPunishment").html('— —');
	}
	// 修改回显处罚决定书
    var punishmentDocumentPath = data.punishmentDocumentPath;
	if(punishmentDocumentPath != null && punishmentDocumentPath != ''){
		$("#punishmentDocumentName").html("");
	    initAttachmentInfo('commonCase', punishmentDocumentPath, 'punishmentDocumentName');
	}
	// 修改回显送达回证
	var sendProofDocumentPath = data.sendProofDocumentPath;
	if(sendProofDocumentPath != null && sendProofDocumentPath != ''){
		$("#sendProofDocumentName").html("");
	    initAttachmentInfo('commonCase', sendProofDocumentPath, 'sendProofDocumentName');
	}
	// 撤销立案
    if(data.closedState == '02'){
        initRevokeJsonStr(data);
    }
}

/**
 * 没收违法所得、没收非法财物
 * @param data
 */
function initIsConfiscate(data){
	if(initIsShowOrHide("isConfiscate",data.isConfiscate)){
		if(data.confiscateMoney == null || data.confiscateMoney == '' || data.confiscateMoney == 0 || data.confiscateMoney == 'null'){
			$("#confiscateMoneyTd").html("");
		}
		if(data.confiscateProMon == null || data.confiscateProMon == '' || data.confiscateProMon == 0 || data.confiscateProMon == 'null'){
			$("#confiscateProMonTd").html("");
		}
	}
}

/**
 * 暂扣或者吊销许可证、暂扣或者吊销执照
 * @param data
 */
function initIsTdLicense(data){
	if(initIsShowOrHide("isTdLicense",data.isTdLicense)){
		$("#isTdOrRevokeLicenseTd").html("暂扣");
	}else if(initIsShowOrHide("isTdLicense",data.isRevokeLicense)){
		$("#isTdOrRevokeLicenseTd").html("吊销");
		$('#isTdLicense_tr .text-input-punish').hide();
	}
}

/**
 * 上传完毕后加载已经上传的文件
 * @param obj 文件对象
 * @param elemId 展示元素ID
 * @returns
 */
function addAttachmentProp(obj, elemId, punishFile) {
    var mainObj = $('#' + elemId);
    var page = "";
    page = page + '<div style="float: left; margin-right:20px" id="flowsheet' + obj.sid + '">';
    page = page + '<input type="hidden" name="'+elemId+'FileId" id="'+elemId+'FileId" value="' + obj.sid + '" />';
    page = page + '<input type="hidden" name="'+elemId+'FileName" id="'+elemId+'FileName" value="' + obj.fileName + '" />';
    page = page + '<a href="javascript:void(0);" sid="FILE' + obj.sid + '"></a>';
    page = page + '</div>';
    mainObj.append(page);
    
    obj.priv = 2; // 阅读、下载、删除
    var attach = tools.getAttachElement(obj, {deleteEvent:function(attachModel){
        $("#flowsheet" + attachModel.sid).remove();
        for(var index in punishFile) {
            if(punishFile[index] == attachModel.sid) {
                punishFile.splice(index, 1);
                break;
            }
        }
        
    }});
    $("[sid='FILE" + obj.sid + "']").append(attach);
}

/**
 * 修改初始化附件列表
 * @param model
 * @param modelId
 * @param elemId
 * @returns
 */
function initAttachmentInfo(model, sid, elemId) {
    var json = tools.requestJsonRs("/attachmentController/getAttachmentModelsByIds.action?attachIds="+sid);
    if(json.rtData != null && json.rtData.length > 0) {
        var punishFile = [];
        var attachList = json.rtData;
        $.each(attachList, function(i, o){
            // 添加附件属性
            punishFile.push(o.sid);//存放上传文件ID
            addAttachmentProp(o, elemId, punishFile, 2);
        });
    }
}

/**
 * 撤销立案信息显示
 * @returns
 */
function initRevokeJsonStr(data){
    $("#common_case_revoke_div").show();
	var revokeJsonStr = data.revokeJsonStr;
    if(revokeJsonStr != null && revokeJsonStr != ''){
        var revokeJson = JSON.parse(revokeJsonStr);
        $('#revoke_approvePerson_span').html(revokeJson.approvePerson);
        $('#revoke_approveDateStr_span').html( revokeJson.approveDateStr);
        $('#revoke_revokeRegisterDateStr_span').html(revokeJson.revokeRegisterDateStr);
        $('#revoke_revokeRegisterReason_span').html( revokeJson.revokeRegisterReason);
    }
}

/**
 * 初始化职权依据
 * @param data
 */
function initCasePowerAndGist(data){
    var powerJsonStr = data.powerJsonStr;
    var gistJsonStr = data.gistJsonStr;
    var punishJsonStr = data.punishJsonStr;
    var params;
    var paramsGist;
    var paramsPunish;
    if(powerJsonStr == null || powerJsonStr.length == 0) {
        params = {ids: 'empty'};
    }else {
        params = {ids: powerJsonStr};
    }
    if(gistJsonStr == null || gistJsonStr.length == 0){
        paramsGist = {powerId: 'empty',gistType: '01'};
    }else{
        paramsGist = {id: gistJsonStr,gistType: '01'};//依据类型（01 违法依据，02处罚依据，03设定依据）
    }
    if(punishJsonStr == null || punishJsonStr.length == 0){
        paramsPunish = {powerId: 'empty',gistType: '02'};
    }else{
        paramsPunish = {id: punishJsonStr,gistType: '02'};//依据类型（01 违法依据，02处罚依据，03设定依据）
    }
    initHandDatagrid(params);
    initGistDatagrid(paramsGist)
    initPunishDatagrid(paramsPunish);

}
/**
 * 违法行为信息加载
 * @returns
 */
function initHandDatagrid(params){
    datagrid = $('#common_case_illegal_hand_datagrid').datagrid({
        url: contextPath + '/powerCtrl/getPowerByActSubject.action',
        pagination: false,
        //pageSize : 5,
        //pageList : [5, 10, 20],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar: '#toolbar', // 工具条对象
        checkbox: true,
        border: false,
        striped: true,//隔行变色
        /* idField:'formId',//主键列 */
        fitColumns: true, // 列是否进行自动宽度适应
        singleSelect: true, //为true只能选择一行
        nowrap: true,
        queryParams: params,//查询参数
        onLoadSuccess: function(data) {
        },
        columns: [[
            {field:'ID',title:'序号',align:'center',
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field: 'name', title: '违法行为', width: 200, halign: 'center', align: 'left',
                formatter: function(e, rowData) {
                    var name = rowData.name
                    if(name == null || name == 'null') {
                        name = "";
                    }
                    var lins = "<label class='common-overflow-hidden common-table-td-full-width' title='"+name+"'>"+name+"</label>"
                    return lins;
                }
            },
            {
                field: 'code', title: '职权编号 ', width: 80, halign: 'center', align: 'center'
                
            }
        ]]
    });
}

/**
 * 违法依据信息加载
 * @returns
 */
function initGistDatagrid(params){
    datagrid = $('#common_case_illegal_gist_datagrid').datagrid({
        url: contextPath + '/powerCtrl/findGistsByPowerIds.action',
        queryParams: params,//查询参数
        pagination: false,
        //pageSize : 5,
        //pageList : [5, 10, 20],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar: '#toolbar', // 工具条对象
        checkbox: true,
        border: false,
        striped: true,//隔行变色
        /* idField:'formId',//主键列 */
        fitColumns: true, // 列是否进行自动宽度适应
        singleSelect: false, //为true只能选择一行
        nowrap: true,
        onLoadSuccess: function(data) {
        },
        columns: [[
            {field:'ID',title:'序号',align:'center',
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field: 'lawName', title: '法律名称', halign: 'center', width: 120, align: 'left',
                formatter: function(e, rowData) {
                    var lawName = rowData.lawName
                    if(lawName == null || lawName == 'null') {
                        lawName = "";
                    }
                    var lins = "<label class='common-overflow-hidden common-table-td-full-width' title='"+lawName+"'>"+lawName+"</label>"
                    return lins;
                }
            },
            {
                field: 'gistStrip', title: '条', width: 15, halign: 'center', align: 'center'
            },
            {
                field: 'gistFund', title: '款', width: 15, halign: 'center', align: 'center',
                formatter: function(value, rowData, rowIndex) { 
                    if(value == 0) {
                        return "";
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'gistItem', title: '项', width: 15, halign: 'center', align: 'center',
                formatter: function(value, rowData, rowIndex) { 
                    if(value == 0) {
                        return "";
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'gistCatalog', title: '目', width: 15, halign: 'center', align: 'center',
                formatter: function(value, rowData, rowIndex) { 
                    if(value == 0) {
                        return "";
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'content', title: '内容', halign: 'center', width: 200, align: 'left',
                formatter: function(e, rowData) {
                    var content = rowData.content
                    if(content == null || content == 'null') {
                        content = "";
                    }
                    var lins = "<label class='common-overflow-hidden common-table-td-full-width' title='"+content+"'>"+content+"</label>"
                    return lins;
                }
            }
        ]]
    });
}


/**
 * 处罚依据信息加载
 * @returns
 */
function initPunishDatagrid(params){

    datagrid = $('#common_case_punish_datagrid').datagrid({
        url: contextPath + '/powerCtrl/findGistsByPowerIds.action',
        queryParams: params,//查询参数
        pagination: false,
        //pageSize : 5,
        //pageList : [5, 10, 20],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar: '#toolbar', // 工具条对象
        checkbox: true,
        border: false,
        striped: true,//隔行变色
        /* idField:'formId',//主键列 */
        fitColumns: true, // 列是否进行自动宽度适应
        singleSelect: false, //为true只能选择一行
        nowrap: true,
        onLoadSuccess: function(data) {
        },
        columns: [[
            {field:'ID',title:'序号',align:'center',
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field: 'lawName', title: '法律名称', halign: 'center', width: 120,
                formatter: function(e, rowData) {
                    var lawName = rowData.lawName
                    if(lawName == null || lawName == 'null') {
                        lawName = "";
                    }
                    var lins = "<label class='common-overflow-hidden common-table-td-full-width' title='"+lawName+"'>"+lawName+"</label>"
                    return lins;
                }
            },
            {
                field: 'gistStrip', title: '条', width: 15, halign: 'center', align: 'center'
            },
            {
                field: 'gistFund', title: '款', width: 15, halign: 'center', align: 'center',
                formatter: function(value, rowData, rowIndex) { 
                    if(value == 0) {
                        return "";
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'gistItem', title: '项', width: 15, halign: 'center', align: 'center',
                formatter: function(value, rowData, rowIndex) { 
                    if(value == 0) {
                        return "";
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'gistCatalog', title: '目', width: 15, halign: 'center', align: 'center',
                formatter: function(value, rowData, rowIndex) { 
                    if(value == 0) {
                        return "";
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'content', title: '内容', halign: 'center', width: 200,
                formatter: function(e, rowData) {
                    var content = rowData.content
                    if(content == null || content == 'null') {
                        content = "";
                    }
                    var lins = "<label class='common-overflow-hidden common-table-td-full-width' title='"+content+"'>"+content+"</label>"
                    return lins;
                }
            }
        ]]
    });
}

/****************** 处罚执行阶段  *********************************************************************************************************************
 * 行政强制行为
 * @returns
 */
function doOpenShowPageAct(){
    
    var params = {
         subjectId: subjectId,
         caseSourceType: 200,   //处罚来源
         caseSourceId: caseId,
         departmentId: departmentId,
         pageType: '02' //强制措施  02 强制执行
    }
    var url=contextPath+"/coercionCaseSearchCtrl/seeCoercionCaseInfoPage.action?"+$.param(params);
    top.bsWindow(url ,"行政强制信息查看",{width:"1000",height:"500",buttons:
        [
            {name:"关闭",classStyle:"btn-alert-gray"}
        ]
        ,submit:function(v,h){
            if(h != null && h != ''){
                var cw = h[0].contentWindow;
                if( v == "保存") {
                    return true;
                } else if(v=="关闭"){
                    return true;
                }
            }else{
                return true;
            }
            
        }
    });
}
/**
 * 处罚决定初始化
 * @returns
 */
function initPresent(data){
	// 是否执行处罚决定
    if (initIsTrueOrFalse('isPunishDecisionExecute',data.isPunishDecisionExecute)) {
    	$('#punishDecisionExecute_tr').show();
    	$('#stagesExection_tr').show();
    	$('#delayedExection_tr').show();
    	// 是否分期执行
    	initIsTrueOrFalse('isStagedExection',data.isStagedExection);
    	// 是否延期执行
    	initIsTrueOrFalse('isDelayedExection',data.isDelayedExection);
    }
	if(data.closedState == '06'){
        initRevokePunish(data);//撤销原处罚决定
    }else if(data.closedState == '04'){
        initEnd(data);//案件终结初始化
        
    }
}

/**
 * 撤销原处罚决定
 * @returns
 */
function initRevokePunish(data){
	$("#common_case_revoke_punish_div").show();
	var revokePunishJsonStr = data.revokePunishJsonStr;
    if(revokePunishJsonStr != null && revokePunishJsonStr != ''){
        var revokePunishJson = JSON.parse(revokePunishJsonStr);
        $('#revokePunish_approvePerson_span').html(revokePunishJson.approvePerson);
        $('#revokePunish_approveDateStr_span').html( revokePunishJson.approveDateStr);
        $('#revokePunishmentDateStr_span').html(revokePunishJson.revokePunishmentDateStr);
        $('#revokePunishmentReason_span').html(revokePunishJson.revokePunishmentReason);
        initRevokePunishType(revokePunishJson.revokePunishType);
    }
}

/**
 * 撤销原处罚决定类型
 * @returns
 */
function initRevokePunishType(revokePunishType){
    var params = {
            parentCodeNo: "COMMON_REVOKE_PUNISH_TYPE",
            codeNo: revokePunishType
    }
    var json = tools.requestJsonRs("/sysCode/getSysCodeNameByParentCodeNo.action", params);
    if(json.rtState){
        $('#revokePunishType_span').html(json.rtData);
    }
}

/**
 * 案件终结初始化
 * @returns
 */
function initEnd(data){
	$("#common_case_end_div").show();
	var endJsonStr = data.endJsonStr;
    if(endJsonStr != null && endJsonStr != ''){
        var endJson = JSON.parse(endJsonStr);
        $('#end_approvePerson_span').html(endJson.approvePerson);
        $('#end_approveDateStr_span').html( endJson.approveDateStr);
        $('#endDateStr_span').html(endJson.endDateStr);
        $('#endReason_span').html(endJson.endReason);
    }
	$(".common-tr-present").children('.case-common-present-look-td-class6').addClass("case-common-present-look-td-class1");
	$(".common-tr-present").children('.case-common-present-look-td-class6').removeClass("case-common-present-look-td-class6");
	$(".common-tr-present").children('.case-common-present-look-td-class7').addClass("case-common-present-look-td-class2");
	$(".common-tr-present").children('.case-common-present-look-td-class7').removeClass("case-common-present-look-td-class7");
	$(".common-tr-present").children('.case-common-present-look-td-class8').addClass("case-common-present-look-td-class3");
	$(".common-tr-present").children('.case-common-present-look-td-class8').removeClass("case-common-present-look-td-class8");
	$(".common-tr-present").children('.case-common-present-look-td-class9').addClass("case-common-present-look-td-class4");
	$(".common-tr-present").children('.case-common-present-look-td-class9').removeClass("case-common-present-look-td-class9");
	$(".common-tr-present").children('.case-common-present-look-td-class10').addClass("case-common-present-look-td-class5");
	$(".common-tr-present").children('.case-common-present-look-td-class10').removeClass("case-common-present-look-td-class10");
}

/****************** 结案阶段  *********************************************************************************************************************
 * 结案初始化
 * @returns
 */
function initExecute(data){
	var closedState = data.closedState;
	if(closedState!=null && closedState!=''){
		if(closedState=='01'){
			$('#closedCaseInfo_tr').hide();
			$('#transferOrgan_tr').hide();
		} else if(closedState=='05'){
			$('#closedCaseInfo_tr').show();
			$('#transferOrgan_tr').show();
			$(".common-tr-execute").children('.case-common-execute-look-td-class1').addClass("case-common-execute-look-td-class3");
			$(".common-tr-execute").children('.case-common-execute-look-td-class1').removeClass("case-common-execute-look-td-class1");
			$(".common-tr-execute").children('.case-common-execute-look-td-class2').addClass("case-common-execute-look-td-class4");
			$(".common-tr-execute").children('.case-common-execute-look-td-class2').removeClass("case-common-execute-look-td-class2");
		} else {
			$('#closedCaseInfo_tr').show();
			$('#transferOrgan_tr').hide();
			$(".common-tr-execute").children('.case-common-execute-look-td-class1').addClass("case-common-execute-look-td-class3");
			$(".common-tr-execute").children('.case-common-execute-look-td-class1').removeClass("case-common-execute-look-td-class1");
			$(".common-tr-execute").children('.case-common-execute-look-td-class2').addClass("case-common-execute-look-td-class4");
			$(".common-tr-execute").children('.case-common-execute-look-td-class2').removeClass("case-common-execute-look-td-class2");
		}
	}
}

/** *** 工具方法 *** */

/**
 * 行显示隐藏
 */
function initIsShowOrHide(id, value){
	if(value == 1){
		$("#"+id+"_tr").show();
		return true;
	}else{
		$("#"+id+"_tr").hide();
		return false;
	}
}

/**
 * 是否显示
 * @param id
 * @param data
 * @returns {Boolean}
 */
function initIsTrueOrFalse(id, data){
	var value = "";
	var isTrueOrFalse;
	if(data == 1){
		value = '是';
		isTrueOrFalse = true;
	}else if (data == 0){
		value = '否';
		isTrueOrFalse = false;
	}else {
		value = '— —';
	}
	$("#"+id).html(value);
	return isTrueOrFalse;
}
