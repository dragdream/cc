<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp"%>
	<%@ include file="/header/easyui.jsp"%>
	<%
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int tableId = TeeStringUtil.getInteger(
				request.getParameter("tableId"), 0);
		int catId = TeeStringUtil.getInteger(request.getParameter("catId"),
				0);
	%>
	<title>添加字段信息</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script>
		var sid =<%=sid%>;
		var tableId =<%=tableId%>;
		var catId =<%=catId%>;
		var dbType;
function doInit() {
			//获取tableModel数据
			var url = contextPath + "/bisTable/getModelById.action";
			var json = tools.requestJsonRs(url, {
				sid : tableId
			});
			dbType = json.rtData.dbType;

			if (sid != 0) {
				var url = contextPath + "/bisTableField/getModelById.action";
				var json = tools.requestJsonRs(url, {
					sid : sid
				});
				
				bindJsonObj2Cntrl(json.rtData);
				//获取sql过滤条件
				var sqlFilter=json.rtData.sqlFilter;
				
				//获取字段显示类型的值
				var fieldDisplayType=$("#fieldDisplayType").val();
				//获取字段控制模型的值
				var fieldControlModel=$("#fieldControlModel").val();
				//转换成json对象
				var controlModel =tools.strToJson(fieldControlModel);  
				
				
				if(fieldDisplayType=="NUMBER"){
					$("#container")
					.append(
							"<tr>"
									+ "<td class='TableData'>小数点位数：</td>"
									+ "<td class='TableData'>"
									+ "<input type='text' name='numberdigits' id='numberdigits' class='BigInput' />"
									+ "</td>"
									+ "</tr>"
									+ "<tr>"
									+ "<td class='TableData'>数字显示格式：</td>"
									+ "<td class='TableData'>"
									+ "<select name='numberdisplay' id='numberdisplay' class='BigSelect'>"
									+ "<option value=''></option>"
									+ "<option value='RMB'>人民币大写</option>"
									+ "<option value='Micrometerseparated'>千分位符分隔</option>"
									+ "</select>"
									+ "</td>" + "</tr>");
					//给字段控制模型赋值
					var numberdigits=controlModel.numberdigits;
					$("#numberdigits").val(numberdigits);
					var numberdisplay=controlModel.numberdisplay;
					$("#numberdisplay").val(numberdisplay);
				}else if(fieldDisplayType=="DATE"){
					$("#container")
					.append(
							"<tr>"
									+ "<td class='TableData'>日期显示格式：</td>"
									+ "<td class='TableData'>"
									+ "<select name='datedisplay' id='datedisplay' class='BigSelect'>"
									+ "<option value=''></option>"
									+ "<option value='yyyy-MM-dd'>yyyy-MM-dd</option>"
									+ "<option value='yyyy-MM-dd HH:mm'>yyyy-MM-dd HH:mm</option>"
									+ "<option value='yyyy-MM-dd HH:mm:ss'>yyyy-MM-dd HH:mm:ss</option>"
									+ "</select>"
									+ "</td>" + "</tr>");
					
					//给字段控制模型赋值
					var datedisplay=controlModel.datedisplay;
					$("#datedisplay").val(datedisplay);
				}else if(fieldDisplayType == "SELECT"||fieldDisplayType == "RADIO"){
					$("#container")
					.append(
							"<tr>"
									+ "<td class='TableData'>系统编码：</td>"
									+ "<td class='TableData'>"
									+ "<input type='text' name='sysno' id='sysno' class='BigInput'/>"
									+ "</td>"
									+ "</tr>");
					//给字段控制模型赋值
					var sysno=controlModel.sysno;
					$("#sysno").val(sysno);
					
				}else if(fieldDisplayType=="CHECKBOX"){
					$("#container")
					.append(
							"<tr>"
									+ "<td class='TableData'>显示标签：</td>"
									+ "<td class='TableData'>"
									+ "<input type='text' name='lable' id='lable' class='BigInput'/>"
									+ "</td>"
									+ "</tr>");
					//给字段控制模型赋值
					var lable=controlModel.lable;	
					$("#lable").val(lable);
				}else if(fieldDisplayType=="ASSOCIATE"){
					
					$("#container")
					.append(
							"<tr>"
									+ "<td class='TableData'>选择主表：</td>"
									+ "<td class='TableData'>"
									+ "<select id='mainTable' name='mainTable' class='BigSelect'></select>"
									+ "</td>"
									+ "</tr>"+
							"<tr>"
									+ "<td class='TableData'>关联主键：</td>"
									+ "<td class='TableData'>"
									+ "<select id='key' name='key' class='BigSelect'></select>"
									+ "</td>"
									+ "</tr>"+
							"<tr align='center'>"
									+ "<td class='TableData' colspan='2'>"
									+"<select multiple style='height:160px;width:140px;' id='mainTableFields'>"
									+"</select>"
									+"&nbsp;&nbsp;"
									+"<select multiple style='height:160px;width:140px;' id='currentTableFields'>"
									+"</select>"
									+"<br/>"
									+"<button class='btn btn-primary' onclick='doMapping()' type='button' >映射</button>"
									+ "</td>"
									+ "</tr>"+
							"<tr>"
									+"<td class='TableData' colspan='2' id='detailItem'>"
							        +"</td>"
								    +"</tr>"
								    +"<tr>"
									+"<td class=\"TableData\">主表过滤条件：</td>"
									+"<td class=\"TableData\"><textarea rows=\"5\" cols=\"52\""
									+ "id=\"sqlFilter\" name=\"sqlFilter\" class=\"BigTextarea\"></textarea>"
									+"<br>$[USER_NAME] 当前用户名 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$[USER_ID] 当前用户ID</br>"
									+"$[DEPT_NAME] 当前部门名 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$[DEPT_ID] 当前部门ID</br>"
								    +"$[ROLE_NAME] 当前角色名 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$[ROLE_ID] 当前角色ID</br>"
									+"</td>"
								+"</tr>");
					
					      
					      //给过滤条件赋值
					      $("#sqlFilter").val(sqlFilter);
					
					
					      //动态添加主表的列表
					      getMainTableList();
					      //给关联主表赋值
					      var mainTable=controlModel.mainTable;
					      $("#mainTable").val(mainTable);
					      //加载主表的列
						  var mTId=$("#mainTable").val();
						  renderFields(mTId);
					      //给关联主键赋值
					      var key=controlModel.key;
					      $("#key").val(key);
					      
					      //给对应的映射关系赋值
					      //获取映射关系
					      var mapping=controlModel.mapping;
					      var render = [];
					      for(var i=0;i<mapping.length;i++){
					    	  render.push("<div detail=\"{current:'"+mapping[i].current+"',currentId:'"+mapping[i].currentId+"',main:'"+mapping[i].main+"',mainId:'"+mapping[i].mainId+"'}\">");
		                      render.push(""+mapping[i].main+"&nbsp;=>&nbsp;"+mapping[i].current+"&nbsp;&nbsp;<img src='"+systemImagePath+"/upload/remove.png' title='移除' onclick='remove0(this)'/>");
		                      render.push("</div>");     	  
					      }
					      $("#detailItem").append(render.join(""));
					    
	                     
	                      
					     
					     //加载当前表格的字段
					     getCurrentTableFields();
					     //加载主表的字段
					     getMainTableFields(mTId);
					    //动态加载关联主键的值
					    $("#mainTable").bind("change",function(){
						 var mainTableId=$("#mainTable").val();
						 renderFields(mainTableId);
						 getMainTableFields(mainTableId);
					    }); 
					    
					
				}	
			}
			if (sid == 0) {
				typeChanged($("#fieldType")[0]);
			}
}

