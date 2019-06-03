//字典表
var DICT_URL = "/xzfy/common/getDict.action";

//列表地址
var LIST_URL = "/xzfy/organ/list.action";

//导出地址
var EXPORT_URL = "/xzfy/organ/exportExcel.action";

var datagrid = "";
var treeId = "";
var treeName = "";
function doInit(){
	
	//获取组织机构树ID与名称
	treeId = getQueryString("treeId");
	treeName = unescape(getQueryString("treeName"));
	$("#treeId").val(treeId);
	
	//机关层级数据
	var url = DICT_URL;
	var param = {type:"PERSON_TYPE_CODE"};
	var json = tools.requestJsonRs(url,param);
	var html = createSelectHtml(json);
	$("#organLevel").html(html);
	
	//表格数据
	datagrid = $('#datagrid').datagrid({
		url:contextPath + LIST_URL,
		queryParams:{
			treeID:treeId
		},
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'orgId',//主键列
		striped : true,
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
            {field:'orgId',checkbox:true,width:100,
            	formatter:function(value,data,rowIndex){
                    return data.personId;
	            }
            },
			{field:'orgName',title:'复议机关名称',width:200},
			{field:'orgLevelName',title:'机关层级',width:200},
			{field:'contacts',title:'联系人',width:200},
			{field:'contactsPhone',title:'联系电话',width:300},
			{field:'compilersNum',title:'人员编制',width:100},
			{field:'opt_',title:'操作',width:200,formatter:function(value,data,rowIndex){
                var  opt= "<a href=\"#\" onclick=\"query('"+data.deptId+"')\">查看</a>&nbsp;&nbsp;" 
                        + "<a href=\"#\" onclick=\"addOrUpdate('"+data.deptId+"','"+data.orgName+"')\">编辑</a>";
                return  opt;
			}},
		]]
	
	})
}

//查询
function search(){
	$('#datagrid').datagrid('load',{
		treeID: $('#treeId').val(),
		organLevel: $('#organLevel').val(),
		organName: $('#organName').val(),
		contacts: $('#contacts').val()
	});
}


//查看详情
function query(deptId){
	//地址
	var title = "组织机构人员详情";
	var url='/xzfy/jsp/organ/queryDetail.jsp?deptId='+deptId;
	bsWindow(url ,title,{width:"600",height:"320",
		buttons:
		[
	 	 //{name:"关闭",classStyle:"btn-alert-gray"}
		]
		,submit:function(v,h){
//			var cw = h[0].contentWindow;
//			if(v=="关闭"){
//				return true;
//			}
		}
	});
}

//新建或更新
function addOrUpdate(dept,deptName){
    window.location.href = "/xzfy/jsp/organ/addupdate.jsp?treeId="
    	+dept+"&treeName="+escape(deptName);
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

//导出
function exportExcel(){
	
	if(orgId == null || orgId == "" || orgId == "undifined"){
		$.MsgBox.Alert_auto("请选择组织机构！");
		return;
	}
	var eisShowAll = $('#isShowAll').is(':checked') == true ? "1":"0";
	var eorgId = $('#orgId').val();
	var	epersonName = $('#personName').val();
	var estaffing =$('#staffing').val();
	
	var url = EXPORT_URL + "?isShowAll="+eisShowAll+"&orgId="+eorgId+
	    "&personName="+epersonName+"&staffing="+estaffing;
	$("#exportIframe").attr("src",url);
}
