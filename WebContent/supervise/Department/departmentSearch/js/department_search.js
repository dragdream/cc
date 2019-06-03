var datagrid;
var params;
var natureJsons = [];
var levelJsons = [];
var personJsons = [{codeNo:'1',codeName:'小于10'},
                   {codeNo:'2',codeName:'10-50'},
                   {codeNo:'3',codeName:'51-100'},
                   {codeNo:'4',codeName:'101-200'},
                   {codeNo:'5',codeName:'大于200'}];
var orgJsons = [{codeNo:'1',codeName:'无'},
                {codeNo:'2',codeName:'1-3'},
                {codeNo:'3',codeName:'4-7'},
                {codeNo:'4',codeName:'大于7'}];
var powerJsons = [{codeNo:'1',codeName:'小于100'},
                  {codeNo:'2',codeName:'101-300'},
                  {codeNo:'3',codeName:'301-500'},
                  {codeNo:'4',codeName:'501-1000'},
                  {codeNo:'5',codeName:'大于1000'}];
var subjectJsons = [{codeNo:'1',codeName:'无'},
                    {codeNo:'2',codeName:'单'},
                    {codeNo:'3',codeName:'多'}];
var isManubriumStrJsons = [{codeNo:'1',codeName:'是'},
                           {codeNo:'0',codeName:'否'}];
