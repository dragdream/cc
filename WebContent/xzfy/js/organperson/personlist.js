//字典表
var DICT_URL = "/xzfy/common/getDict.action";

//列表地址
var LIST_URL = "/xzfy/organPerson/list.action";

//删除地址
var DELETE_URL = "/xzfy/organPerson/delete.action";

//导出地址
var EXPORT_URL = "/xzfy/organPerson/exportExcel.action";

var datagrid = "";
var orgId = "";
var orgName = "";
function doInit(){
	
	//获取组织机构树ID与名称
	orgId = getQueryString("treeId");
	orgName = unescape(getQueryString("treeName"));
	$("#orgId").val(orgId);
	
	//机关层级数据
	var url = DICT_URL;
	var param = {type:"PERSON_TYPE_CODE"};
	var json = tools.requestJsonRs(url,param);
	//拼接类型
	var html = createSelectHtml(json);
	$("#staffing").html(html);
	
	//表格数据
	datagrid = $('#datagrid').datagrid({
		url:contextPath + LIST_URL,
		queryParams:{
			orgId:orgId,
			isShowAll:0
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
            {field:'personId',checkbox:true,width:100,
            	formatter:function(value,data,rowIndex){
                    return data.personId;
	            }
            },
			{field:'personName',title:'姓名',width:200},
			{field:'sex',title:'性别',width:200,formatter:function(value,data,rowIndex){
				var sex = data.sex;
				var str = "";
				if(sex == "01"){
					str = "男";
				}else if(sex == "02"){
					str = "女";
				}else if(sex == "99"){
					str = "其他";
				}	
				return str;
			}
			},
			{field:'staffingName',title:'人员编制',width:200},
			{field:'levelName',title:'职级',width:300},
			{field:'educationName',title:'学历',width:100},
			{field:'opt_',title:'操作',width:200,formatter:function(value,data,rowIndex){
                var  opt= "<a href=\"#\" onclick=\"query('"+data.personId+"')\">查看</a>&nbsp;&nbsp;" 
                        + "<a href=\"#\" onclick=\"addOrUpdate('"+data.personId+"','1')\">编辑</a>";
                return  opt;
			}},
		]]
	
	})
}

//查询
function search(){
	var isShowAll = $('#isShowAll').is(':checked') == true ? "1":"0";
	$('#datagrid').datagrid('load',{
		orgId: $('#orgId').val(),
		personName: $('#personName').val(),
		staffing: $('#staffing').val(),
		isShowAll: isShowAll
	});
}


//查看详情
function query(personId){
	//地址
	var title = "组织机构人员详情";
	var url='/xzfy/jsp/organperson/queryDetail.jsp?personId='+personId;
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
function addOrUpdate(personId,statusType){
	
	if(statusType == 0){
		if(orgId == null || orgId == "" || orgId == "undifined"){
			$.MsgBox.Alert_auto("请选择组织机构！");
			return;
		}
	}	
    window.location.href = "/xzfy/jsp/organperson/addupdate.jsp?personId="
    	+personId+"&orgId="+orgId + "&orgName="+escape(orgName);
}

//删除,批量删除
function delByIds(){
	var selections = $('#datagrid').datagrid('getSelections');
	var ids = "";
	//拼接组织机构ID
	for(var i=0;i<selections.length;i++){
		ids += selections[i].personId;
		if(i!=selections.length-1){
			ids+=",";
		}
	}
	//未选中
	if(ids.length==0){
		$.MsgBox.Alert_auto("未选中任何记录！");
		return ;
	}
	else{
		$.MsgBox.Confirm ("提示", "是否确认删除选中的信息？", function(){
			var url=contextPath + DELETE_URL;
			var param = {personIds:ids};
			var json = tools.requestJsonRs(url,param);
			//返回参数判别
			if(json.rtState == true ){					
				$.MsgBox.Alert_auto("删除成功！",function(){
					//datagrid.datagrid('reload');
					$('#datagrid').datagrid('load',{
						orgId: $('#orgId').val(),
						personName: $('#personName').val(),
						staffing: $('#staffing').val(),
						isShowAll: $('#isShowAll').val()
					});
				});
			}
			else{
				$.MsgBox.Alert_auto("删除失败,请联系管理员！");
			}
		});
	}
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
