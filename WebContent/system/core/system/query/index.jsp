
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/upload.jsp" %>

<title>查询</title>

<style type="text/css">
	.query-info{
		width:100px;
		height:28px;
		line-height:28px;
		padding:0px 4px 0px 4px;
		vertical-align: middle;
		float:left;
		 border: 1px solid #dddddd;
		-moz-border-bottom-left-radius: 4px;
		-moz-border-top-left-radius:4px;
		
		-khtml-border-bottom-left-radius: 4px;
		-khtml-border-top-left-radius:4px;
		
		-webkit-border-bottom-left-radius: 4px;
		-webkit-border-top-left-radius:4px;
		
		border-bottom-left-radius:4px;
		border-top-left-radius:4px;
	}
</style>

<script type="text/javascript" src="<%=contextPath%>/system/frame/default/js/index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/system/query/query.js"></script>
<script type="text/javascript">

var QUERY_TYPE = ["系统","用户","邮件", "公告通知" , "公共网盘" , "通讯簿" ,"工作流"];
function doOnload(){
	var queryTypeLi = "";
	for(var i=1 ;i<QUERY_TYPE.length ; i++){
		queryTypeLi = queryTypeLi + "<li><a href=\"#\"  onclick=\"setQueryType(" + i + ");\">" + QUERY_TYPE[i]+ "</a></li>";
	}
	$("#queryTypeUl").append(queryTypeLi);
	
	$("#listDiv").html("<b>信息查询</b>");
}

function setQueryType(type){
	$("#queryTypeDesc").html(QUERY_TYPE[type] + "<span class=\"caret\"></span>");
	$("#queryType").val(type);
}


function doQuery(){
	$("#listDiv").empty();
	var queryType = $("#queryType").val();
	var queryText = $("#queryText").val();
	if(queryText == ""){
		$.jBox.tip("请输入信息");
		
		return;
	}
	
	if(queryType == '1'){//用户
		queryPerson(queryText);
	}else if(queryType == '2'){//邮件
		queryEmail(queryText);
	}else if(queryType == '3'){//公告通知
		queryNotify(queryText);	
	}else if(queryType == '4'){//公共网盘
		queryFile(queryText);	
	}else if(queryType == '5'){//通讯簿
		queryAddress(queryText);	
	}else if(queryType == '6'){//工作流
	
		queryWorkFlow(queryText);
	}

}

/**
 * 查询用户
 */
function queryPerson(queryText){
	var url =   "<%=contextPath %>/orgSelectManager/queryUserByUserIdOrUserName.action";
	var para = {userName:queryText};
	var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		if(prcs.length > 0){
			var tableStr = "<table class='TableList' width='88%' align='center'><tbody id='listTbody'>";
		    	 tableStr = tableStr + "<tr class='TableHeader'>"
			      	 + "<td width='100' align='center'>用户名</td>"
			    	 + "<td width='100' align='center'>姓名</td>"
			     	 +"<td nowrap  width='100'  align='center'>部门</td>"
			     	 +"<td nowrap  width='100'  align='center'>角色</td>"
			     	 +"</tr>";
			 for(var i =0 ; i< prcs.length ; i++){
				 var prc = prcs[i];
				 var sid = prc.sid;
				 var fontStr  = "";
				 tableStr = tableStr +"<tr class=''>"
				 +"<td nowrap  align='center'><font color='" + fontStr + "'><a href='javascript:void(0);'  onclick='toUserInfo(" +prc.sid + ")'>"+ prc.userId +"</a></font></td>"
				 +"<td nowrap   align='center'><font color='" + fontStr + "'>" + prc.userName + "</font></td>"	
				 +"<td nowrap   align='center'><font color='" + fontStr + "'>" + prc.deptName + "</font></td>"
				 +"<td nowrap   align='center'><font color='" + fontStr + "'>" + prc.userRoleName + "</font></td>"
				 + "</tr>";
			 }
			 tableStr = tableStr + "</tbody></table>";	
			 $("#listDiv").html(tableStr) ;
		}else{
			 $("#listDiv").html("未查到相关用户！") ;
		}
	}else{
		alert(jsonObj.rtMsg);
	}
}


/**
 * 查询邮件
 */
