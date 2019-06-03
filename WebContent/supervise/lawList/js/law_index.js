
/*初始化*/
var datagrid;
function doInit() {
	initsubmitlawLevel();
	getSysCodeByParentCodeNo("LAW_TYPE","submitlawLevel");
	datagrid = $('#datagrid') .datagrid( {
						url : contextPath + '/lawInfoController/listByPage.action',
						queryParams : { gender : -1 },
						pagination : true,
						singleSelect : true,
						striped: true,
						pageSize : 20,
				        pageList : [ 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
						view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
						toolbar : '#toolbar',// 工具条对象
						border : false,
						rownumbers : false,
						fit : true,
						idField : 'id',// 主键列
						fitColumns : true,// 列是否进行自动宽度适应
						columns : [ [
								{ field : 'id', title : '', checkbox : true},
								{ field : 'name', title : '法律标题', width : 50,align:'left' , halign: 'center' },
								{ field : 'word', title : '发文字号', width : 50,align:'left' , halign: 'center' },
								{ field : 'organ', title : '发布机关', width : 50,align:'left' , halign: 'center' },
								{ field : 'timeliness', title : '时效性', width : 50,align:'left' , halign: 'center' ,formatter:
									function(data,rowData){
									if(data==01){
										return "现行有效";
									}else{
										return "失效";
									}
								}},
								{ field : 'submitlawLevel', title : '法律类别', width : 50,align:'left' , halign: 'center' ,formatter:
									function(data,rowData){
									if(data==1){
										return "法律";
									}
									if(data==2){
										return "行政法规";
									}
									if(data==3){
										return "地方性法规";
									}
									if(data==4){
										return "部门规章";
									}
									if(data==5){
										return "市政府规章";
									}
									if(data==6){
										return "其他";
									}
								}},
								{ field : 'examine', title : '审核状态', width : 50,align:'left' , halign: 'center' ,formatter:
									function(data,rowData){
									if(rowData.examine == 1){
										return "已审核";
									}else{
										return "待审核";
									}
								}
								},
								{ field : '__', title : '操作', width : 40,align:'center' , halign: 'center', formatter : function(e, rowData) {
										if (rowData.examine == 1) {
//											var optsStr = "<a href=\"#\" style=\"display:block;text-align:center\" onclick=\"delc('"
//													+ rowData.id + "')\">删&nbsp;除</a>";
										} else { 
											var optsStr = "<a href=\"#\"  onclick=\"edit('"
													+ rowData.id
													+ "')\">修改</a>&nbsp;<a href=\"#\" onclick=\"delc('"
													+ rowData.id + "')\">删除</a>";
										}
										return optsStr;
									}
								}, ] ],
						selectOnCheck : true,
						checkOnSelect : true,
						onLoadSuccess : function(data, rowData) {
							if (data) {
								$.each(data.rows, function(index, item) {
									if (item.checked) {
										$('#datagrid').datagrid('checkRow',
												index);
									}
								});
							}
						}

					});
}

function initsubmitlawLevel(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "LAW_TYPE"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: -1, codeName: "请选择"});
        $('#submitlawLevel').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            onLoadSuccess:function(){
                $('#submitlawLevel').combobox('setValue',-1);
            },
            onChange: function() {
                var powerType = $('#submitlawLevel').combobox('getValue');
                if(powerType != "") {
                    var params = {
                        parentCodeNo: "LAW_TYPE",
                        codeNo: powerType
                    };
                    var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action", params);
                    if(result.rtState) {
                        $('#powerDetail').combobox({
                            data: result.rtData,
                            valueField: 'codeNo',
                            textField: 'codeName'
                        });
                    }
                }
            }
        });
    }
}
//查询
function search(){
	var params = {name:$("#name").val(),timeliness:$("#timeliness").val(),submitlawLevel:$("#submitlawLevel").val(),examine:$("#examine").val()};
	console.log(params);
	$('#datagrid').datagrid("reload",params);
}

