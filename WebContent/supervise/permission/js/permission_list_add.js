var id = $("#id").val();
var itemJsons = [];
var partyTypeJsons = [];
var cardTypeJsons = [];

/**
 * 初始化
 */
function doInit(){
	dateValidate('beginValidDateStr', 'endValidDateStr');
	dateValidate('applyDateStr', 'acceptDateStr');
	dateValidate('acceptDateStr', 'decisionDateStr');
	dateValidate('decisionDateStr', 'sendDateStr');
	dateSysdateValidate('sendDateStr');
    if(id==0){
    	initSubjectInput();// 办理主体
    	initPermissionItem();// 许可事项
        initCodeListInput("PERMISSION_LIST_TYPE","listType");// 办件类型
        initCodeListInput("PERMISSION_CURRENT_STATE","currentState");// 办理状态
        initCodeListInput("PERMISSION_APPLY_WAY","applyWay");// 申请方式
        initCodeListInput("PERMISSION_ACCEPT_RESULT","acceptResult");// 受理结果
        initCodeListInput("COMMON_SENT_WAY","sendWay");// 送达方式
        initCodeListInput("PERMISSION_TYPE","permissionType");// 许可类别
    }else{
        var json = tools.requestJsonRs("/permissionListCtrl/getPermissionListById.action?id=" + id);
        if(json.rtState){
            if(json.rtData != null && json.rtData != ''){
            	bindJsonObj2Easyui(json.rtData , "form1");
            	initSubjectInput(json.rtData.subjectId);// 办理主体
            	initPermissionItem(json.rtData.itemId);// 许可事项
                initCodeListInput("PERMISSION_LIST_TYPE","listType",json.rtData.listType);// 办件类型
                initPartyTypeInput(partyTypeJsons, json.rtData.partyType);//行政相对人类型
                initCardTypeInput(cardTypeJsons,json.rtData.partyType+""+json.rtData.cardType);// 行政相对人证件类型
                initCodeListInput("PERMISSION_CURRENT_STATE","currentState",json.rtData.currentState);// 办理状态
                initCodeListInput("PERMISSION_APPLY_WAY","applyWay",json.rtData.applyWay);// 申请方式
                initCodeListInput("PERMISSION_ACCEPT_RESULT","acceptResult",json.rtData.acceptResult);// 受理结果
                initCodeListInput("COMMON_SENT_WAY","sendWay",json.rtData.sendWay);// 送达方式
                initCodeListInput("PERMISSION_TYPE","permissionType",json.rtData.permissionType);// 许可类别
            }
        }
    }
}

/**
 * 初始化许可事项
 */
function initPermissionItem(value){
    var json = tools.requestJsonRs("/permissionListCtrl/getPermissionItemByOneself.action");
    if(json.rtState) {
    	itemJsons = json.rtData;
        $('#itemId').combobox({
            data: json.rtData,
            valueField: 'id',
            textField: 'name',
            panelHeight: 'auto',
            panelMaxHeight : 150,
            prompt : '请选择',
            onLoadSuccess:function(){
                if(value != null && value != "" && value != '0'){
                    $(this).combobox('setValue',value);
                    $('#partyType').combobox({ disabled: false });
                	var partyTypes;
                	var partyTypeValues;
                	for(var i=0;i<itemJsons.length;i++){
                		if(itemJsons[i].id == value){
                			partyTypes = itemJsons[i].partyType.split(",");
                			partyTypeValues = itemJsons[i].partyTypeValue.split("，");
                			break;
                		}
                	}
                	var partyTypeJson;
                	for(var j=0;j<partyTypes.length;j++){
                		partyTypeJson = {codeNo:partyTypes[j],codeName:partyTypeValues[j]};
                		partyTypeJsons.push(partyTypeJson);
                	}
                }else{
                	$('#partyType').combobox({ disabled: true });
                	$('#cardType').combobox({ disabled: true });
                	$('#partyName').textbox({ disabled: true });
                	$('#cardCode').textbox({ disabled: true });
                }
            },
            onHidePanel:function(){
            	var _options = $(this).combobox('options');
                var _data = $(this).combobox('getData');/* 下拉框所有选项 */
                var _value = $(this).combobox('getText');/* 用户输入的值 */
                var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
                for (var i = 0; i < _data.length; i++) {
                    if (_data[i][_options.textField] == _value) {
                        _b = true;
                        break;
                    }
                }
                if (!_b) {
                    $(this).combobox('setValue', '');
                }
            },
            onChange:function(value){
            	if(value == ''){
                	$('#partyType').combobox({ disabled: true });
                	$('#cardType').combobox({ disabled: true });
                	$('#partyName').textbox({ disabled: true });
                	$('#cardCode').textbox({ disabled: true });
                }else{
                	$('#partyType').combobox({ disabled: false });
                	
                	var partyTypes;
                	var partyTypeValues;
                	for(var i=0;i<itemJsons.length;i++){
                		if(itemJsons[i].id == value){
                			partyTypes = itemJsons[i].partyType.split(",");
                			partyTypeValues = itemJsons[i].partyTypeValue.split("，");
                			break;
                		}
                	}
                	partyTypeJsons = [];
                	var partyTypeJson;
                	for(var j=0;j<partyTypes.length;j++){
                		partyTypeJson = {codeNo:partyTypes[j],codeName:partyTypeValues[j]};
                		partyTypeJsons.push(partyTypeJson);
                	}
                	initPartyTypeInput(partyTypeJsons);
                }
            }
        });
    }
}

