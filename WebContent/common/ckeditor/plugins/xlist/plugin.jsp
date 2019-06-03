<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>明细表</title>
</head>
<script type="text/javascript">
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



//改变数据选择类型
function changeType(){
	var type=$("#type").val();
	if(type==0){
		$("#lcsjTr").hide();
		$("#stbsTr").hide();
	}else if(type==1){
		$("#lcsjTr").hide();
		$("#stbsTr").show();
	}else if(type==2){
		$("#lcsjTr").show();
		$("#stbsTr").hide();
	}
}
</script>
<body>
<div style="padding:10px;font-size:12px">
  <table style="font-size:12px">
    <tr>
       <td>
            <b>名称：</b>
			<br/>
			<input id="title" class="BigInput" style="width:173px"/>
       </td>
    </tr>
	
	<tr>
	   <td>
	    <b>数据选择类型：</b>
		<br/>
		<select id="type" name="type" onchange="changeType();">
		   <option value="0">无</option>
		   <option value="1">视图标识</option>
		   <option value="2">流程数据</option>
		</select>
	   </td>
	</tr>
	
	<tr id="lcsjTr" style="display: none;">
	  <td>
	     <b>所属流程：</b>
		 <br/>
		 <select id="flowid" name="flowid"  onchange="renderFormItems();"></select>
	     <br/>
	     <b>映射关系：</b>
		<br/>
		 <select id="title1" name="title1">
		   <option>请选择表单字段</option>
		 </select>
		 ~
		 <input type="text" name="title2" id="title2" />
         <input type="button" value="映射"  onclick="addMapping();"/>
	     <div style="width: 500px;margin-top: 5px">
	        <table id="mapTable" style="border:#dddddd 2px solid;" width="100%" class="TableBlock_page">
               <tr style="background-color:#e8ecf9">
                 <td>表单字段名称</td>
                 <td>映射控件名称</td>
                 <td>操作</td>
               </tr>
             </table>
	     </div>
	  </td>	
	</tr>
	
	
	<tr id="stbsTr" style="display: none">
	  <td>
	      <b>视图标识：</b>
	      <br/>
	      <input id="dfid" class="BigInput" />
		  <span style="color:red">
			&nbsp;与业务引擎有关，一般无需设置该项。
		  </span>
	  </td>
	</tr>
	
	<tr>
	   <td>
	       <b>列表宽度：</b>
			<br/>
			<input id="width" class="BigInput" style=""/>
			<span style="color:red">
					&nbsp;表格整体宽度，例如：100%或者100px。
			</span>
	   </td>
	</tr>
	
	<tr>
	   <td>
	     <b>表头单元格样式：</b>
		<br/>
		<input id="header" class="BigInput" style=""/>
		<span style="color:red">
				&nbsp;所有表头td样式。
		</span> 
	   </td>
	</tr>
	
	<tr>
	   <td>
	     <b>数据单元格样式：</b>
		<br/>
		<input id="data" class="BigInput" style=""/>
		<span style="color:red">
				&nbsp;所有数据行td样式。
		</span>
	   </td>
	</tr>

	<tr>
	   <td>
	        <b>子表名称：</b>
			<br/>
			<input id="tablename" class="BigInput" style="width: 300px"/>
			
			
	   </td>
	</tr>
	
	<tr>
	   <td>
	      <div id='xlist_content' style="font-size:12px;"></div><br/><a href='javascript:void()' onclick='xlist_addDataItem()'>添加表项目</a>
	   </td>
	</tr>
	
	
