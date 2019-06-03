<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String caseId=request.getParameter("caseId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/processbar/jquery.progressbar.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/cmstpls/2/js/script.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/jquery.form.min.js"></script>
<title>归档管理</title>
</head>
<body>
	<form action="<%=contextPath %>/teeXZFYHearingmanagementController/save.action"  method="post" name="form1" id="form1" style="margin-top:20px;">
		<table id="upload" align="center" width="90%" class="TableBlock">
		<!-- 文件计数的fid -->
		<input type="hidden" name="fid" id="fid" value="1"/>
		<!-- caseId -->
		<tr id="g">
			<td class="TableData" style="font-size:24px; height:50px;">归档管理目录</td>
			<td class="TableData"><a class="glyphicon glyphicon-plus"  href="javascript:void(0);" style="color:green;font-size:25px;" onclick="fileUpload()"></a></td>
		</tr>
		<tr id="a">
			<td class="TableData" style="">
				<img src=""/>
				<input name="code1" id="code1" type="hidden" value="1" />
				<input name="security1" id="security1" type="text" value="申请复议材料" readonly="readonly"/>
				<span style="color:red;font-size:30px;display:block; margin-top: -13px; margin-left: 181px;">*</span>
			</td>
			<td class="TableData">
				<input type="button" class="btn btnactiv" style="background-color:blue;" value="选择" onclick="choose(this)"/>
				<input type="button" class="btn btnactiv" style="background-color:yellow" value="上传" onclick="addFile(this)"/>
			</td>
		</tr>
		<tr id="b">
			<td class="TableData" >
				<img src=""/>
				<input name="code2" id="code2" type="hidden" value="2" />
				<input name="security2" id="security2" type="text" value="初审报告" onchange="changg(this);"/>
			</td>
			<td class="TableData">
				<input type="button" class="btn btnactiv" style="background-color:blue;" value="选择" onclick="choose(this)"/>
				<input type="button" class="btn btnactiv" style="background-color:yellow" value="上传" onclick="addFile(this)"/>
				<input type="button" class="btn btnactiv" style="background-color:red" value="删除" onclick="del(this)" />
			</td>
		</tr>
		<tr id="c">
			<td class="TableData" >
				<img src=""/>
				<input name="code3" id="code3" type="hidden" value="3" />
				<input name="security3" id="security3" type="text" value="答复材料" readonly="readonly"/>
				<span style="color:red;font-size:30px;  display:block;  margin-top: -13px;margin-left: 181px;">*</span>
			</td>
			<td class="TableData">
				<input type="button" class="btn btnactiv" style="background-color:blue;" value="选择" onclick="choose(this)"/>
				<input type="button" class="btn btnactiv" style="background-color:yellow" value="上传" onclick="addFile(this)"/>
			</td>
		</tr>
		<tr id="d">
			<td class="TableData" >
				<img src=""/>
				<input name="code4" id="code4" type="hidden" value="4" />
				<input name="security4" id="security4" type="text" value="审理材料" onchange="changg(this);"/>
			</td>
			<td class="TableData">
				<input type="button" class="btn btnactiv" style="background-color:blue;" value="选择" onclick="choose(this)"/>
				<input type="button" class="btn btnactiv" style="background-color:yellow" value="上传" onclick="addFile(this)"/>
				<input type="button" class="btn btnactiv" style="background-color:red" value="删除" onclick="del(this)" />
			</td>
		</tr>
		<tr id="e">
			<td class="TableData" >
				<img src=""/>
				<input name="code5" id="code5" type="hidden" value="5" />
				<input name="security5" id="security5" type="text" value="复议决定" readonly="readonly"/>
				<span style="color:red;font-size:30px; display:block; margin-top: -13px;margin-left: 181px;">*</span>
			</td>
			<td class="TableData">
				<input type="button" class="btn btnactiv" style="background-color:blue;" value="选择" onclick="choose(this)"/>
				<input type="button" class="btn btnactiv" style="background-color:yellow" value="上传" onclick="addFile(this)"/>
			</td>
		</tr>
		<tr id="f">
			<td class="TableData" >
				<img src=""/>
				<input name="code6" id="code6" type="hidden" value="6" />
				<input name="security6" id="security6" type="text" value="审批文书" onchange="changg(this);"/>
			</td>
			<td class="TableData">
				<input type="button" class="btn btnactiv" style="background-color:blue;" value="选择" onclick="choose(this)"/>
				<input type="button" class="btn btnactiv" style="background-color:yellow" value="上传" onclick="addFile(this)"/>
				<input type="button" class="btn btnactiv" style="background-color:red" value="删除" onclick="del(this)" />
			</td>
		</tr>		
	</table>
</form>

<script>
var param = []; //归档目录数据
var paramss = [];//文件数据
var cfType=1;//归档类型
var caseId='<%=caseId%>';//案件id
var count=0;//是否修改
var json='';//后台获取数据
var json1='';//后台获取数据
 //初始化
$(function(){
	getSysCodeByParentCodeNo('FY_DATE' , 'select_deadline');
	getSysCodeByParentCodeNo('FY_SECURITY' , 'select_security');
	//判断是否编辑
	var url = "<%=contextPath%>/XZFYFileCaseController/isDo.action?caseId='"+caseId+"'&cfType='"+cfType+"'";
	var count1=tools.requestJsonRs(url);
     count =count1.rtData;
	if(count>0){
		//去除添加页面的tr
		$("#a").remove();
		$("#b").remove();
		$("#c").remove();
		$("#d").remove();
		$("#e").remove();
		$("#f").remove();
		$("#g").remove();
		$("#upload").empty();
		//回显目录数据
		var url = "<%=contextPath%>/XZFYFileCaseController/listFile.action?caseId='"+caseId+"'&cfType='"+cfType+"'";
		 json=tools.requestJsonRs(url);
		var uploadHtml="";
		//回显文件数据
		var url = "<%=contextPath%>/XZFYFileCaseController/listFiles.action?caseId='"+caseId+"'&cfType='"+cfType+"'";
		json1=tools.requestJsonRs(url); 
		uploadHtml+="<tr>";
		uploadHtml+="<td class=\"TableData\" style=\"font-size:24px; height:50px;\">归档管理目录</td>";
		uploadHtml+="<td class=\"TableData\"><a class=\"glyphicon glyphicon-plus\"  href=\"javascript:void(0);\" style=\"color:green;font-size:25px;\" onclick=\"fileUpload1()\"></a></td>";
		uploadHtml+="</tr>";
		if(cfType==1){
			for(var i=0;i<json.rtData.length;i++){
				if(i<6 &&i>0){
					//判断奇偶
					if(i%2==0){
						uploadHtml+="<tr>";
						uploadHtml+="<td class=\"TableData\">";
						uploadHtml+="<input name=\"code"+json.rtData[i].fileTypeCode+"\" id=\"code"+json.rtData[i].fileTypeCode+"\" type=\"hidden\" value=\""+json.rtData[i].fileTypeCode+"\" />";
						uploadHtml+="<input name=\"security"+json.rtData[i].fileTypeCode+"\" id=\"security"+json.rtData[i].fileTypeCode+"\" type=\"text\" value=\""+json.rtData[i].fileTypeName+"\" readonly=\"readonly\"/>";
						uploadHtml+="<span style=\"color:red;font-size:30px; display:block; margin-top: -13px;margin-left: 181px;\">*</span>";
						uploadHtml+="</td>";
						uploadHtml+="<td class=\"TableData\">";
						uploadHtml+="<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:blue;\" value=\"选择\" onclick=\"choose(this)\"/>";
						uploadHtml+="<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:yellow\" value=\"上传\" onclick=\"addFile(this)\"/>";
						//uploadHtml.append("<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:red\" value=\"删除\" onclick=\"del(this)\" />");
						uploadHtml+="</td>";
						uploadHtml+="</tr>"; 
					}else{
						uploadHtml+="<tr>";
						uploadHtml+="<td class=\"TableData\">";
						uploadHtml+="<input name=\"code"+json.rtData[i].fileTypeCode+"\" id=\"code"+json.rtData[i].fileTypeCode+"\" type=\"hidden\" value=\""+json.rtData[i].fileTypeCode+"\" />";
						uploadHtml+="<input name=\"security"+json.rtData[i].fileTypeCode+"\" id=\"security"+json.rtData[i].fileTypeCode+"\" type=\"text\" value=\""+json.rtData[i].fileTypeName+"\" onchange=\"updateName(this)\"/>";
						uploadHtml+="</td>";
						uploadHtml+="<td class=\"TableData\">";
						uploadHtml+="<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:blue;\" value=\"选择\" onclick=\"choose(this)\"/>";
						uploadHtml+="<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:yellow\" value=\"上传\" onclick=\"addFile(this)\"/>";
						uploadHtml+="<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:red\" value=\"删除\" onclick=\"del(this)\" />";
						uploadHtml+="</td>";
						uploadHtml+="</tr>";
						num2=json.rtData[i].fileTypeCode ;
					}
				}else{
					uploadHtml+="<tr>";
					uploadHtml+="<td class=\"TableData\">";
					uploadHtml+="<input name=\"code"+json.rtData[i].fileTypeCode+"\" id=\"code"+json.rtData[i].fileTypeCode+"\" type=\"hidden\" value=\""+json.rtData[i].fileTypeCode+"\" />";
					uploadHtml+="<input name=\"security"+json.rtData[i].fileTypeCode+"\" id=\"security"+json.rtData[i].fileTypeCode+"\" type=\"text\" value=\""+json.rtData[i].fileTypeName+"\" onchange=\"updateName(this)\"/>";
					uploadHtml+="</td>";
					
					uploadHtml+="<td class=\"TableData\">";
					uploadHtml+="<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:blue;\" value=\"选择\" onclick=\"choose(this)\"/>";
					uploadHtml+="<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:yellow\" value=\"上传\" onclick=\"addFile(this)\"/>";
					uploadHtml+="<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:red\" value=\"删除\" onclick=\"del(this)\" />";
					uploadHtml+="</td>";
					uploadHtml+="</tr>";
					num2=json.rtData[i].fileTypeCode ;
				}
				
			 	//遍历文件
			 	if(json1.rtData.length>0){
			 		for(var y=0;y<json1.rtData.length;y++){
						if(json.rtData[i].fileTypeCode === json1.rtData[y].fileTypeCode){
							 uploadHtml+="<tr> <td class=\"TableData\" ><img src=\"\"/> <span>"+json1.rtData[y].fileName+"</span><input type=\"hidden\" name=\"typeId\" value=\""+json1.rtData[y].fileTypeCode+"\"><input type=\"hidden\" name=\"filesId\" value=\""+json1.rtData[y].fileId+"\"></td><td class=\"TableData\"><input type=\"button\" class=\"btn btnactiv\" style=\"background-color:red\" value=\"删除\" onclick=\"delChild(this)\" /></tr>";
						}
					}  
			 	}
				  
			}
			$("#upload").append(uploadHtml);
		}else{
			
			for(var i=0;i<json.rtData.length;i++){
				
				uploadHtml+="<tr>";
				uploadHtml+="<td class=\"TableData\">";
				uploadHtml+="<input name=\"code"+json.rtData[i].fileTypeCode+"\" id=\"code"+json.rtData[i].fileTypeCode+"\" type=\"hidden\" value=\""+json.rtData[i].fileTypeCode+"\" />";
				uploadHtml+="<input name=\"security"+json.rtData[i].fileTypeCode+"\" id=\"security"+json.rtData[i].fileTypeCode+"\" type=\"text\" value=\""+json.rtData[i].fileTypeName+"\" onchange=\"updateName(this)\"/>";
				uploadHtml+="</td>";
				
				uploadHtml+="<td class=\"TableData\">";
				uploadHtml+="<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:blue;\" value=\"选择\" onclick=\"choose(this)\"/>";
				uploadHtml+="<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:yellow\" value=\"上传\" onclick=\"addFile(this)\"/>";
				uploadHtml+="<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:red\" value=\"删除\" onclick=\"del(this)\" />";
				uploadHtml+="</td>";

				uploadHtml+="</tr>"; 
				
				//遍历文件
				var url = "<%=contextPath%>/XZFYFileCaseController/listFiles.action?caseId='"+caseId+"'&cfType='"+cfType+"'";
				var json1=tools.requestJsonRs(url);
				if(json1.rtData.length>0){
			 		for(var y=0;y<json1.rtData.length;y++){
						//var num1=json1.rtData[y].fileTypeCode;
						if(json.rtData[i].fileTypeCode === json1.rtData[y].fileTypeCode){
							 uploadHtml+="<tr> <td class=\"TableData\" ><img src=\"\"/> <span>"+json1.rtData[y].fileName+"</span><input type=\"hidden\" name=\"typeId\" value=\""+json1.rtData[y].fileTypeCode+"\"><input type=\"hidden\" name=\"filesId\" value=\""+json1.rtData[y].fileId+"\"></td><td class=\"TableData\"><input type=\"button\" class=\"btn btnactiv\" style=\"background-color:red\" value=\"删除\" onclick=\"delChild(this)\" /></tr>";
						}
					}  
			 	}
				
			}
			$("#upload").append(uploadHtml);
		}
		
	}else{
		//修改个页面
		if(cfType==2){
			$("#security1").removeAttr("readOnly");
			$("#security3").removeAttr("readOnly");
			$("#security5").removeAttr("readOnly");
			var html="<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:red\" value=\"删除\" onclick=\"del(this)\" />";
			$("#security1").parent().next().append(html);
			$("#security3").parent().next().append(html);
			$("#security5").parent().next().append(html);
			$('#upload').find('span').remove();
		}
		//重新定义input的id,name的值
		$("#input").find("input[type='text']").each(function(i,current){
			$(this).attr("name","name"+i);
			$(this).attr("id","id"+i);
		})
	}

}) 

//添加时用
var fid = 6;
//累加tr的编号
function fileUpload(){
	//chanages();
	fid++; //行数标识
	var uploadHtml="<tr> <td class=\"TableData\" ><img src=\"\"/><input name='code"+fid+"' id='code"+fid+"' type=\"hidden\" value='"+fid+"' /> <input name=\"security"+fid+"\" id=\"security"+fid+"\" type=\"text\" value=\"\" onchange=\"changg(this);\" /></td><td class=\"TableData\"><input type=\"button\" class=\"btn btnactiv\" style=\"background-color:blue;\" value=\"选择\" onclick=\"choose(this)\"/>&nbsp;<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:yellow\" value=\"上传\" onclick=\"addFile(this)\"/>&nbsp;<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:red;\" value=\"删除\" onclick=\"del(this)\" /></tr>";
	param.push({"fileTypeName":""+$("#security"+fid+"").val()+"","fileTypeCode":""+fid+""});
	// 追加到table中 
	$("#upload").append(uploadHtml);
}

//编辑时用
//累加tr的编号
function fileUpload1(){
	count++; 
	var uploadHtml="<tr> <td class=\"TableData\" ><img src=\"\"/><input name='code"+count+"' id='code"+count+"' type=\"hidden\" value='"+count+"' /> <input name=\"security"+count+"\" id=\"security"+count+"\" type=\"text\" value=\"\" onchange=\"changg(this);\" /></td><td class=\"TableData\"><input type=\"button\" class=\"btn btnactiv\" style=\"background-color:blue;\" value=\"选择\" onclick=\"choose(this)\"/>&nbsp;<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:yellow\" value=\"上传\" onclick=\"addFile(this)\"/>&nbsp;<input type=\"button\" class=\"btn btnactiv\" style=\"background-color:red\" value=\"删除\" onclick=\"del(this)\" /></tr>";
	// 追加到table中 
	$("#upload").append(uploadHtml);
		
	param.push({"fileTypeName":""+$("#security"+count+"").val()+"","fileTypeCode":""+count+""});
	console.log("param"+param);
  //var json = JSON.stringify(param);

}

 // 删除文件目录tr行
function del(obj){
	var id = $(obj).parent().parent().find("input[type='hidden']").val(); 
	//1. 首先我们要得到这个对象
	var tina = param.filter((p) => {
	    return p.fileTypeCode == id;
	});

	//2. 其次得到这个对象在数组中对应的索引
	var index = param.indexOf(tina[0]);

	//3. 如果存在则将其删除，index > -1 代表存在
	index > -1 && param.splice(index, 1);
	
	var para = {'fileTypeCode':id,'caseId':caseId,'cfType':cfType};
	var url = "<%=contextPath%>/XZFYFileCaseController/deleteByFileTypeCode.action";
	if(count>0){
		if(delChild(obj)){
			 tools.requestJsonRs(url,para);
			 $(obj).parent().parent().remove();
		 }
	}else{
		$(obj).parent().parent().remove();
	}
	
	
	
}

//文件删除方法
function delChild(obj){
	//获得文件id
	var fileId = $(obj).parent().parent().find("input[name='filesId']").val();
	//var fileId1 = $(obj).parent().parent().find("span").text();
	if(fileId != ""){
		 if(confirm("是否删除?")){
			//1. 首先我们要得到这个对象
			 var tina = paramss.filter((p) => {
				   return p.fileTypeCode == fileId;
			});
			//2. 其次得到这个对象在数组中对应的索引
			var index = paramss.indexOf(tina[0]);
			//3. 如果存在则将其删除，index > -1 代表存在
			index > -1 && paramss.splice(index, 1);
			var ids=$(obj).parent().parent().find("input[type='hidden']").val(); 
			var para = {'fileTypeCode':ids,'caseId':caseId,'cfType':cfType,'filesId':fileId};
		    var url = "<%=contextPath%>/XZFYFileCaseController/deleteByFilesId.action";
		    tools.requestJsonRs(url,para);
		    window.location.reload();
		 }
	}
	return true;
}



var selection =null;
//验证是否选中案件
function isSelected(){
	selection = datagrid.datagrid("getSelected");
	if(selection==null){
		alert("请选择至少一条案件");
		return ;
	}
	return true;
}

//修改目录名称
function updateName(obj){
	var id=$(obj).parent().find("input[type='hidden']").val();
	var name=$(obj).val();
	/* $.each(json.rtData,function(index,item){
		
		if(item.fileTypeCode == id){
			item.fileTypeName=name;
		}
	}) */
	
   param.push({"fileTypeName":""+name+"","fileTypeCode":""+id+""});
}

//归档保存或修改
function doSave(callback){
	if(count>0){
		//拼接json
		var json1 = JSON.stringify(paramss);
		var json2 = JSON.stringify(param);
		var para = "{\"para1\":"+json2+",\"para2\":"+json1+",\"caseId\":\""+caseId+"\",\"cfType\":\""+cfType+"\"}";
		//console.log(para);
	   $("#form1").ajaxSubmit({
	        type: 'post', // 提交方式 get/post
	        url: "<%=contextPath%>/XZFYFileCaseController/update.action?para="+para+"&cfType="+cfType, // 需要提交的 url
	        success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
	        	callback(true);
	        } 
	    });	 
	}else{
		//拼接json
		if(typeof($("#security1").val())!= "undefined"){
			param.push({"fileTypeName":""+$("#security1").val()+"","fileTypeCode":"1"});
		}
		if(typeof($("#security2").val())!= "undefined"){
			param.push({"fileTypeName":""+$("#security2").val()+"","fileTypeCode":"2"});
		}
		if(typeof($("#security3").val())!= "undefined"){
			param.push({"fileTypeName":""+$("#security3").val()+"","fileTypeCode":"3"});
		}
		if(typeof($("#security4").val())!= "undefined"){
			param.push({"fileTypeName":""+$("#security4").val()+"","fileTypeCode":"4"});
		}
		if(typeof($("#security5").val())!= "undefined"){
			param.push({"fileTypeName":""+$("#security5").val()+"","fileTypeCode":"5"});
		}
		if(typeof($("#security6").val())!= "undefined"){
			param.push({"fileTypeName":""+$("#security6").val()+"","fileTypeCode":"6"});
		}
		
		//拼接json
		var json1 = JSON.stringify(paramss);
		var json2 = JSON.stringify(param);
		var para = "{\"para1\":"+json2+",\"para2\":"+json1+",\"caseId\":\""+caseId+"\",\"cfType\":\""+cfType+"\"}";
		
		//console.log(para);
	    $("#form1").ajaxSubmit({
	        type: 'post', // 提交方式 get/post
	        url: "<%=contextPath%>/XZFYFileCaseController/add.action?para="+para+"&cfType="+cfType, // 需要提交的 url
	        success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
	        	callback(true);
	        }
	    });	
	}	
}

 //选择文件
