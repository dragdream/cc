var datagrid;
var module;
var para = {};
function doInit(){
	$.jBox.tip("正在加载数据……","loading");
	tools.requestJsonRs(contextPath+"/bisModule/getBisModule.action?uuid="+moduleId,{},true,function(json){
		module = json.rtData;
		
		$("#module_title").html(module.moduleName);
		var sp = module.createPrivIds.split(",");
		for(var i=0;i<sp.length;i++){
			if(sp[i]==userId){
				$("#newBtn").show();
				break;
			}
		}
		sp = module.editPrivIds.split(",");
		var editPriv = false;
		for(var i=0;i<sp.length;i++){
			if(sp[i]==userId){
				editPriv = true;
				break;
			}
		}
		sp = module.delPrivIds.split(",");
		var delPriv = false;
		for(var i=0;i<sp.length;i++){
			if(sp[i]==userId){
				delPriv = true;
				break;
			}
		}
		
		var metadata = tools.requestJsonRs(contextPath+"/bisView/listBisViewListItem.action",{identity:module.bisViewId}).rows;
		var opts = [];
		
		var html = [];
		for(var i=0;i<metadata.length;i++){
			var field = metadata[i];
			if(field.isSearch==1){//查询字段
				if(field.model!=null && field.model!="null" && field.model!=""){
					var codes = getSysCodeByParentCodeNo(field.model , "");
					html.push(field.title+"：<select name='"+field.searchField+"_"+field.fieldType+"_SEARCH' class='BigSelect'>");
					html.push("<option value=''></option>");
					for(var j=0;j<=codes.length-1;j++){
						html.push("<option value='"+codes[j].codeNo+"'>"+codes[j].codeName+"</option>");
					}
					html.push("</select>");
				}else{
					html.push(field.title+"：<input type='text' name='"+field.searchField+"_"+field.fieldType+"_SEARCH' class='BigInput'/>&nbsp;");
				}
			}
			opts.push({
				formatterScript:field.formatterScript,
				field:field.field,
				title:field.title,
				width:field.width,
				formatter:function(data,rowData,index){
					if(this.formatterScript=="" || this.formatterScript==null || this.formatterScript=="null" || this.formatterScript==undefined){
						return data;
					}else{
						eval(this.formatterScript);
						return data;
					}
				}
			});
		}
		
		if(html.length!=0){
			html.push("&nbsp;&nbsp;<button class='btn btn-default' type='button' onclick='search()'>查询</button>");
			html.push("&nbsp;&nbsp;<button class='btn btn-default' type='button' onclick='$(this).parent()[0].reset();search();'>重置</button>");
		}
		
		$("#searchForm").html(html.join(""));
		
		opts.push({
			field:"__",
			title:"操作",
			width:100,
			formatter:function(data,rowData,index){
				var render = [];
				render.push("<a href='#' onclick=\"view('"+rowData.BISKEY+"')\">查看</a>");
				if(editPriv){
					render.push("<a href='#' onclick=\"edit('"+rowData.BISKEY+"')\">编辑</a>");	
				}
				if(delPriv){
					render.push("<a href='javascript:void(0)' onclick=\"del('"+rowData.BISKEY+"')\">删除</a>");
				}
				return render.join("&nbsp;&nbsp;");
			}
		});
		
		datagrid = $('#datagrid').datagrid({
			url:contextPath+'/myModule/dflist.action?moduleId='+moduleId,
			singleSelect:true,
			toolbar:'#toolbar',//工具条对象
			checkbox:false,
			border:false,
			idField:'BISKEY',//主键列
			fitColumns:false,//列是否进行自动宽度适应
			columns:[opts],
			pageSize:[20,30,40,50,60,70],
			pagination:true,
			queryParams:para
		});
		
		$.jBox.tip("加载完毕","success");
	});
}

function add(){
	window.location = "addOrUpdate.jsp?moduleId="+moduleId;
}

function edit(bisKey){
	window.location = "addOrUpdate.jsp?moduleId="+moduleId+"&bisKey="+bisKey;
}

function view(bisKey){
	window.location = "view.jsp?moduleId="+moduleId+"&bisKey="+bisKey;
}

function del(bisKey){
	if(window.confirm("是否删除该行数据？")){
		var json = tools.requestJsonRs(contextPath+"/myModule/delete.action",{bisKey:bisKey,moduleId:moduleId});
		if(json.rtState){
			datagrid.datagrid("reload");
			datagrid.datagrid("unselectAll");
		}
	}
}

function search(){
	var tmp = tools.formToJson($("#searchForm"));
	for(var key in tmp){
		para[key] = tmp[key];
	}
	
	$('#datagrid').datagrid("reload",para);
}