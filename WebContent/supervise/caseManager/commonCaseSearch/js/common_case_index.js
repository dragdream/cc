var subjectId = "";//主体ID
var submitJson = [
    {codeNo:'0', codeName: '未提交'},
    {codeNo:'1', codeName: '已提交'}
]
var listdata = {
	isShowColumn: [
		{codeNo:'officeName', codeName: '执法人员'},
		{codeNo:'filingDate', codeName: '立案日期'},
		{codeNo:'surveyEndDate', codeName: '调查终结日期'},
		{codeNo:'punishmentDate', codeName: '行政处罚决定书日期'},
		{codeNo:'pdSentDate', codeName: '决定书送达日期'},
		{codeNo:'closedDate', codeName: '结案日期'},
		{codeNo:'caseTime', codeName: '案件总时长'},
		{codeNo:'filingTime', codeName: '立案周期'},
		{codeNo:'surveyTime', codeName: '调查取证周期'},
		{codeNo:'punishTime', codeName: '作出处罚决定周期'},
		{codeNo:'punishDecisionExecutTime', codeName: '处罚决定执行周期'},
		{codeNo:'closedTime', codeName: '结案周期'},
		{codeNo:'isMajorCase', codeName: '是否重大案件'},
		{codeNo:'isCollectiveDiscussion', codeName: '是否集体讨论'},
		{codeNo:'isFilingLegalReview', codeName: '是否法制审核'},
		{codeNo:'dataSource', codeName: '数据来源'},
		{codeNo:'createDate', codeName: '入库日期'},
		{codeNo:'isPlot', codeName: '情节从轻从重情况'},
		{codeNo:'isForce', codeName: '是否采取强制措施'},
		{codeNo:'isOrganEnforce', codeName: '是否强制执行'}

	]
}
var isShowColumn = [
    {codeNo:'officeName', codeName: '执法人员'},
    {codeNo:'filingDate', codeName: '立案日期'},
    {codeNo:'surveyEndDate', codeName: '调查终结日期'},
    {codeNo:'punishmentDate', codeName: '行政处罚决定书日期'},
    {codeNo:'pdSentDate', codeName: '决定书送达日期'},
    {codeNo:'closedDate', codeName: '结案日期'},
    {codeNo:'caseTime', codeName: '案件总时长'},
    {codeNo:'filingTime', codeName: '立案周期'},
    {codeNo:'surveyTime', codeName: '调查取证周期'},
    {codeNo:'punishTime', codeName: '作出处罚决定周期'},
    {codeNo:'punishDecisionExecutTime', codeName: '处罚决定执行周期'},
    {codeNo:'closedTime', codeName: '结案周期'},
    {codeNo:'isMajorCase', codeName: '是否重大案件'},
    {codeNo:'isCollectiveDiscussion', codeName: '是否集体讨论'},
    {codeNo:'isFilingLegalReview', codeName: '是否法制审核'},
    {codeNo:'dataSource', codeName: '数据来源'},
    {codeNo:'createDate', codeName: '入库日期'},
    {codeNo:'isPlot', codeName: '情节从轻从重情况'},
    {codeNo:'isForce', codeName: '是否采取强制措施'},
    {codeNo:'isOrganEnforce', codeName: '是否强制执行'}
];

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

/*
$(".optional").on("mouseover",function(){
	
		$(this).removeClass("fa-angle-down");
		$(this).addClass("fa-angle-up");
	    $(".panList").show();
});

$(".panList").on("mouseover",function(){
	$('.optional').removeClass("fa-angle-down");
	$('.optional').addClass("fa-angle-up");
	$(".panList").show();
});

$(".panList").on("mouseout",function(){
	$('.optional').removeClass("fa-angle-up");
	$('.optional').addClass("fa-angle-down");
	$(".panList").hide();
});

*/
// $(".panList li").on("click",function(){
// 	$(".panList").hide();
//  });
var temp = [];
function detail(code,th){
	if($(th).children('i').hasClass("fa-check")){
		$(th).children('i').removeClass("fa-check");
		//$(th).css("background-color","#ccc");
		$('#common_case_index_datagrid').datagrid('hideColumn', code);
		for(var i=0;i<temp.length;i++){
			if(temp[i] == code){
				temp.splice(i,1);
				break;
			}
		}
	} else{
		$(th).children('i').addClass("fa-check");
		//$(th).css("background-color","#aaa");
		$('#common_case_index_datagrid').datagrid('showColumn', code);
		temp.push(code);
	}
   }

