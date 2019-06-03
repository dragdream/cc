<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
int folderSid = TeeStringUtil.getInteger(request.getParameter("folderSid"), 0);
int rootFolderPriv = TeeStringUtil.getInteger(request.getParameter("rootFolderPriv"), 0);
int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建/编辑文件详情</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@include file="/header/upload.jsp"%>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">

.btns{
        padding:5px 8px;
		/* padding-left:22px; */
		text-align:center; 
		/* text-align:right; 
		background-repeat:no-repeat;
		background-position:6px center; */
		background-size:17px 17px;
		border-radius:5px;
		background-color:#e6f3fc;
		border:none;
		color:#000;
		outline:none;
		font-size: 12px;
		border: #abd6ea solid 1px ;
}
.TableBlock_page{
   border:2px solid #f2f2f2;
   margin-bottom: 10px;
}
</style>
<script type="text/javascript">
var sid = "<%=sid%>";
var ckedit ;
var uEditorObj;//uEditor编辑器
function doInit(){
	uEditorObj = UE.getEditor('content');//获取编辑器对象
	uEditorObj.ready(function(){//初始化方法
		uEditorObj.setHeight(200);
	    //设置默认字体大小
		if(sid > 0){
			getInfoById(sid);
		}
	});		
}
/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/fileNetdisk/getFileNetdiskById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			
			//bindJsonObj2Cntrl(prc);
			//ckedit.setData(prc.content);
			uEditorObj.setContent(prc.content);
			
			var priv = '<%=rootFolderPriv%>';
			if((priv & 4) == 4){//去掉删除浮动菜单
		    	  priv-=4;
			} 
			
			var attaches = prc.attacheModels;
			if(attaches){
				attaches.priv = priv;
				var fileItem = tools.getAttachElement(attaches);
				$("#fileContainer").append(fileItem);
			}
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}




function doSaveOrUpdate(){
	
		var url = "<%=contextPath %>/fileNetdisk/updateContent.action";
	    var para =  tools.formToJson($("#form1"));
	    para["content"] = uEditorObj.getContent();
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			
			$.MsgBox.Alert_auto("保存成功!");
			toReturn();
			//top.$.jBox.tip("保存成功!",'info',{timeout:1500});
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}

}

function toReturn(){ 
	//window.location.href = "<%=contextPath%>/system/core/base/fileNetdisk/fileManage/fileManage.jsp?sid=<%=folderSid%>";
    history.back();
}
</script>
</head>
<body onload="doInit();" style="padding-right: 10px">
<div id="toolbar" class="topbar clearfix">
       <div class="fl" style="position:static;">
		  <span style="font-size: 14px;font-weight: bold;">新建/编辑文件详情</span>
	   </div>
	   <div class = "right fr clearfix ">
	      <input type="button"  value="保存" class="btns" onclick="doSaveOrUpdate();"/>
		  <input type="button"  value="返回" class="btns" onclick="toReturn();"/>
       </div>
   </div>
   <br>
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<table align="center" width="95%" class="TableBlock_page" >
	<tr>
		<td class="TableData" width="" colspan="4" style="text-indent: 15px;">
			<b>文件备注内容</b>
		</td>
	</tr>
	<tr>
		<td class="TableData" width="" colspan="4" style="text-indent: 15px">
			<DIV style="text-indent: 15px">
				<textarea  id="content" name="content" class="BigTextarea" ></textarea>
			</DIV>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="10%;" style="text-indent: 15px;width:60px;">文件：</td>
		<td class="TableData" width="80%;" colspan="3">
			<div id="fileContainer" style="margin-left: 0px"></div> 
		</td>
	</tr>
	</table>
</form>
</body>
</html>