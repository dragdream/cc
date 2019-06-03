// 当前状态
var xkZt = [
    {codeNo:'1', codeName: '有效'},
    {codeNo:'0', codeName: '无效'}
]

/**
 * 页面初始化函数
 * @returns
 */
function doInitIndex() {
    var params = tools.formToJson("#permission_list_form");
    initIndexDatagrid(params);
    initPermissionItem();// 初始化许可事项
    initCodeListSelect("PERMISSION_PARTY_TYPE", "partyType");// 初始化行政相对人类型
    initCodeListSelect("PERMISSION_TYPE", "permissionType");// 初始化审核类型
    doInitEnter();// 回车查询
}

/**
 * 初始化许可事项，本单位及下级
 */
function initPermissionItem(){
    var json = tools.requestJsonRs("/permissionListSearchCtrl/findPermissionItemRoles.action");
    json.rtData.unshift({id: "", name: "全部"});
    var editable = false;
    if(json.rtData.length>15){
    	editable = true;
    }
    if(json.rtState) {
        $('#itemId').combobox({
            data: json.rtData,
            valueField: 'id',
            textField: 'name',
            panelHeight: 'auto',
            panelMaxHeight : 200,
            editable : editable,
            prompt : '全部',
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
 * 表格加载函数
 * @returns
 */
function initIndexDatagrid(params){
    datagrid = $('#permission_list_index_datagrid').datagrid({
        url: contextPath + '/permissionListSearchCtrl/findListBypageRoles.action',
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
        singleSelect: false, //为true只能选择一行
        nowrap: true,
        onLoadSuccess: function(data) {
        },
        columns: [[
            {field: 'id', checkbox: true, title: "ID", width: 10, halign: 'center', align: 'center'},
            {field:'ID',title:'序号',align:'center',
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {field: 'listCode', title: '办件编号', width: 15, halign: 'center', align: 'left',
                formatter: function(e, rowData) {
                    var listCode = rowData.listCode;
                    if(listCode == null || listCode == 'null') {
                    	listCode = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+listCode+"'><a onclick='permissionListLook(\"" + rowData.id + "\")' href='javaScript:void(0);'>"+listCode+"</lable>"
                    return lins;
                }
            },
            {field: 'itemName', title: '办理事项', width: 15, halign: 'center', align: 'left',
                formatter: function(e, rowData) {
                    var itemName = rowData.itemName;
                    if(itemName == null || itemName == 'null') {
                    	itemName = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+itemName+"'>"+itemName+"</lable>"
                    return lins;
                }
            },
            {field: 'partyTypeValue', title: '行政相对人类型', width: 12, halign: 'center', align: 'center'},
            {field: 'partyName', title: '行政相对人名称', width: 12, halign: 'center', align: 'center'},
            {field: 'decisionCode', title: '许可决定文书号', width: 15, halign: 'center', align: 'left',
                formatter: function(e, rowData) {
                    var decisionCode = rowData.decisionCode;
                    if(decisionCode == null || decisionCode == 'null') {
                    	decisionCode = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+decisionCode+"'>"+decisionCode+"</lable>"
                    return lins;
                }
            },
            {field: 'decisionDateStr', title: '决定日期 ', width: 10, halign: 'center', align: 'center'},
            {field: 'permissionTypeValue', title: '审核类型 ', width: 7, halign: 'center', align: 'center'},
            {
                field: '___',
                title: '操作' , halign: 'center', align: 'center',
                formatter: function(e, rowData) {
                    var lookPage = "<span title='查看'><a href='javaScript:void(0);' onclick='permissionListLook(\"" + rowData.id + "\")'><i class='fa fa fa-eye'></i></a></span>";
                    var optStr = lookPage;
                    return optStr;
                },
                width: 5
            }
        ]]
    });
}

/**
 * 查询方法
 * @returns
 */
function permissionListSearch(){
    if($('#permission_list_form').form('enableValidation').form('validate')){
        var param = tools.formToJson("#permission_list_form");
        $('#permission_list_index_datagrid').datagrid('reload', param);
    }
}

/**
 * 获取回车事件
 * @returns
 */
function doInitEnter() {
    document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
        	permissionListSearch();
        }
    }
}

/**
 * 查看
 * @returns
 */
function permissionListLook(id){
    top.bsWindow(contextPath + "/supervise/permission/permission_list_look.jsp?id=" + id, "查看", {
        width : "960",
        height : "460",
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