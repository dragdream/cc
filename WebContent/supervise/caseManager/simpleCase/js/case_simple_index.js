var menuNameStr = $('#simple_case_index_menuGroupStrNames').val();// 菜单权限

/**
 * 页面初始化函数
 * @returns
 */
function doInitIndex() {
	dateValidate('beginpunishmentDateStr', 'endpunishmentDateStr');
	initCodeListSelect("COMMON_PARTY_TYPE", "partyType");// 初始化当事人类型
	initCodeListMultipleSelect("SIMPLE_PUNISH_DECISION_TYPE","punishDecisionType");// 初始化处罚决定类型
	doInitEnter();//回车响应
    var params = {
        menuNames: menuNameStr
    }
    initIndexDatagrid(params);
    getUserSubjetAndDepartment();//获取登录信息，加载主体
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
            initCommonCaseSelectIndexJson('subjectId', subJson);
//            $('#subjectId').combobox('setValue', subJson[0]);//默认选中第一个主体
        }
    }
}


/**
 * 表格加载函数
 * @returns
 */
function initIndexDatagrid(params){
    datagrid = $('#case_simple_index_datagrid').datagrid({
        url: contextPath + '/caseSimpleBaseCtrl/findListBypage.action',
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
            { 
                field: 'id', checkbox: true, title: "ID", width: 3, halign: 'center', align: 'center'
            },
            {field:'ID',title:'序号',align:'center',
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            /*{field: 'filingDateStr', title: '立案批准日期', width: 10, halign: 'center', align: 'center'},*/
            {field: 'punishmentDateStr', title: '处罚决定日期', width: 10, halign: 'center', align: 'center'},
            {
                field: 'punishmentCode', title: '处罚决定书文号', width: 15, halign: 'center', align: 'left',
                formatter: function(e, rowData) {
                    var punishmentCode = rowData.punishmentCode;
                    if(punishmentCode == null || punishmentCode == 'null') {
                        punishmentCode = "";
                    }
                    var lins = "<label class='common-overflow-hidden common-table-td-full-width' title='"+punishmentCode+"'><a onclick='simpleBaseLook(\"" + rowData.id + "\")' href='javaScript:void(0);'>"+punishmentCode+"</a></label>"
                    return lins;
                }
            },
            {
                field: 'punishDecisionTypeValue', title: '处罚决定种类', width: 15, halign: 'center', align: 'left'
            },
            {
                field: 'name', title: '案由（事由）', width: 25, halign: 'center', align: 'left',
                formatter: function(e, rowData) {
                    var name = rowData.name
                    if(name == null || name == 'null') {
                        name = "";
                    }
                    var lins = "<label class='common-overflow-hidden common-table-td-full-width' title='"+name+"'>" + name + "</label>"
                    return lins;
                }
            },
            {field: 'partyTypeValue', title: '当事人类型', width: 10, halign: 'center', align: 'center'},
            {field: 'partyName', title: '当事人名称', width: 10, halign: 'center', align: 'center'},
            {field: 'createTimeStr', title: '入库日期', width: 10, halign: 'center', align: 'center'},
            {
                field: '___',
                title: '操作' , halign: 'center', align: 'center',
                formatter: function(e, rowData) {
                	var updatePage = "<span title='修改'><a href='javaScript:void(0);' onclick='simpleBaseUpdate(\"" + rowData.id + "\")'><i class='fa fa-pencil common-yellow'></i></a></span>";
                    var deletePage = "<span title='删除'><a href='javaScript:void(0);' onclick='updateDelete(\"" + rowData.id + "\")' ><i class='fa fa-trash-o common-red'></i></a></span>";
                    var lookPage = "<span title='查看'><a href='javaScript:void(0);' onclick='simpleBaseLook(\"" + rowData.id + "\")'><i class='fa fa fa-eye'></i></a></span>";
                    var optStr = "";
                    if(parseInt(rowData.isSubmit) == 1){
                        optStr = lookPage;
                    }else if(parseInt(rowData.isSubmit) == 0 && menuNameStr.indexOf('填报人员') != -1){
                        optStr = updatePage + "&nbsp;&nbsp;" +deletePage;
                    }else if(rowData.isSubmit == null || rowData.isSubmit == ''){
                        optStr = lookPage;
                    }
                    return optStr;
                },
                width: 6
            }
        ]]
    });
}

