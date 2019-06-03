var params;
var handleWayJsons = [];
var listTypeJsons = [];
var actSubject;
var isTrueJson = [
	{codeNo:'1', codeName: '是'},
	{codeNo:'0', codeName: '否'}
];

var caseNumJson = [
	{codeNo:'0', codeName: '未触发'},
	{codeNo:'1', codeName: '1-10'},
	{codeNo:'2', codeName: '11-100'},
	{codeNo:'3', codeName: '>100'},
	{codeNo:'9', codeName: '自定义'}
];
/**
 * 可选列
 */
var listdata = {
    isShowColumn: [
        {codeNo:'listTypeValue', codeName: '办件类型'},
        {codeNo:'subjectName', codeName: '许可主体'},
        {codeNo:'statutoryTimeLimit', codeName: '法定时限'},
        {codeNo:'promisedTimeLimit', codeName: '承诺时限'},
        {codeNo:'partyTypeValue', codeName: '服务对象'},
        {codeNo:'handleWayValue', codeName: '办理形式'},
        {codeNo:'isCollectFeesStr', codeName: '是否收费'}
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
var temp = ['listTypeValue','subjectName','statutoryTimeLimit','promisedTimeLimit'];

/**
 * 可选列选中/取消
 * @param code
 * @param th
 */
function detail(code,th){
    if($(th).children('i').hasClass("fa-check")){
        $(th).children('i').removeClass("fa-check");
        $('#permission_item_index_datagrid').datagrid('hideColumn', code);
        for(var i=0;i<temp.length;i++){
            if(temp[i] == code){
                temp.splice(i,1);
                break;
            }
        }
    } else{
        $(th).children('i').addClass("fa-check");
        $('#permission_item_index_datagrid').datagrid('showColumn', code);
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
    $("#permissionItemDiv").hide();
    // 获取本单位对应主体
	var json = tools.requestJsonRs("/permissionItemCtrl/getActSubject.action");
    if(json.rtState){
        actSubject = json.rtData;
    }
    initPowerId();// 初始化许可职权
    initsubjectId();// 初始化许可主体
    areaInit();// 初始化行政区划
    initListType();// 初始化办件类型
    initHandleWay();// 初始化办理形式
    initJsonListCheckBox(isTrueJson,'isCollectFees');// 是否收费
    initCaseNum();
    initCaseNumBefore();
    // 初始化可选列
    $(".panList").append(juicer(tpl,listdata));
    // 初始化表格
    datagrid = $('#permission_item_index_datagrid') .datagrid( {
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar : '#toolbar'// 工具条对象
    });
}

/**
 * 初始化许可职权
 */
function initPowerId(){
	var params = {
         actSubject: actSubject,
         powerType: '02'
    };
	var json = tools.requestJsonRs("/permissionItemCtrl/getPowerByActSubject.action",params);
	var isEdit = false;
	if(json.total != null && json.total > 18){
		isEdit = true;
	}
	if(json.rows != null && json.rows.length > 0){
		$('#powerId').combobox({
	        data: json.rows,
	        valueField: 'id',
	        textField: 'name',
	        panelHeight: 'auto',
	        panelMaxHeight : 192,
	        multiple:true,
	        prompt : '请选择',
	        editable: isEdit,
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
 * 初始化办件类型
 * @returns
 */
function initListType() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: 'PERMISSION_LIST_TYPE'});
    if(json.rtState) {
        var page = "";
        var listTypeJson;
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelWidth="80px;" labelPosition="after" type="checkbox" name="listType" id="listType'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'  "/>';
                //+ 'value="'+json.rtData[i].codeNo+'" title="'+json.rtData[i].codeName+'" label="<span title=\'' + json.rtData[i].codeName + '\'>'+json.rtData[i].codeName+'</span>"/>';
            listTypeJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
            listTypeJsons.push(listTypeJson);
        }
        var pageDoc = $('#listType_td').html(page);
        $.parser.parse(pageDoc);
    }
}

/**
 * 初始化办理形式
 * @returns
 */
function initHandleWay() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: 'PERMISSION_HANDLE_WAY'});
    if(json.rtState) {
        var page = "";
        var handleWayJson;
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelWidth="80px;" labelPosition="after" type="checkbox" name="handleWay" id="handleWay'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'  "/>';
                //+ 'value="'+json.rtData[i].codeNo+'" title="'+json.rtData[i].codeName+'" label="<span title=\'' + json.rtData[i].codeName + '\'>'+json.rtData[i].codeName+'</span>"/>';
            handleWayJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
            handleWayJsons.push(handleWayJson);
        }
        var pageDoc = $('#handleWay_td').html(page);
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
 * 初始化给定json复选框
 * @param json
 * @param id
 * @param value
 */
function initJsonListCheckBox(json,id,value) {
	var page = "";
    for(var i=0 ; i < json.length; i++){
        page = page + '<input style="width: 15px; height: 15px;" labelWidth="80px" labelAlign="left" labelPosition="after" type="checkbox"' 
        	+' name="'+id+'" id="'+id+json[i].codeNo+'" class="easyui-checkbox" '
        	+ 'value="'+json[i].codeNo+'" label="'+json[i].codeName+'"/>';
    }
    var pageDoc = $('#'+id+'_td').html(page);
	$.parser.parse(pageDoc);
}

/**
 * 初始化办理主体
 */
function initsubjectId(){
    var json = tools.requestJsonRs("/subjectSearchController/getSubjectRoles.action");
    if(json.rtState) {
        $('#subjectId').combobox({
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
 * 获取条件参数
 * @returns {___anonymous_params}
 */
function setParams(){
    params = tools.formToJson($("#permission_item_search_form"));
    var conditionDiv = '';
    // 获取行政区划
    params.area = $("#area").combobox("getValues").join(",");
    if(params.area.length>0){
        conditionDiv += '<span class="tagbox-label" id="areaTag">'
            + '<span title="行政区划：'+$("#area").combobox("getText")+'">行政区划</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'area\',\'combobox\')" class="tagbox-remove"></a></span>';
    }
    // 获取事项名称
    if(params.name.length>0){
        conditionDiv += '<span class="tagbox-label" id="nameTag">'
                     + '<span title="事项名称：'+params.name+'">事项名称</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'name\',\'textbox\')" class="tagbox-remove"></a></span>';
    }
    // 获取许可职权
    params.powerId = $("#powerId").combobox("getValues").join(",");
    if(params.powerId.length>0){
        conditionDiv += '<span class="tagbox-label" id="powerIdTag">'
            + '<span title="许可职权：'+$("#powerId").combobox("getText")+'">许可职权</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'powerId\',\'combobox\')" class="tagbox-remove"></a></span>';
    }
    // 获取许可主体
    params.subjectId = $("#subjectId").combobox("getValues").join(",");
    if(params.subjectId.length>0){
        conditionDiv += '<span class="tagbox-label" id="subjectIdTag">'
            + '<span title="许可主体：'+$("#subjectId").combobox("getText")+'">许可主体</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'subjectId\',\'combobox\')" class="tagbox-remove"></a></span>';
    }
    // 获取办件类型
    var listType = '';
    var listType0 = '';
    if($("#permission_item_search_form input[name='listType']:checked").length >0){
        $("#permission_item_search_form input[name='listType']:checked").each(function(){
            listType += this.value + ',';
            for(var i=0;i<listTypeJsons.length;i++){
            	if(this.value==listTypeJsons[i].codeNo){
            		listType0 += $.trim(listTypeJsons[i].codeName) + '，';
            		break;
            	}
            }
        })
    }
    if(listType.length>0){
        listType = listType.substring(0,listType.length-1);
        conditionDiv += '<span class="tagbox-label" id="listTypeTag">'
                     + '<span title="办件类型：'+listType0.substring(0,listType0.length-1)+'">办件类型</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'listType\',\'checkbox\',\''+ listType +'\')" class="tagbox-remove"></a></span>';
    }
    params.listType = listType;
    // 获取办理形式
    var handleWay = '';
    var handleWay0 = '';
    if($("#permission_item_search_form input[name='handleWay']:checked").length >0){
        $("#permission_item_search_form input[name='handleWay']:checked").each(function(){
            handleWay += this.value + ',';
            for(var i=0;i<handleWayJsons.length;i++){
            	if(this.value==handleWayJsons[i].codeNo){
            		handleWay0 += $.trim(handleWayJsons[i].codeName) + '，';
            		break;
            	}
            }
        })
    }
    if(handleWay.length>0){
        handleWay = handleWay.substring(0,handleWay.length-1);
        conditionDiv += '<span class="tagbox-label" id="handleWayTag">'
                     + '<span title="办理形式：'+handleWay0.substring(0,handleWay0.length-1)+'">办理形式</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'handleWay\',\'checkbox\',\''+ handleWay +'\')" class="tagbox-remove"></a></span>';
    }
    params.handleWay = handleWay;
    // 获取是否收费
    var isCollectFees = '';
    var isCollectFees0 = '';
    if($("#permission_item_search_form input[name='isCollectFees']:checked").length >0){
        $("#permission_item_search_form input[name='isCollectFees']:checked").each(function(){
            isCollectFees += this.value + ',';
            if(this.value==1){
            	isCollectFees0 += '是，';
            }else if(this.value==0){
            	isCollectFees0 += '否，';
            }
        })
    }
    if(isCollectFees.length>0){
        isCollectFees = isCollectFees.substring(0,isCollectFees.length-1);
        conditionDiv += '<span class="tagbox-label" id="isCollectFeesTag">'
                     + '<span title="是否收费：'+isCollectFees0.substring(0,isCollectFees0.length-1)+'">是否收费</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'isCollectFees\',\'checkbox\',\''+ isCollectFees +'\')" class="tagbox-remove"></a></span>';
    }
    params.isCollectFeesStr = isCollectFees;
    // 获取法定办结时限
    if(params.minstatutoryTimeLimit !=null && params.minstatutoryTimeLimit > 0 
    		&& params.maxstatutoryTimeLimit !=null && params.maxstatutoryTimeLimit > 0 ){
        conditionDiv += '<span class="tagbox-label" id="statutoryTimeLimitTag">'
            + '<span title="法定办结时限：'+params.minstatutoryTimeLimit+' - '+params.maxstatutoryTimeLimit+'个工作日">法定办结时限</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'statutoryTimeLimit\',\'textbox\')" class="tagbox-remove"></a></span>';
    }else if(params.minstatutoryTimeLimit !=null && params.minstatutoryTimeLimit > 0){
        conditionDiv += '<span class="tagbox-label" id="statutoryTimeLimitTag">'
                     + '<span title="法定办结时限>'+params.minstatutoryTimeLimit+'个工作日">法定办结时限</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'statutoryTimeLimit\',\'textbox\')" class="tagbox-remove"></a></span>';
    }else if(params.maxstatutoryTimeLimit !=null && params.maxstatutoryTimeLimit > 0){
        conditionDiv += '<span class="tagbox-label" id="statutoryTimeLimitTag">'
            + '<span title="法定办结时限<'+params.maxstatutoryTimeLimit+'个工作日">法定办结时限</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'statutoryTimeLimit\',\'textbox\')" class="tagbox-remove"></a></span>';
    }
    // 获取承诺办结时限
    if(params.minpromisedTimeLimit !=null && params.minpromisedTimeLimit > 0 
    		&& params.maxpromisedTimeLimit !=null && params.maxpromisedTimeLimit > 0 ){
        conditionDiv += '<span class="tagbox-label" id="promisedTimeLimitTag">'
            + '<span title="承诺办结时限：'+params.minpromisedTimeLimit+' - '+params.maxpromisedTimeLimit+'个工作日">承诺办结时限</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'promisedTimeLimit\',\'textbox\')" class="tagbox-remove"></a></span>';
    }else if(params.minpromisedTimeLimit !=null && params.minpromisedTimeLimit > 0){
        conditionDiv += '<span class="tagbox-label" id="promisedTimeLimitTag">'
                     + '<span title="承诺办结时限>'+params.minpromisedTimeLimit+'个工作日">承诺办结时限</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'promisedTimeLimit\',\'textbox\')" class="tagbox-remove"></a></span>';
    }else if(params.maxpromisedTimeLimit !=null && params.maxpromisedTimeLimit > 0){
        conditionDiv += '<span class="tagbox-label" id="promisedTimeLimitTag">'
            + '<span title="承诺办结时限<'+params.maxpromisedTimeLimit+'个工作日">承诺办结时限</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'promisedTimeLimit\',\'textbox\')" class="tagbox-remove"></a></span>';
    }
    
    $("#conditionDiv").html(conditionDiv);
    
    return params;
}

/**
 * 进入综合查询界面
 * @returns
 */
function permissionSearch(){
    params = setParams();
    $("#permissionSearchDiv").hide();
    $("#permissionItemDiv").show();
    initIndexDatagrid(params);
}

/**
 * 重置查询条件
 */
function permissionRefresh(){
	$('#area').combobox('setValue', '');
	$('#name').textbox('setValue', '');
	$('#powerId').combobox('setValue', '');
	$('#subjectId').combobox('setValue', '');
	$("#permission_item_search_form input[name='listType']:checked").each(function(){
        $('#listType' + this.value).checkbox({checked: false});
    });
	$("#permission_item_search_form input[name='handleWay']:checked").each(function(){
        $('#handleWay' + this.value).checkbox({checked: false});
    });
	$("#permission_item_search_form input[name='isCollectFees']:checked").each(function(){
        $('#isCollectFees' + this.value).checkbox({checked: false});
    });
	$('#minstatutoryTimeLimit').textbox('setValue', '');
    $('#maxstatutoryTimeLimit').textbox('setValue', '');
    $('#minpromisedTimeLimit').textbox('setValue', '');
    $('#maxpromisedTimeLimit').textbox('setValue', '');
    $("#permission_item_search_form input[name='caseNum']:checked").each(function(){
        $('#caseNum' + this.value).checkbox({checked: false});
    });
	$('#mincaseNum').textbox('setValue', '');
    $('#maxcaseNum').textbox('setValue', '');
    
    $("#permission_item_search_form input[name='caseNumBefore']:checked").each(function(){
        $('#caseNumBefore' + this.value).checkbox({checked: false});
    });
    $('#mincaseNumBefore').textbox('setValue', '');
    $('#maxcaseNumBefore').textbox('setValue', '');
}

/**
 * 返回条件页
 */
function back(){
    $("#permissionSearchDiv").show();
    $("#permissionItemDiv").hide();
}

/**
 * 删除单个条件
 * @param thisTag ID
 * @param thisType 输入框类型
 * @param thisVal 多选框value
 */
function thisRemove(thisTag, thisType, thisVal){
    //$("#"+thisTag+"Tag").hide();
	
	if(thisType == 'textbox' && (thisTag == 'statutoryTimeLimit' || thisTag == 'promisedTimeLimit')){
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
        $("#"+thisTag).datebox("setValue","");
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
    {field: 'name', title: '事项名称', width: '250px', halign: 'center', align: 'left',
        formatter: function(e, rowData) {
            var name = rowData.name;
            if(name == null || name == 'null') {
                name = "";
            }
            var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+name+"'><a onclick='permissionItemLook(\"" + rowData.id + "\")' href='javaScript:void(0);'>" + name + "</a></lable>"
            return lins;
        }
    }
]];

/**
 * 活动列
 */
var column = [[
	{field: 'listTypeValue', title: '办件类型', width: '120px', halign: 'center', align: 'center', hidden: true},
	{field: 'subjectName', title: '许可主体', width: '180px', halign: 'center', align: 'left', hidden: true},
	{field: 'statutoryTimeLimit', title: '法定时限', width: '120px', halign: 'center', align: 'center', hidden: true},
    {field: 'promisedTimeLimit', title: '承诺时限', width: '120px', halign: 'center', align: 'center', hidden: true},
    {field: 'partyTypeValue', title: '服务对象', width: '120px', halign: 'center', align: 'center', hidden: true},
    {field: 'handleWayValue', title: '办理形式', width: '140px', halign: 'center', align: 'center', hidden: true},
    {field: 'isCollectFeesStr', title: '是否收费', width: '120px', halign: 'center', align: 'center', hidden: true}
]];

/**
 * 表格加载函数
 * @returns
 */
function initIndexDatagrid(params){
    datagrid = $('#permission_item_index_datagrid').datagrid({
        url: contextPath + '/permissionItemSearchCtrl/findListByPageSearch.action',
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
                    $('#permission_item_index_datagrid').datagrid('showColumn', temp[i]);
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
function permissionItemLook(id){
    top.bsWindow(contextPath + "/supervise/permission/permission_item_look.jsp?id=" + id, "查看", {
        width : "900",
        height : "500",
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
function exportPermissionItem(){
    top.$.MsgBox.Confirm("提示","确定导出所有数据？",function(){
        var json = tools.requestJsonRs("/permissionItemSearchCtrl/exportPermissionItem.action?columns=" + temp);
        if(json == null || json.rtState){
            $.MsgBox.Alert_auto("导出成功！");
            location.href = "/permissionItemSearchCtrl/exportPermissionItem.action?columns=" + temp;
        } else{
            $.MsgBox.Alert_auto(json.rtMsg);
        }
    })
}