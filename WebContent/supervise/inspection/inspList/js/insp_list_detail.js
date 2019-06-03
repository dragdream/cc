var listId ='';
var loginDeptId ='';
var loginSubId ='';
var orgSys = '';
var applyHierarchy = '';
var code ='';
var addPower = [];
var powerJson = [];
var gistJson = [];


function doInit(){
	
    initParams();
    //初始化数据
    initInspList();
    
}
function initParams(){
    listId = $('#listId').val();
    loginSubId = $('#loginSubId').val();
    loginDeptId = $('#loginDeptId').val();
    orgSys = $('#orgSys').val();
    console.log(listId);
//    loginDeptId = $('#loginDeptId').val();
//    loginSubId = $('#loginSubId').val();
//    orgSys = $('#orgSys').val();
//    levelCode = $('#levelCode').val();
}
/*
 * 初始化相关控件
 */
function initEasyUiControls(data){
	
    //行使层级
    initCombobox("applyHierarchy","DEPT_LEVEL",data.applyHierarchy);
    //检查类别
    
    initCombobox("inspType","INSPECTION_TYPE",data.inspType);
    //检查方式
    initCombobox("inspWay","INSPECTION_WAY",data.inspWay);
    //是否纳入双随机
    if(data.isDoubleRandom==1){
        $("#isDoubleRandom").text("是");
        $("#doubleRandomCascade").show();
        $.parser.parse($('#doubleRandomCascade'));
    }else if(data.isDoubleRandom==0){
        $("#isDoubleRandom").text("否");
        $("#doubleRandomCascade").hide();
    }
    //是否抽查
    if(data.isSpotCheck==1){
        $("#isSpotCheck").text("是");
    }else if(data.isSpotCheck==0){
        $("#isSpotCheck").text("否");
    }
    //是否协同
    if(data.isSynergy==1){
        $("#isSynergy").text("是");
        $(".synergy-cascade").show();
        $.parser.parse($('.synergy-cascade'));
    }else if(data.isSynergy==0){
        $("#isSynergy").text("否");
        $(".synergy-cascade").hide();
    }
    //事项状态 
    if(data.currentState=="01"){
        $("#currentState").text("在用");
    }else if(data.currentState=="02"){
        $("#currentState").text("取消");
    }else if(data.currentState=="03"){
        $("#currentState").text("暂停");
    }
    
    
}


/**
 * 初始化下拉框
 * @param id
 * @param codeNo
 */
function initCombobox(id,parentCodeNo,codeNo){
    var json = tools.requestJsonRs("/sysCode/getSysCodeNameByParentCodeNo.action",
        {
			parentCodeNo : parentCodeNo,
            codeNo : codeNo
        });
    $('#'+id).text(json.rtData);

}

/**
 * 填报，单选按钮必选校验
 */
function radioValidate(id,name,msg) {
    var value = $("#" + id + " input[name='" + name + "']:checked").val();
    if (value == null || value == ''){
        top.$.MsgBox.Alert_auto(msg);
        $('html, body').animate({scrollTop: $("#" +id).offset().top}, 500);
        return false;
    }else{
        return true;
    }
}

/*********************************************初始化基础信息***************************************************/
function initInspList(){
	var json = tools.requestJsonRs("/inspListBaseCtrl/inspListInput.action?id=" + listId);
	var powerJsonStr = '';
	var gistJsonStr = '';
	if(json.rtState){
		code = json.rtData.code;
		applyHierarchy = json.rtData.applyHierarchy;
		bindJsonObj2Cntrl(json.rtData);
        powerJsonStr = json.rtData.powerJsonStr;
        gistJsonStr = json.rtData.gistJsonStr;
        
    	initEasyUiControls(json.rtData);
    }
	initItemList();//初始化检查项
    initPowerGist(powerJsonStr,gistJsonStr);
    
}
/**********************************************检查项*******************************************************/
var inspItemsStr = [];
var editIndex = undefined;
function initItemList(){
	var json = tools.requestJsonRs("/inspListBaseCtrl/getListItems.action?inspListId=" + listId);
    if(json.rtState){
    	for (var i = 0; i < json.rtData.length; i++) {
            var obj = {};
            obj.inspItemName = json.rtData[i].inspItemName;
            inspItemsStr.push(obj);
        }
    	initItemDatagrid();
    }
    
}

function initItemDatagrid(){
	var data = {
    	    rows:inspItemsStr,
    	    total:inspItemsStr.length
    	};
    datagrid = $('#inspItemDatagrid').datagrid({
        data: data,
        pagination : false,
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        checkbox : false,
        border : false,
        striped:true,
//        rownumbers:true, 
        /* idField:'formId',//主键列 */
        fitColumns : true, // 列是否进行自动宽度适应
        singleSelect : false, // 为true只能选择一行
        nowrap : true,
        columns : [ [ {
            field : 'id',
            checkbox : true,
            title : "ID",
            width : 20,
            align : 'center'
        },
        {
            field : 'ID',
            title : '序号',
            halign : 'center',
            align : 'center',
            formatter : function(value, rowData,rowIndex) {
                return rowIndex+1;
            },
        },
        {
            field : 'inspItemName',
            title : '检查项',
            width : 60,
            halign : 'center',
        } 
        ] ],
    });
}



