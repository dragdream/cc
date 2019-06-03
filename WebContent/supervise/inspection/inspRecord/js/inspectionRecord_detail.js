/**
 * 
 */
var personIds = []; //定义人员ID对象
var personJson = [];//定义人员对象
var ctrlType = '';
var loginDeptId = '';
var loginSubId = '';
var recordId = '';
var orgSys = '';
var inspListId = '';

var inspItems = [];
var saveingFlag = false;
var datagrid = null;

function doInit() {
    initParams();
}
function initParams() {
    recordId = $('#recordId').val();
    var json = tools.requestJsonRs("/inspListRecordCtrl/getById.action", {id : recordId});
    /** 初始化数据 */
    if (json.rtState) {
        bindJsonObj2Cntrl(json.rtData);
        //初始化执法主体
        if(json.rtData.subjectId!=null&&json.rtData.subjectId!=''){
        	var subject = tools.requestJsonRs("/subjectCtrl/get.action", {id : json.rtData.subjectId});
        	if(subject.rtState==true){
        		$('#subjectName').text(subject.rtData.subName);
        	}
        }
        //初始化检查结果
        if(json.rtData.isInspectionPass==1){
            $('#isInspectionPass').text("合格");
        } else if(json.rtData.isInspectionPass==2){
            $('#isInspectionPass').text("不合格");
        } else{
            $('#isInspectionPass').text("");
        }
    }
    //初始化检查单模版
    var modelJson = tools.requestJsonRs("/inspListBaseCtrl/getById.action", {id : json.rtData.inspectionListId});
    if(modelJson.rtState){
        $('#inspListModel').text(modelJson.rtData.listName);
    }
    
    var params = [];
    if(json.rtData.inspItems!=null && json.rtData.inspItems!=''){
        params = json.rtData.inspItems;
        if (inspItems.length == 0) {
            for (var index = 0; index< params.length; index++) {
                inspItems.push({
                    id : params[index].id,
                    inspItemName : params[index].inspItemName,
                    isInspectionPass : params[index].isInspectionPass
                });
            }
            inspItemsTemp = inspItems;
        }
    }
    
    initItemsDatagrid(inspItems);

    //执法人员信息加载
    var personJsonStr = json.rtData.personJsonStr;
    var personJson;
    var param;
    if(personJsonStr != null && personJsonStr != '' && personJsonStr != 'null'){
        personJson = personJsonStr.split(",");
        for (var i = 0; i < personJson.length; i++){
            personIds.push(personJson[i]);
        }
        param = {ids: personIds.join(',')};
        if(personIds == null || personIds.length < 1){
            param = {ids: 'empty'};
        }
    }else{
        param = {ids: 'empty'};
    }
    initPartyTypeInput(json.rtData.partyType);//加载当事人
    initCardTypeInput(json.rtData.partyType + "" + json.rtData.cardType);// 当事人证件类型
    initAddPersonDatagrid(param);//修改人员加载数据
}


/**
 * 初始化行政相对人类型
 */
function initPartyTypeInput(value) {
    var params = {
            parentCodeNo: "PERMISSION_PARTY_TYPE",
            codeNo: value
        }
    var json = tools.requestJsonRs("/sysCode/getSysCodeNameByParentCodeNo.action", params);
    if(json.rtState) {
        $('#partyType').text(json.rtData);
    }
}

/**
 * 初始化证件类型PERMISSION_CARD_TYPE
 */
function initCardTypeInput(value) {
    var params = {
            parentCodeNo: "PERMISSION_CARD_TYPE",
            codeNo: value
        }
    var json = tools.requestJsonRs("/sysCode/getSysCodeNameByParentCodeNo.action", params);
    if(json.rtState) {
        $('#cardType').text(json.rtData);
    }
}

/**
 * 表格加载函数
 * @returns
 */
function initAddPersonDatagrid(params){
    datagrid = $('#insp_add_person_datagrid').datagrid({
        url: contextPath + '/caseCommonStaffCtrl/findListByPersonIds.action',
        queryParams: params,
        pagination: false,
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
//        toolbar: '#toolbar', // 工具条对象
        checkbox : false,
        border: false,
        striped: true,//隔行变色
        /* idField:'formId',//主键列 */
        fitColumns: true, // 列是否进行自动宽度适应
//        singleSelect: true, //为true只能选择一行
        nowrap: true,
        checkOnSelect : false,
        onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
        },
        columns: [[
            { 
                field: 'id', checkbox: true, title: "ID", width: 20, halign: 'center', align: 'center', hidden: true
            },
            {field:'ID',title:'序号',align:'center', width: 10,
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field: 'subjectName', title: '所属主体', width:40, halign: 'center', align: 'left',
                formatter: function(value, rowData) {
                    if(value == null || value == 'null') {
                        value = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</lable>"
                    return lins;
                }
            },
            {
                field: 'name', title: '执法人员 ', width: 24, halign: 'center', align: 'center'
            },
            {
                field: 'enforcerCode', title: '执法证号 ', width: 24, halign: 'center', align: 'center'
            }
        ]]
    });
}


function initItemsDatagrid(params) {
    // inspItems = params;
    editIndex = null;
    datagrid = $('#record_items_table')
            .datagrid(
                    { 
                        data : params,
                        pagination : false,
                        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
                        checkbox : false,
                        border : false,
                        striped: true,//隔行变色
                        fitColumns : true, // 列是否进行自动宽度适应
//                        singleSelect : true, // 为true只能选择一行
                        checkOnSelect : false,
                        pageSize : 20,
                        pageList : [ 10, 20, 50, 100 ],
                        fit : false,
                        nowrap : true,
                        onClickRow: function (rowIndex, rowData) {
                            $(this).datagrid('unselectRow', rowIndex);
                        },
                        columns : [ [
                                { 
                                    field: 'id', checkbox: true, title: "ID", width: 20, halign: 'center', align: 'center', hidden: true
                                },
                                {    field:'ID',title:'序号',align:'center', width: 10,
                                    formatter:function(value,rowData,rowIndex){
                                        return rowIndex+1;
                                    }
                                },
                                {
                                    field : 'inspItemName',
                                    title : '检查项名称',
                                    width : 60,
                                    halign : 'center',
                                    formatter : function(value, rowData,
                                            rowIndex) {
                                        return '<lable class="custom-text-overflow table-td-full-width" title="'
                                                + value
                                                + '">'
                                                + value
                                                + '</lable>';
                                    }
                                },
                                {
                                    field : 'isInspectionPass',
                                    title : '检查结果',
                                    width : 30,
                                    align : 'center',
                                    formatter : function(value, rowData,
                                            rowIndex) {
                                        var optStr = ""    ;
                                        if (value == 1) {
                                            optStr = "合格";
                                        }else if ( value == 2) {
                                            optStr = "不合格";
                                        }
                                        return optStr;
                                    }
                                }] ]
                    });

}
