var year = '';
var params = '';
var caseSourceJson = []; //定义案件来源信息对象
var personIds = []; //定义人员ID对象
var personJson = [];//定义人员对象
var caseId = $('#comm_case_add_filing_caseId').val(); //案件ID
var editFlag = $('#common_case_add_filing_editFlag').val(); //编辑标识（1新增，2修改，3查看）
var isNext = $('#common_case_add_filing_isNext').val();//tab页签标识（立案0,调查取证1,处罚决定2,处罚执行3,结案4）
var subejctId = $('#common_case_add_filing_subjectId').val();//主体ID
var filingModelId = $('#common_case_add_filing_modelId').val();//弹框ID
var subJson = [];//主体对象json
var deptJson = [];//系统对象json

var params;
//可选列
var listdata = {
		isShowColumn: [
		    {codeNo:"sex",codeName:"性别"},
		    {codeNo:"birthStr",codeName:"出生年月"},
		    {codeNo:"politive",codeName:"政治面貌"},
		    {codeNo:"nation",codeName:"民族"},
		    {codeNo:"jobClass",codeName:"职级"},
		    {codeNo:"education",codeName:"学历"},
		    {codeNo:"cityGettimeStr",codeName:"地方证件申领日期"},
		    {codeNo:"effectiveTimeStr",codeName:"地方证件有效截止日期"},
		    {codeNo:"departmentGettimeStr",codeName:"行业证件申领日期"},
		    {codeNo:"deptEffectiveTimeStr",codeName:"行业证件有效截止日期"},
		    {codeNo:"telephone",codeName:"联系方式"},
		    {codeNo:"username",codeName:"账号"},
		    {codeNo:"punishNum",codeName:"行政处罚执法量"}
		]
	};

var i;
if(listdata.isShowColumn.length%7==0){
	i=listdata.isShowColumn.length/7;
}else {i=Math.ceil(listdata.isShowColumn.length/7)}

var widthnum = i*190+10;

var width = widthnum+'px';

$(".panList").css("width",width);

/*$(".isshow").on("click",function(){
	$(".panList").toggle();
});*/
$(".isshow").on("click",function(){
	$(".panList").show();
	$("body").append('<div id="panListBack" class=""></div>');

});

$("body").delegate("#panListBack","click",function(){
	$(".panList").hide();
	$("#panListBack").remove();
	});
var temp = [];

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
	} else{
		$(th).children('i').addClass("fa-check");
		$('#datagrid').datagrid('showColumn', code);
		temp.push(code);
	}
}

var tpl=[
	'{@each isShowColumn as it}',
	'<li onclick="detail(\'${it.codeNo}\',this)" title="${it.codeName}"><i class="fa"></i>${it.codeName}</li>',
	'{@/each}'
   ].join('\n');


/**
 * 默认加载方法
 * @returns
 */
function doInit(){
    //easyui-panel适应父容器大小
    //initCommonCaseIsFilingLegalReview();//是否法制审查
	limitInit();
    getUserSubjetAndDepartment();//获取登录信息，加载主体，和所属系统
    datagrid = $('#datagrid') .datagrid( {
		view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar : '#toolbar',// 工具条对象
	});
    $(".panList").append(juicer(tpl,listdata));
//    listDatagrid(0);
}

/*
 * 初始化办案信息：经办案件量
 */
