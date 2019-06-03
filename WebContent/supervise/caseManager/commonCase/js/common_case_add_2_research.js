var caseId = $('#comm_case_add_research_caseId').val(); // 案件ID
var editFlag = $('#common_case_add_research_editFlag').val(); // 编辑标识（1新增，2修改，3查看）
var isNext = $('#common_case_add_research_isNext').val();// tab页签标识（立案0,调查取证1,处罚决定2,处罚执行3,结案4）
var actSubject = $('#common_case_add_research_subjectId').val();// 实施主体ID
var departmentId = $('#common_case_add_research_departmentId').val();// 部门ID
var researchModelId = $('#common_case_add_research_modelId').val();// 弹框modelID
var reviewFile = []; // 记录删除的法制审查文件
var revokeJson = [];// 撤销立案json对象
var swfUploadObj = null;
var informingbookFile = [];//告知书
var backHoldFile = [];//告知书送达回证
var surveyJsons = [];
var surveyJson;
var illegalJsons = [];
var illegalJson;

/**
 * 默认加载方法
 */
function doInitResearch() {
	$('#common_case_add_2_research_form .easyui-panel').panel('maximize');
	
	if (editFlag == '2'){
		doInitEditResearch();
		$('#common_case_add_2_research_form .easyui-panel').panel('maximize');
	}else{
		initCodeListInput('COMMON_SENT_WAY','informingbookWay');// 送达方式
	}
	initiRadiobutton();
	initSurveyInfoTable();// 初始化调查信息
	initIllegalTable();// 初始化证据信息
	initPunishHearingSendWay();
	initInformingPunishType();
	initRightInform();
	//上传告知书
    doInitMultipleUploadInformingbook('commonCase', caseId+'informingbookDocumentId', 'informingbookDocument');
    
    //上传送达回证
    doInitMultipleUploadBackHold('commonCase', caseId+'backHoldDocumentId', 'backHoldDocument');
}

/**
 * 附件上传
 * @param model 后台文件路径文件夹
 * @param modelId 查询文件关键字
 * @param elemId 前台展示元素ID
 * @returns
 */
function doInitMultipleUploadInformingbook(model, modelId, elemId) {
  //多附件快速上传
    swfUploadObj = new TeeSWFUpload({
        fileContainer:"fileContainerInformingbook",//文件列表容器
        uploadHolder:"uploadHolderInformingbook",//上传按钮放置容器
        valuesHolder:"attachmentSidStrInformingbook",//附件主键返回值容器，是个input
        quickUpload:true,//快速上传
        showUploadBtn:false,//不显示上传按钮
        queueComplele:function(files){//队列上传成功回调函数，可有可无
            
        },
        uploadSuccess:function(files){//上传成功
        	informingbookFile.push(files.sid);//存放上传文件ID
        	informingbookFile = addAttachmentProp(files, elemId, informingbookFile);
        },
        renderFiles:false,//渲染附件
        post_params:{model:model, modelId: modelId}//后台传入值，model为模块标志
    });
}

/**
 * 附件上传
 * @param model 后台文件路径文件夹
 * @param modelId 查询文件关键字
 * @param elemId 前台展示元素ID
 * @returns
 */
function doInitMultipleUploadBackHold(model, modelId, elemId) {
  //多附件快速上传
    swfUploadObj = new TeeSWFUpload({
        fileContainer:"fileContainerBackHold",//文件列表容器
        uploadHolder:"uploadHolderBackHold",//上传按钮放置容器
        valuesHolder:"attachmentSidStrBackHold",//附件主键返回值容器，是个input
        quickUpload:true,//快速上传
        showUploadBtn:false,//不显示上传按钮
        queueComplele:function(files){//队列上传成功回调函数，可有可无
            
        },
        uploadSuccess:function(files){//上传成功
        	backHoldFile.push(files.sid);//存放上传文件ID
        	backHoldFile = addAttachmentProp(files, elemId, backHoldFile);
        },
        renderFiles:false,//渲染附件
        post_params:{model:model, modelId: modelId}//后台传入值，model为模块标志
    });
}

/**
 * 初始化调查信息
 */
