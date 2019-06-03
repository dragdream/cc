/**
 * 
 */
var personIds = []; //定义人员ID对象
var personJson = [];//定义人员对象
var ctrlType = '';
var loginDeptId = '';
var loginSubId = '';
var recordId = '';
var orgSys = '';
var inspListId = '';
var cardTypeJsons = [];
var inspItems = [];
var inspItemsTemp = [];
var saveingFlag = false;
var datagrid = null;
var isTrueJson = [
    {codeNo:'1', codeName: '合格'},
    {codeNo:'2', codeName: '不合格'}
]

function doInit() {
    initParams();
    initSubjectInput();
}
function initParams() {
    ctrlType = $('#ctrlType').val();
    loginDeptId = $('#loginDeptId').val();
    loginSubId = $('#loginSubId').val();
    recordId = $('#recordId').val();
    orgSys = $('#orgSysCtrl').val();
    if (recordId != null && recordId != "") {
        var json = tools.requestJsonRs("/inspListRecordCtrl/getById.action", {
            id : recordId
        });
        if (json.rtState) {
            bindJsonObj2Easyui(json.rtData, "inspRecordInput_base_form");
            bindJsonObj2Easyui(json.rtData, "inspRecordInput_result_form");
            initJsonListRadio(isTrueJson,'isInspectionPass',json.rtData.isInspectionPass);
        }
        //初始化检查项
        var params = [];
        if(json.rtData.inspItems!=null && json.rtData.inspItems!=''){
            params = json.rtData.inspItems;
            if (inspItems.length == 0) {
                for (var index = 0; index< params.length; index++) {
                    inspItems.push({
                        id : params[index].id,
                        inspItemName : params[index].inspItemName,
                        isInspectionPass : params[index].isInspectionPass
                    });
                }
                inspItemsTemp = inspItems;
            }
        }
//        inspItemsTemp = inspItems;
        //执法人员信息加载
        var personJsonStr = json.rtData.personJsonStr;
        var personJson;
        var param;
        if(personJsonStr != null && personJsonStr != '' && personJsonStr != 'null'){
            personJson = personJsonStr.split(",");
            for (var i = 0; i < personJson.length; i++){
                personIds.push(personJson[i]);
            }
            param = {ids: personIds.join(',')};
            if(personIds == null || personIds.length < 1){
                param = {ids: 'empty'};
            }
        }else{
            param = {ids: 'empty'};
        }
        initPartyTypeInput(json.rtData.partyType);//加载当事人
        initCardTypeInput(cardTypeJsons,json.rtData.partyType + "" + json.rtData.cardType);// 当事人证件类型
        initAddPersonDatagrid(param);//修改人员加载数据
        //初始化时间
        initInspectionDateStrComboBox(json.rtData.inspectionDateStr);
        inspListChange(json.rtData.inspectionListId);
    } else {
        inspListChange();
        initPartyTypeInput();
        initInspectionDateStrComboBox();
        initJsonListRadio(isTrueJson,'isInspectionPass')
    }
}


/**
 * 初始化本单位对应许可主体，填报，不可搜索，单选
 */
function initSubjectInput(value){
    //所属主体
    var json = tools.requestJsonRs("/permissionItemCtrl/getSubjectListByOneself.action");
    if(json.rtState) {
        $('#subjectId').combobox({
            data: json.rtData,
            valueField: 'id',
            textField: 'subName',
            panelHeight: 'auto',
            editable: false,
            prompt : '请选择',
            onLoadSuccess:function(data){
                if(value != null && value != "" && value != '0'){
                    $(this).combobox('setValue',value);
                }else{
                    $(this).combobox('setValue',data[0].id);
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
            }
        });
    }
}
/**
 * 初始化行政相对人类型
 */
function initPartyTypeInput(value) { 
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "PERMISSION_PARTY_TYPE"});
    if(json.rtState) {
        $('#partyType').combobox({
            data: json.rtData,
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
                    $('#contactPhone').textbox({ disabled: true });
                    $('#address').textbox({ disabled: true });
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
                    $('#contactPhone').textbox({ disabled: false });
                    $('#address').textbox({ disabled: false });
                }else{
                    $('#cardType').combobox({disabled: true });
                    $('#partyName').textbox({ disabled: true });
                    $('#contactPhone').textbox({ disabled: true });
                    $('#address').textbox({ disabled: true });
                    $('#cardCode').textbox({ disabled: true });
                }
            }
        });
    }
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
/**
 * 填报检查单模版变更
 * 
 * @returns
 */
