var deptId = "";
var sub = "";
var busId = "";
var isSubId = "";//有部门有主体为1
var subId = "";
var code_data = [];//执法证返回值
var code_delete_data = [];//执法证删除
var param_code;//执法证返回值
var datagrid;
function doInit(){
	nationInit();
	politiveInit();
	var json = tools.requestJsonRs("/OfficialsCtrl/getOrgSystemByCurrentPerson.action");
	//获取主体信息 id为1代表为空
	var subName = json.rtData.businessSubName;
	subId = json.rtData.businessSubId;
	//获取部门信息  
	deptId = json.rtData.deptId;
	var deptName = json.rtData.deptName;
	//无部门禁填
	if(deptId == '1'){
		dis();
	}else{//有部门   有主体直接填入，无主体获取部门下的主体
		if(subId == '1'){
			deptSubject();
		}else{
			$('#businessSubName').textbox('setValue',subName);
			$('#businessSubName').textbox('textbox').attr('readonly',true);
			isSubId = 1;
			sub = json.rtData.businessSubId;
			//处理附件
				var json = tools.requestJsonRs(contextPath + "/SupPersonController/getFilelistById.action",{id:id});
			var attachModels = json.rtData;
			if(attachModels != null){
			    for(var i=0;i<attachModels.length;i++){
			        attachModels[i].priv = 1+2+4+8+16;
			        var attachElement = tools.getAttachElement(attachModels[i]);
			        $("#attachDiv").append(attachElement);
			    }
			}
		}
	}
	//根据身份证获取性别
	personIdToSex();
	
	if(id!=0){//编辑
		var json = tools.requestJsonRs("/OfficialsCtrl/get.action",{id:id});
		bindJsonObj2Easyui(json.rtData , "form1");
 		busId = json.rtData.businessSubId;
 		if(json.rtData.sex == '01'){
 			$('#sex').textbox('setValue','男');
 		}else{
 			$('#sex').textbox('setValue','女');
 		}
 		//委托组织
		$('#entrustId').combobox({
			prompt:'输入关键字后自动搜索',
			mode:'remote',
			url:contextPath + '/subjectCtrl/getSysCodeSub.action?subId='+subId+'&id='+json.rtData.entrustId,
			valueField:'id',
			textField:'subName',
			multiple:false,
			method:'post',
			panelHeight:'100px',
			//label: 'Language:',
			labelPosition: 'top',
			onClick:function(row){
				ComboboxCommonProcess($(this));
			},
			onHidePanel:function(){
			var _options = $(this).combobox('options');
		    var _data = $(this).combobox('getData');/* 下拉框所有选项 */
		    var _value = $(this).combobox('getValue');/* 用户输入的值 */
		    var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
		    for (var i = 0; i < _data.length; i++) {
		        if (_data[i][_options.valueField] == _value) {
		            _b = true;
		            break;
		        }
		    }
		    if (!_b) {
		        $(this).combobox('setValue', '');
		    }
		}
		});
	}else{//新增
		//委托组织
		Org();
	}
	educationInit();
	jobClassInit();
	personTypeInit();
//	doInitMultipleUpload();
//	jsonArray = ajax('getCerts');
	datagrate();
	//获取执法证信息
	var jsonPersonCode = tools.requestJsonRs("/personGetcodeCtrl/get.action",{id:id});
	for(var i=0;i < jsonPersonCode.rtData.length;i++){
		code_data.push(jsonPersonCode.rtData[i]);
		$("#datagrid").datagrid("loadData",code_data);
	}
}
//根据身份证获取性别和出生日期
function personIdToSex(){
	$('#personId').textbox({   
		"onChange":function(){
			var age = document.getElementById("personId").value;
			if (age.length == 18){
				if (parseInt(age.substr(16, 1)) % 2 == 0) {     
			        $('#sex').textbox('setValue','女');
				}else{                               
			        $('#sex').textbox('setValue','男');
				}
				birthday = age.substr(6,8);
				birthday = birthday.replace(/(.{4})(.{2})/,"$1-$2-");
				$('#birthStr').textbox('setValue',birthday);
			}
			else if (age.length == 15){
				if (parseInt(age.substr(14, 1)) % 2 == 0) {     
			        $('#sex').textbox('setValue','女');
				}else{                               
			        $('#sex').textbox('setValue','男');
				}
				birthday = "19"+age.substr(6,6);
				birthday = birthday.replace(/(.{4})(.{2})/,"$1-$2-");
				$('#birthStr').textbox('setValue',birthday);
			}else{
				$.MsgBox.Alert_auto("身份证号码位数错误");
			}
		}
	});
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
function save(){
	if($('#form1').form('enableValidation').form('validate')){
		var param = tools.formToJson($("#form1"));
		//校验姓名
		var text = /^((([\u4e00-\u9fa5]+[·]{0,10}[\u4e00-\u9fa5]){0,50})|([a-zA-Z]{2,50}))$/;
		var flag = text.test(param.name);
		if(!flag){
			$.MsgBox.Alert_auto("姓名输入不正确"); 
			return false;
		}
		//校验身份证
		var text = /^(([1][1-5])|([2][1-3])|([3][1-7])|([4][1-6])|([5][0-4])|([6][1-5])|([7][1])|([8][1-2]))\d{4}(([1][9]\d{2})|([2]\d{3}))(([0][1-9])|([1][0-2]))(([0][1-9])|([1-2][0-9])|([3][0-1]))\d{3}[0-9xX]$/;
		var flag = text.test(param.personId);
		if(!flag){
			$.MsgBox.Alert_auto("身份证号输入不正确");
			return false;
		}
		if($("#sex").val()=="男"){
			param.sex = '01';
		}else{
			param.sex = '02';
		}
		param.isDelete = 0;
		param.examine = 0;
		if(isSubId == 1){
			param.businessSubName = subId;
		}
		param.paramMaps = code_data;
		param.paramDeleteMaps = code_delete_data;
		//校验法律职业资格
		if(param.isLawcode =="" || param.isLawcode ==null){
			$.MsgBox.Alert_auto("请选择法律职业资格");
			return false;
		}
		//校验执法证
		if(param.paramMaps =="" || param.paramMaps ==null){
			$.MsgBox.Alert_auto("请至少填写一个执法证");
			return false;
		}
		//校验联系电话
		if($("#telephone").val()!=""){
			var text = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$|(^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$)/;
			var flag = text.test(param.telephone);
			if(!flag){
				$.MsgBox.Alert_auto("联系电话输入不正确");
				return false;
			}
		}
	    if(id!=0){//编辑
	    			param.businessSubId = busId;
		    		var json = tools.requestJsonRs("/OfficialsCtrl/update.action",param);
		    		if(json.rtMsg == 1){
						$.MsgBox.Alert_auto("该身份证号已存在");
					}
				    return json.rtState;
		}else{//新增
			var json = tools.requestJsonRs("/OfficialsCtrl/save.action",param);
			if(json.rtMsg == 1){
				$.MsgBox.Alert_auto("该身份证号已存在");
			}
		    return json.rtState;
		}
	}else{
		return false;
	}
}
function politiveInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "SYSTEM_CODE_POLITIVE"});
    if(json.rtState) {
        $('#politive').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'auto',
        });
    }
}
//民族
function nationInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "ENROLMENT_CODE_NATION"});
    if(json.rtState) {
        $('#nation').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'150px',
            onHidePanel:function(){
				var _options = $(this).combobox('options');
			    var _data = $(this).combobox('getData');/* 下拉框所有选项 */
			    var _value = $(this).combobox('getValue');/* 用户输入的值 */
			    var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
			    for (var i = 0; i < _data.length; i++) {
			        if (_data[i][_options.valueField] == _value) {
			            _b = true;
			            break;
			        }
			    }
			    if (!_b) {
			        $(this).combobox('setValue', '');
			    }
			}
        });
    }
}
//function sexInit(){
//	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "TEST_GENDER"});
//    if(json.rtState) {
//        $('#sex').combobox({
//            data: json.rtData,
//            valueField: 'codeNo',
//            textField: 'codeName',
//            panelHeight:'70px',
//        });
//    }
//}
function educationInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "SYSTEM_CODE_EDUCATION"});
    if(json.rtState) {
        $('#education').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'150px',
        });
    }
}
function jobClassInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "JOB_CLASS"});
    if(json.rtState) {
        $('#jobClass').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'150px',
        });
    }
}
function personTypeInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "PERSON_NATURE"});
    if(json.rtState) {
        $('#personType').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'auto',
        });
    }
}
//function show(checkbox){
//	if ( checkbox.checked == true){
//		document.getElementById("div1").style.display="block";		
//	}else{
//		document.getElementById("div1").style.display="none";
//	}	
//}
//获取部门下的主体
function deptSubject(){
	$('#businessSubName').combobox({
			prompt:'输入关键字后自动搜索',
			mode:'remote',
			url:contextPath + '/subjectCtrl/getSysOrgSub.action?id='+deptId,
			valueField:'id',
			textField:'subName',
			multiple:false,
			method:'post',
			panelHeight:'100px',
			//label: 'Language:',
			labelPosition: 'top',
			onClick:function(row){
				ComboboxCommonProcess($(this));
			},
			onHidePanel:function(){
			var _options = $(this).combobox('options');
		    var _data = $(this).combobox('getData');/* 下拉框所有选项 */
		    var _value = $(this).combobox('getValue');/* 用户输入的值 */
		    var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
		    for (var i = 0; i < _data.length; i++) {
		        if (_data[i][_options.valueField] == _value) {
		            _b = true;
		            break;
		        }
		    }
		    if (!_b) {
		        $(this).combobox('setValue', '');
		    }
		    busId = "";
		    sub = $(this).combobox('getValue');
		    if(sub != null){
		    	Org();
		    }
		    $('#entrustId').combobox('setValue', '');
		}
		
		});
	//处理附件
		var json = tools.requestJsonRs(contextPath + "/SupPersonController/getFilelistById.action",{id:id});
	var attachModels = json.rtData;
	if(attachModels != null){
	    for(var i=0;i<attachModels.length;i++){
	        attachModels[i].priv = 1+2+4+8+16;
	        var attachElement = tools.getAttachElement(attachModels[i]);
	        $("#attachDiv").append(attachElement);
	    }
	}
}
//设置控件为不可编辑
function dis(){
    var a = document.getElementsByTagName("input");   
    for(var i=0;i<a.length;i++){  
          if   (a[i].type=="checkbox"   ||   a[i].type=="radio" || a[i].type=="text"|| a[i].type=="button")     
        	  a[i].readOnly=true;   
    }
    
    //$('#sex').combobox({disabled:true});
    $('#politive').combobox({disabled:true});
    $('#nation').combobox({disabled:true});
    $('#education').combobox({disabled:true});
    $('#jobClass').combobox({disabled:true});
    
    var c = document.getElementsByTagName("select");   
    for (var i=0; i<c.length; i++){ 
    	//disabled="disabled"
          c[i].disabled="disabled";   
    }
}
//委托组织
function Org(){
	$('#entrustId').combobox({
		prompt:'输入关键字后自动搜索',
		mode:'remote',
		url:contextPath + '/subjectCtrl/getSysCodeSub.action?subId='+sub,
		valueField:'id',
		textField:'subName',
		multiple:false,
		method:'post',
		panelHeight:'100px',
		//label: 'Language:',
		labelPosition: 'top',
		onClick:function(row){
			ComboboxCommonProcess($(this));
		},
		onHidePanel:function(){
			var _options = $(this).combobox('options');
		    var _data = $(this).combobox('getData');/* 下拉框所有选项 */
		    var _value = $(this).combobox('getValue');/* 用户输入的值 */
		    var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
		    for (var i = 0; i < _data.length; i++) {
		        if (_data[i][_options.valueField] == _value) {
		            _b = true;
		            break;
		        }
		    }
		    if (!_b) {
		        $(this).combobox('setValue', '');
		    }
		}
	});
}
/**
 * 附件上传
 * @param model 后台文件路径文件夹
 * @param modelId 查询文件关键字
 * @param elemId 前台展示元素ID
 * @returns
 */
