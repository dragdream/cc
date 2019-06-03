/**
 * 法律法规调整上报管理js
 */
var applyTable = null;
function doInit() {
    initCombobox();
	initDataGrid();
	document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  // 取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
        	search();
        }
    };
}

function initCombobox(){
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "LAW_TYPE"});
    json.rtData.unshift({codeNo: '', codeName: "全部"});
    if(json.rtState) {
        $('#submitlawLevel').combobox({
            data: json.rtData,
            prompt:'请选择',
            editable:false,
            panelHeight:'auto',
            valueField: 'codeNo',
            textField: 'codeName',
            onLoadSuccess: function(){
                var submitlawLevel = $('#submitlawLevel_value').val();
                if(submitlawLevel != null && submitlawLevel != ''){
                    $('#submitlawLevel').combobox('setValue', submitlawLevel);
                }
            }
        });
    }
}

function initDataGrid() {
	applyTable = $('#datagrid').datagrid(
					{
						url : contextPath
								+ '/lawAdjustExamineCtrl/examineListByPage.action',
						queryParams : {
							gender : -1
						},
						pagination : true,
						singleSelect : false,
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
						},
						columns : [ [
								{
									field : 'controlType',
									title : '调整类型',
									width : 30,
									align : 'left',
									align : 'center',
									formatter : function(data, rowData) {
										if (rowData.controlType == '01') {
											return "新颁";
										} else if (rowData.controlType == '02') {
											return "修订";
										} else if (rowData.controlType == '03') {
											return "废止";
										}
									}
								},
								{
									field : 'name',
									title : '法律法规名称',
									width : 50,
									align : 'left',
									halign : 'center', formatter: 
										function(value,rowData,rowIndex){
										if(value == null){
											return "";
										}else{
	                                		var optsStr = "<a href=\"#\" title=" + value + " onclick=\"look('" + rowData.id + "')\">" + value + "</a>";
											return optsStr;
										}
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
									field : 'submitlawLevel',
									title : '效力级别',
									width : 30,
									align : 'center',
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
									field : 'submitDeptName',
									title : '提交部门',
									width : 30,
									align : 'center',
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
									field : 'submitName',
									title : '提交人员',
									width : 30,
									align : 'center',
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
									field : 'submitDateStr',
									title : '提交日期',
									width : 30,
									align : 'center',
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
									field : 'examine',
									title : '审核状态',
									width : 30,
									align : 'center',
									halign : 'center',
									formatter : function(data, rowData) {
										if (rowData.examine == 2) {
											return "待审核";
										} else if (rowData.examine > 2
												&& rowData.examine < 4) {
											return "审核通过";
										} else if (rowData.examine >= 4) {
											return "已入库";
										} else if (rowData.examine == 1 && rowData.isBack == 1) {
											return "审核退回";
										}
									}
								},
								{
									field : '__',
									title : '操作',
									width : 30,
									align : 'center',
									halign : 'center',
									formatter : function(e, rowData) {
										var optsStr = '';
										if(rowData.examine == 2){
										optsStr = "<span title='审核'><a href=\"#\" onclick=\"openExamineReport('"
												+ rowData.id
												+ "')\"><i class='fa fa-pencil-square-o common-green'></i></a></span>&nbsp;";
										}else if(rowData.examine == 3){
											optsStr = "<span title='法律条文'><a href=\"#\" onclick=\"openDetailManage('"
												+ rowData.id+"','"+rowData.name
												+ "')\"><i class='fa fa-navicon (alias) common-yellow'></i></a></span>&nbsp;&nbsp;&nbsp;";
											optsStr = optsStr + "<span title='原文下载'><a href=\"#\" onclick=\"downloadFiles('"
                                                + rowData.id
                                                + "')\"><i class='fa fa-download common-blue'></i></a></span>&nbsp;&nbsp;&nbsp;";
											optsStr = optsStr + "<span title='正式入库'><a href=\"#\" onclick=\"loadIntoFormalTable('"
                                            + rowData.id
                                            + "')\"><i class='fa fa-paper-plane common-green'></i></a></span>&nbsp;";
										}else if(rowData.examine == 4){
										    
										}
										return optsStr;
									}
								}, ] ]
					});
}