//juicer.register('detail', detail);
var tpl=[
	'{@each isShowColumn as it}',
	'<li onclick="detail(\'${it.codeNo}\',this)" title="${it.codeName}"><i class="fa"></i>${it.codeName}</li>',
	'{@/each}'
   ].join('\n');
$(".panList").append(juicer(tpl,listdata));


//var param = $("#param").val();
//操作权限
var menuNameStr = $('#common_case_index_menuGroupStrNames').val();
/**
 * 固定列
 */
var frozenColumn = [[
	{ 
	    field: 'id', checkbox: true, title: "ID", width: '3%', halign: 'center', align: 'center'
	},
	{field:'ID',title:'<span title="序号">序号</span>',align:'center', width: '40px',
	    formatter:function(value,rowData,rowIndex){
	        return rowIndex+1;
	    }
	},
	{
	    field: 'filingNumber', title: '<span title="立案号">立案号</span>', width: '180px', halign: 'center', align: 'left', sortable:true,
	    formatter: function(e, rowData) {
	        var filingNumber = rowData.filingNumber;
	        if(filingNumber == null || filingNumber == 'null') {
	        	filingNumber = "";
	        }
	        var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+filingNumber+"'>"+filingNumber+"</lable>"
	        return lins;
	    }
	},
	{
	    field: 'punishmentCode', title: '<span title="处罚决定书文号">处罚决定书文号</span>', width: '180px', halign: 'center', align: 'left', sortable:true,
	    formatter: function(e, rowData) {
	        var punishmentCode = rowData.punishmentCode;
	        if(punishmentCode == null || punishmentCode == 'null') {
	            punishmentCode = "";
	        }
	        var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+punishmentCode+"'>"+punishmentCode+"</lable>"
	        return lins;
	    }
	},
	{
	    field: 'name', title: '<span title="案件名称">案件名称</span>', width: '300px', halign: 'center', align: 'left',
	    formatter: function(e, rowData) {
	        var name = rowData.name;
	        if(name == null || name == 'null') {
	            name = "";
	        }
	        var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+name+"'><a onclick='commonCaseLook(\"" + rowData.id + "\")' href='javaScript:void(0);'>" + name + "</a></lable>"
	        return lins;
	    }
	}
]];

