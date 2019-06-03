var areaId = "";
var deptLevelId = "";
var parentId = "";
function doInit(){
	natureInit();
	orgSysInit();
	if(id!=0){//编辑
		var json = tools.requestJsonRs("/departmentInfoController/get.action",{id:id});
 		bindJsonObj2Easyui(json.rtData , "form1");
 		//获取归属政府，部门地区和部门层级的数据
		var parentIdJson = tools.requestJsonRs("/departmentInfoController/parentIdToLevel.action");
		$('#parentId').textbox('setValue',parentIdJson.rtData.name);
    	$('#administrativeDivision').textbox('setValue',parentIdJson.rtData.areaName);
    	$('#deptLevel').textbox('setValue',parentIdJson.rtData.levelName);
    	parentId = parentIdJson.rtData.id;
    	areaId = parentIdJson.rtData.areaId;
    	deptLevelId = parentIdJson.rtData.levelId;
    	
 		if(json.rtData.isManubrium==1){
 			document.getElementById("div1").style.display="";
 			document.getElementById("div2").style.display="";
 			document.getElementById("div3").style.display="";
 			document.getElementById("div4").style.display="";
 			$.parser.parse($('#div1'));
 			$.parser.parse($('#div2'));
 			$.parser.parse($('#div3'));
 			$.parser.parse($('#div4'));
 		}
 		//监督部门
 		$('#superviceDepartmentId').combobox({
 			prompt:'输入关键字后自动搜索',
 			mode:'remote',
 			url:contextPath + '/SuperviseController/getSuperviceDept.action?id='+json.rtData.superviceDepartmentId,
 			valueField:'id',
 			textField:'name',
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
 		//垂管部门
 		$('#droopId').combobox({
 			prompt:'输入关键字后自动搜索',
 			mode:'remote',
 			url:contextPath + '/subjectCtrl/getSysCodeTempByIdAll.action?id='+json.rtData.droopId,
 			valueField:'id',
 			textField:'name',
 			panelHeight:'100px',
 			multiple:false,
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
	}else{
		//监督部门
		$('#superviceDepartmentId').combobox({
			prompt:'输入关键字后自动搜索',
			mode:'remote',
			url:contextPath + '/SuperviseController/getSuperviceDept.action',
			valueField:'id',
			textField:'name',
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
		//获取归属政府，部门地区和部门层级的数据
		var parentIdJson = tools.requestJsonRs("/departmentInfoController/parentIdToLevel.action");
		$('#parentId').textbox('setValue',parentIdJson.rtData.name);
    	$('#administrativeDivision').textbox('setValue',parentIdJson.rtData.areaName);
    	$('#deptLevel').textbox('setValue',parentIdJson.rtData.levelName);
    	parentId = parentIdJson.rtData.id;
    	areaId = parentIdJson.rtData.areaId;
    	deptLevelId = parentIdJson.rtData.levelId;
    	//垂管部门
		$('#droopId').combobox({
			prompt:'输入关键字后自动搜索',
			mode:'remote',
			url:contextPath + '/subjectCtrl/getSysCodeTempByIdAll.action',
			valueField:'id',
			textField:'name',
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
	manubriumLevelInit();
	innerSupOrgLevelInit();
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
//保存
function save(){
	if($('#form1').form('enableValidation').form('validate')){	
		var param = tools.formToJson($("#form1"));
		//校验统一社会信用代码
		var text = /[^_IOZSVa-z\W]{2}\d{6}[^_IOZSVa-z\W]{10}/g;
		var flag = text.test(param.departmentCode);
		if(!flag){
			$.MsgBox.Alert_auto("统一社会信用代码输入不正确");
			return false;
		}
		//校验是否垂管
		if(param.isManubrium =="" || param.isManubrium ==null){
			$.MsgBox.Alert_auto("请选择是否垂管");
			return false;
		}
		if(param.isManubrium == 1){
			if(param.droopId =="" || param.droopId == null ){
				$.MsgBox.Alert_auto("请选择垂管部门");
				return false;
			}
			if(param.manubriumLevel =="" || param.manubriumLevel == null ){
				$.MsgBox.Alert_auto("请选择垂管层级");
				return false;
			}
		}
		//校验邮编
		if(param.postCode !="" && param.postCode !=null){
			var text = /^[0-9]{6}$/;
			var flag = text.test(param.postCode);
			if(!flag){
				$.MsgBox.Alert_auto("邮编输入不正确");
				return false;
			}
		}
		//校验编制数
		if(param.innerSupOrgPostNum !="" && param.innerSupOrgPostNum !=null){
			var text = /^[0-9]*$/;
			var flag = text.test(param.innerSupOrgPostNum);
			if(!flag){
				$.MsgBox.Alert_auto("编制数只能输入数字");
				return false;
			}
		}
		//校验电子邮箱
		if(param.mail !="" && param.mail !=null){
			var text = /(^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$)/;
			var flag = text.test(param.mail);
			if(!flag){
				$.MsgBox.Alert_auto("电子邮箱格式输入不正确");
				return false;
			}
		}
		
		param.isDelete = 0;
		param.isExamine = 0;
		param.isGovernment = 0;
		param.parentId = parentId;
		param.administrativeDivision = areaId;
		param.deptLevel = deptLevelId;
		param.orgSys = $('#orgSys').combobox('getValues')+"";
		if(param.isManubrium == 0){
			param.manubriumLevel = "";
			param.droopId = "";
		}
	    if(id!=0){//编辑
	    	var json = tools.requestJsonRs("/departmentInfoController/update.action",param);
		    return json.rtState;
		}else{//新增
			var json = tools.requestJsonRs("/departmentInfoController/save.action",param);
		    return json.rtState;
		}
	}else{
		return false;
		
	}
	
}

//是否垂管
function show(radio){
	if ( radio.value == 1){
		document.getElementById("div1").style.display="";
		document.getElementById("div2").style.display="";
		document.getElementById("div3").style.display="";
		document.getElementById("div4").style.display="";
		$.parser.parse($('#div1'));
		$.parser.parse($('#div2'));
		$.parser.parse($('#div3'));
		$.parser.parse($('#div4'));
	}
	if ( radio.value == 0){
		document.getElementById("div1").style.display="none";
		document.getElementById("div2").style.display="none";
		document.getElementById("div3").style.display="none";
		document.getElementById("div4").style.display="none";
		$.parser.parse($('#div1'));
		$.parser.parse($('#div2'));
		$.parser.parse($('#div3'));
		$.parser.parse($('#div4'));
	}
}
//部门性质
function natureInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "DEPT_NATURE"});
    if(json.rtState) {
        //json.rtData.unshift({codeNo: -1, codeName: "请选择"});
        $('#nature').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
    		panelHeight:'150px',
        });
    }
}
//垂管层级
function manubriumLevelInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "MANUBRIUM_LEVEL"});
    if(json.rtState) {
        //json.rtData.unshift({codeNo: -1, codeName: "请选择"});
        $('#manubriumLevel').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
    		panelHeight:'150px',
        });
    }
}
//内设监督机构层级
function innerSupOrgLevelInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "INNER_SUP_ORG_LEVEL"});
    if(json.rtState) {
        $('#innerSupOrgLevel').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
    		panelHeight:'150px',
        });
    }
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
    		multiple:true,
            formatter: function (row) {
                var opts = $(this).combobox('options');
                return '<input type="checkbox" class="combobox-checkbox" style="margin-right:5px;cursor:pointer;">' + row[opts.textField]
            },

            onShowPanel: function () {
                var opts = $(this).combobox('options');
                var target = this;
                var values = $(target).combobox('getValues');
                $.map(values, function (value) {
                    var el = opts.finder.getEl(target, value);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                })
            },
            onLoadSuccess: function () {
                var opts = $(this).combobox('options');
                var target = this;
                var values = $(target).combobox('getValues');
                $.map(values, function (value) {
                    var el = opts.finder.getEl(target, value);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                })
            },
            onSelect: function (row) {
                //console.log(row);
                var opts = $(this).combobox('options');
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
            },
            onUnselect: function (row) {
                var opts = $(this).combobox('options');
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', false);
            }
        });
    }
}