function inspListChange(inspectionListId) {
    var params = {
        loginDeptId : loginDeptId,
        loginSubId : loginSubId,
        ctrlType : ctrlType,
        orgSys : orgSys
    };
    // alert(params);
    var result = tools.requestJsonRs(
            "/inspListBaseCtrl/getValidInspLists.action", params);
    if (result.rtState) {
        $('#inspListModel').combobox({
            data : result.rtData,
            panelHeight : 'auto',
            prompt : '输入关键字后自动搜索',
            editable : true,
            valueField : 'id',
            textField : 'listName',
            onLoadSuccess : function() {
                if (inspectionListId != null && inspectionListId != "") {
                    $('#inspListModel').combobox('setValue', inspectionListId);
                }
            },
            onChange : function() {
                inspListId = $('#inspListModel').combobox('getValue');
                if(inspectionListId == inspListId){
                    inspItems = inspItemsTemp;
                }else{
                    inspItems = [];
                }
                initItemsDatagrid(inspItems);
            },
            onHidePanel : function() {
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
            }
        });
    }
}

/**
 * 查询所选择的检查单下的检查项
 * 
 * @returns
 */
function openInspItemInput() {
    if (inspListId == null || inspListId == '') {
        $.MsgBox.Alert_auto("请选择本次检查使用的检查模版");
        return false;
    }
    var params = {
        id : inspListId,
        ctrlType : ctrlType,
        loginDeptId : loginDeptId,
        loginSubId : loginSubId
    }
    var url = contextPath + "/inspListRecordCtrl/chocieInspItemPage.action?"
            + $.param(params);
    top.bsWindow(url,"选择检查项",
                    {
                        width : "900",
                        height : "500",
                        buttons : [ {
                            name : "关闭",
                            classStyle : "btn-alert-gray"
                        } , {
                            name : "确定",
                            classStyle : "btn-alert-blue"
                        }],
                        submit : function(v, h) {
                            var cw = h[0].contentWindow;
                            if (v == "确定") {
                                var items = cw.getSelectedItems();
                                if (inspItems == null || inspItems.length == 0) {
                                    for ( var index in items) {
                                        inspItems.push({
                                                    id : items[index].id,
                                                    inspItemName : items[index].inspItemName,
                                                    isInspectionPass : 1
                                                });
                                    }
                                } else {

                                    for (var newIndex = 0; newIndex < items.length; newIndex++) {
                                        var flag = true;// 该检查项是否新增标记
                                        for (var oldIndex = 0; oldIndex < inspItems.length; oldIndex++) {
                                            if (inspItems[oldIndex].id == items[newIndex].id) {
                                                flag = false;// 已存在，非新增
                                                break;
                                            }
                                        }
                                        if (flag) {
                                            inspItems.push({
                                                        id : items[newIndex].id,
                                                        inspItemName : items[newIndex].inspItemName,
                                                        isInspectionPass : 1
                                                    });
                                        }
                                    }
                                }
                                initItemsDatagrid(inspItems);
                                return true;
                            } else if (v == "关闭") {
                                return true;
                            }
                        }
                    });
}

/**
 * 删除添加的违法依据
 * 
 * @returns
 */
function deleteItem(id) {
    for (var index = 0; index < (inspItems.length); index++) {
        if (inspItems[index].id == id) {
            inspItems.splice(index, 1);
            break;
        }
    }
    initItemsDatagrid(inspItems);
}

