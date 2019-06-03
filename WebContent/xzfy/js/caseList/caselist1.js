//字典表
var DICT_URL = "/xzfy/common/getDict.action";
//列表地址
var LIST_URL = "/xzfy/caseQuery/getCaseListByType.action"; 
var params;
var listdata = {
		isShowColumn: [
			{codeNo:'nature',codeName:'机关性质'},
			{codeNo:'administrativeDivision',codeName:'行政区划'},
			{codeNo:'deptLevel',codeName:'所属层级'},
			{codeNo:'departmentCode',codeName:'统一社会信用代码'},
			{codeNo:'representative',codeName:'法定代表人'},
			{codeNo:'personNo',codeName:'监督人员数量'},
		]
	}
var i;
if(listdata.isShowColumn.length%7==0){
	i=listdata.isShowColumn.length/7;
}else {i=Math.ceil(listdata.isShowColumn.length/7)}

var widthnum = i*190+10;

var width = widthnum+'px';

$(".panList").css("width",width);

$(".isshow").on("click",function(){
	$(".panList").show();
	$("body").append('<div id="panListBack" class=""></div>');

});

$("body").delegate("#panListBack","click",function(){
	$(".panList").hide();
	$("#panListBack").remove();
	});

/** 初始化展示可选列 */
var temp = ['nature','administrativeDivision','deptLevel','departmentCode','representative','personNo'];

function detail(code,th){
	if($(th).children('i').hasClass("fa-check")){
		$(th).children('i').removeClass("fa-check");
		$('#datagrid').datagrid('hideColumn', code);
		for(var i=0;i<temp.length;i++){
			if(temp[i] == code){
				temp.splice(i,1);
				break;
			}
		}
	}else{
		$(th).children('i').addClass("fa-check");
		$('#datagrid').datagrid('showColumn', code);
		temp.push(code);
	}
}

var tpl=[
	'{@each isShowColumn as it}',
	'<li onclick="detail(\'${it.codeNo}\',this)" id=\'${it.codeNo}\' ><i class="fa"></i>${it.codeName}</li>',
	'{@/each}'
   ].join('\n');



/**
 * 默认加载方法
 * @returns
 */
function doInit(){
	//机关性质
	natureInit();
	//所属地区
	administrativeDivisionInit();
	//机关层级
	deptLevelInit();

	//可选列
	$(".panList").append(juicer(tpl,listdata));
}

//重置
function refresh(){
	$("#form1").form('clear');
}
//部门地区
function administrativeDivisionInit(){
	$('#administrativeDivision').combobox({
		prompt:'请选择',
		mode:'remote',
		url:contextPath + '/adminDivisionManageCtrl/getAreaSearch.action',
		valueField:'ID',
		textField:'NAME',
		multiple:true,
		method:'post',
		panelHeight:'100px',
		labelPosition: 'top'
	});
	
}	
//加载代码表的机关层级
function deptLevelInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "DEPT_LEVEL"});
	var page = "";
	for (var i=0 , j=1; i<=json.rtData.length-1;i++,j++){
		//每五个换一行
//		if(j && j % 5 == 0){
//			page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="subLevel" id="subLevel'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
//        			+ 'value="'+json.rtData[i].codeNo+'" labelWidth="100px" label="'+json.rtData[i].codeName+'  "/><br>';
//		    var pageDoc = $('#subLevel').html(page);
//		    $.parser.parse(pageDoc);
//		}else{
	        page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="deptLevel" id="deptLevel'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
	            	+ 'value="'+json.rtData[i].codeNo+'" labelWidth="100px" label="'+json.rtData[i].codeName+'  "/>\n';
	        var pageDoc = $('#deptLevel').html(page);
	        $.parser.parse(pageDoc);
//		}
	}
}
//加载代码表的机关性质
function natureInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "DEPT_NATURE"});
	var page = "";
	for (var i=0 , j=1; i<=json.rtData.length-1;i++,j++){
		//每五个换一行
//		if(j && j % 5 == 0){
//			page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="nature" id="nature'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
//        			+ 'value="'+json.rtData[i].codeNo+'" labelWidth="150px" label="'+json.rtData[i].codeName+'  "/><br>';
//		    var pageDoc = $('#nature').html(page);
//		    $.parser.parse(pageDoc);
//		}else{
	        page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="nature" id="nature'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
	            	+ 'value="'+json.rtData[i].codeNo+'" labelWidth="100px" label="'+json.rtData[i].codeName+'"/>\n';
	        var pageDoc = $('#nature').html(page);
	        $.parser.parse(pageDoc);
//		}
	}
}
//通用的combobox处理方法
function ComboboxCommonProcess(obj){
	var values = $(obj).combobox("getValues");
	var getData = $(obj).combobox("getData");
	var valuesT = [];
	
	for(var i=0;i<values.length;i++){
		for(var ii=0;ii<getData.length;ii++){
			if(values[i]==getData[ii].id){
				valuesT.push(values[i]);
				break;
			}
		}
	}
	$(obj).combobox("setValues",valuesT);
}
/**
 * 进入综合查询界面
 * @returns
 */