var column = [
	{
		field: 'partyType', title: '当事人类型', width: '100px', halign: 'center', align: 'center',
		formatter: function(value,rowData,rowIndex){
			var jsonData = tools.requestJsonRs("/sysCode/getSysCodeNameByParentCodeNo.action", {parentCodeNo: 'COMMON_PARTY_TYPE', codeNo: value});
			if(jsonData.rtState){
		        return jsonData.rtData;
		    }else{
		    	return "";
		    }
		}
	},
	{
		field: 'citizenName', title: '当事人名称', width: '100px', halign: 'center', align: 'center',
		formatter: function(value,rowData,rowIndex){
			if(rowData.partyType == null || rowData.partyType == ''){
				return '';
			}else if(rowData.partyType=='2' || rowData.partyType=='9'){
		        return rowData.principal != null?rowData.principal:'';
		    }else{
		    	return rowData.citizenName != null?rowData.citizenName:'';
		    }
		}
	},
	{
		field: 'currentState', title: '办理状态', width: '130px', halign: 'center', align: 'center',
		formatter: function(value,rowData,rowIndex){
			var jsonData = tools.requestJsonRs("/sysCode/getSysCodeNameByParentCodeNo.action", {parentCodeNo: 'COMMON_CURRENT_STATE', codeNo: value});
			if(jsonData.rtState){
		        return jsonData.rtData;
		    }else{
		    	return "";
		    }
		}
	},
	{
		field: 'closedState', title: '结案状态', width: '130px', halign: 'center', align: 'center',
		formatter: function(value,rowData,rowIndex){
			var jsonData = tools.requestJsonRs("/sysCode/getSysCodeNameByParentCodeNo.action", {parentCodeNo: 'COMMON_CLOSED_STATE', codeNo: value});
			if(jsonData.rtState){
		        return jsonData.rtData;
		    }else{
		    	return "";
		    }
		}
	},
	{
		field: 'officeName', title: '执法人员', width: '200px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			var officeName = '';
			if(rowData.caseCommonStaffs != null && rowData.caseCommonStaffs.length > 0){
				for(var i=0;i<rowData.caseCommonStaffs.length;i++){
					officeName += rowData.caseCommonStaffs[i].officeName + ',';
				}
				officeName = officeName.substring(0,officeName.length - 1);
			}
			return officeName;
		}
	},
	{
		field: 'filingDate', title: '立案日期', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			return longToTime(value);
		}
	},
	{
		field: 'surveyEndDate', title: '调查终结日期', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			return longToTime(value);
		}
	},
	{
		field: 'punishmentDate', title: '行政处罚决定书日期', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			return longToTime(value);
		}
	},
	{
		field: 'pdSentDate', title: '决定书送达日期', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			return longToTime(value);
		}
	},
	{
		field: 'closedDate', title: '结案日期', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			return longToTime(value);
		}
	},
	{
		field: 'caseTime', title: '案件总时长', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			if(rowData.filingDate != null && rowData.closedDate != null){
				var time = rowData.closedDate - rowData.filingDate;
				return time/1000/60/60/24 + 1;
			}else{
				return '';
			}
		}
	},
	{
		field: 'filingTime', title: '立案周期', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			if(rowData.caseSource == '1' && rowData.filingDate != null && rowData.caseSources != null && rowData.caseSources.length > 0 && rowData.caseSources[0].commonCaseDate != null){
				var time = rowData.filingDate - rowData.caseSources[0].commonCaseDate;
				return time/1000/60/60/24 + 1;
			}else{
				return '';
			}
		}
	},
	{
		field: 'surveyTime', title: '调查取证周期', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			if(rowData.filingDate != null && rowData.surveyEndDate != null){
				var time = rowData.surveyEndDate - rowData.filingDate;
				return time/1000/60/60/24 + 1;
			}else{
				return '';
			}
		}
	},
	{
		field: 'punishTime', title: '作出处罚决定周期', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			if(rowData.punishmentDate != null && rowData.surveyEndDate != null){
				var time = rowData.punishmentDate - rowData.surveyEndDate;
				return time/1000/60/60/24 + 1;
			}else{
				return '';
			}
		}
	},
	{
		field: 'punishDecisionExecutTime', title: '处罚决定执行周期', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			if(rowData.punishDecisionExecutDate != null && rowData.punishmentDate != null){
				var time = rowData.punishDecisionExecutDate - rowData.punishmentDate;
				return time/1000/60/60/24 + 1;
			}else{
				return '';
			}
		}
	},
	{
		field: 'closedTime', title: '结案周期', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			if(rowData.closedDate != null && rowData.punishDecisionExecutDate != null){
				var time = rowData.closedDate - rowData.punishDecisionExecutDate;
				return time/1000/60/60/24 + 1;
			}else{
				return '';
			}
		}
	},
	{
		field: 'isMajorCase', title: '是否重大案件', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			return value == 1?'是':(value == 0 ?'否':'');
		}
	},
	{
		field: 'isCollectiveDiscussion', title: '是否集体讨论', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			return value == 1?'是':(value == 0 ?'否':'');
		}
	},
	{
		field: 'isFilingLegalReview', title: '是否法制审核', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			return value == 1?'是':(value == 0 ?'否':'');
		}
	},
	{
		field: 'dataSource', title: '数据来源', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			return value == 1?'系统录入':(value == 2 ?'接口对接':'');
		}
	},
	{
		field: 'createDate', title: '入库日期', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			return longToTime(value);
		}
	},
	{
		field: 'isPlot', title: '情节从轻从重情况', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			return value == 1?'无':(value == 2 ?'具有从轻情节':(value == 3 ?'具有减轻情节':(value == 4 ?'具有从重情节':'')));
		}
	},
	{
		field: 'isForce', title: '是否采取强制措施', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			return value == 1?'是':(value == 0 ?'否':'');
		}
	},
	{
		field: 'isOrganEnforce', title: '是否强制执行', width: '140px', halign: 'center', align: 'center', hidden: true,
		formatter: function(value,rowData,rowIndex){
			return value == 1?'是':(value == 0 ?'否':'');
		}
	}
];

