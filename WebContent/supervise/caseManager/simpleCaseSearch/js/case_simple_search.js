var params;
var partyTypeJsons = [];
var cardTypeJsons = [];
var permissionTypeJsons = [];
/**
 * 可选列
 */
var listdata = {
    isShowColumn: [
        {codeNo:'punishDecisionTypeValue', codeName: '处罚决定种类'},
        {codeNo:'name', codeName: '案由（事由）'},
        {codeNo:'partyTypeValue', codeName: '当事人类型'},
        {codeNo:'partyName', codeName: '当事人名称'},
        {codeNo:'cardTypeValue', codeName: '当事人证件类型'},
        {codeNo:'cardCode', codeName: '证件号码'},
        {codeNo:'subjectName', codeName: '执法主体'},
        {codeNo:'punishDecisionExecutDateStr', codeName: '处罚决定执行日期'},
        {codeNo:'closedDateStr', codeName: '结案日期'},
        {codeNo:'dataSourceValue', codeName: '数据来源'},
        {codeNo:'createTimeStr', codeName: '入库日期'}
    ]
};

/** 可选列提示框大小控制 */
var i;
if(listdata.isShowColumn.length%7==0){
    i=listdata.isShowColumn.length/7;
}else {i=Math.ceil(listdata.isShowColumn.length/7)}

var widthnum = i*190+10;

var width = widthnum+'px';

$(".panList").css("width",width);

$(".isshow").on("click",function(){
	$(".panList").show();
	$("body").append('<div id="panListBack" class=""></div>');
});

$("body").delegate("#panListBack","click",function(){
	$(".panList").hide();
	$("#panListBack").remove();
});

/** 初始化展示可选列 */
var temp = ['punishDecisionTypeValue','subjectName','createTimeStr'];

/**
 * 可选列选中/取消
 * @param code
 * @param th
 */
function detail(code,th){
    if($(th).children('i').hasClass("fa-check")){
        $(th).children('i').removeClass("fa-check");
        $('#simple_case_index_datagrid').datagrid('hideColumn', code);
        for(var i=0;i<temp.length;i++){
            if(temp[i] == code){
                temp.splice(i,1);
                break;
            }
        }
    } else{
        $(th).children('i').addClass("fa-check");
        $('#simple_case_index_datagrid').datagrid('showColumn', code);
        temp.push(code);
    }
}

var tpl=[
    '{@each isShowColumn as it}',
    '<li onclick="detail(\'${it.codeNo}\',this)" title="${it.codeName}" id=\'${it.codeNo}\'><i class="fa"></i>${it.codeName}</li>',
    '{@/each}'
].join('\n');

/**
 * 默认加载方法
 * @returns
 */
function doInit(){
    // 隐藏查询页
    $("#simpleCaseDiv").hide();
    dateValidate('beginpunishmentDateStr', 'endpunishmentDateStr');
    dateValidate('beginpunishDecisionExecutDateStr', 'endpunishDecisionExecutDateStr');
    initSubject(); // 加载执法主体
    initDepartment(); // 加载执法机关
    orgSysInit(); // 加载执法系统
    areaInit(); // 加载行政区划
    initPartyType(); // 加载当事人类型
    initCardType(); // 加载当事人证件类型
    initPunishDecisionType();// 加载处罚决定种类
    // 初始化可选列
    $(".panList").append(juicer(tpl,listdata));
    // 初始化表格
    datagrid = $('#simple_case_index_datagrid') .datagrid( {
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar : '#toolbar'// 工具条对象
    });
}

/**
 * 初始化当事人类型
 * @returns
 */
function initPartyType(){
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: 'COMMON_PARTY_TYPE'});
    if(json.rtState) {
        var page = "";
        var partyTypeJson;
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelWidth="110px" labelAlign="left" labelPosition="after" type="checkbox" name="partyType" id="partyType'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'"/>';
            partyTypeJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
            partyTypeJsons.push(partyTypeJson);
        }
        var pageDoc = $('#common_case_part_type_td').html(page);
        $.parser.parse(pageDoc);
    }
}

