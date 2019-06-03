var caseSourceJson = []; //定义案件来源信息对象
var personJson = [];//定义人员对象
var personIds = [];// 新增人员ID
var subJson = [];//主体对象json
var deptJson = [];//系统对象json
var modelId = $('#modelId').val();//弹框ID
var cardTypeJsons = [];
var editFlag = $("#editFlag").val();
var caseId = $("#caseId").val();

/**
 * 默认加载方法
 * @returns
 */
function doInitFiling(){
	if (editFlag=='2') {
        //修改和查看初始化页面数据
        doInitEditPage();
    }else{
        initCaseId();//修改和查看时不需要生成caseId
        initIndexDatagrid({ids:'empty'});//执法人员列表
        initCodeListInput("COMMON_CASE_SOURCE","caseSource");// 案件来源
        initCodeListInput("COMMON_SENT_WAY","sendWay");// 送达方式
        initPartyTypeInput();// 当事人类型
        initSubjectInput();// 执法主体
        initPunishDecisionType();
    }
	var classTd1 = $(".case-simple-td-class1").width();
	var classTd2 = $(".case-simple-td-class2").width();
	if(classTd2>250){
		wid = classTd1+classTd2+250;
		var pageDoc = $("#name_td").html('<input class="easyui-textbox" name="name" id="name" data-options="required:true, novalidate:true, validType:\'length[0,200]\', missingMessage:\'请输入事由（案由）\' "'
                            +'style="width: '+wid+'px;height:30px;"/>');
		$.parser.parse(pageDoc);
	}
}

/**
 * 初始化处罚决定类型
 * @returns
 */
function initPunishDecisionType(value){
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: 'SIMPLE_PUNISH_DECISION_TYPE'});
    if(json.rtState) {
        var page = "";
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="punishDecisionType" id="punishDecisionType'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'"/>';
        }
        $('#punishDecisionTypeSpan').html(page);
        $.parser.parse($('#punishDecisionTypeSpan'));
        $("#fineSumSpan").hide();
        $("#punishDecisionType02").checkbox({
            onChange: function(checked){
                if(checked){
                    $("#fineSumSpan").show();
                    $("#fineSum").textbox({disabled:false,required:true, missingMessage:'请输入罚款金额', validType:'decimal'});
                }else{
                    $("#fineSumSpan").hide();
                    $("#fineSum").textbox({disabled:true,required: false});
                }
            }
        });
        if(typeof(value)!='undefined' && value != null && value.length > 0){
            for(var j=0;j<value.split(",").length;j++){
                $("#punishDecisionType"+value.split(",")[j]).checkbox({checked: true});
            }
            if(value.split(",").contains("02")){
                $("#fineSumSpan").show();
                $("#fineSum").textbox({required:true, missingMessage:'请输入罚款金额', validType:'decimal'});
            }
        }
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
 * 获取登录信息，加载主体，和所属系统
 * @returns
 */
function getUserSubjetAndDepartment(){
    var json = tools.requestJsonRs("/commonCtrl/getRelations.action");
    if(json.rtState){
        if(json.rtData != null && json.rtData != ''){
            subJson = [];//主体对象json
            deptJson = [];//系统对象json
            var subArr = []; //主体去重
            var deptArr = [];//部门去重
            for(var i=0; i < json.rtData.length; i++){
                var sub = {}; //主体对象
                var dept = {};//系统对象
                sub.codeNo = json.rtData[i].businessSubjectId;
                sub.codeName = json.rtData[i].businessSubjectName;
                dept.codeNo = json.rtData[i].businessDeptId;
                dept.codeName = json.rtData[i].businessDeptName;
                if(subArr.indexOf(sub.codeNo) == -1){
                    //不存在，则存入数组
                    subArr.push(sub.codeNo);
                    subJson.push(sub);
                }
                if(deptArr.indexOf(dept.codeNo) == -1){
                    //不存在，则存入数组
                    deptArr.push(dept.codeNo);
                    deptJson.push(dept);
                }
            }
            initCommonCaseSelectJson('subjectId', subJson);
            $('#departmentId').textbox('setValue',deptJson[0].codeNo);//默认选中第一个部门
            $('#departmentId_label').html(deptJson[0].codeName);//默认选中第一个部门
        }
    }
}

/**
 * 初始化执法人员
 * @returns
 */
