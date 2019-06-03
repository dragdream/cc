/*初始化*/
var datagrid;
function doInit() {
	//人员性质
	personTypeInit();
	//单独获取所属主体
	var json = tools.requestJsonRs("/OfficialsCtrl/getOrgSystemByCurrentPerson.action");
	var subLevel = json.rtData.businessSubName;
	listDatagrid();
	document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
        	queryS();
        }
    }
}
//人员性质
function personTypeInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "PERSON_NATURE"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: -1, codeName: "全部"});
        $('#personType').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'auto',
            onLoadSuccess:function(){
                $('#personType').combobox('setValue',-1);
            },
        });
    }
}
function listDatagrid(){
	datagrid = $('#datagrid') .datagrid( {
		url : contextPath + '/OfficialsCtrl/findSearchListBypageQuery.action',
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
//				{ field : 'id', title : '', checkbox : true},
				{field:'ID',title:'序号',align:'center',
                    formatter:function(value,rowData,rowIndex){
                        return rowIndex+1;
                    }
                },
                { field : 'name', title : '姓名', width:'8%',align:'left' , halign: 'center', formatter: 
					function(e,rowData){
            		var optsStr = "<a href=\"#\" title=" + e + " onclick=\"edit('" + rowData.id + "')\">" + e + "</a>";
					return optsStr;
	            }, },
				{ field : 'sex', title : '性别', width : '6%',align:'center' , halign: 'center' , formatter : 
					function(data, rowData) {
					if (rowData.sex == '01') {
						return "男";
					}else {
						return "女";
					}
				}},
				{ field : 'birthStr', title : '出生日期', width :'10%',align:'center' , halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
				},},
				{ field : 'personType', title : '人员性质', width :'16%',align:'center' , halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
				},},
				{ field : 'code', title : '执法证号', width : 50,align:'left' , halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
				},},
				{ field : 'businessSubName', title : '所属主体', width : '20%',align:'left' , halign: 'center', formatter: 
					function(e, rowData) {
						if(rowData.businessSubName == null){
							return "";
						}else{
							var lins = "<span title=" + rowData.businessSubName + ">" + rowData.businessSubName + "</span>"
			                return lins;
						}
	            },},
//				{ field : 'examine', title : '审核状态', width : '8%',align:'center' , halign: 'center' , formatter : 
//					function(data, rowData) {
//					if (rowData.examine == 0) {
//						return "未审核";
//					}else {
//						return "已审核";
//					}
//				}},
				{ field : '___', title : '操作', width : '6%',align:'center' , halign: 'center', formatter : function(e, rowData) {
						var optsStr = "<span title='查看'><a href='javaScript:void(0);' onclick='edit(\"" + rowData.id + "\")'><i class='fa fa-eye'></i></a></span>";
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
						$('#datagrid').datagrid('checkRow',
								index);
					}
				});
			}
		}

	});
}

/*
 * 修改
 */
function edit(id) {
	top.bsWindow(contextPath + "/supervise/officials/officials_search_look.jsp?id="+id, "查看",
			{
				width : "1000",
				height : "500",
				buttons : [ {
					name : "关闭",
					classStyle : "btn-alert-gray"
				} ],
				submit : function(v, h) {
					var cw = h[0].contentWindow;
					if (v == "保存") {
						var status = cw.save();
						if (status == true) {
							$("#datagrid").datagrid("reload");
							$('#datagrid').datagrid("clearSelections");
							return true;
						}
					} else if (v == "关闭") {
						return true;
					}
				}
			});

}

/*
 * * 查询
 */
function queryS() {	
	var params = {
			name : $("#name").val(),
			departmentCode : $("#departmentCode").val(),
			code : $("#code").val(),
			personId : $("#personId").val(),
			personType : $("#personType").val(),
			examine : $("#examine").val()
		};
		if(params.personType == -1){
			params.personType = "";
		}
		console.log(params);
		$('#datagrid').datagrid("reload", params);
		$('#datagrid').datagrid("clearSelections");
}