var listdata = {
		isShowColumn: [
			{codeNo:'deptLevel',codeName:'部门层级'},
			{codeNo:'administrativeDivision',codeName:'部门地区'},
			{codeNo:'orgSys',codeName:'所属领域'},
			{codeNo:'isManubriumStr',codeName:'是否垂管'},
			{codeNo:'personNo',codeName:'执法人员数量'},
			{codeNo:'orgNo',codeName:'委托组织个数'},
			{codeNo:'powerNo',codeName:'职权个数'},
			{codeNo:'subNo',codeName:'执法主体个数'},
			{codeNo:'postCode',codeName:'邮编'},
			{codeNo:'address',codeName:'地址'},
			{codeNo:'departmentCode',codeName:'统一社会信用代码'},
			{codeNo:'nature',codeName:'部门性质'},
			{codeNo:'representative',codeName:'法定代表人'},
			{codeNo:'userName',codeName:'账号'},
			{codeNo:'isExamine',codeName:'审核状态'},
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
var temp = ['deptLevel','administrativeDivision','orgSys','isManubriumStr'];

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
var paramses = "";
function doInit(){
	//部门性质
	natureInit();
	//执法系统
	orgSysInit();
	//部门地区
	administrativeDivisionInit();
	//部门层级
	deptLevelInit();
	//可选列
	$(".panList").append(juicer(tpl,listdata));
}

/**
 * 进入综合查询界面
 * @returns
 */
var dataParams = "";
function doFilingSave(){
	dataParams = {	  name:$("#name").val(),
				  orgSys:$("#orgSys").val(),
				  orgSysName:$("#orgSys").combobox('getText'),
				  administrativeDivision:$("#administrativeDivision").val(),
				  administrativeDivisionName:$("#administrativeDivision").combobox('getText'),
				  subName:$("#subName").val(),
				  orgName:$("#orgName").val(),
				  powerName:$("#powerName").val(),
				  nature:"",
				  natureName:"",
				  deptLevel:"",
				  deptLevelName:"",
				  personNo:"",
				  personNoName:"",
				  orgNo:"",
				  orgNoName:"",
				  powerNo:"",
				  powerNoName:"",
				  subNo:"",
				  subNoName:"",
				  isManubriumStr:"",
				  isManubriumStrName:""};
		if($("#form1 input[name='personNo']:checked").length >0){
			$("#form1 input[name='personNo']:checked").each(function(){
				dataParams.personNo += this.value + ',';
				for(var i=0;i<personJsons.length;i++){
	            	if(this.value==personJsons[i].codeNo){
	            		dataParams.personNoName += $.trim(personJsons[i].codeName) + '，';
	            		break;
	            	}
	            }
			})
		}
		if($("#form1 input[name='orgNo']:checked").length >0){
			$("#form1 input[name='orgNo']:checked").each(function(){
				dataParams.orgNo += this.value + ',';
				for(var i=0;i<orgJsons.length;i++){
	            	if(this.value==orgJsons[i].codeNo){
	            		dataParams.orgNoName += $.trim(orgJsons[i].codeName) + '，';
	            		break;
	            	}
	            }
			})
		}
		if($("#form1 input[name='powerNo']:checked").length >0){
			$("#form1 input[name='powerNo']:checked").each(function(){
				dataParams.powerNo += this.value + ',';
				for(var i=0;i<powerJsons.length;i++){
	            	if(this.value==powerJsons[i].codeNo){
	            		dataParams.powerNoName += $.trim(powerJsons[i].codeName) + '，';
	            		break;
	            	}
	            }
			})
		}
		if($("#form1 input[name='nature']:checked").length >0){
			$("#form1 input[name='nature']:checked").each(function(){
				dataParams.nature += this.value + ',';
				for(var i=0;i<natureJsons.length;i++){
	            	if(this.value==natureJsons[i].codeNo){
	            		dataParams.natureName += $.trim(natureJsons[i].codeName) + '，';
	            		break;
	            	}
	            }
			})
		}
		if($("#form1 input[name='deptLevel']:checked").length >0){
			$("#form1 input[name='deptLevel']:checked").each(function(){
				dataParams.deptLevel += this.value + ',';
				for(var i=0;i<levelJsons.length;i++){
	            	if(this.value==levelJsons[i].codeNo){
	            		dataParams.deptLevelName += $.trim(levelJsons[i].codeName) + '，';
	            		break;
	            	}
	            }
			})
		}
		if($("#form1 input[name='subNo']:checked").length >0){
			$("#form1 input[name='subNo']:checked").each(function(){
				dataParams.subNo += this.value + ',';
				for(var i=0;i<subjectJsons.length;i++){
	            	if(this.value==subjectJsons[i].codeNo){
	            		dataParams.subNoName += $.trim(subjectJsons[i].codeName) + '，';
	            		break;
	            	}
	            }
			})
		}
		if($("#form1 input[name='isManubriumStr']:checked").length >0){
			$("#form1 input[name='isManubriumStr']:checked").each(function(){
				dataParams.isManubriumStr += this.value + ',';
				for(var i=0;i<isManubriumStrJsons.length;i++){
	            	if(this.value==isManubriumStrJsons[i].codeNo){
	            		dataParams.isManubriumStrName += $.trim(isManubriumStrJsons[i].codeName) + '，';
	            		break;
	            	}
	            }
			})
		}
//	location.href = '/supervise/Department/departmentSearch/department_index.jsp?'+$.param(params);
	//初始化表格
	listDatagrid();
	//已选标签
	isTerm();
	document.getElementById("divSearch").style.display="none";
	document.getElementById("divIndex").style.display="block";
}
//重置
function refresh(){
	$("#form1").form('clear');
}
//执法系统
function orgSysInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "LAW_ENFORCEMENT_FIELD"});
	if(json.rtState) {
        $('#orgSys').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
    		panelHeight:'150px',
        });
    }
}
//加载代码表的部门性质
function natureInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "DEPT_NATURE"});
	var page = "";
	var natureJson;
	for (var i=0 , j=1; i<=json.rtData.length-1;i++,j++){
	        page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="nature" id="nature'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
	            	+ 'value="'+json.rtData[i].codeNo+'" labelWidth="100px" label="'+json.rtData[i].codeName+'"/>\n';
	        natureJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
	        natureJsons.push(natureJson);
	}
	var pageDoc = $('#nature').html(page);
    $.parser.parse(pageDoc);
}
//加载代码表的部门层级
function deptLevelInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "DEPT_LEVEL"});
	var page = "";
	var levelJson;
	for (var i=0 , j=1; i<=json.rtData.length-1;i++,j++){
	        page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="deptLevel" id="deptLevel'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
	            	+ 'value="'+json.rtData[i].codeNo+'" labelWidth="100px" label="'+json.rtData[i].codeName+'  "/>\n';
	        levelJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
	        levelJsons.push(levelJson);
	}
	var pageDoc = $('#deptLevel').html(page);
    $.parser.parse(pageDoc);
}
//部门地区和部门层级
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
		//label: 'Language:',
		labelPosition: 'top'
	});
	
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "DEPT_LEVEL"});
	var jsonLevel = tools.requestJsonRs("/adminDivisionManageCtrl/getAreaSearchLevel.action");
	var paramses = [{codeNo:jsonLevel.rtData.id,codeName:jsonLevel.rtData.name}];
	for (var i=0 ; i<=json.rtData.length-1;i++){
		if(json.rtData[i].codeNo > jsonLevel.rtData.id){
			var add = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName}
			paramses.push(add);
		}
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
//返回
function back(){
	$("#condition").empty();
	document.getElementById("divSearch").style.display="block";
	document.getElementById("divIndex").style.display="none";
//	location.href = '/supervise/Department/departmentSearch/department_search.jsp';
}
//标签删除
function thisRemove(thisTag,thisType,thisVal){
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
	
	//删除搜索条件
	if(thisType == 'textbox'){
        $("#"+thisTag).textbox("setValue","");
	}else{
		var val = thisVal.split(",");
        for(var i=0 ; i < val.length; i++){
            $("#"+thisTag+val[i]).checkbox({checked: false});
        }
	}
}

