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
var isTrueJson = [
	{codeNo:'1', codeName: '是'},
	{codeNo:'0', codeName: '否'}
];
var partyTypeJsons = [];
var caseSourceJsons = [];
var currentStateJsons = [];
var closedStateJsons = [];

var params;
/**
 * 默认加载方法
 * @returns
 */
function doInit(){
    $("#commonCaseDiv").hide();
    dateValidate('begincreateTimeStr', 'endcreateTimeStr');
    dateValidate('beginfilingDateStr', 'endfilingDateStr');
    dateValidate('beginclosedDateStr', 'endclosedDateStr');
    dateValidate('beginpunishDateStr', 'endpunishDateStr');
    dateValidate('beginpunishExecutDateStr', 'endpunishExecutDateStr');
    var params = tools.formToJson($("#common_case_search_form"));
    initCommonCaseSource(); // 加载案件来源
    initCommonCasePartType(); // 加载当事人类型
    initCommonCaseCurrentState(); // 加载办理状态
    initCommonCaseClosedState(); // 加载结案状态
    initSubject(); // 加载执法主体
    initDepartment(); // 加载执法机关
    orgSysInit(); // 加载执法系统
    areaInit(); // 加载行政区划
    initJsonListCheckBox(isTrueJson,'isDepute');// 是否委托办案
    var json = [
        {codeNo:'1', codeName: '已提交'},
	    {codeNo:'0', codeName: '未提交'}
    ];
    initJsonListCheckBox(json,'isSubmit');// 提交状态
    initJsonListCheckBox(isTrueJson,'isMajorCase');// 是否重大案件
    initJsonListCheckBox(isTrueJson,'isLegalReview');// 是否法制审核
    initJsonListCheckBox(isTrueJson,'isCriminal');// 是否涉刑
    
    // 初始化可选列
    $(".panList").append(juicer(tpl,listdata));
    // 初始化表格
    datagrid = $('#common_case_index_datagrid') .datagrid( {
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar : '#toolbar'// 工具条对象
    });
    $("#hideTable").hide(); // 高级筛选隐藏
    initPunishState(); // 加载处罚决定类型
    initpunishExecutState(); // 加载处罚执行类型
}


/**
 * 初始化案件来源
 * @returns
 */
function initCommonCaseSource() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: 'COMMON_CASE_SOURCE'});
    if(json.rtState) {
        var page = "";
        var caseSourceJson;
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelWidth="90px" labelAlign="left" labelPosition="after" type="checkbox" name="caseSource" id="caseSource'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+' "/>';
            caseSourceJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
            caseSourceJsons.push(caseSourceJson);
        }
        var pageDoc = $('#common_case_source_td').html(page);
        $.parser.parse(pageDoc);
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
        var currentStateJson;
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelWidth="90px" labelAlign="left" labelPosition="after" type="checkbox" name="currentState" id="currentState'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'  "/>';
            currentStateJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
            currentStateJsons.push(currentStateJson);
        }
        var pageDoc = $('#common_case_current_state_td').html(page);
        $.parser.parse(pageDoc);
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
        var closedStateJson;
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelWidth="90px" labelAlign="left" labelPosition="after" type="checkbox" name="closedState" id="closedState'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'  "/>';
            closedStateJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
            closedStateJsons.push(closedStateJson);
        }
        var pageDoc = $('#common_case_closed_state_td').html(page);
        $.parser.parse(pageDoc);
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
        var partyTypeJson;
        for(var i=0 ; i < json.rtData.length; i++){
            page = page + '<input style="width: 15px; height: 15px;" labelWidth="90px" labelAlign="left" labelPosition="after" type="checkbox" name="partyType" id="partyType'+json.rtData[i].codeNo+'" class="easyui-checkbox" '
                + 'value="'+json.rtData[i].codeNo+'" label="'+json.rtData[i].codeName+'"/>';
            partyTypeJson = {codeNo:json.rtData[i].codeNo,codeName:json.rtData[i].codeName};
            partyTypeJsons.push(partyTypeJson);
        }
        var pageDoc = $('#common_case_part_type_td').html(page);
        $.parser.parse(pageDoc);
    }
}