function openExamineReport(id) {
    var params = {
            id: id
    }
    var url = contextPath + "/lawAdjustExamineCtrl/openLawExamineInput.action";
    url = url + "?" + $.param(params);
	top.bsWindow(url, "审核", {
				width : "850",
				height : "400",
				buttons : [ {
                    name : "关闭",
                    classStyle : "btn-alert-gray"
                },{
                    name : "退回",
                    classStyle : "btn-alert-blue"
                },{
					name : "通过",
					classStyle : "btn-alert-blue"
				} ],
				submit : function(v, h) {
					var cw = h[0].contentWindow;
					if (v == "通过") {
						var status = cw.examinePass();
						if (status == true) {
							$("#datagrid").datagrid("reload");
							return true;
						}
					} else if (v == "退回") {
						backReason(id);
						return true;
// alert(backStatus);
// if(backStatus){
// var status = cw.examineNotPass();
// alert(status);
// if (status == true) {
// $("#datagrid").datagrid("reload");
// return true;
// }
// }else{
// return false;
// }
					} else if (v == "关闭") {
						return true;
					}
				}
			});
}
// 打开退回原因界面
function backReason(id){
	top.bsWindow(contextPath + "/supervise/lawManage/lawExamine/lawExamine_back.jsp?id="+id, "退回原因", {
		width : "850",
		height : "400",
		buttons : [ {
			name : "取消",
			classStyle : "btn-alert-gray"
		} ,{
			name : "确定",
			classStyle : "btn-alert-blue"
		} ],
		submit : function(v, h) {
			var cw = h[0].contentWindow;
			if (v == "确定") {
				var status = cw.save();
				var backStatus = cw.examineNotPass();
				if (status == true && backStatus == true) {
					$("#datagrid").datagrid("reload");
					
					return true;
				}
			} else if (v == "取消") {
// $(".iframeDiv"+"121212").hide();
// $(".iframeDIv"+"121212").find("iframe").remove();
// $(".iframeDiv"+"121212").remove();
// $(".zhezhao"+"121212").remove();
				return true;
			}
		}
	});
}
function search(){
    var param = {
            name: $('#lawName').val(),
            submitlawLevel: $('#submitlawLevel').combobox('getValue'),
        }
        $('#datagrid').datagrid('reload', param);
}

function openDetailManage(id, name){
	var params = {
			id : id,
			lawName : name
	}
	location.href = contextPath+"/supervise/lawManage/lawExamine/law_contentManage.jsp?"+$.param(params);
}

/**
 * 法律下载
 */
function downloadFiles(lawId){
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
}
    
function look(id) {
	var params = {
            id: id
    }
    var url = contextPath + "/lawAdjustExamineCtrl/openLawExamineInput.action";
    url = url + "?" + $.param(params);
	top.bsWindow(url, "查看", {
				width : "850",
				height : "320",
				buttons : [ {
                    name : "关闭",
                    classStyle : "btn-alert-gray"
                }],
				submit : function(v, h) {
					var cw = h[0].contentWindow;
					if (v == "通过") {
						var status = cw.examinePass();
						if (status == true) {
							$("#datagrid").datagrid("reload");
							return true;
						}
					} else if (v == "退回") {
						var status = cw.examineNotPass();
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
function loadIntoFormalTable(id){
	//判断是否有已经拆好的法条
	var formalJson = tools.requestJsonRs("/detailTempController/listByPage.action?id="+id);
	if(formalJson.total == 0){
		$.MsgBox.Alert_auto("请先维护法律法条，再正式入库");
		return false;
	}
    var finalParams ={
            id: id
    };
    top.$.MsgBox.Confirm('提示', '入库后将不能再修改，确定入库？', function(){
        var json = tools.requestJsonRs("/lawAdjustExamineCtrl/loadIntoFormalTable.action",finalParams);
        if(json.rtState){
            $.MsgBox.Alert_auto("保存成功！");
            $("#datagrid").datagrid("reload");
            return true;
        }else{
            $.MsgBox.Alert_auto("保存失败！");
            return false;
        }
    });
}