/**
 * 初始化行政相对人类型
 */
function initPartyTypeInput(partyTypeJsons,value) {
        $('#partyType').combobox({
            data: partyTypeJsons,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            editable: false,
            prompt : '请选择',
            onLoadSuccess:function(){
                if(value != null && value != "" && value != '0'){
                    $(this).combobox('setValue',value);
                    $('#cardType').combobox({ disabled: false });
                	$('#partyName').textbox({ disabled: false });
                	var params = {
                            parentCodeNo: "PERMISSION_CARD_TYPE",
                            codeNo: value
                        };
                        cardTypeJsons = [];
                        var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action", params);
                        if(result.rtState) {
                        	cardTypeJsons = result.rtData;
                        }
                }else{
                	$('#cardType').combobox({ disabled: true });
                	$('#partyName').textbox({ disabled: true });
                	$('#cardCode').textbox({ disabled: true });
                }
            },
            onChange: function() {
                var partyType = $('#partyType').combobox('getValue');
                if(partyType != "") {
                    var params = {
                        parentCodeNo: "PERMISSION_CARD_TYPE",
                        codeNo: partyType
                    };
                    cardTypeJsons = [];
                    var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action", params);
                    if(result.rtState) {
                    	cardTypeJsons = result.rtData;
                    }
                    initCardTypeInput(cardTypeJsons);
                    $('#cardType').combobox({disabled: false });
                    $('#partyName').textbox({ disabled: false });
                }else{
                	$('#cardType').combobox({disabled: true });
                	$('#partyName').textbox({ disabled: true });
                	$('#cardCode').textbox({ disabled: true });
                }
            }
        });
}

/**
 * 初始化证件类型
 */
