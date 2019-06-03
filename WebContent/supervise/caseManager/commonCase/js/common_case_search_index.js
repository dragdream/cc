//权限组
var menuNames = $('#common_case_search_index_menuGroupStrNames').val();
/**
 * 页面初始化函数
 * @returns
 */
function doInitSearchIndex() {
	dateValidate('beginpunishmentDateStr', 'endpunishmentDateStr');
	doInitEnter();//响应回车
    //commonCaseRefreshIndex();//重置查询条件
    var params = {
        menuNames: menuNames,
        isSubmit: 1
    }
    initDatagrid(params);
//    var url = '/caseCommonBaseCtrl/selectSubjectList.action?menuNames='+menuNames;
//    var loaderUrl = '/caseCommonBaseCtrl/selectSubjectList.action';
//    getSelectSearch('subjectId',loaderUrl, params);//首页主体查询条件获取
    //initSubject();
    initCodeListSelect("COMMON_CLOSED_STATE", "closedState");// 初始化结案状态
}

function initClosedState(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "COMMON_CLOSED_STATE"});
    if(json.rtState) {
        $('#closedState').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
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
 * 表格加载函数
 * @returns
 */
function initDatagrid(params){
    datagrid = $('#common_case_search_index_datagrid').datagrid({
        url: contextPath + '/caseCommonBaseCtrl/findSearchListBypage.action',
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
			{field: 'filingDateStr', title: '立案批准日期', width: 10, halign: 'center', align: 'center'},
			{field: 'punishmentDateStr', title: '处罚决定日期', width: 10, halign: 'center', align: 'center'},
			{
			    field: 'punishmentCode', title: '处罚决定书文号', width: 15, halign: 'center', align: 'left',
			    formatter: function(e, rowData) {
			        var punishmentCode = rowData.punishmentCode
			        if(punishmentCode == null || punishmentCode == 'null') {
			            punishmentCode = "";
			        }
			        var lins = "<label class='common-overflow-hidden common-table-td-full-width' title='"+punishmentCode+"'><a onclick='commonCaseLook(\"" + rowData.id + "\")' href='javaScript:void(0);'>" + punishmentCode + "</a></label>"
			        return lins;
			    }
			},
			{field: 'partyType', title: '当事人类型', width: 10, halign: 'center', align: 'center'},
			{field: 'partyName', title: '当事人名称', width: 10, halign: 'center', align: 'center'},
			{field: 'closedState', title: '结案状态', width: 12, halign: 'center', align: 'center'},
			{field: 'createTimeStr', title: '入库日期', width: 10, halign: 'center', align: 'center'},
            {
                field: '___',
                title: '操作' , halign: 'center', align: 'center',
                formatter: function(e, rowData) {
                    var optStr = "<span title='查看'><a href='javaScript:void(0);' onclick='commonCaseLook(\"" + rowData.id + "\")'><i class='fa fa fa-eye'></i></a></span>";
                    return optStr;
                },
                width: 10
            }
        ]]
    });
}

/**
 * 重置查询条件
 * @returns
 */
function commonCaseRefreshIndex() {
    $('#subjectId').combobox('setValue', null);
    $('#isSubmit').combobox('setValue', null);
    $('#name').textbox('setValue', '');
    $('#punishmentCode').textbox('setValue', '');
    $('#createStartDateStr').datebox('setValue', '');
    $('#createEndDateStr').datebox('setValue', '');
}


/**
 * 获取回车事件
 * @returns
 */
function doInitEnter() {
    document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
            commonCaseSearchIndex();
        }
    }
}


/**
 * 查询方法
 * @returns
 */
/*function commonCaseSearchIndex(){
    var subjectId = $.trim($('#subjectId').combobox('getValue'));
    var name = $.trim($('#name').val());
    var punishmentCode = $.trim($('#punishmentCode').val());
    var createStartDateStr = $.trim($('#createStartDateStr').val());
    var createEndDateStr = $.trim($('#createEndDateStr').val());
    var param = {
        subjectId: subjectId,
        name: name,
        punishmentCode: punishmentCode,
        createStartDateStr: createStartDateStr,
        createEndDateStr: createEndDateStr,
        menuNames: menuNames
    }
    $('#common_case_search_index_datagrid').datagrid('reload', param);
}*/
function commonCaseSearchIndex(){
    if($('#common_case_search_form').form('enableValidation').form('validate')){
        var param = tools.formToJson("#common_case_search_form");
        $('#common_case_search_index_datagrid').datagrid('reload', param);
    }
}

/**
 * 案件信息查看方法
 * @returns
 */
/*function commonCaseLook(id)
{
    // 获取案件ID
    window.location.href = contextPath+"/caseCommonBaseCtrl/commonCaseAdd.action?id=" + id + "&editFlag=3";
}
*/
function commonCaseLook(id) {
	location.href = "/supervise/caseManager/commonCase/common_case_index_look.jsp?caseId="+ id;
	/*var params = {
            caseId: id,
            pageUrl: '/supervise/caseManager/commonCase/common_case_index_look.jsp'
    }
    var url = contextPath+"/caseCommonBaseCtrl/commonCaseLook.action?"+ $.param(params);
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
                    if(h != null && h != ''){
                        var cw = h[0].contentWindow;
                        if(v=="关闭"){
                            return true;
                        }
                    }else{
                        return true;
                    }
                    commonCaseSearch();
                }
            }
     );*/
}
