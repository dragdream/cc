<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
	TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/header/header2.0.jsp"%>
<link rel="stylesheet" type="text/css" href="/system/subsys/topic/css/topic.css"/>
<script type="text/javascript" src="/system/subsys/topic/pagination/pagination-with-styles.js"></script>
<script src="/common/ueditor/ueditor.config.js"></script>
<script src="/common/ueditor/ueditor.all.min.js"></script>

<script type="text/javascript">
//项目主键
 var uuid="<%=uuid%>";
var loginPersonId = "<%=loginPerson.getUuid()%>";
var status=0;//项目状态
var uEditorObj;//uEditor编辑器
function doInit(){
	getProjectStatus();
	
	if(status==3){
		$("#addDiv").show();
	}else{
		$("#addDiv").hide();
	}
	
	getTopicReplyPage(uuid);
	uEditorObj = UE.getEditor('content');//获取编辑器对象
	uEditorObj.ready(function(){//初始化方法
		uEditorObj.setHeight(200);
	});
}


//根据项目主键  获取项目状态
function getProjectStatus(){
	var url=contextPath+"/projectController/getInfoByUuid.action";
	var json=tools.requestJsonRs(url,{uuid:uuid});
	if(json.rtState){
		var data=json.rtData;
		status=data.status;
	}
}



/**
 * 获取分页数据
 */
 var counter = 0;
function getTopicReplyPage(uuid){
	$('#pagination-demo1').pagination({
		dataSource: contextPath+'/projectCommunicationController/getCommunicationPage.action?projectId=' + uuid,
		pageSize: 5,
		goPage:function(number,attributes){
			counter = (parseInt(number) - 1) * parseInt(attributes.pageSize);
		},
		showNavigator:true,
		callback: function(data,pagination){
			var list = data.rows;
			if(list.length>0){
				var postDesc = "";
				jQuery.each(list,function(i,sysPara){
					counter ++;
					var editStr = "";
					var deleteStr = "";
					if(sysPara.createUserUuid == loginPersonId&&status==3){
						editStr = "<a href='#' onclick=setPoint('" +sysPara.sid + "') ><IMG src='../../img/icon_edit.png'> 编辑</a>&nbsp;&nbsp;";
						deleteStr = "<a href='javascript:void(0);' onclick=del('" +sysPara.sid + "')><IMG src='../../img/icon_delete.png'>删除</a>&nbsp;&nbsp;";
					}

					var avatar ="../../img/icon_touxiang.png";

					postDesc += "<div class='b_box_1'>"
							+ "		<div class='b_list'>"
							+ "			<table width='100%' border='0' cellSpacing=0 cellPadding=0>"
							+ "				<tr>"
							+ "					<td style='text-align:left;' class='b_list_L' vAlign=top>"
							+ "						<p style='display:inline-block;float:left;overflow:hidden;'>"
							+ "							<img style='width:50px;height:50px;display:inline-block;margin:20px 15px 0;border-radius:5px;' src='" + avatar + "' border=0>"
							+ "						</p><span style='height:50px;float:left;display:inline-block;;overflow:hidden;margin:25px 15px  0 0'>"
							+ "							<span style='font-weight:bold;margin-top:5px;'>" + sysPara.createUserName +"</span>"
							+ "							<div style='color:#999;margin-top:10px;'>" + sysPara.createTimeStr +"</div>"
							+ "						</span>"
							+ "					</td>"
							+ "				</tr>"
							+ "				<tr>"
							+ "					<td  class='b_list_R'>"
							+ "						<div>"
							+ "							<div class='b_list_R_1' style='border:none' >"
							+ "							</div>"
							+ "							<div class='b_list_R_2'>" + sysPara.content + "</div>"
							+ "							<div class='b_list_R_2' id='fileContainer_" + sysPara.sid + "'></div>"
							+ "							<div class='b_list_R_3'>"
							+ 									editStr
							+ 									deleteStr
							+ "							</div>"
							+ "							<span style='width:98%;margin:0 auto;display:block;border-bottom:1px dashed #cdcdcd;position:relative;margin-top:25px;'></span>"
							+ "						</div>"
							+ "					</td>"
							+ "				</tr>"
							+ "			</table>"
							+ "			<div class='clear'></div>"
							+ "		</div>"
							+ "</div>";
				});
				$("#contentList").html(postDesc); 

				
			}else{//隐藏分页标签
				$("#topicDiv").hide();
				$("#page").hide();
				messageMsg("暂且没有交流内容！", "message" ,'info' ,300);
			}
		}
	});
}