function limitInit(){
	$('#beginInspNum').next('span').find('input').focus(
		function (){
			year = $('input[name="year"]:checked').val();
			if(year==null || year==''){
				$('#beginInspNum').textbox('readonly',true);	
				$.MsgBox.Alert_auto("请先选择案件年份");
			}else {
				$('#beginInspNum').textbox('readonly',false);
				caseBeginNumCheck("InspNum");
			}
		}	
	);
	$('#endInspNum').next('span').find('input').focus(
			function (){
				year = $('input[name="year"]:checked').val();
				if(year==null || year==''){
					$('#endInspNum').textbox('readonly',true);	
					$.MsgBox.Alert_auto("请先选择案件年份");
				}else {
					$('#endInspNum').textbox('readonly',false);
					caseEndNumCheck("InspNum");
				}
			}	
		);
	$('#beginCaseNum').next('span').find('input').focus(
			function (){
				year = $('input[name="year"]:checked').val();
				if(year==null || year==''){
					$('#beginCaseNum').textbox('readonly',true);	
					$.MsgBox.Alert_auto("请先选择案件年份");
				}else {
					$('#beginCaseNum').textbox('readonly',false);
					caseBeginNumCheck("CaseNum");
				}
			}	
		);
	$('#endCaseNum').next('span').find('input').focus(
			function (){
				year = $('input[name="year"]:checked').val();
				if(year==null || year==''){
					$('#endCaseNum').textbox('readonly',true);	
					$.MsgBox.Alert_auto("请先选择案件年份");
				}else {
					$('#endCaseNum').textbox('readonly',false);
					caseEndNumCheck("CaseNum");
				}
			}	
		);
	$('#beginCoercionNum').next('span').find('input').focus(
			function (){
				year = $('input[name="year"]:checked').val();
				if(year==null || year==''){
					$('#beginCoercionNum').textbox('readonly',true);	
					$.MsgBox.Alert_auto("请先选择案件年份");
				}else {
					$('#beginCoercionNum').textbox('readonly',false);
					caseBeginNumCheck("CoercionNum");
				}
			}	
		);
	$('#endCoercionNum').next('span').find('input').focus(
			function (){
				year = $('input[name="year"]:checked').val();
				if(year==null || year==''){
					$('#endCoercionNum').textbox('readonly',true);	
					$.MsgBox.Alert_auto("请先选择案件年份");
				}else {
					$('#endCoercionNum').textbox('readonly',false);
					caseEndNumCheck("CoercionNum");
				}
			}	
		);
}
//案件数量校验
function caseBeginNumCheck(str){
	
	var beginInspNum = '';
	var endInspNum = '';
	$('#begin'+str).textbox({
		onChange:function(){
			beginInspNum = $('#begin'+str).textbox('getText');
			endInspNum = $('#end'+str).textbox('getText');
			if(beginInspNum!=''&& beginInspNum!=null){
				if(/^\d+$/.test(beginInspNum)){
					if(endInspNum!=''&&endInspNum!=null){
						if( parseInt(endInspNum)< parseInt(beginInspNum)){
							$.MsgBox.Alert_auto("输入的值大于最大值，请重新输入");
							$('#begin'+str).textbox('setText','');
						}
					}
				}else{
					$.MsgBox.Alert_auto("请输入数字");
					$('#begin'+str).textbox('setText','');
				}
			}
		}
	})
}
function caseEndNumCheck(str){
	
	var beginInspNum = '';
	var endInspNum = '';
	$('#end'+str).textbox({
		onChange:function(){
			beginInspNum = $('#end'+str).textbox('getText');
			endInspNum = $('#begin'+str).textbox('getText');
			if(beginInspNum!=''&& beginInspNum!=null){
				if(/^\d+$/.test(beginInspNum)){
					if(endInspNum!=''&&endInspNum!=null){
						if( parseInt(endInspNum)> parseInt(beginInspNum)){
							$.MsgBox.Alert_auto("输入的值小与最小值，请重新输入");
							$('#end'+str).textbox('setText','');
						}
					}
				}else{
					$.MsgBox.Alert_auto("请输入数字");
					$('#end'+str).textbox('setText','');
				}
			}
		}
	})
}
/**
 * 获取登录信息，加载主体，和所属系统
 * @returns
 */
