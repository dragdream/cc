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
/**
 * 默认加载方法
 * @returns
 */
function doInit(){
    //easyui-panel适应父容器大小
	var params = tools.formToJson($("#common_case_search_form"));
	initCommonCaseSource();//案件来源下拉框
    initCommonCasePartType();//加载当事人类型
    initCommonCaseCurrentState();
    initCommonCaseClosedState();
    getUserSubjetAndDepartment();//获取登录信息，加载主体，和所属系统
    initDepartment();
    orgSysInit();
    administrativeDivisionInit();
}


/**
 * 初始化案件来源
 * @returns
 */
function initCommonCaseSource() {
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: 'COMMON_CASE_SOURCE'});
    if(json.rtState) {
        var page = "";
        page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="caseSource0" id="caseSource0" class="easyui-checkbox" '
        + 'value="all" title="全选" label="<span title=\'全选\'>全选</span>"/>';
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="caseSource" id="caseSource'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'  "/>';
            	//+ 'value="'+json.rtData[i].codeNo+'" label="<span title=\'' + json.rtData[i].codeName + '\'>'+json.rtData[i].codeName+'</span>"/>';
            
        }
        var pageDoc = $('#common_case_source_td').html(page);
        $.parser.parse(pageDoc);
        //单个项点击触发事件
		for(var i=0 ; i < json.rtData.length; i++){
		    $("#caseSource"+json.rtData[i].codeNo).checkbox({
		        onChange: function(checked){
		        	if(checked){
		        		if($("#common_case_search_form input[name='caseSource']:checked").length==json.rtData.length){
		        			$("#caseSource0").checkbox({checked: true});
		        		}
		            }else{
		            	$("#caseSource0").checkbox({checked: false});
		            }
		        }
		    });
		}
		// 全选点击触发事件
		$("#caseSource0").checkbox({
		    onChange: function(checked){
		    	if(checked){
		    		for(var i=0 ; i < json.rtData.length; i++){
		    			$("#caseSource"+json.rtData[i].codeNo).checkbox({checked: true});
		    		}
		        }else{
		        	for(var i=0 ; i < json.rtData.length; i++){
		    			$("#caseSource"+json.rtData[i].codeNo).checkbox({checked: false});
		    		}
		        }
		    }
		});
    }
}

/**
 * 初始化办理状态
 * @returns
 */
function initCommonCaseCurrentState() {
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: 'COMMON_CURRENT_STATE'});
    if(json.rtState) {
        var page = "";
        page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="currentState0" id="currentState0" class="easyui-checkbox" '
        + 'value="all" title="全选" label="<span title=\'全选\'>全选</span>"/>';
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="currentState" id="currentState'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
            	+ 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'  "/>';
            	//+ 'value="'+json.rtData[i].codeNo+'" title="'+json.rtData[i].codeName+'" label="<span title=\'' + json.rtData[i].codeName + '\'>'+json.rtData[i].codeName+'</span>"/>';
        }
        var pageDoc = $('#common_case_current_state_td').html(page);
        $.parser.parse(pageDoc);
        //单个项点击触发事件
		for(var i=0 ; i < json.rtData.length; i++){
		    $("#currentState"+json.rtData[i].codeNo).checkbox({
		        onChange: function(checked){
		        	if(checked){
		        		if($("#common_case_search_form input[name='currentState']:checked").length==json.rtData.length){
		        			$("#currentState0").checkbox({checked: true});
		        		}
		            }else{
		            	$("#currentState0").checkbox({checked: false});
		            }
		        }
		    });
		}
		// 全选点击触发事件
		$("#currentState0").checkbox({
		    onChange: function(checked){
		    	if(checked){
		    		for(var i=0 ; i < json.rtData.length; i++){
		    			$("#currentState"+json.rtData[i].codeNo).checkbox({checked: true});
		    		}
		        }else{
		        	for(var i=0 ; i < json.rtData.length; i++){
		    			$("#currentState"+json.rtData[i].codeNo).checkbox({checked: false});
		    		}
		        }
		    }
		});
    }
}

/**
 * 初始化结案状态
 * @returns
 */
