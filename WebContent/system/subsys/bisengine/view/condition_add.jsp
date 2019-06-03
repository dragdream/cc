<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<title>条件连接</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
function doInit(){
	tables = xparent.tables;
	for(var i=0;i<tables.length;i++){
		$("#table1").append("<option value=\""+tables[i].sid+","+tables[i].name+","+tables[i].index+","+tables[i].desc+"\">"+tables[i].desc+"("+tables[i].name+"_"+tables[i].index+")</option>");
	}
	
	selectChange(window.table1,window.field1);
}


function selectChange(obj,field){
	$(field).html("");
	var tableSid = obj.value.split(",")[0];
	var tableName = obj.value.split(",")[1];
	var tableIndex = obj.value.split(",")[2];
	var tableDesc = obj.value.split(",")[3];
	
	var json = tools.requestJsonRs(contextPath+"/bisTableField/datagrid.action?bisTableId="+tableSid);
	var list = json.rows;
	for(var i=0;i<list.length;i++){
		$(field).append("<option value=\""+tableName+","+tableIndex+","+list[i].fieldName+","+tableDesc+","+list[i].fieldDesc+","+list[i].fieldType+"\">"+list[i].fieldDesc+"</option>");
	}
}

/**
 * [{t1:表1|1,t2:表2|2,l1:表1字段,l2:表2字段,desc:xxxxxxxxx}]
 */
function ok(){
	var f1 = $("#field1").val();
	var name1 = f1.split(",")[0];
	var index1 = f1.split(",")[1];
	var fieldName1 = f1.split(",")[2];
	var tableDesc1 = f1.split(",")[3];
	var fieldDesc1 = f1.split(",")[4];
	var fieldType = f1.split(",")[5];
	
	var f2 = $("#field2").val();
	
	var desc = [];
	var exp = [];
	var oper = $("#oper");
	var operDesc = "";
	if(oper.val()=="="){
		operDesc = "&nbsp;等于&nbsp;";
	}else if(oper.val()==">"){
		operDesc = "&nbsp;大于&nbsp;";
	}else if(oper.val()==">="){
		operDesc = "&nbsp;大于等于&nbsp;";
	}else if(oper.val()=="<"){
		operDesc = "&nbsp;小于&nbsp;";
	}else if(oper.val()=="<="){
		operDesc = "&nbsp;小于等于&nbsp;";
	}else if(oper.val()=="!="){
		operDesc = "&nbsp;不等于&nbsp;";
	}else if(oper.val()=="like"){
		operDesc = "&nbsp;包含&nbsp;";
	}else if(oper.val()=="like1"){
		operDesc = "&nbsp;开始于&nbsp;";
	}else if(oper.val()=="like2"){
		operDesc = "&nbsp;结束于&nbsp;";
	}
	
	if(fieldType=="NUMBER"){//数字型
		if(oper.val()=="="){
			exp.push(name1+"_"+index1+"."+fieldName1+"="+f2);
		}else if(oper.val()==">"){
			exp.push(name1+"_"+index1+"."+fieldName1+">"+f2);
		}else if(oper.val()==">="){
			exp.push(name1+"_"+index1+"."+fieldName1+">="+f2);
		}else if(oper.val()=="<"){
			exp.push(name1+"_"+index1+"."+fieldName1+"<"+f2);
		}else if(oper.val()=="<="){
			exp.push(name1+"_"+index1+"."+fieldName1+"<="+f2);
		}else if(oper.val()=="!="){
			exp.push(name1+"_"+index1+"."+fieldName1+"!="+f2);
		}else{
			alert("数字类型不存在该条件操作符");
			return;
		}
	}else if(fieldType=="TEXT" || fieldType=="VARCHAR" || fieldType=="CHAR" || fieldType=="TEXT"){//字符串类型
		if(oper.val()=="="){
			exp.push(name1+"_"+index1+"."+fieldName1+" = '"+f2+"'");
		}else if(oper.val()==">"){
			exp.push(name1+"_"+index1+"."+fieldName1+" > '"+f2+"'");
		}else if(oper.val()==">="){
			exp.push(name1+"_"+index1+"."+fieldName1+" >= '"+f2+"'");
		}else if(oper.val()=="<"){
			exp.push(name1+"_"+index1+"."+fieldName1+" < '"+f2+"'");
		}else if(oper.val()=="<="){
			exp.push(name1+"_"+index1+"."+fieldName1+" <= '"+f2+"'");
		}else if(oper.val()=="!="){
			exp.push(name1+"_"+index1+"."+fieldName1+" != '"+f2+"'");
		}else if(oper.val()=="like"){
			exp.push(name1+"_"+index1+"."+fieldName1+" like '%"+f2+"%'");
		}else if(oper.val()=="like1"){
			exp.push(name1+"_"+index1+"."+fieldName1+" like '"+f2+"%'");
		}else if(oper.val()=="like2"){
			exp.push(name1+"_"+index1+"."+fieldName1+" like '%"+f2+"'");
		}
	}
	
	desc.push(fieldDesc1+operDesc+f2);
	
	xparent.appendConditionItem({desc:desc.join(""),exp:exp.join("")});
	CloseWindow();
}

</script>
<style>
td{
border:1px solid gray;
padding:5px;
}
</style>
</head>
<body onload="doInit()" style="margin:10px">
<table>
	<tr>
		<td colspan="3">
			<select class="BigSelect" id="table1"  onchange="selectChange(this,window.field1)">
				
			</select>
		</td>
	</tr>
	<tr>
		<td>
			<select class="BigSelect" id="field1">
				
			</select>
		</td>
		<td>
			<center>
				<select class="BigSelect" id="oper">
					<option value="=">=</option>
					<option value=">">&gt;</option>
					<option value=">=">&gt;=</option>
					<option value="<">&lt;</option>
					<option value="<=">&lt;=</option>
					<option value="!=">!=</option>
					<option value="like">包含</option>
					<option value="like1">开始于</option>
					<option value="like2">结束于</option>
				</select>
			</center>
		</td>
		<td>
			<input id="field2" class="BigInput"/>
		</td>
	</tr>
</table>
<br/>
<button type="button" class="btn btn-info" onclick="ok()">确定</button>
</body>
</html>
