/**
 * 法律法规调整上报管理js
 */
var applyTable = null;
function doInit() {
    initCombobox();
    initDataGrid();
    document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
            search();
        }
    }
}

function initCombobox(){
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "LAW_TYPE"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: '', codeName: "全部"});
        $('#submitlawLevel').combobox({
            data: json.rtData,
            prompt:'全部',
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
    applyTable = $('#datagrid')
            .datagrid(
                    {
                        url : contextPath
                                + '/lawAdjustReportCtrl/listByPage.action',
                        queryParams : {
                            gender : -1
                        },
                        pagination : true,
                        singleSelect : false,
                        striped : true,
                        pageSize : 20,
                        pageList : [ 10, 20, 50,100 ],
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
                                    field : 'id',
                                    title : '',
                                    checkbox : true,

                                },
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
                                        }else{
                                        	return "";
                                        }
                                    }
                                },
                                {
                                    field : 'name',
                                    title : '法律法规名称',
                                    width : 50,
                                    align : 'left',
                                    halign : 'center', formatter: 
                    					function(e,rowData){
                                		var optsStr = "<a href=\"#\" title=" + e + " onclick=\"look('" + rowData.id + "')\">" + e + "</a>";
                    					return optsStr;
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
                                    field : 'promulgation',
                                    title : '发布日期',
                                    width : 50,
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
                                    field : 'backReason',
                                    title : '退回原因',
                                    width : 50,
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
                                    align : 'left',
                                    halign : 'center',
                                    formatter : function(data, rowData) {
                                        if (rowData.examine == 1 ) {
                                        	if(rowData.isBack == 1){
                                        		return "审核退回";
                                        	}else{
                                        		return "待上报";
                                        	}
                                        }else if (rowData.examine == 2) {
                                            return "待审核";
                                        } else if (rowData.examine > 2) {
                                            return "审核通过";
                                        }
                                    }
                                },
//                                {
//                                    field : 'implementation',
//                                    title : '生效日期',
//                                    width : 50,
//                                    align : 'center',
//                                    halign : 'center', formatter: 
//										function(value,rowData,rowIndex){
//										if(value == null){
//											return "";
//										}else{
//											var optsStr = "<span title=" + value + ">" + value + "</span>";
//											return optsStr;
//										}
//							            },
//                                },
//                                {
//                                    field : 'timelinessStr',
//                                    title : '时效性',
//                                    width : 50,
//                                    align : 'center',
//                                    halign : 'center', formatter: 
//										function(value,rowData,rowIndex){
//										if(value == null){
//											return "";
//										}else{
//											var optsStr = "<span title=" + value + ">" + value + "</span>";
//											return optsStr;
//										}
//							            },
//                                },
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
                                    field : '__',
                                    title : '操作',
                                    width : 30,
                                    align : 'center',
                                    halign : 'center',
                                    formatter : function(e, rowData) {
                                    	var optsStr = "";
                                    	if(rowData.examine == 1){
                                    		optsStr = "<span title='修改'><a href='javaScript:void(0);' onclick='openEditReport(\""
                                                + rowData.id
                                                + "\")'><i class='fa fa-pencil common-yellow'></i></a></span>&nbsp;&nbsp;&nbsp;&nbsp;";
                                    		optsStr = optsStr + "<span title='删除'><a href='javaScript:void(0);' onclick='deleteAdjust(\""
                                            	+ rowData.id
                                            	+ "\")'><i class='fa fa-trash-o common-red'></i></a></span>";
                                    	}else{
                                    		optsStr = optsStr + "<span title='查看'><a href='javaScript:void(0);' onclick='look(\""
                                        	+ rowData.id
                                        	+ "\")'><i class='fa fa-eye'></i></a></span>";
                                    	}
                                        return optsStr;
                                    }
                                }, ] ]
                    });
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
function openNewReport() {
    var url = contextPath + "/lawAdjustReportCtrl/openLawAdjustInput.action";
    top.bsWindow(url, "申请调整", {
        width : "780",
        height : "350",
        buttons : [ {
            name : "关闭",
            classStyle : "btn-alert-gray"
        }, {
            name : "保存",
            classStyle : "btn-alert-blue"
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

function openEditReport(id) {
    var params = {
        id : id
    }
    var url = contextPath + "/lawAdjustReportCtrl/openLawAdjustInput.action";
    url = url + "?" + $.param(params);
    top.bsWindow(url, "修改", {
        width : "750",
        height : "350",
        buttons : [ {
            name : "关闭",
            classStyle : "btn-alert-gray"
        }, {
            name : "保存",
            classStyle : "btn-alert-blue"
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

function submitReport() {
    var submitIds = [];
    var examines = [];
    $.each($('#datagrid').datagrid('getChecked'), function() {
        submitIds.push(this.id);
        examines.push(this.examine);
    });
    if (submitIds.length == 0) {
        $.MsgBox.Alert_auto("请选择需要提交审核的法律法规");
        return false;
    }
    for(var i=0;i<examines.length;i++){
    	if(examines[i] != 1){
    		$.MsgBox.Alert_auto("审核状态在待审核和审核通过下，不能重复提交审核");
    		return false;
    	}
    }
    var params = {
        submitIds : submitIds.join(",")
    };
    var json = tools.requestJsonRs("/lawAdjustReportCtrl/submitReport.action",
            params);
    if (json.rtState) {
        $.MsgBox.Alert_auto("提交成功");
        $("#datagrid").datagrid("reload");
		$('#datagrid').datagrid("clearSelections");
        return true;
    } else {
        $.MsgBox.Alert_auto("提交失败");
        return false;
    }
}

function search() {
    var param = {
    	controlType : $('#controlType').val(),
        name : $('#lawName').val(),
        organ : $('#organ').val(),
        submitlawLevel : $('#submitlawLevel').combobox('getValue')
    };
    $('#datagrid').datagrid('reload', param);
}

function deleteAdjust(id) {
    if (window.confirm("确定删除该条数据？")) {
        var params = {
            id : id
        };
        var json = tools.requestJsonRs(
                "/lawAdjustReportCtrl/deleteAdjust.action?", $.param(params));
        if (json.rtState) {
            $.MsgBox.Alert_auto("已删除");
            $("#datagrid").datagrid("reload");
            return true;
        } else {
            $.MsgBox.Alert_auto("删除失败");
            return false;
        }
    }
}