function initCommonCaseClosedState() {
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: 'COMMON_CLOSED_STATE'});
    if(json.rtState) {
        var page = "";
        page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="closedState0" id="closedState0" class="easyui-checkbox" '
        + 'value="all" title="全选" label="<span title=\'全选\'>全选</span>"/>';
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="closedState" id="closedState'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
            	+ 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'  "/>';
            	//+ 'value="'+json.rtData[i].codeNo+'" title="'+json.rtData[i].codeName+'" label="<span title=\'' + json.rtData[i].codeName + '\'>'+json.rtData[i].codeName+'</span>"/>';
        }
        var pageDoc = $('#common_case_closed_state_td').html(page);
		$.parser.parse(pageDoc);
		//单个项点击触发事件
		for(var i=0 ; i < json.rtData.length; i++){
		    $("#closedState"+json.rtData[i].codeNo).checkbox({
		        onChange: function(checked){
		        	if(checked){
		        		if($("#common_case_search_form input[name='closedState']:checked").length==json.rtData.length){
		        			$("#closedState0").checkbox({checked: true});
		        		}
		            }else{
		            	$("#closedState0").checkbox({checked: false});
		            }
		        }
		    });
		}
		// 全选点击触发事件
		$("#closedState0").checkbox({
		    onChange: function(checked){
		    	if(checked){
		    		for(var i=0 ; i < json.rtData.length; i++){
		    			$("#closedState"+json.rtData[i].codeNo).checkbox({checked: true});
		    		}
		        }else{
		        	for(var i=0 ; i < json.rtData.length; i++){
		    			$("#closedState"+json.rtData[i].codeNo).checkbox({checked: false});
		    		}
		        }
		    }
		});
    }
}

/**
 * 初始化当事人类型
 * @returns
 */
function initCommonCasePartType(){
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: 'COMMON_PARTY_TYPE'});
    if(json.rtState) {
        var page = "";
        page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="partyType0" id="partyType0" class="easyui-checkbox" '
        + 'value="all" title="全选" label="<span title=\'全选\'>全选</span>"/>';
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" type="checkbox" name="partyType" id="partyType'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'"/>';
        }
        var pageDoc = $('#common_case_part_type_td').html(page);
        $.parser.parse(pageDoc);
        // 单个项点击触发事件
        for(var i=0 ; i < json.rtData.length; i++){
            $("#partyType"+json.rtData[i].codeNo).checkbox({
                onChange: function(checked){
                	if(checked){
                		if($("#common_case_search_form input[name='partyType']:checked").length==json.rtData.length){
                			$("#partyType0").checkbox({checked: true});
                		}
                    }else{
                    	$("#partyType0").checkbox({checked: false});
                    }
                }
            });
        }
        // 全选点击触发事件
		$("#partyType0").checkbox({
		    onChange: function(checked){
		    	if(checked){
		    		for(var i=0 ; i < json.rtData.length; i++){
		    			$("#partyType"+json.rtData[i].codeNo).checkbox({checked: true});
		    		}
		        }else{
		        	for(var i=0 ; i < json.rtData.length; i++){
		    			$("#partyType"+json.rtData[i].codeNo).checkbox({checked: false});
		    		}
		        }
		    }
		});
    }
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
            //$('#departmentId').val(deptJson[0].codeNo);//默认选中第一个部门
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
//			,
//		onHidePanel:function(row){
//		    var _value = $(this).combobox('getValues');/* 用户输入的值 */
//		    var json = tools.requestJsonRs("/adminDivisionManageCtrl/getAreaSearchLevel.action?id="+_value,);
//		    console.log(json.rtData);
//		    if(json.rtState) {
//		        $('#deptLevel').combobox({
//		            data: json.rtData,
//		            valueField: 'ID',
//		            textField: 'NAME',
//		    		panelHeight:'150px',
//		        });
//		    }
//		}
	});
}

/**
 * 进入综合查询界面
 * @returns
 */
