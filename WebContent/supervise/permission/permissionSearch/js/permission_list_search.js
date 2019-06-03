var params;
var partyTypeJsons = [];
var permissionTypeJsons = [];
/**
 * 可选列
 */
var listdata = {
    isShowColumn: [
        {codeNo:'itemName', codeName: '事项名称'},
        {codeNo:'partyTypeValue', codeName: '相对人类型'},
        {codeNo:'partyName', codeName: '相对人名称'},
        {codeNo:'cardTypeValue', codeName: '相对人证件类型'},
        {codeNo:'cardCode', codeName: '证件号码'},
        {codeNo:'decisionDateStr', codeName: '许可决定日期'},
        {codeNo:'permissionTypeValue', codeName: '审核类型'},
        {codeNo:'subjectName', codeName: '办理主体'},
        {codeNo:'certificateCode', codeName: '许可证书编号'},
        {codeNo:'matters', codeName: '许可内容'},
        {codeNo:'validDateStr', codeName: '有效期限'}
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
var temp = ['itemName','decisionDateStr','permissionTypeValue','subjectName'];

/**
 * 可选列选中/取消
 * @param code
 * @param th
 */
function detail(code,th){
    if($(th).children('i').hasClass("fa-check")){
        $(th).children('i').removeClass("fa-check");
        $('#permission_list_index_datagrid').datagrid('hideColumn', code);
        for(var i=0;i<temp.length;i++){
            if(temp[i] == code){
                temp.splice(i,1);
                break;
            }
        }
    } else{
        $(th).children('i').addClass("fa-check");
        $('#permission_list_index_datagrid').datagrid('showColumn', code);
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
    $("#permissionListDiv").hide();
    dateValidate('endValidDateStr', 'endEndValidDateStr');
    initsubjectId();
    initPermissionItem();
    initPermissionType();
    //initXkBjlx();
    //initXkZt();
    initpartyType();
    // 初始化可选列
    $(".panList").append(juicer(tpl,listdata));
    // 初始化表格
    datagrid = $('#permission_list_index_datagrid') .datagrid( {
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar : '#toolbar'// 工具条对象
    });
}

/**
 * 初始化许可事项
 */
function initPermissionItem(id){
    var json = tools.requestJsonRs("/permissionListCtrl/getPermissionItemByOneself.action");
    if(json.rtState) {
        $('#itemId').combobox({
            data: json.rtData,
            valueField: 'id',
            textField: 'name',
            panelHeight: 'auto',
            panelMaxHeight : 150,
            onLoadSuccess:function(){
                if(id != null && id != "" && id != '0'){
                    $(this).combobox('setValue',id);
                }
            },
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
 * 初始化审核类型
 * @returns
 */
function initPermissionType() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: 'PERMISSION_TYPE'});
    if(json.rtState) {
        var page = "";
        var permissionTypeJson;
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelWidth="80px;" labelPosition="after" type="checkbox" name="permissionType" id="permissionType'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'  "/>';
                //+ 'value="'+json.rtData[i].codeNo+'" title="'+json.rtData[i].codeName+'" label="<span title=\'' + json.rtData[i].codeName + '\'>'+json.rtData[i].codeName+'</span>"/>';
            permissionTypeJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
            permissionTypeJsons.push(permissionTypeJson);
        }
        var pageDoc = $('#permissionType_td').html(page);
        $.parser.parse(pageDoc);
    }
}

/**
 * 初始化
 * @returns
 */
function initXkBjlx() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: 'PERMISSION_TYPE'});
    if(json.rtState) {
        var page = "";
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelWidth="80px;" labelPosition="after" type="checkbox" name="xkBjlx" id="xkBjlx'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'  "/>';
                //+ 'value="'+json.rtData[i].codeNo+'" title="'+json.rtData[i].codeName+'" label="<span title=\'' + json.rtData[i].codeName + '\'>'+json.rtData[i].codeName+'</span>"/>';
        }
        var pageDoc = $('#xkBjlx_td').html(page);
        $.parser.parse(pageDoc);
    }
}

/**
 * 初始化许可状态
 * @returns
 */
function initXkZt() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: 'XK_ZT'});
    if(json.rtState) {
        var page = "";
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelWidth="80px;" labelPosition="after" type="checkbox" name="xkZt" id="xkZt'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'  "/>';
                //+ 'value="'+json.rtData[i].codeNo+'" title="'+json.rtData[i].codeName+'" label="<span title=\'' + json.rtData[i].codeName + '\'>'+json.rtData[i].codeName+'</span>"/>';
        }
        var pageDoc = $('#xkZt_td').html(page);
        $.parser.parse(pageDoc);
    }
}