</div>
<script>
	var contextPath = "<%=contextPath%>";

	var headerstyle = "style='text-align:center;font-weight:bold;'";
	var bodystyle = "style='text-align:center;'";
	var defaultTableName="XLIST_ITEM_"+parent.formId+"_"+<%=System.currentTimeMillis()%>;
	
	//默认字表名称
	$("#tablename").val(defaultTableName);
	
	//校验，required
	function validate(){
		var reg=/^[a-zA-Z]+$/;
		//判断是否填写标题了
		if($("#title").val()==""){
			alert("请填写控件名称！");
			$("#title").focus();
			return false;
		}
		
		//判断是否填写子表名称
		if($("#tablename").val()==""){
			alert("请填写子表名称！");
			$("#tablename").focus();
			return false;
		}
		/* if(!reg.test($("#tablename").val())){
			alert("子表名称必须为字母");
			$("#tablename").focus();
			return false;
		} */
		
		//验证字段名
		
		var fieldInputs=$(".fieldClass");
		
		var fieldMap=[];
		var error=0;
		if(fieldInputs!=null&&fieldInputs.length>0){
			var fieldLength=fieldInputs.length;
			for(var i=0;i<fieldInputs.length;i++){
				error=0;
				if($(fieldInputs[i]).val()==""||$(fieldInputs[i]).val()=="null"||$(fieldInputs[i]).val()==null||$(fieldInputs[i]).val()=="undefined"){
					alert("字段名不能为空！");
					$($(fieldInputs[i])).focus();
					error=1;
					return false;
				}
				/* if(!reg.test($(fieldInputs[i]).val())){
					alert("字段名只能是字母！");
					$($(fieldInputs[i])).focus();
					error=1;
					return false;
				}
				 */
				fieldMap[$(fieldInputs[i]).val()]=$(fieldInputs[i]).val();
			}
			if(error==0){
				if( Object.keys(fieldMap).length< fieldLength){
					alert("字段名不能重复！");
					return false;
				}
			}
			
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


	
	function xlist_addDataItem(){
		maxField+=1;
		var xlist_tbody = $("#xlist_tbody");
		var html = "";

		html+="<tr>";
		html+="<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type='text' name=\"title\" style='width:100px'  /></td>" +
		        "<td "+bodystyle+"><input class='cke_dialog_ui_input_text fieldClass' type='text' name='width' style='width:100px' value='"+"FIELD_"+maxField+"'  /></td>" +
				"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type='text' name='width' style='width:50px'  /></td>" +
				"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type='text' name='style'  /></td>" +
				"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type=\"checkbox\" name=\"sum\"  /></td>" +
				"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type=\"text\" name=\"formula\"  /></td>" +
				"<td "+bodystyle+">"+xlist_renderType("")+"</td>" +
				"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type=\"text\" name=\"model\"  /></td>" +
				"<td "+bodystyle+"><a href='javascript:void()' onclick='xlist_deleteDataItem(this)'>删除</a></td>";
		html+="</tr>";
		
		var child = $(html);

		xlist_tbody.append(child);
	}

	function xlist_deleteDataItem(obj){
		$(obj).parent().parent().remove();
	}

	function xlist_renderType(value){
		var html = "<select class='cke_dialog_ui_input_text' type=\"text\" name=\"type\">";
		html+="<option value='1' "+(value=="1"?"selected":"")+">单行输入框</option>";
		html+="<option value='2' "+(value=="2"?"selected":"")+">多行文本框</option>";
		html+="<option value='3' "+(value=="3"?"selected":"")+">下拉菜单</option>";
		html+="<option value='4' "+(value=="4"?"selected":"")+">单选框</option>";
		html+="<option value='5' "+(value=="5"?"selected":"")+">多选框</option>";
		html+="<option value='6' "+(value=="6"?"selected":"")+">列表序号</option>";
		html+="<option value='7' "+(value=="7"?"selected":"")+">日期时间</option>";
		html+="<option value='8' "+(value=="8"?"selected":"")+">数据选择</option>";
		html+="<option value='9' "+(value=="9"?"selected":"")+">人员单选</option>";
		html+="<option value='10' "+(value=="10"?"selected":"")+">人员多选</option>";
		html+="<option value='11' "+(value=="11"?"selected":"")+">部门单选</option>";
		html+="<option value='12' "+(value=="12"?"selected":"")+">部门多选</option>";
		html+="<option value='13' "+(value=="13"?"selected":"")+">角色单选</option>";
		html+="<option value='14' "+(value=="14"?"selected":"")+">角色多选</option>";
		html+="</select>";
		return html;
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
		 
		var mappingstr=tools.jsonArray2String(mapping);
		
		
		var title = $("#title").val();
		var width = $("#width").val();
		var header = $("#header").val();
		var data = $("#data").val();
		var tablename = $("#tablename").val();
		var dfid = $("#dfid").val();
		var flowid = $("#flowid").val();
		var type=$("#type").val();
		var html = "<img src=\""+(contextPath+"/common/images/workflow/xlist.png")+"\" xtype=\"xlist\" title=\""+title+"\" ";
		
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\" ";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}
		html+=" rows=\""+width+"\" tablename=\""+tablename+"\" header=\""+header+"\" data=\""+data+"\" dfid=\""+dfid+"\"";
		
		if(flowid){
			html+=" flowid=\""+flowid+"\"";
		}
		
		if(type){
			html+=" type=\""+type+"\"";
		}
		
		if(mappingstr){
			html+=" mappingstr=\""+tools.string2Unicode(mappingstr)+"\"";
		}
		
		
		var dataList = $("#xlist_tbody tr");
		var json = "[";
		
		dataList.each(function(i,obj){
			var data = $(obj).find("td");
			var title = $(data[0]).find("input:first").val();
			var fieldName=$(data[1]).find("input:first").val();
			var width = $(data[2]).find("input:first").val();
			var style = $(data[3]).find("input:first").val();
			var sum = $(data[4]).find("input:first").attr("checked")?"1":"0";
			var formula = $(data[5]).find("input:first").val();
			var type = $(data[6]).find("select:first").val();
			var model = $(data[7]).find("input:first").val();
			json+="{'title':'"+title+"','fieldName':'"+fieldName+"','width':'"+width+"','style':'"+style+"','sum':'"+sum+"','formula':'"+formula+"','type':'"+type+"','model':'"+model+"'}";
			if(i!=dataList.length-1){
				json+=",";
			}
		});
		
		json+="]";
		
		html+="model=\""+json+"\"";
		html+="/>";
		
		
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
	var xlist_content = $("#xlist_content");
	
	//设置表头
	var html = "<table style='width:900px;font-size:12px'>";
	html+="<thead>";
	html+="<tr>";
	html+="<td "+headerstyle+">表头项名称</td><td "+headerstyle+">字段名</td><td "+headerstyle+">控件宽度</td><td "+headerstyle+">数据单元格样式</td><td "+headerstyle+">列合计</td><td "+headerstyle+">计算公式</td><td "+headerstyle+">字段类型</td><td "+headerstyle+">值数据</td><td "+headerstyle+">操作</td>";
	html+="</tr>";
	html+="</thead>";

	html+="<tbody id='xlist_tbody'>";
	
	if(element!=null && element.getAttribute('xtype')=='xlist'){
		nameCode = element.getAttribute('name');
		var title = $("#title");
		var width = $("#width");
		var data = $("#data");
		var tablename = $("#tablename");
		var header = $("#header");
		var dfid = $("#dfid");
		var type=$("#type");
		var flowid=$("#flowid");
		if(element.getAttribute("title")){
			title[0].value = element.getAttribute("title");
		}
		if(element.getAttribute("rows")){
			width[0].value = element.getAttribute("rows");
		}
		if(element.getAttribute("data")){
			data[0].value = element.getAttribute("data");
		}
		if(element.getAttribute("header")){
			header[0].value = element.getAttribute("header");
		}
		if(element.getAttribute("dfid")){
			dfid[0].value = element.getAttribute("dfid");
		}
		if(element.getAttribute("tablename")){
			tablename[0].value = element.getAttribute("tablename");
		}
		if(element.getAttribute("type")){
			type[0].value = element.getAttribute("type");
		}
		//控制视图标识和流程数据的显示情况
		changeType();
		
		if(element.getAttribute("flowid")){
			flowid[0].value = element.getAttribute("flowid");
		}
		//渲染流程关联的表单字段
		 renderFormItems();
		
		var mappingstr=tools.unicode2String(element.getAttribute('mappingstr'));
		var mappingArr=tools.string2JsonObj(mappingstr);
		if(mappingArr.length>0){
			for(var k=0;k<mappingArr.length;k++){
				 $("#mapTable").append("<tr class='mapClass'><td>"+mappingArr[k].title1+"</td><td>"+mappingArr[k].title2+"</td><td><a href=\"#\" onclick=\"remove(this);\" >删除</a></td></tr>");
			}
		}
		
		var model = element.getAttribute("model");//获取元素model属性
		if(model && model!=""){
			var list = eval("("+model+")");
			for(var i=0;i<list.length;i++){
				html+="<tr>";
				html+="<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type='text' value='"+list[i].title+"'  name=\"title\" style='width:100px'></td>" +
				    "<td "+bodystyle+"><input class='cke_dialog_ui_input_text  fieldClass' type='text' name='width' style='width:100px' value=\""+(list[i].fieldName)+"\"/></td>" +
					"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type='text' name='width' style='width:50px' value=\""+(list[i].width)+"\" /></td>" +
					"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type='text' name='style' value=\""+(list[i].style)+"\" /></td>" +
					"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type=\"checkbox\" name=\"sum\" "+(list[i].sum=="1"?"checked":"")+" /></td>" +
					"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type=\"text\" name=\"formula\" value=\""+(list[i].formula)+"\" /></td>" +
					"<td "+bodystyle+">"+xlist_renderType(list[i].type)+"</td>" +
					"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type=\"text\" name=\"model\"  value=\""+(list[i].model)+"\" /></td>" +
					"<td "+bodystyle+"><a href='javascript:void()' onclick='xlist_deleteDataItem(this)'>删除</a></td>";
				html+="</tr>";
			}
		}
		
	}else{
		nameCode = null;
	}
	html+="</tbody>";
	html+="</table>";
	xlist_content.html(html);

	
	
	
	
	var maxField=0;//字段最大数
	var fieldInput=$(".fieldClass");
	if(fieldInput.length>0){
	    for(var i=0;i<fieldInput.length;i++){
	    	var val=$(fieldInput[i]).attr("value");
	    	if(val.indexOf("FIELD_")==0){//以FILELD_开头
	    		if(!isNaN(val.substring(6,val.length))){//是数字
	    			
	    			if(parseInt(val.substring(6,val.length))>maxField){
	    				maxField =parseInt( val.substring(6,val.length));
	    			}
	    		}
	    	}
	    }	
	}
</script>
</body>

</html>