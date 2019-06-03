//字典表
var DICT_URL = "/xzfy/common/getDict.action";

// 案件提取
var LIST_URL = "/xzfy/caseInfo/getCaseExtract.action";

//全局案件ID
var caseId = "";

var datagrid = "";

function doInit(){
	
	caseId = getQueryString("caseId");
	//初始化
	init();
	
	//表格数据
	datagrid = $('#datagrid').datagrid({
		url:contextPath + LIST_URL,
		queryParams:{
			caseId:caseId,
			caseNum:$('#caseNum').val(),
			name:$('#name').val(),
			respondentName:$('#respondentName').val(),
			dealMan1Id:$('#dealMan1Id').val(),
			startTime:$('#startTime').val(),
			endTime:$('#endTime').val()
		},
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'personId',//主键列
		striped : true,
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
            {field:'caseId',checkbox:true,width:100,
            	formatter:function(value,data,rowIndex){
                    return data.caseId;
	            }
            },
//			{field:'opt_',title:'案件编号',width:200,formatter:function(value,data,rowIndex){
//                var  opt= "<a href=\"#\" onclick=\"query('"+data.caseId+"')\">"
//                    + data.caseNum + "</a>&nbsp;&nbsp;";
//                return  opt;
//			}},
//			{field:'caseId',title:'案件ID',width:200},
			{field:'caseNum',title:'案件编号',width:200},
			{field:'postType',title:'申请方式',width:200},
			{field:'name',title:'申请人',width:200},
			{field:'respondentName',title:'被申请人',width:200},
			{field:'applicationDate',title:'复议日期',width:200},
			{field:'caseStatus',title:'案件状态',width:200},
			{field:'caseChiledStatus',title:'受理状态',width:200},
			{field:'remainderTime',title:'剩余时长',width:200}
		]]
	
	})
}


//初始化
function init(){
	
	//初始化申请方式
	var url = DICT_URL;
	var param = {type:"APPLICATION_TYPE_CODE"};
	var json = tools.requestJsonRs(url,param);
	var html = createSelectHtml(json);
	$("#postType").html(html);
	
	//初始化案件受理状态
	param = {type:"ACCEPT_STATUS_CODE"};
	json = tools.requestJsonRs(url,param);
	html = createSelectHtml(json);
	$("#acceptType").html(html);
	
}

//创建下拉单选框HTML
function createSelectHtml(json){
	var html = "<option value=''>--请选择--</option>";
	if( json.rtState == true ){
		var list = json.rtData;
		for(var i=0;i<list.length;i++){
			html = html + "<option value='"+list[i].code+"'>"+list[i].codeDesc+"</option>";
		}
	}
	return html;
}

/********************************************************************/

// 查询
function search(){
	$('#datagrid').datagrid('load',{
		type:2,	
		caseNum:$('#caseNum').val(),
		name:$('#name').val(),
		respondentName:$('#respondentName').val(),
		dealMan1Id:$('#dealMan1Id').val(),
		startTime:$('#startTime').val(),
		endTime:$('#endTime').val()
	});
}

// 重置
function formReset() {
	$('#form1')
	 .not(':button, :submit, :reset, :hidden')
	 .val('')
    .removeAttr('checked')
    .removeAttr('selected');   
}
