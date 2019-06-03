var SupDeptName='';
var DeptName ='';
var SubjectName = '';
function doInit(){
	if(id!=0){
		var json = tools.requestJsonRs("/departmentCtrl/get.action",{id:id});
 		bindJsonObj2Easyui(json.rtData , "form1");
	}
	//执法部门
	$('#businessDeptId').combobox({
		prompt:'输入关键字后自动搜索',
		mode:'remote',
		url:contextPath + '/subjectCtrl/getSysCodeTempById.action',
		valueField:'id',
		textField:'name',
		multiple:false,
		method:'post',
		panelHeight:'100px',
		//label: 'Language:',
		labelPosition: 'top',
		onClick:function(row){
			DeptName = row.name;
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
	//监督部门
	$('#businessSupDeptId').combobox({
		prompt:'输入关键字后自动搜索',
		mode:'remote',
		url:contextPath + '/SuperviseController/getSysCodeTempById.action',
		valueField:'id',
		textField:'name',
		multiple:false,
		method:'post',
		panelHeight:'100px',
		//label: 'Language:',
		labelPosition: 'top',
		onClick:function(row){
			//$('#businessSupDeptName').textbox('setValue',row.name);
			SupDeptName = row.name;
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
	//执法主体
	$('#businessSubjectId').combobox({
		prompt:'输入关键字后自动搜索',
		mode:'remote',
		url:contextPath + '/subjectCtrl/getSysCodeSubject.action',
		valueField:'id',
		textField:'subName',
		multiple:false,
		method:'post',
		panelHeight:'100px',
		//label: 'Language:',
		labelPosition: 'top',
		onClick:function(row){
			SubjectName = row.subName;
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
function show(radio){
	if(radio.value==10){
		document.getElementById("div1").style.display = 'none';
		document.getElementById("div2").style.display = '';
	}
	if(radio.value==20){
		document.getElementById("div1").style.display = '';
		document.getElementById("div2").style.display = 'none';
	}
}
function save(){
	if($('#form1').form('validate')){	
		var param = tools.formToJson($("#form1"));
			param.uuid = id;
			param.businessSupDeptName = SupDeptName;
			param.businessDeptName = DeptName;
			param.businessSubjectName = SubjectName;
			var json = tools.requestJsonRs("/departmentCtrl/update.action",param);
		    return json.rtState;
	}else{
		return false;
		
	}
	
}

