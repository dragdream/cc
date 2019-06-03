var id = $("#id").val();
var addPower = [];
var gistJson = [];
var actSubject = '';

/**
 * 初始化
 */
function doInit(){
    // 获取本单位对应主体
	var json = tools.requestJsonRs("/permissionItemCtrl/getActSubject.action");
    if(json.rtState){
        actSubject = json.rtData;
    }
    initIsCollectFees();// 是否收费
    if(id==0){// 新增
        initSubjectInput();// 许可主体
        initCodeListInput("PERMISSION_LIST_TYPE","listType");// 办件类型
        initCodeListMultipleInput("PERMISSION_PARTY_TYPE","partyType");// 服务对象
        initCodeListMultipleInput("PERMISSION_HANDLE_WAY","handleWay");// 办理形式
        initHandDatagrid({ids: 'empty'});//许可职权信息加载 
        initGistDatagrid({powerId: 'empty'}); //设定依据
    }else{// 修改
        var json = tools.requestJsonRs("/permissionItemCtrl/getPermissionItemById.action?id=" + id);
        if(json.rtState){
            if(json.rtData != null && json.rtData != ''){
                bindJsonObj2Easyui(json.rtData , "form1");
                console.log(json.rtData);
                initSubjectInput(json.rtData.subjectId);
                initCodeListInput("PERMISSION_LIST_TYPE","listType",json.rtData.listType);// 办件类型
                initCodeListMultipleInput("PERMISSION_PARTY_TYPE","partyType",json.rtData.partyType);// 服务对象
                initCodeListMultipleInput("PERMISSION_HANDLE_WAY","handleWay",json.rtData.handleWay);// 办理形式
                if(json.rtData.isCollectFees == 1){
                	$("#isCollectFees1").radiobutton({checked: true});
                	$('#isCollectFees_tbody').show();
                }else if(json.rtData.isCollectFees == 0){
                	$("#isCollectFees0").radiobutton({checked: true});
                	$('#isCollectFees_tbody').hide();
                }
                var powerJsonStr = json.rtData.powerJsonStr;
                var powerJson;
                if(powerJsonStr != null && powerJsonStr != '' && powerJsonStr != 'null'){
                    powerJson = powerJsonStr.split(",");
                    var params;
                    var paramsGist;
                    for (var i = 0; i < powerJson.length; i++){
                        addPower.push(powerJson[i]);
                    }
                    if(addPower == null || addPower.length < 1){
                        params = {ids: 'empty',actSubject: actSubject};
                        paramsGist = {powerId: 'empty',gistType: '03'};
                    } else {
                        params = {
                            ids : addPower.join(","),
                            actSubject : actSubject
                        };
                        paramsGist = {
                            powerId : addPower.join(","),
                            gistType : '03'
                        };
                    }
                    initHandDatagrid(params);//许可职权信息加载 
                    initGistDatagrid(paramsGist);  //设定依据信息加载
                    
                    var gistJsonStr = json.rtData.gistJsonStr;
                    gistJson = [];
                    if(gistJsonStr != null && gistJsonStr != '' && gistJsonStr != 'null'){
                        gistJson = gistJsonStr.split(",");
                    }
                }else{
                    initHandDatagrid({ids: 'empty'});//许可职权信息加载 
                    initGistDatagrid({powerId: 'empty'}); //设定依据
                }
            }
        }
    }
}

/**
 * 是否收费
 * @returns
 */
/*function initIsCollectFees(){
    $('#isCollectFees').checkbox({
        label: '收费',
        value: '1',
        width: 15,
        height: 15,
        labelAlign: 'left',
        labelPosition: 'after',
        labelWidth: '110',
        onChange: function(){
            var isCollectFees = $("#tableFirst input[name='isCollectFees']").is(':checked');
            if(isCollectFees){
                $('#isCollectFees_tbody').show();
                $.parser.parse('#isCollectFees_tbody');
            }else{
                $('#isCollectFees_tbody').hide();
                $.parser.parse('#isCollectFees_tbody');
            }
        }
    });
}*/

/**
 * 是否收费
 * @returns
 */