/**
 * 初始化执法主体
 * @param id
 */
function initSubject(id){
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
 * 行政区划
 */
function areaInit(){
    $('#area').combobox({
        prompt:'请选择',
        mode:'remote',
        url:contextPath + '/adminDivisionManageCtrl/getAreaSearch.action',
        valueField:'ID',
        textField:'NAME',
        multiple:true,
        method:'post',
        panelHeight: 'auto',
        panelMaxHeight : 192,
        labelPosition: 'top'
    });
}

/**
 * 查询，给定json，多选
 */
function initJsonListCheckBox(json,id,value) {
	var page = "";
    for(var i=0 ; i < json.length; i++){
//        page = page + '<input name="'+id+'" id="'+id+json[i].codeNo+'" class="easyui-checkbox" '
//	        + 'style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="90px"'
//	        + 'required value="'+json[i].codeNo+'" label="'+json[i].codeName+'"/>';
        page = page + '<input style="width: 15px; height: 15px;" labelWidth="90px" labelAlign="left" labelPosition="after" type="checkbox"' 
        	+' name="'+id+'" id="'+id+json[i].codeNo+'" class="easyui-checkbox" '
        	+ 'value="'+json[i].codeNo+'" label="'+json[i].codeName+'"/>';
    }
    var pageDoc = $('#'+id+'_td').html(page);
	$.parser.parse(pageDoc);
}

/**
 * 获取条件参数
 * @returns {___anonymous_params}
 */
function setParams(){
    params = tools.formToJson($("#common_case_search_form"));
    var conditionDiv = '';
    // 获取行政区划
    params.area = $("#area").combobox("getValues").join(",");
    if(params.area.length>0){
        conditionDiv += '<span class="tagbox-label" id="areaTag">'
            + '<span title="行政区划：'+$("#area").combobox("getText")+'">行政区划</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'area\',\'combobox\')" class="tagbox-remove"></a></span>';
    }
    // 获取执法机关
    params.departmentId = $("#departmentId").combobox("getValues").join(",");
    if(params.departmentId.length>0){
        conditionDiv += '<span class="tagbox-label" id="departmentIdTag">'
            + '<span title="执法机关：'+$("#departmentId").combobox("getText")+'">执法机关</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'departmentId\',\'combobox\')" class="tagbox-remove"></a></span>';
    }
    // 获取所属领域
    params.orgSys = $("#orgSys").combobox("getValues").join(",");
    if(params.orgSys.length>0){
        conditionDiv += '<span class="tagbox-label" id="orgSysTag">'
            + '<span title="所属领域：'+$("#orgSys").combobox("getText")+'">所属领域</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'orgSys\',\'combobox\')" class="tagbox-remove"></a></span>';
    }
    // 获取执法主体
    params.subjectId = $("#subjectId").combobox("getValues").join(",");
    if(params.subjectId.length>0){
        conditionDiv += '<span class="tagbox-label" id="subjectIdTag">'
            + '<span title="执法主体：'+$("#subjectId").combobox("getText")+'">执法主体</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'subjectId\',\'combobox\')" class="tagbox-remove"></a></span>';
    }
    
    // 获取是否委托办案
    var isDepute = '';
    var isDepute0 = '';
    if($("#common_case_search_form input[name='isDepute']:checked").length >0){
        $("#common_case_search_form input[name='isDepute']:checked").each(function(){
            isDepute += this.value + ',';
            if(this.value==1){
            	isDepute0 += '是，';
            }else if(this.value==0){
            	isDepute0 += '否，';
            }
        })
    }
    if(isDepute.length>0){
        isDepute = isDepute.substring(0,isDepute.length-1);
        conditionDiv += '<span class="tagbox-label" id="isDeputeTag">'
                     + '<span title="是否委托办案：'+isDepute0.substring(0,isDepute0.length-1)+'">是否委托办案</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'isDepute\',\'checkbox\',\''+ isDepute +'\')" class="tagbox-remove"></a></span>';
    }
    params.isDeputeStr = isDepute;
    
    // 获取当事人类型
    var partyType = '';
    var partyType0 = '';
    if($("#common_case_search_form input[name='partyType']:checked").length >0){
        $("#common_case_search_form input[name='partyType']:checked").each(function(){
            partyType += this.value + ',';
            for(var i=0;i<partyTypeJsons.length;i++){
            	if(this.value==partyTypeJsons[i].codeNo){
            		partyType0 += $.trim(partyTypeJsons[i].codeName) + '，';
            		break;
            	}
            }
        })
    }
    if(partyType.length>0){
        partyType = partyType.substring(0,partyType.length-1);
        conditionDiv += '<span class="tagbox-label" id="partyTypeTag">'
                     + '<span title="当事人类型：'+partyType0.substring(0,isDepute0.length-1)+'">当事人类型</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'partyType\',\'checkbox\',\''+ partyType +'\')" class="tagbox-remove"></a></span>';
    }
    params.partyType = partyType;
    
    // 获取案件来源
    var caseSource = '';
    var caseSource0 = '';
    if($("#common_case_search_form input[name='caseSource']:checked").length >0){
        $("#common_case_search_form input[name='caseSource']:checked").each(function(){
            caseSource += this.value + ',';
            for(var i=0;i<caseSourceJsons.length;i++){
            	if(this.value==caseSourceJsons[i].codeNo){
            		caseSource0 += $.trim(caseSourceJsons[i].codeName) + '，';
            		break;
            	}
            }
        })
    }
    if(caseSource.length>0){
        caseSource = caseSource.substring(0,caseSource.length-1);
        conditionDiv += '<span class="tagbox-label" id="caseSourceTag">'
                     + '<span title="案件来源：'+caseSource0.substring(0,caseSource0.length-1)+'">案件来源</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'caseSource\',\'checkbox\',\''+ caseSource +'\')" class="tagbox-remove"></a></span>';
    }
    params.caseSource = caseSource;
    
    // 获取办理状态
    var currentState = '';
    var currentState0 = '';
    if($("#common_case_search_form input[name='currentState']:checked").length >0){
        $("#common_case_search_form input[name='currentState']:checked").each(function(){
            currentState += this.value + ',';
            for(var i=0;i<currentStateJsons.length;i++){
            	if(this.value==currentStateJsons[i].codeNo){
            		currentState0 += $.trim(currentStateJsons[i].codeName) + '，';
            		break;
            	}
            }
        })
    }
    if(currentState.length>0){
        currentState = currentState.substring(0,currentState.length-1);
        conditionDiv += '<span class="tagbox-label" id="currentStateTag">'
                     + '<span title="办理状态：'+currentState0.substring(0,currentState0.length-1)+'">办理状态</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'currentState\',\'checkbox\',\''+ currentState +'\')" class="tagbox-remove"></a></span>';
    }
    params.currentState = currentState;
    
    // 获取结案状态
    var closedState = '';
    var closedState0 = '';
    if($("#common_case_search_form input[name='closedState']:checked").length >0){
        $("#common_case_search_form input[name='closedState']:checked").each(function(){
            closedState += this.value + ',';
            for(var i=0;i<closedStateJsons.length;i++){
            	if(this.value==closedStateJsons[i].codeNo){
            		closedState0 += $.trim(closedStateJsons[i].codeName) + '，';
            		break;
            	}
            }
        })
    }
    if(closedState.length>0){
        closedState = closedState.substring(0,closedState.length-1);
        conditionDiv += '<span class="tagbox-label" id="isDeputeTag">'
                     + '<span title="结案状态：'+closedState0.substring(0,closedState0.length-1)+'">结案状态</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'closedState\',\'checkbox\',\''+ closedState +'\')" class="tagbox-remove"></a></span>';
    }
    params.closedState = closedState;
    
    // 获取提交状态
    var isSubmit = '';
    var isSubmit0 = '';
    if($("#common_case_search_form input[name='isSubmit']:checked").length >0){
        $("#common_case_search_form input[name='isSubmit']:checked").each(function(){
            isSubmit += this.value + ',';
            if(this.value==1){
            	isSubmit0 += '已提交，';
            }else if(this.value==0){
            	isSubmit0 += '未提交，';
            }
        })
    }
    if(isSubmit.length>0){
        isSubmit = isSubmit.substring(0,isSubmit.length-1);
        conditionDiv += '<span class="tagbox-label" id="isSubmitTag">'
                     + '<span title="提交状态：'+isDepute0.substring(0,isSubmit0.length-1)+'">提交状态</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'isSubmit\',\'checkbox\',\''+ isSubmit +'\')" class="tagbox-remove"></a></span>';
    }
    params.isSubmitStr = isSubmit;
    
    // 获取入库日期
    if(params.begincreateTimeStr.length>0 && params.endcreateTimeStr.length>0){
        conditionDiv += '<span class="tagbox-label" id="createTimeStrTag">'
            + '<span title="入库日期：'+params.begincreateTimeStr+' - '+params.endcreateTimeStr+'">入库日期</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'createTimeStr\',\'datebox\')" class="tagbox-remove"></a></span>';
    }else if(params.begincreateTimeStr.length>0){
        conditionDiv += '<span class="tagbox-label" id="createTimeStrTag">'
                     + '<span title="入库日期>'+params.begincreateTimeStr+'">入库日期</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'createTimeStr\',\'datebox\')" class="tagbox-remove"></a></span>';
    }else if(params.endcreateTimeStr.length>0){
        conditionDiv += '<span class="tagbox-label" id="createTimeStrTag">'
            + '<span title="入库日期<'+params.endcreateTimeStr+'">入库日期</span>'
            + '<a href="javascript:;" onclick="thisRemove(\'createTimeStr\',\'datebox\')" class="tagbox-remove"></a></span>';
    }
    
    // 获取是否重大案件
    var isMajorCase = '';
    var isMajorCase0 = '';
    if($("#common_case_search_form input[name='isMajorCase']:checked").length >0){
        $("#common_case_search_form input[name='isMajorCase']:checked").each(function(){
            isMajorCase += this.value + ',';
            if(this.value==1){
            	isMajorCase0 += '是，';
            }else if(this.value==0){
            	isMajorCase0 += '否，';
            }
        })
    }
    if(isMajorCase.length>0){
        isMajorCase = isMajorCase.substring(0,isMajorCase.length-1);
        conditionDiv += '<span class="tagbox-label" id="isMajorCaseTag">'
                     + '<span title="是否重大案件：'+isMajorCase0.substring(0,isMajorCase0.length-1)+'">是否重大案件</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'isMajorCase\',\'checkbox\',\''+ isMajorCase +'\')" class="tagbox-remove"></a></span>';
    }
    params.isMajorCaseStr = isMajorCase;
    
    // 获取是否涉刑
    var isCriminal = '';
    var isCriminal0 = '';
    if($("#common_case_search_form input[name='isCriminal']:checked").length >0){
        $("#common_case_search_form input[name='isCriminal']:checked").each(function(){
            isCriminal += this.value + ',';
            if(this.value==1){
            	isCriminal0 += '是，';
            }else if(this.value==0){
            	isCriminal0 += '否，';
            }
        })
    }
    if(isCriminal.length>0){
        isCriminal = isCriminal.substring(0,isCriminal.length-1);
        conditionDiv += '<span class="tagbox-label" id="isCriminalTag">'
                     + '<span title="是否涉刑：'+isCriminal0.substring(0,isCriminal0.length-1)+'">是否涉刑</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'isCriminal\',\'checkbox\',\''+ isCriminal +'\')" class="tagbox-remove"></a></span>';
    }
    params.isCriminalStr = isCriminal;
    
    // 获取是否法制审核
    var isLegalReview = '';
    var isLegalReview0 = '';
    if($("#common_case_search_form input[name='isLegalReview']:checked").length >0){
        $("#common_case_search_form input[name='isLegalReview']:checked").each(function(){
            isLegalReview += this.value + ',';
            if(this.value==1){
            	isFilingLegalReview0 += '是，';
            }else if(this.value==0){
            	isFilingLegalReview0 += '否，';
            }
        })
    }
    if(isLegalReview.length>0){
        isLegalReview = isLegalReview.substring(0,isLegalReview.length-1);
        conditionDiv += '<span class="tagbox-label" id="isLegalReviewTag">'
                     + '<span title="是否法制审核：'+isLegalReview0.substring(0,isLegalReview0.length-1)+'">是否法制审核</span>'
                     + '<a href="javascript:;" onclick="thisRemove(\'isLegalReview\',\'checkbox\',\''+ isLegalReview +'\')" class="tagbox-remove"></a></span>';
    }
    params.isLegalReviewStr = isLegalReview;
    
    $("#conditionDiv").html(conditionDiv);
    
    return params;
}

/**
 * 进入综合查询界面
 * @returns
 */
function caseCommonSearch(){
	if($("#common_case_search_form").form('enableValidation').form('validate')){
		params = setParams();
	    $("#commonCaseSearchDiv").hide();
	    $("#commonCaseDiv").show();
	    initIndexDatagrid(params);
	}
}

/**
 * 重置查询条件
 * @returns
 */
function caseCommonRefresh() {
    $('#area').combobox('setValue', '');
    $('#departmentId').combobox('setValue', '');
    $('#orgSys').combobox('setValue', '');
    $('#subjectId').combobox('setValue', '');
    $("#common_case_search_form input[name='isDepute']:checked").each(function(){
        $('#isDepute' + this.value).checkbox({checked: false});
    });
    $("#common_case_search_form input[name='partyType']:checked").each(function(){
        $('#partyType' + this.value).checkbox({checked: false});
    });
    $("#common_case_search_form input[name='caseSource']:checked").each(function(){
        $('#caseSource' + this.value).checkbox({checked: false});
    });
    $("#common_case_search_form input[name='currentState']:checked").each(function(){
        $('#currentState' + this.value).checkbox({checked: false});
    });
    $("#common_case_search_form input[name='closedState']:checked").each(function(){
        $('#closedState' + this.value).checkbox({checked: false});
    });
    $("#common_case_search_form input[name='isSubmit']:checked").each(function(){
        $('#isSubmit' + this.value).checkbox({checked: false});
    });
    $('#begincreateTimeStr').datebox({validType:''});
    $('#endcreateTimeStr').datebox({validType:''});
    $('#begincreateTimeStr').datebox('setValue', '');
    $('#endcreateTimeStr').datebox('setValue', '');
    $('#begincreateTimeStr').datebox({validType:'date'});
    $('#endcreateTimeStr').datebox({validType:'date'});
    $("#common_case_search_form input[name='isMajorCase']:checked").each(function(){
        $('#isMajorCase' + this.value).checkbox({checked: false});
    });
    $("#common_case_search_form input[name='isCriminal']:checked").each(function(){
        $('#isCriminal' + this.value).checkbox({checked: false});
    });
    $("#common_case_search_form input[name='isLegalReview']:checked").each(function(){
        $('#isLegalReview' + this.value).checkbox({checked: false});
    });
}

/**
 * 初始化执法系统
 */
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

var subjectId = "";//主体ID
var submitJson = [
    {codeNo:'0', codeName: '未提交'},
    {codeNo:'1', codeName: '已提交'}
]
var listdata = {
    isShowColumn: [
        {codeNo:'officeName', codeName: '执法人员'},
        {codeNo:'filingDateStr', codeName: '立案日期'},
        {codeNo:'surveyEndDateStr', codeName: '调查终结日期'},
        {codeNo:'punishmentDateStr', codeName: '行政处罚决定书日期'},
        {codeNo:'punishmentSendDateStr', codeName: '决定书送达日期'},
        {codeNo:'closedDateStr', codeName: '结案日期'},
        {codeNo:'caseTime', codeName: '案件总时长'},
        {codeNo:'filingTime', codeName: '立案周期'},
        {codeNo:'surveyTime', codeName: '调查取证周期'},
        {codeNo:'punishTime', codeName: '作出处罚决定周期'},
        {codeNo:'punishDecisionExecutTime', codeName: '处罚决定执行周期'},
        {codeNo:'closedTime', codeName: '结案周期'},
        {codeNo:'isMajorCase', codeName: '是否重大案件'},
        {codeNo:'isLegalReview', codeName: '是否法制审核'},
        {codeNo:'dataSourceValue', codeName: '数据来源'},
        {codeNo:'createTimeStr', codeName: '入库日期'},
        {codeNo:'isForce', codeName: '是否采取强制措施'},
        {codeNo:'isCoercion', codeName: '是否强制执行'}

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
	$("body").append('<div id="panListBack" class="" ></div>');
	//$("body").append('<div id="panListBack" class="" style="background-color:#000;opacity:0.6;"></div>');
});

$("body").delegate("#panListBack","click",function(){
	$(".panList").hide();
	$("#panListBack").remove();
});

var temp = [];
function detail(code,th){
    if($(th).children('i').hasClass("fa-check")){
        $(th).children('i').removeClass("fa-check");
        $('#common_case_index_datagrid').datagrid('hideColumn', code);
        for(var i=0;i<temp.length;i++){
            if(temp[i] == code){
                temp.splice(i,1);
                break;
            }
        }
    } else{
        $(th).children('i').addClass("fa-check");
        $('#common_case_index_datagrid').datagrid('showColumn', code);
        temp.push(code);
    }
   }

var tpl=[
    '{@each isShowColumn as it}',
    '<li onclick="detail(\'${it.codeNo}\',this)" title="${it.codeName}"><i class="fa"></i>${it.codeName}</li>',
    '{@/each}'
   ].join('\n');

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
        field: 'caseCode', title: '<span title="案件编号">案件编号</span>', width: '180px', halign: 'center', align: 'left', sortable:true,
        formatter: function(e, rowData) {
            var caseCode = rowData.caseCode;
            if(caseCode == null || caseCode == 'null') {
            	caseCode = "";
            }
            var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+caseCode+"'>"+caseCode+"</lable>"
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
        field: 'name', title: '<span title="案由（事由）">案由（事由）</span>', width: '300px', halign: 'center', align: 'left',
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

/**
 * 可选列
 */
var column = [[
    {
        field: 'partyTypeValue', title: '当事人类型', width: '100px', halign: 'center', align: 'center'
    },
    {
        field: 'partyName', title: '当事人名称', width: '100px', halign: 'center', align: 'center'
    },
    {
        field: 'currentStateValue', title: '办理状态', width: '130px', halign: 'center', align: 'center'
    },
    {
        field: 'closedStateValue', title: '结案状态', width: '130px', halign: 'center', align: 'center'
    },
    {
        field: 'officeName', title: '执法人员', width: '200px', halign: 'center', align: 'center', hidden: true
    },
    {
        field: 'filingDateStr', title: '立案日期', width: '140px', halign: 'center', align: 'center', hidden: true
    },
    {
        field: 'surveyEndDateStr', title: '调查终结日期', width: '140px', halign: 'center', align: 'center', hidden: true
    },
    {
        field: 'punishmentDateStr', title: '行政处罚决定书日期', width: '140px', halign: 'center', align: 'center', hidden: true
    },
    {
        field: 'punishmentSendDateStr', title: '决定书送达日期', width: '140px', halign: 'center', align: 'center', hidden: true
    },
    {
        field: 'closedDateStr', title: '结案日期', width: '140px', halign: 'center', align: 'center', hidden: true
    },
    {
        field: 'caseTime', title: '案件总时长', width: '140px', halign: 'center', align: 'center', hidden: true
    },
    {
        field: 'filingTime', title: '立案周期', width: '140px', halign: 'center', align: 'center', hidden: true
    },
    {
        field: 'surveyTime', title: '调查取证周期', width: '140px', halign: 'center', align: 'center', hidden: true
    },
    {
        field: 'punishTime', title: '作出处罚决定周期', width: '140px', halign: 'center', align: 'center', hidden: true
    },
    {
        field: 'punishDecisionExecutTime', title: '处罚决定执行周期', width: '140px', halign: 'center', align: 'center', hidden: true
    },
    {
        field: 'closedTime', title: '结案周期', width: '140px', halign: 'center', align: 'center', hidden: true
    },
    {
        field: 'isMajorCase', title: '是否重大案件', width: '140px', halign: 'center', align: 'center', hidden: true,
        formatter: function(value,rowData,rowIndex){
            return value == 1?'是':(value == 0 ?'否':'');
        }
    },
    {
        field: 'isLegalReview', title: '是否法制审核', width: '140px', halign: 'center', align: 'center', hidden: true,
        formatter: function(value,rowData,rowIndex){
            return value == 1?'是':(value == 0 ?'否':'');
        }
    },
    {
        field: 'dataSourceValue', title: '数据来源', width: '140px', halign: 'center', align: 'center', hidden: true
    },
    {
        field: 'createTimeStr', title: '入库日期', width: '140px', halign: 'center', align: 'center', hidden: true
    },
    {
        field: 'isForce', title: '是否采取强制措施', width: '140px', halign: 'center', align: 'center', hidden: true,
        formatter: function(value,rowData,rowIndex){
            return value == 1?'是':(value == 0 ?'否':'');
        }
    },
    {
        field: 'isCoercion', title: '是否强制执行', width: '140px', halign: 'center', align: 'center', hidden: true,
        formatter: function(value,rowData,rowIndex){
            return value == 1?'是':(value == 0 ?'否':'');
        }
    }
]];

var params = tools.formToJson($("#common_case_search_form"));

/**
 * 导出
 */
function exportCase(){
    top.$.MsgBox.Confirm("提示","确定导出所有数据？",function(){
        params = setParams();
        params.isShow = temp.join(",");
        var json = tools.requestJsonRs("/caseCommonBaseSearchCtrl/export.action?params="+encodeURIComponent(tools.jsonObj2String(params)));
        if(json == null || json.rtState){
            $.MsgBox.Alert_auto("导出成功！");
            location.href = "/caseCommonBaseSearchCtrl/export.action?params="+encodeURIComponent(tools.jsonObj2String(params));
        } else{
            $.MsgBox.Alert_auto(json.rtMsg);
        }
    })
}

/**
 * 重置高级查询条件
 * @returns
 */
function commonCaseRefresh() {
    $('#name').textbox('setValue', '');
    $('#officeName').textbox('setValue', '');
    $('#cardCode').textbox('setValue', '');
    $('#powerName').textbox('setValue', '');
    $('#powerCode').textbox('setValue', '');
    $('#punishmentCode').textbox('setValue', '');
    
    $('#beginfilingDateStr').datebox({validType:''});
    $('#endfilingDateStr').datebox({validType:''});
    $('#beginclosedDateStr').datebox({validType:''});
    $('#endclosedDateStr').datebox({validType:''});
    $('#beginpunishDateStr').datebox({validType:''});
    $('#endpunishDateStr').datebox({validType:''});
    $('#beginpunishExecutDateStr').datebox({validType:''});
    $('#endpunishExecutDateStr').datebox({validType:''});
    
    $('#beginfilingDateStr').datebox('setValue', '');
    $('#endfilingDateStr').datebox('setValue', '');
    $('#beginclosedDateStr').datebox('setValue', '');
    $('#endclosedDateStr').datebox('setValue', '');
    $('#beginpunishDateStr').datebox('setValue', '');
    $('#endpunishDateStr').datebox('setValue', '');
    $('#beginpunishExecutDateStr').datebox('setValue', '');
    $('#endpunishExecutDateStr').datebox('setValue', '');
    
    $('#beginfilingDateStr').datebox({validType:'date'});
    $('#endfilingDateStr').datebox({validType:'date'});
    $('#beginclosedDateStr').datebox({validType:'date'});
    $('#endclosedDateStr').datebox({validType:'date'});
    $('#beginpunishDateStr').datebox({validType:'date'});
    $('#endpunishDateStr').datebox({validType:'date'});
    $('#beginpunishExecutDate').datebox({validType:'date'});
    $('#endpunishExecutDateStr').datebox({validType:'date'});
    
    $('#punishState').combobox('setValue', '0');
    $('#punishExecutState').combobox('setValue', '0');
}

/**
 * 表格加载函数
 * @returns
 */
function initIndexDatagrid(params){
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
        columns: column
    });
}

