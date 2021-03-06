var title = "";
var caseId = $('#caseId').val(); //案件ID
$(function() {
	datagrid = $('#datagrid').datagrid({
		url : '/caseInvestigationController/getListByCaseId.action?caseId='+caseId ,
		toolbar : '#toolbar',
		title : title,
		striped:true,
		singleSelect : true,
		checkbox : true,
		pagination : true,
		fit : true,
		fitColumns : true,
		idField : 'id',
		columns : [ [
			{
				field : 'id',
				checkbox : true,
				title : 'ID',
				align:'center'
			},
			{
    				field:'count',
    				title:'序号',
    				width:40,
    				align:'center',
    				formatter:function(value,rowData,rowIndex){
					return rowIndex+1;
				}
    			},
    		 {
 				field : 'investName',
 				title : '调查人姓名',
 				width : 170,
 				align:'center',
 				/* formatter:function(value,rowData,rowIndex){
				 return "<a style=''  href='javascript:void(0);' onclick='toLook("+rowIndex+")'>"+rowData.investName+"</a>"; 
			} */
  		 },
		    {
			field : 'startTime',
			title : '调查时间',
			width : 120,
			align:'center'
				
		    },  
		  {
			field : 'investType',
			title : '调查方式',
			align:'center',
			width : 100	 
		  } ,
			{
			field : 'respondent',
			title : '被调查人姓名',
			align:'center',
			width : 120
		  }, 
		  {
			field : 'respondentPhoneNum',
			title : '被调查人电话',
			align:'center',
			width : 150
		   },
		 
		  {field:'2',title:'操作',align:'center',width:150,formatter:function(e,rowData,index){
				return "<button style='height:25px;width:45px;padding:0;'  class='btn btn-mini btn-primary' href='javascript:void(0);' onclick='toUpdate("+index+")'>修改</button>&nbsp;&nbsp;"+
				
				"<button style='height:25px;width:45px;padding:0;'  class='btn btn-mini btn-danger' href='javascript:void(0);' onclick='del("+index+")'>删除</button>";
			}
		  }	
		] ]
	});
 });


function toAdd(){
	$("#form1").get(0).reset();
	$("#fyDocumentName").html("");
}
//保存
function save(){
	if(!checkForm()){
		return false;
	} 
	$(form1).ajaxSubmit({
        type: 'post', // 提交方式 get/post
        url: '/caseInvestigationController/save.action', // 需要提交的 url
        success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
            // 此处可对 data 作相关处理
        	$.jBox.tip("保存成功", 'info' , {timeout:1500});
        	$("#datagrid").datagrid("reload");
        }
    });
}

//表单校验
function checkForm(){
  return $("#form1").form('validate');
}
function toSee(index){
	$('#datagrid').datagrid('clearSelections');
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	var id = selection.id;
	bsWindow("caseTogether_look.jsp?caseId="+caseId, "并案查看", {
		width : "1100",
		height : "450",
			buttons:
			[
			 {name:"保存",classStyle:"btn btn-primary"},
		 	 {name:"关闭",classStyle:"btn btn-primary"}
			 ]
			,
		submit : function(v, h, c, b) {
			
			var result = h[0].contentWindow;
			
				if(v == "关闭"){
					return true;
				}
		}
	});  
	
}

$(function(){
	getSysCodeByParentCodeNo('FY_CASE_STATUS' , 'caseTypeSelect');
});


 function toUpdate(index) {
	 //先刷新本页面
   $('#datagrid').datagrid('clearSelections');
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	var id = selection.id;
	var caseId = selection.caseId;
	//通过调查管理id将本条数据信息回填
	 var json = tools.requestJsonRs("/caseInvestigationController/getById.action", {id: id});
	//后台返回对象后绑定到form表单
	 bindJsonObj2Cntrl(json.rtData);
	 var id = json.rtData.id;
	 $("#id").val(id);
	 //展示文件信息
	 initFiling("trial_investigation",id);
	 documentCtralInit("trial_investigation");	
} 
 
 function toLook(index) {
	   $('#datagrid').datagrid('clearSelections');
		datagrid.datagrid("unselectAll");
		if(index>=0){
			datagrid.datagrid("selectRow",index);
		}
		var selection = datagrid.datagrid("getSelected");
		var id = selection.caseInfoId;
		bsWindow("caseRegister_look.jsp?id="+id, "案件查看", {
		width : "1100",
		height : "450",
			buttons:
			[
		 	 {name:"关闭",classStyle:"btn btn-primary"}
			 ]
			,
		submit : function(v, h, c, b) {
			
			var result = h[0].contentWindow;
			
				if(v == "关闭"){
					return true;
				}
		}
	});  
	
} 

//删除一条调查管理 zck
function del(index){
	var msg = "您真的确定要删除吗？\n\n请确认！"; 
	if (confirm(msg)==true){ 
		$('#datagrid').datagrid('clearSelections');
		datagrid.datagrid("unselectAll");
		if(index>=0){
			datagrid.datagrid("selectRow",index);
		}
		var selection = datagrid.datagrid("getSelected");
		if(selection==null || selection==undefined){
			$.MsgBox.Alert_auto("请选择需要删除的信息");
			return;
		}
		var id = selection.id;
		var para = {'id':id};
		var url = "/caseInvestigationController/del.action";
		tools.requestJsonRs(url,para);
		$.jBox.tip("删除成功", 'info' , {timeout:1500});
		$('#datagrid').datagrid('clearSelections');
		$("#datagrid").datagrid("reload");
	}
}
//生成委托调查函
function entrust(){
	alert("文书等待开发!");
}
//生成调查笔录
function record(){
	alert("文书等待开发!");
}