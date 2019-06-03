/*初始化*/
var datagrid;
function doInit() {
	initsubmitlawLevel();
	getSysCodeByParentCodeNo("LAW_TYPE", "submitlawLevel");
	initDatagrid();
	initComboBox();
	enterKeydownForSearch();
	document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
        	search();
        }
    }
}
function initDatagrid(){
	datagrid = $('#datagrid')
	.datagrid(
			{
				url : contextPath
						+ '/lawInfoController/listByPage.action',
				queryParams : {
					gender : -1
				},
				pagination : true,
				singleSelect : true,
				striped : true,
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
						{
							field : 'name',
							title : '法律法规名称',
							width : 50,
							align : 'left',
							halign : 'center', formatter: 
								function(e,rowData){
								if(e == null){
									return "";
								}
			            		var optsStr = "<a href=\"#\" title=" + e + " onclick=\"look('" + rowData.id + "')\">" + e + "</a>";
				            	return optsStr;
				            }, 
						},
						{
							field : 'word',
							title : '发文字号',
							width : 50,
							align : 'left',
							halign : 'center', formatter: 
								function(value,rowData,rowIndex){
								if(value == null){
									return "";
								}else{
									var optsStr = "<span title=" + value + ">" + value + "</span>";
									return optsStr;
								}
					            },
						},
						{
							field : 'organ',
							title : '发布机关',
							width : 50,
							align : 'left',
							halign : 'center', formatter: 
								function(value,rowData,rowIndex){
								if(value == null){
									return "";
								}else{
									var optsStr = "<span title=" + value + ">" + value + "</span>";
									return optsStr;
								}
					            },
						},
						{
							field : 'timeliness',
							title : '时效性',
							width : 30,
							align : 'center', formatter: 
								function(value,rowData,rowIndex){
								if(value == null){
									return "";
								}else{
									var optsStr = "<span title=" + value + ">" + value + "</span>";
									return optsStr;
								}
					            },
						},
						{
							field : 'submitlawLevel',
							title : '效力级别',
							width : 30,
							align : 'center', formatter: 
								function(value,rowData,rowIndex){
								if(value == null){
									return "";
								}else{
									var optsStr = "<span title=" + value + ">" + value + "</span>";
									return optsStr;
								}
					            },
						},{
							field : 'createDateStr',
							title : '入库日期',
							width : 30,
							align : 'center', formatter: 
								function(value,rowData,rowIndex){
								if(value == null){
									return "";
								}else{
									var optsStr = "<span title=" + value + ">" + value + "</span>";
									return optsStr;
								}
					            },
						},{
							field : '__',
							title : '操作',
							width : 40,
							align : 'center',
							halign : 'center',
							formatter : function(e, rowData) {
//								if (rowData.examine == 1) {
									// var optsStr = "<a href=\"#\"
									// style=\"display:block;text-align:center\"
									// onclick=\"delc('"
									// + rowData.id +
									// "')\">删&nbsp;除</a>";
//								} else {
									var optsStr = "<span title='查看'><a href=\"#\"  onclick=\"look('"
											+ rowData.id
											+ "')\"><i class='fa fa-eye'></i></a></span>&nbsp;&nbsp;&nbsp;&nbsp;<span title='原文下载'><a href=\"#\" onclick=\"legalDownloads('"
											+ rowData.id
											+ "')\"><i class='fa fa-download common-blue'></i></a></span>";
//								}
								return optsStr;
							}
						}, ] ],
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

function initComboBox(){
	$('#submitlawLevel').combobox({
        panelHeight : 'auto'
	});
	$('#timeliness').combobox({
        panelHeight : 'auto'
	});
	$('#examine').combobox({
        panelHeight : 'auto'
	});
}

function initsubmitlawLevel() {
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action",
			{
				codeNo : "LAW_TYPE"
			});
	if (json.rtState) {
		json.rtData.unshift({
			codeNo : -1,
			codeName : "全部"
		});
		$('#submitlawLevel').combobox(
				{
					data : json.rtData,
					valueField : 'codeNo',
					textField : 'codeName',
					onLoadSuccess : function() {
						$('#submitlawLevel').combobox('setValue', -1);
					},
					onChange : function() {
						var powerType = $('#submitlawLevel').combobox(
								'getValue');
						if (powerType != "") {
							var params = {
								parentCodeNo : "LAW_TYPE",
								codeNo : powerType
							};
							var result = tools.requestJsonRs(
									"/sysCode/getSysParaByParentCode.action",
									params);
							if (result.rtState) {
								$('#powerDetail').combobox({
									data : result.rtData,
									valueField : 'codeNo',
									textField : 'codeName'
								});
							}
						}
					}
				});
	}
}
// 查询
function search() {
	var params = {
		name : $("#name").val(),
		timeliness : $("#timeliness").val(),
		submitlawLevel : $("#submitlawLevel").val(),
		examine : $("#examine").val()
	};
	console.log(params);
	$('#datagrid').datagrid("reload", params);
}

