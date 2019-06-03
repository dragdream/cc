/*评查细则列表页面管理方法*/

/**
 * 初始化方法
 * @returns
 */
function doInit() {
    initDatagrid();
    initCombobox();
    enterKeydownForSearch();
}

/**
 * 表格初始化
 */
function initDatagrid(params) {
    datagrid = $('#rules_table').datagrid(
            {
                url : contextPath + '/jdCasecheckRuleDetailCtrl/datagrid.action',
                queryParams : params,
                pagination : true,
                singleSelect : true,
                striped : true,
                pageSize : 20,
                pageList : [ 10, 20, 50, 100 ],
                view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
                toolbar : '#toolbar',
                // 工具条对象
                border : false,
                /* idField:'formId',//主键列 */
                fitColumns : true,
                // 列是否进行自动宽度适应
                nowrap : true,
                rownumbers: true,
                columns : [ [
                        {
                            field : 'code',
                            title : '编号',
                            halign : 'center',
                            align : 'center',
                            width : 20
                        },
                        {
                            field : 'checkSubject',
                            title : '评查项目',
                            halign : 'center',
                            align : 'left',
                            width : 50,
                            formatter:function(value,rowData,rowIndex){
                        		return '<span title='+value+'>'+value+'</span>';
                            }
                        },
                        {
                        	field : 'basisDescribe',
                        	title : '评查标准',
                        	halign : 'center',
                        	align : 'left',
                        	width : 50,
                        	formatter:function(value,rowData,rowIndex){
                        		if(typeof(rowData.basisDescribe)!="undefined"&&rowData.basisDescribe!=null&&rowData.basisDescribe!=""){
                        			return '<span title='+value+'>'+value+'</span>';
                        		}
                        		return null;
                        	}
                        },
                        {
                            field : 'deductscore',
                            title : '扣分分值',
                            halign : 'center',
                            align : 'center',
                            width : 20,
                            formatter:function(value,rowData,rowIndex){
                            	if(rowData.checkSubjectType=="01"){
                            		return value;
                            	}
                            	return null;
                            }

                        },
                        {
                            field : '——',
                            title : '加分项(分值)',
                            halign : 'center',
                            align : 'center',
                            width : 20,
                            formatter:function(value,rowData,rowIndex){
                            	if(rowData.checkSubjectType=="02"){
                            		return rowData.deductscore;
                            	}
                            	return null;
                            }
                        },
                        {
                            field : '__',
                            title : '操作',
                            halign : 'center',
                            align : 'center',
                            width : 30,
                            formatter : function(e, rowData) {
                                var optsStr = "";
                                optsStr += "<span title='修改'><a href='javaScript:void(0);' onclick='openEditInput(\"" + rowData.id
                                + "\")'><i class='fa fa-pencil common-yellow'></i></a></span>&nbsp;&nbsp;&nbsp;&nbsp;";
                                optsStr += "<span title='删除'><a href='javaScript:void(0);' onclick='deleteAdminDivision(\"" + rowData.id
                                        + "\")'><i class='fa fa-trash-o common-red'></i></a></span>";
                                return optsStr;
                            }
                        } ] ]
            });
}
/**
 * 初始化下拉选项 
 */
function initCombobox(){
	//评分版本
    $('#rulesVersion').combobox({
    	url: contextPath+"/jdCasecheckRuleVersionCtrl/getList.action",
        valueField: 'id',
        textField: 'name',
        editable:false,
        panelHeight:'auto',
        onLoadSuccess:function(){
        	var versionArr=$('#rulesVersion').combobox('getData');
        	//默认选中生效版本
        	for(var i in versionArr){
        		if(versionArr[i].isValid==1){
        			$('#rulesVersion').combobox('setValue',versionArr[i].id);
        		}
        	}
        	getRateLevel();
        },
        onChange: function() {
        	getRateLevel();
        }
    });
    //适用范围
    var rulesSuitRange = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "RATE_RANGE_TYPE"});
    if(rulesSuitRange.rtState) {
    	rulesSuitRange.rtData.unshift({codeNo: -1, codeName: "请选择"});
    	$('#rulesSuitRange').combobox({
    		data: rulesSuitRange.rtData,
    		valueField: 'codeNo',
    		textField: 'codeName',
    		editable:false,
            panelHeight:'auto',
    		onLoadSuccess:function(){
    			$('#rulesSuitRange').combobox('setValue',-1);
    		}
    	});
    }
}