//超链接删除
function delc(id) {
if (window.confirm("是否确认删除该数据")) {
	var json = tools.requestJsonRs("/lawInfoController/delete.action",{id:id});
	$('#datagrid').datagrid("reload");
}
}
	/*导出excel*/
	function exportExcel() {

		var selections = $("#datagrid").datagrid("getSelections");
		if (selections.length == 0) {
			top.$.MsgBox.Confirm("提示", "是否确认导出所有查询结果？", function() {

				//获取查询参数
				var params = tools.formToJson($("#form"));
				params["runIds"] = "0";
				if (flowTypeId > 0) {
					params["flowId"] = flowTypeId;
				}
				$("#iframe0").attr(
						"src",
						contextPath
								+ "/workQuery/exportExcel.action?params="
								+ encodeURIComponent(tools
										.jsonObj2String(params)));
			});
			 if(confirm("是否确认导出所有查询结果？")){
				//导出所有的查询结果
				params["runIds"]="0";
				$("#iframe0").attr("src",contextPath+"/workQuery/exportExcel.action?params="+tools.jsonObj2String(params));
			}else{
				return;
			} 
		} else {
			var runIds = [];
			for (var i = 0; i < selections.length; i++) {
				runIds.push(selections[i].runId);
			}
			var params = tools.formToJson($("#form"));
			if (flowTypeId > 0) {
				params["flowId"] = flowTypeId;
			}
			params["runIds"] = runIds.join(",");
			//导出所勾选的数据
			$("#iframe0").attr(
					"src",
					contextPath + "/workQuery/exportExcel.action?params="
							+ encodeURIComponent(tools.jsonObj2String(params)));
		}
	}

	
	/*
	 * 新法
	 *    */  
	function add(){
		
		top.bsWindow(contextPath+"/supervise/lawList/law_add.jsp" ,"新增",{width:"600",height:"400",buttons:
			[
			 {name:"保存",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="保存"){
					var status = cw.save();
					console.log(status);
					if(status==true){
						$("#datagrid").datagrid("reload");
						return true;
					}
				}else if(v=="关闭"){
					return true;
				}
		}});

	}
	
	/*
	 * 修订
	 *    */  
	function revise(){
		var obj = $('#datagrid').datagrid("getSelections");
		if (obj==''){
			alert("请选择一条数据");
		}else{
			top.bsWindow(contextPath+"/supervise/lawList/law_revise.jsp?id="+obj[0].id ,"修订",{width:"600",height:"450",buttons:
				[
				 {name:"保存",classStyle:"btn-alert-blue"},
			 	 {name:"关闭",classStyle:"btn-alert-gray"}
				 ]
				,submit:function(v,h){
					var cw = h[0].contentWindow;
					if(v=="保存"){
						var status = cw.save();
						if(status==true){
							$("#datagrid").datagrid("reload");
							return true;
						}
					}else if(v=="关闭"){
						return true;
					}
			}});

		}
		}
		
	
	/*
	 * 废止
	 *    */  
function abolish(){
		var obj = $('#datagrid').datagrid("getSelections");
		if (obj==''){
			alert("请选择一条数据");
		}else{
			if (obj[0].timeliness=='02'){
				alert("该法律已经废止！");
			}else{
				if (window.confirm("是否确认废止？")) {
					var json = tools.requestJsonRs("/lawInfoController/abolish.action",{id:obj[0].id});
					$('#datagrid').datagrid("reload");
			    }
			}
	}
}