/**
 * 获取回车事件
 * @returns
 */
function doInitEnter() {
    document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
            commonCaseSearch();
        }
    }
}


/**
 * 查询方法
 * @returns
 */
/*function commonCaseSearch(){
    
    var name = $.trim($('#name').val());
    var isSubmit = $.trim($('#isSubmit').combobox('getValue'));
    if(isSubmit == null || isSubmit == ''){
        isSubmit = null;
    }
    var subjectId = $.trim($('#subjectId').combobox('getValue'));
    var punishmentCode = $.trim($('#punishmentCode').val());
    var createStartDateStr = $.trim($('#createStartDateStr').val());
    var createEndDateStr = $.trim($('#createEndDateStr').val());
    var punishmentStartDateStr = $.trim($('#punishmentStartDateStr').val());
    var punishmentEndDateStr = $.trim($('#punishmentEndDateStr').val());
    var param = {
        name: name,
        isSubmit: isSubmit,
        subjectId: subjectId,
        punishmentCode: punishmentCode,
        createStartDateStr: createStartDateStr,
        createEndDateStr: createEndDateStr,
        punishmentStartDateStr: punishmentStartDateStr,
        punishmentEndDateStr: punishmentEndDateStr,
        menuNames: menuNameStr
    }
    $('#case_simple_index_datagrid').datagrid('reload', param);
}*/
function commonCaseSearch(){
	if($('#case_simple_form').form('enableValidation').form('validate')){
        var param = tools.formToJson("#case_simple_form");
        if(param.punishDecisionType != null && param.punishDecisionType.length>0){
        	if(param.punishDecisionType.toString().split(",").contains("02")){
                param.isFine = 1;
            }
            if(param.punishDecisionType.toString().split(",").contains("01")){
                param.isWarn = 1;
            }
        }
        initIndexDatagrid(param);
    }
}

/**
 * 案件信息查看方法
 * @returns
 */
function simpleBaseLook(id) {
	location.href = "/supervise/caseManager/simpleCase/case_simple_look.jsp?id="+id;
	/*var url = "/supervise/caseManager/simpleCase/case_simple_look.jsp?id="+id;
    var title = "案件查看";
    top.bsWindow(
            url,
            title,
            {
                width:"1000", 
                height:"500", 
                buttons:[
                    {name:"关闭",classStyle:"btn-alert-gray"}
                ],
                submit:function(v,h){
                    var cw = h[0].contentWindow;
                    if( v == "保存") {
                        return true;
                    } else if(v=="关闭"){
                        return true;
                    }
                }
            }
     );*/
    
}

/**
 * 案件信息修改方法
 * @returns
 */
function simpleBaseUpdate(id) {
	location.href = "/caseSimpleBaseCtrl/simpleBaseAdd.action?id=" + id + "&editFlag=2";
    /*var timestamp = Date.parse(new Date());
    var modelId = timestamp/1000;
    var url = "/caseSimpleBaseCtrl/simpleBaseAdd.action?id=" + id + "&editFlag=2&modelId="+modelId;
    var title = "案件修改";
    top.bsWindow2(
            url,
            title,
            {
                width:"1000", 
                height:"550", 
                buttons:[
                    {name:"关闭",classStyle:"btn-alert-gray"}
                ],
                submit:function(v,h){
                    var cw = h[0].contentWindow;
                    if(v=="关闭"){
                    	$("#case_simple_index_datagrid").datagrid("reload");
                        $('#case_simple_index_datagrid').datagrid("clearSelections");
                        return true;
                    }
                    $("#case_simple_index_datagrid").datagrid("reload");
                    $('#case_simple_index_datagrid').datagrid("clearSelections");
                }
            },
            modelId
     );
    */
}