function initItemsDatagrid(params) {
    // inspItems = params;
    editIndex = null;
    datagrid = $('#record_items_table').datagrid({
        data : params,
        pagination : false,
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        checkbox : false,
        border : false,
        striped: true,
        fitColumns : true, // 列是否进行自动宽度适应
        singleSelect : true, // 为true只能选择一行
        checkOnSelect : false,
        selectOnCheck : false,
        pageSize : 20,
        pageList : [ 10, 20, 50, 100 ],
        fit : false,
        nowrap : true,
        onLoadSuccess : function(data) {

            $('.ctrlRadio').on('click',function() {
                var value = $(this).parents(
                        '.control-div').find(
                        '.inspItemId').val();
                for ( var index in inspItems) {
                    if (inspItems[index].id == value) {
                        inspItems[index].isInspectionPass = $(this).val();
                        break;
                    }
                }
            });
        },
        columns : [ [
                {field:'ID',title:'序号',align:'center', 
                    formatter:function(value,rowData,rowIndex){
                        return rowIndex+1;
                    }
                },
                {
                    field : 'inspItemName',
                    title : '检查项名称',
                    width : 50,
                    halign : 'center',
                    formatter : function(value, rowData,
                            rowIndex) {
                        return '<lable class="custom-text-overflow table-td-full-width" title="'
                                + value
                                + '">'
                                + value
                                + '</lable>';
                    }
                },
                {
                    field : 'isInspectionPass',
                    title : '检查结果',
                    width : 40,
                    align : 'center',
                    editor : {
                        type : 'radiobutton',
                        option : {required:true}
                    },
                    formatter : function(value, rowData,rowIndex) {
                        var optStr = "<div class='control-div'><input type='hidden' class='inspItemId' value='"
                                + rowData.id + "'/>";
                        
                        optStr = optStr
                                + '<label class="radio" for="isInspectionPass'+rowIndex+'1"><span class="radio-bg"></span><input type="radio" class="ctrlRadio" id="isInspectionPass'+rowIndex+'1" name="isInspectionPass'
                                + rowIndex + '"';
                        if (value != null && value == 1) {
                            optStr = optStr + ' checked ';
                        }
                        optStr = optStr
                                + 'value="1"/><span class="radio-on"></span>合格</label>';
                        optStr = optStr
                                + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
                        optStr = optStr
                                + '<label class="radio" for="isInspectionPass'+rowIndex+'2"><span class="radio-bg"></span><input type="radio" class="ctrlRadio" id="isInspectionPass'+rowIndex+'2" name="isInspectionPass'
                                + rowIndex + '"';
                        if (value != null && value == 2) {
                            optStr = optStr + ' checked ';
                        }
                        optStr = optStr
                                + 'value="2"/><span class="radio-on"></span>不合格</label></div>';                     
                        return optStr;
                    }
                },
                {
                    field : '___',
                    title : '操作',
                    formatter : function(e, rowData) {
                        var optStr = "<span title='删除'><a href=\"#\" onclick=\"deleteItem('" + rowData.id + "')\"><i class='fa fa-trash-o common-red'></i></a></span>";
                        return optStr;
                    },
                    width : 10,
                    align : 'center'
                } ] ]
    });

}


function initDatagridListRadion(json,id,/*param,*/value){
    var page = "";
    for(var i=0 ; i < json.length; i++){
        page = page + '<input type="radio" name="'+id+'" id="'+id+json[i].codeNo+'" class="easyui-radiobutton" '
            + 'style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="50px"'    
            + 'required value="'+json[i].codeNo+'" label="'+json[i].codeName+'"/>';
    }
    $('#'+id).html(page);
    $.parser.parse($('#'+id));
    if(value==null || value==''){
        $("#"+id+"1").radiobutton('check');
    } else {
        $("#"+id+value).radiobutton('check');
    }
}

