function doInit() {
    initDatagriad();
}

function initDatagriad(){
    datagrid = $('#datagrid').datagrid(
            {
                url : contextPath + '/detailTempController/listByPage.action',
                queryParams : {
                    id : lawId
                },
                checkbox : false,
                pagination : true,
                singleSelect : true,
                selectOnCheck : true,
                checkOnSelect : true,
                striped : true,
                pageSize : 20,
                pageList : [ 10, 20, 50, 100 ],
                view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
                toolbar : '#toolbar',// 工具条对象
                border : false,
                rownumbers : false,
                nowrap : true,
                fitColumns : true,// 列是否进行自动宽度适应    
                columns : [ [
                        {
                            field : 'id',
                            title : '序号',
                            align : 'center',
                            width : 20,
                            align : 'center',
                            formatter : function(value, rowData, rowIndex) {
                                return rowIndex + 1;
                            }
                        },
                        {
                            field : 'detailChapter',
                            title : '章',
                            width : 30,
                            align : 'center',
                            halign : 'center',
                            formatter : function(value, rowData, rowIndex) {
                            	if(value == null){
                            		return "";
                            	}else{
                            		return value;
                            	}
                            }
                        },
                        {
                            field : 'detailStrip',
                            title : '条',
                            width : 30,
                            align : 'center',
                            halign : 'center',
                            formatter : function(value, rowData, rowIndex) {
                            	if(value == null){
                            		return "";
                            	}else{
                            		return value;
                            	}
                            }
                        },
                        {
                            field : 'detailFund',
                            title : '款',
                            width : 30,
                            align : 'center',
                            halign : 'center',
                            formatter : function(value, rowData, rowIndex) {
                            	if(value == null){
                            		return "";
                            	}else{
                            		return value;
                            	}
                            }
                        },
                        {
                            field : 'detailItem',
                            title : '项',
                            width : 30,
                            align : 'center',
                            halign : 'center',
                            formatter : function(value, rowData, rowIndex) {
                            	if(value == null){
                            		return "";
                            	}else{
                            		return value;
                            	}
                            }
                        },
                        {
                            field : 'detailCatalog',
                            title : '目',
                            width : 30,
                            align : 'center',
                            halign : 'center',
                            formatter : function(value, rowData, rowIndex) {
                            	if(value == null){
                            		return "";
                            	}else{
                            		return value;
                            	}
                            }
                        },
                        {
                            field : 'content',
                            title : '内容',
                            width : 260,
                            align : 'left',
                            halign : 'center',
                            formatter : function(value, rowData, rowIndex) {
                            	if(value == null){
                            		return "";
                            	}else{
                                    return '<span class="custom-text-overflow table-td-full-width" title="' + value + '">' + value + '</span>';
                            	}
                            }
                        },
                        {
                            field : '__',
                            title : '操作',
                            width : 45,
                            align : 'center',
                            halign : 'center',
                            formatter : function(e, rowData) {
                                var optsStr = "";
                                optsStr = "<span title='修改'><a href='javaScript:void(0);' onclick='edit(\"" + rowData.id
                                        + "\",\""+rowData.lawId+"\",\""+rowData.lawName+"\")'><i class='fa fa-pencil common-yellow'></i></a></span>&nbsp;&nbsp;&nbsp;&nbsp;";
                                optsStr = optsStr + "<span title='删除'><a href='javaScript:void(0);' onclick='del(\"" + rowData.id
                                        + "\")'><i class='fa fa-trash-o common-red'></i></a></span>";

                                return optsStr;
                            }

                        }, ] ]
            });
}
/*
 * 新增
 */
function add() {
    top.bsWindow(contextPath + "/supervise/lawManage/lawExamine/law_contentManage_input.jsp?lawId=" + lawId + "&lawName=" + lawName, "新增", {
        width : "600",
        height : "215",
        buttons : [ {
            name : "关闭",
            classStyle : "btn-alert-gray"
        }, {
            name : "保存",
            classStyle : "btn-alert-blue"
        } ],
        submit : function(v, h) {
            var cw = h[0].contentWindow;
//            if (cw.isEdit){
//                if(window.confirm("确定放弃所做操作，退出编辑？")){
//                    return true;
//                }else{
//                    return false;
//                }
//            }
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

/* 修改 */
function edit(id, lawId, lawName) {
    // alert(id);法律id
    top.bsWindow(contextPath + "/supervise/lawManage/lawExamine/law_contentManage_input.jsp?id=" + id + "&lawName=" + lawName + "&lawId=" + lawId, "修改", {
        width : "600",
        height : "215",
        buttons : [ {
            name : "关闭",
            classStyle : "btn-alert-gray"
        }, {
            name : "保存",
            classStyle : "btn-alert-blue"
        }, ],
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
function del(id) {
    var obj = $('#datagrid').datagrid("getSelections");
    // console.log(obj[0].id);

    top.$.MsgBox.Confirm("提示", "是否确认删除该数据", function(){
        var json = tools.requestJsonRs("/detailTempController/delete.action", {
            id : id
        });
        $("#datagrid").datagrid("reload");
        $('#datagrid').datagrid("clearSelections");
    });

}

/*
 * * 导入
 */
function importLaw() {
    top.bsWindow(contextPath + "/supervise/lawManage/lawExamine/law_importPage.jsp?id=" + lawId, "法律导入 ", {
        width : "850",
        height : "450",
        buttons : [ {
            name : "关闭",
            classStyle : "btn-alert-gray"
        }, {
            name : "导入",
            classStyle : "btn-alert-blue"
        } ],
        submit : function(v, h) {
            var cw = h[0].contentWindow;
            if (v == "导入") {
                cw.saveSpliteResult(window, h[0]);
                return false;
            } else if (v == "关闭") {
                return true;
            }
        }
    });
}
/*
 * * 导入
 */
function excelImportLaw() {
    top.bsWindow(contextPath+"/supervise/lawManage/lawExamine/law_input.jsp?id="+lawId ,"法律导入",{width:"500",height:"100",buttons:
        [
         {name:"关闭",classStyle:"btn-alert-gray"},
         {name:"导入",classStyle:"btn-alert-blue"}
         ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if(v=="导入"){
                var status = cw.save(window);
                console.log(status);
                if(status==true){
                    return true;
                }
            }else if(v=="关闭"){
                return true;
            }
    }});
}

function backMainPage() {
    location.href = contextPath + "/supervise/lawManage/lawExamine/lawExamine_base.jsp";
}
