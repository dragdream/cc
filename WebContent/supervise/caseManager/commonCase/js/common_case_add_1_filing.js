
var personIds = []; //定义人员ID对象
var personJson = [];//定义人员对象
var caseId = $('#comm_case_add_filing_caseId').val(); //案件ID
var editFlag = $('#common_case_add_filing_editFlag').val(); //编辑标识（1新增，2修改，3查看）
var isNext = $('#common_case_add_filing_isNext').val();//tab页签标识（立案0,调查取证1,处罚决定2,处罚执行3,结案4）
var subejctId = $('#common_case_add_filing_subjectId').val();//主体ID
var filingModelId = $('#common_case_add_filing_modelId').val();//弹框ID
var introduceFile = [];//处罚决定文书
/**
 * 默认加载方法
 * @returns
 */
function doInitFiling(){
	//easyui-panel适应父容器大小
	//上传立案呈批表
    doInitMultipleUploadIntroduce('commonCase', caseId+'introduceDocumentId', 'filingApprovalDocument');
    
    if('1' == editFlag){// 新增
        initAddPersonDatagrid({ids: 'empty'});//执法人员列表
        initCodeListInput("COMMON_CASE_SOURCE","caseSource");// 案件来源
        initPartyTypeInput();// 当事人类型
        initSubjectInput();// 执法主体
    }else{// 修改
        doInitEditPage();
    }
}

/**
 * 初始化行政相对人类型
 */
function initPartyTypeInput(value) {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "COMMON_PARTY_TYPE"});
    if(json.rtState) {
        $('#partyType').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            editable: false,
            prompt : '请选择',
            onLoadSuccess:function(){
                if(value != null && value != "" && value != '0'){
                    $(this).combobox('setValue',value);
                    if(value == '02' || value == "03"){
                    	$('#adressTr').show();
                        $('#registerAddress').textbox({disabled:false, validType:'length[0,200]', required:true, missingMessage:'请填写注册地址'});
                        $('#businessAddress').textbox({disabled:false, required:true, validType:'length[0,200]', missingMessage:'请填写经营地址'});
                    }else{
                        $('#registerAddress').textbox({disabled:true, required:false});
                        $('#businessAddress').textbox({disabled:true, required:false});
                        $('#adressTr').hide();
                    }
                    $('#cardType').combobox({ disabled: false });
                    $('#partyName').textbox({ disabled: false });
                    var params = {
                            parentCodeNo: "COMMON_CARD_TYPE",
                            codeNo: value
                        };
                        cardTypeJsons = [];
                        var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action", params);
                        if(result.rtState) {
                            cardTypeJsons = result.rtData;
                        }
                }else{
                    $('#cardType').combobox({ disabled: true });
                    $('#partyName').textbox({ disabled: true });
                    $('#contactPhone').textbox({ disabled: true });
                    $('#address').textbox({ disabled: true });
                    $('#cardCode').textbox({ disabled: true });
                }
            },
            onChange: function() {
                var partyType = $('#partyType').combobox('getValue');
                if(partyType != "") {
                    if(partyType == '02' || partyType == "03"){
                    	$('#adressTr').show();
                        $('#registerAddress').textbox({disabled:false, validType:'length[0,200]', required:true, missingMessage:'请填写注册地址'});
                        $('#businessAddress').textbox({disabled:false, required:true, validType:'length[0,200]', missingMessage:'请填写经营地址'});
                    }else{
                        $('#registerAddress').textbox({disabled:true, required:false});
                        $('#businessAddress').textbox({disabled:true, required:false});
                        $('#adressTr').hide();
                    }
                	
                	var params = {
                        parentCodeNo: "COMMON_CARD_TYPE",
                        codeNo: partyType
                    };
                    cardTypeJsons = [];
                    var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action", params);
                    if(result.rtState) {
                        cardTypeJsons = result.rtData;
                    }
                    initCardTypeInput(cardTypeJsons);
                    $('#cardType').combobox({disabled: false });
                    $('#partyName').textbox({ disabled: false });
                    $('#contactPhone').textbox({ disabled: false });
                    $('#address').textbox({ disabled: false });
                }else{
                    $('#cardType').combobox({disabled: true });
                    $('#partyName').textbox({ disabled: true });
                    $('#contactPhone').textbox({ disabled: true });
                    $('#address').textbox({ disabled: true });
                    $('#cardCode').textbox({ disabled: true });
                }
            }
        });
    }
}