/**
 * 初始化当事人证件类型
 * @returns
 */
function initCardType(){
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: 'COMMON_CARD_TYPE_NEW'});
    if(json.rtState) {
        var page = "";
        var cardTypeJson;
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelWidth="110px" labelAlign="left" labelPosition="after" type="checkbox" name="cardType" id="cardType'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'"/>';
            cardTypeJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
            cardTypeJsons.push(cardTypeJson);
        }
        var pageDoc = $('#common_case_card_type_td').html(page);
        $.parser.parse(pageDoc);
    }
}

/**
 * 初始化执法主体
 * @param id
 */
function initSubject(id){
    var json = tools.requestJsonRs("/subjectSearchController/getSubjectRoles.action");
    if(json.rtState) {
        $('#subjectId').combobox({
            data: json.rtData,
            valueField: 'id',
            textField: 'subName',
            panelHeight: 'auto',
            panelMaxHeight : 192,
            multiple:true,
            prompt : '请选择',
            onHidePanel: function() {
                var valueField = $(this).combobox("options").valueField;
                var val = $(this).combobox("getValues"); //当前combobox的值
                var allData = $(this).combobox("getData");  //获取combobox所有数据
                var unSelect = [allData.length]
                var currentValue=val.toString().split(",");//把选中的值及输入值分割为数组
                for(var j=0;j<currentValue.length;j++){//循环选中的值和com中所有值进行比对，不存在的利用unselect清除
                    var result = true;     //为true说明输入的值在下拉框数据中不存在
                    for (var i = 0; i < allData.length; i++) {
                        if (currentValue[j] == allData[i][valueField]) {
                            result = false;
                        }
                    }
                    if(result){//仅仅清除不存在的值
                        $(this).combobox('unselect', currentValue[j]);
                    }
                }
            }
        });
    }
}

/**
 * 初始化执法机关
 */
function initDepartment(){
    var json = tools.requestJsonRs("/departmentSearchController/getDepartmentRoles.action");
    if(json.rtState) {
        $('#departmentId').combobox({
            data: json.rtData,
            valueField: 'id',
            textField: 'name',
            panelHeight: 'auto',
            panelMaxHeight : 192,
            multiple:true,
            prompt : '请选择',
            onHidePanel: function() {
                var valueField = $(this).combobox("options").valueField;
                var val = $(this).combobox("getValues"); //当前combobox的值
                var allData = $(this).combobox("getData");  //获取combobox所有数据
                var unSelect = [allData.length]
                var currentValue=val.toString().split(",");//把选中的值及输入值分割为数组
                for(var j=0;j<currentValue.length;j++){//循环选中的值和com中所有值进行比对，不存在的利用unselect清除
                    var result = true;     //为true说明输入的值在下拉框数据中不存在
                    for (var i = 0; i < allData.length; i++) {
                        if (currentValue[j] == allData[i][valueField]) {
                            result = false;
                        }
                    }
                    if(result){//仅仅清除不存在的值
                        $(this).combobox('unselect', currentValue[j]);
                    }
                }
            }
        });
    }
}

/**
 * 初始化执法系统
 */
function orgSysInit(){
    var json = tools.requestJsonRs("/commonCtrl/getOrgSys.action");
    if(json.rtState) {
        $('#orgSys').combobox({
            data: json.orgSys,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            panelMaxHeight : 192,
            multiple:true,
            prompt : '请选择',
            onHidePanel: function() {
                var valueField = $(this).combobox("options").valueField;
                var val = $(this).combobox("getValues"); //当前combobox的值
                var allData = $(this).combobox("getData");  //获取combobox所有数据
                var unSelect = [allData.length]
                var currentValue=val.toString().split(",");//把选中的值及输入值分割为数组
                for(var j=0;j<currentValue.length;j++){//循环选中的值和com中所有值进行比对，不存在的利用unselect清除
                    var result = true;     //为true说明输入的值在下拉框数据中不存在
                    for (var i = 0; i < allData.length; i++) {
                        if (currentValue[j] == allData[i][valueField]) {
                            result = false;
                        }
                    }
                    if(result){//仅仅清除不存在的值
                        $(this).combobox('unselect', currentValue[j]);
                    }
                }
            }
        });
    }
}