//初始化已选标签
function isTerm(){
	if(dataParams.name != null && dataParams.name != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" title=部门名称："+dataParams.name+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"nameTag\">&nbsp;部门名称" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('name','textbox')\" class=\"tagbox-remove\"></a></span>";
		$("#condition").append(vTr);
	}
	if(dataParams.nature != null && dataParams.nature != ""){
		var $table= $("#condition");
		var val = dataParams.nature;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.natureName;
		var natureStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=部门性质："+natureStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"natureTag\">&nbsp;部门性质" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('nature','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.deptLevel != null && dataParams.deptLevel != ""){
		var $table= $("#condition");
		var val = dataParams.deptLevel;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.deptLevelName;
		var deptLevelStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=部门层级："+deptLevelStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"deptLevelTag\">&nbsp;部门层级" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('deptLevel','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.administrativeDivision != null && dataParams.administrativeDivision != ""){
		var $table= $("#condition");
		var str = dataParams.administrativeDivisionName;
		var administrativeDivisionStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=部门地区："+administrativeDivisionStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"administrativeDivisionTag\">&nbsp;部门地区" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('administrativeDivision','textbox')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.orgSys != null && dataParams.orgSys != ""){
		var $table= $("#condition");
		var str = dataParams.orgSysName;
		var orgSysStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=所属领域："+orgSysStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"orgSysTag\">&nbsp;所属领域" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('orgSys','textbox')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.isManubriumStr != null && dataParams.isManubriumStr != ""){
		var $table= $("#condition");
		var val = dataParams.isManubriumStr;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.isManubriumStrName;
		var isManubriumStrNameStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=是否垂管："+isManubriumStrNameStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"isManubriumStrTag\">&nbsp;是否垂管" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('isManubriumStr','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.subName != null && dataParams.subName != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" title=执法主体名称："+dataParams.subName+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"subNameTag\">&nbsp;执法主体名称" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('subName','textbox')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.orgName != null && dataParams.orgName != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" title=委托组织名称："+dataParams.orgName+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"orgNameTag\">&nbsp;委托组织名称" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('orgName','textbox')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.powerName != null && dataParams.powerName != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" title=职权名称："+dataParams.powerName+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"powerNameTag\">&nbsp;职权名称" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('powerName','textbox')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.personNo != null && dataParams.personNo != ""){
		var $table= $("#condition");
		var val = dataParams.personNo;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.personNoName;
		var personNoStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=执法人员数量："+personNoStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"personNoTag\">&nbsp;执法人员数量" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('personNo','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.orgNo != null && dataParams.orgNo != ""){
		var $table= $("#condition");
		var val = dataParams.orgNo;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.orgNoName;
		var orgNoStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=委托组织个数："+orgNoStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"orgNoTag\">&nbsp;委托组织个数" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('orgNo','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.powerNo != null && dataParams.powerNo != ""){
		var $table= $("#condition");
		var val = dataParams.powerNo;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.powerNoName;
		var powerNoStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=职权个数："+powerNoStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"powerNoTag\">&nbsp;职权个数" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('powerNo','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.subNo != null && dataParams.subNo != ""){
		var $table= $("#condition");
		var val = dataParams.subNo;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.subNoName;
		var subNoStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=执法主体个数："+subNoStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"subNoTag\">&nbsp;执法主体个数" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('subNo','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
}
//导出
function exportDept(){
	var obj = temp.join(",");
	if (window.confirm("确定导出所有数据？")) {
		var json = tools.requestJsonRs("/departmentSearchController/exportDept.action?term="+obj);
		if(json.rtData < 1001){
			location.href = '/departmentSearchController/exportDept.action?isTrue=1&term='+obj;
		}else{
			alert("导出数据过大，请精确查询后再操作（导出数据限制：1000）");
		}
	}
}
/*
 * 查看
 */
function look(id) {
	top.bsWindow(contextPath + "/supervise/Department/department_search_look.jsp?id="+id, "查看",
			{
				width : "850",
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
function listDatagrid(){
	datagrid = $('#datagrid') .datagrid( {
		url : contextPath + '/departmentSearchController/generalListByPageRoles.action',
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
		frozenColumns: [ [
//				{ field : 'id', title : '', checkbox : true},
				{field:'ID',title:'序号',align:'center',
				    formatter:function(value,rowData,rowIndex){
				        return rowIndex+1;
				    }
				},
				{ field : 'name', title : '部门名称', width : 250,align:'left' , halign: 'center',
					formatter:function(value,rowData,rowIndex){
						var optsStr = "<a href=\"#\" title=" + value + " onclick=\"look('" + rowData.id + "')\">" + value + "</a>";
						return optsStr;
					} 
				},
		               ] ],
		columns : [ [
				
				{ field : 'deptLevel', title : '部门层级', width : '17%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'administrativeDivision', title : '部门地区', width : '26%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'orgSys', title : '所属领域', width :  '20%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'isManubriumStr', title : '是否垂管', width :  '8%',align:'center' , halign: 'center',hidden:true , formatter : 
					function(data, rowData) {
					if (rowData.isManubriumStr == 1) {
						return "是";
					} else {
						return "否";
					}
				}},
				{ field : 'personNo', title : '执法人员数量', width : '10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'orgNo', title : '委托组织个数', width : '10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'powerNo', title : '职权个数', width : '10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'subNo', title : '执法主体个数', width : '10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'postCode', title : '邮编', width : '10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'address', title : '地址', width : '10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'departmentCode', title : '统一社会信用代码', width : '10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'nature', title : '部门性质', width : '10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'representative', title : '法定代表人', width : '10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'userName', title : '账号', width : '10%',align:'left' , halign: 'center' ,hidden:true, formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'isExamine', title : '审核状态', width : '8%',align:'center' , halign: 'center',hidden:true , formatter : 
					function(data, rowData) {
						if (rowData.isExamine == 1) {
							return "已审核";
						} else {
							return "未审核";
						}
					}
				},
				] ],
		singleSelect : true,
		selectOnCheck : true,
		checkOnSelect : true,
		onLoadSuccess : function(data) {
            if(temp.length>0){
                for(var i = 0; i < temp.length; i++){
                    $('#datagrid').datagrid('showColumn', temp[i]);
                    if(!$('#'+temp[i]).children('i').hasClass("fa-check")){
                        $('#'+temp[i]).children('i').addClass("fa-check");
                    }
                }
            }
        }
	});
	
}