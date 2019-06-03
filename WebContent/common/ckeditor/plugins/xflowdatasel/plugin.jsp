<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>流程数据选择控件</title>
</head>
<body>
<div style="padding:10px;font-size:12px">
	<table style="font-size:12px">
		<tr>
			<td>
				<b>控件名称：</b>
				<br/>
				<input  type="text"  id="title" class="BigInput" style="width:100%"/>
			</td>
		</tr>
		<tr>
			<td>
				<b>所属流程：</b>
				<br/>
				<select id="flowid" name="flowid"  onchange="renderFormItems();"></select>
			</td>
		</tr>
		<tr>
			<td>
				<b>映射关系：</b>
				<br/>
				<select id="title1" name="title1">
				   <option>请选择表单字段</option>
				</select>
				~
				<input type="text" name="title2" id="title2" />
                <input type="button" value="映射"  onclick="addMapping();"/>
			</td>
		</tr>
	</table>
	
	<table id="mapTable" style="border:#dddddd 2px solid;" width="100%" class="TableBlock_page">
      <tr style="background-color:#e8ecf9">
         <td>表单字段名称</td>
         <td>映射控件名称</td>
         <td>操作</td>
      </tr>
    </table>
</div>
<script>


 //渲染所有的流程类型
 function getAllFlowTypes(){
	 var url=contextPath+"/flowType/getAllFlowTypesAndFlowSorts.action";
		var json=tools.requestJsonRs(url,{});
		if(json.rtState){
			var data=json.rtData;
			var sortList=data[0];
			var flowTypeList=data[1];

			var html=[];
			//先处理默认分类
			html.push("<optgroup label='默认分类'>");
			for(var i=0;i<flowTypeList.length;i++){
				if(flowTypeList[i].flowSortId==0){
					html.push("<option value="+flowTypeList[i].sid+">"+flowTypeList[i].flowName+"</option>")
				}
			}
			html.push("</optgroup>");
			//处理有分类的流程
			for(var m=0;m<sortList.length;m++){
				html.push("<optgroup label="+sortList[m].sortName+">");
				for(var n=0;n<flowTypeList.length;n++){
					if(flowTypeList[n].flowSortId==sortList[m].sid){
						html.push("<option value="+flowTypeList[n].sid+">"+flowTypeList[n].flowName+"</option>")
					}
				}
				html.push("</optgroup>");
			}
			
			
			
			$("#flowid").append(html.join(""));
		}
 }
 
 
 
 //渲染流程关联的表单字段
 function renderFormItems(){
	 //清空映射表中的数据
	 $("#mapTable tr:not(:first)").remove();
	 //先清空所有的选项
	  $("#title1").empty(); 
	 var flowId=$("#flowid").val();
	 var url=contextPath+"/flowForm/getFormItemsByFlowType.action";
	 var json=tools.requestJsonRs(url,{flowId:flowId});
	 if(json.rtState){
		 var data=json.rtData;
		 var html=[];
		 if(data!=null&&data.length>0){
			 for(var i=0;i<data.length;i++){
				 html.push("<option value="+data[i].title+">"+data[i].title+"</option>")
			 }
		 }
		 $("#title1").append(html.join(""));
	 }
 }
 
 
 //映射
 function addMapping(){
	 var title1=$("#title1").val();
	 var title2=$("#title2").val().replace(/(^\s*)|(\s*$)/g, "");
	 if(title1==null||title1=="null"||title1==""){
		 alert("请选择表单字段！");
		 return;
	 }
	 if(title2==null||title2=="null"||title2==""){
		 alert("请填写映射字段！");
		 return;
	 }
	 //判断映射关系不能重复
	 var mapTr=$(".mapClass");
	 var m={};
	 if(mapTr.length>0){
		 for(var i=0;i<mapTr.length;i++ ){
		    var t1=$(mapTr[i]).find("td:eq(0)").text();
		    var t2=$(mapTr[i]).find("td:eq(1)").text();
		    m[t1+t2]=t1+t2;
		 }
	 }
	 
	 for(var key in  m){
		 if((title1+title2)==key){
			 alert("该映射关系已存在，不能重复设置！");
			 return;
		 }
	 }
	 $("#mapTable").append("<tr class='mapClass'><td>"+title1+"</td><td>"+title2+"</td><td><a href=\"#\" onclick=\"remove(this);\" >删除</a></td></tr>");
	 $("#title2").val("");
 }

 
 
 
 function remove(obj){
	 $(obj).parent().parent().remove();
 }
 
	var contextPath = "<%=contextPath%>";
	
	//校验，required
	function validate(){
		//判断是否填写标题了
		if($("#title").val()==""){
			alert("请填写控件名称！");
			$("#title").focus();
			return false;
		}
		//判断当前title是否在整个dom文档中
		var findit = $(parent.editor.getData()).find("[title="+$("#title").val()+"]").length!=0?true:false;
		if(findit){
			if(element && $("#title").val()!=element.getAttribute('title')){
				alert("已存在该名称的控件，控件名称禁止重复。");
				return false;
			}else if(element && $("#title").val()==element.getAttribute('title')){
				return true;
			}else{
				alert("已存在该名称的控件，控件名称禁止重复。");
				return false;
			}
		}
		return true;
	}

	//转换dom节点，required
	function toDomStr(){
		 var mapTr=$(".mapClass");
		 var mapping=[];
		 if(mapTr.length>0){
			 for(var i=0;i<mapTr.length;i++ ){
			    var t1=$(mapTr[i]).find("td:eq(0)").text();
			    var t2=$(mapTr[i]).find("td:eq(1)").text(); 
			    mapping.push({"title1":t1,"title2":t2});
			 }
		 }
		 
		window.mappingstr=tools.jsonArray2String(mapping);
		window.title = $("#title").val();
		window.flowid = $("#flowid").val();

		var html = "<input type=\"button\" xtype=\"xflowdatasel\" ";
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
		}else{
			
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}
		
		if(title){
			html+=" title=\""+title+"\"";
		}
		
		if(flowid){
			html+=" flowid=\""+flowid+"\"";
		}
		
		if(mappingstr){
			html+=" mappingstr=\""+tools.string2Unicode(mappingstr)+"\"";
		}
		
		html+=" value=\""+title+"\"  />";
		return html;
	}

	 //渲染所有的流程类型
	 getAllFlowTypes();
	//渲染流程关联的表单字段
	 renderFormItems();
	//加载数据
	var selection = parent.editor.getSelection();
	var ranges = selection.getRanges();
	var element = selection.getSelectedElement();
	
	if(element!=null && element.getAttribute('xtype')=="xflowdatasel"){
		nameCode = element.getAttribute('name');
		var title = $("#title");
		if(element.getAttribute('title')){
			title.attr("value",element.getAttribute('title'));
		}
		$("#flowid").attr("value",element.getAttribute('flowid'));
		

		//渲染流程关联的表单字段
		 renderFormItems();
		
		var mappingstr=tools.unicode2String(element.getAttribute('mappingstr'));
		var mappingArr=tools.string2JsonObj(mappingstr);
		if(mappingArr.length>0){
			for(var k=0;k<mappingArr.length;k++){
				 $("#mapTable").append("<tr class='mapClass'><td>"+mappingArr[k].title1+"</td><td>"+mappingArr[k].title2+"</td><td><a href=\"#\" onclick=\"remove(this);\" >删除</a></td></tr>");
			}
		}
		
	}else{
		nameCode = null;
	}

	
	 
</script>
</body>

</html>