function doInitMultipleUpload() {
  //多附件快速上传
	swfUploadObj = new TeeSWFUpload({
		fileContainer:"fileContainer2",//文件列表容器
		renderContainer:"renderContainer2",//渲染容器
		uploadHolder:"uploadHolder2",//上传按钮放置容器
		valuesHolder:"attaches",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		renderFiles:true,//渲染附件
		post_params:{model:"officials"}//后台传入值，model为模块标志
	});
}
////行业执法证
//function codeTypeDept(checkbox){
//		if ( checkbox.checked == true){
//			$('#hangye').panel('open');
//		}else{
//			$('#departmentCode').textbox('setValue',"");
//			$('#departmentGettimeStr').datebox('setValue',"");
//			$('#deptGettimeBeginStr').datebox('setValue',"");
//			$('#deptGettimeEndStr').datebox('setValue',"");
//			$('#deptGettimeOffice').textbox('setValue',"");
//			$('#hangye').panel('close');
//		}	
//}
//function codeTypeCity(checkbox){
//	if ( checkbox.checked == true){
//		$('#difang').panel('open');
//	}else{
//		$('#cityCode').textbox('setValue',"");
//		$('#cityGettimeStr').datebox('setValue',"");
//		$('#cityGettimeBeginStr').datebox('setValue',"");
//		$('#cityGettimeEndStr').datebox('setValue',"");
//		$('#cityGettimeOffice').textbox('setValue',"");
//		$('#difang').panel('close');
//	}	
//}
/*
 * 新增执法证信息
 */