/**
 * 初始化相对人类型
 * @returns
 */
function initpartyType() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: 'PERMISSION_PARTY_TYPE'});
    if(json.rtState) {
        var page = "";
        var partyTypeJson;
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelWidth="80px;" labelPosition="after" type="checkbox" name="partyType" id="partyType'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'  "/>';
                //+ 'value="'+json.rtData[i].codeNo+'" title="'+json.rtData[i].codeName+'" label="<span title=\'' + json.rtData[i].codeName + '\'>'+json.rtData[i].codeName+'</span>"/>';
            partyTypeJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
            partyTypeJsons.push(partyTypeJson);
        }
        var pageDoc = $('#partyType_td').html(page);
        $.parser.parse(pageDoc);
    }
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
    params = tools.formToJson($("#permission_list_search_form"));
    var conditionDiv = '';
    // 获取许可部门
    params.subjectId = $("#subjectId").combobox("getValues").join(",");
    if(params.subjectId.length>0){
        conditionDiv += '<span class="tagbox-label" id="subjectIdTag">'
            + '<span title="办理主体：'+$("#subjectId").combobox("getText")+'">办理主体</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'subjectId\',\'combobox\')" class="tagbox-remove"></a></span>';
    }
    // 获取许可事项
    params.itemId = $("#itemId").combobox("getValues").join(",");
    if(params.itemId.length>0){
        conditionDiv += '<span class="tagbox-label" id="itemIdTag">'
            + '<span title="许可事项：'+$("#itemId").combobox("getText")+'">许可事项</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'itemId\',\'combobox\')" class="tagbox-remove"></a></span>';
    }
    
    // 获取审核类型
    var permissionType = '';
    var permissionType0 = '';
    if($("#permission_list_search_form input[name='permissionType']:checked").length >0){
        $("#permission_list_search_form input[name='permissionType']:checked").each(function(){
            permissionType += this.value + ',';
            for(var i=0;i<permissionTypeJsons.length;i++){
            	if(this.value==permissionTypeJsons[i].codeNo){
            		permissionType0 += $.trim(permissionTypeJsons[i].codeName) + '，';
            		break;
            	}
            }
        })
    }
    if(permissionType.length>0){
        permissionType = permissionType.substring(0,permissionType.length-1);
        conditionDiv += '<span class="tagbox-label" id="permissionTypeTag">'
                     + '<span title="审核类型：'+permissionType0.substring(0,permissionType0.length-1)+'">审核类型</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'permissionType\',\'checkbox\',\''+ permissionType +'\')" class="tagbox-remove"></a></span>';
    }
    params.permissionType = permissionType;
    
    // 获取办件类型
//    var xkBjlx = '';
//    var xkBjlx0 = '';
//    if($("#permission_list_search_form input[name='xkBjlx']:checked").length >0){
//        $("#permission_list_search_form input[name='xkBjlx']:checked").each(function(){
//        	xkBjlx += this.value + ',';
//            xkBjlx0 += $.trim(this.labels[0].innerHTML) + '，';
//        })
//    }
//    if(xkBjlx.length>0){
//        xkBjlx = xkBjlx.substring(0,xkBjlx.length-1);
//        conditionDiv += '<span class="tagbox-label" id="xkBjlxTag">'
//                     + '<span title="办件类型：'+xkBjlx0.substring(0,xkBjlx0.length-1)+'">办件类型</span>'
//                     + '<a href="javascript:;" onclick="thisRemove(\'xkBjlx\',\'checkbox\',\''+ xkBjlx +'\')" class="tagbox-remove"></a></span>';
//    }
//    params.xkBjlx = xkBjlx;
    
    // 获取决定书文号
    if(params.decisionCode.length>0){
        conditionDiv += '<span class="tagbox-label" id="decisionCodeTag">'
                     + '<span title="决定书文号：'+params.decisionCode+'">决定书文号</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'decisionCode\',\'textbox\')" class="tagbox-remove"></a></span>';
    }
    
    // 获取许可证书编号
    if(params.certificateCode.length>0){
        conditionDiv += '<span class="tagbox-label" id="certificateCodeTag">'
                     + '<span title="许可证书编号：'+params.certificateCode+'">许可证书编号</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'certificateCode\',\'textbox\')" class="tagbox-remove"></a></span>';
    }
    
    // 获取当前状态