/**
 * 查询方法
 * @returns
 */
function commonCaseSearch(){
	if($("#common_case_search_form").form('enableValidation').form('validate')){
		if($(".tabshow").hasClass("fa-angle-up")){
	        $("#hideTable").slideUp(800);
	        $(".tabshow").removeClass("fa-angle-up");
	        $(".tabshow").addClass("fa-angle-down");
	        $('.showtext').text("高级筛选");
	    }
	    
	    params = setParams();
	    window.setTimeout(initIndexDatagrid(params),800);
	}
}

/**
 * 返回条件页
 */
function back(){
    $("#commonCaseSearchDiv").show();
    $("#commonCaseDiv").hide();
}

/**
 * 案件信息查看方法
 * @returns
 */
function commonCaseLook(id) {
	/*location.href = "/supervise/caseManager/commonCase/common_case_index_look.jsp?caseId="+ id;
	var params = {
            caseId: id,
            pageUrl: '/supervise/caseManager/commonCase/common_case_index_look.jsp'
    }*/
    var url = contextPath+"/supervise/caseManager/commonCase/common_case_index_look.jsp?caseId="+ id + "&editFlag=4";
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
                }
            }
     );
}

/**
 * 高级筛选显示隐藏
 */
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


/**
 * 删除单个条件
 * @param thisTag ID
 * @param thisType 输入框类型
 * @param thisVal 多选框value
 */