/**
 * 活动列
 *//*
var column = new Array();*/

var params = {
	partyType : $("#partyType").val(),
	caseSource : $("#caseSource").val(),
	currentState : $("#currentState").val(),
	closedState : $("#closedState").val(),
	isSubmit : $("#isSubmit").val(),
	beginfilingDate : $("#beginfilingDate").val(),
	endfilingDate : $("#endfilingDate").val(),
	isMajorCase : $("#isMajorCase").val(),
	isFilingLegalReview : $("#isFilingLegalReview").val()
}

var params = tools.formToJson($("#common_case_form"));

/**
 * 页面初始化函数
 * @returns
 */
function doInitIndex() {
	
	//var pageDoc = $("#condition").html(condition);
	//$.parser.parse(pageDoc);
	
	console.log($("#isSubmit").val());
	//console.log(jsonobject);
	//initColumns();
	$("#hideTable").hide();
	//dateValidate('createStartDateStr', 'createEndDateStr');
	//initRows();
	//doInitEnter();//回车响应
    //commonCaseRefresh();//重置查询条件
    initIndexDatagrid(params);
    //getUserSubjetAndDepartment();// 获取登录信息，加载主体
    initPunishState();
    initpunishExecutState();
}

//导出
function exportCase(){
	/*var obj = $('#isShow').combobox('getValues');
	if (window.confirm("确定导出所有数据？")) {
		var json = tools.requestJsonRs("/caseCommonBaseSearchCtrl/export.action?term="+obj);
		console.log(json);
		if(json.rtData < 1001){
			location.href = '/caseCommonBaseSearchCtrl/export.action?isTrue=1&term='+obj;
		}else{
			alert("导出数据过大，请精确查询后再操作（导出数据限制：1000）");
		}
	}*/
	top.$.MsgBox.Confirm ("提示", "是否确认导出所有查询结果？", function(){
        //导出所有的查询结果
        //获取查询参数
        var params = tools.formToJson($("#common_case_form"));
        params.isShow = $('#isShow').combobox('getValues');
        console.log(params);
        //tools.requestJsonRs("/caseCommonBaseSearchCtrl/export.action", params);
        //if(json.rtState){
        	//$.MsgBox.Alert_auto("导出成功！");
//        }else{
//        	$.MsgBox.Alert_auto(json.rtMsg);
//        }
        $("#frame0").attr("src",contextPath+"/caseCommonBaseSearchCtrl/export.action?params="+encodeURIComponent(tools.jsonObj2String(params)));
    });
}

/**
 * 重置查询条件
 * @returns
 */
function commonCaseRefresh() {
    $('#name').textbox('setValue', '');
    $('#officeName').textbox('setValue', '');
    $('#cardCode').textbox('setValue', '');
    $('#powerName').textbox('setValue', '');
    $('#powerCode').textbox('setValue', '');
    $('#punishmentCode').textbox('setValue', '');
    $('#beginfilingDate').datebox('setValue', '');
    $('#endfilingDate').datebox('setValue', '');
    $('#beginclosedDate').datebox('setValue', '');
    $('#endclosedDate').datebox('setValue', '');
    $('#beginpunishDate').datebox('setValue', '');
    $('#endpunishDate').datebox('setValue', '');
    $('#beginpunishExecutDate').datebox('setValue', '');
    $('#endpunishExecutDate').datebox('setValue', '');
    $('#punishState').combobox('setValue', '0');
    $('#punishExecutState').combobox('setValue', '0');
}