function lawadd(){
		
		top.bsWindow(contextPath+"/supervise/lawList/lawAdd.jsp" ,"新增",{width:"600",height:"550",buttons:
			[
			 {name:"保存",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="保存"){
					var status = cw.save();
					if(status==true){
						$("#datagrid").datagrid("reload");
						return true;
					}
				}else if(v=="关闭"){
					return true;
				}
		}});

	}

	
	/*
	 * 内容管理
	 *      */
	function editn(){
		var obj = $('#datagrid').datagrid("getSelections");
		if (obj==''){
			alert("请选择一条数据");
		}
		//console(obj[0].examineStr);
		if(obj[0].examine==1){
			//已审核
			//console.log(obj[0].examineStr);
			top.bsWindow(contextPath+"/supervise/lawList/law_contentRes.jsp?id="+obj[0].id,"修改",{width:"600",height:"450",buttons:
				[
			 	 {name:"关闭",classStyle:"btn-alert-gray"}
				 ]
				,submit:function(v,h){
					var cw = h[0].contentWindow;
					if(v=="保存"){
						var status = cw.save();
						if(status==true){
							$("#datagrid").datagrid("reload");
							return true;
						}
					}else if(v=="关闭"){
						return true;
					}
			}});
		}else{
			top.bsWindow(contextPath+"/supervise/lawList/law_content.jsp?id="+obj[0].id,"修改",{width:"600",height:"450",buttons:
				[
			 	 {name:"关闭",classStyle:"btn-alert-gray"}
				 ]
				,submit:function(v,h){
					var cw = h[0].contentWindow;
					if(v=="保存"){
						var status = cw.save();
						if(status==true){
							$("#datagrid").datagrid("reload");
							return true;
						}
					}else if(v=="关闭"){
						return true;
					}
			}});
		}
		

	}
	/**
	 * 修改
	 * @param id
	 */
	function edit(id){
		top.bsWindow(contextPath+"/supervise/lawList/law_edit.jsp?id="+id ,"修改",{width:"600",height:"450",buttons:
			[
			 {name:"保存",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="保存"){
					var status = cw.save();
					if(status==true){
						$("#datagrid").datagrid("reload");
						return true;
					}
				}else if(v=="关闭"){
					return true;
				}
		}});

	}

	 /** 审核
	  * 0 改 1
	 *      */
	function  auditing(){
		var obj = $('#datagrid').datagrid("getSelections");
		if (obj[0].examine==1){
			alert("您已审核该数据！")
		}else{
			if (window.confirm("是否确认审核")) {
				console.log(obj[0].id);
				var json = tools.requestJsonRs("/lawInfoController/examine.action",{id:obj[0].id});
				$('#datagrid').datagrid("reload");
		}
		}
		
	}
		
	
	 /** 取消审核
	  * 1改0
	 *      */
	function  RemoveAuditing(){
		var obj = $('#datagrid').datagrid("getSelections");
		if (obj[0].examine==0){
			alert("该数据已在取消审核状态！")
		}else{
			if (window.confirm("是否取消审核")) {
				console.log(obj[0].id);
				var json = tools.requestJsonRs("/lawInfoController/examine.action",{id:obj[0].id});
				$('#datagrid').datagrid("reload");
		}
		}

	}
	
	 /** 法律下载
	 *      */
	function  legalDownloads(){
		var obj = $('#datagrid').datagrid("getSelections");
			if (window.confirm("是否下载该法律")) {
				console.log(obj[0].id);
				var json = tools.requestJsonRs("/attachmentController/downFile.action",{id:obj[0].id});
				$('#datagrid').datagrid("reload");
		}
	}	
	
	
	/* * 模板下载
	 *      */
	function templateDownload(){
		window.location.href='/supervise/lawList/附件1-法律法规信息模板.xls';
		
//		var url = "<%=contextPath%>/orgImportExport/exportDept.action";
//		window.location.href = url;
		
	}
	
	/* * 导入
	 *    */  
	function importLaw(){
		var obj = $('#datagrid').datagrid("getSelections");
		var length = Object.getOwnPropertyNames(obj).length;
		if (length == 1){
			alert("请选择一条数据！");
		}else{
			top.bsWindow(contextPath+"/supervise/lawList/law_input.jsp?id="+obj[0].id ,"法律导入 --测试填报法律第二部",{width:"500",height:"150",buttons:
				[
				 //{name:"保存",classStyle:"btn-alert-blue"},
			 	 {name:"关闭",classStyle:"btn-alert-gray"}
				 ]
				,submit:function(v,h){
					var cw = h[0].contentWindow;
					if(v=="保存"){
						var status = cw.save();
						if(status==true){
							$("#datagrid").datagrid("reload");
							return true;
						}
					}else if(v=="关闭"){
						return true;
					}
			}});
		}
	}
	
	
	/* * 停用
	 *      */
	function blockUp(){
		
		top.bsWindow(contextPath+"" ,"停用",{width:"550",height:"350",buttons:
			[
			 {name:"保存",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="保存"){
					var status = cw.save();
					if(status==true){
						$("#datagrid").datagrid("reload");
						return true;
					}
				}else if(v=="关闭"){
					return true;
				}
		}});

	}