function commit() {
			if (!$("#form1").form("validate")) {
				return false;
			}
			//获取字段显示类型
			var fieldDisplayType=$("#fieldDisplayType").val();
			//字段控制模型
			var fieldControlModel="";
            if(fieldDisplayType=="NUMBER"){
            	var numberdigits=$("#numberdigits").val();
            	var numberdisplay=$("#numberdisplay").val();
            	fieldControlModel="{numberdigits:"+numberdigits+",numberdisplay:'"+numberdisplay+"'}";  	
            }else if(fieldDisplayType=="DATE"){
            	var datedisplay=$("#datedisplay").val();
            	fieldControlModel="{datedisplay:'"+datedisplay+"'}";  	
            }else if(fieldDisplayType=="SELECT"||fieldDisplayType=="RADIO"){
            	var sysno=$("#sysno").val();
            	fieldControlModel="{sysno:'"+sysno+"'}"; 
            }else if(fieldDisplayType=="CHECKBOX"){
            	var lable=$("#lable").val();
            	fieldControlModel="{lable:'"+lable+"'}";
            	
            }else if(fieldDisplayType=="ASSOCIATE"){
            	var mainTable=$("#mainTable").val();
            	var key=$("#key").val();
            	
            	//获取映射的div
            	var str="["; 	
            	
            	$("#detailItem div").each(function(){
            	    str=str+$(this).attr("detail")+",";
            	});
                str=str.substring(0, str.length-1);
            	str+="]";
            	
            	fieldControlModel="{mainTable:'"+mainTable+"',key:'"+key+"',mapping:"+str+"}"; 
            	//alert(fieldControlModel);
            }else{
            	fieldControlModel="{}";
            }
            
            
           //给隐藏域fieldControlModel赋值
            $("#fieldControlModel").val(fieldControlModel);
            
			var url;
			if (sid == 0) {
				url = contextPath + "/bisTableField/addBisTableField.action";
			} else {
				url = contextPath + "/bisTableField/updateBisTableField.action";
			}

			var params = tools.formToJson($("#form1"));
			var json = tools.requestJsonRs(url, params);
			if (json.rtState) {
				alert(json.rtMsg);
				window.location = "field_list.jsp?tableId=" + tableId
						+ "&catId=" + catId;
			} else {
				alert(json.rtMsg);
			} 
}

