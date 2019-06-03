<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html  style='overflow:hidden;'>
<%
   //督办任务主键
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);

   //获取父任务的主键
    int parentId=TeeStringUtil.getInteger(request.getParameter("parentId"),0);
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<title>新建/编辑督办任务</title>
</head>
<script>
var sid=<%=sid %>;
var parentId=<%=parentId %>;
//初始化
function doInit(){
	var ttAttach = new TeeSimpleUploadRender({
		fileContainer:"upfileList"
	});
	
	if(sid>0){
		getInfoBySid();
	}
	
	
}
//获取详情
function getInfoBySid(){
	var url=contextPath+"/supervisionController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
		
		$.each(data.attachmentsMode,function(i,v){
			var attachElement = tools.getAttachElement(v,{});
			$("#attachList").append(attachElement);
	    });
	}
}

//验证
function check(){
	var supName=$("#supName").val();
	var typeName=$("#typeName").val();
	var leaderName=$("#leaderName").val();
	var managerName=$("#managerName").val();
	var beginTimeStr=$("#beginTimeStr").val();
	var endTimeStr=$("#endTimeStr").val();
	if(supName==""||supName==null){
		$.MsgBox.Alert_auto("请填写工作事项！");
		return false;
	}
	if(typeName==""||typeName==null){
		$.MsgBox.Alert_auto("请选择所属分类！");
		return false;
	}
	if(leaderName==""||leaderName==null){
		$.MsgBox.Alert_auto("请选择责任领导！");
		return false;
	}
	if(managerName==""||managerName==null){
		$.MsgBox.Alert_auto("请选择主办人！");
		return false;
	}
	if(beginTimeStr==""||beginTimeStr==null){
		$.MsgBox.Alert_auto("请选择开始时间！");
		return false;
	}
	if(endTimeStr==""||endTimeStr==null){
		$.MsgBox.Alert_auto("请选择结束时间！");
		return false;
	}
	return true;
}
//保存 / 提交
function save(status,callback){
	
	if(check()){
		var url=contextPath+"/supervisionController/addOrUpdate.action";
		var param=tools.formToJson("#form1");
		param['status']=status;
		 $("#form1").ajaxSubmit({
	           url:url,
	           iframe: true,
	           data: param,
	           success: function(res) {
	        	   callback(true);
	           },
	           error: function(arg1, arg2, ex) {
	                 $.MsgBox.Alert_auto("操作出错！");
	           },
	           dataType: 'json'
	      });
	}
}


//选择分类  
function selectType(){
	var url=contextPath+"/system/subsys/supervise/supervisionType/selectParent.jsp?sid="+sid;
	top.bsWindow(url ,"选择分类",{width:"400",height:"200",buttons:
		[
		 {name:"选择",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="选择"){		
			var selections =cw.$('#treeGrid').treegrid('getSelections');
			$("#typeId").val(selections[0].sid);
			$("#typeName").val(selections[0].typeName);
			return true;
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}


</script>
<body onload="doInit()" style="background-color: #f2f2f2;overflow: auto;">
    <form method="post" name="form1" id="form1" enctype="multipart/form-data" >
    <input type="hidden" name="sid" id="sid" value="<%=sid%>"/>
    <input type="hidden" name="parentId" id="parentId" value="<%=parentId%>"/>
      <table class="TableBlock" width="100%">
         <tr>
            <td style="text-indent: 10px">工作事项：</td>
            <td>
              <input type="text" style="width: 350px;height: 23px" name="supName" id="supName"/>
            </td>
         </tr>
         <tr>
            <td style="text-indent: 10px">所属类别：</td>
            <td>
              <input type="hidden" name="typeId" id="typeId"/>
              <input type="text" style="width: 350px;height: 23px" name="typeName" id="typeName" readonly="readonly"/>
              &nbsp;&nbsp;<a href="#" onclick="selectType()">选择
            </td>
         </tr>
          <tr>
            <td style="text-indent: 10px">责任领导：</td>
            <td>
              <input name="leaderId" id="leaderId" type="hidden"/>
			  <input class="BigInput readonly" type="text" id="leaderName" name="leaderName" style="height:23px;width:350px"  readonly/>
              <span class='addSpan'>
			   <img src="<%=contextPath %>/system/subsys/supervise/imgs/icon_select.png" onclick="selectSingleUser(['leaderId','leaderName'],'14')" value="选择"/>
				 &nbsp;&nbsp;
				<img src="<%=contextPath %>/system/subsys/supervise/imgs/icon_cancel.png" onclick="clearData('leaderId','leaderName')" value="清空"/>
			</span>
            </td>
         </tr>
         <tr>
            <td style="text-indent: 10px">主办人：</td>
            <td>
              <input name="managerId" id="managerId" type="hidden"/>
			  <input class="BigInput readonly" type="text" id="managerName" name="managerName" style="height:23px;width:350px"  readonly/>
              <span class='addSpan'>
			   <img src="<%=contextPath %>/system/subsys/supervise/imgs/icon_select.png" onclick="selectSingleUser(['managerId','managerName'],'14')" value="选择"/>
				 &nbsp;&nbsp;
				<img src="<%=contextPath %>/system/subsys/supervise/imgs/icon_cancel.png" onclick="clearData('managerId','managerName')" value="清空"/>
			</span>
            </td>
         </tr>
         <tr>
            <td style="text-indent: 10px">协办人：</td>
            <td>
              <input name="assistIds" id="assistIds" type="hidden"/>
			  <textarea class="BigTextArea readonly" type="text" id="assistNames" name="assistNames" style="width: 450px;height: 100px" readonly="readonly"></textarea>
              <span class='addSpan'>
			   <img src="<%=contextPath %>/system/subsys/supervise/imgs/icon_select.png" onclick="selectUser(['assistIds','assistNames'],'14')" value="选择"/>
				 &nbsp;&nbsp;
				<img src="<%=contextPath %>/system/subsys/supervise/imgs/icon_cancel.png" onclick="clearData('assistIds','assistNames')" value="清空"/>
			   </span>
            </td>
         </tr>
         <tr>
            <td style="text-indent: 10px">开始时间：</td>
            <td>
               <input type="text" id='beginTimeStr' name='beginTimeStr' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTimeStr\')}'})" class="Wdate BigInput" style="width: 200px" />
            </td>
         </tr>
         <tr>
            <td style="text-indent: 10px">结束时间：</td>
            <td>
              <input type="text" id='endTimeStr' name='endTimeStr' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTimeStr\')}'})" class="Wdate BigInput" style="width: 200px" />
            </td>
         </tr>
         <tr>
            <td style="text-indent: 10px">督办内容：</td>
            <td>
              <textarea style="width: 550px;height: 100px" name="content" id="content"></textarea>
            </td>
         </tr>
         <tr>
            <td style="text-indent: 10px">相关附件：</td>
            <td align="left" >
              <div id="attachList"></div>
			  <div id="upfileList"></div>
            </td>
         </tr>
      </table>
    </form>
</body>
</html>