/**
 * 行政区划
 */
function areaInit(){
    $('#area').combobox({
        prompt:'请选择',
        mode:'remote',
        url:contextPath + '/adminDivisionManageCtrl/getAreaSearch.action',
        valueField:'ID',
        textField:'NAME',
        multiple:true,
        method:'post',
        panelHeight: 'auto',
        panelMaxHeight : 192,
        labelPosition: 'top'
    });
}

/**
 * 初始化处罚决定类型
 * @returns
 */
function initPunishDecisionType(){
	$('#isWarn').checkbox({
        label: '警告',
        value: 1,
        width: 15,
        height: 15,
        labelAlign: 'left',
        labelPosition: 'after',
        labelWidth: '110',
    });
	$('#isFine').checkbox({
        label: '罚款',
        value: 1,
        width: 15,
        height: 15,
        labelAlign: 'left',
        labelPosition: 'after',
        labelWidth: '110',
        onChange: function(check){
        	if(check){
                $("#fineSumSpan").show();
                $("#minfineSum").textbox({disabled:false, validType:'decimal'});
                $("#maxfineSum").textbox({disabled:false, validType:'decimal'});
            }else{
                $("#fineSumSpan").hide();
                $("#minfineSum").textbox({disabled:true});
                $("#maxfineSum").textbox({disabled:true});
            }
        }
    });
}

/**
 * 获取条件参数
 * @returns {___anonymous_params}
 */
