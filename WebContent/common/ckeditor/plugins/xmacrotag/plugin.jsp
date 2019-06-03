<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="background:#f0f0f0">

<head>
<%@ include file="/header/header2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>宏标记</title>
</head>

<body style="background:#f0f0f0">
<div style="padding:10px;font-size:12px">
	<table style="font-size:12px;width: 100%;border-spacing:0;">
		<tr style="height: 30px">
		   <td style="border-bottom: 1px solid #BBB" >#MACRO_表单名称</td>
		   <td style="border-bottom: 1px solid #BBB" width="20%"><a href="javascript:void(0)" onclick="insertMacroTag(1)">[添加]</a></td>
		</tr>
		<tr style="height: 30px">
		   <td style="border-bottom: 1px solid #BBB" >#MACRO_流程名称</td>
		   <td style="border-bottom: 1px solid #BBB"  width="20%"><a href="javascript:void(0)" onclick="insertMacroTag(2)">[添加]</a></td>
		</tr>
		<tr style="height: 30px">
		   <td style="border-bottom: 1px solid #BBB">#MACRO_流程开始时间</td>
		   <td style="border-bottom: 1px solid #BBB" width="20%"><a href="javascript:void(0)" onclick="insertMacroTag(4)">[添加]</a></td>
		</tr>
		<tr style="height: 30px">
		   <td style="border-bottom: 1px solid #BBB">#MACRO_流程结束时间</td>
		   <td style="border-bottom: 1px solid #BBB" width="20%"><a href="javascript:void(0)" onclick="insertMacroTag(5)">[添加]</a></td>
		</tr>
		<tr style="height: 30px">
		   <td style="border-bottom: 1px solid #BBB">#MACRO_流水号</td>
		   <td style="border-bottom: 1px solid #BBB" width="20%"><a href="javascript:void(0)" onclick="insertMacroTag(6)">[添加]</a></td>
		</tr>
		<tr style="height: 30px">
		   <td style="border-bottom: 1px solid #BBB">#MACRO_流程发起人姓名</td>
		   <td style="border-bottom: 1px solid #BBB" width="20%"><a href="javascript:void(0)" onclick="insertMacroTag(7)">[添加]</a></td>
		</tr>
		<tr style="height: 30px">
		   <td style="border-bottom: 1px solid #BBB">#MACRO_流程发起人ID</td>
		   <td style="border-bottom: 1px solid #BBB" width="20%"><a href="javascript:void(0)" onclick="insertMacroTag(8)">[添加]</a></td>
		</tr>
		<tr style="height: 45px">
		   <td style="border-bottom: 1px solid #BBB">#MACRO_公共附件
		       &nbsp;&nbsp; 附件序号：<input type="text" id="attnum" name="attnum" style="height:19px;width: 100px;"/>
		      <br>
		      <span style="color:red">说明：列出所有的或者第N个公共附件,附件序号为空，则为所有附件</span>
		   </td>
		   <td style="border-bottom: 1px solid #BBB" width="20%"><a href="javascript:void(0)" onclick="insertMacroTag(10)">[添加]</a></td>
		</tr>
		<tr style="height: 60px">
		   <td style="border-bottom: 1px solid #BBB">#MACRO_会签意见
		        <select id="fbType" name="fbType" style="height: 21px" >
		           <option value="1">按步骤实际编号</option>
		           <option value="2">按步骤设计编号</option>
		        </select>
		        <input type="text"name="num" id="num" style="height:19px;width: 100px;"/><br>
		                        格式表达式：<input type="text" name="expression" id="expression" style="height:19px;width: 220px;"/>
		        &nbsp;&nbsp;&nbsp;<a href="#" onclick="showIntroduce();">说明</a>
		        <div style="display: none;" id="introduceDiv">
		            {C}：表示意见内容<br>
					{B}：表示意见内容(过滤退回意见)<br>
					<!-- {F}：表示意见附件<br> -->
					{Y}：表示年<br>
					{M}：表示月<br>
					{D}：表示日<br>
					{H}：表示时<br>
					{I}：表示分<br>
					{S}：表示秒<br>
					{U}：表示用户姓名<br>
					{R}：表示角色<br>
					{P}：表示步骤名称<br>
					{SD}：表示短部门<br>
					{LD}：表示长部门<br>
		        </div>
		   </td>
		   <td style="border-bottom: 1px solid #BBB" width="20%"><a href="javascript:void(0)" onclick="insertMacroTag(9)">[添加]</a></td>
		</tr>
	</table>
</div>
<script>
	var contextPath = "<%=contextPath%>";

    function insertMacroTag(type){
		switch(type){
			case 1:parent.editor.insertHtml("#[MACRO_FORM_NAME]");break;
			case 2:parent.editor.insertHtml("#[MACRO_RUN_NAME]");break;
			case 3:parent.editor.insertHtml("#[MACRO_NUMBERING]");break;
			case 4:parent.editor.insertHtml("#[MACRO_BEGIN_TIME]");break;
			case 5:parent.editor.insertHtml("#[MACRO_END_TIME]");break;
			case 6:parent.editor.insertHtml("#[MACRO_RUN_ID]");break;
			case 7:parent.editor.insertHtml("#[MACRO_BEGIN_USERNAME]");break;
			case 8:parent.editor.insertHtml("#[MACRO_BEGIN_USERID]");break;
			case 9:
				var fbType=$("#fbType").val();
				var num=$("#num").val();
				var expression=$("#expression").val();
				if(fbType==1){
					if(num!=null&&num!=""){
						parent.editor.insertHtml("#[MACRO_SIGN"+num+"]["+expression+"]");
					}else{
						parent.editor.insertHtml("#[MACRO_SIGN]["+expression+"]");
					}
				}else{
					if(num!=null&&num!=""){
						parent.editor.insertHtml("#[MACRO_SIGN"+num+"*]["+expression+"]");
					}else{
						parent.editor.insertHtml("#[MACRO_SIGN*]["+expression+"]");
					}
				}
				
				//parent.editor.insertHtml("#[MACRO_FEEDBACK]");
				break;
			case 10:
				var attnum=$("#attnum").val();
				if(attnum!=null&&attnum!=""){
					parent.editor.insertHtml("#[MACRO_ATTACH"+attnum+"]");
				}else{
					parent.editor.insertHtml("#[MACRO_ATTACH]");
				}
				break;
			default:parent.editor.insertHtml("");
		}
    }
    
    var flag=0;
    function showIntroduce(){
    	if(flag==0){
    		$("#introduceDiv").show();
    		flag=1;
    	}else{
    		$("#introduceDiv").hide();
    		flag=0;
    	}
    }
</script>
</body>

</html>