function doFilingSave(){
	var params = tools.formToJson($("#common_case_search_form"));
	// 获取当事人类型
	var partyType = '';
	var partyType0 = '';
	if(/*(!$("#common_case_search_form input[name='partyType0']").is(':checked')) &&*/
			$("#common_case_search_form input[name='partyType']:checked").length >0){
		$("#common_case_search_form input[name='partyType']:checked").each(function(){
			partyType += this.value + ',';
			partyType0 += this.labels[0].innerHTML + '、';
		})
	}
	params.partyType = partyType.length>0?partyType.substring(0,partyType.length-1):'';
	params.partyType0 = partyType0.length>0?partyType0.substring(0,partyType0.length-1):'';
	// 获取案件来源
	var caseSource = '';
	var caseSource0 = '';
	if(/*(!$("#common_case_search_form input[name='caseSource0']").is(':checked')) &&*/
		$("#common_case_search_form input[name='caseSource']:checked").length >0){
		$("#common_case_search_form input[name='caseSource']:checked").each(function(){
			caseSource += this.value + ',';
			caseSource0 += this.labels[0].innerHTML + '、';
		})
	}
	params.caseSource = caseSource.length>0?caseSource.substring(0,caseSource.length-1):'';
	params.caseSource0 = caseSource0.length>0?caseSource0.substring(0,caseSource0.length-1):'';
	// 获取办理状态
	var currentState = '';
	var currentState0 = '';
	if(/*(!$("#common_case_search_form input[name='currentState0']").is(':checked')) &&*/
		$("#common_case_search_form input[name='currentState']:checked").length >0){
		$("#common_case_search_form input[name='currentState']:checked").each(function(){
			currentState += this.value + ',';
			currentState0 += this.labels[0].innerHTML + '、';
		})
	}
	params.currentState = currentState.length>0?currentState.substring(0,currentState.length-1):'';
	params.currentState0 = currentState0.length>0?currentState0.substring(0,currentState0.length-1):'';
	// 获取结案状态
	var closedState = '';
	var closedState0 = '';
	if(/*(!$("#common_case_search_form input[name='closedState0']").is(':checked')) &&*/
		$("#common_case_search_form input[name='closedState']:checked").length >0){
		$("#common_case_search_form input[name='closedState']:checked").each(function(){
			closedState += this.value + ',';
			closedState0 += this.labels[0].innerHTML + '、';
		})
	}
	params.closedState = closedState.length>0?closedState.substring(0,closedState.length-1):'';
	params.closedState0 = closedState0.length>0?closedState0.substring(0,closedState0.length-1):'';
	
	// 获取提交状态
	var isSubmit = '';
	if($("#common_case_search_form input[name='isSubmit']:checked").length == 1){
		isSubmit = $("#common_case_search_form input[name='isSubmit']:checked").val();
	}
	params.isSubmit = isSubmit;
	// 是否重大案件
	var isMajorCase = '';
	if($("#common_case_search_form input[name='isMajorCase']:checked").length == 1){
		isMajorCase = $("#common_case_search_form input[name='isMajorCase']:checked").val();
	}
	params.isMajorCase = isMajorCase;
	// 是否委托办案
	var isDepute = '';
	if($("#common_case_search_form input[name='isDepute']:checked").length == 1){
		isDepute = $("#common_case_search_form input[name='isDepute']:checked").val();
	}
	params.isDepute = isDepute;
	// 是否涉刑
	var isCriminal = '';
	if($("#common_case_search_form input[name='isCriminal']:checked").length == 1){
		isCriminal = $("#common_case_search_form input[name='isCriminal']:checked").val();
	}
	params.isCriminal = isCriminal;
	// 是否法制审核
	var isFilingLegalReview = '';
	if($("#common_case_search_form input[name='isFilingLegalReview']:checked").length == 1){
		isFilingLegalReview = $("#common_case_search_form input[name='isFilingLegalReview']:checked").val();
	}
	params.isFilingLegalReview = isFilingLegalReview;
	location.href = '/supervise/caseManager/commonCaseSearch/common_case_index.jsp?'+$.param(params);
		//params='+encodeURIComponent(tools.jsonObj2String(params));
	//location.href = '/supervise/caseManager/commonCaseSearch/common_case_index.jsp?params='+JSON.stringify(params);
}

/**
 * 初始化执法系统
 */
function orgSysInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "LAW_ENFORCEMENT_FIELD"});
	if(json.rtState) {
        $('#orgSys').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            panelMaxHeight : 150,
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

/**
 * 初始化执法机关
 */
function initDepartment(){
	var json = tools.requestJsonRs("/departmentSearchController/getDepartmentRoles.action");
	if(json.rtState) {
    	$('#departmentId').combobox({
            data: json.rtData,
            valueField: 'id',
            textField: 'name',
            panelHeight: 'auto',
            panelMaxHeight : 150,
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