function setParams(){
    params = tools.formToJson($("#simple_case_search_form"));
    var conditionDiv = '';
    // 获取行政区划
    params.area = $("#area").combobox("getValues").join(",");
    if(params.area.length>0){
        conditionDiv += '<span class="tagbox-label" id="areaTag">'
            + '<span title="行政区划：'+$("#area").combobox("getText")+'">行政区划</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'area\',\'combobox\')" class="tagbox-remove"></a></span>';
    }
    // 获取执法机关
    params.departmentId = $("#departmentId").combobox("getValues").join(",");
    if(params.departmentId.length>0){
        conditionDiv += '<span class="tagbox-label" id="departmentIdTag">'
            + '<span title="执法机关：'+$("#departmentId").combobox("getText")+'">执法机关</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'departmentId\',\'combobox\')" class="tagbox-remove"></a></span>';
    }
    // 获取所属领域
    params.orgSys = $("#orgSys").combobox("getValues").join(",");
    if(params.orgSys.length>0){
        conditionDiv += '<span class="tagbox-label" id="orgSysTag">'
            + '<span title="所属领域：'+$("#orgSys").combobox("getText")+'">所属领域</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'orgSys\',\'combobox\')" class="tagbox-remove"></a></span>';
    }
    // 获取执法主体
    params.subjectId = $("#subjectId").combobox("getValues").join(",");
    if(params.subjectId.length>0){
        conditionDiv += '<span class="tagbox-label" id="subjectIdTag">'
            + '<span title="执法主体：'+$("#subjectId").combobox("getText")+'">执法主体</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'subjectId\',\'combobox\')" class="tagbox-remove"></a></span>';
    }
    // 获取当事人类型
    var partyType = '';
    var partyType0 = '';
    if($("#simple_case_search_form input[name='partyType']:checked").length >0){
        $("#simple_case_search_form input[name='partyType']:checked").each(function(){
            partyType += this.value + ',';
            for(var i=0;i<partyTypeJsons.length;i++){
            	if(this.value==partyTypeJsons[i].codeNo){
            		partyType0 += $.trim(partyTypeJsons[i].codeName) + '，';
            		break;
            	}
            }
        })
    }
    if(partyType.length>0){
        partyType = partyType.substring(0,partyType.length-1);
        conditionDiv += '<span class="tagbox-label" id="partyTypeTag">'
                     + '<span title="当事人类型：'+partyType0.substring(0,partyType0.length-1)+'">当事人类型</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'partyType\',\'checkbox\',\''+ partyType +'\')" class="tagbox-remove"></a></span>';
    }
    params.partyType = partyType;
    
    // 获取当事人名称
    if(params.partyName.length>0){
        conditionDiv += '<span class="tagbox-label" id="partyNameTag">'
                     + '<span title="当事人名称：'+params.partyName+'">当事人名称</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'partyName\',\'textbox\')" class="tagbox-remove"></a></span>';
    }
    
    // 获取当事人证件类型
    var cardType = '';
    var cardType0 = '';
    if($("#simple_case_search_form input[name='cardType']:checked").length >0){
        $("#simple_case_search_form input[name='cardType']:checked").each(function(){
            cardType += this.value + ',';
            for(var i=0;i<cardTypeJsons.length;i++){
            	if(this.value==cardTypeJsons[i].codeNo){
            		cardType0 += $.trim(cardTypeJsons[i].codeName) + '，';
            		break;
            	}
            }
        })
    }
    if(cardType.length>0){
        cardType = cardType.substring(0,cardType.length-1);
        conditionDiv += '<span class="tagbox-label" id="cardTypeTag">'
                     + '<span title="当事人证件类型：'+cardType0.substring(0,cardType0.length-1)+'">当事人证件类型</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'cardType\',\'checkbox\',\''+ cardType +'\')" class="tagbox-remove"></a></span>';
    }
    params.cardType = cardType;
    
    // 获取执法人员
    if(params.officeName.length>0){
        conditionDiv += '<span class="tagbox-label" id="officeNameTag">'
                     + '<span title="执法人员：'+params.officeName+'">执法人员</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'officeName\',\'textbox\')" class="tagbox-remove"></a></span>';
    }
    // 获取执法证号
    if(params.officeCode.length>0){
        conditionDiv += '<span class="tagbox-label" id="officeCodeTag">'
                     + '<span title="执法证号：'+params.officeCode+'">执法证号</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'officeCode\',\'textbox\')" class="tagbox-remove"></a></span>';
    }
    // 获取决定书文号
    if(params.punishmentCode.length>0){
        conditionDiv += '<span class="tagbox-label" id="punishmentCodeTag">'
                     + '<span title="决定书文号：'+params.punishmentCode+'">决定书文号</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'punishmentCode\',\'textbox\')" class="tagbox-remove"></a></span>';
    }
    
    // 获取处罚决定日期
    if(params.beginpunishmentDateStr.length>0 && params.endpunishmentDateStr.length>0){
        conditionDiv += '<span class="tagbox-label" id="punishmentDateStrTag">'
            + '<span title="处罚决定日期：'+params.beginpunishmentDateStr+' - '+params.endpunishmentDateStr+'">处罚决定日期</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'punishmentDateStr\',\'datebox\')" class="tagbox-remove"></a></span>';
    }else if(params.beginpunishmentDateStr.length>0){
        conditionDiv += '<span class="tagbox-label" id="punishmentDateStrTag">'
                     + '<span title="处罚决定日期>'+params.beginpunishmentDateStr+'">处罚决定日期</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'punishmentDateStr\',\'datebox\')" class="tagbox-remove"></a></span>';
    }else if(params.endpunishmentDateStr.length>0){
        conditionDiv += '<span class="tagbox-label" id="punishmentDateStrTag">'
            + '<span title="处罚决定日期<'+params.endpunishmentDateStr+'">处罚决定日期</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'punishmentDateStr\',\'datebox\')" class="tagbox-remove"></a></span>';
    }
    
    // 获取处罚决定日期
    if(params.beginpunishDecisionExecutDateStr.length>0 && params.endpunishDecisionExecutDateStr.length>0){
        conditionDiv += '<span class="tagbox-label" id="punishDecisionExecutDateStrTag">'
            + '<span title="处罚决定日期：'+params.beginpunishDecisionExecutDateStr+' - '+params.endpunishDecisionExecutDateStr+'">处罚决定日期</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'punishDecisionExecutDateStr\',\'datebox\')" class="tagbox-remove"></a></span>';
    }else if(params.beginpunishDecisionExecutDateStr.length>0){
        conditionDiv += '<span class="tagbox-label" id="punishDecisionExecutDateStrTag">'
                     + '<span title="处罚决定日期>'+params.beginpunishDecisionExecutDateStr+'">处罚决定日期</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'punishDecisionExecutDateStr\',\'datebox\')" class="tagbox-remove"></a></span>';
    }else if(params.endpunishDecisionExecutDateStr.length>0){
        conditionDiv += '<span class="tagbox-label" id="punishDecisionExecutDateStrTag">'
            + '<span title="处罚决定日期<'+params.endpunishDecisionExecutDateStr+'">处罚决定日期</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'punishDecisionExecutDateStr\',\'datebox\')" class="tagbox-remove"></a></span>';
    }
    // 获取处罚决定种类
    if(params.isWarn == 1 && params.isFine == 1){
    	conditionDiv += '<span class="tagbox-label" id="isWarnTag">'
            + '<span title="处罚决定种类：警告，罚款">处罚决定种类</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'is\',\'checkbox\',\'Warn,Fine\')" class="tagbox-remove"></a></span>';
    }else if(params.isWarn == 1){
    	conditionDiv += '<span class="tagbox-label" id="isWarnTag">'
            + '<span title="处罚决定种类：警告">处罚决定种类</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'is\',\'checkbox\',\'Warn\')" class="tagbox-remove"></a></span>';
    	params.isFine = '';
    }else if(params.isFine == 1){
    	conditionDiv += '<span class="tagbox-label" id="isWarnTag">'
            + '<span title="处罚决定种类：罚款">处罚决定种类</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'is\',\'checkbox\',\'Fine\')" class="tagbox-remove"></a></span>';
    	params.isWarn = '';
    }else{
    	params.isWarn = '';
    	params.isFine = '';
    }
    // 获取罚款金额
    if(params.minfineSum !=null && params.minfineSum > 0 
    		&& params.maxfineSum !=null && params.maxfineSum > 0 ){
        conditionDiv += '<span class="tagbox-label" id="fineSumTag">'
            + '<span title="罚款金额：'+params.minfineSum+' - '+params.maxfineSum+'">罚款金额</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'fineSum\',\'textbox\')" class="tagbox-remove"></a></span>';
    }else if(params.minfineSum !=null && params.minfineSum > 0){
        conditionDiv += '<span class="tagbox-label" id="fineSumTag">'
                     + '<span title="罚款金额>'+params.minfineSum+'">罚款金额</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'fineSum\',\'textbox\')" class="tagbox-remove"></a></span>';
    }else if(params.maxfineSum !=null && params.maxfineSum > 0){
        conditionDiv += '<span class="tagbox-label" id="fineSumTag">'
            + '<span title="罚款金额<'+params.maxfineSum+'">罚款金额</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'fineSum\',\'textbox\')" class="tagbox-remove"></a></span>';
    }
    
    $("#conditionDiv").html(conditionDiv);
    
    return params;
}