function typeChanged(obj) {
			var url = contextPath + "/bisTableField/getFieldTypeExt.action";
			var json = tools.requestJsonRs(url, {
				dbType : dbType,
				fieldType : obj.value
			});
			$("#fieldTypeExt").attr("value", json.rtData);
			
}
//获取主表的列表
function getMainTableList(){
	var url = contextPath+"/bisFormController/getTableList.action";
	var json = tools.requestJsonRs(url,{});
	 //动态添加所属表格
	if(json.rtState){
		var html="";
		var datas = json.rtData;
		for(var i=0;i<datas.length;i++){
			html+=("<optgroup label='"+datas[i].cat+"'>");
			var tableList=datas[i].tableList;
			for(var j=0;j<tableList.length;j++){
				html+=("<option value="+tableList[j].sid+">"+tableList[j].tableName+"</option>");
			}
			html+=("</optgroup>"); 
		}
		$("#mainTable").html(html);
	} 
}

//动态渲染关联主键的列表
function renderFields(tableId){
	
	var url = contextPath+"/bisTableField/getFieldsByTableId.action";
	var json = tools.requestJsonRs(url,{tableId:tableId});
	 //动态添加所属表格
	if(json.rtState){
		var html="";
		var datas = json.rtData;
		for(var i=0;i<datas.length;i++){
			html+=("<option value="+datas[i].sid+">"+datas[i].fieldName+"</option>");
		}
		$("#key").html(html);
	} 

}
//动态获取当前表的所有的字段
function getCurrentTableFields(){
	var url = contextPath+"/bisTableField/getFieldsByTableId.action";
	var json = tools.requestJsonRs(url,{tableId:tableId});
	 //动态添加所属表格
	if(json.rtState){
		var html="";
		var datas = json.rtData;
		for(var i=0;i<datas.length;i++){
			html+=("<option value="+datas[i].sid+">"+datas[i].fieldName+"</option>");
		}
		$("#currentTableFields").html(html);
	} 
}