function queryEmail(queryText){
	var url =   "<%=contextPath %>/mail/selectMailForQueryIndex.action";
	var para = {key:queryText,maxSize:100};
	var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		if(prcs.length > 0){
			var tableStr = "<table class='TableList' width='88%' align='center'><tbody id='listTbody'>";
		    	 tableStr = tableStr + "<tr class='TableHeader'>"
			      	 + "<td width='100' align='center'>标题</td>"
			    	 + "<td width='100' align='center'>邮件位置</td>"
			     	 +"<td nowrap  width='100'  align='center'>时间</td>"
			     	 +"</tr>";
			 for(var i =0 ; i< prcs.length ; i++){
				 var prc = prcs[i];
				 var sid = prc.sid;
				 var fontStr  = "";
				 var sendTimeStr =   getFormatDateStr(prc.sendTime , "yyyy-MM-dd hh:mm:ss");
				 tableStr = tableStr +"<tr class=''>"
				 +"<td nowrap  align='center'><font color='" + fontStr + "'><a href='javascript:void(0);'  onclick='toEmailDetail(" +prc.sid + ","+prc.type+","+prc.ifBox+")'>"+ prc.subject +"</a></font></td>"
				 +"<td nowrap   align='center'><font color='" + fontStr + "'>" + prc.queryType + "</font></td>"	
				 +"<td nowrap   align='center'><font color='" + fontStr + "'>" + sendTimeStr + "</font></td>"
				 + "</tr>";
			 }
			 tableStr = tableStr + "</tbody></table>";	
			 $("#listDiv").html(tableStr) ;
		}else{
			 $("#listDiv").html("未查到相关邮件！") ;
		}
	}else{
		alert(jsonObj.rtMsg);
	}
}

/**
 * 邮件
 * @param type:类型，ifBox:分类箱id
 */
function toEmailDetail(id , type,ifBox ){
	var url = contextPath + "/mail/readMailForOther.action?type="+type+"&ifBox="+ifBox+"&mailId="+ id;
	
 	top.bsWindow(url ,"查看邮件详情",{width:"800",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

/**
 * 查询公告通知
 */
function queryNotify(queryText){
	var url =   "<%=contextPath %>/teeNotifyController/getNotifyByState.action";
	var para = {subject:queryText};
	var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		if(prcs.length > 0){
			var tableStr = "<table class='TableList' width='88%' align='center'><tbody id='listTbody'>";
		    	 tableStr = tableStr + "<tr class='TableHeader'>"
			      	 + "<td width='100' align='center'>发布人</td>"
			    	 + "<td width='100' align='center'>标题</td>"
			     	 +"<td nowrap  width='100'  align='center'>发布时间</td>"
			     	 +"</tr>";
			 for(var i =0 ; i< prcs.length ; i++){
				 var prc = prcs[i];
				 var sid = prc.sid;
				 var fontStr  = "";
				 var sendTimeStr =   getFormatDateStr(prc.sendTime , "yyyy-MM-dd hh:mm:ss");
				 tableStr = tableStr +"<tr class=''>"
				 +"<td nowrap  align='center'><font color='" + fontStr + "'><a href='javascript:void(0);'  onclick='jBoxNotifyDetail(" +prc.sid + ")'>"+ prc.subject +"</a></font></td>"
				 +"<td nowrap   align='center'><font color='" + fontStr + "'>" + prc.fromPersonName + "</font></td>"	
				 +"<td nowrap   align='center'><font color='" + fontStr + "'>" + sendTimeStr + "</font></td>"
				 + "</tr>";
			 }
			 tableStr = tableStr + "</tbody></table>";	
			 $("#listDiv").html(tableStr) ;
		}else{
			 $("#listDiv").html("未查到相关公告通知！") ;
		}
	}else{
		alert(jsonObj.rtMsg);
	}
}

/**
 * 查询通讯簿
 */
function queryAddress(queryText){
	var url =   "<%=contextPath %>/teeAddressController/queryAddress2PageIndex.action";
	var para = {psnName:queryText};
	var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		if(prcs.length > 0){
			var tableStr = "<table class='TableList' width='88%' align='center'><tbody id='listTbody'>";
		    	 tableStr = tableStr + "<tr class='TableHeader'>"
			      	 + "<td width='100' align='center'>姓名</td>"
			    	 + "<td width='100' align='center'>性别</td>"
			     	 +"<td nowrap  width='100'  align='center'>单位</td>"
			     	 +"</tr>";
			 for(var i =0 ; i< prcs.length ; i++){
				 var prc = prcs[i];
				 var sid = prc.sid;
				 var fontStr  = "";
				 var sexDesc = "男";
				 if(prc.sex == '1'){
					 sexDesc = "女";
				 }
				 tableStr = tableStr +"<tr class=''>"
				 +"<td nowrap  align='center'><font color='" + fontStr + "'><a href='javascript:void(0);'  onclick='jBoxAddressDetail(" +prc.sid + ")'>"+ prc.psnName +"</a></font></td>"
				 +"<td nowrap   align='center'><font color='" + fontStr + "'>" +sexDesc + "</font></td>"	
				 +"<td nowrap   align='center'><font color='" + fontStr + "'>" + prc.addDept + "</font></td>"
				 + "</tr>";
			 }
			 tableStr = tableStr + "</tbody></table>";	
			 $("#listDiv").html(tableStr) ;
		}else{
			 $("#listDiv").html("未查到相关通讯信息！") ;
		}
	}else{
		alert(jsonObj.rtMsg);
	}
}



/**
 * 查询公共网盘
 */
function queryFile(queryText){
	var url =   "<%=contextPath %>/fileNetdisk/queryFileNetdiskByName.action";
	var para = {fileName:queryText};
	var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		if(prcs.length > 0){
			var tableStr = "<table class='TableList' width='88%' align='center'><tbody id='listTbody'>";
		    	 tableStr = tableStr + "<tr class='TableHeader'>"
			      	 + "<td width='100' align='center'>文件名称</td>"
			    	 + "<td width='100' align='center'>创建时间</td>"
			     
			     	 +"</tr>";
			 for(var i =0 ; i< prcs.length ; i++){
				 var prc = prcs[i];
				 var sid = prc.sid;
				 var fontStr = "";
				 var fileNameStr = "<div class='fileItem' priv='" + 3 + "' sid='" + prc.attachSid +"' fileName='"+prc.fileName+"' fileExt='"+prc.fileExt+"'></div>";
				 tableStr = tableStr +"<tr class=''>"
				 +"<td nowrap  align='center'><font color='" + fontStr + "'><a href='javascript:void(0);'  >"+ fileNameStr +"</a></font></td>"
				// +"<td nowrap   align='center'><font color='" + fontStr + "'>" +prc.createUser + "</font></td>"	
				 +"<td nowrap   align='center'><font color='" + fontStr + "'>" + prc.createTime+ "</font></td>"
				 + "</tr>";
			 }
			 tableStr = tableStr + "</tbody></table>";	
			 $("#listDiv").html(tableStr) ;
			 
			 
			 $(".fileItem").each(function(i,obj){
					var attachModel = {fileName:$(obj).attr("fileName"),
					priv:parseInt($(obj).attr("priv"))
					,ext:$(obj).attr("fileExt"),sid:$(obj).attr("sid")};
					var fileItem = tools.getAttachElement(attachModel,{});
					$(obj).append(fileItem);
				});

		}else{
			 $("#listDiv").html("未查到相关网盘文件信息！") ;
		}
	}else{
		alert(jsonObj.rtMsg);
	}
}




