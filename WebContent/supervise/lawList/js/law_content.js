function doInit() {
    datagrid = $('#datagrid').datagrid({
        url : contextPath + '/detailController/listByPage.action',
        queryParams : {
            id : id
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
        idField : 'uuid',// 主键列
        fitColumns : true,// 列是否进行自动宽度适应
        columns : [ [ {
            field : 'uuid',
            checkbox : true,
        },
        {field:'ID',title:'序号',align:'center',
            formatter:function(value,rowData,rowIndex){
                return rowIndex+1;
            }
        },
        {
            field : 'detailChapter',
            title : '章',
            width : 50,align:'center' , halign: 'center'
        }, {
            field : 'detailStrip',
            title : '条',
            width : 50,align:'center' , halign: 'center'
        }, {
            field : 'detailFund',
            title : '款',
            width : 100,align:'center' , halign: 'center'
        }, {
            field : 'detailItem',
            title : '项',
            width : 100,align:'center' , halign: 'center'
        }, {
            field : 'detailCatalog',
            title : '目',
            width : 100,align:'center' , halign: 'center'
        }, {
            field : 'content',
            title : '内容',
            width : 100,align:'left' , halign: 'center'
        }, ] ],
        singleSelect : false,
        selectOnCheck : true,
        checkOnSelect : true
    });
}
/*
 * 新增
 */
function add() {
    top.bsWindow(contextPath + "/supervise/lawList/law_conadd.jsp?id=" + id +"&lawName=" + lawName,
            "新增", {
                width : "550",
                height : "350",
                buttons : [ {
                    name : "关闭",
                    classStyle : "btn-alert-gray"
                },{
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

/* 修改 */
function edit() {
    // alert(id);法律id
    var obj = $('#datagrid').datagrid("getSelections");
    // console.log(obj[0].id);
    if(obj.length==1){
    	top.bsWindow(contextPath + "/supervise/lawList/law_conadd.jsp?id=" + id +"&lawName=" + lawName + "&lawId=" + obj[0].id, "修改", {
            width : "550",
            height : "350",
            buttons : [ {
                name : "关闭",
                classStyle : "btn-alert-gray"
            } ,{
                name : "保存",
                classStyle : "btn-alert-blue"
            },],
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
    }else{
    	$.MsgBox.Alert_auto("请选择一条数据！"); 
		return false;
    }
    

}
function del() {
    var obj = $('#datagrid').datagrid("getSelections");
    // console.log(obj[0].id);
    if(obj.length == 0){
    	$.MsgBox.Alert_auto("请选择一条数据！"); 
		return false;
    }else{
    	if (window.confirm("是否确认删除该数据")) {
            var json = tools.requestJsonRs("/detailController/delete.action", {
                id : obj[0].id
            });
            $("#datagrid").datagrid("reload");
			$('#datagrid').datagrid("clearSelections");
        }
    }
}

/*
 * * 导入
 */
function importLaw() {
    top.bsWindow(contextPath + "/supervise/lawList/law_input.jsp?id="
            + id, "法律导入 ", {
        width : "500",
        height : "150",
        buttons : [
        // {name:"保存",classStyle:"btn-alert-blue"},
        {
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