function initIndexDatagrid(params){
    datagrid = $('#case_simple_add_1_datagrid').datagrid({
        url: contextPath + '/caseSimpleStaffCtrl/findListBypage.action',
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
        singleSelect: false, //为true只能选择一行
        nowrap: true,
        onLoadSuccess: function(data) {
            personIds = [];
            if(data.rows != null && data.rows.length > 0){
                for (var i = 0; i < data.rows.length; i++) {
                    personIds.push(data.rows[i].id);
                }
            }
        },
        columns: [[
            { 
                field: 'id', checkbox: true, title: "ID", width: 10, halign: 'center', align: 'center'
            },
            {field:'ID',title:'序号',align:'center',
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field: 'subjectName', title: '执法主体', width: 20, halign: 'center', align: 'left',
                formatter: function(value, rowData) {
                    if(value == null || value == 'null') {
                        value = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</lable>"
                    return lins;
                }
            },
            {
                field: 'name', title: '姓名', width: 12, halign: 'center', align: 'center'
            },
            {
                field: 'code', title: '执法证号 ', width: 12, halign: 'center', align: 'center'
            },
            {
                field: '___',
                title: '操作' , halign: 'center', align: 'center',
                formatter: function(e, rowData) {
                    var optStr = "&nbsp;&nbsp;&nbsp;&nbsp;<a href='###' onclick='deleteRow(\"" + rowData.id + "\")'><i class='fa fa-trash'></i> </a>&nbsp;&nbsp;&nbsp;&nbsp;";
                    return optStr;
                },
                width: 10
            }
        ]]
    });
}

/**
 * 生成caseid
 */
function initCaseId(){
    var json = tools.requestJsonRs("/caseSimpleBaseCtrl/initCaseId.action");
    if(json.rtState){
        caseId = json.rtData;
    }
}

/**
 * datagrid中删除行
 * @param id
 */
function deleteRow(id){
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
    initIndexDatagrid(params);
}

/**
 * 打开添加执法人员页面
 * @returns
 */
function doAddGistLawDetail() {
    var subjectId = $('#subjectId').combobox('getValue');
    if (subjectId == "") {
        alert("请选择执法主体");
        return;
    }
    var url=contextPath+"/supervise/caseManager/simpleCase/addStaff.jsp?subjectId="+subjectId;
    top.bsWindow(url ,"添加执法人员",{width:"1000",height:"500",buttons:
        [
            {name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "保存") {
                var personIdList = cw.saveLawDetails();
                /*if(personIds.length + personIdList.length > 2){
                    top.$.MsgBox.Alert("提示","总共只能添加2名执法人员");
                    return false;
                }*/
                if(personIds == null || personIds.length == 0) {
                    for(var index in personIdList) {
                        personIds.push(personIdList[index].id);
                    }
                } else {
                    for(var index in personIdList) {
                        if(personIds.indexOf(personIdList[index].id) == -1) {
                            personIds.push(personIdList[index].id);
                        }
                    }
                }
                var params = {
                    ids: personIds.join(",")
                };
                initIndexDatagrid(params);
                return true;
            } else if(v=="关闭"){
                return true;
            }
        }
    });
}
/**
 * 拿到主体id
 */
$('#subjectId').combobox({
    onChange : function(n,o){
        parent.subjectId = n;
    }  
});

/**
 * 页面跳转到下一页
 */
function nextPage(){
    if($("#common_case_add_1_filing_form").form('enableValidation').form('validate')){
        var params = tools.formToJson($("#common_case_add_1_filing_form"));
        
        // 获取处罚决定种类
        var punishDecisionType = '';
        if($("#common_case_add_1_filing_form input[name='punishDecisionType']:checked").length >0){
            $("#common_case_add_1_filing_form input[name='punishDecisionType']:checked").each(function(){
                if('01'==this.value){
                	params.isWarn = 1;
                }else if('02'==this.value){
                	params.isFine = 1;
                }
            })
        }else{
            $.MsgBox.Alert_auto("请选择至少一种处罚决定种类！");
            $('html, body').animate({scrollTop: $("#punishDecisionTypeSpan").offset().top}, 500);
            return false;
        }
        if(typeof(params.isFine)=='undefined' || params.isFine != 1){
            params.fineSum="";
        }else{
        	if(params.fineSum>1000){
        		$.MsgBox.Alert_auto("罚款金额应小于1000元");
        	}
        }
        if(punishDecisionType.length>0){
            punishDecisionType = punishDecisionType.substring(0,punishDecisionType.length-1);
        }
        params.punishDecisionType = punishDecisionType;
        
        // 获取执法人员
        if ($('#case_simple_add_1_datagrid').datagrid('getData').rows.length < 2) {
            $.MsgBox.Alert_auto("请选择至少两名执法人员");
            $('html, body').animate({scrollTop: $("#case_simple_add_1_datagrid").offset().top}, 500);
            return false;
        }
        
        // 案件部门、地区、执法系统手动设置
        var subJson = tools.requestJsonRs("/subjectSearchController/getSubjectById.action", {id:params.subjectId});
        if(subJson.rtState){
        	params.departmentId = subJson.rtData.departmentId;
        	params.area = subJson.rtData.areaId;
        	params.orgSys = subJson.rtData.orgSysId;
        	params.isDepute = subJson.rtData.isDepute;
        }
        
        params.id = caseId;
        params.staffIds = personIds.join(",");
        parent.filingForm = params;
        return true;
    }
}

/**
 * 修改时，加载页面
 * @returns
 */
function doInitEditPage(){
    var json = tools.requestJsonRs("/caseSimpleBaseCtrl/findSimpleBaseById.action?id=" + caseId);
    if(json.rtState) {
        parent.filingForm = json.rtData;
        bindJsonObj2Easyui(json.rtData, 'common_case_add_1_filing_form');
        //debugger;
        initCodeListInput("COMMON_CASE_SOURCE","caseSource",json.rtData.caseSource);// 案件来源
        initCodeListInput("COMMON_SENT_WAY","sendWay",json.rtData.sendWay);// 送达方式
        initPartyTypeInput(json.rtData.partyType);// 当事人类型
        initCardTypeInput(cardTypeJsons,json.rtData.partyType + "" + json.rtData.cardType);// 行政相对人证件类型
        initSubjectInput(json.rtData.subjectId);// 执法主体
        initPunishDecisionType(json.rtData.punishDecisionType);
        parent.subjectId = json.rtData.subjectId;

        var params;
        if(json.rtData.staffIds != null && json.rtData.staffIds != ''){
            personIds = [];
            personIds = json.rtData.staffIds.split(",");
            params = {
                    ids: json.rtData.staffIds
            };
        }else{
            params = {
                    ids: 'empty'
            };
        }
        initIndexDatagrid(params);
    }
}