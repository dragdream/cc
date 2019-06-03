    $(document).ready(function(e) {
        $(".but").click(function(e) {
            $(".abc").toggle();
        });
    });
/*初始化*/
var datagrid;
function doInit() {
    natureInit();
    orgSysInit();
    subLevelInit();
    getSysCodeByParentCodeNo("SYSTEM_SUBJECT_NATURE","nature");
    listDatagrid();
    document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
        	queryS();
        }
    }
}

function listDatagrid(){
    datagrid = $('#datagrid') .datagrid( {
        url : contextPath + '/subjectSearchController/findListByPageRoles.action',
        queryParams : { gender : -1 ,isDepute:0},
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
                { field : 'subName', title : '主体名称', width : 100,align:'left' , halign: 'center',
                    formatter:function(value,rowData,rowIndex){
                        var optsStr = "<a href=\"#\" title = " + value + " onclick=\"look('" + rowData.id + "')\">" + value + "</a>";
                        return optsStr;
                    } 
                },
                { field : 'orgSys', title : '所属领域', width : 50,align:'center' , halign: 'center', formatter : 
					function(data, rowData) {
					if (rowData.orgSys == null) {
						return "";
					}else{
						return "<span title=" + rowData.orgSys + ">" + rowData.orgSys + "</span>";
					}
				} },
				{ field : 'subLevel', title : '主体层级', width : 50,align:'center' , halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
		            },},
				{ field : 'nature', title : '主体类别', width : 50 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
				{ field : 'innerSupOrgPostNum', title : '编制人数', width : '8%' ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
//                { field : 'examine', title : '审核状态', width : '8%',align:'center' , halign: 'center', formatter : 
//                    function(data, rowData) {
//                        if (rowData.examine == 1) {
//                            return "已审核";
//                        } else {
//                            return "未审核";
//                        }
//                    }
//                },
                { field : '___', title : '操作', width : '6%',align:'center' , halign: 'center', formatter : function(e, rowData) {
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
                        $('#datagrid').datagrid('checkRow',
                                index);
                    }
                });
            }
        }

    });
}

/**
 * 查看详情
 */
function look(id) {
    top.bsWindow(contextPath + "/supervise/subject/subject_search_look.jsp?id="+id, "查看",
            {
                width : "760",
                height : "260",
                buttons : [ {
                    name : "关闭",
                    classStyle : "btn-alert-gray"
                } ],
                submit : function(v, h) {
                    var cw = h[0].contentWindow;
                    if (v == "关闭") {
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
        subName : $("#subName").val(),
        orgSys : $("#orgSys").val(),
        nature : $("#nature").val(),
        subLevel : $("#subLevel").val(),
        examine : $("#examine").val(),
        isDepute : $("#isDepute").val()
    };
    $('#datagrid').datagrid("reload", params);
    $('#datagrid').datagrid("clearSelections");
}

function natureInit(){
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "SYSTEM_SUBJECT_NATURE"});
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
function orgSysInit(){
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "LAW_ENFORCEMENT_FIELD"});
    if(json.rtState) {
//        json.rtData.unshift({codeNo: -1, codeName: "全部"});
        $('#orgSys').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'150px'
//            onLoadSuccess:function(){
//                $('#orgSys').combobox('setValue',-1);
//            },
        });
    }
}
function subLevelInit(){
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "DEPT_LEVEL"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: -1, codeName: "请选择"});
        $('#subLevel').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            onLoadSuccess:function(){
                $('#subLevel').combobox('setValue',-1);
            },
            onChange: function() {
                var powerType = $('#subLevel').combobox('getValue');
                if(powerType != "") {
                    var params = {
                        parentCodeNo: "DEPT_LEVEL",
                        codeNo: powerType
                    };
                    var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action", params);
                    if(result.rtState) {
                        $('#powerDetail').combobox({
                            data: result.rtData,
                            valueField: 'codeNo',
                            textField: 'codeName'
                        });
                    }
                }
            }
        });
    }
}