//动态获取主表的所有的字段   回填映射的下拉框
function getMainTableFields(tableId){
	var url = contextPath+"/bisTableField/getFieldsByTableId.action";
	var json = tools.requestJsonRs(url,{tableId:tableId});
	 //动态添加所属表格
	if(json.rtState){
		var html="";
		var datas = json.rtData;
		for(var i=0;i<datas.length;i++){
			html+=("<option value="+datas[i].sid+">"+datas[i].fieldName+"</option>");
		}
		$("#mainTableFields").html(html);
	} 
}

//点击映射按钮
function doMapping(){
	var mainField = $("#mainTableFields option:selected");
	var currentField = $("#currentTableFields option:selected");
	var render = [];
	render.push("<div detail=\"{current:'"+currentField.html()+"',currentId:'"+currentField.attr("value")+"',main:'"+mainField.html()+"',mainId:'"+mainField.attr("value")+"'}\">");
	render.push(""+mainField.html()+"&nbsp;=>&nbsp;"+currentField.html()+"&nbsp;&nbsp;<img src='"+systemImagePath+"/upload/remove.png' title='移除' onclick='remove0(this)'/>");
	render.push("</div>");
	$("#detailItem").append(render.join(""));
}

//删除映射
function remove0(cur){
	$(cur).parent().remove();
}