/**
 * 跳转新增页面
 * @returns
 */
/*function commonCaseAdd()
{
    window.location.href = contextPath+"/supervise/caseManager/simpleCase/case_simple_add.jsp";
}
*/
function commonCaseAdd() {
    location.href = "/supervise/caseManager/simpleCase/case_simple_add.jsp?editFlag=1";
    /*var url = "/supervise/caseManager/simpleCase/case_simple_add.jsp?modelId="+modelId +"&editFlag=1";
    var title = "案件填报";
    
    top.bsWindow2(
            url,
            title,
            {
                width:"1000", 
                height:"550", 
                buttons:[
                    {name:"关闭",classStyle:"btn-alert-gray"}
                ],
                submit:function(v,h){
                    var cw = h[0].contentWindow;
                    if(v=="关闭"){
                    	$("#case_simple_index_datagrid").datagrid("reload");
                        $('#case_simple_index_datagrid').datagrid("clearSelections");
                        return true;
                    }
                    $("#case_simple_index_datagrid").datagrid("reload");
                    $('#case_simple_index_datagrid').datagrid("clearSelections");
                }
            },
            modelId
     );*/
    
}

/**
 * 删除案件
 * @param id
 * @returns
 */
function updateDelete(id){
    var params = {
            id: id,
            isDelete: 1
    };
    top.$.MsgBox.Confirm("提示","确定删除该条数据？",function(){
		var params = {
		    id: id,
		    isDelete: 1
		};
		var json = tools.requestJsonRs("/caseSimpleBaseCtrl/updateOrdeleteOrSubmit.action", params);
        if(json.rtState){
            $.MsgBox.Alert_auto("删除成功！");
            $('#case_simple_index_datagrid').datagrid("reload");
        }else{
            $.MsgBox.Alert_auto("删除失败！");
        }
    })
}

/**
 * 提交 或者退回 案件
 * @param id
 * @returns
 */
function caseSubmit(isSubmit) {
    var getRows = $("#case_simple_index_datagrid").datagrid("getSelections");//获取当前页数据
    var flag = false; //判断是否存在已经提交的案件
    var message = "";
    var successMessage = "";
    var falseMessage = "";
    if(isSubmit == 1){
        message = "是否确认提交？";
        successMessage = "提交成功！";
        falseMessage = "提交失败！";
        if(getRows != null && getRows.length > 0){
            $.each(getRows, function(index,val){//遍历JSON
                if(val.isSubmit != null && val.isSubmit != '' && parseInt(val.isSubmit) == 1){
                	top.$.MsgBox.Alert("提示","存在已提交的案件，不能重复提交！！！");
                    flag = true;
                    return false;
                }
            });
        }else{
        	top.$.MsgBox.Alert("提示","请选择要提交的数据！");
            return false;
        }
    }else{
        message = "是否确认退回？";
        successMessage = "退回成功！";
        falseMessage = "退回失败！";
        if(getRows != null && getRows.length > 0){
            $.each(getRows, function(index,val){//遍历JSON
                if(val.isSubmit == null || val.isSubmit == '' || parseInt(val.isSubmit) == 0){
                	top.$.MsgBox.Alert("提示","存在未提交的案件，不能退回！！！");
                    flag = true;
                    return false;
                }
            });
        }else{
        	top.$.MsgBox.Alert("提示","请选择要退回的数据！");
            return false;
        }
    }
    if (flag){
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
    var params = {
            id: id,
            isSubmit: isSubmit
    };
    top.$.MsgBox.Confirm("提示",message,function(){
		var json = tools.requestJsonRs("/caseSimpleBaseCtrl/updateOrdeleteOrSubmit.action", params);
        if(json.rtState){
            $.MsgBox.Alert_auto(successMessage);
            $('#case_simple_index_datagrid').datagrid("reload");
        }else{
            $.MsgBox.Alert_auto(falseMessage);
        }
    })
}