function initSurveyInfoTable(){
	var json = tools.requestJsonRs(contextPath + '/caseCommonBaseCtrl/findSurveyInfoList.action',{caseId:caseId});
	refreshSurveyInfoTable(json);
	if(json.rows != null && json.rows.length > 0){
		for(var i=0; i<json.rows.length; i++){
			surveyJson = {
				id : json.rows[i].id,
				surveyDateStr : json.rows[i].surveyDateStr,
				surveyPerson : json.rows[i].surveyPerson,
				surveyObject : json.rows[i].surveyObject,
				address : json.rows[i].address,
				surveyRecord : json.rows[i].surveyRecord,
				sceneMeasures : json.rows[i].sceneMeasures,
				createDateStr : json.rows[i].createDateStr,
				updateDateStr : json.rows[i].updateDateStr,
				updatePersonId : json.rows[i].updatePersonId
			};
			surveyJsons.push(surveyJson);
		}
	}
}

function refreshSurveyInfoTable(datas) {
	datagrid = $('#survey_information').datagrid({
		data : datas,
		//queryParams : params,// 查询参数
		pagination : false,
		// pageSize : 5,
		// pageList : [5, 10, 20],
		view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
		//toolbar : '#surveyEndTable', // 工具条对象
		checkbox : true,
		border : false,
		striped : true,
		/* idField:'formId',//主键列 */
		fitColumns : true, // 列是否进行自动宽度适应
		singleSelect : true, // 为true只能选择一行
		nowrap : true,
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
				formatter : function(value, rowData, rowIndex) {
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
				halign : 'center'
			},
			{
				field : '___',
				title : '操作',
				formatter : function(value, rowData, rowIndex) {
					var optStr = "<span title='编辑'><a href=\"#\" onclick=\"editSurveyInfo('" + rowIndex + "')\"><i class='fa fa-pencil common-yellow'></i></a></span>&nbsp;&nbsp;&nbsp;&nbsp;"
							+ "<span title='删除'><a href=\"#\" onclick=\"deleteSurveyInfo('" + rowIndex + "')\"><i class='fa fa-trash-o common-red'></i></a></span>";
					return optStr;
				},
				width : 20,
				align : 'center'
			} 
		] ]
	});
}

/**
 * 添加调查信息
 * @param id
 */
function addSurveyInfo() {
	var url = contextPath + "/supervise/caseManager/commonCase/common_case_add_2_research_survey.jsp";
	top.bsWindow(url, "调查信息填报", {
		width : "800",
		height : "350",
		buttons : [ 
		    {name : "关闭", classStyle : "btn-alert-gray"},
		    {name : "保存", classStyle : "btn-alert-blue"}
		],
		submit : function(v, h) {
			var cw = h[0].contentWindow;
			if (v == "保存") {
				var status = cw.saveSurveyInfo();
				if (status != false) {
					surveyJson = {
						id : status.id,
						surveyDateStr : status.surveyDateStr,
						surveyPerson : status.surveyPerson,
						surveyObject : status.surveyObject,
						address : status.address,
						surveyRecord : status.surveyRecord,
						sceneMeasures : status.sceneMeasures,
						createDateStr : "",
						updateDateStr : "",
						updatePersonId : ""
					};
					surveyJsons.push(surveyJson);
					var data = {
					    rows:surveyJsons,
					    total:surveyJsons.length
					};
					refreshSurveyInfoTable(data);
					return true;
				}
			} else if (v == "关闭") {
				return true;
			}
		}
	});
}

/**
 * 编辑调查信息
 * @param id
 */
function editSurveyInfo(index) {
	var params = surveyJsons[index];
	var url = contextPath + "/supervise/caseManager/commonCase/common_case_add_2_research_survey.jsp";
	top.params = params;
	top.bsWindow(url, "调查信息修改", {
		width : "800",
		height : "350",
		buttons : [ 
		    {name : "关闭", classStyle : "btn-alert-gray"},
		    {name : "保存", classStyle : "btn-alert-blue"}
		],
		submit : function(v, h) {
			var cw = h[0].contentWindow;
			if (v == "保存") {
				var status = cw.saveSurveyInfo();
				if (status != false) {
					surveyJson = {
						id : status.id,
						surveyDateStr : status.surveyDateStr,
						surveyPerson : status.surveyPerson,
						surveyObject : status.surveyObject,
						address : status.address,
						surveyRecord : status.surveyRecord,
						sceneMeasures : status.sceneMeasures,
						createDateStr : status.createDateStr,
						updateDateStr : "",
						updatePersonId : ""
					};
					surveyJsons[index]=surveyJson;
					var data = {
					    rows:surveyJsons,
					    total:surveyJsons.length
					};
					refreshSurveyInfoTable(data);
					return true;
				}
			} else if (v == "关闭") {
				return true;
			}
		},
		params : params,
		onload : function(){
		}
	});
}

