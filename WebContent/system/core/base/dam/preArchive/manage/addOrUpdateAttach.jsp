<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<% 
   int fileId=TeeStringUtil.getInteger(request.getParameter("fileId"),0);
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>

<%@ include file="/header/validator2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>新建/编辑附件信息</title>
</head>
<script type="text/javascript">
var fileId=<%=fileId %>;//档案主键
var sid=<%=sid %>;//档案附件主键
//初始化
function doInit(){
	new TeeSingleUpload({
		uploadBtn:"uploadBtn",
		callback:function(json){//回调函数，json.rtData
			/* alert(json.rtData[0].sid);
			alert(json.rtData[0].fileName);  */
			$("#attchId").val(json.rtData[0].sid);
		},
		post_params:{model:"dam"}//后台传入值，model为模块标志
	});
	
	if(sid>0){
		$("#attTr").show();
		getInfoBySid();
		
	}
}

//根据主键获取详情
function getInfoBySid(){
	var url=contextPath+"/TeeFileAttchController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var prc = json.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			var attModel=prc.attModel;
			if(attModel!=null){
				attModel['priv']=3;
			}
			var attObj=tools.getAttachElement(attModel,{});
			$("#attDiv").append(attObj);
		}
	}
}


//提交
function commit(){
	if($("#form1").valid()){
		  var url=contextPath+"/TeeFileAttchController/addOrUpdate.action";	
		  var para =  tools.formToJson($("#form1")) ;
		  var json = tools.requestJsonRs(url,para);
		  return json; 
	}
}
</script>
<body  onload="doInit()" style="background-color: #f2f2f2">
    <form id="form1" name="form1">
    <input type="hidden" name="sid" id="sid" value="<%=sid %>"/>
    <input type="hidden" name="fileId" id="fileId" value="<%=fileId %>"/>
    <table class="TableBlock" width="100%">
        <tr>
           <td style="text-indent: 10px">顺序号：</td>
           <td>
              <input type="text" name="sortNo" id="sortNo" required="true" positive_integer="true" style="height: 23px;width: 300px"/>
           </td>
        </tr>
        <tr>
           <td style="text-indent: 10px">责任人：</td>
           <td>
              <input type="text" name="manager" id="manager" required="true" style="height: 23px;width: 300px"/>
           </td>
        </tr>
        <tr>
           <td style="text-indent: 10px">文件字：</td>
           <td>
              <input type="text" name="wjz" id="wjz"  style="height: 23px;width: 300px"/>
           </td>
        </tr>
        <tr>
           <td style="text-indent: 10px">标题：</td>
           <td>
              <input type="text" name="title" id="title" required="true" style="height: 23px;width: 300px"/>
           </td>
        </tr>
        <tr>
           <td style="text-indent: 10px">页数：</td>
           <td>
              <input type="text" name="pageNum" id="pageNum"  no_negative_number="true" style="height: 23px;width: 300px"/>
           </td>
        </tr>
        <tr>
           <td style="text-indent: 10px">生成日期：</td>
           <td>
              <input type="text" name="pubTimeStr" id="pubTimeStr" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" required="true" style="height: 23px;width: 300px"/>
           </td>
        </tr>
        <tr style="display: none;" id="attTr">
           <td style="text-indent: 10px">附件：</td>
           <td>
              <div id="attDiv"></div>
           </td>
        </tr>
        <tr>
           <td style="text-indent: 10px">上传附件：</td>
           <td>
              <input id="uploadBtn" type="button" class="btn-win-white" value="上传"/>
              <input type="hidden" name="attchId" id="attchId" />
           </td>
        </tr>
        <tr>
           <td style="text-indent: 10px">备注：</td>
           <td>
              <textarea rows="6" cols="60" name="remark" id="remark"></textarea>
           </td>
        </tr>
        
    </table>  
  </form>
</body>
<script>
$("#form1").validate();
</script>
</html>