/**********************************************职权*******************************************************/
function initPowerGist(powerJsonStr,gistJsonStr){
    // 获取强制执行信息，尝试初始化职权依据表
    var powerJson;
    if (powerJsonStr != null && powerJsonStr != ''
            && powerJsonStr != 'null') {
        powerJson = powerJsonStr.split(",");
        var params;
        var paramsGist;
        if (powerJson != null && powerJson.length > 0) {
            for (var i = 0; i < powerJson.length; i++) {
                addPower.push(powerJson[i]);
            }
            params = {
                ids : addPower.join(","),
                actSubject : loginSubId
            }
            paramsGist = {
                powerId : addPower.join(",")
            }
            initCoercionPowerTable(params);// 违法行为信息加载
            initCoercionGistTable(paramsGist); // 违法依据信息加载
            gistJson = [];
            if (gistJsonStr != null && gistJsonStr != ''
                    && gistJsonStr != 'null') {
                gistJson = gistJsonStr.split(",");
            }
        }
    }else{
        var emptyPowerParams = {
                ids: 'empty',
                actSubject: 'empty'
        }
        var emptyGistParams = {
                powerId: 'empty'
                
        }
        initCoercionPowerTable(emptyPowerParams);
        initCoercionGistTable(emptyGistParams);
    }
    
}


/**
* 初始化强制职权表
*/
initCoercionPowerTable = function(params) {
   datagrid = $('#powerDatagrid')
           .datagrid(
                   {
                       url : contextPath + '/powerCtrl/getPowerByActSubject.action',
                       pagination : false,
                       // pageSize : 5,
                       // pageList : [5, 10, 20],
                       view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
//                       toolbar : '#toolbar', // 工具条对象
                       checkbox : true,
                       border : false,
                       striped:true,
                       /* idField:'formId',//主键列 */
                       fitColumns : true, // 列是否进行自动宽度适应
                       singleSelect : true, // 为true只能选择一行
                       nowrap : true,
                       queryParams : params,// 查询参数
                       onLoadSuccess : function(data) {
                       },
                       onLoadError : function(data){
                       },
                       columns : [[ 
                               {
                                   field : 'id',
                                   checkbox : true,
                                   title : "ID",
                                   width : 10,
                                   hidden : true,
                                   align : 'center'
                               },
                               {
                                   field : 'name',
                                   title : '违法行为',
                                   width : 200,
                                   halign : 'center',
                                   formatter: function(e, rowData) {
                                       var name = rowData.name;
                                       if(name == null || name == 'null') {
                                           name = "";
                                       }
                                       var optStr = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+name+"'>"+name+"</lable>"
                                       return optStr;
                                   }
                               },
                               {
                                   field : 'code',
                                   title : '职权编号 ',
                                   width : 80,
                                   align : 'center'
                               }] ]
                   });
}

/**
* 初始化检查依据表
*/
initCoercionGistTable = function(params) {
   datagrid = $('#settingDatagrid').datagrid(
           {
               url : contextPath + '/powerCtrl/findGistsByPowerIds.action',
               queryParams : params,// 查询参数
               pagination : false,
               view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
//               toolbar : '#toolbar', // 工具条对象
               checkbox : true,
               border : false,
               striped:true,
               /* idField:'formId',//主键列 */
               fitColumns : true, // 列是否进行自动宽度适应
               singleSelect : false, // 为true只能选择一行
               nowrap : true,
               onLoadSuccess : function(data) {
//                   var rowData = data.rows;
//                   $.each(rowData, function(index, val) {// 遍历JSON
//                       if (gistJson != null && gistJson.length > 0) {
//                           for (var i = 0; i < gistJson.length; i++) {
//                               if (gistJson[i] == val.id) {
//                                   $("#settingDatagrid").datagrid(
//                                           "selectRow", index);// 如果数据行为已选中则选中改行
//                                   break;
//                               }
//                           }
//                       } else {
//                           $("#settingDatagrid").datagrid("selectRow",
//                                   index);// 如果数据行为已选中则选中改行
//                       }
//                   });
            	   $(this).datagrid('unselectAll'); 
               },
               columns : [ [ {
                   field : 'id',
                   checkbox : true,
                   title : "ID",
                   hidden : true,
                   width : 20,
                   align : 'center'
               }, {
                   field : 'lawName',
                   title : '法律名称',
                   width : 120,
                   halign : 'center'
               }, {
                   field : 'gistStrip',
                   title : '条',
                   width : 15,
                   align : 'center'
               }, {
                   field : 'gistFund',
                   title : '款',
                   width : 15,
                   align : 'center',
                   formatter : function(value, rowData, rowIndex) {
                       if (value == 0) {
                           return "";
                       } else {
                           return value;
                       }
                   }
               }, {
                   field : 'gistItem',
                   title : '项',
                   width : 15,
                   align : 'center',
                   formatter : function(value, rowData, rowIndex) {
                       if (value == 0) {
                           return "";
                       } else {
                           return value;
                       }
                   }
               }, {
                   field : 'content',
                   title : '内容',
                   width : 200,
                   halign : 'center',
                   formatter: function(e, rowData) {
                       var content = rowData.content;
                       if(content == null || content == 'null') {
                           content = "";
                       }
                       var optStr = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+content+"'>"+content+"</lable>"
                       return optStr;
                   }
               } ] ]
           });
}