/**
 * 删除调查信息
 * @param id
 */
function deleteSurveyInfo(index){
	top.$.MsgBox.Confirm("提示","确定删除该条数据？",function(){
		surveyJsons.splice(index,1);
        $.MsgBox.Alert_auto("删除成功！");
        var data = {
		    rows:surveyJsons,
		    total:surveyJsons.length
		};
		refreshSurveyInfoTable(data);
    });
}

/**
 * 初始化证据信息
 */
function initIllegalTable(){
	var json = tools.requestJsonRs(contextPath + '/caseCommonBaseCtrl/findIllegalList.action',{caseId:caseId});
	refreshIllegalTable(json);
	if(json.rows != null && json.rows.length > 0){
		for(var i=0; i<json.rows.length; i++){
			illegalJson = {
				id : json.rows[i].id,
				illegalEvidenceType : json.rows[i].illegalEvidenceType,
				illegalEvidenceTypeValue : json.rows[i].illegalEvidenceTypeValue,
				illegalSource : json.rows[i].illegalSource,
				illegalSourceValue : json.rows[i].illegalSourceValue,
				illegalPerson : json.rows[i].illegalPerson,
				illegalDateStr : json.rows[i].illegalDateStr,
				address : json.rows[i].address,
				illegalDocumentName : json.rows[i].illegalDocumentName,
				illegalDocumentPath : json.rows[i].illegalDocumentPath,
				createTimeStr : json.rows[i].createTimeStr,
				updateTimeStr : json.rows[i].updateTimeStr,
				updatePersonId : json.rows[i].updatePersonId
			};
			illegalJsons.push(illegalJson);
		}
	}
}

function refreshIllegalTable(datas) {
	datagrid = $('#illegal_information').datagrid({
		data : datas,
		//queryParams : params,// 查询参数
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
			},
			{
				field : '___',
				title : '操作',
				formatter : function(value, rowData, rowIndex) {
					var optStr = "<span title='编辑'><a href=\"#\" onclick=\"editIllegal('" + rowIndex + "')\"><i class='fa fa-pencil common-yellow'></i></a></span>&nbsp;&nbsp;&nbsp;&nbsp;"
							+ "<span title='删除'><a href=\"#\" onclick=\"deleteIllegal('" + rowIndex + "')\"><i class='fa fa-trash-o common-red'></i></a></span>";
					return optStr;
				},
				width : 20,
				align : 'center'
			} 
		] ]
	});
}

/**
 * 添加证据信息
 * @param id
 */
function addIllegal() {
	var url = contextPath + "/supervise/caseManager/commonCase/common_case_add_2_research_illegal.jsp?isEdit=0&caseId=" + caseId;
	top.bsWindow(url, "证据信息填报", {
		width : "800",
		height : "370",
		buttons : [ 
		    {name : "关闭", classStyle : "btn-alert-gray"},
		    {name : "保存", classStyle : "btn-alert-blue"}
		],
		submit : function(v, h) {
			var cw = h[0].contentWindow;
			if (v == "保存") {
				var status = cw.saveIllegal();
				if (status != false) {
					illegalJson = {
						id : status.id,
						illegalEvidenceType : status.illegalEvidenceType,
						illegalEvidenceTypeValue : status.illegalEvidenceTypeValue,
						illegalSource : status.illegalSource,
						illegalSourceValue : status.illegalSourceValue,
						illegalPerson : status.illegalPerson,
						illegalDateStr : status.illegalDateStr,
						address : status.address,
						illegalDocumentName : status.illegalDocumentName,
						illegalDocumentPath : status.illegalDocumentPath,
						createTimeStr : "",
						updateTimeStr : "",
						updatePersonId : ""
					};
					illegalJsons.push(illegalJson);
					var data = {
					    rows:illegalJsons,
					    total:illegalJsons.length
					};
					refreshIllegalTable(data);
					return true;
				}
			} else if (v == "关闭") {
				return true;
			}
		}
	});
}

