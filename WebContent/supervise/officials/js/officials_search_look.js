var code_data = [];
var datagrid = '';
function doInit(){
	var url = contextPath + "/OfficialsCtrl/getPersonById.action";
    var json = tools.requestJsonRs(url,{id:$("#id").val()});
    if(json.rtState){
    	bindJsonObj2Cntrl(json.rtData);
    	if(json.rtData.sex == '01'){
    		document.getElementById('sex').innerText = "男";
    	}else{
    		document.getElementById('sex').innerText = "女";
    	}
    	if(json.rtData.isLawcode == '01'){
    		document.getElementById('isLawcode').innerText = "具有";
    	}else{
    		document.getElementById('isLawcode').innerText = "不具有";
    	}
    }
    datagrate();
    var jsonPersonCode = tools.requestJsonRs("/personGetcodeCtrl/get.action",{id:id});
	for(var i=0;i < jsonPersonCode.rtData.length;i++){
		code_data.push(jsonPersonCode.rtData[i]);
		$("#datagrid").datagrid("loadData",code_data);
	}
}

//初始化表格
function datagrate(){
	datagrid = $('#datagrid').datagrid({
		data:code_data,
		pagination: false,
		singleSelect:true,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		idField:'id',//主键列
		fitColumns:true,//列是否进行自动宽度适应  
		columns:[[
			{field:'ID',title:'序号',align:'center',
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
			{field:'codeType',title:'证件类型',width:50,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'code',title:'证件编号',width:50,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'codeDateStr',title:'发证日期',width:100,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'awardDept',title:'颁发机关',width:100,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'codeValid',title:'有效期限',width:100,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
		]],
		singleSelect: true,
		checkOnSelect: true
	});
}

