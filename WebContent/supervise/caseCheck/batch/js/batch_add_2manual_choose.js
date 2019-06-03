var batchId = $("#batchId").val();// 批次ID
var area = $("#area").val();// 所属区域
var caseType = $("#caseType").val();// 案卷类型
var missionId = $("#missionId").val();// 任务ID

/**
 * 页面初始化
 */
function doInit(){
	inputValidate();// 数据校验
	initCondition();// 初始化查询条件
	var params = {
		missionId : missionId
	};
	initIndexDatagrid(params);// 列表初始化
}

/**
 * 数据校验
 */
function inputValidate(){
	
}

/**
 * 初始化查询条件
 */
function initCondition(){
	if('01' == caseType){// 行政检查
		$("#inspectionTable").show();
		$("#coercionTable").hide();
		$("#punishTable").hide();
	}else if('02' == caseType){// 行政强制
		$("#coercionTable").show();
		$("#inspectionTable").hide();
		$("#punishTable").hide();
	}else if('03' == caseType){// 行政处罚
		$("#punishTable").show();
		$("#inspectionTable").hide();
		$("#coercionTable").hide();
	}
}

/**
 * 初始化列表
 * @param params
 */
function initIndexDatagrid(params){
    datagrid = $('#case_choose_datagrid').datagrid({
        url: contextPath + '/caseCommonBaseCtrl/findListBypage.action',
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
			        var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+punishmentCode+"'><a onclick='commonCaseLook(\"" + rowData.id + "\")' href='javaScript:void(0);'>" + punishmentCode + "</a></lable>"
			        return lins;
			    }
			},
			{field: 'partyType', title: '当事人类型', width: 10, halign: 'center', align: 'center'},
			{field: 'partyName', title: '当事人名称', width: 10, halign: 'center', align: 'center'},
			{field: 'closedState', title: '结案状态', width: 12, halign: 'center', align: 'center'},
			{field: 'createTimeStr', title: '入库日期', width: 10, halign: 'center', align: 'center'}
        ]]
    });
}

/**
 * 保存
 */
function save(){
	
}