var dataParams = "";
function doFilingSave(){
	if($('#form1').form('enableValidation').form('validate')){
	dataParams = tools.formToJson($("#form1"));
	dataParams.nature = "";
	dataParams.natureName = "";
	dataParams.deptLevel = "";
	dataParams.deptLevelName = "";
	dataParams.personNo = "";
	dataParams.personNoName = "";
	dataParams.administrativeDivision = $("#administrativeDivision").val();
	dataParams.administrativeDivisionName = $("#administrativeDivision").combobox('getText');
	if($("#form1 input[name='nature']:checked").length >0){
			$("#form1 input[name='nature']:checked").each(function(){
				dataParams.nature += this.value + ',';
				dataParams.natureName += this.labels[0].innerHTML + '，';
			})
	}
	if($("#form1 input[name='deptLevel']:checked").length >0){
			$("#form1 input[name='deptLevel']:checked").each(function(){
				dataParams.deptLevel += this.value + ',';
				dataParams.deptLevelName += $.trim(this.labels[0].innerHTML) + '，';
			})
	}
	if($("#form1 input[name='personNo']:checked").length >0){
		$("#form1 input[name='personNo']:checked").each(function(){
			dataParams.personNo += this.value + ',';
			dataParams.personNoName += $.trim(this.labels[0].innerHTML) + '，';
		})
	}
	//校验
//	if(!/^\d+$/.test(powerNoStart)){
//		$.MsgBox.Alert_auto("请输入数字");
//		return false;
//	}
	//初始化表格
	listDatagrid();
	//可选列
//	initRows();
	//已选查询条件标签
	isTerm();
	document.getElementById("divSearch").style.display="none";
	document.getElementById("divIndex").style.display="block";
	
//	location.href = '/supervise/Department/departmentSearch/department_index.jsp?'+$.param(params);
}
}
//返回
function back(){
	$("#condition").empty();
	document.getElementById("divSearch").style.display="block";
	document.getElementById("divIndex").style.display="none";
}
//初始化表格
function listDatagrid(){
	datagrid = $('#datagrid').datagrid( {
        url : contextPath + '/SuperviseController/generalListByPageRoles.action',
        queryParams : dataParams,
        pagination : true,
        pageSize : 20,
        pageList : [ 10, 20, 50, 100 ],
        singleSelect : true,
        striped: true,
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar : '#toolbar',// 工具条对象
        border : false,
        rownumbers : false,
        fit : true,
        idField : 'id',// 主键列
        fitColumns : true,// 列是否进行自动宽度适应
        onLoadSuccess: function(data, rowData) {
//        	debugger;
            if(temp.length>0){
                for(var i = 0; i < temp.length; i++){
                    $('#datagrid').datagrid('showColumn', temp[i]);
                    if(!$('#'+temp[i]).children('i').hasClass("fa-check")){
                        $('#'+temp[i]).children('i').addClass("fa-check");
                    }
                }
            }
        },
        frozenColumns: [ [
				{field:'ID',title:'序号',align:'center',
				    formatter:function(value,rowData,rowIndex){
				        return rowIndex+1;
				    }
				},
				{ field : 'name', title : '机关全称', width : '25%',align:'left' , halign: 'center',
				    formatter:function(value,rowData,rowIndex){
				    	if(value == null){
				    		return "";
				    	}else{
				    		var optsStr = "<a href=\"#\" title = " + value + " onclick=\"look('" + rowData.id + "')\">" + value + "</a>";
					        return optsStr;
				    	}
				    }
				},
				       ] ],
        columns : [ [
     			{ field : 'nature', title : '机关性质', width : '10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
     			{ field : 'administrativeDivision', title : '行政区划', width :'20%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'deptLevel', title : '所属层级', width : '10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
                { field : 'departmentCode', title : '统一社会信用代码', width : '15%',align:'center' , halign: 'center' ,hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(rowData.code == null){
						return "";
					}else{
						var optsStr = "<span title=" + rowData.code + ">" + rowData.code + "</span>";
	                    return optsStr;
					}
	            },}, 
				{ field : 'representative', title : '法定代表人', width :'10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'personNo', title : '监督人员数量', width :'8%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},

	            ] ],
        singleSelect : false,
        selectOnCheck : true,
        checkOnSelect : true
//        onLoadSuccess : function(data, rowData) {
//            if (data) {
//                $.each(data.rows, function(index, item) {
//                    if (item.checked) {
//                        $('#datagrid').datagrid('checkRow',
//                                index);
//                    }
//                });
//            }
//        }
    });
}
/*
 * 查看
 */
function look(id) {
	top.bsWindow(contextPath + "/supervise/supervise/supervise_search_look.jsp?id="+id, "查看",
			{
				width : "800",
				height : "370",
				buttons : [ {
					name : "关闭",
					classStyle : "btn-alert-gray"
				} ],
				submit : function(v, h) {
					if (v == "关闭") {
						return true;
					}
				}
			});

}
//初始化已选标签
function isTerm(){
	if(dataParams.name != null && dataParams.name != ""){
		var $table= $("#condition");
		var vTr= "<span class='tagbox-label' title=机关全称："+dataParams.name+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id='nameTag'>&nbsp;机关全称<a href='javascript:;' onclick=\"thisRemove('name')\" class='tagbox-remove'></a></span>";
		$("#condition").append(vTr);
	}
	if(dataParams.nature != null && dataParams.nature != ""){
		var $table= $("#condition");
		var str = dataParams.natureName;
		var natureStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=机关性质："+natureStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"natureTag\">&nbsp;机关性质<a href=\"javascript:;\" onclick=\"thisRemove('nature')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.administrativeDivision != null && dataParams.administrativeDivision != ""){
		var $table= $("#condition");
		var str = dataParams.administrativeDivisionName;
		var administrativeDivisionStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=行政区划："+administrativeDivisionStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"administrativeDivisionTag\">&nbsp;行政区划<a href=\"javascript:;\" onclick=\"thisRemove('administrativeDivision')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.deptLevel != null && dataParams.deptLevel != ""){
		var $table= $("#condition");
		var str = dataParams.deptLevelName;
		var deptLevelStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=所属层级："+deptLevelStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"deptLevelTag\">&nbsp;所属层级<a href=\"javascript:;\" onclick=\"thisRemove('deptLevel')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.departmentCode != null && dataParams.departmentCode != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" title=统一社会信用代码："+dataParams.departmentCode+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"departmentCodeTag\">&nbsp;统一社会信用代码<a href=\"javascript:;\" onclick=\"thisRemove('departmentCode')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.representative != null && dataParams.representative != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" title=法定代表人："+dataParams.representative+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"representativeTag\">&nbsp;法定代表人<a href=\"javascript:;\" onclick=\"thisRemove('representative')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.personNo != null && dataParams.personNo != ""){
		var $table= $("#condition");
		var str = dataParams.personNoName;
		var personStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=监督人员数量："+personStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"personNoTag\">&nbsp;监督人员数量<a href=\"javascript:;\" onclick=\"thisRemove('personNo')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
}
//标签删除
function thisRemove(thisTag){
	if(dataParams == ""){
		var dataPar = tools.formToJson($("#form1"));
		dataParams = dataPar
	}else{
		dataParams = dataParams;
	}
	
	//删除标签
	$("#"+thisTag+"Tag").hide();
	dataParams[thisTag] = "";
	$('#datagrid').datagrid("reload",dataParams);
	$('#datagrid').datagrid("clearSelections");
}
//导出
function exportDept(){
	var obj = temp.join(",");
	if (window.confirm("确定导出所有数据？")) {
		var json = tools.requestJsonRs("/subjectSearchController/exportDept.action?term="+obj);
		if(json.rtData < 1001){
			location.href = '/subjectSearchController/exportDept.action?isTrue=1&term='+obj;
		}else{
			alert("导出数据过大，请精确查询后再操作（导出数据限制：1000）");
		}
	}
}


