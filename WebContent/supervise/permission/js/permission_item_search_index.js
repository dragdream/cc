/**
 * 页面初始化函数
 * @returns
 */
function doInitIndex() {
    var params = tools.formToJson("#permission_item_form");
    initIndexDatagrid(params);
    initCodeListSelect("PERMISSION_LIST_TYPE", "listType");// 初始化办件类型
    initCodeListSelect("PERMISSION_PARTY_TYPE", "partyType");// 初始化服务对象
    initCodeListSelect("PERMISSION_HANDLE_WAY", "handleWay");// 初始化办理形式
    doInitEnter();// 回车查询
}

/**
 * 表格加载函数
 * @returns
 */
function initIndexDatagrid(params){
    datagrid = $('#permission_item_index_datagrid').datagrid({
        url: contextPath + '/permissionItemSearchCtrl/findListBypageRoles.action',
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
			{field: 'name', title: '事项名称', width: 40, halign: 'center', align: 'left',
			    formatter: function(e, rowData) {
			        var name = rowData.name;
			        if(name == null || name == 'null') {
			            name = "";
			        }
			        var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+name+"'><a onclick='permissionItemLook(\"" + rowData.id + "\")' href='javaScript:void(0);'>" + name + "</a></lable>";
			        return lins;
			    }
			},
			{field: 'listTypeValue', title: '办件类型', width: 10, halign: 'center', align: 'center'},
			{field: 'subjectName', title: '许可主体', width: 20, halign: 'center', align: 'left',
			    formatter: function(e, rowData) {
			        var subjectName = rowData.subjectName;
			        if(subjectName == null || subjectName == 'null') {
			            subjectName = "";
			        }
			        var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+subjectName+"'>"+subjectName+"</lable>"
			        return lins;
			    }
			},
			{field: 'statutoryTimeLimit', title: '法定时限', width: 8, halign: 'center', align: 'center'},
			{field: 'promisedTimeLimit', title: '承诺时限', width: 8, halign: 'center', align: 'center'},
            {
                field: '___', title: '操作' , halign: 'center', align: 'center', width: 5,
                formatter: function(e, rowData) {
                    var lookPage = "<span title='查看'><a href='javaScript:void(0);' onclick='permissionItemLook(\"" + rowData.id + "\")'><i class='fa fa fa-eye'></i></a></span>";
                    var optStr = lookPage;
                    return optStr;
                }
            }
        ]]
    });
}

/**
 * 查询方法
 * @returns
 */
function permissionItemSearch(){
    if($('#permission_item_form').form('enableValidation').form('validate')){
        var param = tools.formToJson("#permission_item_form");
        $('#permission_item_index_datagrid').datagrid('reload', param);
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
        	permissionItemSearch();
        }
    }
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
 * 初始化许可部门
 */
function initDepartment(id){
    //所属部门
    var json = tools.requestJsonRs("/departmentSearchController/getDepartmentRoles.action");
    if(json.rtState) {
        $('#xkGsdw').combobox({
            data: json.rtData,
            valueField: 'id',
            textField: 'name',
            panelHeight: 'auto',
            panelMaxHeight : 200,
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

//通用的combobox处理方法
function ComboboxCommonProcess(obj){
    var values = $(obj).combobox("getValues");
    var getData = $(obj).combobox("getData");
    var valuesT = [];
    for(var i=0;i<values.length;i++){
        for(var ii=0;ii<getData.length;ii++){
            if(values[i]==getData[ii].id){
                valuesT.push(values[i]);
                break;
            }
        }
    }
    $(obj).combobox("setValues",valuesT);
}