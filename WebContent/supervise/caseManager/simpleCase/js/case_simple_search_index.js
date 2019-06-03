//权限组
var menuNameStr = $('#simple_case_serach_index_menuGroupStrNames').val();
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
        menuNames: menuNameStr,
        isSubmit: 1
    }
    initIndexDatagrid(params);
    getUserSubjetAndDepartment();//获取登录信息，加载主体
}


function initSubject(id){
    //所属部门
    var json = tools.requestJsonRs("/subjectSearchController/getSubjectRoles.action");
    if(json.rtState) {
        $('#subjectId').combobox({
            data: json.rtData,
            valueField: 'id',
            textField: 'subName',
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
//            initCommonCaseSelectJson('departmentId', deptJson);
        }
    }
}


/**
 * 表格加载函数
 * @returns
 */
function initIndexDatagrid(params){
    datagrid = $('#case_simple_index_datagrid').datagrid({
        url: contextPath + '/caseSimpleBaseCtrl/findSearchListByPage.action',
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
                    var punishmentCode = rowData.punishmentCode
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
                    var optStr = "<span title='查看'><a href='javaScript:void(0);' onclick='simpleBaseLook(\"" + rowData.id + "\")'><i class='fa fa fa-eye'></i></a></span>";
                    return optStr;
                },
                width: 3
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
    var punishmentCode = $.trim($('#punishmentCode').val());
    var createStartDateStr = $.trim($('#createStartDateStr').val());
    var createEndDateStr = $.trim($('#createEndDateStr').val());
    var punishmentStartDateStr = $.trim($('#punishmentStartDateStr').val());
    var punishmentEndDateStr = $.trim($('#punishmentEndDateStr').val());
    var subjectId = $.trim($('#subjectId').combobox('getValue'));
    var param = {
        subjectId: subjectId,
    	name: name,
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
/*function simpleBaseLook(id)
{
    window.location.href = contextPath+"/caseSimpleBaseCtrl/simpleBaseAdd.action?id=" + id + "&editFlag=3";
}
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

