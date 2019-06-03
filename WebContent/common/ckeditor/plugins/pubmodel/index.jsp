<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
.pubmodel_plugin_item{
	padding:5px;
	border:1px solid gray;
	margin-bottom:5px;
}
.pubmodel_plugin_item:hover{
	background:#f0f0f0;
	cursor:pointer;
}
.pubmodel_plugin_item_active{
	background:#f0f0f0;
	cursor:pointer;
}
</style>
<div style="padding:10px;font-size:12px" id="pubmodel_plugin">
	
</div>
<script>
	$(function(){
		var json = tools.requestJsonRs(contextPath+"/pubTemplate/listTemplateSimple.action");
		var html = "";
		for(var i=0;i<json.rtData.length;i++){
			html+="<div class='pubmodel_plugin_item' sid='"+json.rtData[i].sid+"'>";
			html+="<b>"+json.rtData[i].tplName+"</b>";
			html+="<br/>";
			html+=json.rtData[i].tplDesc;
			html+="</div>";
		}
		$("#pubmodel_plugin").append(html);
		
		$("#pubmodel_plugin div").each(function(i,obj){
			
			$(obj).click(function(){
				$("#pubmodel_plugin div").each(function(i,obj1){
					$(obj1).removeClass("pubmodel_plugin_item_active");
				});
				$(obj).addClass("pubmodel_plugin_item_active");
			});
		});
	})
</script>