/**
 * 编辑证据信息
 * @param id
 */
function editIllegal(index) {
	var params = illegalJsons[index];
	var url = contextPath + "/supervise/caseManager/commonCase/common_case_add_2_research_illegal.jsp?isEdit=1&caseId=" + caseId;
	top.params = params;
	top.bsWindow(url, "证据信息修改", {
		width : "800",
		height : "370",
		buttons : [ 
		    {name : "关闭", classStyle : "btn-alert-gray"},
		    {name : "保存", classStyle : "btn-alert-blue"}
		],
		submit : function(v, h) {
			var cw = h[0].contentWindow;
			if (v == "保存") {
				var status = cw.saveIllegal();
				if (status != false) {
					illegalJson = {
						id : status.id,
						illegalEvidenceType : status.illegalEvidenceType,
						illegalEvidenceTypeValue : status.illegalEvidenceTypeValue,
						illegalSource : status.illegalSource,
						illegalSourceValue : status.illegalSourceValue,
						illegalPerson : status.illegalPerson,
						illegalDateStr : status.illegalDateStr,
						address : status.address,
						illegalDocumentName : status.illegalDocumentName,
						illegalDocumentPath : status.illegalDocumentPath,
						createTimeStr : status.createTimeStr,
						updateTimeStr : "",
						updatePersonId : ""
					};
					illegalJsons[index]=illegalJson;
					var data = {
					    rows:illegalJsons,
					    total:illegalJsons.length
					};
					refreshIllegalTable(data);
					return true;
				}
			} else if (v == "关闭") {
				return true;
			}
		},
		params : params,
		onload : function(){
		}
	});
}

/**
 * 删除证据信息
 * @param id
 */
function deleteIllegal(index){
	top.$.MsgBox.Confirm("提示","确定删除该条数据？",function(){
		illegalJsons.splice(index,1);
        $.MsgBox.Alert_auto("删除成功！");
        var data = {
		    rows:illegalJsons,
		    total:illegalJsons.length
		};
		refreshIllegalTable(data);
    });
}

/**
 * 初始化是否采取强制措施 
 */
function initiRadiobutton(){
	var isForce = $('#common_case_add_research_isForce').val();
	var isTrueJson = [
	              	{codeNo:'1', codeName: '是'},
	              	{codeNo:'0', codeName: '否'}
	              ]
	initJsonListRadio(isTrueJson, 'isForce');
	if(isForce!=null && isForce!=''){
		$("#isForce" + isForce).radiobutton('check');
	}
}
/**
 * 初始化告知方式
 */
function initPunishHearingSendWay() {
	var json = tools.requestJsonRs(contextPath +"/sysCode/getSysCodeByParentCodeNo.action",{codeNo : 'PUNISH_HEARING_SEND_WAY'});
	if (json.rtState) {
		$('#punishHearingSendWay').combobox({
			data : json.rtData,
			valueField : 'codeNo',
			textField : 'codeName',
			panelHeight : 'auto',
			prompt : '请选择',
			onLoadSuccess : function() {
				var informingbookWay = $('#punishHearingSendWay').combobox('getValue');
				if (informingbookWay != null && informingbookWay != '') {
					$('#punishHearingSendWay').combobox('setValue',informingbookWay);
				}
			},
			editable : false
		});
	}
}

/*
 * 初始化告知处罚种类
 */