function initCardTypeInput(cardTypeJsons,value) {
    $('#cardType').combobox({
        data: cardTypeJsons,
        valueField: 'codeNo',
        textField: 'codeName',
        editable: false,
        prompt : '请选择',
        panelHeight: 'auto',
        onLoadSuccess:function(){
            if(value != null && value != "" && value != '0'){
                $(this).combobox('setValue',value);
            }else{
            	$('#cardCode').textbox({ disabled: true });
            }
        },
        onChange: function() {
            var cardType = $('#cardType').combobox('getValue');
            cardType = cardType.substr(2);
            if(cardType != "") {
            	$('#cardCode').textbox({ disabled: false });
                if ('01' == cardType){// 身份证号
                	$('#cardCode').textbox({validType:['length[0,18]','idCard']});
                }else if ('05' == cardType){// 统一社会信用代码
                	$('#cardCode').textbox({validType:['length[0,18]','organizationCode']});
                }else{
                    $('#cardCode').textbox({validType:'length[0,18]'});
                }
            }else{
                $('#cardCode').textbox({ disabled: true });
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

/**
 * 行政相对人类型改变事件
 * @param data
 * @returns
 */
function initXkXdrTypeRefresh(data){
    for(var i=0 ; i < data.length; i++){
        $('#xkXdrType'+data[i].codeNo).radiobutton({
            label: data[i].codeName,
            value: data[i].codeNo,
            width: 15,
            height: 15,
            labelAlign: 'left',
            labelPosition: 'after',
            onChange: function(){
                var partyType = $("#xkXdrTypeTd input[name='xkXdrType']:checked").val();
                partyTypeValidate(partyType);
            }
        });
    }
}

/**
 * 行政相对人类型对应属性校验
 * @param partyType
 * @returns
 */
function partyTypeValidate(partyType){
    if(partyType != null && partyType != ''){
        if('1' == partyType){
        $("#partyType1").show();
        $("#partyType2").hide();
        $("#partyType3").hide();
        //行政相对人类型 自然人
        $('#xkXdrZjlx').combobox({required:true, novalidate:true, missingMessage:'请选择证件类型'});
        $('#xkXdrZjhm').textbox({required:true, novalidate:true, missingMessage:'请输入证件号码'});
        //取消校验
        $('#xkFrdb').textbox({required:false});
        $('#xkFrSfzh').textbox({required:false});
        $('#xkXdrShxym').textbox({required:false});
    }else if('2' == partyType || '3' == partyType){
        $("#partyType1").hide();
        $("#partyType2").show();
        $("#partyType3").show();
        //行政相对人类型 其他
        $('#xkFrdb').textbox({required:true, novalidate:true, missingMessage:'请输入法定代表人' });
        $('#xkFrSfzh').textbox({required:true, novalidate:true, missingMessage:'请输入法定代表人身份证号' });
        $('#xkXdrShxym').textbox({required:true, novalidate:true, missingMessage:'请输入统一社会信用码' });
        //取消校验
        $('#xkXdrZjlx').combobox({required:false});
        $('#xkXdrZjhm').textbox({required:false});
    }else if('4' == partyType){
        $("#partyType1").hide();
        $("#partyType2").show();
        $("#partyType3").hide();
        //行政相对人类型 其他
        $('#xkFrdb').textbox({required:true, novalidate:true, missingMessage:'请输入法定代表人' });
        $('#xkFrSfzh').textbox({required:true, novalidate:true, missingMessage:'请输入法定代表人身份证号' });
        $('#xkXdrShxym').textbox({required:true, novalidate:true, missingMessage:'请输入统一社会信用码' });
        //取消校验
        $('#xkXdrZjlx').combobox({required:false});
        $('#xkXdrZjhm').textbox({required:false});
    }
    $.parser.parse($('#partyType1'));
    $.parser.parse($('#partyType2'));
    $.parser.parse($('#partyType3'));
    }
}

/**
 * 提交表单
 * @returns
 */
function save(){
	if($('#form1').form('enableValidation').form('validate')){    
        var params = tools.formToJson($("#form1"));
        // 部门、地区、执法系统手动设置
        var subJson = tools.requestJsonRs("/subjectSearchController/getSubjectById.action", {id:params.subjectId});
        if(subJson.rtState){
        	params.departmentId = subJson.rtData.departmentId;
        	params.area = subJson.rtData.areaId;
        	params.orgSys = subJson.rtData.orgSysId;
        	params.isDepute = subJson.rtData.isDepute;
        }
        var json = tools.requestJsonRs("/permissionListCtrl/savePermissionList.action",params);
        return json.rtState;
    }else{
        return false;
    }
    
    /*if($('#form1').form('enableValidation').form('validate')){    
        var param = tools.formToJson($("#form1"));
        //校验邮编
        var text = /^[0-9]{6}$/;
        var flag = text.test(param.postCode);
        if(!flag){
            $.MsgBox.Alert_auto("邮编输入不正确！");
            return false;
        }
             
        console.log(param);
        param.isDelete = 0;
        param.isExamine = 0;
        param.isGovernment = 0;
        param.orgSys = $('#orgSys').combobox('getValues')+"";
        if(id!=0){//编辑
            var json = tools.requestJsonRs("/departmentInfoController/update.action",param);
            return json.rtState;
        }else{//新增
            var json = tools.requestJsonRs("/departmentInfoController/save.action",param);
            return json.rtState;
        }
    }else{
        return false;
        
    }*/
    
}

/**
 * 修改
 * @returns
 */
function update(){
    if($('#form1').form('enableValidation').form('validate')){    
        var params = tools.formToJson($("#form1"));
        // 部门、地区、执法系统手动设置
        var subJson = tools.requestJsonRs("/subjectSearchController/getSubjectById.action", {id:params.subjectId});
        if(subJson.rtState){
        	params.departmentId = subJson.rtData.departmentId;
        	params.area = subJson.rtData.areaId;
        	params.orgSys = subJson.rtData.orgSysId;
        	params.isDepute = subJson.rtData.isDepute;
        }
        var json = tools.requestJsonRs("/permissionListCtrl/updateOrDelete.action",params);
        return json.rtState;
    }else{
        return false;
    }
}

/*$('#xkYxqziStr').datebox().datebox('calendar').calendar({
    validator: function(date){
        var d1 = $('#xkYxqzStr').val();
//        var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
//        var d2 = new Date(now.getFullYear(), now.getMonth(), now.getDate()+10);
        return d1<=date;
    }
});*/