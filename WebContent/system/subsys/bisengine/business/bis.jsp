<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/bsgrid.jsp" %>
<%@ include file="/header/upload.jsp" %>
<%
	String bisKey = request.getParameter("bisKey");
%>
<title>业务模型显示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
#queryTable td{
	padding:5px;
}

	.modal-test{
		width: 564px;
		height: auto;
		position: absolute;
		display: none;
		z-index: 999;
	}
	.modal-test .modal-header{
		width: 100%;
		height: 50px;
		background-color:#8ab0e6;
	}
	.modal-test .modal-header .modal-title{
		color: #fff;
		font-size: 16px;
		line-height:50px;
		margin-left:20px;
		float: left;
	}
	.modal-test .modal-header .modal-win-close{
		color:#fff;
		font-size: 16px;
		line-height:50px;
		margin-right:20px;
		float: right;
		cursor: pointer;
	}
	.modal-test .modal-body{
		width: 100%;
		height: auto;
		background-color:#f4f4f4;
	}
	.modal-test .modal-body ul{
		overflow: hidden;
		clear:both;
	}
	.modal-test .modal-body ul li{
		width: 510px;
		height: 30px;
		line-height: 30px;
		margin-top: 25px;
		margin-left: 20px;
	}
	.modal-test .modal-body ul li span{
		display: inline-block;
		float:left;
		vertical-align: middle;
	}
	.modal-test .modal-body ul li input{
		display: inline-block;
		float: right;
		width: 400px;
		height: 25px;
	}
	.modal-test .modal-footer{
		width: 100%;
		height: 60px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-footer input{
		margin-top:12px;
		float: right;
		margin-right:20px;
	}

</style>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var bisKey='<%=bisKey%>';
var datagrid;
var flowId=0;//绑定的流程
function doInit(){
	var url = contextPath + "/businessModelController/getBusinessModelByBisKey.action";
	var json = tools.requestJsonRs(url, {bisKey : bisKey});
	var data=json.rtData;
	//绑定的流程
	flowId=data.flowId;
	//给业务标题和业务名称赋值
	$("#businessTitle").text(data.businessTitle);
	document.title = data.businessTitle;
	var groupField = data.groupField;
	//获取查询字段动态渲染
	var queryIds=data.queryFields.split(",");
	var p="";
	var desc="";
	var name="";
	var types="";
	var controlModel="";
	var displayType="";
	var j=null;

    var searchModel={};
    for(var n=0;queryIds[0]!="" && n<queryIds.length;n++){
    	p=contextPath+"/bisTableField/getModelById.action";
    	j = tools.requestJsonRs(p, {sid : queryIds[n]});
    	desc=j.rtData.fieldDesc;
    	name=j.rtData.fieldName;
    	types=j.rtData.fieldType;
    	controlModel=j.rtData.fieldControlModel;
    	displayType=j.rtData.fieldDisplayType;

        if(types=="DATE"){
        	var b=name+"$begin";
        	var e=name+"$end";
        	$("#queryTable").append("<tr><td style=\"text-indent:10px\">"
        		+desc	
        	    +"&nbsp;&nbsp;:</td><td>"
        	    +"<input type=\"text\" style=\"height:23px;\"    id="+b+" name="+b+"  onFocus=\"WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\\'"+e+"\\')}'})\"	class=\"BigInput easyui-validatebox Wdate\" required=\"true\" />"
				+"&nbsp;至&nbsp;"
				+"<input type=\"text\" style=\"height:23px;\"  id="+e+"	name="+e+"  onFocus=\"WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\\'"+b+"\\')}'})\"  class=\"BigInput easyui-validatebox Wdate\" required=\"true\" />"
				+"</td></tr>"
        	);

        	 searchModel[b] = "";
        	 searchModel[e] = "";
    	}else if(types=="DATETIME"){
    		var b1=name+"$begin";
        	var e1=name+"$end";
    		$("#queryTable").append("<tr><td style=\"text-indent:10px\">"
            		+desc	
            	    +"&nbsp;&nbsp;:</td><td>"
            	    +"<input type=\"text\" style=\"height:23px;\" id="+b1+" name="+b1+" size=\"18\" maxlength=\"19\" onFocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\\'"+e1+"\\')}'})\"		class=\"BigInput easyui-validatebox Wdate\" required=\"true\"	/>"
    				+"&nbsp;至&nbsp;"
    				+"<input type=\"text\"  style=\"height:23px;\"   id="+e1+"	name="+e1+" size=\"18\" maxlength=\"19\"  onFocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\\'"+b1+"\\')}'})\"  class=\"BigInput easyui-validatebox Wdate\" required=\"true\"  />"
    				+"</td></tr>"
            	);
    		

       	 searchModel[b1] = "";
    	 searchModel[e1] = "";
    	}else if(displayType=="RADIO"||displayType=="SELECT"){
    		var jsonss=tools.strToJson(controlModel);
    		$("#queryTable").append("<tr><td style=\"text-indent:10px\" >"
            		+desc	
            	    +"&nbsp;&nbsp;:</td><td>"
            	    +"<select name=\""+name+"\" id=\""+name+"\">"
    				+"<option value=\"\"></option>"
    				+"</select>"
    				+"</td></tr>"
            	);
    		getSysCodeByParentCodeNo(jsonss.sysno , name);
    		
       	 searchModel[name] = "";
    	}else if(displayType=="CHECKBOX"){
    		var jsonss=tools.strToJson(controlModel);
    		$("#queryTable").append("<tr><td style=\"text-indent:10px\" >"
            		+desc	
            	    +"&nbsp;&nbsp;:</td><td>"
            	    +"<select name=\""+name+"\" id=\""+name+"\">"
    				+"<option value=\"\"></option>"
    				+"<option value=\"0\">未选中</option>"
    				+"<option value=\"1\">选中</option>"
    				+"</select>"
    				+"</td></tr>"
            	);
         
       	 searchModel[name] = "";
    	}else if(displayType=="SINGLEPERSON"||displayType=="CURRENTUSER"){
    		$("#queryTable").append("<tr><td style=\"text-indent:10px\" >"
            		+desc	
            	    +"&nbsp;&nbsp;:</td><td>"
            	    + "<input id=\""
					+ name
					+ "\" name=\""
					+ name
					+ "\" type=\"text\" style=\"display:none\"> "
					+ "<input id=\""
					+ name
					+ "_Desc\" name=\""
					+ name
					+ "_Desc\""
					+ " class=\"Text\" onClick=\"selectSingleUser(['"+ name+ "','"+ name+ "_Desc'])\"  readonly=\"readonly\"  style=\"height:23px;\" />"
					+ "<a href=\"javascript:void(0);\" class=\"orgClear\" onClick=\"clearData('"
					+ name + "','" + name
					+ "_Desc')\">清空</a>"
    				+"</td></tr>"
            	);
    		 searchModel[name] = "";
    	}else if(displayType=="SINGLEDEPT"||displayType=="CURRENTDEPT"){
    		$("#queryTable").append("<tr><td style=\"text-indent:10px\">"
            		+desc	
            	    +"&nbsp;&nbsp;:</td><td>"
            	    + "<input id=\""
					+ name
					+ "\" name=\""
					+ name
					+ "\" type=\"text\" style=\"display:none\"> "
					+ "<input id=\""
					+ name
					+ "_Desc\" name=\""
					+ name
					+ "_Desc\""
					+ " class=\"Text\" onClick=\"selectSingleDept(['"+ name+ "','"+ name+ "_Desc'])\"  readonly=\"readonly\" style=\"height:23px;\" />"
					+ "<a href=\"javascript:void(0);\" class=\"orgClear\" onClick=\"clearData('"
					+ name + "','" + name
					+ "_Desc')\">清空</a>"
    				+"</td></tr>"
            	);	
    		 searchModel[name] = "";
    	}
    	else if(displayType=="SINGLEROLE"||displayType=="CURRENTROLE"){
    		$("#queryTable").append("<tr><td style=\"text-indent:10px\">"
            		+desc	
            	    +"&nbsp;&nbsp;:</td><td>"
            	    + "<input id=\""
					+ name
					+ "\" name=\""
					+ name
					+ "\" type=\"text\" style=\"display:none\"> "
					+ "<input id=\""
					+ name
					+ "_Desc\" name=\""
					+ name
					+ "_Desc\""
					+ " class=\"Text\" onClick=\"selectSingleRole(['"+ name+ "','"+ name+ "_Desc'])\"  readonly=\"readonly\"    style=\"height:23px;\" />"
					+ "<a href=\"javascript:void(0);\" class=\"orgClear\" onClick=\"clearData('"
					+ name + "','" + name
					+ "_Desc')\">清空</a>"
    				+"</td></tr>"
            	);
    		 searchModel[name] = "";
    	}
    	
    	
    	
    	
    	
    	else{
    		$("#queryTable").append("<tr><td style=\"text-indent:10px\">"
    				+desc
    			    +"&nbsp;&nbsp;:</td><td>"
    				+"<input type='text' style=\"height:23px;\"  id='"+name+"' name='"+name+"'  class='BigInput'/>"
    		        +"</td></tr>");
     	  searchModel[name] = "";
    	} 
    }	
    //将searchModel放到隐藏域中
    $("#searchModel").val(tools.jsonObj2String(searchModel));
    
    
    //获取表格的主键
    var pkurl = contextPath + "/businessModelController/getPkIdByBisKey.action";
	var pkJson = tools.requestJsonRs(pkurl, {bisKey : bisKey});
    var pk=pkJson.rtData;
    
	//获取表头字段的 id字符串
	var headIds=data.headerFields.split(",");
	var path="";
	var fDesc="";
	var fName="";
	var jsonRs=null;
	var fields = [];
    for(var i=0;headIds[0]!="" && i<headIds.length;i++){
    	path=contextPath+"/bisTableField/getModelById.action";
    	jsonRs = tools.requestJsonRs(path, {sid : headIds[i]});
    	fDesc=jsonRs.rtData.fieldDesc;
    	fName=jsonRs.rtData.fieldName;
    	fDisplayType=jsonRs.rtData.fieldDisplayType;
    	if(fDisplayType=="CHECKBOX"||fDisplayType=="RADIO"||fDisplayType=="SELECT"||fDisplayType=="SINGLEPERSON"||fDisplayType=="MANYPERSON"||fDisplayType=="SINGLEDEPT"||fDisplayType=="MANYDEPT"||fDisplayType=="SINGLEROLE"||fDisplayType=="MANYROLE"||fDisplayType=="CURRENTUSER"||fDisplayType=="CURRENTDEPT"||fDisplayType=="CURRENTROLE"||fDisplayType=="CURRENTTIME"||fDisplayType=="CURRENTDATE"){
    		
    		fields.push({field:fName+"_DESC",title:fDesc,width:1});
    		
    	}else if(fDisplayType=="FILEUPLOAD"){//附件
    	    fields.push({field:fName,title:fDesc,width:1,formatter:function(data,rowData){
    	    	var render = [];
				for(var i=0;i<data.length;i++){
				
					render.push("<p class='attach' fileName='"+data[i].fileName+"' ext='"+data[i].ext+"' sid='"+data[i].sid+"'></p>");
				}
				return render.join("");
    	    }});
    		
    	}else{
    		fields.push({field:fName,title:fDesc,width:1});
    	}
    	
    }
    
    fields.push({field:"operate",title:"操作",width:1,formatter : function(e, rowData){
    	 //获取操作字符串    
    	 var Str="";
    	 var operations=data.businessOperation.split(",");
 	     for(var j=0;j<operations.length;j++){
 	    	if(operations[j]=="update"){
	    		Str+="<a href=\"#\" onclick=\"javascript:addOrUpdate('update','"+rowData[pk]+"');\">修改</a>&nbsp;&nbsp;";
	    	}else if(operations[j]=="delete"){
	    		Str+="<a href=\"#\" onclick=\"javascript:del('"+rowData[pk]+"');\">删除</a>&nbsp;&nbsp;";
	    	}else if(operations[j]=="view"){
	    		Str+="<a href=\"#\" onclick=\"javascript:view('"+rowData[pk]+"');\">查看</a>&nbsp;&nbsp;";
    	    } 
 	     }
 	    return Str;
      }});

    //动态渲染操作
    var ope=data.businessOperation.split(",");
     for(var n=0;n<ope.length;n++){  
    	if(ope[n]=="add"){
          $("#commonOperate").append("<button class=\"btn-win-white\" onclick=\"javascript:addOrUpdate('add',0);\" >新建</button>&nbsp;&nbsp;");
   	   }else if(ope[n]=="export"){
   		  $("#commonOperate").append("<button class=\"btn-win-white\" onclick=\"javascript:exportList();\" >导出</button>&nbsp;");
   	   }else if(ope[n]=="query"){
   		  $("#query").append("<button class=\"btn-win-white modal-menu-test \" onclick='$(this).modal();'>查询</button>");
	   } 
     }
    

       
        //获取分组字段
        var groupField=data.groupField;
       
        var path2=contextPath+"/bisTableField/getModelById.action";
        if(groupField!=""){
        	var json2 = tools.requestJsonRs(path2, {sid : groupField});	
        	var groupType=json2.rtData.fieldDisplayType;
        	var groupName="";
        	if(groupType=="CHECKBOX"||groupType=="RADIO"||groupType=="SELECT"||groupType=="SINGLEPERSON"||groupType=="MANYPERSON"||groupType=="SINGLEDEPT"||groupType=="MANYDEPT"||groupType=="SINGLEROLE"||groupType=="MANYROLE"){
            	groupName=json2.rtData.fieldName+"_DESC";
        	}else{
        		groupName=json2.rtData.fieldName;
        	}
        }
    	
        var param= tools.formToJson($("#form1"));
        
        datagrid=$('#datagrid').datagrid({
    		url:contextPath + '/businessModelController/getTable.action',
    		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
    		queryParams:param,
    		method:"post",
    		pagination:true,
    		singleSelect:false,
    		toolbar:'#toolbar',//工具条对象
    		checkbox:false,
    		border:false,
    		pageSize: 10,
    		//idField:'formId',//主键列
    		fitColumns:true,//列是否进行自动宽度适应
    		columns:[fields],
    		onLoadSuccess:function(){
	        	$(".attach").each(function(i,obj){
					var att = {priv:1+2,fileName:obj.getAttribute("fileName"),ext:obj.getAttribute("ext"),sid:obj.getAttribute("sid")};
					var attach = tools.getAttachElement(att,{});
					$(obj).append(attach);
				});
	        }
        });
       
}


//点击添加或者编辑
function addOrUpdate(ope,pkid){
	if(ope=="add"){//新建
		
		if(flowId!=0){//绑定了流程
			var url=contextPath+"/flowRun/createNewWork.action";
		    var json=tools.requestJsonRs(url,{fType:flowId,businessKey:bisKey});
		    if(json.rtState){
		    	openFullWindow(contextPath+"/system/core/workflow/flowrun/prcs/index.jsp?runId="+json.rtData.runId+"&frpSid="+json.rtData.frpSid+"&flowId="+flowId);
		    }
		}else{
			var url=contextPath+"/system/subsys/bisengine/business/bisAddOrUpdate.jsp?bisKey="+bisKey+"&operate="+ope+"&pkid="+pkid;	
		    window.location.href=url;
		}
	}else{
		var url=contextPath+"/system/subsys/bisengine/business/bisAddOrUpdate.jsp?bisKey="+bisKey+"&operate="+ope+"&pkid="+pkid;	
	    window.location.href=url;
	}
	
}
//点击高级查询
function query(){
	$('#searchDiv').modal('show');
}
//查询
function doSearch(){
	//重新拼接searchModel的值 
	var search=tools.formToJson($('#queryTable'));
	$("#searchModel").val(tools.jsonObj2String(search));
	//获取查询字段  和  值
	var newpara = tools.formToJson($("#form1"));
	datagrid.datagrid('load',newpara);
	 //先重置高级查询的内容   然后隐藏高级查询的表格
	$(".modal-win-close").click(); 
}
//查看
function  view(pkid){
	var url=contextPath+"/system/subsys/bisengine/business/bisView.jsp?bisKey="+bisKey+"&pkid="+pkid;	
    window.location.href=url;
}

//删除
function  del(pkid){
	if(window.confirm("是否确认删除该数据")){
		var url=contextPath+"/businessModelController/del.action";
		var json= tools.requestJsonRs(url, {bisKey : bisKey,pkId:pkid}); 
		if (json.rtState) {
			datagrid.datagrid("reload");
			return true;
		}
		alert(json.rtMsg);
	}
}

//导出
function exportList(){
	//查询条件
	var searchModel=$("#searchModel").val();
	var options = $("#datagrid" ).datagrid("getPager" ).data("pagination" ).options;
    var pageNumber = options.pageNumber;
    var pageSize=options.pageSize;
	$("#exportIframe").attr("src",contextPath+"/businessModelController/export.action?bisKey="+bisKey+"&pageNumber="+pageNumber+"&pageSize="+pageSize+"&searchModel="+searchModel);
}
</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">

  <div id="toolbar" class = "topbar clearfix">
     <div class="fl" style="position:static;">
		<span class="title"><h4 id="businessTitle" class="module_header"></h4></span>
	 </div>
	 <div class = "right fr clearfix">
	  	<span id="commonOperate"></span>
	  	<span id="query"></span>
	  </div>
  </div>
  <table id="datagrid" fit="true" ></table>


 <!-- Modal -->
 <div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			高级查询
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body" id="mailBoxForm">
		<form id="form1" name="form1" method="post">
			<table  style="width:100%;font-size:12px" id="queryTable"  >
			   
			</table>
			<input type="hidden" id="bisKey" value="<%=bisKey%>" name="bisKey"/>
		    <input type="hidden" id="searchModel" name="searchModel"/> 
		</form>
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="doSearch();" value = '查询'/>
	</div>
</div>    

      <iframe id="exportIframe" style="display:none"></iframe>
</body>
      
</html>