/**
 * 进入综合查询界面
 * @returns
 */
function caseSimpleSearch(){
    params = setParams();
    $("#simpleCaseSearchDiv").hide();
    $("#simpleCaseDiv").show();
    initIndexDatagrid(params);
}

/**
 * 重置查询条件
 */
function caseSimpleRefresh(){
	$('#area').combobox('setValue', '');
    $('#departmentId').combobox('setValue', '');
    $('#orgSys').combobox('setValue', '');
    $('#subjectId').combobox('setValue', '');
    $("#simple_case_search_form input[name='partyType']:checked").each(function(){
        $('#partyType' + this.value).checkbox({checked: false});
    });
    $('#partyName').textbox('setValue', '');
    $("#simple_case_search_form input[name='cardType']:checked").each(function(){
        $('#cardType' + this.value).checkbox({checked: false});
    });
    $('#officeName').textbox('setValue', '');
    $('#officeCode').textbox('setValue', '');
    $('#punishmentCode').textbox('setValue', '');
    
    $('#beginpunishmentDateStr').datebox({validType:''});
    $('#endpunishmentDateStr').datebox({validType:''});
    $('#beginpunishmentDateStr').datebox('setValue', '');
    $('#endpunishmentDateStr').datebox('setValue', '');
    $('#beginpunishmentDateStr').datebox({validType:'date'});
    $('#endpunishmentDateStr').datebox({validType:'date'});
    
    $('#beginpunishDecisionExecutDateStr').datebox({validType:''});
    $('#endpunishDecisionExecutDateStr').datebox({validType:''});
    $('#beginpunishDecisionExecutDateStr').datebox('setValue', '');
    $('#endpunishDecisionExecutDateStr').datebox('setValue', '');
    $('#beginpunishDecisionExecutDateStr').datebox({validType:'date'});
    $('#endpunishDecisionExecutDateStr').datebox({validType:'date'});
    
    $('#isWarn').checkbox({checked: false});
    $('#isFine').checkbox({checked: false});
    
    $('#minfineSum').textbox('setValue', '');
    $('#maxfineSum').textbox('setValue', '');
}