//删除
function del(sid){
	 $.MsgBox.Confirm ("提示", "是否确认删除该项目交流？", function(){
		 var url = contextPath + "/projectCommunicationController/delBySid.action";
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！");
				window.location.reload();
			}  
	  });
}


//新增/编辑  设置锚点
function setPoint(sid){
	var scrollWidth = $("#content").position().top;
	$("body").animate({scrollTop:scrollWidth},500);
	$("#cancelButton").show();
	$("#editButton").show();
	$("#saveButton").hide();
	$("#cId").val(sid);
	if(sid>0){
		var url=contextPath+"/projectCommunicationController/getInfoBySid.action";
		var json=tools.requestJsonRs(url,{sid:sid});
		if(json.rtState){
			var  data=json.rtData;
			uEditorObj.setContent(data.content);
		}else{
			$.MsgBox.Alert_auto("数据获取失败 ！");
		}
	}
	
}



function checkForm(){
	if(uEditorObj.getContent()==""||uEditorObj.getContent()==null){
		$.MsgBox.Alert_auto("请输入内容！");
		return false; 
	}
	return true;
}

//保存或者编辑
function doSaveOrUpdate(){
	if(checkForm()){
		var url =contextPath+"/projectCommunicationController/addOrUpdate.action";
		var jsonRs = tools.requestJsonRs(url,{content:uEditorObj.getContent(),projectId:uuid,sid:$("#cId").val()});
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("保存成功!");
			window.location.reload();
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}

//取消编辑
function cancelUpdate(){
	$("#cId").val(0);
	uEditorObj.setContent("");
	
	$("#cancelButton").hide();
	$("#editButton").hide();
	$("#saveButton").show();
}



</script>
	<style>
	.b_list_R_3 img{
		vertical-align:bottom;
	}
	.reply_img {
		margin-right:3px;
	}
	</style>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
	<!-- <div  id=""  class="topbar clearfix">
	   <div class="fl left">
	       <img id="img1" class = 'title_img' src="/common/zt_webframe/imgs/zsjl/tlq/icon_bankuaishezhi.png">
	       <span class="title">项目交流</span>
	   </div>
	   <div class="fr right">
	   </div>
	</div> -->

	<!-- 人员回复 -->
	<div class="photo" style="margin-top: 5px;" >
	 <!--  <DIV class=s_sear_1>
	  </DIV> -->
	  <DIV class=clear></DIV>
	  <div id="topicDiv"></div>
	  <div id="contentList"></div>
	  <div id="message"></div>
	  <DIV class="tb_tools no_mar" id="page">
	    <DIV class="paging">
	    </DIV>
	    <div id="pagination-demo1" class="app-pagination" style="float: right;" ></div>
	  </DIV>
	  <DIV class=clear></DIV>
</div>

<!-- 快速回复 -->
<div id="addDiv" style="display: none">
<div class="topbar clearfix" style="margin: 10px">
  <div class="fl left">
     <span style="font-size: 12px;font-weight: bold;">新增/编辑</span>
  </div>
  <div class="fr right">
     <input type="button" id="saveButton" class="btn-win-white" onclick="doSaveOrUpdate();" value="保存"  />
     <input type="button" style="display: none" id="editButton" class="btn-win-white" onclick="doSaveOrUpdate();" value="确认编辑"  />
     <input type="button" style="display: none"  id="cancelButton" class="btn-win-white" onclick="cancelUpdate();" value="取消编辑"  />
  </div>
</div>

 <form action="" method="post" name="form1" id="form1">
   <input type="hidden" name="cId" id="cId" value="">
   <table align="center" width="98%" class="TableBlock" style='margin:0 auto;'>
	 <tr>
	    <td class="TableData" colspan="2">
	    <A name=webEdit></A>
	    <textarea rows="5" cols="83" id="content" name="content" class="BigTextarea"></textarea>
	    </td>
	 </tr>
  </table>
</form>
</div>


</body>
</html>