$(function() {
			//当字段显示类型发生变化时   动态渲染字段控制模型
			$("#fieldDisplayType")
					.bind(
							"change",
							function() {
								//先获取字段显示类型的值
								var fieldDisplayType = $("#fieldDisplayType")
										.val();
								//数字类型
								if (fieldDisplayType == "NUMBER") {
									//先清空tbody中的内容
									$("#container").html("");

									$("#container")
											.append(
													"<tr>"
															+ "<td class='TableData'>小数点位数：</td>"
															+ "<td class='TableData'>"
															+ "<input type='text' name='numberdigits' id='numberdigits' class='BigInput'/>"
															+ "</td>"
															+ "</tr>"
															+ "<tr>"
															+ "<td class='TableData'>数字显示格式：</td>"
															+ "<td class='TableData'>"
															+ "<select name='numberdisplay' id='numberdisplay' class='BigSelect'>"
															+ "<option value=''></option>"
															+ "<option value='RMB'>人民币大写</option>"
															+ "<option value='Micrometerseparated'>千分位符分隔</option>"
															+ "</select>"
															+ "</td>" + "</tr>");
								}else if (fieldDisplayType == "DATE") {//日期格式
									//先清空tbody中的内容
									$("#container").html("");

									$("#container")
											.append(
													"<tr>"
															+ "<td class='TableData'>日期显示格式：</td>"
															+ "<td class='TableData'>"
															+ "<select name='datedisplay' id='datedisplay' class='BigSelect'>"
															+ "<option value=''></option>"
															+ "<option value='yyyy-MM-dd'>yyyy-MM-dd</option>"
															+ "<option value='yyyy-MM-dd HH:mm'>yyyy-MM-dd HH:mm</option>"
															+ "<option value='yyyy-MM-dd HH:mm:ss'>yyyy-MM-dd HH:mm:ss</option>"
															+ "</select>"
															+ "</td>" + "</tr>");
								}else if(fieldDisplayType == "SELECT"||fieldDisplayType == "RADIO"){
									//先清空tbody中的内容
									$("#container").html("");

									$("#container")
											.append(
													"<tr>"
															+ "<td class='TableData'>系统编码：</td>"
															+ "<td class='TableData'>"
															+ "<input type='text' name='sysno' id='sysno' class='BigInput'/>"
															+ "</td>"
															+ "</tr>");
								}else if(fieldDisplayType == "CHECKBOX"){
									//先清空tbody中的内容
									$("#container").html("");

									$("#container")
											.append(
													"<tr>"
															+ "<td class='TableData'>显示标签：</td>"
															+ "<td class='TableData'>"
															+ "<input type='text' name='lable' id='lable' class='BigInput'/>"
															+ "</td>"
															+ "</tr>");
									
									
								}else if(fieldDisplayType == "ASSOCIATE"){
									//先清空tbody中的内容
									$("#container").html("");
									$("#container")
									.append(
											"<tr>"
													+ "<td class='TableData'>选择主表：</td>"
													+ "<td class='TableData'>"
													+ "<select id='mainTable' name='mainTable' class='BigSelect'></select>"
													+ "</td>"
													+ "</tr>"+
											"<tr>"
													+ "<td class='TableData'>关联主键：</td>"
													+ "<td class='TableData'>"
													+ "<select id='key' name='key' class='BigSelect'></select>"
													+ "</td>"
													+ "</tr>"+
											"<tr align='center'>"
													+ "<td class='TableData' colspan='2'>"
													+"<select multiple style='height:160px;width:140px;' id='mainTableFields'>"
													+"</select>"
													+"&nbsp;&nbsp;"
													+"<select multiple style='height:160px;width:140px;' id='currentTableFields'>"
													+"</select>"
													+"<br/>"
													+"<button class='btn btn-primary' onclick='doMapping()' type='button' >映射</button>"
													+ "</td>"
													+ "</tr>"+
											"<tr>"
													+"<td class='TableData' colspan='2' id='detailItem'>"
											        +"</td>"
											        +"<tr>"
													+"<td class=\"TableData\">主表过滤条件：</td>"
													+"<td class=\"TableData\"><textarea rows=\"5\" cols=\"52\""
													+ "id=\"sqlFilter\" name=\"sqlFilter\" class=\"BigTextarea\"></textarea>"
													+"<br>$[USER_NAME] 当前用户名 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$[USER_ID] 当前用户ID</br>"
													+"$[DEPT_NAME] 当前部门名 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$[DEPT_ID] 当前部门ID</br>"
												    +"$[ROLE_NAME] 当前角色名 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$[ROLE_ID] 当前角色ID</br>"
													+"</td>"
												+"</tr>"
											        
											        
											        
												    +"</tr>");
									//动态添加主表的列表
									getMainTableList();
									//加载主表的列
									var mTId=$("#mainTable").val();
									renderFields(mTId);
									//加载当前表格的字段
									getCurrentTableFields();
									//加载主表的字段
									getMainTableFields(mTId);
									//动态加载关联主键的值
									$("#mainTable").bind("change",function(){
										var mainTableId=$("#mainTable").val();
										renderFields(mainTableId);
										getMainTableFields(mainTableId);
									});
									
									
								}else{
									
									//清空tbody中的内容
									$("#container").html("");
									
								}

							});

});
	</script>
