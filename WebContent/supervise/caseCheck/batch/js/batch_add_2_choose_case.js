var missionId = $("#missionId").val();
var caseType = $("#caseType").val();
var isSaved = false;

/**
 * 页面初始化
 */
function doInit(){
	var params = {
		menuNameStr : ''
	}
	initIndexDatagrid(params);
}

/**
 * 智能抽选
 */
function autoChoose(){
	var url=contextPath+"/supervise/caseCheck/batch/batch_add_2auto_choose.jsp";
	top.bsWindow(url ,"智能抽选",{
		width:"400",
		height:"150",
		buttons: [
            {name:"关闭",classStyle:"btn-alert-gray"},
            {name:"确认",classStyle:"btn-alert-blue"}
        ],
        submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "确认") {
            	var status = cw.save();
                if (status != false) {
                	alert(cw.save());
                    return true;
                }
            } else if(v=="关闭"){
                return true;
            }
        }
    });
}

/**
 * 手动抽卷
 */
function manualChoose(){
	var url=contextPath+"/supervise/caseCheck/batch/batch_add_2manual_choose.jsp?missionId=" + missionId + "&caseType=" + caseType;
	top.bsWindow(url ,"手动抽卷",{
		width:"1100",
		height:"550",
		buttons: [
            {name:"添加案卷",classStyle:"btn-alert-blue"}
        ],
        submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "添加案卷") {
            	var status = cw.save();
                if (status != false) {
                	alert(cw.save());
                    return true;
                }
            }
        }
    });
}

/**
 * 导出案卷抽取统计
 */
function exportAnalysis(){
	
}

/**
 * 导出案卷目录
 */
function exportCase(){
	
}

/**
 * 表格加载函数
 * @returns
 */
function initIndexDatagrid(params){
    datagrid = $('#common_case_index_datagrid').datagrid({
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
			{field: 'name', title: '案卷名称', width: 10, halign: 'center', align: 'center'},
			{field: 'powerCode', title: '职权编号', width: 10, halign: 'center', align: 'center'},
			{
			    field: 'caseType', title: '案卷类型', width: 15, halign: 'center', align: 'left',
			    formatter: function(e, rowData) {
			        var punishmentCode = rowData.punishmentCode
			        if(punishmentCode == null || punishmentCode == 'null') {
			            punishmentCode = "";
			        }
			        var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+punishmentCode+"'><a onclick='commonCaseLook(\"" + rowData.id + "\")' href='javaScript:void(0);'>" + punishmentCode + "</a></lable>"
			        return lins;
			    }
			},
			{field: 'departmentName', title: '所属部门', width: 10, halign: 'center', align: 'center'},
			{field: 'filingDateStr', title: '立案日期', width: 10, halign: 'center', align: 'center'},
			{field: 'closedDateStr', title: '结案日期', width: 12, halign: 'center', align: 'center'},
            {
                field: '___',
                title: '操作' , halign: 'center', align: 'center',
                formatter: function(e, rowData) {
                    var updatePage = "<span title='修改'><a href='javaScript:void(0);' onclick='commonCaseEdit(\"" + rowData.id + "\")'><i class='fa fa-pencil common-yellow'></i></a></span>";
                    var deletePage = "<span title='删除'><a href='javaScript:void(0);' onclick='commonCaseDelete(\"" + rowData.id + "\")' ><i class='fa fa-trash-o common-red'></i></a></span>";
                    var lookPage = "<span title='查看'><a href='javaScript:void(0);' onclick='commonCaseLook(\"" + rowData.id + "\")'><i class='fa fa fa-eye'></i></a></span>";
                    var optStr = "";
                    /*if(parseInt(rowData.isSubmit) == 1){
                        optStr = lookPage;
                    }else if(parseInt(rowData.isSubmit) == 0 && menuNameStr.indexOf('填报人员') != -1){
                        optStr = updatePage + "&nbsp;&nbsp;" +deletePage;
                    }else if(rowData.isSubmit == null || rowData.isSubmit == ''){
                        optStr = lookPage;
                    }*/
                    return deletePage;
                },
                width: 7
            }
        ]]
    });
}

function save(){
	if($('#form1').form('enableValidation').form('validate')){
		var param = tools.formToJson($("#form1"));
		var json = tools.requestJsonRs("/jdCasecheckBatchTempCtrl/save.action",param);
		isSaved = json.rtState;
	    return json.rtState;
	}
}

/**
 * 返回
 */
function back(){
	top.$.MsgBox.Confirm("提示","离开后未保存数据将丢失，确定返回？",function(){
		location.href = "/supervise/caseCheck/batch/batch_index.jsp";
    })
}

/**
 * 上一步
 */
function before(){
	beforeStep(1,missionId);
}

/**
 * 下一步
 */
function next(){
	if(isSaved){
		nextStep(1);
	}else{
		$.MsgBox.Alert_auto("请先进行保存");
	}
}