/**
 * 表格加载函数
 * @returns
 */
function initIndexDatagrid(params){
	var params = tools.formToJson($("#common_case_form"));
	datagrid = $('#common_case_index_datagrid').datagrid({
        url: contextPath + '/caseCommonBaseSearchCtrl/findListBypage.action',
        queryParams: params,
        pagination: true,
        pageSize : 20,
        pageList : [ 10, 20, 50, 100 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar: '#toolbar', // 工具条对象
        checkbox: true,
        border: false,
        striped: true,//隔行变色
        /* idField:'formId',//主键列 */
        fitColumns: true, // 列是否进行自动宽度适应
        fit: true,
        singleSelect: false, //为true只能选择一行
        nowrap: true,
        multiSort: true,
        onLoadSuccess: function(data) {
        	//var isShow = $('#isShow').combobox('getValues');
        	if(temp.length>0){
        		for(var i = 0; i < temp.length; i++){
        			$('#common_case_index_datagrid').datagrid('showColumn', temp[i]);
            	}
        	}
        },
        frozenColumns:frozenColumn,
        columns: [column],
        onSortColumn: function(sort,order){
        	//alert('sort:'+sort+',order:'+order);
        }
    });
}

function initColumns(){
	/*var params = {
		tableName : 'TBL_CASE_COMMON_BASE',
		isShow : 1
	};
	var cols;
	var lengths;
	var dictionaryValue;
	var json = tools.requestJsonRs("/caseCommonDataCtrl/findListBypage.action", params);
	if(json.rtState) {
		for(var i = 0; i < json.rtData.length; i++){
			var col = json.rtData[i];
			if(col.isRegular == 1){
				continue;
			}
			if(col.isDictionary == 1){
				dictionaryValue = col.dictionaryValue;
				console.log(dictionaryValue);
				cols = {
					field: col.field, 
					title: '<span title=' + col.comments + '>' + col.comments + '</span>', 
					width: '10%', 
					halign: 'center',
					align: 'center',
					formatter: function(value,rowData,rowIndex){
						var jsonData = tools.requestJsonRs("/sysCode/getSysCodeNameByParentCodeNo.action", {parentCodeNo: dictionaryValue, codeNo: value});
						if(jsonData.rtState){
					        return jsonData.rtData;
					    }else{
					    	return "";
					    }
					}
				}
			}else if('VARCHAR2' == col.dataType || 'CHAR' == col.dataType){
				if(col.dataLength >= 1000){
					lengths = col.dataLength/100 + '%';
				}else if(col.dataLength >= 100){
					lengths = col.dataLength/40 + '%';
				}else if(col.dataLength >= 10){
					lengths = col.dataLength/5 + '%';
				}
				cols = {
					field: col.field, 
					title: '<span title=' + col.comments + '>' + col.comments + '</span>', 
					width: lengths, 
					halign: 'center'
					//align: 'center',
				}
				if(col.dataLength >= 200){
					cols.align = 'left';
				}
			}else if('DATE' == col.dataType){
				cols = {
					field: col.field, 
					title: '<span title=' + col.comments + '>' + col.comments + '</span>', 
					width: '140px', 
					halign: 'center',
					align: 'center',
					formatter: function(value,rowData,rowIndex){
						return longToTime(value);
					}
				}
			}else{
				if(col.dataLength >= 1000){
					lengths = col.dataLength/100;
				}else if(col.dataLength >= 100){
					lengths = col.dataLength/40;
				}else if(col.dataLength >= 10){
					lengths = col.dataLength/5;
				}
				cols = {
					field: col.field, 
					title: '<span title=' + col.comments + '>' + col.comments + '</span>', 
					width: lengths + '%', 
					halign: 'center',
					align: 'center'
				}
				if(col.dataLength >= 200){
					cols.align = 'left';
				}
				if(col.field.substring(0,2) == 'is'){
					cols.formatter = function(value,rowData,rowIndex){
						return value == 1?'是':(value == 0 ?'否':'');
					}
				}
			}
			if(col.isRegular == 0){
				cols.hidden = true;
			}
			if(col.isSortable == 1){
				cols.sortable = true;
			}
			column.push(cols);
		}
	}*/
	
}

/**
 * 获取回车事件
 * @returns
 */
function doInitEnter() {
    document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
            commonCaseSearch();
        }
    }
}