/**
 * 初始化证件类型
 */
function initCardTypeInput(cardTypeJsons,value) {
    $('#cardType').combobox({
        data: cardTypeJsons,
        valueField: 'codeNo',
        textField: 'codeName',
        editable: false,
        prompt : '请选择',
        panelHeight: 'auto',
        onLoadSuccess:function(){
            if(value != null && value != "" && value != '0'){
                $(this).combobox('setValue',value);
            }else{
                $('#cardCode').textbox({ disabled: true });
            }
        },
        onChange: function() {
            var cardType = $('#cardType').combobox('getValue');
            cardType = cardType.substr(2);
            if(cardType != "") {
                $('#cardCode').textbox({ disabled: false });
                if ('01' == cardType){// 身份证号
                    $('#cardCode').textbox({validType:['length[0,18]','idCard']});
                }else if ('05' == cardType){// 统一社会信用代码
                    $('#cardCode').textbox({validType:['length[0,18]','organizationCode']});
                }else{
                    $('#cardCode').textbox({validType:'length[0,18]'});
                }
            }else{
                $('#cardCode').textbox({ disabled: true });
            }
        }
    });
}

/**
 * 查询人员信息
 * @returns
 */
function commonFindPerson(){
    var subjectId = $('#subjectId').combobox('getValue');
    if (subjectId == null || subjectId == "" || subjectId == 'null') {
        $.MsgBox.Alert_auto("请选择执法主体");
        $('html, body').animate({scrollTop: $("#subjectId_td").offset().top}, 500);
        return false;
    }
    var url=contextPath+"/caseCommonStaffCtrl/commonCaseAddStaff.action?subjectId="+subjectId;
    top.bsWindow(url ,"添加执法人员",{width:"1000",height:"500",buttons:
        [
            {name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "保存") {
                var personList = cw.savePersonList();
                /*if(personIds.length + personList.length > 2){
                	top.$.MsgBox.Alert("提示","总共只能添加2名执法人员");
                	return false;
                }*/
                if(personList == null || personList.length == 0) {
                    for(var index in personList) {
                        personIds.push(personList[index].id);
                    }
                } else {
                    for(var index in personList) {
                        if(personIds.indexOf(personList[index].id) == -1) {
                            personIds.push(personList[index].id);
                        }
                    }
                }
                var params = {
                    ids: personIds.join(","),
                    editFlag: editFlag
                };
                initAddPersonDatagrid(params);
                return true;
            } else if(v=="关闭"){
                return true;
            }
        }
    });
}

/**
 * 表格加载函数
 * @returns
 */
function initAddPersonDatagrid(params){
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
            { 
                field: 'id', checkbox: true, title: "ID", width: 10, halign: 'center', align: 'center', hidden: true
            },
            {field:'ID',title:'序号',align:'center', width: 5,
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field: 'subjectName', title: '所属主体', width: 20, halign: 'center', align: 'left',
                formatter: function(value, rowData) {
                    if(value == null || value == 'null') {
                        value = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</lable>"
                    return lins;
                }
            },
            {
                field: 'name', title: '执法人员 ', width: 12, halign: 'center', align: 'center'
            },
            {
                field: 'code', title: '执法证号 ', width: 12, halign: 'center', align: 'center'
            },
            {
                field: '___',
                title: '操作' , halign: 'center', align: 'center',
                formatter: function(e, rowData) {
                    var optStr = "";
                    if (editFlag != '3'){
                        optStr = "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deleteAddPerson(\"" + rowData.id + "\")'><i class='fa fa-trash'></i> </a>&nbsp;&nbsp;&nbsp;&nbsp;";
                    }
                    return optStr;
                },
                width: 5
            }
        ]]
    });
}

/**
 * 删除添加的执法人员
 * @param id 执法人员的ID
 * @returns
 */