function code_add() {
	top.bsWindow(contextPath + "/supervise/officials/officials_code_add.jsp", "新增执法证", {
		width : "1000",
		height : "500",
		buttons : [ {
			name : "关闭",
			classStyle : "btn-alert-gray"
		} ,{
			name : "保存",
			classStyle : "btn-alert-blue"
		} ],
		submit : function(v, h) {
			var cw = h[0].contentWindow;
			if (v == "保存") {
				param_code = cw.save();
				param_code.personId = id;
				//证件类型
				var jsonCode = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "DOCUMENTS_TYPE"});
				for (var i=0; i<=jsonCode.rtData.length-1;i++){
					if(param_code.codeType == jsonCode.rtData[i].codeNo){
						param_code.codeType = jsonCode.rtData[i].codeName;
					}
				}
				//有效期限
				var be = param_code.codeBeginStr +"—"+ param_code.codeEndStr;
				param_code.codeValid = be.replace(/-/g,".");
				code_data.push(param_code);
					$("#datagrid").datagrid("loadData",code_data);
					return true;
			} else if (v == "关闭") {
				return true;
			}
		}
	});

}
//初始化表格
function datagrate(){
	datagrid = $('#datagrid').datagrid({
		data:code_data,
		pagination: false,
		singleSelect:true,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		idField:'id',//主键列
		fitColumns:true,//列是否进行自动宽度适应  
		columns:[[
			{field:'ID',title:'序号',align:'center',
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
			{field:'codeType',title:'证件类型',width:50,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'code',title:'证件编号',width:50,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'codeDateStr',title:'发证日期',width:100,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'awardDept',title:'颁发机关',width:100,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{field:'codeValid',title:'有效期限',width:100,align:'center' , halign: 'center', formatter: 
				function(value,rowData,rowIndex){
				if(value == null){
					return "";
				}else{
					var optsStr = "<span title=" + value + ">" + value + "</span>";
					return optsStr;
				}
	            },},
			{ field : '___', title : '操作', width : '6%',align:'center' , halign: 'center', formatter : function(value,row,index) {
					row.index = index;
					var rowCode = JSON.stringify(row);
					var optsStr = "<span title='修改'><a href='javaScript:void(0);' onclick='codeEdit("+rowCode+")'><i class='fa fa-pencil common-yellow'></i></a></span>" +
							"&nbsp;&nbsp;<span title='删除'><a href='javaScript:void(0);' onclick='codeDel(\"" + index + "\")' ><i class='fa fa-trash-o common-red'></i></a></span>";
					return optsStr;
				}
			},
		]],
		singleSelect: true,
		checkOnSelect: true
	});
}
//编辑执法证信息
function codeEdit(rowCode){
    var url = contextPath + "/personGetcodeCtrl/openCodeEdit.action";
    url = url + "?" + $.param(rowCode);
	top.bsWindow(url, "修改执法证", {
		width : "1000",
		height : "500",
		buttons : [ {
			name : "关闭",
			classStyle : "btn-alert-gray"
		} ,{
			name : "保存",
			classStyle : "btn-alert-blue"
		} ],
		submit : function(v, h) {
			var cw = h[0].contentWindow;
			if (v == "保存") {
				param_code = cw.save();
				param_code.personId = id;
				//证件类型
				var jsonCode = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "DOCUMENTS_TYPE"});
				for (var i=0; i<=jsonCode.rtData.length-1;i++){
					if(param_code.codeType == jsonCode.rtData[i].codeNo){
						param_code.codeType = jsonCode.rtData[i].codeName;
					}
				}
				//有效期限
				var be = param_code.codeBeginStr +"—"+ param_code.codeEndStr;
				param_code.codeValid = be.replace(/-/g,".");
				code_data.id = param_code.id;
				code_data.splice(param_code.index, 1); 
				code_data.push(param_code);
					$("#datagrid").datagrid("loadData",code_data);
					return true;
			} else if (v == "关闭") {
				return true;
			}
		}
	});
}
//删除执法证
function codeDel(code){
	code_delete_data.push(code_data[code]);
	code_data.splice(code, 1); 
	$("#datagrid").datagrid("loadData",code_data);
}