/**
 * 查询方法
 * @returns
 */
function commonCaseSearch(){
    /*var subjectId = $.trim($('#subjectId').combobox('getValue'));
    var isSubmit = $.trim($('#isSubmit').combobox('getValue'));
    if(isSubmit == null || isSubmit == ''){
        isSubmit = null;
    }
    var name = $.trim($('#name').val());
    var punishmentCode = $.trim($('#punishmentCode').val());
    var createStartDateStr = $.trim($('#createStartDateStr').val());
    var createEndDateStr = $.trim($('#createEndDateStr').val());
    var param = {
        subjectId: subjectId,
        name: name,
        isSubmit: isSubmit,
        punishmentCode: punishmentCode,
        createStartDateStr: createStartDateStr,
        createEndDateStr: createEndDateStr,
        menuNames: menuNameStr
    }*/
	/*if($("#showOrHide").attr("title")=="收起"){
		//$("#hideTable").hide();
		$("#hideTable").slideToggle(800);
		$("#showOrHide").attr("title","展开");
	}
	*/
	
	if($(".tabshow").hasClass("fa-angle-up")){
		$("#hideTable").slideUp(800);
		$(".tabshow").removeClass("fa-angle-up");
		$(".tabshow").addClass("fa-angle-down");
		$('.showtext').text("高级筛选");
	}
	
	var params = tools.formToJson($("#common_case_form"));
	window.setTimeout(initIndexDatagrid(params),800);
}

/**
 * 返回
 * @param id
 * @returns
 */
function commonCaseSubmit() {
	params = tools.formToJson($("#common_case_form"));
	location.href = '/supervise/caseManager/commonCaseSearch/common_case_search0.jsp?'+$.param(params);
}

/**
 * 案件信息查看方法
 * @returns
 */
function commonCaseLook(id) {
    var params = {
            caseId: id,
            pageUrl: '/supervise/caseManager/commonCase/common_case_index_look.jsp'
    }
    var url = contextPath+"/caseCommonBaseCtrl/commonCaseLook.action?"+ $.param(params);
    var title = "案件查看";
    top.bsWindow(
            url,
            title,
            { 
                width:"1000", 
                height:"500", 
                buttons:[
                    {name:"关闭",classStyle:"btn-alert-gray"}
                ],
                submit:function(v,h){
                    if(h != null && h != ''){
                        var cw = h[0].contentWindow;
                        if(v=="关闭"){
                            return true;
                        }
                    }else{
                        return true;
                    }
                    commonCaseSearch();
                }
            }
     );
}

/*$('#caseSource').checkbox({
    label: '案件来源',
    value: '1',
    width: 15,
    height: 15,
    labelAlign: 'left',
    labelPosition: 'after',
    labelWidth: '100',
    onChange: function(){
    	var caseSource = $("input[name='caseSource']:checked").val();
    	if(caseSource == 1){
            $('#common_case_index_datagrid').datagrid('showColumn', 'caseSource');//pictureId:filed值
        }else{
            $('#common_case_index_datagrid').datagrid('hideColumn', 'caseSource');//gridspecialprogram:datagrid的id
        }
    }
});*/

