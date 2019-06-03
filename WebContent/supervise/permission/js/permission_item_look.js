var id = $("#id").val();
var addPower = [];
var addGist = [];
var gistJson = [];
var actSubject = '';


/**
 * 初始化
 */
function doInit(){
	var json1 = tools.requestJsonRs("/permissionItemCtrl/getActSubject.action");
	if(json1.rtState){
		actSubject = json1.rtData;
	}
	//initIsCollectFees();
	var url = contextPath + "/permissionItemCtrl/getPermissionItemById.action";
    var json = tools.requestJsonRs(url,{id:$("#id").val()});
    if(json.rtState){
        bindJsonObj2Cntrl(json.rtData);
        var promisedTimeLimit = json.rtData.promisedTimeLimit;
        if(promisedTimeLimit != null && /^[0-9]{1,10}$/.test(promisedTimeLimit)){
            $("#promisedTimeLimit").html(promisedTimeLimit + "个工作日");
        }
        var statutoryTimeLimit = json.rtData.statutoryTimeLimit;
        if(statutoryTimeLimit != null && /^[0-9]{1,10}$/.test(statutoryTimeLimit)){
            $("#statutoryTimeLimit").html(statutoryTimeLimit + "个工作日");
        }
        bindJsonObj2Easyui(json.rtData , "form1");
        
        if(json.rtData.isCollectFees == 1){
        	$("#isCollectFees").html("是");
        	$('#isCollectFees_tbody').show();
        }else if(json.rtData.isCollectFees == 0){
        	$("#isCollectFees").html("否");
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
            
            
            var gistJsonStr = json.rtData.gistJsonStr;
            gistJson = [];
            if(gistJsonStr != null && gistJsonStr != '' && gistJsonStr != 'null'){
                gistJson = gistJsonStr.split(",");
                for (var i = 0; i < gistJson.length; i++){
                    addGist.push(gistJson[i]);
                }
                if(addGist == null || addGist.length < 1){
                    paramsGist = {powerId: 'empty',gistType: '03'};
                } else {
                    paramsGist = {
                        id : addGist.join(","),
                        gistType : '03'
                    };
                }
            }
            initHandDatagrid(params);//违法行为信息加载 
            initGistDatagrid(paramsGist);  //违法依据信息加载
        }else{
            initHandDatagrid({ids: 'empty'});//违法行为信息加载 
            initGistDatagrid({powerId: 'empty'}); //违法依据
        }
    }
}

/**
 * 是否收费
 * @returns
 */
function initIsCollectFees(){
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
                width: 20, 
                halign: 'center', 
                align: 'center'
                
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
        },
        columns: [[
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