function getUserSubjetAndDepartment(){
    var json = tools.requestJsonRs("/commonCtrl/getRelations.action");
    if(json.rtState){
        if(json.rtData != null && json.rtData != ''){
            subJson = [];//主体对象json
            deptJson = [];//系统对象json
            var subArr = []; //主体去重
            var deptArr = [];//部门去重
            for(var i=0; i < json.rtData.length; i++){
                var sub = {}; //主体对象
                var dept = {};//系统对象
                sub.codeNo = json.rtData[i].businessSubjectId;
                sub.codeName = json.rtData[i].businessSubjectName;
                dept.codeNo = json.rtData[i].businessDeptId;
                dept.codeName = json.rtData[i].businessDeptName;
                if(subArr.indexOf(sub.codeNo) == -1){
                    //不存在，则存入数组
                    subArr.push(sub.codeNo);
                    subJson.push(sub);
                }
                if(deptArr.indexOf(dept.codeNo) == -1){
                    //不存在，则存入数组
                    deptArr.push(dept.codeNo);
                    deptJson.push(dept);
                }
            }
            initCommonCaseSelectJson('subjectId', subJson);
            /*initCommonCaseSelectJson('departmentId', deptJson);*/
            $('#departmentId').val(deptJson[0].codeNo);//默认选中第一个部门
            $('#departmentId_label').html(deptJson[0].codeName);//默认选中第一个部门
        }
    }
}

/**
 * 查看方法 查询执法主体
 * @returns
 */
function getSubjectList(params){
    var json = tools.requestJsonRs("/caseCommonBaseCtrl/selectSubjectList.action?id="+params);
    if(json.rtState){
        if(json.rtData != null && json.rtData != ''){
            initCommonCaseSelectJson('subjectId', json.rtData);
        }
    }
}

/**
 * 查看方法 查询执法部门
 * @returns
 */
function getDepartmentList(params){
    var json = tools.requestJsonRs("/caseCommonBaseCtrl/selectDepartmentList.action?id="+params);
    if(json.rtState){
        if(json.rtData != null && json.rtData != ''){
            initCommonCaseSelectJson('departmentId', json.rtData);
        }
    }
}

/**
 * 进入综合查询界面
 * @returns
 */
