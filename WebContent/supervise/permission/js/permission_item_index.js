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
        url: contextPath + '/permissionItemCtrl/findListByPageRoles.action',
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
                field: '___', title: '操作' , halign: 'center', align: 'center', width: 10,
                formatter: function(e, rowData) {
                    var updatePage = "<span title='修改'><a href='javaScript:void(0);' onclick='permissionItemEdit(\"" + rowData.id + "\")'><i class='fa fa-pencil common-yellow'></i></a></span>";
                    var deletePage = "<span title='删除'><a href='javaScript:void(0);' onclick='permissionItemDelete(\"" + rowData.id + "\")' ><i class='fa fa-trash-o common-red'></i></a></span>";
                    var lookPage = "<span title='查看'><a href='javaScript:void(0);' onclick='permissionItemLook(\"" + rowData.id + "\")'><i class='fa fa fa-eye'></i></a></span>";
                    var optStr = updatePage + "&nbsp;&nbsp;" + deletePage + "&nbsp;&nbsp;" + lookPage;
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
 * 新增
 * @returns
 */
function permissionItemAdd(){
    top.bsWindow(contextPath + "/supervise/permission/permission_item_add.jsp?id=0", "新增", {
        width : "900",
        height : "500",
        buttons : [ {
            name : "关闭",
            classStyle : "btn-alert-gray"
        }, {
            name : "保存",
            classStyle : "btn-alert-blue"
        } ],
        submit : function(v, h) {
            if (v == "保存") {
                var cw = h[0].contentWindow;
                var status = cw.save();
                if (status == true) {
                    $("#permission_item_index_datagrid").datagrid("reload");
                    $('#permission_item_index_datagrid').datagrid("clearSelections");
                    return true;
                }
            } else if (v == "关闭") {
                return true;
            }
        }
    });
}

/**
 * 修改
 * @returns
 */
function permissionItemEdit(id){
    top.bsWindow(contextPath + "/supervise/permission/permission_item_add.jsp?id=" + id, "修改", {
        width : "900",
        height : "500",
        buttons : [ {
            name : "关闭",
            classStyle : "btn-alert-gray"
        }, {
            name : "保存",
            classStyle : "btn-alert-blue"
        } ],
        submit : function(v, h) {
            if (v == "保存") {
                var cw = h[0].contentWindow;
                var status = cw.update();
                if (status == true) {
                    $("#permission_item_index_datagrid").datagrid("reload");
                    $('#permission_item_index_datagrid').datagrid("clearSelections");
                    return true;
                }
            } else if (v == "关闭") {
                return true;
            }
        }
    });
}

/**
 * 删除
 * @param id
 * @returns
 */
function permissionItemDelete(id) {
	top.$.MsgBox.Confirm("提示","确定删除该条数据？",function(){
		var params = {
		    id: id,
		    isDelete: 1
		};
		var json = tools.requestJsonRs("/permissionItemCtrl/updateOrDelete.action", params);
        if(json.rtState){
            $.MsgBox.Alert_auto("您已完成删除所选数据！");
            $('#permission_item_index_datagrid').datagrid("reload");
        }else{
            $.MsgBox.Alert_auto("删除失败！");
        }
    },function(){
    	$.MsgBox.Alert_auto("您已放弃删除所选数据！");
    })
}

/**
 * 批量删除
 * @param id
 * @returns
 */
function permissionItemBatchDelete() {
    var getRows = $("#permission_item_index_datagrid").datagrid("getSelections");//获取当前页数据
    if(getRows == null || getRows == ""){
    	$.MsgBox.Alert_auto("请选择要删除的项！");
        return false;
    }
    var ids = [];
    $.each(getRows, function(index,val){//遍历JSON
        ids.push(val.id);
    });
    var id = "empty";
    if(ids != null && ids.length > 0){
        id = ids.join(',');
    }
    top.$.MsgBox.Confirm("提示","确定删除选中数据？",function(){
		var params = {
		    id: id,
		    isDelete: 1
		};
		var json = tools.requestJsonRs("/permissionItemCtrl/updateOrDelete.action", params);
        if(json.rtState){
            $.MsgBox.Alert_auto("您已完成删除所选数据！");
            $('#permission_item_index_datagrid').datagrid("reload");
        }else{
            $.MsgBox.Alert_auto("删除失败！");
        }
    },function(){
    	$.MsgBox.Alert_auto("您已放弃删除所选数据！");
    })
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
 * 获取登录信息，加载主体，和所属系统
 * @returns
 */
function getUserSubjetAndDepartment(){
    var json = tools.requestJsonRs("/commonCtrl/getRelations.action");
    if(json.rtState){
        if(json.rtData != null && json.rtData != ''){
            var subJson = [];//主体对象json
            var deptJson = [];//系统对象json
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
            subjectId = subJson.join(',');
            initCommonCaseSelectIndexJson('subjectId', subJson);
//            $('#subjectId').combobox('setValue', subJson[0]);//默认选中第一个主体
        }
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