function showOrHide1(){
	var thisObj = $("#showOrHide");
	var value = thisObj.val();
	//$("#hideTable").slideToggle();
	if(value=="1"){
		//$("#hideTable").show();
//		$("#hideTable").animate({left:"100",opacity:1},1000,function(){
//			$(this).show();
//		})
		
		thisObj.html("<i class='fa fa-angle-double-up'></i> 收 起");
		thisObj.val("0");
	}else{
		//$("#hideTable").hide();
		thisObj.html("<i class='fa fa-angle-double-down'></i> 展 开");
		thisObj.val("1");
	}
}
function showOrHide(){
	
	/*
	$("#hideTable").slideToggle(800,function(){
		var thisObj = $("#showOrHide");
		var thisObjTd = $("#showOrHide_td");
		var value = thisObj.attr("title");
		var pageDoc;
		if(value=="展开"){
			pageDoc = thisObjTd.html("<a class=\"easyui-linkbutton\" id=\"showOrHide\" title=\"收起\" href=\"javascript:void(0);\" onclick='showOrHide();'><i class='fa fa-angle-double-up'></i> 收 起</a>");
			thisObj.attr("title","收起");
		}else{
			//$("#hideTable").hide();
			pageDoc = thisObjTd.html("<a class=\"easyui-linkbutton\" id=\"showOrHide\" title=\"展开\" href=\"javascript:void(0);\" onclick='showOrHide();'><i class='fa fa-angle-double-down'></i> 展 开</a>");
			thisObj.attr("title","展开");
		}
		//$.parser.parse($("#showOrHide_td"));
		$.parser.parse(pageDoc);
		
//		$("#common_case_index_datagrid").datagrid("reload");
//        $('#common_case_index_datagrid').datagrid("clearSelections");
	});
*/
}

$("#showOrHide").on("click",function(){
	if($('.tabshow').hasClass("fa-angle-down")){
		$('.tabshow').removeClass("fa-angle-down");
		$('.tabshow').addClass("fa-angle-up");
		$('.showtext').text("收起筛选");
	} else{
		$('.tabshow').removeClass("fa-angle-up");
		$('.tabshow').addClass("fa-angle-down");
		$('.showtext').text("高级筛选");
	}
	$("#hideTable").slideToggle(800);
});

function initRows(){
    $('#isShow').combobox({
        data: isShowColumn,
        valueField: 'codeNo',
        textField: 'codeName',
        panelHeight: 'auto',
        panelMaxHeight : 200,
        multiple:true,
        editable:false,//是否可输入
        prompt : '请选择',
        /*onSelect: function(record){
            $.tabIndex = $('#'+ id).index(this);
            $('#'+ id).eq($.tabIndex).textbox("setValue", record.id);
        },*/
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
        },
        onChange: function(newValue, oldValue){
        	var newValues = newValue.toString();
            var oldValues = oldValue.toString();
            var values;
        	if(newValues.length > oldValues.length){
            	oldValues = oldValues.split(",");
            	for(var i = 0; i < oldValues.length; i++){
                	newValues = newValues.replace(oldValues[i],'');
                	newValues = newValues.replace(',','');
                }
            	$('#common_case_index_datagrid').datagrid('showColumn', newValues);
            }else{
            	newValues = newValues.split(",");
            	for(var i = 0; i < newValues.length; i++){
            		oldValues = oldValues.replace(newValues[i],'');
            		oldValues = oldValues.replace(',','');
                }
            	$('#common_case_index_datagrid').datagrid('hideColumn', oldValues);
            }
        }
    });
}

function thisRemove(thisTag){
	$("#"+thisTag+"Tag").hide();
	$("#"+thisTag).val("");
	commonCaseSearch();
}

var punishState = [
	{codeNo:'0', codeName: '全部'},
    {codeNo:'1', codeName: '作出处罚'},
    {codeNo:'2', codeName: '不予处罚'},
    {codeNo:'3', codeName: '撤销立案'}
]

var punishExecutState = [
	{codeNo:'0', codeName: '全部'},
	{codeNo:'1', codeName: '执行处罚'},
	{codeNo:'2', codeName: '撤销处罚'},
	{codeNo:'3', codeName: '案件终结'}
]