function initInformingPunishType() {

	var json = tools.requestJsonRs(contextPath +"/sysCode/getSysCodeByParentCodeNo.action",{codeNo : 'INFORMING_PUNISH_TYPE'});
	if (json.rtState) {
		var page = "";
		for (var i = 0; i < json.rtData.length; i++) {
			page = page + '<span style="white-space: nowrap;float:left;padding:5px 0;">'
					+ '<input style="width: 15px; height: 15px;" labelAlign="left" labelWidth="300px" labelPosition="after" type="checkbox" name="informingPunishType" id="informingPunishType'
					+ json.rtData[i].codeNo + '" class="easyui-checkbox" '
					+ 'value="' + json.rtData[i].codeNo + '" label="'
					+ json.rtData[i].codeName + '" /></span>';
		}
		var pageDoc = $('#informPunishType').html(page);
		$.parser.parse(pageDoc);
		//初始化赋值

		var informingPunishType = $('#common_case_add_research_informPunishType').val() ;

		if(informingPunishType!=null && informingPunishType!=''){
			var informingPunishTy = informingPunishType.split(",");
			for(var j=0;j<informingPunishTy.length;j++){
				$("#informingPunishType" + informingPunishTy[j]).checkbox('check');
			}
		}
		// 单个项点击触发事件
		for (var i = 0; i < json.rtData.length; i++) {
			$("#informingPunishType" + json.rtData[i].codeNo).checkbox({
				onChange : function(checked) {
					if (checked) {
						if ($("#common_case_add_2_research_form input[name='informingPunishType']:checked").length == json.rtData.length) {
						$("#informingPunishType0").checkbox({checked : true});
						}
					} else {
						$("#informingPunishType0").checkbox({checked : false});
					}
				}
			});
		}

	}
}

/**
 * 初始化权利告知
 * 
 */
function initRightInform() {
	var json = tools.requestJsonRs(contextPath +"/sysCode/getSysCodeByParentCodeNo.action",{codeNo : 'RIGHT_INFORM'});
	if (json.rtState) {
		var page = "";
		for (var i = 0; i < json.rtData.length; i++) {
			page = page + '<div style="display:inline-block"><input style="width: 15px; height: 15px;" labelAlign="left" labelWidth="300px" labelPosition="after" type="checkbox" name="rightInform" id="rightInform'
					+ json.rtData[i].codeNo + '" class="easyui-checkbox" value="' + json.rtData[i].codeNo + '" label="'
					+ json.rtData[i].codeName + '"/></div>';
		}
		var pageDoc = $('#powerNotice').html(page);
		$.parser.parse(pageDoc);
		//初始化权利告知
		var rightInform = $('#common_case_add_research_powerNotice').val();
		if(rightInform!=""&&rightInform!=null){
			var rightInfo = rightInform.split(",");
			for(var j=0;j<rightInfo.length;j++){
				$("#rightInform" + rightInfo[j]).checkbox('check');
			}
		}
		// 单个项点击触发事件
		for (var i = 0; i < json.rtData.length; i++) {
			$("#rightInform" + json.rtData[i].codeNo).checkbox({
				onChange : function(checked) {
					if (checked) {
						if ($("#common_case_add_2_research_form input[name='rightInform']:checked").length == json.rtData.length) {
							$("#rightInform0").checkbox({checked : true});
						}
					} else {
						$("#rightInform0").checkbox({checked : false});
					}
				}
			});
		}

	}
}

/**
 * 修改时加载数据方法
 * 
 * @returns
 */
function doInitEditResearch() {
	var grading = '02';// 调查取证阶段
	var json = tools.requestJsonRs("/caseCommonBaseCtrl/findCaseCommonBaseById.action?id=" + caseId + "&grading=" + grading);
	if (json.rtState) {
		bindJsonObj2Easyui(json.rtData, 'common_case_add_2_research_form');
		initCodeListInput('COMMON_SENT_WAY','informingbookWay',json.rtData.informingbookWay);
		var informingbookDocumentPath = json.rtData.informingbookDocumentPath;
    	if(informingbookDocumentPath != null && informingbookDocumentPath != ''){
    		// 修改回显处罚决定书
    	    //initAttachmentInfoPunish('commonCase', informingbookDocumentPath, 'informingbookDocumentName');
    		informingbookFile = initAttachmentInfo('commonCase', informingbookDocumentPath, 'informingbookDocument');
    	}
    	
    	var backHoldDocumentPath = json.rtData.backHoldDocumentPath;
    	if(backHoldDocumentPath != null && backHoldDocumentPath != ''){
    		// 修改回显送达回证
    		backHoldFile = initAttachmentInfo('commonCase', backHoldDocumentPath, 'backHoldDocument');
    	}
	}
}

/**
 * 保存 方法
 * 
 * @returns
 */