/* 导出excel */
function exportExcel() {

	var selections = $("#datagrid").datagrid("getSelections");
	if (selections.length == 0) {
		top.$.MsgBox.Confirm("提示", "是否确认导出所有查询结果？",
				function() {

					// 获取查询参数
					var params = tools.formToJson($("#form"));
					params["runIds"] = "0";
					if (flowTypeId > 0) {
						params["flowId"] = flowTypeId;
					}
					$("#iframe0").attr(
							"src",
							contextPath
									+ "/workQuery/exportExcel.action?params="
									+ encodeURIComponent(tools
											.jsonObj2String(params)));
				});
		if (confirm("是否确认导出所有查询结果？")) {
			// 导出所有的查询结果
			params["runIds"] = "0";
			$("#iframe0").attr(
					"src",
					contextPath + "/workQuery/exportExcel.action?params="
							+ tools.jsonObj2String(params));
		} else {
			return;
		}
	} else {
		var runIds = [];
		for (var i = 0; i < selections.length; i++) {
			runIds.push(selections[i].runId);
		}
		var params = tools.formToJson($("#form"));
		if (flowTypeId > 0) {
			params["flowId"] = flowTypeId;
		}
		params["runIds"] = runIds.join(",");
		// 导出所勾选的数据
		$("#iframe0").attr(
				"src",
				contextPath + "/workQuery/exportExcel.action?params="
						+ encodeURIComponent(tools.jsonObj2String(params)));
	}
}

function lawadd() {

	top.bsWindow(contextPath + "/supervise/lawList/lawAdd.jsp", "新增", {
		width : "600",
		height : "550",
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
					return true;
				}
			} else if (v == "关闭") {
				return true;
			}
		}
	});

}

//查询法律详情和内容
function look(id){
	top.bsWindow(contextPath + "/supervise/lawList/law_look.jsp?id="+id, "查看", {
		width : "850",
		height : "370",
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
					return true;
				}
			} else if (v == "关闭") {
				return true;
			}
		}
	});
}

/*
 * 内容管理
 */
function editn(id) {
	// 已审核
	// console.log(obj[0].examineStr);
	top.bsWindow(
			contextPath + "/supervise/lawList/law_contentRes.jsp?id=" + id,
			"内容查看", {
				width : "850",
				height : "450",
				buttons : [
//				            {
//					name : "关闭",
//					classStyle : "btn-alert-gray"
//				}
				            ],
				submit : function(v, h) {
					var cw = h[0].contentWindow;
					if (v == "保存") {
						var status = cw.save();
						if (status == true) {
							$("#datagrid").datagrid("reload");
							return true;
						}
					} else if (v == "关闭") {
						return true;
					}
				}
			});
}

/**
 * 法律下载
 */
function legalDownloads(lawId) {
	if (window.confirm("是否下载该法律")) {
        var json = tools.requestJsonRs(contextPath + "/lawAdjustReportCtrl/getFilelistById.action",{id: lawId});
        var attachModels = json.rtData;
        for(var i = 0; i < attachModels.length; i++){
            attachModels[i].priv = 1+2;
            var id = attachModels[i].sid; 
            var _downloadFrame = document.createElement("iframe");
            _downloadFrame.style.display = "none";
            document.body.appendChild(_downloadFrame);
            _downloadFrame.src = contextPath + "/attachmentController/downFile.action?id=" +id;
        }
//        $('#datagrid').datagrid("reload");
    }
}

function enterKeydownForSearch(){
    $('#toolbar').bind('keypress',function(event){
        if(event.keyCode == "13"){
        	var params = {
        			name : $("#name").val(),
        			timeliness : $("#timeliness").val(),
        			submitlawLevel : $("#submitlawLevel").val(),
        			examine : $("#examine").val()
        		};
        		$('#datagrid').datagrid("reload", params);
        }
    });
}
