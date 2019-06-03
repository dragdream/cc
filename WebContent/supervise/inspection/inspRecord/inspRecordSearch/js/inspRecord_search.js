var params;
var listdata = {
		isShowColumn: [
			{codeNo:'subjectId',codeName:'执法主体'},
			{codeNo:'departmentId',codeName:'执法机关'},
			{codeNo:'area',codeName:'行政区划'},
			{codeNo:'orgSys',codeName:'所属领域'},
			{codeNo:'partyType',codeName:'当事人类型'},
			{codeNo:'partyName',codeName:'当事人名称'},
			{codeNo:'cardType',codeName:'当事人证件类型'},
			{codeNo:'officeName',codeName:'执法人员姓名'},
			{codeNo:'cardCode',codeName:'执法证号码'},
			{codeNo:'isInspectionPass',codeName:'检查结果'},
			{codeNo:'act',codeName:'行政行为'},
			{codeNo:'inspectionDate',codeName:'检查日期'},
			{codeNo:'inspectionNumber',codeName:'检查单'},
			{codeNo:'inspectionItem',codeName:'检查项'},
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
var temp = ['inspectionAddr','inspectionDateStr','isInspectionPass'];

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
	//执法机关
	departmentIdInit();
	//执法主体
	subjectIdInit();
	//所属地区
	areaInit();
	//所属领域
	 orgSysInit();
	
	//当事人类型
	partyTypeInit();
	//当事人证件类型
	cardTypeInit();
	//行政行为
	act();
	//检查单
//	inspectionNumberInit();
	//检查项
//	inspectionItemInit();
	
	//可选列
	$(".panList").append(juicer(tpl,listdata));
}

//重置
function refresh(){
	$("#form1").form('clear');
}

//所属地区
function areaInit(){
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
//所属领域
function orgSysInit(){
 var json = tools.requestJsonRs("/commonCtrl/getOrgSys.action");
    if(json.rtState) {
        $('#orgSys').combobox({
            data: json.orgSys,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            panelMaxHeight : 192,
            multiple:true,
            prompt : '请选择',
            onHidePanel: function() {
                var valueField = $(this).combobox("options").valueField;
                var val = $(this).combobox("getValues"); //当前combobox的值
                var allData = $(this).combobox("getData");  //获取combobox所有数据
                var unSelect = [allData.length]
                var currentValue=val.toString().split(",");//把选中的值及输入值分割为数组
                for(var j=0;j<currentValue.length;j++){//循环选中的值和com中所有值进行比对，不存在的利用unselect清除
                    var result = true;     //为true说明输入的值在下拉框数据中不存在
                    for (var i = 0; i < allData.length; i++) {
                        if (currentValue[j] == allData[i][valueField]) {
                            result = false;
                        }
                    }
                    if(result){//仅仅清除不存在的值
                        $(this).combobox('unselect', currentValue[j]);
                    }
                }
            }
        });
    }
}
//当时人类型
function partyTypeInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "COMMON_PARTY_TYPE"});
	var page = "";
	for (var i=0 , j=1; i<=json.rtData.length-1;i++,j++){
		//每五个换一行
//		if(j && j % 5 == 0){
//			page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="partyType" id="partyType'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
//        			+ 'value="'+json.rtData[i].codeNo+'" labelWidth="100px" label="'+json.rtData[i].codeName+'  "/><br>';
//		    var pageDoc = $('#subLevel').html(page);
//		    $.parser.parse(pageDoc);
//		}else{
	        page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="partyType" id="partyType'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
	            	+ 'value="'+json.rtData[i].codeNo+'" labelWidth="100px" label="'+json.rtData[i].codeName+'  "/>\n';
	        var pageDoc = $('#partyType').html(page);
	        $.parser.parse(pageDoc);
//		}
	}
}
//当事人证件类型
function cardTypeInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "COMMON_CARD_TYPE_NEW"});
	var page = "";
	for (var i=0 , j=1; i<=json.rtData.length-1;i++,j++){
		//每n个换一行
		if(j && j % 5 == 0){
			page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="cardType" id="cardType'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
        			+ 'value="'+json.rtData[i].codeNo+'" labelWidth="100px" label="'+json.rtData[i].codeName+'  "/><br>';
		    var pageDoc = $('#cardType').html(page);
		    $.parser.parse(pageDoc);
		}else{
	        page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="cardType" id="cardType'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
	            	+ 'value="'+json.rtData[i].codeNo+'" labelWidth="100px" label="'+json.rtData[i].codeName+'"/>\n';
	        var pageDoc = $('#cardType').html(page);
	        $.parser.parse(pageDoc);
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
/**
 * 进入综合查询界面
 * @returns
 */
var dataParams = "";
function doFilingSave(){
	if($('#form1').form('enableValidation').form('validate')){
	dataParams = tools.formToJson($("#form1"));
	alert(dataParams);
	console.log(dataParams);
	dataParams.partyType = "";
	dataParams.partyTypeName = "";
	dataParams.cardType = "";
	dataParams.cardTypeName = "";
	dataParams.isInspectionPass = "";
	dataParams.isInspectionPassName = "";
	dataParams.act = "";
	dataParams.actName = "";
	dataParams.subjectId = $("#subjectId").val();
	dataParams.subjectIdName = $("#subjectId").combobox('getText');
	dataParams.departmentId = $("#departmentId").val();
	dataParams.departmentIdName = $("#departmentId").combobox('getText');
	dataParams.area = $("#area").val();
	dataParams.areaName = $("#area").combobox('getText');
	dataParams.orgSys = $("#orgSys").val();
	dataParams.orgSysName = $("#orgSys").combobox('getText');
	
	if($("#form1 input[name='partyType']:checked").length >0){
			$("#form1 input[name='partyType']:checked").each(function(){
				dataParams.partyType += this.value + ',';
				dataParams.partyTypeName += this.labels[0].innerHTML + '，';
			})
	}
	if($("#form1 input[name='cardType']:checked").length >0){
		$("#form1 input[name='cardType']:checked").each(function(){
			dataParams.cardType += this.value + ',';
			dataParams.cardTypeName += this.labels[0].innerHTML + '，';
		})
	}
	if($("#form1 input[name='isInspectionPass']:checked").length >0){
			$("#form1 input[name='isInspectionPass']:checked").each(function(){
				dataParams.isInspectionPass += this.value + ',';
				dataParams.isInspectionPassName += $.trim(this.labels[0].innerHTML) + '，';
			})
	}
	if($("#form1 input[name='act']:checked").length >0){
		$("#form1 input[name='act']:checked").each(function(){
			dataParams.act += this.value + ',';
			dataParams.actName += $.trim(this.labels[0].innerHTML) + '，';
		})
	}
	//初始化表格
	listDatagrid();
	//已选查询条件标签
	isTerm();
	document.getElementById("divSearch").style.display="none";
	document.getElementById("divIndex").style.display="block";
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
        url : contextPath + '/inspListRecordCtrl/findSearchListBypage.action',
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
				{ field : 'inspectionNumber', title : '检查单号', width : '18%',align:'left' , halign: 'center',
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
     			{ field : 'inspectionAddr', title : '检查地点', width : '25%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
     			{ field : 'inspectionDateStr', title : '检查日期', width :'10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'isInspectionPass', title : '检查结果', width : '10%',align:'center' , halign: 'center',hidden:true , formatter: 
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
	top.bsWindow(contextPath + "/supervise/inspection/inspRecord/inspectionRecord_detail.jsp?id="+id, "查看",
			{
				width : "700",
				height : "360",
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
	if(dataParams.subjectId != null && dataParams.subjectId != ""){
		var $table= $("#condition");
		var str = dataParams.subjectIdName;
		var subjectIdStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=执法主体："+subjectIdStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"subjectIdTag\">&nbsp;执法主体<a href=\"javascript:;\" onclick=\"thisRemove('subjectId')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.departmentId != null && dataParams.departmentId != ""){
		var $table= $("#condition");
		var str = dataParams.departmentIdName;
		var departmentIdStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=执法机关："+departmentIdStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"departmentIdTag\">&nbsp;执法机关<a href=\"javascript:;\" onclick=\"thisRemove('departmentId')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.area != null && dataParams.area != ""){
		var $table= $("#condition");
		var str = dataParams.areaName;
		var areaStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=行政区划："+areaStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"areaTag\">&nbsp;行政区划<a href=\"javascript:;\" onclick=\"thisRemove('area')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.orgSys != null && dataParams.orgSys != ""){
		var $table= $("#condition");
		var str = dataParams.orgSysName;
		var orgSysStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=所属领域："+orgSysStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"orgSysTag\">&nbsp;所属领域<a href=\"javascript:;\" onclick=\"thisRemove('orgSys')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.partyType != null && dataParams.partyType != ""){
		var $table= $("#condition");
		var str = dataParams.partyTypeName;
		var partyTypeStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=当事人类型："+partyTypeStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"partyTypeTag\">&nbsp;当事人类型<a href=\"javascript:;\" onclick=\"thisRemove('partyType')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.partyName != null && dataParams.partyName != ""){
		var $table= $("#condition");
		var vTr= "<span class='tagbox-label' title=当事人姓名："+dataParams.partyName+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id='partyNameTag'>&nbsp;当事人姓名<a href='javascript:;' onclick=\"thisRemove('partyName')\" class='tagbox-remove'></a></span>";
		$("#condition").append(vTr);
	}
	if(dataParams.cardType != null && dataParams.cardType != ""){
		var $table= $("#condition");
		var str = dataParams.cardTypeName;
		var cardTypeStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=当事人证件类型："+cardTypeStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"cardTypeTag\">&nbsp;当事人证件类型<a href=\"javascript:;\" onclick=\"thisRemove('cardType')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.officeName != null && dataParams.officeName != ""){
		var $table= $("#condition");
		var vTr= "<span class='tagbox-label' title=执法人员姓名："+dataParams.officeName+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id='officeNameTag'>&nbsp;执法人员姓名<a href='javascript:;' onclick=\"thisRemove('officeName')\" class='tagbox-remove'></a></span>";
		$("#condition").append(vTr);
	}
	if(dataParams.cardCode != null && dataParams.cardCode != ""){
		var $table= $("#condition");
		var vTr= "<span class='tagbox-label' title=执法证号码："+dataParams.cardCode+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id='cardCodeTag'>&nbsp;执法证号码<a href='javascript:;' onclick=\"thisRemove('cardCode')\" class='tagbox-remove'></a></span>";
		$("#condition").append(vTr);
	}
	if(dataParams.isInspectionPass != null && dataParams.isInspectionPass != ""){
		var $table= $("#condition");
		var str = dataParams.isInspectionPassName;
		var isInspectionPassStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=检查结果："+isInspectionPassStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"isInspectionPassTag\">&nbsp;检查结果<a href=\"javascript:;\" onclick=\"thisRemove('isInspectionPass')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	//行政行为
	//检查日期
	//检查单
	//检查项
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
	
}

//行政行为
function act(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "INSPECTION_ADMIN_BEHAVIOR"});
	console.log(json);
	var page = "";
	for (var i=0 , j=1; i<=json.rtData.length-1;i++,j++){
	        page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="partyType" id="partyType'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
	            	+ 'value="'+json.rtData[i].codeNo+'" labelWidth="100px" label="'+json.rtData[i].codeName+'  "/>\n';
	        var pageDoc = $('#act').html(page);
	        $.parser.parse(pageDoc);
	}
}

//行政主体
function subjectIdInit(){
	var json = tools.requestJsonRs("/subjectSearchController/getSubjectRoles.action");
	if(json.rtState) {
		$('#subjectId').combobox({
	    data: json.rtData,
	    valueField: 'id',
	    textField: 'subName',
	    panelHeight: 'auto',
	    panelMaxHeight : 192,
	    multiple:true,
	    prompt : '请选择',
	    onHidePanel: function() {
	        var valueField = $(this).combobox("options").valueField;
	        var val = $(this).combobox("getValues"); //当前combobox的值
	        var allData = $(this).combobox("getData");  //获取combobox所有数据
	        var unSelect = [allData.length]
	        var currentValue=val.toString().split(",");//把选中的值及输入值分割为数组
	        for(var j=0;j<currentValue.length;j++){//循环选中的值和com中所有值进行比对，不存在的利用unselect清除
	            var result = true;     //为true说明输入的值在下拉框数据中不存在
	            for (var i = 0; i < allData.length; i++) {
	                if (currentValue[j] == allData[i][valueField]) {
	                    result = false;
	                }
	            }
	            if(result){//仅仅清除不存在的值
	                $(this).combobox('unselect', currentValue[j]);
	                }
	            }
	        }
	    });
	}
}

//行政机关
function departmentIdInit(){
	var json = tools.requestJsonRs("/departmentSearchController/getDepartmentRoles.action");
	if(json.rtState) {
	    $('#departmentId').combobox({
	        data: json.rtData,
	        valueField: 'id',
	        textField: 'name',
	        panelHeight: 'auto',
	        panelMaxHeight : 192,
	        multiple:true,
	        prompt : '请选择',
	        onHidePanel: function() {
	            var valueField = $(this).combobox("options").valueField;
	            var val = $(this).combobox("getValues"); //当前combobox的值
	            var allData = $(this).combobox("getData");  //获取combobox所有数据
	            var unSelect = [allData.length]
	            var currentValue=val.toString().split(",");//把选中的值及输入值分割为数组
	            for(var j=0;j<currentValue.length;j++){//循环选中的值和com中所有值进行比对，不存在的利用unselect清除
	                var result = true;     //为true说明输入的值在下拉框数据中不存在
	                for (var i = 0; i < allData.length; i++) {
	                    if (currentValue[j] == allData[i][valueField]) {
	                        result = false;
	                    }
	                }
	                if(result){//仅仅清除不存在的值
	                    $(this).combobox('unselect', currentValue[j]);
	                }
	            }
	        }
	    });
	}
}
