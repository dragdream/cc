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
var isUserStrJsons = [{codeNo:'1',codeName:'是'},
                      {codeNo:'0',codeName:'否'}];
var listdata = {
		isShowColumn: [
			{codeNo:'area',codeName:'所属地区'},
			{codeNo:'orgSys',codeName:'所属领域'},
			{codeNo:'subLevel',codeName:'主体层级'},
//			{codeNo:'subjectPower',codeName:'职权类别'},
			{codeNo:'code',codeName:'统一社会信用代码'},
			{codeNo:'nature',codeName:'主体性质'},
			{codeNo:'userName',codeName:'账号'},
			{codeNo:'examine',codeName:'审核状态'},
			{codeNo:'personNo',codeName:'执法人员数量'},
			{codeNo:'orgNo',codeName:'委托组织数量'},
			{codeNo:'powerNo',codeName:'职权总数'},
			{codeNo:'punishNo',codeName:'行政处罚职权数量'},
			{codeNo:'inspectNo',codeName:'行政检查职权数量'},
			{codeNo:'permitNo',codeName:'行政许可职权数量'},
			{codeNo:'forceNo',codeName:'行政强制职权数量'},
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
var temp = ['area','orgSys','subLevel','code','nature'];

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
	//所属领域
	orgSysInit();
	//所属地区
	administrativeDivisionInit();
	//职权类型
//	subjectPowerInit();
	//主体层级
	subLevelInit();
	//主体性质
	natureInit();
	//可选列
	$(".panList").append(juicer(tpl,listdata));
}

//重置
function refresh(){
	$("#form1").form('clear');
}
//所属领域
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
//部门地区
function administrativeDivisionInit(){
	$('#area').combobox({
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
//加载代码表的职权类别
//function subjectPowerInit(){
//	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "POWER_TYPE"});
//	var page = "";
//	for (var i=0 , j=1; i<=json.rtData.length-1;i++,j++){
//		//每五个换一行
////		if(j && j % 5 == 0){
////			page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="subjectPower" id="subjectPower'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
////        			+ 'value="'+json.rtData[i].codeNo+'" labelWidth="100px" label="'+json.rtData[i].codeName+'  "/><br>';
////		    var pageDoc = $('#subjectPower').html(page);
////		    $.parser.parse(pageDoc);
////		}else{
//	        page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="subjectPower" id="subjectPower'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
//	            	+ 'value="'+json.rtData[i].codeNo+'" labelWidth="100px" label="'+json.rtData[i].codeName+'  "/>';
//	        var pageDoc = $('#subjectPower').html(page);
//	        $.parser.parse(pageDoc);
////		}
//	}
//}
//加载代码表的主体层级
function subLevelInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "DEPT_LEVEL"});
	var page = "";
	var levelJson;
	for (var i=0 , j=1; i<=json.rtData.length-1;i++,j++){
		//每五个换一行
//		if(j && j % 5 == 0){
//			page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="subLevel" id="subLevel'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
//        			+ 'value="'+json.rtData[i].codeNo+'" labelWidth="100px" label="'+json.rtData[i].codeName+'  "/><br>';
//		    var pageDoc = $('#subLevel').html(page);
//		    $.parser.parse(pageDoc);
//		}else{
	        page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="subLevel" id="subLevel'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
	            	+ 'value="'+json.rtData[i].codeNo+'" labelWidth="100px" label="'+json.rtData[i].codeName+'  "/>';
	        var pageDoc = $('#subLevel').html(page);
	        $.parser.parse(pageDoc);
	        levelJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
	        levelJsons.push(levelJson);
//		}
	}
}
//加载代码表的主体性质
function natureInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "SYSTEM_SUBJECT_NATURE"});
	var page = "";
	var natureJson;
	for (var i=0 , j=1; i<=json.rtData.length-1;i++,j++){
		//每五个换一行
//		if(j && j % 5 == 0){
//			page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="nature" id="nature'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
//        			+ 'value="'+json.rtData[i].codeNo+'" labelWidth="150px" label="'+json.rtData[i].codeName+'  "/><br>';
//		    var pageDoc = $('#nature').html(page);
//		    $.parser.parse(pageDoc);
//		}else{
	        page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="nature" id="nature'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
	            	+ 'value="'+json.rtData[i].codeNo+'" labelWidth="150px" label="'+json.rtData[i].codeName+'"/>';
	        var pageDoc = $('#nature').html(page);
	        $.parser.parse(pageDoc);
	        natureJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
	        natureJsons.push(natureJson);
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
	dataParams.subLevel = "";
	dataParams.subLevelName = "";
//	dataParams.subjectPower = "";
//	dataParams.subjectPowerName = "";
	dataParams.personNo = "";
	dataParams.personNoName = "";
	dataParams.orgNo = "";
	dataParams.orgNoName = "";
	dataParams.isUserStr = "";
	dataParams.isUserStrName = "";
	dataParams.orgSys = $("#orgSys").val();
	dataParams.orgSysName = $("#orgSys").combobox('getText');
	dataParams.area = $("#area").val();
	dataParams.areaName = $("#area").combobox('getText');
//	dataParams.powerNoStart =
//	dataParams.powerNoEnd
//	dataParams.punishNoStart
//	dataParams.punishNoEnd
//	dataParams.inspectNoStart
//	dataParams.inspectNoEnd
//	dataParams.permitNoStart
//	dataParams.permitNoEnd
//	dataParams.forceNoStart
//	dataParams.forceNoEnd
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
	if($("#form1 input[name='subLevel']:checked").length >0){
			$("#form1 input[name='subLevel']:checked").each(function(){
				dataParams.subLevel += this.value + ',';
				for(var i=0;i<levelJsons.length;i++){
	            	if(this.value==levelJsons[i].codeNo){
	            		dataParams.subLevelName += $.trim(levelJsons[i].codeName) + '，';
	            		break;
	            	}
	            }
			})
	}
//	if($("#form1 input[name='subjectPower']:checked").length >0){
//			$("#form1 input[name='subjectPower']:checked").each(function(){
//				dataParams.subjectPower += this.value + ',';
//				dataParams.subjectPowerName += $.trim(this.labels[0].innerHTML) + '，';
//			})
//	}
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
	if($("#form1 input[name='isUserStr']:checked").length >0){
		$("#form1 input[name='isUserStr']:checked").each(function(){
			dataParams.isUserStr += this.value + ',';
			for(var i=0;i<isUserStrJsons.length;i++){
            	if(this.value==isUserStrJsons[i].codeNo){
            		dataParams.isUserStrName += $.trim(isUserStrJsons[i].codeName) + '，';
            		break;
            	}
            }
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
        url : contextPath + '/subjectSearchController/generalListByPageRoles.action',
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
				{ field : 'subName', title : '主体名称', width : 250,align:'left' , halign: 'center',
				    formatter:function(value,rowData,rowIndex){
				        var optsStr = "<a href=\"#\" title = " + value + " onclick=\"look('" + rowData.id + "')\">" + value + "</a>";
				        return optsStr;
				    } 
				},
				       ] ],
        columns : [ [
     			{ field : 'area', title : '所属地区', width :'20%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'orgSys', title : '所属领域', width : '10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'subLevel', title : '主体层级', width : '15%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
                { field : 'code', title : '统一社会信用代码', width : '15%',align:'center' , halign: 'center' ,hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(rowData.code == null){
						return "";
					}else{
						var optsStr = "<span title=" + rowData.code + ">" + rowData.code + "</span>";
	                    return optsStr;
					}
	            },},
//				{ field : 'subjectPower', title : '职权类别', width :'22%',align:'center' , halign: 'center',hidden:true , formatter: 
//					function(value,rowData,rowIndex){
//					if(value == null){
//						return "";
//					}else{
//						var optsStr = "<span title=" + value + ">" + value + "</span>";
//	                    return optsStr;
//					}
//	            },},
				{ field : 'personNo', title : '执法人员数量', width :'10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'orgNo', title : '委托组织数量', width :'10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'powerNo', title : '职权总数', width :'10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'punishNo', title : '行政处罚职权数量', width :'10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'inspectNo', title : '行政检查职权数量', width :'10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'permitNo', title : '行政许可职权数量', width :'10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'forceNo', title : '行政强制职权数量', width :'10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
                { field : 'nature', title : '主体性质', width : '13%' ,align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'userName', title : '账号', width : '10%',align:'left' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
                { field : 'examine', title : '审核状态', width : '8%',align:'center' , halign: 'center',hidden:true, formatter : 
                    function(data, rowData) {
                        if (rowData.examine == 1) {
                            return "已审核";
                        } else {
                            return "未审核";
                        }
                    }
                }, ] ],
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
	top.bsWindow(contextPath + "/supervise/subject/subject_search_look.jsp?id="+id, "查看",
			{
				width : "760",
				height : "230",
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
	if(dataParams.subName != null && dataParams.subName != ""){
		var $table= $("#condition");
		var vTr= "<span class='tagbox-label' title=主体名称："+dataParams.subName+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id='subNameTag'>&nbsp;主体名称" +
				"<a href='javascript:;' onclick=\"thisRemove('subName','textbox')\" class='tagbox-remove'></a></span>";
		$("#condition").append(vTr);
	}
	if(dataParams.area != null && dataParams.area != ""){
		var $table= $("#condition");
		var str = dataParams.areaName;
		var areaStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=所属地区："+areaStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"areaTag\">&nbsp;所属地区" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('area','textbox')\" class=\"tagbox-remove\"></a></span>";
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
	if(dataParams.subLevel != null && dataParams.subLevel != ""){
		var $table= $("#condition");
		var val = dataParams.subLevel;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.subLevelName;
		var subLevelStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=主体层级："+subLevelStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"subLevelTag\">&nbsp;主体层级" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('subLevel','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
//	if(dataParams.subjectPower != null && dataParams.subjectPower != ""){
//		var $table= $("#condition");
//		var val = dataParams.subjectPower;
//		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
//		var str = dataParams.subjectPowerName;
//		var subjectPowerStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
//		var vTr= "<span class=\"tagbox-label\" title=职权类别："+subjectPowerStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"subjectPowerTag\">&nbsp;职权类别" +
//				"<a href=\"javascript:;\" onclick=\"thisRemove('subjectPower','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
//		$table.append(vTr);
//	}
	if(dataParams.code != null && dataParams.code != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" title=统一社会信用代码："+dataParams.code+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"codeTag\">&nbsp;统一社会信用代码" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('code','textbox')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.nature != null && dataParams.nature != ""){
		var $table= $("#condition");
		var val = dataParams.nature;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.natureName;
		var natureStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=主体性质："+natureStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"natureTag\">&nbsp;主体性质" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('nature','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.isUserStr != null && dataParams.isUserStr != ""){
		var $table= $("#condition");
		var val = dataParams.isUserStr;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.isUserStrName;
		var isUserStrStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=是否已分配账号："+isUserStrStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"isUserStrTag\">&nbsp;是否已分配账号" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('isUserStr','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
//	if(dataParams.examine != null && dataParams.examine != ""){
//		var $table= $("#condition");
//		var vTr= "<span class=\"tagbox-label\" title=审核状态："+dataParams.examine+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"examineTag\">&nbsp;审核状态" +
//				"<a href=\"javascript:;\" onclick=\"thisRemove('examine')\" class=\"tagbox-remove\"></a></span>";
//		$table.append(vTr);
//	}
	if(dataParams.personNo != null && dataParams.personNo != ""){
		var $table= $("#condition");
		var val = dataParams.personNo;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.personNoName;
		var personStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=执法人员数量："+personStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"personNoTag\">&nbsp;执法人员数量" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('personNo','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.orgNo != null && dataParams.orgNo != ""){
		var $table= $("#condition");
		var val = dataParams.orgNo;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.orgNoName;
		var orgStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=委托组织数量："+orgStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"orgNoTag\">&nbsp;委托组织数量" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('orgNo','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	
	
	if(dataParams.powerNo != null && dataParams.powerNo != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" title=职权总数："+dataParams.powerNo+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"powerNoTag\">&nbsp;职权总数" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('powerNo')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.punishNo != null && dataParams.punishNo != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" title=行政处罚职权数量："+dataParams.punishNo+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"punishNoTag\">&nbsp;行政处罚职权数量" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('punishNo')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.inspectNo != null && dataParams.inspectNo != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" title=行政检查职权数量："+dataParams.inspectNo+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"inspectNoTag\">&nbsp;行政检查职权数量" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('inspectNo')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.permitNo != null && dataParams.permitNo != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" title=行政许可职权数量："+dataParams.permitNo+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"permitNoTag\">&nbsp;行政许可职权数量" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('permitNo')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.forceNo != null && dataParams.forceNo != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" title=行政强制职权数量："+dataParams.forceNo+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"forceNoTag\">&nbsp;行政强制职权数量" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('forceNo')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.isUser != null && dataParams.isUser != ""){
		var $table= $("#condition");
		var str = dataParams.isUserName;
		var isUserStrStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=是否分配账号："+isUserStrStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"isUserStrTag\">&nbsp;是否分配账号" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('isUserStr','checkbox','"+dataParams.isGetcodeStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	
	
	if(dataParams.powerNoStart != null && dataParams.powerNoStart != "" && dataParams.powerNoEnd != null && dataParams.powerNoEnd != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"powerNoTag\" title=\"职权总数："+dataParams.powerNoStart+"-"+dataParams.powerNoEnd+"\">&nbsp;职权总数<a href=\"javascript:;\" onclick=\"thisRemoveTwo('powerNo')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}else if(dataParams.powerNoStart != null && dataParams.powerNoStart != ""&&(dataParams.powerNoEnd == null || dataParams.powerNoEnd == "")){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"powerNoStartTag\" title=\"职权总数：>="+dataParams.powerNoStart+"\">&nbsp;职权总数<a href=\"javascript:;\" onclick=\"thisRemove('powerNoStart')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}else if((dataParams.powerNoStart == null || dataParams.powerNoStart == "")&&dataParams.powerNoEnd != null && dataParams.powerNoEnd != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"powerNoEndTag\" title=\"职权总数：<="+dataParams.powerNoEnd+"\">&nbsp;职权总数<a href=\"javascript:;\" onclick=\"thisRemove('powerNoEnd')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	
	if(dataParams.punishNoStart != null && dataParams.punishNoStart != "" && dataParams.punishNoEnd != null && dataParams.punishNoEnd != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"punishNoTag\" title=\"行政处罚职权数量："+dataParams.punishNoStart+"-"+dataParams.punishNoEnd+"\">&nbsp;行政处罚职权数量<a href=\"javascript:;\" onclick=\"thisRemoveTwo('punishNo')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}else if(dataParams.punishNoStart != null && dataParams.punishNoStart != ""&&(dataParams.punishNoEnd == null || dataParams.punishNoEnd == "")){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"punishNoStartTag\" title=\"行政处罚职权数量：>="+dataParams.punishNoStart+"\">&nbsp;行政处罚职权数量<a href=\"javascript:;\" onclick=\"thisRemove('punishNoStart')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}else if((dataParams.punishNoStart == null || dataParams.punishNoStart == "")&&dataParams.punishNoEnd != null && dataParams.punishNoEnd != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"punishNoEndTag\" title=\"行政处罚职权数量：<="+dataParams.punishNoEnd+"\">&nbsp;行政处罚职权数量<a href=\"javascript:;\" onclick=\"thisRemove('punishNoEnd')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	
	if(dataParams.inspectNoStart != null && dataParams.inspectNoStart != "" && dataParams.inspectNoEnd != null && dataParams.inspectNoEnd != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"inspectNoTag\" title=\"行政检查职权数量："+dataParams.inspectNoStart+"-"+dataParams.inspectNoEnd+"\">&nbsp;行政检查职权数量<a href=\"javascript:;\" onclick=\"thisRemoveTwo('inspectNo')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}else if(dataParams.inspectNoStart != null && dataParams.inspectNoStart != ""&&(dataParams.inspectNoEnd == null || dataParams.inspectNoEnd == "")){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"inspectNoStartTag\" title=\"行政检查职权数量：>="+dataParams.inspectNoStart+"\">&nbsp;行政检查职权数量<a href=\"javascript:;\" onclick=\"thisRemove('inspectNoStart')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}else if((dataParams.inspectNoStart == null || dataParams.inspectNoStart == "")&&dataParams.inspectNoEnd != null && dataParams.inspectNoEnd != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"inspectNoEndTag\" title=\"行政检查职权数量：<="+dataParams.inspectNoEnd+"\">&nbsp;行政检查职权数量<a href=\"javascript:;\" onclick=\"thisRemove('inspectNoEnd')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	
	if(dataParams.permitNoStart != null && dataParams.permitNoStart != "" && dataParams.permitNoEnd != null && dataParams.permitNoEnd != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"permitNoTag\" title=\"行政许可职权数量："+dataParams.permitNoStart+"-"+dataParams.permitNoEnd+"\">&nbsp;行政许可职权数量<a href=\"javascript:;\" onclick=\"thisRemoveTwo('permitNo')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}else if(dataParams.permitNoStart != null && dataParams.permitNoStart != ""&&(dataParams.permitNoEnd == null || dataParams.permitNoEnd == "")){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"permitNoStartTag\" title=\"行政许可职权数量：>="+dataParams.permitNoStart+"\">&nbsp;行政许可职权数量<a href=\"javascript:;\" onclick=\"thisRemove('permitNoStart')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}else if((dataParams.permitNoStart == null || dataParams.permitNoStart == "")&&dataParams.permitNoEnd != null && dataParams.permitNoEnd != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"permitNoEndTag\" title=\"行政许可职权数量：<="+dataParams.permitNoEnd+"\">&nbsp;行政许可职权数量<a href=\"javascript:;\" onclick=\"thisRemove('permitNoEnd')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	
	if(dataParams.forceNoStart != null && dataParams.forceNoStart != "" && dataParams.forceNoEnd != null && dataParams.forceNoEnd != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"forceNoTag\" title=\"行政强制职权数量："+dataParams.forceNoStart+"-"+dataParams.forceNoEnd+"\">&nbsp;行政强制职权数量<a href=\"javascript:;\" onclick=\"thisRemoveTwo('forceNo')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}else if(dataParams.forceNoStart != null && dataParams.forceNoStart != ""&&(dataParams.forceNoEnd == null || dataParams.forceNoEnd == "")){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"forceNoStartTag\" title=\"行政强制职权数量：>="+dataParams.forceNoStart+"\">&nbsp;行政强制职权数量<a href=\"javascript:;\" onclick=\"thisRemove('forceNoStart')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}else if((dataParams.forceNoStart == null || dataParams.forceNoStart == "")&&dataParams.forceNoEnd != null && dataParams.forceNoEnd != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"forceNoEndTag\" title=\"行政强制职权数量：<="+dataParams.forceNoEnd+"\">&nbsp;行政强制职权数量<a href=\"javascript:;\" onclick=\"thisRemove('forceNoEnd')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
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