function initIsCollectFees(){
    for(var i=0 ; i < 2; i++){
        $('#isCollectFees'+i).radiobutton({
            onChange: function(){
                var isCollectFees = $("#tableFirst input[name='isCollectFees']:checked").val();
                if(isCollectFees != null && isCollectFees != ''){
                	if(isCollectFees==1){
                        $('#isCollectFees_tbody').show();
                        $.parser.parse('#isCollectFees_tbody');
                    }else{
                        $('#isCollectFees_tbody').hide();
                        $.parser.parse('#isCollectFees_tbody');
                    }
                }
            }
        });
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
 * 提交表单
 * @returns
 */
function save(){
    if($('#form1').form('enableValidation').form('validate')){    
        var params = tools.formToJson($("#form1"));
        
        // 服务对象
        var partyType = $('#partyType').combobox('getValues');
        if(partyType != null && partyType.length > 0){
            params.partyType = partyType.join(',');
        }
        
        // 办理形式
        var handleWay = $('#handleWay').combobox('getValues');
        if(handleWay != null && handleWay.length > 0){
            params.handleWay = handleWay.join(',');
        }
        
        // 许可职权
        var powerList = $("#powerDatagrid").datagrid("getRows");
        if(powerList == null || powerList.length < 1){
            $.MsgBox.Alert_auto("请添加至少1条许可职权");
            $('html, body').animate({scrollTop: $("#setting_panel").offset().top}, 500);
            return false;
        }
        powerJson = []//许可职权对象
        for(var i=0;i<powerList.length;i++){
            var obj = {};
            obj.powerId = powerList[i].id;
            obj.powerCode = powerList[i].code;
            obj.powerName = powerList[i].name;
            powerJson.push(obj);
        }
        //许可职权对象转成json字符串
        params.powerJsonStr = tools.parseJson2String(powerJson);
        
        var gistList = $("#settingDatagrid").datagrid("getSelections");
        if(gistList == null || gistList.length < 1){
            $.MsgBox.Alert_auto("请添加至少1条设定依据");
            $('html, body').animate({scrollTop: $("#gist_panel").offset().top}, 500);
            return false;
        }
        gistJson = []//设定依据对象
        for(var i=0;i<gistList.length;i++){
            var obj = {};
            obj.gistId = gistList[i].id;
            obj.lawName = gistList[i].lawName;
            obj.strip = gistList[i].gistStrip;
            if(gistList[i].gistFund == null || gistList[i].gistFund == ''){
                obj.fund = 0;
            }else{
                obj.fund = gistList[i].gistFund;
            }
            if(gistList[i].gistItem == null || gistList[i].gistItem == ''){
                obj.item = 0;
            }else{
                obj.item = gistList[i].gistItem;
            }
            if(gistList[i].gistCatalog == null || gistList[i].gistCatalog == ''){
                obj.gistCatalog = 0;
            }else{
                obj.gistCatalog = gistList[i].gistCatalog;
            }
            obj.content = gistList[i].content;
            gistJson.push(obj);
        }
        //设定依据json
        params.gistJsonStr = tools.parseJson2String(gistJson);
        
        // 部门、地区、执法系统手动设置
        var subJson = tools.requestJsonRs("/subjectSearchController/getSubjectById.action", {id:params.subjectId});
        if(subJson.rtState){
        	params.departmentId = subJson.rtData.departmentId;
        	params.area = subJson.rtData.areaId;
        	params.orgSys = subJson.rtData.orgSysId;
        }
        
        var json = tools.requestJsonRs("/permissionItemCtrl/savePermissionItem.action",params);
        return json.rtState;
    }else{
        return false;
    }
}

/**
 * 修改
 * @returns
 */
function update(){
    if($('#form1').form('enableValidation').form('validate')){    
        var params = tools.formToJson($("#form1"));
        params.id = $("#id").val();
        
        // 服务对象
        var partyType = $('#partyType').combobox('getValues');
        if(partyType != null && partyType.length > 0){
            params.partyType = partyType.join(',');
        }
        
        // 办理形式
        var handleWay = $('#handleWay').combobox('getValues');
        if(handleWay != null && handleWay.length > 0){
            params.handleWay = handleWay.join(',');
        }
        // 许可职权
        var powerList = $("#powerDatagrid").datagrid("getRows");
        if(powerList == null || powerList.length < 1){
            $.MsgBox.Alert_auto("请添加至少1条许可职权");
            $('html, body').animate({scrollTop: $("#setting_panel").offset().top}, 500);
            return false;
        }
        powerJson = []//许可职权对象
        for(var i=0;i<powerList.length;i++){
            var obj = {};
            obj.powerId = powerList[i].id;
            obj.powerCode = powerList[i].code;
            obj.powerName = powerList[i].name;
            powerJson.push(obj);
        }
        //许可职权对象转成json字符串
        params.powerJsonStr = tools.parseJson2String(powerJson);
        
        var gistList = $("#settingDatagrid").datagrid("getSelections");
        if(gistList == null || gistList.length < 1){
            $.MsgBox.Alert_auto("请添加至少1条设定依据");
            $('html, body').animate({scrollTop: $("#gist_panel").offset().top}, 500);
            return false;
        }
        gistJson = []//设定依据对象
        for(var i=0;i<gistList.length;i++){
            var obj = {};
            obj.gistId = gistList[i].id;
            obj.lawName = gistList[i].lawName;
            obj.strip = gistList[i].gistStrip;
            if(gistList[i].gistFund == null || gistList[i].gistFund == ''){
                obj.fund = 0;
            }else{
                obj.fund = gistList[i].gistFund;
            }
            if(gistList[i].gistItem == null || gistList[i].gistItem == ''){
                obj.item = 0;
            }else{
                obj.item = gistList[i].gistItem;
            }
            if(gistList[i].gistCatalog == null || gistList[i].gistCatalog == ''){
                obj.gistCatalog = 0;
            }else{
                obj.gistCatalog = gistList[i].gistCatalog;
            }
            obj.content = gistList[i].content;
            gistJson.push(obj);
        }
        //设定依据json
        params.gistJsonStr = tools.parseJson2String(gistJson);
        
        // 部门、地区、执法系统手动设置
        var subJson = tools.requestJsonRs("/subjectSearchController/getSubjectById.action", {id:params.subjectId});
        if(subJson.rtState){
        	params.departmentId = subJson.rtData.departmentId;
        	params.area = subJson.rtData.areaId;
        	params.orgSys = subJson.rtData.orgSysId;
        	params.isDepute = subJson.rtData.isDepute;
        }
        
        var json = tools.requestJsonRs("/permissionItemCtrl/updateOrDelete.action",params);
        return json.rtState;
    }else{
        return false;
    }
}

/**
 * 查询实施主体下的职权
 * @returns
 */
function findPower(){
    var powerType = '02';
    var url=contextPath+"/supervise/permission/permission_item_add_power.jsp?actSubject="+actSubject+"&powerType="+powerType;
    top.bsWindow(url ,"添加许可职权",{width:"1000",height:"450",buttons:
        [
            {name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
        ]
        ,submit:function(v,h){
            if( v == "保存") {
                var cw = h[0].contentWindow;
                var formalPowers = cw.getSelectedPower();
                if(addPower == null || addPower.length == 0) {
                    for(var index in formalPowers) {
                        addPower.push(formalPowers[index].id);
                    }
                } else {
                    for(var index in formalPowers) {
                        if(addPower.indexOf(formalPowers[index].id) == -1) {
                            addPower.push(formalPowers[index].id);
                        }
                    }
                }
                var params;
                var paramsGist;
                if(addPower == null || addPower.length == 0) {
                    params = {ids: 'empty', actSubject: actSubject};
                    paramsGist = {powerId: 'empty',gistType: '01'};
                }else {
                    params = {
                        ids : addPower.join(","),
                        actSubject : actSubject
                    };
                    paramsGist = {
                        powerId : addPower.join(","),
                        gistType : '03' // 依据类型（01 违法依据，02处罚依据，03设定依据）
                    };
                }
                initHandDatagrid(params);//加载许可职权
                initGistDatagrid(paramsGist);//加载设定依据
                return true;
            } else if(v=="关闭"){
                return true;
            }
        }
    });
}

function initHandDatagrid(params){
    datagrid = $('#powerDatagrid').datagrid({
        url: contextPath + '/permissionItemCtrl/getPowerByActSubject.action',
        pagination: false,
        //pageSize : 5,
        //pageList : [5, 10, 20],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar: '#toolbar', // 工具条对象
        checkbox: true,
        border: false,
        striped: true,//隔行变色
        /* idField:'formId',//主键列 */
        fitColumns: true, // 列是否进行自动宽度适应
        singleSelect: true, //为true只能选择一行
        nowrap: true,
        queryParams: params,//查询参数
        onLoadSuccess: function(data) {
        },
        columns: [[
            {field:'ID',title:'序号',align:'center',
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field: 'name', 
                title: '职权名称', 
                width: 60, 
                halign: 'center', 
                align: 'left',
                formatter: function(e, rowData) {
                    var name = rowData.name;
                    if(name == null || name == 'null') {
                        name = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+name+"'>"+name+"</lable>";
                    return lins;
                }
            },
            {
                field: 'code', 
                title: '职权编号', 
                width: 80, 
                halign: 'center', 
                align: 'center'
                
            },
            {
                field: '___',
                title: '操作',
                formatter: function(e, rowData) {
                    var optStr = "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deleteAddPower(\"" + rowData.id + "\")'><i class='fa fa-trash'></i> </a>&nbsp;&nbsp;&nbsp;&nbsp;";
                    return optStr;
                },
                width: 20, 
                halign: 'center', align: 'center'
            }
        ]]
    });
}

/**
 * 设定依据信息加载
 * @returns
 */
function initGistDatagrid(params){
    gistDatagrid = $('#settingDatagrid').datagrid({
        url: contextPath + '/powerCtrl/findGistsByPowerIds.action',
        queryParams: params,//查询参数
        pagination: false,
        //pageSize : 5,
        //pageList : [5, 10, 20],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar: '#toolbar', // 工具条对象
        checkbox: true,
        border: false,
        striped: true,//隔行变色
        /* idField:'formId',//主键列 */
        fitColumns: true, // 列是否进行自动宽度适应
        singleSelect: false, //为true只能选择一行
        nowrap: true,
        onLoadSuccess: function(data) {
            var rowData = data.rows;
            $.each(rowData, function(index,val){//遍历JSON
                if(gistJson != null && gistJson.length > 0){
                    for(var i = 0; i < gistJson.length; i++){
                        if(gistJson[i] == val.id){
                            $("#settingDatagrid").datagrid("selectRow", index);//如果数据行为已选中则选中改行
                            break;
                        }
                    }
                }else{
                    $("#settingDatagrid").datagrid("selectRow", index);//如果数据行为已选中则选中改行
                }
            });   
        },
        columns: [[
            {
                field: 'id', checkbox: true, title: "ID", width: 20, halign: 'center', align: 'center'
            },
            {field:'ID',title:'序号',align:'center',
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field: 'lawName', title: '法律名称', halign: 'center', width: 120, align: 'left',
                formatter: function(e, rowData) {
                    var lawName = rowData.lawName
                    if(lawName == null || lawName == 'null') {
                        lawName = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+lawName+"'>"+lawName+"</lable>"
                    return lins;
                }
            },
            {
                field: 'gistStrip', title: '条', width: 15, halign: 'center', align: 'center'
            },
            {
                field: 'gistFund', title: '款', width: 15, halign: 'center', align: 'center',
                formatter: function(value, rowData, rowIndex) { 
                    if(value == 0) {
                        return "";
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'gistItem', title: '项', width: 15, halign: 'center', align: 'center',
                formatter: function(value, rowData, rowIndex) { 
                    if(value == 0) {
                        return "";
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'gistCatalog', title: '目', width: 15, halign: 'center', align: 'center',
                formatter: function(value, rowData, rowIndex) { 
                    if(value == 0) {
                        return "";
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'content', title: '内容', halign: 'center', width: 200, align: 'left',
                formatter: function(e, rowData) {
                    var content = rowData.content
                    if(content == null || content == 'null') {
                        content = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+content+"'>"+content+"</lable>"
                    return lins;
                }
            }
        ]]
    });
}

/**
 * 删除添加的违法依据
 * @param id 违法依据ID
 * @returns
 */
function deleteAddPower(id){
    for(var index in addPower) {
        if(addPower[index] == id) {
            addPower.splice(index, 1);
            break;
        }
    }
    var params;
    var paramsGist;
    if(addPower == null || addPower.length == 0) {
        params = {ids: 'empty', actSubject: actSubject};
        paramsGist = {powerId: 'empty',gistType: '03'};
    }else {
        params = {
            ids: addPower.join(","),
            actSubject: actSubject
        };
        paramsGist = {
            powerId: addPower.join(","),
            gistType: '03' 
        };//依据类型（01 违法依据，02处罚依据，03设定依据）
    }
    initHandDatagrid(params);
    initGistDatagrid(paramsGist);//加载违法依据
}