</head>
<body style="padding: 5px" onload="doInit()">
	<div class="moduleHeader">
		<b> <%
 	if (sid == 0) {
 		out.print("添加");
 	} else {
 		out.print("编辑");
 	}
 %>字段信息
		</b>
	</div>
	<center>
		<form id="form1">
			<table class="TableBlock" style="width: 470px">
				<tbody>
					<tr>
						<td class="TableData">字段标识（英文）：</td>
						<td class="TableData"><input type="text"
							class="BigInput easyui-validatebox" required="true"
							name="fieldName" id="fieldName" /></td>
					</tr>
					<tr>
						<td class="TableData">字段描述（中文）：</td>
						<td class="TableData"><input type="text"
							class="BigInput easyui-validatebox" required="true"
							name="fieldDesc" id="fieldDesc" /></td>
					</tr>
					<tr>
						<td class="TableData">别名（英文）：</td>
						<td class="TableData"><input type="text"
							class="BigInput easyui-validatebox" required="true" name="alias"
							id="alias" /></td>
					</tr>
					<tr>
						<td class="TableData">字段类型：</td>
						<td class="TableData"><select name="fieldType" id="fieldType"
							class="BigSelect" onchange="typeChanged(this)">
								<option value="NUMBER">数字类型</option>
								<option value="DATE">日期类型</option>
								<option value="DATETIME">日期和时间类型</option>
								<option value="CHAR">字符类型</option>
								<option value="VARCHAR">字符串类型</option>
								<option value="TEXT">文本类型</option>
						</select></td>
					</tr>
					<tr>
						<td class="TableData">字段类型扩展：</td>
						<td class="TableData"><input type="text" name="fieldTypeExt"
							id="fieldTypeExt" class="BigInput" /></td>
					</tr>

					<tr>
						<td class="TableData">默认值：</td>
						<td class="TableData"><input type="text" name="defaultVal"
							id="defaultVal" class="BigInput" /></td>
					</tr>
					<tr>
						<td class="TableData">是否为主键：</td>
						<td class="TableData"><select name="primaryKeyFlag"
							id="primaryKeyFlag" class="BigSelect">
								<option value="0">否</option>
								<option value="1">是</option>
						</select></td>
					</tr>
					<tr>
						<td class="TableData">是否必填：</td>
						<td class="TableData"><select name="isRequired"
							id="isRequired" class="BigSelect">
								<option value="0">否</option>
								<option value="1">是</option>
						</select></td>
					</tr>
					<tr>
						<td class="TableData">主键生成策略：</td>
						<td class="TableData"><select name="generatedType"
							id="generatedType" class="BigSelect">
								<option value="0"></option>
								<option value="1">本地自增策略</option>
								<option value="2">GUID生成策略</option>
								<option value="4">序列生成策略</option>
								<option value="3">自定义生成策略</option>
						</select></td>
					</tr>
					<tr>
						<td class="TableData">策略表达式<br />（只针对序列和自定义生成策略）：
						</td>
						<td class="TableData"><input type="text"
							name="generatePlugin" id="generatePlugin" class="BigInput" /></td>
					</tr>

					<tr>
						<td class="TableData">字段显示类型</td>
						<td class="TableData"><select name="fieldDisplayType"
							id="fieldDisplayType" class="BigSelect">
								<option value=""></option>
								<option value="NUMBER">数字</option>
								<option value="DATE">日期</option>
								<option value="INPUT">单行文本</option>
								<option value="TEXTAREA">多行文本</option>
								<option value="SELECT">下拉列表</option>
								<option value="RADIO">单项选择</option>
								<option value="CHECKBOX">多项选择</option>
								<option value="SINGLEPERSON">单选人员</option>
								<option value="SINGLEDEPT">单选部门</option>
								<option value="SINGLEROLE">单选角色</option>
								<option value="MANYPERSON">多选人员</option>
								<option value="MANYDEPT">多选部门</option>
								<option value="MANYROLE">多选角色</option>
								<option value="ASSOCIATE">关联主表</option>
								<option value="CURRENTUSER">当前用户</option>
								<option value="CURRENTDEPT">当前部门</option>
								<option value="CURRENTROLE">当前角色</option>
								<option value="CURRENTDATE">当前日期</option>
								<option value="CURRENTTIME">当前时间</option>
								<option value="FILEUPLOAD">上传控件</option>
						</select></td>
					</tr>
					<input type="hidden" name="fieldControlModel" id="fieldControlModel"/>
				</tbody>
				<tbody id="container">

				</tbody>
				<tfoot>
					<tr align="center">
						<td colspan="2">
							<button type="button" class="btn btn-primary" onclick="commit()">提交</button>
							&nbsp;&nbsp;
							<button type="button" class="btn btn-default"
								onclick="window.location = 'field_list.jsp?tableId=<%=tableId%>&catId=<%=catId%>'">返回</button>
						</td>
					</tr>
				</tfoot>
			</table>
			<input type="hidden" name="sid" value="<%=sid%>" /> <input
				type="hidden" name="bisTableId" value="<%=tableId%>" />
		</form>
	</center>
</body>
</html>
