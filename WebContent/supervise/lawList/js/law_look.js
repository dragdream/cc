function doInit(){
	var url = contextPath + "/lawInfoController/get.action.action";
    var json = tools.requestJsonRs(url,{id:id});
    if(json.rtState){
    	bindJsonObj2Cntrl(json.rtData);
    }
    //处理附带的附件信息
		var attachModels = json.rtData.attachModels;
		for(var i=0;i<attachModels.length;i++){
			attachModels[i].priv = 1+2+4+8+16;
			var attachElement = tools.getAttachElement(attachModels[i]);
			$("#attachDiv").append(attachElement);
		}
    datagrate();
}
function datagrate(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/detailController/listByPage.action',
		queryParams:{id:id},
		pagination:true,
		singleSelect:true,
		pageSize : 20,
        pageList : [ 10, 20, 50, 100 ],
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		idField:'uuid',//主键列
		fitColumns:true,//列是否进行自动宽度适应  
		columns:[[
		    {field:'uuid',checkbox : true,}, 
			{field:'detailSeries',title:'编号',align:'center' , halign: 'center',width:'6%'},
			{field:'detailChapter',title:'章',align:'center' , halign: 'center',width:'6%'},
			{field:'detailStrip',title:'条',align:'center' , halign: 'center',width:'6%'},
			{field:'detailFund',title:'款',align:'center' , halign: 'center',width:'6%',},
			{field:'detailItem',title:'项',align:'center' , halign: 'center',width:'6%'},
			{field:'detailCatalog',title:'目',align:'center' , halign: 'center',width:'6%'},
			{field:'content',title:'内容',align:'center' , halign: 'center',width:100},
		]],
		singleSelect: false,
		selectOnCheck: true,
		checkOnSelect: true
	}); 
}