function doResearchSave() {
	// 禁用保存按钮
	if ($("#common_case_add_2_research_form").form('enableValidation').form('validate')) {
		var params = tools.formToJson($("#common_case_add_2_research_form"));
		
		// 作出处罚信息保存
        if(informingbookFile != null && informingbookFile.length > 1){
            $.MsgBox.Alert_auto("请仅上传一个告知书");
            $('html, body').animate({scrollTop: $("#informingbookDocument").offset().top}, 500);
            return false;
        }
        
        if(backHoldFile != null && backHoldFile.length > 1){
            $.MsgBox.Alert_auto("请仅上传一个送达回证");
            $('html, body').animate({scrollTop: $("#backHoldDocument").offset().top}, 500);
            return false;
        }
		
		var informingPunishType = '';
		if($("input[name='informingPunishType']:checked").length >0){
			$("input[name='informingPunishType']:checked").each(function(){
				informingPunishType += this.value + ',';
			})
		}
		if(informingPunishType == ''){
			$.MsgBox.Alert_auto("请勾选至少一种告知处罚种类");
            $('html, body').animate({scrollTop: $("#informPunishType").offset().top}, 500);
            return false;
		}
		var rightInform = '';
		if($("input[name='rightInform']:checked").length >0){
			$("input[name='rightInform']:checked").each(function(){
				rightInform += this.value + ',';
			})
		}
		surveyJsons = [];
		// 调查信息对象转成json字符串
		var jsons = $("#survey_information").datagrid("getRows");
		if(jsons != null && jsons.length > 0){
			for(var i=0; i<jsons.length; i++){
				var surveyJson = {
					id : jsons[i].id,
					surveyDateStr : jsons[i].surveyDateStr,
					surveyPerson : jsons[i].surveyPerson,
					surveyObject : jsons[i].surveyObject,
					address : jsons[i].address,
					surveyRecord : jsons[i].surveyRecord,
					sceneMeasures : jsons[i].sceneMeasures,
					createDateStr : jsons[i].createDateStr,
					updateDateStr : jsons[i].updateDateStr,
					updatePersonId : jsons[i].updatePersonId
				};
				surveyJsons.push(surveyJson);
			}
		}
        params.surveyInfoJsonStr = tools.parseJson2String(surveyJsons);
        
        illegalJsons = [];
		// 证据信息对象转成json字符串
		var jsons = $("#illegal_information").datagrid("getRows");
		if(jsons != null && jsons.length > 0){
			for(var i=0; i<jsons.length; i++){
				var illegalJson = {
					id : jsons[i].id,
					illegalEvidenceType : jsons[i].illegalEvidenceType,
					illegalEvidenceTypeValue : jsons[i].illegalEvidenceTypeValue,
					illegalSource : jsons[i].illegalSource,
					illegalSourceValue : jsons[i].illegalSourceValue,
					illegalPerson : jsons[i].illegalPerson,
					illegalDateStr : jsons[i].illegalDateStr,
					address : jsons[i].address,
					illegalDocumentName : jsons[i].illegalDocumentName,
					illegalDocumentPath : jsons[i].illegalDocumentPath,
					createTimeStr : jsons[i].createTimeStr,
					updateTimeStr : jsons[i].updateTimeStr,
					updatePersonId : jsons[i].updatePersonId
				};
				illegalJsons.push(illegalJson);
			}
		}
        params.illegalJsonStr = tools.parseJson2String(illegalJsons);
        
		params.informingPunishType = informingPunishType;
		params.rightInform = rightInform;
		params.id = caseId;
        params.isNext = 2; //调查取证保存tabs页签index
        params.editFlag = parseInt(editFlag);
        params.grading = '02';
        params.currentState = '02';

		var json = tools.requestJsonRs("/caseCommonBaseCtrl/saveFilingStage.action", params);
		if (json.rtState) {
			// 保存成功，进行下一步
			var tabId = "common_case_add_tabs";
			var title = "审查决定";
			var url = '/caseCommonBaseCtrl/commonCaseAddGrading.action';
			var pageUrl = '/supervise/caseManager/commonCase/common_case_add_3_correct.jsp';
			var grading = '03';
			var paramsObj = {
				pageUrl : pageUrl,
				grading : grading,
				caseId : caseId,
				editFlag : editFlag,
				saveEvent : '01', // 点击保存事件
				modelId : researchModelId
			}
			url = url + "?" + $.param(paramsObj);
			parent.addTab(tabId, title, url);
			editFlag = '2';
			return json.rtState;
		} else {
			$.MsgBox.Alert_auto("保存失败！");
			return json.rtState;
		}
	} else {
		return false;
	}
}