function choose(obj){
		bsWindow("/beidasoft/XZFYPlaceOnFile/choosePlaceOnFile.jsp?type=1&id="+caseId, "请选择归档材料", {
			width : "800",
			height : "200",
				 buttons:
				[
				 {name:"保存",classStyle:""},
			 	 {name:"关闭",classStyle:""}
				], submit : function(v, h) {
				var result = h[0].contentWindow;				
				if(v == "保存"){
					var result = result.choose();
					
					  var ids11=$(obj).parent().prev().find("input[type='hidden']").val();
	
					  
					 for ( var i = 0; i <result.length; i++){
						var uploadHtml="<tr> <td class=\"TableData\" ><img src=\"\"/> <span>"+result[i].name+"</span><input type=\"hidden\" name=\"filesId\" value=\""+result[i].id+"\"><input type=\"hidden\" name=\"typeId\" value=\""+ids11+"\"></td><td class=\"TableData\"><input type=\"button\" class=\"btn btnactiv\" style=\"background-color:red\" value=\"删除\" onclick=\"delChild(this)\" /></tr>";
						$(obj).parent().parent().after(uploadHtml);
						paramss.push({"filesName":""+result[i].name+"","filesId":""+result[i].id+"","fileTypeCode":""+ids11+"","type":"1"});
					 } 
					return true;
				}
				if(v =="关闭"){
					return true;
				}
			} 
		});
} 