/**
 * 查询公共工作流
 */
function queryWorkFlow(queryText){
	var url =   "<%=contextPath %>/workflow/quickSearch.action";
	var para = {psnName:queryText};
	var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		if(prcs.length > 0){
			var tableStr = "<table class='TableList' width='88%' align='center'><tbody id='listTbody'>";
		    	 tableStr = tableStr + "<tr class='TableHeader'>"
			      	 + "<td width='100' align='center'>流程名称</td>"
			    	 + "<td width='100' align='center'>流水号</td>"
			     	// +"<td nowrap  width='100'  align='center'>开始时间</td>"
			     	 +"</tr>";
			 for(var i =0 ; i< prcs.length ; i++){
				 var prc = prcs[i];
				 var sid = prc.sid;
				 var fontStr = "";
				 tableStr = tableStr +"<tr class=''>"

				 +"<td nowrap  align='center'><font color='" + fontStr + "'><a href='javascript:void(0);'  onclick='jBoxForwardDetail(" +prc.runId + "," + prc.flowId + ",0)'>"+ prc.runName +"</a></font></td>"
				 +"<td nowrap   align='center'><font color='" + fontStr + "'>" +prc.runId + "</font></td>"	
			///	 +"<td nowrap   align='center'><font color='" + fontStr + "'>" + prc.time + "</font></td>"
				 + "</tr>";
			 }
			 tableStr = tableStr + "</tbody></table>";	
			 $("#listDiv").html(tableStr) ;
		}else{
			 $("#listDiv").html("未查到相关流程！") ;
		}
	}else{
		alert(jsonObj.rtMsg);
	}
}



</script>
</head>
<body class="" topmargin="5" onload="doOnload();">

<form   method="post" name="form1">
 <table  class="" width="100%" align="center">
    <tr>
      <td nowrap class="TableData" width=""> 
     	  <div class="col-lg-6" style="width:150px;" >
		     <div class="input-group">
          		<input class="query-info" type="text" id="queryText" >
            	<div class="input-group-btn">
            	
	                <button tabindex="-1" id="queryTypeDesc" class="btn btn-default dropdown-toggle" data-toggle="dropdown" type="button">
	                	 用户
	                    <span class="caret"></span>
	                </button>
	                <ul class="dropdown-menu pull-right" role="menu" id="queryTypeUl" style="min-width:110px;left:0px;">
	              		 
	                </ul>
	                
	            <button tabindex="-1" class="btn btn-primary" type="button" style="margin-left:-5px;" onclick="doQuery()" >查询</button>
               </div>
             </div>
	      </div>
       </td> 
       <td>
       
       </td>
    </tr>
    </table>
    
    <input type="hidden" name="queryType" id="queryType" value='1'/>
</form>


<div id="listDiv" align="center" style="padding-top:15px;">
	
</div>
</body>
</html>