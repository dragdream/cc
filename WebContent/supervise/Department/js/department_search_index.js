/*初始化*/
var datagrid;
function doInit() {
	natureInit();
	// 遍历查询条件：部门性质
	getSysCodeByParentCodeNo("DEPT_NATURE","nature");
	listDatagrid();
	/**
	 * 获取回车事件
	 * @returns
	 */
	    document.onkeyup = function (e) {
	        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
	        if (code == 13) {
	        	queryDepartment();
	        }
	    }
}

function listDatagrid(){
	datagrid = $('#datagrid') .datagrid( {
		url : contextPath + '/departmentSearchController/findListByPageRoles.action',
		queryParams : { gender : -1 },
		pagination : true,
		pageSize : 20,
        pageList : [ 10, 20, 50, 100 ],
		singleSelect : true,
		striped: true,
		view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar : '#toolbar',// 工具条对象
		border : false,
		rownumbers : false,
		fit : true,
		idField : 'id',// 主键列
		fitColumns : true,// 列是否进行自动宽度适应
		columns : [ [
				{field:'ID',title:'序号',align:'center',
                    formatter:function(value,rowData,rowIndex){
                        return rowIndex+1;
                    }
                },
				{ field : 'name', title : '机关全称', width : 100,align:'left' , halign: 'center',
					formatter:function(value,rowData,rowIndex){
						var optsStr = "<a href=\"#\" title=" + value + " onclick=\"look('" + rowData.id + "')\">" + value + "</a>";
						return optsStr;
					} 
				},
				{ field : 'deptLevel', title : '机关层级', width : 50,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            }, },
				{ field : 'nature', title : '机关性质', width : 50,align:'center' , halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
				{ field : 'orgSys', title : '所属领域', width : 50,align:'center' , halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
				{ field : 'isManubrium', title : '是否垂管', width : '8%',align:'center' , halign: 'center' , formatter : 
					function(data, rowData) {
						if (rowData.isManubrium == 1) {
							return "是";
						} else {
							return "否";
						}
					}
				},
//				{ field : 'userName', title : '账号', width : '12%',align:'left' , halign: 'center' , formatter: 
//					function(value,rowData,rowIndex){
//					if(value == null){
//						return "";
//					}else{
//						var optsStr = "<span title=" + value + ">" + value + "</span>";
//						return optsStr;
//					}
//		            },},
//				{ field : 'isExamine', title : '审核状态', width : '8%',align:'center' , halign: 'center' , formatter : 
//					function(data, rowData) {
//						if (rowData.isExamine == 1) {
//							return "已审核";
//						} else {
//							return "未审核";
//						}
//					}
//				},
				{ field : '___', title : '操作', width : '6%', align:'center' , halign: 'center',formatter : function(e, rowData) {
					var optsStr = "<span title='查看'><a href='javaScript:void(0);' onclick='look(\"" + rowData.id + "\")'><i class='fa fa-eye'></i></a></span>";
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
 * 修改
 */
function look(id) {
	top.bsWindow(contextPath + "/supervise/Department/department_search_look.jsp?id="+id, "查看",
			{
				width : "930",
				height : "450",
				buttons : [ {
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
 * 查询
 */
function queryDepartment() {	
	var params = {
			name:$("#name").val(),
			nature:$("#nature").val(),
			isManubrium:$("#isManubrium").val(),
			isExamine:$("#isExamine").val()
		};
	$('#datagrid').datagrid("reload",params);
	$('#datagrid').datagrid("clearSelections");
}
/**
 * 机构性质
 */
function natureInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "DEPT_NATURE"});
	if(json.rtState) {
		json.rtData.unshift({codeNo: -1, codeName: "全部"});
		$('#nature').combobox({
			data: json.rtData,
			valueField: 'codeNo',
			textField: 'codeName',
			panelHeight:'150px',
			onLoadSuccess:function(){
				$('#nature').combobox('setValue',-1);
			},
		});
	}
}