function thisRemove(thisTag, thisType, thisVal){
	debugger;
    if(thisType == 'textbox'){
        $("#"+thisTag).textbox("setValue","");
    }else if(thisType == 'combobox'){
        $("#"+thisTag).combobox("setValue","");
    }else if(thisType == 'checkbox'){
        var val = thisVal.split(",");
        for(var i=0 ; i < val.length; i++){
            $("#"+thisTag+val[i]).checkbox({checked: false});
        }
    }else if(thisType == 'datebox'){
        $("#begincreateTimeStr").datebox("setValue","");
        $("#endcreateTimeStr").datebox("setValue","");
    }
    params = setParams();
    initIndexDatagrid(params);
}

/** 处罚决定类型 */
var punishState = [
    {codeNo:'0', codeName: '全部'},
    {codeNo:'1', codeName: '作出处罚'},
    {codeNo:'2', codeName: '不予处罚'},
    {codeNo:'3', codeName: '撤销立案'}
]

/** 处罚执行类型 */
var punishExecutState = [
    {codeNo:'0', codeName: '全部'},
    {codeNo:'1', codeName: '执行处罚'},
    {codeNo:'2', codeName: '撤销处罚'},
    {codeNo:'3', codeName: '案件终结'}
]

/**
 * 初始化处罚决定类型
 */
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
                $('#beginpunishDateStr').datebox({ disabled: true }); 
                $('#endpunishDateStr').datebox({ disabled: true }); 
            }else{
                $('#beginpunishDateStr').datebox({ disabled: false }); 
                $('#endpunishDateStr').datebox({ disabled: false }); 
            }
            if(value=='1'){
                punishDateName='决定书日期：';
                $('#punishExecutState').combobox({ disabled: false }); 
                $('#beginpunishExecutDateStr').datebox({ disabled: false }); 
                $('#endpunishExecutDateStr').datebox({ disabled: false }); 
            }else if(value=='2'){
                punishDateName='不予处罚日期：';
                $('#punishExecutState').combobox({ disabled: true }); 
                $('#beginpunishExecutDateStr').datebox({ disabled: true }); 
                $('#endpunishExecutDateStr').datebox({ disabled: true }); 
            }else if(value=='3'){
                punishDateName='撤销立案日期：';
                $('#punishExecutState').combobox({ disabled: true }); 
                $('#beginpunishExecutDateStr').datebox({ disabled: true }); 
                $('#endpunishExecutDateStr').datebox({ disabled: true }); 
            }
            $("#punishDateName").html(punishDateName);
        },
        onLoadSuccess: function(data) {
            $(this).combobox('setValue','0');
        }
    });
}

/**
 * 初始化处罚执行类型
 */
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
                $('#beginpunishExecutDateStr').datebox({ disabled: true }); 
                $('#endpunishExecutDateStr').datebox({ disabled: true }); 
            }else{
                $('#beginpunishExecutDateStr').datebox({ disabled: false }); 
                $('#endpunishExecutDateStr').datebox({ disabled: false }); 
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