//上传文件
function addFile(obj){
	bsWindow("choosePlaceOnFile.jsp?type=2&id="+caseId, "请上传归档材料", {
		width : "800",
		height : "200",
			 buttons:
			[
			 {name:"保存",classStyle:""},
		 	 {name:"关闭",classStyle:""}
			], submit : function(v, h) {
			var result1 = h[0].contentWindow;
			if(v == "保存"){
				var result = result1.addFile();
				var ids=$(obj).parent().parent().find("input[type='hidden']").val();
				for(var i = 0; i <result.length; i++){
					console.log(result[i]);
					 var uploadHtml="<tr> <td class=\"TableData\" ><img src=\"\"/> <span>"+result[i].fileName+"</span><input type=\"hidden\" name=\"typeId\" value=\""+result[i].fileId+"\"></td><td class=\"TableData\"><input type=\"button\" class=\"btn btnactiv\" style=\"background-color:red\" value=\"删除\" onclick=\"delChild(this)\" /></tr>";
					$(obj).parent().parent().after(uploadHtml);
					paramss.push({"filesName":result[i].fileName,"filesId":result[i].fileId,"fileTypeCode":ids,"type":"2"});  
				}
					
				return true;
			}
			if(v =="关闭"){
				return true;
			}
		} 
	});
}

//修改目录名称
function changg(obj){
	var id=$(obj).parent().find("input[type='hidden']").val();
	var name=$(obj).val();
	$.each(param,function(index,item){
		if(item.fileTypeCode == id){
			item.fileTypeName=name;
		}
	})
	//console.log(param);
}
</script>
</body>
</html>