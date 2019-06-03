var params;
var partyTypeJsons = [];
var permissionTypeJsons = [];
var powerLevelJsons = [];// 职权层级

var permissionPowerTypeJsons = [];
var punishPowerTypeJsons = [];
var coercionPowerTypeJsons = [];
var lawTypeJsons = [];
var isTrueJson = [
	{codeNo:'1', codeName: '是'},
	{codeNo:'0', codeName: '否'}
];

var caseNumJson = [
	{codeNo:'0', codeName: '未触发'},
	{codeNo:'1', codeName: '1个'},
	{codeNo:'2', codeName: '2个及以上'},
	{codeNo:'9', codeName: '自定义'}
];

/**
 * 可选列
 */
var listdata = {
    isShowColumn: [
        {codeNo:'powerType', codeName: '职权类型'},
        {codeNo:'powerDetail', codeName: '职权分类'},
        {codeNo:'powerLevel', codeName: '职权层级'},
        {codeNo:'powerMold', codeName: '职权领域'},
        {codeNo:'subjectName', codeName: '管理主体'},
        {codeNo:'createDateStr', codeName: '创建日期'}
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
var temp = ['powerType', 'powerDetail', 'powerLevel', 'powerMold', 'subjectName'];

/**
 * 可选列选中/取消
 * @param code
 * @param th
 */
function detail(code,th){
    if($(th).children('i').hasClass("fa-check")){
        $(th).children('i').removeClass("fa-check");
        $('#power_search_datagrid').datagrid('hideColumn', code);
        for(var i=0;i<temp.length;i++){
            if(temp[i] == code){
                temp.splice(i,1);
                break;
            }
        }
    } else{
        $(th).children('i').addClass("fa-check");
        $('#power_search_datagrid').datagrid('showColumn', code);
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
    $("#powerListDiv").hide();
    dateValidate('begincreateDateStr', 'endcreateDateStr');
    //initPowerLevel();// 初始化职权层级
    //areaInit(); // 加载行政区划
    //powerMoldInit();// 初始化职权领域
    initSubjectId();// 初始化管理主体
    //initPowerSubjectId();// 初始化实施主体
    initJsonListCheckBox(isTrueJson,'isCriminal');// 是否涉刑
    initPowerType('02','permissionPowerType');//初始化许可职权分类
    initPowerType('01','punishPowerType');//初始化处罚职权分类
    initPowerType('03','coercionPowerType');//初始化强制职权分类
    //initLawType();// 初始化法律法规类别
    //initDepartment();// 初始化发布机关
//    initCaseNum();
//    initCaseNumBefore();

    // 初始化可选列
    $(".panList").append(juicer(tpl,listdata));
    // 初始化表格
    datagrid = $('#power_search_datagrid') .datagrid( {
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar : '#toolbar'// 工具条对象
    });
}

/**
 * 初始化初始化发布机关
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
 * 初始化职权层级
 */
function initPowerLevel(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: 'POWER_LEVEL'});
    if(json.rtState) {
        var page = "";
        var powerLevelJson;
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelWidth="90px;" labelPosition="after" type="checkbox" name="powerLevel" id="powerLevel'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'  "/>';
            powerLevelJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
            powerLevelJsons.push(powerLevelJson);
        }
        var pageDoc = $('#powerLevel_td').html(page);
        $.parser.parse(pageDoc);
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
 * 初始化职权领域
 */
function powerMoldInit(){
    var json = tools.requestJsonRs("/commonCtrl/getOrgSys.action");
    if(json.rtState) {
        $('#powerMold').combobox({
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
 * 初始化管理主体
 */
function initSubjectId(){
    //所属主体
    var json = tools.requestJsonRs("/permissionItemCtrl/getSubjectListByOneself.action");
    if(json.rtState) {
    	$('#subjectId').combobox({
            data: json.rtData,
            valueField: 'id',
            textField: 'subName',
            panelHeight: 'auto',
            editable: true,
            prompt : '请选择',
            onLoadSuccess:function(data){
                $(this).combobox('setValue',data[0].id);
            },
            onHidePanel:function(){
            	var _options = $(this).combobox('options');
                var _data = $(this).combobox('getData');/* 下拉框所有选项 */
                var _value = $(this).combobox('getText');/* 用户输入的值 */
                var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
                for (var i = 0; i < _data.length; i++) {
                    if (_data[i][_options.textField] == _value) {
                        _b = true;
                        break;
                    }
                }
                if (!_b) {
                    $(this).combobox('setValue', '');
                }
            }
        });
    }
}

/**
 * 初始化实施主体
 */
function initPowerSubjectId(){
    var json = tools.requestJsonRs("/subjectSearchController/getSubjectRoles.action");
    if(json.rtState) {
        $('#powerSubjectId').combobox({
            data: json.rtData,
            valueField: 'id',
            textField: 'subName',
            panelHeight: 'auto',
            panelMaxHeight : 150,
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
 * 初始化给定json复选框
 * @param json
 * @param id
 * @param value
 */
function initJsonListCheckBox(json,id,value) {
	var page = "";
    for(var i=0 ; i < json.length; i++){
        page = page + '<input style="width: 15px; height: 15px;" labelWidth="90px" labelAlign="left" labelPosition="after" type="checkbox"' 
        	+' name="'+id+'" id="'+id+json[i].codeNo+'" class="easyui-checkbox" '
        	+ 'value="'+json[i].codeNo+'" label="'+json[i].codeName+'"/>';
    }
    var pageDoc = $('#'+id+'_td').html(page);
	$.parser.parse(pageDoc);
}

/**
 * 初始化职权类型
 */
function initPowerType(type,id){
	var params = {
            parentCodeNo: "POWER_DETAIL",
            codeNo: type
        };
	var json = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action", params);
    if(json.rtState) {
        var page = "";
        var powerTypeJson;
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<div style="float:left;padding:7px 0;" title="'+json.rtData[i].codeName+'">'
            	+ '<input style="width: 15px; height: 15px;" labelAlign="left" labelWidth="150px;" labelPosition="after" type="checkbox" name="'+id+'" id="'+id+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'" /></div>';
            powerTypeJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
            if(id=='permissionPowerType'){
            	permissionPowerTypeJsons.push(powerTypeJson);
            }else if(id=='punishPowerType'){
            	punishPowerTypeJsons.push(powerTypeJson);
            }else if(id=='coercionPowerType'){
            	coercionPowerTypeJsons.push(powerTypeJson);
            }
        }
        var pageDoc = $('#' + id + "_td").html(page);
        $.parser.parse(pageDoc);
    }
}

/**
 * 初始化法律法规类别
 */
function initLawType(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: 'LAW_TYPE'});
    if(json.rtState) {
        var page = "";
        var lawTypeJson;
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelWidth="90px;" labelPosition="after" type="checkbox" name="lawType" id="lawType'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'  "/>';
            lawTypeJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
            lawTypeJsons.push(lawTypeJson);
        }
        var pageDoc = $('#lawType_td').html(page);
        $.parser.parse(pageDoc);
    }
}

/**
 * 初始化本年度案件数量
 */
function initCaseNum(){
	initJsonListCheckBox(caseNumJson,'caseNum');
	$('#caseNum9').checkbox({
        onChange: function(check){
        	if(check){
                $("#caseNumAuto_span").show();
                $("#mincaseNum").textbox({disabled:false, validType:'number'});
                $("#maxcaseNum").textbox({disabled:false, validType:'number'});
            }else{
                $("#caseNumAuto_span").hide();
                $("#mincaseNum").textbox({disabled:true});
                $("#maxcaseNum").textbox({disabled:true});
            }
        }
    });
}

/**
 * 初始化上年度案件数量
 */
function initCaseNumBefore(){
	initJsonListCheckBox(caseNumJson,'caseNumBefore');
	$('#caseNumBefore9').checkbox({
        onChange: function(check){
        	if(check){
                $("#caseNumAutoBefore_span").show();
                $("#mincaseNumBefore").textbox({disabled:false, validType:'number'});
                $("#maxcaseNumBefore").textbox({disabled:false, validType:'number'});
            }else{
                $("#caseNumAutoBefore_span").hide();
                $("#mincaseNumBefore").textbox({disabled:true});
                $("#maxcaseNumBefore").textbox({disabled:true});
            }
        }
    });
}

/**
 * 获取条件参数
 * @returns {___anonymous_params}
 */
function setParams(){
    params = tools.formToJson($("#power_search_form"));
    var conditionDiv = '';
    // 获取职权名称
    if(params.name.length>0){
        conditionDiv += '<span class="tagbox-label" id="nameTag">'
                     + '<span title="职权名称：'+params.name+'">职权名称</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'name\',\'textbox\')" class="tagbox-remove"></a></span>';
    }
    // 获取执法主体
    params.subjectId = $("#subjectId").combobox("getValues").join(",");
    if(params.subjectId.length>0){
        conditionDiv += '<span class="tagbox-label" id="subjectIdTag">'
            + '<span title="执法主体：'+$("#subjectId").combobox("getText")+'">执法主体</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'subjectId\',\'combobox\')" class="tagbox-remove"></a></span>';
    }
    // 获取是否涉刑
    var isCriminal = '';
    var isCriminal0 = '';
    if($("#power_search_form input[name='isCriminal']:checked").length >0){
        $("#power_search_form input[name='isCriminal']:checked").each(function(){
            isCriminal += this.value + ',';
            if(this.value==1){
            	isCriminal0 += '是，';
            }else if(this.value==0){
            	isCriminal0 += '否，';
            }
        })
    }
    if(isCriminal.length>0){
        isCriminal = isCriminal.substring(0,isCriminal.length-1);
        conditionDiv += '<span class="tagbox-label" id="isCriminalTag">'
                     + '<span title="是否涉刑：'+isCriminal0.substring(0,isCriminal0.length-1)+'">是否涉刑</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'isCriminal\',\'checkbox\',\''+ isCriminal +'\')" class="tagbox-remove"></a></span>';
    }
    params.isCriminalStr = isCriminal;
    // 获取入库日期
    if(params.begincreateDateStr.length>0 && params.endcreateDateStr.length>0){
        conditionDiv += '<span class="tagbox-label" id="createDateStrTag">'
            + '<span title="入库日期：'+params.begincreateDateStr+' - '+params.endcreateDateStr+'">入库日期</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'createDateStr\',\'datebox\')" class="tagbox-remove"></a></span>';
    }else if(params.begincreateDateStr.length>0){
        conditionDiv += '<span class="tagbox-label" id="createDateStrTag">'
                     + '<span title="入库日期>'+params.begincreateDateStr+'">入库日期</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'createDateStr\',\'datebox\')" class="tagbox-remove"></a></span>';
    }else if(params.endcreateDateStr.length>0){
        conditionDiv += '<span class="tagbox-label" id="createDateStrTag">'
            + '<span title="入库日期<'+params.endcreateDateStr+'">入库日期</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'createDateStr\',\'datebox\')" class="tagbox-remove"></a></span>';
    }
    
    // 职权分类
    var powerDetail = '';
    
    // 获取许可职权类型
    var permissionPowerType = '';
    var permissionPowerType0 = '';
    if($("#power_search_form input[name='permissionPowerType']:checked").length >0){
        $("#power_search_form input[name='permissionPowerType']:checked").each(function(){
            permissionPowerType += this.value + ',';
            powerDetail += this.value + ',';
            for(var i=0;i<permissionPowerTypeJsons.length;i++){
            	if(this.value==permissionPowerTypeJsons[i].codeNo){
            		permissionPowerType0 += $.trim(permissionPowerTypeJsons[i].codeName) + '，';
            		break;
            	}
            }
        })
    }
    if(permissionPowerType.length>0){
        permissionPowerType = permissionPowerType.substring(0,permissionPowerType.length-1);
        conditionDiv += '<span class="tagbox-label" id="permissionPowerTypeTag">'
                     + '<span title="许可职权类型：'+permissionPowerType0.substring(0,permissionPowerType0.length-1)+'">许可职权类型</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'permissionPowerType\',\'checkbox\',\''+ permissionPowerType +'\')" class="tagbox-remove"></a></span>';
    }
    
    // 获取处罚职权类型
    var punishPowerType = '';
    var punishPowerType0 = '';
    if($("#power_search_form input[name='punishPowerType']:checked").length >0){
        $("#power_search_form input[name='punishPowerType']:checked").each(function(){
            punishPowerType += this.value + ',';
            powerDetail += this.value + ',';
            for(var i=0;i<punishPowerTypeJsons.length;i++){
            	if(this.value==punishPowerTypeJsons[i].codeNo){
            		punishPowerType0 += $.trim(punishPowerTypeJsons[i].codeName) + '，';
            		break;
            	}
            }
        })
    }
    if(punishPowerType.length>0){
        punishPowerType = punishPowerType.substring(0,punishPowerType.length-1);
        conditionDiv += '<span class="tagbox-label" id="punishPowerTypeTag">'
                     + '<span title="处罚职权类型：'+punishPowerType0.substring(0,punishPowerType0.length-1)+'">处罚职权类型</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'punishPowerType\',\'checkbox\',\''+ punishPowerType +'\')" class="tagbox-remove"></a></span>';
    }
    
    // 获取强制职权类型
    var coercionPowerType = '';
    var coercionPowerType0 = '';
    if($("#power_search_form input[name='coercionPowerType']:checked").length >0){
        $("#power_search_form input[name='coercionPowerType']:checked").each(function(){
            coercionPowerType += this.value + ',';
            powerDetail += this.value + ',';
            for(var i=0;i<coercionPowerTypeJsons.length;i++){
            	if(this.value==coercionPowerTypeJsons[i].codeNo){
            		coercionPowerType0 += $.trim(coercionPowerTypeJsons[i].codeName) + '，';
            		break;
            	}
            }
        })
    }
    if(coercionPowerType.length>0){
        coercionPowerType = coercionPowerType.substring(0,coercionPowerType.length-1);
        conditionDiv += '<span class="tagbox-label" id="coercionPowerTypeTag">'
                     + '<span title="强制职权类型：'+coercionPowerType0.substring(0,coercionPowerType0.length-1)+'">强制职权类型</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'coercionPowerType\',\'checkbox\',\''+ coercionPowerType +'\')" class="tagbox-remove"></a></span>';
    }
    
    if(powerDetail.length>0){
    	powerDetail = powerDetail.substring(0,powerDetail.length-1);
    }
    params.powerDetail = powerDetail;
    
    $("#conditionDiv").html(conditionDiv);
    
    return params;
}

/**
 * 进入综合查询界面
 * @returns
 */
function powerSearch(){
    params = setParams();
    $("#powerSearchDiv").hide();
    $("#powerListDiv").show();
    initIndexDatagrid(params);
}

/**
 * 重置查询条件
 */
function powerRefresh(){
	$('#name').textbox('setValue', '');
	$('#subjectId').combobox('setValue', '');
	$("#power_search_form input[name='isCriminal']:checked").each(function(){
        $('#isCriminal' + this.value).checkbox({checked: false});
    });
	
	$('#begincreateDateStr').datebox({validType:''});
    $('#endcreateDateStr').datebox({validType:''});
    $('#begincreateDateStr').datebox('setValue', '');
    $('#endcreateDateStr').datebox('setValue', '');
    $('#begincreateDateStr').datebox({validType:'date'});
    $('#endcreateDateStr').datebox({validType:'date'});
    
	$("#power_search_form input[name='permissionPowerType']:checked").each(function(){
        $('#permissionPowerType' + this.value).checkbox({checked: false});
    });
	$("#power_search_form input[name='punishPowerType']:checked").each(function(){
        $('#permissionPowerType' + this.value).checkbox({checked: false});
    });
	$("#power_search_form input[name='coercionPowerType']:checked").each(function(){
        $('#permissionPowerType' + this.value).checkbox({checked: false});
    });
	
	$("#power_search_form input[name='caseNum']:checked").each(function(){
        $('#caseNum' + this.value).checkbox({checked: false});
    });
	$('#mincaseNum').textbox('setValue', '');
    $('#maxcaseNum').textbox('setValue', '');
    
    $("#power_search_form input[name='caseNumBefore']:checked").each(function(){
        $('#caseNumBefore' + this.value).checkbox({checked: false});
    });
    $('#mincaseNumBefore').textbox('setValue', '');
    $('#maxcaseNumBefore').textbox('setValue', '');
}

/**
 * 返回条件页
 */
function back(){
    $("#powerSearchDiv").show();
    $("#powerListDiv").hide();
}

/**
 * 删除单个条件
 * @param thisTag ID
 * @param thisType 输入框类型
 * @param thisVal 多选框value
 */
function thisRemove(thisTag, thisType, thisVal){
    //$("#"+thisTag+"Tag").hide();
	if(thisType == 'textbox' && (thisTag == 'caseNum'||thisTag == 'caseNumBefore')){
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
    {field: 'code', title: '职权编号', width: '110px', halign: 'center', align: 'center'},
    {field: 'name', title: '职权名称', width: '400px', halign: 'center', align: 'left',
        formatter: function(e, rowData) {
            var name = rowData.name;
            if(name == null || name == 'null') {
            	name = "";
            }
            var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+name+"'><a onclick='powerListLook(\"" + rowData.id + "\")' href='javaScript:void(0);'>" + name + "</a></lable>"
            return lins;
        }
    }
]];

/**
 * 活动列
 */
var column = [[
    {field: 'powerType', title: '职权类型 ', width: '80px', halign : 'center', align: 'center', hidden: true},
    {field: 'powerDetail', title: '职权分类', width: '200px', halign : 'center', align: 'left', hidden: true},
    {field: 'powerLevel', title: '职权层级', width: '80px', halign: 'center', align: 'center', hidden: true},
    {field: 'powerMold', title: '职权领域', width: '80px', halign: 'center', align: 'center', hidden: true},
    {field: 'subjectName', title: '管理主体', width: '180px', halign: 'center', align: 'left', hidden: true},
    {field: 'createDateStr', title: '创建日期', width: '140px', halign: 'center', align: 'center', hidden: true}
]];

/**
 * 表格加载函数
 * @returns
 */
function initIndexDatagrid(params){
    datagrid = $('#power_search_datagrid').datagrid({
        url: contextPath + '/powerSearchCtrl/listByPage.action',
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
                    $('#power_search_datagrid').datagrid('showColumn', temp[i]);
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
function powerListLook(id){
	var url=contextPath+"/powerCtrl/showById.action?id=" + id;
    top.bsWindow(url ,"职权查看",{width:"900",height:"400",buttons:
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
 * 导出excel
 */
function exportPowerList(){
    top.$.MsgBox.Confirm("提示","确定导出所有数据？",function(){
        var json = tools.requestJsonRs("/powerSearchCtrl/exportPower.action?columns=" + temp);
        if(json == null || json.rtState){
            $.MsgBox.Alert_auto("导出成功！");
            location.href = "/powerSearchCtrl/exportPower.action?columns=" + temp;
        } else{
            $.MsgBox.Alert_auto(json.rtMsg);
        }
    })
}