function initPunishState(){
    $('#punishState').combobox({
        data: punishState,
        valueField: 'codeNo',
        textField: 'codeName',
        panelHeight: 'auto',
        panelMaxHeight : 200,
        multiple:false,
        editable:false,//是否可输入
        onChange: function(value){
        	var punishDateName='';
        	if(value=='0'){
        		$('#beginpunishDate').datebox({ disabled: true }); 
        		$('#endpunishDate').datebox({ disabled: true }); 
        	}else{
        		$('#beginpunishDate').datebox({ disabled: false }); 
        		$('#endpunishDate').datebox({ disabled: false }); 
        	}
        	if(value=='1'){
        		punishDateName='决定书日期：';
        		$('#punishExecutState').combobox({ disabled: false }); 
        		$('#beginpunishExecutDate').datebox({ disabled: false }); 
        		$('#endpunishExecutDate').datebox({ disabled: false }); 
        	}else if(value=='2'){
        		punishDateName='不予处罚日期：';
        		$('#punishExecutState').combobox({ disabled: true }); 
        		$('#beginpunishExecutDate').datebox({ disabled: true }); 
        		$('#endpunishExecutDate').datebox({ disabled: true }); 
        	}else if(value=='3'){
        		punishDateName='撤销立案日期：';
        		$('#punishExecutState').combobox({ disabled: true }); 
        		$('#beginpunishExecutDate').datebox({ disabled: true }); 
        		$('#endpunishExecutDate').datebox({ disabled: true }); 
        	}
        	$("#punishDateName").html(punishDateName);
        },
        onLoadSuccess: function(data) {
        	$(this).combobox('setValue','0');
        }
    });
}

function initpunishExecutState(){
    $('#punishExecutState').combobox({
        data: punishExecutState,
        valueField: 'codeNo',
        textField: 'codeName',
        panelHeight: 'auto',
        panelMaxHeight : 200,
        multiple:false,
        editable:false,//是否可输入
        onChange: function(value){
        	var punishExecutDateName='';
        	if(value=='0'){
        		$('#beginpunishExecutDate').datebox({ disabled: true }); 
        		$('#endpunishExecutDate').datebox({ disabled: true }); 
        	}else{
        		$('#beginpunishExecutDate').datebox({ disabled: false }); 
        		$('#endpunishExecutDate').datebox({ disabled: false }); 
        	}
        	if(value=='1'){
        		punishExecutDateName='决定执行日期：';
        	}else if(value=='2'){
        		punishExecutDateName='撤销处罚日期：';
        	}else if(value=='3'){
        		punishExecutDateName='终结日期：';
        	}
        	$("#punishExecutDateName").html(punishExecutDateName);
        },
        onLoadSuccess: function(data) {
        	$(this).combobox('setValue','0');
        }
    });
}

/**
 * 初始化可选择列
 */
/*function initRows(){
    var params = {
        tableName : 'TBL_CASE_COMMON_BASE',
        isRegular : 0,
        isShow : 1
    };
    var json = tools.requestJsonRs("/caseCommonDataCtrl/findListBypage.action", params);
    if(json.rtState) {
        var panelHeight = 'auto';
        if(json.rtData.length > 6){
            panelHeight = '200';
        }
        $('#isShow').combobox({
            data: json.rtData,
            valueField: 'field',
            textField: 'comments',
            panelHeight: 'auto',
            panelMaxHeight : 200,
            multiple:true,
            editable:false,//是否可输入
            prompt : '请选择',
            onSelect: function(record){
                $.tabIndex = $('#'+ id).index(this);
                $('#'+ id).eq($.tabIndex).textbox("setValue", record.id);
            },
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
            },
            onChange: function(newValue, oldValue){
            	var newValues = newValue.toString();
                var oldValues = oldValue.toString();
                var values;
            	if(newValues.length > oldValues.length){
            		oldValues = oldValues.split(",");
            		for(var i = 0; i < oldValues.length; i++){
                		newValues = newValues.replace(oldValues[i],'');
                		newValues = newValues.replace(',','');
                	}
            		$('#common_case_index_datagrid').datagrid('showColumn', newValues);
            	}else{
            		newValues = newValues.split(",");
            		for(var i = 0; i < newValues.length; i++){
            			oldValues = oldValues.replace(newValues[i],'');
            			oldValues = oldValues.replace(',','');
                	}
            		$('#common_case_index_datagrid').datagrid('hideColumn', oldValues);
            	}
            	
            }
        });
    }
}*/