/**
 * 返回条件页
 */
function back(){
    $("#simpleCaseSearchDiv").show();
    $("#simpleCaseDiv").hide();
}

/**
 * 删除单个条件
 * @param thisTag ID
 * @param thisType 输入框类型
 * @param thisVal 多选框value
 */
function thisRemove(thisTag, thisType, thisVal){
    //$("#"+thisTag+"Tag").hide();
	if(thisType == 'textbox' && thisTag == 'fineSum'){
    	$("#min"+thisTag).textbox("setValue","");
    	$("#max"+thisTag).textbox("setValue","");
    }else if(thisType == 'textbox'){
        $("#"+thisTag).textbox("setValue","");
    }else if(thisType == 'combobox'){
        $("#"+thisTag).combobox("setValue","");
    }else if(thisType == 'checkbox'){
        var val = thisVal.split(",");
        for(var i=0 ; i < val.length; i++){
            $("#"+thisTag+val[i]).checkbox({checked: false});
        }
    }else if(thisType == 'datebox'){
        $("#begin"+thisTag).datebox("setValue","");
        $("#end"+thisTag).datebox("setValue","");
    }
    params = setParams();
    initIndexDatagrid(params);
}

/**
 * 固定列
 */
var frozenColumn = [[
    {field: 'id', checkbox: true, title: "ID", width: '40px', halign: 'center', align: 'center'},
    {field:'ID',title:'序号',align:'center',
        formatter:function(value,rowData,rowIndex){
            return rowIndex+1;
        }
    },
    {field: 'filingDateStr', title: '立案批准日期', width: '140px', halign: 'center', align: 'center'},
    {field: 'punishmentDateStr', title: '处罚决定日期', width: '140px', halign: 'center', align: 'center'},
    {field: 'punishmentCode', title: '处罚决定书文号', width: '180px', halign: 'center', align: 'left',
        formatter: function(e, rowData) {
            var punishmentCode = rowData.punishmentCode;
            if(punishmentCode == null || punishmentCode == 'null') {
            	punishmentCode = "";
            }
            var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+punishmentCode+"'><a onclick='SimpleCaseLook(\"" + rowData.id + "\")' href='javaScript:void(0);'>" + punishmentCode + "</a></lable>"
            return lins;
        }
    }
]];

/**
 * 活动列
 */
