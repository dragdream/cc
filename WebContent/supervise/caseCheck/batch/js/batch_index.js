/*初始化*/
var datagrid;
function doInit() {
	listDatagrid();
//	document.onkeyup = function (e) {
//        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
//        if (code == 13) {
//        	queryS();
//        }
//    }
}
//初始化表格
function listDatagrid(){
	datagrid = $('#datagrid') .datagrid( {
		url : contextPath + '/jdCasecheckMissionTempCtrl/ListBypage.action',
		queryParams : { gender : -1 },
		pagination : true,
		singleSelect : true,
		striped: true,
		pageSize : 20,
        pageList : [ 10, 20, 50, 100 ],
		view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar : '#toolbar',// 工具条对象
		border : false,
		rownumbers : false,
		fit : true,
		idField : 'id',// 主键列
		fitColumns : true,// 列是否进行自动宽度适应
		columns : [ [
				{ field : 'id', title : '', checkbox : true},
				{field:'ID',title:'序号',align:'center',
                    formatter:function(value,rowData,rowIndex){
                        return rowIndex+1;
                    }
                },
				{ field : 'name', title : '任务名称', width:'20%',align:'left' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
				},},
				{ field : 'caseTypeValue', title : '案卷类型', width :'16%',align:'center' , halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
				},},
				{ field : 'createName', title : '创建人员', width : '20%',align:'center' , halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
				},},
				{ field : 'createDateStr', title : '入库时间', width :50 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
	            },},
				{ field : '___', title : '操作', width : '8%',align:'center' , halign: 'center', formatter : function(e, rowData) {
						var optsStr = "<span title='编辑'><a href='javaScript:void(0);' onclick='edit(\"" + rowData.id + "\")'><i class='fa fa-pencil common-yellow'></i></a></span>" +
						"&nbsp;&nbsp;<span title='下载'><a href='javaScript:void(0);' onclick='legalDownloads(\"" + rowData.id + "\")'><i class='fa fa-download'></i></a></span>" +
								"&nbsp;&nbsp;<span title='删除'><a href='javaScript:void(0);' onclick='cdel(\"" + rowData.id + "\")' ><i class='fa fa-trash-o common-red'></i></a></span>";
						return optsStr;
					}
				}, ] ],
		singleSelect : false,
		selectOnCheck : true,
		checkOnSelect : true,
		onLoadSuccess : function(data, rowData) {
			if (data) {
				$.each(data.rows, function(index, item) {
					if (item.checked) {
						$('#datagrid').datagrid('checkRow',index);
					}
				});
			}
		}

	});
}

/*
 * 新增
 */
function add() {
	location.href = "/jdCasecheckMissionTempCtrl/batchTempAdd.action?editFlag=1";
}

/*
 * 编辑
 */
function edit(id) {
	location.href = "/jdCasecheckMissionTempCtrl/batchTempAdd.action?editFlag=2&id="+id;
}

/* 超链接删除 */
function cdel(id) {
	top.$.MsgBox.Confirm("提示","确定删除该数据？",function(){
		var json = tools.requestJsonRs("/casecheckMessageCtrl/delete.action", {id : id});
		if(json.rtState){
			$('#datagrid').datagrid("reload");
			$('#datagrid').datagrid("clearSelections");
		}else{
			$.MsgBox.Alert_auto("删除失败");
		}
	});
}
/*
 * * 查询
 */
function queryS() {	
	var params = {
		name : $("#name").val(),
		deptName : $("#deptName").val(),
		messageType : $("#messageType").val()
	};
	if(params.messageType == -1){
		params.messageType = "";
	}
	$('#datagrid').datagrid("reload", params);
	$('#datagrid').datagrid("clearSelections");
}

/*
 * 抽卷
 */
function choose() {
	location.href = contextPath + "/supervise/caseCheck/batch/batch_add_2_choose_case.jsp";
}
