/*初始化*/
var datagrid;
function doInit() {
	//资料类型
	messageTypeInit();
	listDatagrid();
	document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
        	queryS();
        }
    }
}
//资料类型
function messageTypeInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "MESSAGE_TYPE"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: -1, codeName: "全部"});
        $('#messageType').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'auto',
            onLoadSuccess:function(){
                $('#messageType').combobox('setValue',-1);
            },
        });
    }
}
//初始化表格
function listDatagrid(){
	datagrid = $('#datagrid') .datagrid( {
		url : contextPath + '/casecheckMessageCtrl/ListBypage.action',
		queryParams : { gender : -1 },
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
				{field:'ID',title:'序号',align:'center',
                    formatter:function(value,rowData,rowIndex){
                        return rowIndex+1;
                    }
                },
				{ field : 'name', title : '资料名称', width:'20%',align:'left' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
				},},
				{ field : 'messageType', title : '资料类型', width :'16%',align:'center' , halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
				},},
				{ field : 'uploadPersonName', title : '上传人员', width : '20%',align:'center' , halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
				},},
				{ field : 'deptName', title : '所属部门', width :50 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
	            },},
				{ field : '___', title : '操作', width : '8%',align:'center' , halign: 'center', formatter : function(e, rowData) {
						var optsStr = "<span title='下载'><a href='javaScript:void(0);' onclick='legalDownloads(\"" + rowData.id + "\")'><i class='fa fa-download'></i></a></span>";
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
						$('#datagrid').datagrid('checkRow',index);
					}
				});
			}
		}

	});
}
/*
 * 下载
 */
function legalDownloads(MessageId) {
	$.MsgBox.Confirm("提示","确定下载该文件？",function(){
        var json = tools.requestJsonRs(contextPath + "/casecheckMessageCtrl/getFilelistById.action",{id: MessageId});
        var attachModels = json.rtData;
        for(var i = 0; i < attachModels.length; i++){
            attachModels[i].priv = 1+2;
            var id = attachModels[i].sid; 
            var _downloadFrame = document.createElement("iframe");
            _downloadFrame.style.display = "none";
            document.body.appendChild(_downloadFrame);
            _downloadFrame.src = contextPath + "/attachmentController/downFile.action?id=" +id;
        }
	});
}
/*
 * * 查询
 */
function queryS() {	
	var params = {
		name : $("#name").val(),
		deptName : $("#deptName").val(),
		messageType : $("#messageType").val()
	};
	if(params.messageType == -1){
		params.messageType = "";
	}
	$('#datagrid').datagrid("reload", params);
	$('#datagrid').datagrid("clearSelections");
}