var column = [[

	{field: 'punishDecisionTypeValue', title: '处罚决定种类', width: '160px', halign: 'center', align: 'left', hidden: true},
	{
	    field: 'name', title: '案由（事由）', width: '200px', halign: 'center', align: 'left', hidden: true,
	    formatter: function(e, rowData) {
	        var name = rowData.name
	        if(name == null || name == 'null') {
	            name = "";
	        }
	        var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+name+"'>" + name + "</lable>"
	        return lins;
	    }
	},
	{field: 'partyTypeValue', title: '当事人类型', width: '100px', halign: 'center', align: 'center', hidden: true},
	{field: 'partyName', title: '当事人名称', width: '100px', halign: 'center', align: 'center', hidden: true},
	
    {field: 'cardTypeValue', title: '当事人证件类型', width: '140px', halign: 'center', align: 'center', hidden: true},
    {field: 'cardCode', title: '证件号码', width: '180px', halign: 'center', align: 'center', hidden: true},
    {field: 'subjectName', title: '执法主体', width: '180px', halign: 'center', align: 'left', hidden: true,
        formatter: function(e, rowData) {
            var subjectName = rowData.subjectName;
            if(subjectName == null || subjectName == 'null') {
                subjectName = "";
            }
            var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+subjectName+"'>"+subjectName+"</lable>"
            return lins;
        }
    },
    {field: 'punishDecisionExecutDateStr', title: '处罚决定执行日期', width: '140px', halign: 'center', align: 'center', hidden: true},
    {field: 'closedDateStr', title: '结案日期', width: '140px', halign: 'center', align: 'center', hidden: true},
    {field: 'dataSourceValue', title: '数据来源', width: '140px', halign: 'center', align: 'center', hidden: true},
    {field: 'createTimeStr', title: '入库日期', width: '140px', halign: 'center', align: 'center', hidden: true}
]];

/**
 * 表格加载函数
 * @returns
 */
function initIndexDatagrid(params){
    datagrid = $('#simple_case_index_datagrid').datagrid({
        url: contextPath + '/caseSimpleBaseSearchCtrl/findListByPageSearch.action',
        queryParams: params,
        pagination: true,
        pageSize : 20,
        pageList : [ 10, 20, 50, 100 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar: '#toolbar', // 工具条对象
        checkbox: true,
        border: false,
        striped: true,//隔行变色
        /* idField:'formId',//主键列 */
        fitColumns: true, // 列是否进行自动宽度适应
        fit: true,
        singleSelect: false, //为true只能选择一行
        nowrap: true,
        onLoadSuccess: function(data) {
            if(temp.length>0){
                for(var i = 0; i < temp.length; i++){
                    $('#simple_case_index_datagrid').datagrid('showColumn', temp[i]);
                    if(!$('#'+temp[i]).children('i').hasClass("fa-check")){
                        $('#'+temp[i]).children('i').addClass("fa-check");
                    }
                }
            }
        },
        frozenColumns: frozenColumn,
        columns: column
    });
}

/**
 * 查看
 * @returns
 */
function SimpleCaseLook(id){
    top.bsWindow(contextPath + "/supervise/caseManager/simpleCase/case_simple_look.jsp?id=" + id + "&editFlag=4", "查看", {
        width : "960",
        height : "480",
        buttons : [{
            name : "关闭",
            classStyle : "btn-alert-gray"
        } ],
        submit : function(v, h) {
            if (v == "关闭") {
                return true;
            }
        }
    });
}

/**
 * 导出excel
 */
function exportSimpleCase(){
    top.$.MsgBox.Confirm("提示","确定导出所有数据？",function(){
        var json = tools.requestJsonRs("/caseSimpleBaseSearchCtrl/exportCaseSimpleBase.action?columns=" + temp);
        if(json == null || json.rtState){
            $.MsgBox.Alert_auto("导出成功！");
            location.href = "/caseSimpleBaseSearchCtrl/exportCaseSimpleBase.action?columns=" + temp;
        } else{
            $.MsgBox.Alert_auto(json.rtMsg);
        }
    })
}