function deleteAddPerson(id){
    for(var index in personIds) {
        if(personIds[index] == id) {
            personIds.splice(index, 1);
            break;
        }
    }
    var params;
    if(personIds.length == 0) {
        params = {ids: "empty"};
    }else {
        params = {
            ids: personIds.join(",")
        }
    }
    initAddPersonDatagrid(params);
}

/**
 * 修改时，加载页面
 * @returns
 */
function doInitEditPage(){
    var grading = '01';// 立案阶段
    var json = tools.requestJsonRs("/caseCommonBaseCtrl/findCaseCommonBaseById.action?id=" + caseId +"&grading="+grading);
    if(json.rtState) {
        bindJsonObj2Easyui(json.rtData, 'common_case_add_1_filing_form');
        
        initAddPersonDatagrid({ids: 'empty'});//执法人员列表
        initCodeListInput("COMMON_CASE_SOURCE","caseSource",json.rtData.caseSource);// 案件来源
        initPartyTypeInput(json.rtData.partyType);// 当事人类型
        initCardTypeInput(cardTypeJsons,json.rtData.partyType + "" + json.rtData.cardType);// 当事人证件类型
        initSubjectInput(json.rtData.subjectId);// 执法主体
        
        var filingApprovalDocumentPath = json.rtData.filingApprovalDocumentPath;
    	if(filingApprovalDocumentPath != null && filingApprovalDocumentPath != ''){
    		// 修改回显处罚决定书
    		introduceFile = initAttachmentInfo('commonCase', filingApprovalDocumentPath, 'filingApprovalDocument');
    	}
    	
        // 执法人员信息加载
        var personJsonStr = json.rtData.personJsonStr;
        var personJson;
        var params;
        if(personJsonStr != null && personJsonStr != '' && personJsonStr != 'null'){
            personJson = personJsonStr.split(",");
            for (var i = 0; i < personJson.length; i++){
                personIds.push(personJson[i]);
            }
            params = {ids: personIds.join(',')};
            if(personIds == null || personIds.length < 1){
                params = {ids: 'empty'};
            }
        }else{
            params = {ids: 'empty'};
        }
        initAddPersonDatagrid(params);//修改加载数据
    }
}
/**
 * 立案阶段保存方法
 * @returns
 */
function doFilingSave(){
	// 禁用保存按钮
	//$('#btn').linkbutton('disable');
	//得到表单窗口中的form对象
    if($("#common_case_add_1_filing_form").form('enableValidation').form('validate')){
        var params = tools.formToJson($("#common_case_add_1_filing_form"));
        
        // 立案呈批表保存
        if(introduceFile != null && introduceFile.length > 0){
            if(introduceFile.length > 1){
                $.MsgBox.Alert_auto("请仅上传一个决定书");
                $('html, body').animate({scrollTop: $("#introduceDocumentName").offset().top}, 500);
                return false;
            }else if(introduceFile.length == 1){
            	//params.filingApprovalDocumentPath = $('#introduceFileId').val();
            }
        }
        
        var personRows = $("#common_case_add_person_datagrid").datagrid("getRows");
        if(personRows == null || personRows.length < 2){
            $.MsgBox.Alert_auto("请选择至少两名执法人员");
            $('html, body').animate({scrollTop: $("#common_case_add_person_div").offset().top}, 500);
            //$('#btn').linkbutton('enable');
            return false;
        }
        personJson = []//执法人员对象
        subejctId = $('#subjectId').combobox('getValue');
        for(var i=0;i<personRows.length;i++){
            var obj = {};
            obj.caseId = caseId;
            obj.identityId = personRows[i].id;
            obj.officeName = personRows[i].name;
            obj.cardCode = personRows[i].code;
            obj.subjectId = subejctId;
            personJson.push(obj);
        }
        //执法人员对象转成json字符串
        params.personJsonStr = tools.parseJson2String(personJson);
        params.id = caseId;
        params.isNext = 1; //立案保存tabs页签index
        params.editFlag = parseInt(editFlag);
        params.grading = '01'; //立案环节
        params.currentState = '01'; //当前状态
        
        // 案件部门、地区、执法系统手动设置
        var subJson = tools.requestJsonRs("/subjectSearchController/getSubjectById.action", {id:params.subjectId});
        if(subJson.rtState){
        	params.departmentId = subJson.rtData.departmentId;
        	params.area = subJson.rtData.areaId;
        	params.orgSys = subJson.rtData.orgSysId;
        	params.isDepute = subJson.rtData.isDepute;
        }
        var json = tools.requestJsonRs("/caseCommonBaseCtrl/saveFilingStage.action", params);
        if(json.rtState){
            //保存成功，进行下一步
            var tabId="common_case_add_tabs";
            var title = "调查取证";
            var url = '/caseCommonBaseCtrl/commonCaseAddGrading.action';
            var pageUrl = '/supervise/caseManager/commonCase/common_case_add_2_research.jsp';
            var grading = '02';
            var paramsObj = {
                    pageUrl: pageUrl,
                    grading: grading,
                    caseId: caseId,
                    editFlag: editFlag,
                    saveEvent: '01', //点击保存事件
                    modelId: filingModelId
            }
            url = url + "?"+$.param(paramsObj);
            parent.addTab(tabId, title, url);
            editFlag = '2';
            //$('#btn').linkbutton('enable');
            return json.rtState;
        }else{
            $.MsgBox.Alert_auto("保存失败！");
            //$('#btn').linkbutton('enable');
            return json.rtState;
        }
    }else{
    	//$('#btn').linkbutton('enable');
    	return false;
    }
    
}

