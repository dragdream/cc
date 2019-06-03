/*初始化*/
var datagrid;
function doInit() {
	//撤销原因
	revockreasonIdInit();
	listDatagrid();
	document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
        	queryS();
        }
    }
}
//初始化表格
function listDatagrid(){
	datagrid = $('#datagrid') .datagrid( {
		url : contextPath + '/casecheckPersonCtrl/listBypage.action',
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
				{ field : 'name', title : '姓名', width:'10%',align:'left' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
				},},
				{ field : 'sex', title : '性别', width :20 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						if(value == '01'){
							value = '男';
						}else{
							value = '女';
						}
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
	            },},
				{ field : 'organizationName', title : '所属部门', width :'10%',align:'center' , halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
				},},
				{ field : 'position', title : '职级', width : '10%',align:'center' , halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
				},},
				{ field : 'personType', title : '人员类型', width :50 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
	            },},
	            { field : 'phoneNumber', title : '移动电话', width :50 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
	            },},
	            { field : 'confirmDateStr', title : '确认日期', width :50 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
	            },},
	            { field : 'checkcount', title : '参评次数', width :30 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						if(value == 0){
							var optsStr = value;
						}else{
		            		var optsStr = "<a href=\"#\" title=" + value + " onclick=\"checkCount('" + rowData.id + "')\">" + value + "</a>";
						}
						return optsStr;
					}
	            },},
	            { field : 'revockDateStr', title : '撤销日期', width :50 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
	            },},
	            { field : 'revockreasonId', title : '撤销原因', width :50 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
	            },},
				{ field : '___', title : '操作', width : '8%',align:'center' , halign: 'center', formatter : function(e, rowData) {
					if(rowData.isRevock == '1'){
						
					}else if(rowData.sourceType == '03'){
						var optsStr = "<span title='修改'><a href='javaScript:void(0);' onclick='edit(\"" + rowData.id + "\")'><i class='fa fa-pencil common-yellow'></i></a></span>" +
						"&nbsp;&nbsp;<span title='撤销'><a href='javaScript:void(0);' onclick='cdel(\"" + rowData.id + "\")' ><i class='fa fa-trash-o common-red'></i></a></span>";
						return optsStr;
					}else{
						var optsStr = "<span title='撤销'><a href='javaScript:void(0);' onclick='cdel(\"" + rowData.id + "\")' ><i class='fa fa-trash-o common-red'></i></a></span>";
						return optsStr;
					}
					}},
			] ],
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

//撤销原因
function revockreasonIdInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "REVOCKREASON"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: -1, codeName: "全部"});
        $('#revockreasonId').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'auto',
            onLoadSuccess:function(){
                $('#revockreasonId').combobox('setValue',-1);
            },
        });
    }
}
/*
 * 确定评查人员
 */
function add() {
	top.bsWindow(contextPath + "/supervise/caseCheck/person/person_add.jsp", "确定评查人员", {
		width : "1200",
		height : "500",
		buttons : [ {
			name : "关闭",
			classStyle : "btn-alert-gray"
		} ,{
			name : "保存",
			classStyle : "btn-alert-blue"
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
 * 确定其他评查人员
 */
function addOther() {
	top.bsWindow(contextPath + "/supervise/caseCheck/person/person_addOther.jsp", "确定其他评查人员", {
		width : "600",
		height : "150",
		buttons : [ {
			name : "关闭",
			classStyle : "btn-alert-gray"
		} ,{
			name : "保存",
			classStyle : "btn-alert-blue"
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
//撤销
function cdel(rid) {
	top.bsWindow(contextPath + "/supervise/caseCheck/person/person_revoke.jsp?id="+rid, "撤销", {
		width : "400",
		height : "150",
		buttons : [ {
			name : "关闭",
			classStyle : "btn-alert-gray"
		} ,{
			name : "保存",
			classStyle : "btn-alert-blue"
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
//修改
function edit(rid) {
	top.bsWindow(contextPath + "/supervise/caseCheck/person/person_addOther.jsp?id="+rid, "修改", {
		width : "600",
		height : "150",
		buttons : [ {
			name : "关闭",
			classStyle : "btn-alert-gray"
		} ,{
			name : "保存",
			classStyle : "btn-alert-blue"
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
	var queryState = true;
	var params = {
			organizationName : $("#organizationName").val(),
			name : $("#name").val(),
			checkcountBegin : $("#checkcountBegin").val(),
			checkcountEnd : $("#checkcountEnd").val(),
			isRevock : $("#isRevock").val(),
			revockreasonId : $("#revockreasonId").val()
		};
	if(params.revockreasonId == -1){
		params.revockreasonId = "";
	}
	//校验参评次数起
	if(params.checkcountBegin != "" || params.checkcountBegin != null){
		var text = /^[0-9]*$/g;
		var flag = text.test(params.checkcountBegin);
		if(!flag){
			$.MsgBox.Alert_auto("参评次数只能输入数字");
			queryState = false;
		}
	}
	//校验参评次数止
	if(params.checkcountEnd != "" || params.checkcountEnd != null){
		var text = /^[0-9]*$/g;
		var flag1 = text.test(params.checkcountEnd);
		if(!flag1){
			$.MsgBox.Alert_auto("参评次数只能输入数字");
			queryState = false;
		}
	}
	if(queryState){
		$('#datagrid').datagrid("reload", params);
		$('#datagrid').datagrid("clearSelections");
	}
	
}
//参评次数
function checkCount(checkCountId){
	top.bsWindow(contextPath + "/supervise/caseCheck/person/person_check.jsp?id="+checkCountId, "评查批次信息", {
		width : "600",
		height : "400",
		buttons : [ {
			name : "关闭",
			classStyle : "btn-alert-gray"
		} ,{
			name : "保存",
			classStyle : "btn-alert-blue"
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
//导出
function exportCasePerson(){
	top.$.MsgBox.Confirm("提示","确定导出所有数据？",function(){
		var json = tools.requestJsonRs("/casecheckPersonCtrl/export.action");
		if(json.rtData < 1001){
			location.href = '/casecheckPersonCtrl/export.action?isTrue=1';
		}else{
			alert("导出数据过大，请精确查询后再操作（导出数据限制：1000）");
		}
	});
}