//    var xkZt = '';
//    var xkZt0 = '';
//    if($("#permission_list_search_form input[name='xkZt']:checked").length >0){
//        $("#permission_list_search_form input[name='xkZt']:checked").each(function(){
//            xkZt += this.value + ',';
//            xkZt0 += $.trim(this.labels[0].innerHTML) + '，';
//        })
//    }
//    if(xkZt.length>0){
//        xkZt = xkZt.substring(0,xkZt.length-1);
//        conditionDiv += '<span class="tagbox-label" id="xkZtTag">'
//                     + '<span title="当前状态：'+xkZt0.substring(0,xkZt0.length-1)+'">当前状态</span>'
//                     + '<a href="javascript:;" onclick="thisRemove(\'xkZt\',\'checkbox\',\''+ xkZt +'\')" class="tagbox-remove"></a></span>';
//    }
//    params.xkZt = xkZt;
    
    // 获取有效截至日期
    if(params.endValidDateStr.length>0 && params.endEndValidDateStr.length>0){
        conditionDiv += '<span class="tagbox-label" id="endValidDateStrTag">'
            + '<span title="有效截至日期：'+params.endValidDateStr+' - '+params.endEndValidDateStr+'">有效截至日期</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'endValidDateStr\',\'datebox\')" class="tagbox-remove"></a></span>';
    }else if(params.endValidDateStr.length>0){
        conditionDiv += '<span class="tagbox-label" id="endValidDateStrTag">'
                     + '<span title="有效截至日期>'+params.endValidDateStr+'">有效截至日期</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'endValidDateStr\',\'datebox\')" class="tagbox-remove"></a></span>';
    }else if(params.endEndValidDateStr.length>0){
        conditionDiv += '<span class="tagbox-label" id="endValidDateStrTag">'
            + '<span title="有效截至日期<'+params.endEndValidDateStr+'">有效截至日期</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'endValidDateStr\',\'datebox\')" class="tagbox-remove"></a></span>';
    }
    
    // 获取相对人类型
    var partyType = '';
    var partyType0 = '';
    if($("#permission_list_search_form input[name='partyType']:checked").length >0){
        $("#permission_list_search_form input[name='partyType']:checked").each(function(){
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
                     + '<span title="相对人类型：'+partyType0.substring(0,partyType0.length-1)+'">相对人类型</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'partyType\',\'checkbox\',\''+ partyType +'\')" class="tagbox-remove"></a></span>';
    }
    params.partyType = partyType;
    
    // 获取相对人名称
    if(params.partyName.length>0){
        conditionDiv += '<span class="tagbox-label" id="partyNameTag">'
                     + '<span title="相对人名称：'+params.partyName+'">相对人名称</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'partyName\',\'textbox\')" class="tagbox-remove"></a></span>';
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
    $("#permissionListDiv").show();
    initIndexDatagrid(params);
}

/**
 * 重置查询条件
 */
function permissionRefresh(){
	$('#subjectId').combobox('setValue', '');
	$('#itemId').combobox('setValue', '');
	$("#permission_list_search_form input[name='permissionType']:checked").each(function(){
        $('#permissionType' + this.value).checkbox({checked: false});
    });
	$("#permission_list_search_form input[name='xkBjlx']:checked").each(function(){
        $('#xkBjlx' + this.value).checkbox({checked: false});
    });
	$('#decisionCode').textbox('setValue', '');
    $('#certificateCode').textbox('setValue', '');
    $("#permission_list_search_form input[name='xkZt']:checked").each(function(){
        $('#xkZt' + this.value).checkbox({checked: false});
    });
    $('#endValidDateStr').datebox({validType:''});
    $('#endEndValidDateStr').datebox({validType:''});
    $('#endValidDateStr').datebox('setValue', '');
    $('#endEndValidDateStr').datebox('setValue', '');
    $('#endValidDateStr').datebox({validType:'date'});
    $('#endEndValidDateStr').datebox({validType:'date'});
    $("#permission_list_search_form input[name='partyType']:checked").each(function(){
        $('#partyType' + this.value).checkbox({checked: false});
    });
    $('#partyName').textbox('setValue', '');
}

/**
 * 返回条件页
 */
function back(){
    $("#permissionSearchDiv").show();
    $("#permissionListDiv").hide();
}

/**
 * 删除单个条件
 * @param thisTag ID
 * @param thisType 输入框类型
 * @param thisVal 多选框value
 */
function thisRemove(thisTag, thisType, thisVal){
    //$("#"+thisTag+"Tag").hide();
    if(thisType == 'textbox'){
        $("#"+thisTag).textbox("setValue","");
    }else if(thisType == 'combobox'){
        $("#"+thisTag).combobox("setValue","");
    }else if(thisType == 'checkbox'){
        var val = thisVal.split(",");
        for(var i=0 ; i < val.length; i++){
            $("#"+thisTag+val[i]).checkbox({checked: false});
        }
    }else if(thisType == 'datebox'){
        $("#endValidDateStr").datebox("setValue","");
        $("#endEndValidDateStr").datebox("setValue","");
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
    {field: 'decisionCode', title: '许可决定文书号', width: '180px', halign: 'center', align: 'left',
        formatter: function(e, rowData) {
            var decisionCode = rowData.decisionCode;
            if(decisionCode == null || decisionCode == 'null') {
                decisionCode = "";
            }
            var lins = "<label class='common-overflow-hidden common-table-td-full-width' title='"+decisionCode+"'><a onclick='permissionListLook(\"" + rowData.id + "\")' href='javaScript:void(0);'>" + decisionCode + "</a></label>"
            return lins;
        }
    }
]];

/**
 * 活动列
 */
var column = [[
    {field: 'itemName', title: '事项名称', width: '250px', halign: 'center', align: 'left', hidden: true,
        formatter: function(e, rowData) {
            var itemName = rowData.itemName;
            if(itemName == null || itemName == 'null') {
                itemName = "";
            }
            var lins = "<label class='common-overflow-hidden common-table-td-full-width' title='"+itemName+"'>"+itemName+"</label>"
            return lins;
        }
    },
    {field: 'partyTypeValue', title: '相对人类型', width: '140px', halign: 'center', align: 'center', hidden: true},
    {field: 'partyName', title: '相对人名称', width: '120px', halign: 'center', align: 'left', hidden: true,
        formatter: function(e, rowData) {
            var partyName = rowData.partyName;
            if(partyName == null || partyName == 'null') {
                partyName = "";
            }
            var lins = "<label class='common-overflow-hidden common-table-td-full-width' title='"+partyName+"'>"+partyName+"</label>"
            return lins;
        }
    },
    {field: 'cardTypeValue', title: '相对人证件类型', width: '140px', halign: 'center', align: 'center', hidden: true},
    {field: 'cardCode', title: '证件号码', width: '150px', halign: 'center', align: 'center', hidden: true},
    {field: 'decisionDateStr', title: '许可决定日期', width: '140px', halign: 'center', align: 'center', hidden: true},
    {field: 'permissionTypeValue', title: '审核类型 ', width: '120px', halign: 'center', align: 'center', hidden: true},
    {field: 'subjectName', title: '办理主体', width: '180px', halign: 'center', align: 'left', hidden: true,
        formatter: function(e, rowData) {
            var subjectName = rowData.subjectName;
            if(subjectName == null || subjectName == 'null') {
                subjectName = "";
            }
            var lins = "<label class='common-overflow-hidden common-table-td-full-width' title='"+subjectName+"'>"+subjectName+"</label>"
            return lins;
        }
    },
    {field: 'certificateCode', title: '许可证书编号 ', width: '120px', halign: 'center', align: 'center', hidden: true},
    {field: 'matters', title: '许可内容', width: '180px', halign: 'center', align: 'left', hidden: true,
        formatter: function(e, rowData) {
            var matters = rowData.matters;
            if(matters == null || matters == 'null') {
                matters = "";
            }
            var lins = "<label class='common-overflow-hidden common-table-td-full-width' title='"+matters+"'>"+matters+"</label>"
            return lins;
        }
    },
    {field: 'validDateStr', title: '有效期限', width: '140px', halign: 'center', align: 'center', hidden: true}
]];

/**
 * 表格加载函数
 * @returns
 */
function initIndexDatagrid(params){
    datagrid = $('#permission_list_index_datagrid').datagrid({
        url: contextPath + '/permissionListSearchCtrl/findListByPageSearch.action',
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
                    $('#permission_list_index_datagrid').datagrid('showColumn', temp[i]);
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
function permissionListLook(id){
    top.bsWindow(contextPath + "/supervise/permission/permission_list_look.jsp?id=" + id, "查看", {
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
function exportPermissionList(){
    top.$.MsgBox.Confirm("提示","确定导出所有数据？",function(){
        var json = tools.requestJsonRs("/permissionListSearchCtrl/exportPermissionList.action?columns=" + temp);
        if(json == null || json.rtState){
            $.MsgBox.Alert_auto("导出成功！");
            location.href = "/permissionListSearchCtrl/exportPermissionList.action?columns=" + temp;
        } else{
            $.MsgBox.Alert_auto(json.rtMsg);
        }
    })
}