var params;
var educationJsons = [];
var sexJsons = [{codeNo:'01',codeName:'男'},
                {codeNo:'02',codeName:'女'}];
var politiveJsons = [{codeNo:'1',codeName:'中共党员'},
                {codeNo:'2',codeName:'非中共'}];
var nationJsons = [{codeNo:'0',codeName:'汉族'},
                {codeNo:'1',codeName:'非汉族'}];
var isGetcodeStrJsons = [{codeNo:'1',codeName:'是'},
                {codeNo:'0',codeName:'否'}];
var isLawyerStrJsons = [{codeNo:'1',codeName:'是'},
                {codeNo:'0',codeName:'否'}];
var jobClassJsons = [{codeNo:'a',codeName:'办事员'},
	                 {codeNo:'b',codeName:'科员'},
	                 {codeNo:'c',codeName:'科级'},
	                 {codeNo:'d',codeName:'处级'},
	                 {codeNo:'e',codeName:'厅局级及以上'},
	                 {codeNo:'f',codeName:'其他'}];
var listdata = {
		isShowColumn: [
			{codeNo:'sex',codeName:'性别'},
			{codeNo:'politive',codeName:'政治面貌'},
			{codeNo:'nation',codeName:'民族'},
			{codeNo:'education',codeName:'最高学历'},
			{codeNo:'jobClass',codeName:'职级'},
			{codeNo:'isGetcodeStr',codeName:'是否取得过执法证'},
			{codeNo:'isLawyerStr',codeName:'是否公职律师'},
//			{codeNo:'deptLevel',codeName:'机关层级'},
//			{codeNo:'administrativeDivision',codeName:'行政区划'},
//			{codeNo:'deptName',codeName:'监督机关'},
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
var temp = ['sex','politive','nation','education','jobClass','isGetcodeStr','isLawyerStr'];

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
	//最高学历
	educationInit();
//	//所属地区
//	administrativeDivisionInit();
//	//机关层级
//	deptLevelInit();

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
//加载代码表的最高学历
function educationInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "SYSTEM_CODE_EDUCATION"});
	var page = "";
	var educationJson;
	for (var i=0 , j=1; i<=json.rtData.length-1;i++,j++){
		//每n个换一行
		if(j && j % 5 == 0){
			page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="education" id="education'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
        			+ 'value="'+json.rtData[i].codeNo+'" labelWidth="100px" label="'+json.rtData[i].codeName+'  "/><br>';
		    var pageDoc = $('#education').html(page);
		    $.parser.parse(pageDoc);
		    educationJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
	        educationJsons.push(educationJson);
		}else{
	        page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="education" id="education'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
	            	+ 'value="'+json.rtData[i].codeNo+'" labelWidth="100px" label="'+json.rtData[i].codeName+'"/>\n';
	        var pageDoc = $('#education').html(page);
	        $.parser.parse(pageDoc);
	        educationJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
	        educationJsons.push(educationJson);
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
	dataParams.sex = "";
	dataParams.sexName = "";
	dataParams.politive = "";
	dataParams.politiveName = "";
	dataParams.nation = "";
	dataParams.nationName = "";
	dataParams.education = "";
	dataParams.educationName = "";
	dataParams.jobClass = "";
	dataParams.jobClassName = "";
	dataParams.isGetcodeStr = "";
	dataParams.isGetcodeStrName = "";
	dataParams.isLawyerStr = "";
	dataParams.isLawyerStrName = "";
//	dataParams.deptLevel = "";
//	dataParams.deptLevelName = "";
//	dataParams.administrativeDivision = $("#administrativeDivision").val();
//	dataParams.administrativeDivisionName = $("#administrativeDivision").combobox('getText');
	if($("#form1 input[name='sex']:checked").length >0){
			$("#form1 input[name='sex']:checked").each(function(){
				dataParams.sex += this.value + ',';
				for(var i=0;i<sexJsons.length;i++){
	            	if(this.value==sexJsons[i].codeNo){
	            		dataParams.sexName += $.trim(sexJsons[i].codeName) + '，';
	            		break;
	            	}
	            }
			})
	}
	if($("#form1 input[name='politive']:checked").length >0){
		$("#form1 input[name='politive']:checked").each(function(){
			dataParams.politive += this.value + ',';
			for(var i=0;i<politiveJsons.length;i++){
            	if(this.value==politiveJsons[i].codeNo){
            		dataParams.politiveName += $.trim(politiveJsons[i].codeName) + '，';
            		break;
            	}
            }
		})
	}
	if($("#form1 input[name='nation']:checked").length >0){
			$("#form1 input[name='nation']:checked").each(function(){
				dataParams.nation += this.value + ',';
				for(var i=0;i<nationJsons.length;i++){
	            	if(this.value==nationJsons[i].codeNo){
	            		dataParams.nationName += $.trim(nationJsons[i].codeName) + '，';
	            		break;
	            	}
	            }
			})
	}
	if($("#form1 input[name='education']:checked").length >0){
		$("#form1 input[name='education']:checked").each(function(){
			dataParams.education += this.value + ',';
			for(var i=0;i<educationJsons.length;i++){
            	if(this.value==educationJsons[i].codeNo){
            		dataParams.educationName += $.trim(educationJsons[i].codeName) + '，';
            		break;
            	}
            }
		})
	}
	if($("#form1 input[name='jobClass']:checked").length >0){
		$("#form1 input[name='jobClass']:checked").each(function(){
			dataParams.jobClass += this.value + ',';
			for(var i=0;i<jobClassJsons.length;i++){
            	if(this.value==jobClassJsons[i].codeNo){
            		dataParams.jobClassName += $.trim(jobClassJsons[i].codeName) + '，';
            		break;
            	}
            }
		})
	}
	if($("#form1 input[name='isGetcodeStr']:checked").length >0){
		$("#form1 input[name='isGetcodeStr']:checked").each(function(){
			dataParams.isGetcodeStr += this.value + ',';
			for(var i=0;i<isGetcodeStrJsons.length;i++){
            	if(this.value==isGetcodeStrJsons[i].codeNo){
            		dataParams.isGetcodeStrName += $.trim(isGetcodeStrJsons[i].codeName) + '，';
            		break;
            	}
            }
		})
	}
	if($("#form1 input[name='isLawyerStr']:checked").length >0){
			$("#form1 input[name='isLawyerStr']:checked").each(function(){
				dataParams.isLawyerStr += this.value + ',';
				for(var i=0;i<isLawyerStrJsons.length;i++){
	            	if(this.value==isLawyerStrJsons[i].codeNo){
	            		dataParams.isLawyerStrName += $.trim(isLawyerStrJsons[i].codeName) + '，';
	            		break;
	            	}
	            }
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
        url : contextPath + '/SupPersonController/generalListByPageRoles.action',
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
				{ field : 'name', title : '姓名', width : '18%',align:'left' , halign: 'center',
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
     			{ field : 'sex', title : '性别', width : '5%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
     			{ field : 'politive', title : '政治面貌', width :'10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'nation', title : '民族', width : '10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
                { field : 'education', title : '最高学历', width : '15%',align:'center' , halign: 'center' ,hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },}, 
				{ field : 'jobClass', title : '职级', width :'15%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'isGetcodeStr', title : '是否取得过执法证', width :'15%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
	            { field : 'isLawyerStr', title : '是否公职律师', width :'10%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
	            { field : 'deptLevel', title : '机关层级', width :'8%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
	            { field : 'administrativeDivision', title : '行政区划', width :'8%',align:'center' , halign: 'center',hidden:true , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
	                    return optsStr;
					}
	            },},
	            { field : 'deptName', title : '监督机关', width :'8%',align:'center' , halign: 'center',hidden:true , formatter: 
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
	top.bsWindow(contextPath + "/supervise/supervise/supperson_search_look.jsp?id="+id, "查看",
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
	if(dataParams.name != null && dataParams.name != ""){
		var $table= $("#condition");
		var vTr= "<span class='tagbox-label' title=姓名："+dataParams.name+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id='nameTag'>&nbsp;姓名" +
				"<a href='javascript:;' onclick=\"thisRemove('name','textbox')\" class='tagbox-remove'></a></span>";
		$("#condition").append(vTr);
	}
	if(dataParams.sex != null && dataParams.sex != ""){
		var $table= $("#condition");
		var val = dataParams.sex;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.sexName;
		var sexStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=性别："+sexStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"sexTag\">&nbsp;性别" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('sex','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.politive != null && dataParams.politive != ""){
		var $table= $("#condition");
		var val = dataParams.politive;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.politiveName;
		var politiveStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=政治面貌："+politiveStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"politiveTag\">&nbsp;政治面貌" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('politive','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.nation != null && dataParams.nation != ""){
		var $table= $("#condition");
		var val = dataParams.nation;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.nationName;
		var nationStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=民族："+nationStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"nationTag\">&nbsp;民族" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('nation','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.education != null && dataParams.education != ""){
		var $table= $("#condition");
		var val = dataParams.education;
		var educationVal = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.educationName;
		var educationStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=最高学历："+educationStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"educationTag\">&nbsp;最高学历" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('education','checkbox','"+educationVal+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.jobClass != null && dataParams.jobClass != ""){
		var $table= $("#condition");
		var val = dataParams.jobClass;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.jobClassName;
		var jobClassStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=职级："+jobClassStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"jobClassTag\">&nbsp;职级" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('jobClass','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.isGetcodeStr != null && dataParams.isGetcodeStr != ""){
		var $table= $("#condition");
		var val = dataParams.isGetcodeStr;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.isGetcodeStrName;
		var isGetcodeStrStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=是否取得过执法证："+isGetcodeStrStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"isGetcodeStrTag\">&nbsp;是否取得过执法证" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('isGetcodeStr','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(dataParams.isLawyerStr != null && dataParams.isLawyerStr != ""){
		var $table= $("#condition");
		var val = dataParams.isLawyerStr;
		var valStr = (val.substring(val.length-1)==',')?val.substring(0,val.length-1):val
		var str = dataParams.isLawyerStrName;
		var isLawyerStrStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" title=是否公职律师："+isLawyerStrStr+" style=\"float:left;height: 26px;line-height: 26px;margin: 5px 2px 4px 4px;\" id=\"isLawyerStrTag\">&nbsp;是否公职律师" +
				"<a href=\"javascript:;\" onclick=\"thisRemove('isLawyerStr','checkbox','"+valStr+"')\" class=\"tagbox-remove\"></a></span>";
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