$.extend($.fn.datagrid.methods, {
    editCell: function(jq,param){
        return jq.each(function(){
            var opts = $(this).datagrid('options');
            var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field){
                    col.editor = null;
                }
            }
            $(this).datagrid('beginEdit', param.index);
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});
var editIndex = undefined;
function endEditing(){
    if (editIndex == undefined){return true}
    if ($('#coercionManage_datagrid').datagrid('validateRow', editIndex)){
        $('#coercionManage_datagrid').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}
function onClickCell(index, field){
    if (endEditing()){
        $('#coercionManage_datagrid').datagrid('selectRow', index)
                .datagrid('editCell', {index:index,field:field});
        editIndex = index;
    }
}




/**
 * 保存检查记录信息
 * @returns
 */
function saveRecord() {

    if ($('#inspRecordInput_base_form').form('enableValidation').form(
            'validate') && $('#inspRecordInput_result_form').form('enableValidation').form(
            'validate')) {
        var baseParams = tools.formToJson($('#inspRecordInput_base_form'));
        var resultParams = tools.formToJson($('#inspRecordInput_result_form'));
        var finalParams = {
            id : recordId,
            inspectionListId : inspListId,
            inspectionNumber : baseParams.inspectionNumber,
            inspectionDateStr : baseParams.inspectionDateStr,
            inspectionAddr : baseParams.inspectionAddr,
            isInspectionPass : resultParams.isInspectionPass,
            resultDiscribe : resultParams.resultDiscribe,
            subjectId : loginSubId,
            departmentId : loginDeptId,
            inspItems : inspItems,
            partyType : baseParams.partyType,
            partyName : baseParams.partyName,
            cardType : baseParams.cardType.substr(baseParams.cardType.length-2),
            cardCode : baseParams.cardCode,
            contactPhone : baseParams.contactPhone,
            address : baseParams.address,
        };
        var personRows = $("#insp_add_person_datagrid").datagrid("getRows");
        if(personRows == null || personRows.length < 2){
            $.MsgBox.Alert_auto("请添加2个执法人员");
            $('html, body').animate({scrollTop: $("#insp_add_person_div").offset().top}, 500);
            //$('#btn').linkbutton('enable');
            return false;
        }
        personJson = []//执法人员对象
        for(var i=0;i<personRows.length;i++){
            var obj = {};
            obj.recordMainId = recordId;
            obj.identityId = personRows[i].id;
            obj.officeName = personRows[i].name;
            obj.cardCode = personRows[i].enforcerCode;
            obj.subjectId = loginSubId;
            personJson.push(obj);
        }
        //执法人员对象转成json字符串
        finalParams.personJsonStr = tools.parseJson2String(personJson);
        
        var result = tools.requestJsonRs(
                "/inspListRecordCtrl/saveRecordInfo.action", finalParams);
        return true;
    }
}

/**
 * 对时间组件进行控制
 */
function initInspectionDateStrComboBox(adate) {
    $("#inspectionDateStr").datebox().datebox('calendar').calendar(
            {
                validator : function(date) {
                    var now = new Date();
                    var d1 = new Date(now.getFullYear(), now.getMonth(), now
                            .getDate());
                    return date <= d1;
                }
            });
    $('#inspectionDateStr').datebox('setValue', adate);
    $("input", $("#inspectionDateStr").next("span")).blur(function() {
        var sDate = $('#inspectionDateStr').datebox('getValue');
        if (sDate != null && sDate != "") {
            if (!isNaN(Date.parse(sDate))) {
                $('#inspectionDateStr').datebox('setValue', sDate);
            } else {
                $.MsgBox.Alert_auto("请输入正确的时间格式");
                $('#inspectionDateStr').datebox('setValue', "");
            }
        }
    });
}

/**
 * 查询人员信息
 * @returns
 */
function inspFindPerson(){
    var subjectId = $('#subjectId').combobox('getValue');
    if (subjectId == null || subjectId == "" || subjectId == 'null') {
        $.MsgBox.Alert_auto("请选择执法主体");
        $('html, body').animate({scrollTop: $("#subjectId_td").offset().top}, 500);
        return false;
    }
    var url=contextPath+"/caseCommonStaffCtrl/commonCaseAddStaff.action?subjectId="+subjectId;
    top.bsWindow(url ,"添加执法人员",{width:"900",height:"500",buttons:
        [
            {name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "保存") {
                var personList = cw.savePersonList();
                
                if(personIds.length + personList.length > 2){
                    top.$.MsgBox.Alert("提示","总共只能添加2名执法人员");
                    return false;
                }
                if(personList == null || personList.length == 0) {
                    for(var index in personList) {
                        personIds.push(personList[index].id);
                    }
                } else {
                    for(var index in personList) {
                        if(personIds.indexOf(personList[index].id) == -1) {
                            personIds.push(personList[index].id);
                        }
                    }
                }
                var params = {
                    ids: personIds.join(","),
                    editFlag: '3'
                };
                initAddPersonDatagrid(params);
                return true;
            } else if(v=="关闭"){
                return true;
            }
        }
    });
}

/**
 * 表格加载函数
 * @returns
 */
function initAddPersonDatagrid(params){
    datagrid = $('#insp_add_person_datagrid').datagrid({
        url: contextPath + '/caseCommonStaffCtrl/findListByPersonIds.action',
        queryParams: params,
        pagination: false,
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
//        toolbar: '#toolbar', // 工具条对象
        checkbox: true,
        border: false,
        striped: true,//隔行变色
        /* idField:'formId',//主键列 */
        fitColumns: true, // 列是否进行自动宽度适应
        singleSelect: true, //为true只能选择一行
        nowrap: true,
        onLoadSuccess: function(data) {
        },
        columns: [[
            { 
                field: 'id', checkbox: true, title: "ID", width: 20, halign: 'center', align: 'center', hidden: true
            },
            {field:'ID',title:'序号',align:'center', width: 10,
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field: 'subjectName', title: '所属主体', width: 40, halign: 'center', align: 'left',
                formatter: function(value, rowData) {
                    if(value == null || value == 'null') {
                        value = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</lable>"
                    return lins;
                }
            },
            {
                field: 'name', title: '执法人员 ', width: 30, halign: 'center', align: 'center'
            },
            {
                field: 'code', title: '执法证号 ', width: 30, halign: 'center', align: 'center'
            },
            {
                field: '___',
                title: '操作' , halign: 'center', align: 'center',
                formatter: function(e, rowData) {
                    var optStr = "";
                        optStr = "<span title='删除'><a href=\"#\" onclick=\"deleteAddPerson('" + rowData.id + "')\"><i class='fa fa-trash-o common-red'></i></a></span>"
                    return optStr;
                },
                width: 10
            }
        ]]
    });
}

/**
 * 删除添加的执法人员
 * @param id 执法人员的ID
 * @returns
 */
function deleteAddPerson(id){
    for(var index in personIds) {
        if(personIds[index] == id) {
            personIds.splice(index, 1);
            break;
        }
    }
    var params;
    if(personIds.length == 0) {
        params = {ids: "empty"};
    }else {
        params = {
            ids: personIds.join(",")
        };
    }
    initAddPersonDatagrid(params);
}


/**
 * 初始化单选按钮
 * @param json
 * @param id
 * @param value
 */
function initJsonListRadio(json,id,/*param,*/value) {
    var page = "";
    for(var i=0 ; i < json.length; i++){
        page = page + '<input type="radio" name="'+id+'" id="'+id+json[i].codeNo+'" class="easyui-radiobutton" '
            + 'style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="50px"'    
            + 'required value="'+json[i].codeNo+'" label="'+json[i].codeName+'"/>';
    }
    $('#'+id+'Td').html(page);
    $.parser.parse($('#'+id+'Td'));
    if(value==null || value==''){
        $("#"+id+"1").radiobutton('check');
    } else {
        $("#"+id+value).radiobutton('check');
    }
}



