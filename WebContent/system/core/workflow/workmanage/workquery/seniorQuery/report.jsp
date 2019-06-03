<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="overflow:hidden">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<title>统计报表</title>
<style>
	table{
		border-collapse:collapse;
		border:0px;
	}
	td{
		border:1px solid #000;
		padding:5px;
	}
</style>
<script>
	function doInit(){
		//渲染表头
		var headerHtml = ["<tr>"];
		headerHtml.push("<td nowrap style='font-weight:bold;background:#f0f0f0'>分组："+xparent.groupDesc+"</td>");
		for(var i=0;i<xparent.showFieldDesc.length;i++){
			headerHtml.push("<td nowrap style='font-weight:bold;background:#f0f0f0'>"+xparent.showFieldDesc[i]+"</td>");
		}
		headerHtml.push("</tr>");
		$("#thead").html(headerHtml.join(""));
		
		tools.requestJsonRs(contextPath+"/seniorQuery/toSeniorQuery.action?page=1&rows=100000000",xparent.reportParams,true,function(json){
			var rows = json.rows;
			//遍历数据，并进行分组
			var groupMap = {};
			for(var i=0;i<rows.length;i++){
				var tmp = groupMap[getRealValue(xparent.group,rows[i])];
				if(!tmp){//如果分组不存在，则将其分组加入
					groupMap[getRealValue(xparent.group,rows[i])] = [rows[i]];
				}else{
					groupMap[getRealValue(xparent.group,rows[i])].push(rows[i]);
				}
			}
			
			//遍历分组，渲染表格体
			var bodyHtml = [];
			for(var key in groupMap){
				var list = groupMap[key];
				for(var i=0;i<list.length;i++){
					bodyHtml.push("<tr>");
					if(i==0){
						if(xparent.sumField.length==0){
							bodyHtml.push("<td style='background:#f0f0f0;text-align:center' nowrap rowspan="+(list.length)+" style='font-weight:bold'>"+key+"<br/>共"+list.length+"项</td>");
						}else{
							bodyHtml.push("<td style='background:#f0f0f0;text-align:center' nowrap rowspan="+(list.length+1)+" style='font-weight:bold'>"+key+"<br/>共"+list.length+"项</td>");
						}
						
					}
					
					//渲染指定的显示列
					for(var j=0;j<xparent.showField.length;j++){
						bodyHtml.push("<td nowrap>"+getRealValue(xparent.showField[j],list[i])+"</td>");
					}
					
					
					bodyHtml.push("</tr>");
				}
				
				
				if(xparent.sumField.length!=0){
					bodyHtml.push("<tr>");
					//渲染指定的显示列
					for(var i=0;i<xparent.showField.length;i++){
						var exists = false;
						for(var j=0;j<xparent.sumField.length;j++){
							if(xparent.sumField[j]==xparent.showField[i]){
								exists = true;
								break;
							}
						}
						if(exists){
							var sum = 0;
							for(var j=0;j<list.length;j++){
								var num = Number(replaceDot(getRealValue(xparent.showField[i],list[j])));
								if(isNaN(num)){
									num = 0;
								}
								sum = addNum(sum,num);
							}
							bodyHtml.push("<td style='background:#f0f0f0'><b>合计："+sum+"</b></td>");
						}else{
							bodyHtml.push("<td></td>");
						}
						
					}
					bodyHtml.push("</tr>");
				}
				
			}
			
			if(xparent.sumField.length!=0){
				var foothtml = [];
				//渲染指定的显示列
				for(var i=0;i<xparent.showField.length;i++){
					var exists = false;
					for(var j=0;j<xparent.sumField.length;j++){
						if(xparent.sumField[j]==xparent.showField[i]){
							exists = true;
							break;
						}
					}
					if(exists){
						var sum = 0;
						for(var j=0;j<rows.length;j++){
							var num = Number(replaceDot(getRealValue(xparent.showField[i],rows[j])));
							if(isNaN(num)){
								num = 0;
							}
							sum = addNum(sum,num);
						}
						foothtml.push("<td style='color:blue'><b>总计："+sum+"</b></td>");
					}else{
						foothtml.push("<td></td>");
					}
					
				}
				$("#tfoot_tr").append(foothtml.join(""));
			}else{
				$("#tfoot").hide();
			}
			$("#tbody").html(bodyHtml.join(""));
			
		});
		
		function getRealValue(field,data){
			if(field=="RUN_NAME"){
				return data["runName"];
			}else if(field=="RUN_ID"){
				return data["runId"];
			}else if(field=="END_FLAG"){
				if(data["endFlag"]==1){
					return "<span style='color:green'>已结束</span>";
				}else{
					return "<span style='color:red'>进行中</span>";
				}
			}else if(field=="BEGIN_TIME"){
				return data["beginTimeDesc"];
			}else if(field=="END_TIME"){
				return data["endTimeDesc"];
			}else{
				if(data[field+"_type"]=="xfeedback"){//会签控件
					var fbDatas= data[field]==null?"":data[field];
				    if(fbDatas==""){
	                    return "";
				    }else{
				    	var fbHtml="<table style='width:100%'>";
				    	for(var i=0;i<fbDatas.length;i++){
				    		fbHtml+="<tr><td>"+fbDatas[i].content+"</td><td>"+fbDatas[i].createUserName+"</td><td>"+fbDatas[i].createTime+"</td></tr>";
				    	}
				    	fbHtml+="</table>";
				    	return fbHtml;
				    }
					
				}else if(data[field+"_type"]=="xlist"){//列表控件
					return data[field]==null?"":showXlistTable(data[field]);
				
				}else{
					return data[field]==null?"":data[field];
				}
				
			}
		}
	}
	
	//自定义加法运算
	function addNum (num1, num2) {
	 var sq1,sq2,m;
	 try {
	  sq1 = num1.toString().split(".")[1].length;
	 }
	 catch (e) {
	  sq1 = 0;
	 }
	 try {
	  sq2 = num2.toString().split(".")[1].length;
	 }
	 catch (e) {
	  sq2 = 0;
	 }
	 m = Math.pow(10,Math.max(sq1, sq2));
	 return (num1 * m + num2 * m) / m;
	}
	
	function replaceDot(m){
		
		if(!m || m==null){
			m = "";
		}else{
			m = ""+m;
			m = m.replace(/,/gi,"");
		}
		return m;
	}
	
	
//列表控件显示成表格
function showXlistTable(dataStr){
	if(dataStr==undefined || dataStr==""){
		return "";
	}
	var value = tools.strToJson(dataStr);
	if(value.length==0){
		return "";
	}
	var html="<table>";
	for(var m in value[0]){
		if(m=='bisKey'){
			continue;
		}
		html+="<td>"+m+"</td>";
	}
 	html+="</tr>";
 	
	for(var i = 0 ;i<value.length;i++){
		html+="<tr>";
		for(var m in value[i]){
			if(m=='bisKey'){
	 			continue;
	 		}
	 		html+="<td>"+value[i][m]+"</td>";
	 	}
		html+="</tr>";
	} 
	html+="</table>";
    return html; 
}
	
</script>
</head>
<body onload="doInit()" style="overflow:auto">
	<table>
		<thead id="thead">
			
		</thead>
		<tbody id="tbody">
			
		</tbody>
		<tbody id="tfoot">
			<tr id="tfoot_tr">
				<td nowrap></td>
			</tr>
		</tbody>
	</table>
</body>
</html>
