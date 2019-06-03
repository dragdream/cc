/*初始化*/
var datagrid;
function doInit() {
	natureInit();
	administrativeDivision();
	listDatagrid();
	document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
        	doSearch();
        }
    }
}

function listDatagrid(){
	datagrid = $('#datagrid') .datagrid( {
		url : contextPath + '/SuperviseController/findSearchListBypage.action',
		queryParams : { gender : -1 },
		pagination : true,
		singleSelect : true,
		pageSize : 20,
        pageList : [ 10, 20, 50, 100 ],
		view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar : '#toolbar',// 工具条对象
		border : false,
		striped: true,
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
				{ field : 'name', title : '机关全称', width : 50,align:'left' ,width:'30%', halign: 'center', formatter: 
					function(e, rowData) {
					var optsStr = "<a href=\"#\" title=" + e + " onclick=\"edit('" + rowData.id + "')\">" + e + "</a>";
					return optsStr;
	            }, },
	            { field : 'departmentCode', title : '统一社会信用代码', width : '16%',align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            }, },
				{ field : 'administrativeDivision', title : '行政区划', width : 50,align:'center' , halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
				{ field : 'deptLevel', title : '所属层级', width : 50,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            }, },
	            { field : 'nature', title : '机关性质', width : '10%',align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            }, },
//				{ field : 'isExamine', title : '审核状态', width : 50,align:'center' , halign: 'center' , formatter : 
//					function(data, rowData) {
//						if (rowData.isExamine == 1) {
//							return "已审核";
//						} else {
//							return "未审核";
//						}
//					}
//				},
				{ field : '___', title : '操作', width : 40, align:'center' , halign: 'center',formatter : function(e, rowData) {
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
 * 新增
 */
function add() {

	top.bsWindow(contextPath + "/supervise/supervise/supervise_add.jsp", "新增", {
		width : "690",
		height : "190",
		buttons : [ {
			name : "保存",
			classStyle : "btn-alert-blue"
		}, {
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

function administrativeDivision(){
	$('#administrativeDivision').combobox({
		prompt:'输入关键字后自动搜索',
		mode:'remote',
		url:contextPath + '/subjectCtrl/getSysCodeById.action',
		valueField:'ID',
		textField:'NAME',
		multiple:false,
		method:'post',
		panelHeight:'100px',
		//label: 'Language:',
		labelPosition: 'top',
		onClick:function(row){
			ComboboxCommonProcess($(this));
		},
		onHidePanel:function(){
			var _options = $(this).combobox('options');
		    var _data = $(this).combobox('getData');/* 下拉框所有选项 */
		    var _value = $(this).combobox('getValue');/* 用户输入的值 */
		    var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
		    for (var i = 0; i < _data.length; i++) {
		        if (_data[i][_options.valueField] == _value) {
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

//通用的combobox处理方法
function ComboboxCommonProcess(obj){
	var values = $(obj).combobox("getValues");
	var getData = $(obj).combobox("getData");
	var valuesT = [];
	
	for(var i=0;i<values.length;i++){
		for(var ii=0;ii<getData.length;ii++){
			if(values[i]==getData[ii].id){
				valuesT.push(values[i]);
				break;
			}
		}
	}
	$(obj).combobox("setValues",valuesT);
}

/*
 * 修改
 */
function edit(id) {
	top.bsWindow(contextPath + "/supervise/supervise/supervise_search_look.jsp?id="+id, "查看",
			{
				width : "800",
				height : "370",
				buttons : [{
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
 * 多删
 */
function deletes() {
//	debugger;
	var checkedItems = $('#datagrid').datagrid('getChecked');
	if (checkedItems == ""){
		$.MsgBox.Alert_auto("请至少选择一条数据");
	}else{
		var ids = [];
		$.each(checkedItems, function(index, item) {
			ids.push(item.id);
		});
		if (window.confirm("是否确认删除该数据")) {
			var json = tools.requestJsonRs("/SuperviseController/deletes.action",{ids:ids.join(",")});
			$('#datagrid').datagrid("reload");
			$('#datagrid').datagrid("clearSelections");
		}
	}
	
}

/** 审核
 * 0 改 1
*      */
function  auditing(){
	var obj = $('#datagrid').datagrid("getSelections");
	if (obj ==""){
		$.MsgBox.Alert_auto("请先选择一条数据");
	}else{
		if (obj[0].examine==1){
			$.MsgBox.Alert_auto("您已审核该数据");
		}else{
			if (window.confirm("是否确认审核")) {
				var json = tools.requestJsonRs("/SuperviseController/examine.action",{id:obj[0].id});
				$('#datagrid').datagrid("reload");
				$('#datagrid').datagrid("clearSelections");
				}	
			}
	}
}
	
/** 取消审核
 * 1改0
*      */
function  RemoveAuditing(){
	var obj = $('#datagrid').datagrid("getSelections");
	if(obj == ""){
		$.MsgBox.Alert_auto("请先选择一条数据");
	}else{
		if (obj[0].examine==0){
			$.MsgBox.Alert_auto("该数据已在取消审核状态");
		}else{
			if (window.confirm("是否取消审核")) {
				var json = tools.requestJsonRs("/SuperviseController/examine.action",{id:obj[0].id});
				$('#datagrid').datagrid("reload");
				$('#datagrid').datagrid("clearSelections");
		}
		}
	}
	

}

/* 超链接删除 */
function cdel(id) {
	if (window.confirm("是否确认删除该数据")) {
		var json = tools.requestJsonRs(
				"/SuperviseController/delete.action", {
					id : id
				});
		$('#datagrid').datagrid("reload");
		$('#datagrid').datagrid("clearSelections");
	}
}

/*
 * * 查询
 */
function doSearch() {	
	var params = {name:$("#name").val(),
				  nature:$("#nature").val(),
				  departmentCode:$("#departmentCode").val(),
				  representative:$("#representative").val()};
	if(params.nature == -1){
		params.nature = "";
	}
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
            panelHeight:'auto',
            onLoadSuccess:function(){
                $('#nature').combobox('setValue',-1);
            },
        });
    }
}

