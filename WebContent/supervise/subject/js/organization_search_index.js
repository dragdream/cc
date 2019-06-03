/*初始化*/
    $(document).ready(function(e) {
        $(".but").click(function(e) {
            $(".abc").toggle();
        });
    });
var datagrid;
function doInit() {
	natureInit();
//    getSysCodeByParentCodeNo("DEPT_NATURE","nature");
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
        queryParams : {gender:-1,isDepute:1},
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
                { field : 'subName', title : '受委托组织名称', width : '25%' ,align:'left' , halign: 'center',
                    formatter:function(value,rowData,rowIndex){
                        var optsStr = "<a href=\"#\" title = " + value + " onclick=\"look('" + rowData.id + "')\">" + value + "</a>";
                        return optsStr;
                    } 
                },
                { field : 'code', title : '统一社会信用代码', width : '16%' ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(rowData.code == null){
						return "";
					}else{
						var optsStr = "<span title=" + rowData.code + ">" + rowData.code + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'entrustNature', title : '受委托组织性质', width : 50 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
	            { field : 'parentId', title : '委托主体', width : 50,align:'left' , halign: 'center', formatter: 
					function(e, rowData) {
	                var lins = "<span title=" + rowData.parentId + ">" + rowData.parentId + "</span>"
	                return lins;
	            }, },
				{ field : 'orgMode', title : '委托方式', width : 50 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
				{ field : 'createName', title : '创建人', width : 50 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
				{ field : 'createTimeStr', title : '创建时间', width : 50 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
                { field : '___', title : '操作', width : '6%',align:'center', halign: 'center',formatter : function(e, rowData) {
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

/*
 * 修改
 */
function look(id) {
    top.bsWindow(contextPath + "/supervise/subject/organization_search_look.jsp?id="+id, "查看",
            {
                width : "800",
                height : "420",
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

/**
 * 查询
 */
function queryS() {    
    var params = {
        subName : $("#subName").val(),
        code : $("#code").val(),
        entrustNature : $("#entrustNature").val(),
        parentId : $("#parentId").val(),
        isDepute : $("#isDepute").val()
    };
    if(params.entrustNature == -1){
    	params.entrustNature = "";
    }
    $('#datagrid').datagrid("reload", params);
    $('#datagrid').datagrid("clearSelections");
}

function natureInit(){
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "ORGANIZATION_NATURE"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: -1, codeName: "全部"});
        $('#entrustNature').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'auto',
            onLoadSuccess:function(){
                $('#entrustNature').combobox('setValue',-1);
            },
        });
    }
}