//评分细则层级
function getRateLevel(){
    var json = tools.requestJsonRs("/jdCasecheckRuleKindCtrl/getRootListByVersionId.action", {versionId: $('#rulesVersion').combobox('getValue')});
    if(json.rtState) {
        json.rtData.unshift({id: -1, describe: "请选择"});
        $('#firstLevel').combobox({
            data: json.rtData,
            valueField: 'id',
            textField: 'describe',
            editable:false,
            panelHeight:'auto',
            onChange: function() {
                var firstLevel = $('#firstLevel').combobox('getValue');
                if(firstLevel != -1) {
                    var params = {
                		parentId: firstLevel
                    };
                    var result = tools.requestJsonRs("/jdCasecheckRuleKindCtrl/getListByParentId.action", params);
                    if(result.rtState) {
                    	result.rtData.unshift({id: -1, describe: "请选择"});
                        $('#secLevel').combobox({
                            data: result.rtData,
                            valueField: 'id',
        		            textField: 'describe',
                            editable:false,
                            panelHeight:'auto',
                            onLoadSuccess:function(){
                                $('#secLevel').combobox('setValue',-1);
                            },
                        });
                    }
                }
            }
        });
    }
}

/**
 * 回车键搜索
 */
function enterKeydownForSearch() {
    $('#rules_search_div').bind('keypress', function(event) {
        if (event.keyCode == "13") {
            var params = tools.formToJson($('#rules_search_form'));
            initDatagrid(params);
        }
    });
}

/*
 * 查询
 */
function rulesSearch() {
    var params = tools.formToJson($('#rules_search_form'));
    initDatagrid(params);
}

/*
 * 新增版本
 */
function addVersion() {
    top.bsWindow(contextPath + "/supervise/caseCheck/rules/rules_addVersion.jsp", "新增版本", {
        width : "700",
        height : "300",
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
                	$.MsgBox.Alert_auto("添加成功！");
                	$('#rulesVersion').combobox('reload');
                    $("#rules_table").datagrid("reload");
                    $('#rules_table').datagrid("clearSelections");
                    return true;
                }
            } else if (v == "关闭") {
                return true;
            }
        }
    });
}

/*
 * 新增评分细则
 */
function addRateRules() {
	var vId=$('#rulesVersion').combobox("getValue"),vName=$('#rulesVersion').combobox("getText");
	top.bsWindow(contextPath + "/supervise/caseCheck/rules/rules_add.jsp?id=0&vId="+vId+"&vName="+vName, "新增评分细则", {
		width : "700",
		height : "300",
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
					$("#rules_table").datagrid("reload");
					$('#rules_table').datagrid("clearSelections");
					return true;
				}
			} else if (v == "关闭") {
				return true;
			}
		}
	});
}

/*
 * 修改
 */
function openEditInput(id) {
    var params = {
        id : id
    };
    url = contextPath + "/supervise/caseCheck/rules/rules_add.jsp";
    top.bsWindow(url, "修改评分细则", {
        width : "700",
        height : "300",
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
                    $("#rules_table").datagrid("reload");
                    $('#rules_table').datagrid("clearSelections");
                    return true;
                }
            } else if (v == "关闭") {
                return true;
            }
        }
    });
}

function showStandard(msg){
	$.MsgBox.Alert("评分标准", msg);
}
/*
 * 删除
 */
function deleteAdminDivision(id) {
    $.MsgBox.Confirm("提示", "确定删除？", function() {
        var json = tools.requestJsonRs("/adminDivisionManageCtrl/deleteAdminDivisionInfo.action", {
            id : id
        });
        $('#rules_table').datagrid("reload");
        $('#rules_table').datagrid("clearSelections");
        $.MsgBox.Alert_auto("数据已删除！");
    });
}