function officialsSearch(){
	$("#searchPage").attr("style","display:none;");
	$("#indexPage").removeAttr("style","display:none;");

	var sex = '';
	var sexStr = '';
	var politive = '';
	var politiveStr = '';
	var nation = '';
	var nationStr = '';
	var education = '';
	var educationStr = '';
	var jobClass = '';
	var jobStr = '';
	var codeType = '';
	var codeTypeStr = '';
	var isGetCode = '';
	var isGetCodeStr = '';
	
	if($("input[name='sex']:checked").length >0){
		$("input[name='sex']:checked").each(function(){
			sex += this.value + ',';
			if(this.value=="01"){
				sexStr += "男" + '，';
			}else if (this.value=="02"){
				sexStr += "女" + '，';
			}
		})
	}
	if($("input[name='politive']:checked").length >0){
		$("input[name='politive']:checked").each(function(){
			politive += this.value + ',';
			if(this.value=="01"){
				politiveStr += "中共党员" + '，';
			}else if (this.value=="02,03,04"){
				politiveStr += "非中共" + '，';
			}
		})
	}
	if($("input[name='nation']:checked").length >0){
		$("input[name='nation']:checked").each(function(){
			nation += $(this).val();
			if(this.value=="01"){
				nationStr += "汉族" + '，';
			}else if (this.value=="02"){
				nationStr += "少数民族" + '，';
			}
		})
	}
	if($("input[name='education']:checked").length >0){
		$("input[name='education']:checked").each(function(){
			education += this.value + ',';
			if(this.value=="01,02"){
				educationStr += "研究生" + '，';
			}else if (this.value=="03"){
				educationStr += "大学" + '，';
			}else if (this.value=="04"){
				educationStr += "大专" + '，';
			}else if (this.value=="05"){
				educationStr += "高中" + '，';
			}else if (this.value=="06"){
				educationStr += "初中" + '，';
			}else if (this.value=="99"){
				educationStr += "其他" + '，';
			}
		})
	}
	if($("input[name='jobClass']:checked").length >0){
		$("input[name='jobClass']:checked").each(function(){
			jobClass += this.value + ',';
			if(this.value=="10"){
				jobStr += "办事员" + '，';
			}else if (this.value=="09"){
				jobStr += "科员" + '，';
			}else if (this.value=="07,08"){
				jobStr += "科级" + '，';
			}else if (this.value=="05,06"){
				jobStr += "处级" + '，';
			}else if (this.value=="03,04"){
				jobStr += "厅局级及以上" + '，';
			}else if (this.value=="99"){
				jobStr += "其他" + '，';
			}
		})
	}
	if($("input[name='codeType']:checked").length >0){
		$("input[name='codeType']:checked").each(function(){
			codeType += this.value + ',';
			if(this.value=="001"){
				codeTypeStr += "仅地方证件" + '，';
			}else if (this.value=="002"){
				codeTypeStr += "仅行业证件" + '，';
			}else if (this.value=="000"){
				codeTypeStr += "双证" + '，';
			}
		})
	}
	if($("input[name='isGetCode']:checked").length >0){
		$("input[name='isGetCode']:checked").each(function(){
			isGetCode += this.value + ',';
			if(this.value=="1"){
				isGetCodeStr += "是" + '，';
			}else if (this.value=="0"){
				isGetCodeStr += "否" + '，';
			}
		})
	}

	params = {name:$("#name").val(),
			sex:sex,
			politive:politive,
			nation:nation,
			education:education,
			jobClass:jobClass,
			codeType:codeType,
			isGetCode:isGetCode,
			
			
			sexStr:sexStr,
			politiveStr:politiveStr,
			nationStr:nationStr,
			educationStr:educationStr,
			jobStr:jobStr,
			codeTypeStr:codeTypeStr,
			isGetCodeStr:isGetCodeStr,
			//单选按钮
			year:$('input[name="year"]:checked').val(),
			beginInspNum:$('#beginInspNum').textbox('getText'),
			endInspNum:$("#endInspNum").textbox('getText'),
			beginCaseNum:$("#beginCaseNum").textbox('getText'),
			endCaseNum:$("#endCaseNum").textbox('getText'),
			beginCoercionNum:$("#beginCoercionNum").textbox('getText'),
			endCoercionNum:$("#endCoercionNum").textbox('getText'),
	};		
	listDatagrid(params);
	isTerm();
//	location.href = '/supervise/officials/officialsSearch/officials_index.jsp?'+$.param(params);
}
/**
 * 重置表单
 */
function officialsReSet(){
    $('.easyui-radiobutton').radiobutton({checked: false});
    $('.easyui-checkbox').checkbox({checked: false});
    $('.easyui-textbox').textbox('setValue', '');
}


