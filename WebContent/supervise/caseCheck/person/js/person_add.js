//执法人员id
var personList = [];
//监督人员id
var supPersonList = [];
//已选评查人员信息
var casecheckPersonList = [];
//删除评查人员index
var DelCasecheckPerson = [];
//删除评查人员id
var delPersonList = [];
function doInit(){
	//执法人员查询初始化表格
	checkDatagridInit();
	//监督人员查询初始化表格
	checkSupDatagridInit();
	//入库人员初始化表格
	putDatagridInit();
	//执法人员回车搜索
	$('#perName').textbox('textbox').keydown(function (e) {
	    if (e.keyCode == 13) {
	    	queryPer();
	    }
	});
	$('#perDeptName').textbox('textbox').keydown(function (e) {
	    if (e.keyCode == 13) {
	    	queryPer();
	    }
	});
	//监督人员回车搜索
	$('#supName').textbox('textbox').keydown(function (e) {
	    if (e.keyCode == 13) {
	    	querySup();
	    }
	});
	$('#supDeptName').textbox('textbox').keydown(function (e) {
	    if (e.keyCode == 13) {
	    	querySup();
	    }
	});
}
function save(){
	var param = {
		personIds:JSON.stringify(personList),
		supPersonIds:JSON.stringify(supPersonList)
	}
	var json = tools.requestJsonRs("/casecheckPersonCtrl/save.action",param); 
	return json.rtState;
}
//执法人员查询初始化表格
function checkDatagridInit(){
	datagrid = $('#checkDatagrid').datagrid({
		url : contextPath + '/OfficialsCtrl/findSearchListBypageQuery.action?isCasecheckPerson=1',
		pagination: true,
		singleSelect:true,
		pageSize : 20,
        pageList : [ 10, 20, 50, 100 ],
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		idField:'id',//主键列
		fitColumns:true,//列是否进行自动宽度适应  
		columns:[[
			{ field : 'id', title : '', checkbox : true},
			{field:'name',title:'姓名',width:50,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
            		var optsStr = "<a href=\"#\" title=" + value + " onclick=\"look('" + rowData.id + "')\">" + value + "</a>";
					return optsStr;
				}
	            },},
			{field:'sex',title:'性别',width:30,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					if(value == '01'){
						value = '男';
					}else{
						value = '女';
					}
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'departmentName',title:'所属部门',width:100,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'jobClass',title:'职级',width:50,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'personType',title:'人员类型',width:50,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
		]],
		singleSelect: false,
		checkOnSelect: true,
		onDblClickRow:function(rowIndex, rowData){
			doubleCasePer(rowData);
		}
	});
}
//监督人员查询初始化表格
function checkSupDatagridInit(){
	datagrid = $('#checkSupDatagrid').datagrid({
		url : contextPath + '/SupPersonController/findSearchListBypage.action?isCasecheckPerson=1',
		pagination: true,
		singleSelect:true,
		pageSize : 20,
        pageList : [ 10, 20, 50, 100 ],
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		idField:'id',//主键列
		fitColumns:true,//列是否进行自动宽度适应  
		columns:[[
			{ field : 'id', title : '', checkbox : true},
			{field:'name',title:'姓名',width:50,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
            		var optsStr = "<a href=\"#\" title=" + value + " onclick=\"lookSup('" + rowData.id + "')\">" + value + "</a>";
					return optsStr;
				}
	            },},
			{field:'sex',title:'性别',width:30,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					if(value == '01'){
						value = '男';
					}else{
						value = '女';
					}
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'departmentName',title:'所属部门',width:100,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'jobClass',title:'职级',width:50,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'personType',title:'人员类型',width:50,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
		]],
		singleSelect: false,
		checkOnSelect: true,
		onDblClickRow:function(rowIndex, rowData){
			doubleCaseSup(rowData);
		}
	});
}
//入库人员初始化表格
function putDatagridInit(){
	datagrid = $('#putDatagrid').datagrid({
		data:casecheckPersonList,
		pagination: true,
		singleSelect:false,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		pageSize : 20,
        pageList : [ 10, 20, 50, 100 ],
		idField:'id',//主键列
		fitColumns:true,//列是否进行自动宽度适应  
		columns:[[
			{ field : 'id', title : '', checkbox : true},
			{field:'name',title:'姓名',width:50,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'sex',title:'性别',width:30,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					if(value == '01'){
						value = '男';
					}else{
						value = '女';
					}
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'departmentName',title:'所属部门',width:100,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'jobClass',title:'职级',width:50,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'personType',title:'人员类型',width:50,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
		]],
		checkOnSelect: true,
		onSelect:function (rowIndex,rowData){
			DelCasecheckPerson.push(rowIndex);
		},
		onUnselect:function (rowIndex,rowData){
			for(var i=0;i<DelCasecheckPerson.length;i++){
				if(DelCasecheckPerson[i] == rowIndex){
					DelCasecheckPerson.splice(i,1);
				}
			}
		},
		onDblClickRow:function(rowIndex, rowData){
			doubleCaseDel(rowIndex,rowData);
		}
	});
}
//增加
function selectedCasecheck(){
	var personItems = $('#checkDatagrid').datagrid('getChecked');
	var supPersonItems = $('#checkSupDatagrid').datagrid('getChecked');
	for(var i=0;i<personItems.length;i++){
		personList.push(personItems[i].id);
		casecheckPersonList.push(personItems[i]);
	}
	for(var i=0;i<supPersonItems.length;i++){
		supPersonList.push(supPersonItems[i].id);
		casecheckPersonList.push(supPersonItems[i]);
	}
	//刷新入库人员
	$("#putDatagrid").datagrid("loadData",casecheckPersonList);
	//刷新执法人员和监督人员
	if(personItems.length > 0){
		$('#checkDatagrid').datagrid("reload",{personIds:JSON.stringify(personList)});
		$('#checkDatagrid').datagrid("clearSelections");
	}
	if(supPersonItems.length > 0){
		$('#checkSupDatagrid').datagrid("reload", {supPersonIds:JSON.stringify(supPersonList)});
		$('#checkSupDatagrid').datagrid("clearSelections");
	}
}
//执法人员双击增加
function doubleCasePer(data){
	personList.push(data.id);
	casecheckPersonList.push(data);
	//刷新入库人员
	$("#putDatagrid").datagrid("loadData",casecheckPersonList);
	//刷新执法人员和监督人员
	$('#checkDatagrid').datagrid("reload",{personIds:JSON.stringify(personList)});
	$('#checkDatagrid').datagrid("clearSelections");
	
}
//监督人员双击增加
function doubleCaseSup(data){
	supPersonList.push(data.id);
	casecheckPersonList.push(data);
	//刷新入库人员
	$("#putDatagrid").datagrid("loadData",casecheckPersonList);
	//刷新执法人员和监督人员
	$('#checkSupDatagrid').datagrid("reload",{supPersonIds:JSON.stringify(supPersonList)});
	$('#checkSupDatagrid').datagrid("clearSelections");
	
}
//删除
function removeCasecheck(){
	var personState = false;
	var supPersonState = false;
	//降序排列
	DelCasecheckPerson.sort(function (x,y) {
        return y-x;
    });
	for(var i=0;i<DelCasecheckPerson.length;i++){
		delPersonList.push(casecheckPersonList[DelCasecheckPerson[i]].id);
		casecheckPersonList.splice(DelCasecheckPerson[i],1);
	}
	//刷新入库人员
	$("#putDatagrid").datagrid("loadData",casecheckPersonList);
	$('#putDatagrid').datagrid("clearSelections");
	DelCasecheckPerson = [];
	//刷新执法人员
	for(var i=0;i<delPersonList.length;i++){
		for(var j=0;j<personList.length;j++){
			if(personList[j] == delPersonList[i]){
				personList.splice(j,1);
				personState = true;
			}
		}
	}
	//刷新监督人员
	for(var i=0;i<delPersonList.length;i++){
		for(var j=0;j<supPersonList.length;j++){
			if(supPersonList[j] == delPersonList[i]){
				supPersonList.splice(j,1);
				supPersonState = true;
			}
		}
	}
		if(personState){
			$('#checkDatagrid').datagrid("reload",{personIds:JSON.stringify(personList)});
			$('#checkDatagrid').datagrid("clearSelections");
		}
		if(supPersonState){
			$('#checkSupDatagrid').datagrid("reload", {supPersonIds:JSON.stringify(supPersonList)});
			$('#checkSupDatagrid').datagrid("clearSelections");	
		}
}
//双击删除
function doubleCaseDel(index,data){
	var personState = false;
	var supPersonState = false;
	casecheckPersonList.splice(index,1);
	//刷新入库人员
	$("#putDatagrid").datagrid("loadData",casecheckPersonList);
	$('#putDatagrid').datagrid("clearSelections");
	//刷新执法人员
	for(var j=0;j<personList.length;j++){
		if(personList[j] == data.id){
			personList.splice(j,1);
			personState = true;
		}
	}
	//刷新监督人员
	for(var j=0;j<supPersonList.length;j++){
		if(supPersonList[j] == data.id){
			supPersonList.splice(j,1);
			supPersonState = true;
		}
	}
	if(personState){
		$('#checkDatagrid').datagrid("reload",{personIds:JSON.stringify(personList)});
		$('#checkDatagrid').datagrid("clearSelections");
	}
	if(supPersonState){
		$('#checkSupDatagrid').datagrid("reload", {supPersonIds:JSON.stringify(supPersonList)});
		$('#checkSupDatagrid').datagrid("clearSelections");	
	}
}
//执法人员查询
function queryPer(){
	var paramsPer = {
			name : $("#perName").val(),
			departmentName : $("#perDeptName").val(),
		};
		$('#checkDatagrid').datagrid("reload", paramsPer);
		$('#checkDatagrid').datagrid("clearSelections");
}
//监督人员查询
function querySup(){
	var paramsSup = {
			name : $("#supName").val(),
			departmentName : $("#supDeptName").val(),
		};
		$('#checkSupDatagrid').datagrid("reload", paramsSup);
		$('#checkSupDatagrid').datagrid("clearSelections");
}
/*
 * 执法人员查看
 */
function look(id) {
	top.bsWindow(contextPath + "/supervise/officials/officials_search_look.jsp?id="+id, "查看",
			{
				width : "1200",
				height : "500",
				buttons : [ {
					name : "关闭",
					classStyle : "btn-alert-gray"
				} ],
				submit : function(v, h) {
					var cw = h[0].contentWindow;
					if (v == "保存") {
						var status = cw.save();
						if (status == true) {
							$("#datagrid").datagrid("reload");
							$('#datagrid').datagrid("clearSelections");
							return true;
						}
					} else if (v == "关闭") {
						return true;
					}
				}
			});

}
/*
 * 监督人员查看
 */
function lookSup(id) {
	top.bsWindow(contextPath + "/supervise/supervise/supperson_search_look.jsp?id="+id, "查看",
			{
				width : "700",
				height : "320",
				buttons : [{
					name : "关闭",
					classStyle : "btn-alert-gray"
				} ],
				submit : function(v, h) {
					var cw = h[0].contentWindow;
					if (v == "保存") {
						var status = cw.save();
						if (status == true) {
							$("#datagrid").datagrid("reload");
							$('#datagrid').datagrid("clearSelections");
							return true;
						}
					} else if (v == "关闭") {
						return true;
					}
				}
			});

}