/**
 * 附件上传
 * @param model 后台文件路径文件夹
 * @param modelId 查询文件关键字
 * @param elemId 前台展示元素ID
 * @returns
 */
function doInitMultipleUploadIntroduce(model, modelId, elemId) {
  //多附件快速上传
    swfUploadObj = new TeeSWFUpload({
        fileContainer:"fileContainerIntroduce",//文件列表容器
        uploadHolder:"uploadHolderIntroduce",//上传按钮放置容器
        valuesHolder:"attachmentSidStrIntroduce",//附件主键返回值容器，是个input
        quickUpload:true,//快速上传
        showUploadBtn:false,//不显示上传按钮
        queueComplele:function(files){//队列上传成功回调函数，可有可无
        },
        uploadSuccess:function(files){//上传成功
        	$.MsgBox.Alert_auto("上传成功");
        	introduceFile.push(files.sid);//存放上传文件ID
            introduceFile = addAttachmentProp(files, elemId, introduceFile);
        },
        renderFiles:false,//渲染附件
        post_params:{model:model, modelId: modelId}//后台传入值，model为模块标志
    });
}
/**
 * 上传完毕后加载已经上传的文件
 * @param obj 文件对象
 * @param elemId 展示元素ID
 * @returns
 */
function addAttachmentPropIntroduce(obj, elemId) {
    var mainObj = $('#' + elemId);
    var page = "";
    page = page + '<div style="float: left; margin-right:20px" id="flowsheet' + obj.sid + '">';
    page = page + '<input type="hidden" name="introduceFileId" id="introduceFileId" value="' + obj.sid + '" />';
    page = page + '<input type="hidden" name="introduceFileName" id="introduceFileName" value="' + obj.fileName + '" />';
    page = page + '<a href="javascript:void(0);" sid="FILE' + obj.sid + '"></a>';
    page = page + '</div>';
    mainObj.append(page);
    
    obj.priv = 1+2+4; // 阅读、下载、删除
    var attach = tools.getAttachElement(obj, {deleteEvent:function(attachModel){
        $("#flowsheet" + attachModel.sid).remove();
        for(var index in introduceFile) {
            if(introduceFile[index] == attachModel.sid) {
                introduceFile.splice(index, 1);
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
function initAttachmentInfoIntroduce(model, sid, elemId) {
    var json = tools.requestJsonRs("/attachmentController/getAttachmentModelsByIds.action?attachIds="+sid);
    if(json.rtData != null && json.rtData.length > 0) {
        introduceFile = [];
        var attachList = json.rtData;
        $.each(attachList, function(i, o){
            // 添加附件属性
            introduceFile.push(o.sid);//存放上传文件ID
            addAttachmentPropIntroduce(o, elemId);
        });
    }
}