function listDatagrid(params){
	datagrid = $('#datagrid') .datagrid( {
		url : contextPath + '/searchOfficials/findSearchBypageQuery.action',
		queryParams : params,
		pagination : true,
		singleSelect : true,
		striped: true,
		pageSize : 20,
        pageList : [ 10, 20, 50, 100 ],
		view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar : '#toolbar',// 工具条对象
		border : false,
		rownumbers : false,
		fit : true,
		idField : 'id',// 主键列
		fitColumns : true,// 列是否进行自动宽度适应
		frozenColumns: [ [
			//{ field : 'id', title : '', checkbox : true},
			{field:'ID',title:'序号',align:'center',
			    formatter:function(value,rowData,rowIndex){
			        return rowIndex+1;
			        console.log(rowData);
			    }
			},
			{ field : 'name', title : '姓名', width : '20%', halign: 'center',align: 'center',
				formatter:function(value,rowData,rowIndex){
					var optsStr = "<a href=\"#\" title=" + value + " onclick=\"look('" + rowData.id + "')\">" + value + "</a>";
					return optsStr;
				} 
			},
			       ] ],
		columns : [ [
				{ field : 'cityCode', title : '地方证件号', width : '20%',align:'center' , halign: 'center',hidden:false },
				{ field : 'departmentCode', title : '行业证件号', width : '20%',align:'center' , halign: 'center',hidden:false },
				{ field : 'businessSubName', title : '所属主体', width : '40%',align:'left' , halign: 'center',hidden:false, formatter: 
					function(e, rowData) {
						if(rowData.businessSubName == null){
							return "";
						}else{
							var lins = "<span title=" + rowData.businessSubName + ">" + rowData.businessSubName + "</span>"
			                return lins;
						}
	            	},
	            },
				{ field : 'sex', title : '性别', width : '10%',align:'center' , halign: 'center',hidden:true ,formatter: 
					function(e, rowData) {
						if (rowData.sex == '01') {
							return "男";
						}else {
							return "女";
						}
					}
				},
				{ field : 'birthStr', title : '出生年月', width : '10%',align:'center' , halign: 'center',hidden:true },
				{ field : 'politive', title : '政治面貌', width : '10%',align:'center' , halign: 'center',hidden:true },
				{ field : 'nation', title : '民族', width : '10%',align:'center' , halign: 'center',hidden:true },
				{ field : 'jobClass', title : '职级', width : '20%',align:'center' , halign: 'center',hidden:true },
				{ field : 'education', title : '学历', width : '10%',align:'center' , halign: 'center',hidden:true },
				{ field : 'telephone', title : '联系方式', width : '10%',align:'center' , halign: 'center',hidden:true },
				{ field : 'username', title : '账号', width : '10%',align:'left' , halign: 'center' ,hidden:true},
//				{ field : 'id', title : '', checkbox : true},
	            { field : 'cityGettimeStr', title : '地方证件申领日期', width : '10%',align:'center' , halign: 'center',hidden:true },
				{ field : 'effectiveTimeStr', title : '地方证件有效截止日期', width : '10%',align:'center' , halign: 'center',hidden:true },
				{ field : 'departmentGettimeStr', title : '行业证件申领日期', width : '10%',align:'center' , halign: 'center',hidden:true },
				{ field : 'deptEffectiveTimeStr', title : '行业证件有效截止日期', width : '10%',align:'center' , halign: 'center',hidden:true },
				{ field : 'punishNum', title : '行政处罚执法量', width : '10%',align:'center' , halign: 'center',hidden:true },
				] ],
		singleSelect : false,
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
//初始化已选标签
function isTerm(){
	console.log(params);
	if(params.name != null && params.name != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"nameTag\" title=\"姓名："+params.name+"\">&nbsp;姓名<a href=\"javascript:;\" onclick=\"thisRemove('name')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(params.sex != null && params.sex != ""){
		var $table= $("#condition");
		var str = params.sexStr;
		var sexStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" id=\"sexTag\" title=\"性别："+sexStr+"\">&nbsp;性别<a href=\"javascript:;\" onclick=\"thisRemove('sex')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(params.politive != null && params.politive != ""){
		var $table= $("#condition");
		var str = params.politiveStr;
		var politiveStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" id=\"politiveTag\" title=\"政治面貌："+politiveStr+"\">&nbsp;政治面貌<a href=\"javascript:;\" onclick=\"thisRemove('politive')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(params.nation != null && params.nation != ""){
		var $table= $("#condition");
		var str = params.nationStr;
		var nationStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" id=\"nationTag\" title=\"民族："+nationStr+"\">&nbsp;民族<a href=\"javascript:;\" onclick=\"thisRemove('nation')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(params.education != null && params.education != ""){
		var $table= $("#condition");
		var str = params.educationStr;
		var educationStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" id=\"educationTag\" title=\"学历："+educationStr+"\">&nbsp;学历<a href=\"javascript:;\" onclick=\"thisRemove('education')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(params.jobClass != null && params.jobClass != ""){
		var $table= $("#condition");
		var str = params.jobStr;
		var jobStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" id=\"jobClassTag\" title=\"职级："+jobStr+"\">&nbsp;职级<a href=\"javascript:;\" onclick=\"thisRemove('jobClass')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(params.codeType != null && params.codeType != ""){
		var $table= $("#condition");
		var str = params.codeTypeStr;
		var codeTypeStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" id=\"codeTypeTag\" title=\"执法证件类型："+codeTypeStr+"\">&nbsp;执法证件类型<a href=\"javascript:;\" onclick=\"thisRemove('codeType')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(params.isGetCode != null && params.isGetCode != ""){
		var $table= $("#condition");
		var str = params.isGetCodeStr;
		var isGetCodeStr = (str.substring(str.length-1)=='，')?str.substring(0,str.length-1):str
		var vTr= "<span class=\"tagbox-label\" id=\"isGetCode\" title=\"是否分配账号："+isGetCodeStr+"\">&nbsp;是否分配账号<a href=\"javascript:;\" onclick=\"thisRemove('isGetCode')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(params.year != null && params.year != ""){
		if(params.year == 1){
			var $table= $("#condition");
			var vTr= "<span class=\"tagbox-label\" id=\"yearTag\" title=\"案件年份：本年度\">&nbsp;案件年份<a href=\"javascript:;\" onclick=\"thisRemove('year')\" class=\"tagbox-remove\"></a></span>";
			$table.append(vTr);
		}else{
			var $table= $("#condition");
			var vTr= "<span class=\"tagbox-label\" id=\"yearTag\" title=\"案件年份：上年度\">&nbsp;案件年份<a href=\"javascript:;\" onclick=\"thisRemove('year')\" class=\"tagbox-remove\"></a></span>";
			$table.append(vTr);
		}
	}
	if(params.beginInspNum != null && params.beginInspNum != "" && params.endInspNum != null && params.endInspNum != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"InspNum\" title=\"经办案件量(行政检查)："+params.beginInspNum+"-"+params.endInspNum+"\">&nbsp;经办案件量(行政检查)<a href=\"javascript:;\" onclick=\"thisRemoveTwo('InspNum')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}else if(params.beginInspNum != null && params.beginInspNum != ""&&(params.endInspNum == null || params.endInspNum == "")){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"beginInspNumTag\" title=\"经办案件量(行政检查)：>="+params.beginInspNum+"\">&nbsp;经办案件量(行政检查)<a href=\"javascript:;\" onclick=\"thisRemove('beginInspNum')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}else if((params.beginInspNum == null || params.beginInspNum == "")&&params.endInspNum != null && params.endInspNum != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"endInspNumTag\" title=\"经办案件量(行政检查)：<"+params.endInspNum+"\">&nbsp;经办案件量(行政检查)<a href=\"javascript:;\" onclick=\"thisRemove('endInspNum')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(params.beginCaseNum != null && params.beginCaseNum != ""&&params.endCaseNum != null && params.endCaseNum != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"CaseNum\" title=\"经办案件量(行政处罚)："+params.beginCaseNum+"-"+params.endCaseNum+"\">&nbsp;经办案件量(行政处罚)<a href=\"javascript:;\" onclick=\"thisRemoveTwo('CaseNum')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(params.beginCaseNum != null && params.beginCaseNum != ""&&(params.endCaseNum == null || params.endCaseNum == "")){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"beginCaseNumTag\" title=\"经办案件量(行政处罚)：>="+params.beginCaseNum+"\">&nbsp;经办案件量(行政处罚)<a href=\"javascript:;\" onclick=\"thisRemove('beginCaseNum')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if((params.beginCaseNum == null || params.beginCaseNum == "")&&params.endCaseNum != null && params.endCaseNum != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"endCaseNumTag\" title=\"经办案件量(行政处罚)：<"+params.endCaseNum+"\">&nbsp;经办案件量(行政处罚)<a href=\"javascript:;\" onclick=\"thisRemove('endCaseNum')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(params.beginCoercionNum != null && params.beginCoercionNum != ""&&params.endCoercionNum != null && params.endCoercionNum != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"CoercionNum\" title=\"经办案件量(行政强制)："+params.beginCoercionNum+"-"+params.endCoercionNum+"\">&nbsp;经办案件量(行政强制)<a href=\"javascript:;\" onclick=\"thisRemoveTwo('CoercionNum')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if(params.beginCoercionNum != null && params.beginCoercionNum != ""&&(params.endCoercionNum == null || params.endCoercionNum == "")){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"beginCoercionNumTag\" title=\"经办案件量(行政强制)：>="+params.beginCoercionNum+"\">&nbsp;经办案件量(行政强制)<a href=\"javascript:;\" onclick=\"thisRemove('beginCoercionNum')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
	if((params.beginCoercionNum == null || params.beginCoercionNum == "")&&params.endCoercionNum != null && params.endCoercionNum != ""){
		var $table= $("#condition");
		var vTr= "<span class=\"tagbox-label\" id=\"endCoercionNumTag\" title=\"经办案件量(行政强制)：<"+params.endCoercionNum+"\">&nbsp;经办案件量(行政强制)<a href=\"javascript:;\" onclick=\"thisRemove('endCoercionNum')\" class=\"tagbox-remove\"></a></span>";
		$table.append(vTr);
	}
}

//标签删除
function thisRemove(thisTag){
	if(params == ""){
		var dataPar = tools.formToJson($("#form1"));
		params = dataPar
	}else{
		params = params;
	}
	if(thisTag=="year"){
		$("#InspNum").hide();
		$("#beginInspNumTag").hide();
		$("#endInspNumTag").hide();
		params[beginInspNum] = "";
		params[endInspNum] = "";
		$("#CaseNum").hide();
		$("#beginCaseNumTag").hide();
		$("#endCaseNumTag").hide();
		params[beginCaseNum] = "";
		params[endCaseNum] = "";
		$("#CoercionNum").hide();
		$("#beginCoercionNumTag").hide();
		$("#endCoercionNumTag").hide();
		params[beginCoercionNum] = "";
		params[endCoercionNum] = "";
	}
	//删除标签
	$("#"+thisTag+"Tag").hide();
	params[thisTag] = "";
	$('#datagrid').datagrid("reload",params);
	$('#datagrid').datagrid("clearSelections");
}

function thisRemoveTwo(thisNum){
	if(params == ""){
		var dataPar = tools.formToJson($("#form1"));
		params = dataPar
	}else{
		params = params;
	}
	//删除标签
	$("#"+thisNum).hide();
	params["begin"+thisNum] = "";
	params["end"+thisNum] = "";
	$('#datagrid').datagrid("reload",params);
	$('#datagrid').datagrid("clearSelections");
}
/**
 * 返回查询条件页面
 */
function officialsReturn() {
	params = tools.formToJson($("#officials_index_form"));
//	console.log(params);
//	debugger;
	$("#condition").empty();
	$("#searchPage").removeAttr("style","display:none;");
	$("#indexPage").attr("style","display:none;");
}
/**
 * 导出
 */
function officialsExport(){
	var obj = temp.join(",");
	if (window.confirm("确定导出所有数据？")) {
		var json = tools.requestJsonRs("/searchOfficials/exportOfficials.action?term="+obj);
		console.log(json);
		if(json.rtData < 1001){
			location.href = '/searchOfficials/exportOfficials.action?isTrue=1&term='+obj;
		}else{
			alert("导出数据过大，请精确查询后再操作（导出数据限制：1000）");
		}
	}
}


function look(id) {
	top.bsWindow(contextPath + "/supervise/officials/officials_search_look.jsp?id="+id, "查看",
			{
				width : "700",
				height : "420",
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
