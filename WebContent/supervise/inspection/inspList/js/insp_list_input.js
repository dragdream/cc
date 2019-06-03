var listId ='';
var loginDeptId ='';
var loginSubId ='';
var orgSys = '';
var applyHierarchy = '';
var code ='';
var addPower = [];
var powerJson = [];
var gistJson = [];
var isSuperviseList =null;


function doInit(){
	
    initParams();
    initEasyUiControls();
    //初始化数据
    initInspList();
}
function initParams(){
    listId = $('#listId').val();
    loginSubId = $('#loginSubId').val();
    loginDeptId = $('#loginDeptId').val();
    orgSys = $('#orgSys').val();
//    loginDeptId = $('#loginDeptId').val();
//    loginSubId = $('#loginSubId').val();
//    orgSys = $('#orgSys').val();
//    levelCode = $('#levelCode').val();
}
/*
 * 初始化单选按钮
 */
function initEasyUiControls(){
	//校验时间
	dateValidate("planEffectDateStr","planCancelDateStr");
    
    var radioJson1 = [
                      {codeNo:'1', codeName: '是'},
                      {codeNo:'0', codeName: '否'}
                  ];
    var radioJson2 = [
                          {codeNo:'01', codeName: '在用'},
                          {codeNo:'02', codeName: '取消'},
                          {codeNo:'03', codeName: '暂停'},
                          /*{codeNo:'04', codeName: '其他'}*/
                      ];
    
    //是否纳入双随机
    var isDoubleRandom =$('#isDoubleRandom').val();
    initJsonListRadio(radioJson1, 'isDoubleRandom');
    
    $('#isDoubleRandom1').radiobutton({
        onChange: function(check){
            if(check){
                $("#doubleRandomCascade").show();
                $('#inspProportion').textbox({disabled:false });
                $('#inspFrequency').textbox({disabled:false });
                $.parser.parse($('#doubleRandomCascade'));
            }else{
                $("#doubleRandomCascade").hide();
                $('#inspProportion').textbox({disabled:true });
                $('#inspFrequency').textbox({disabled:true });
            }
        }
    });
    //是否抽查
    var isSpotCheck = $('#isSpotCheck').val();
    initJsonListRadio(radioJson1, 'isSpotCheck');
    
    //是否协同
    var isSynergy = $('#isSynergy').val();
    initJsonListRadio(radioJson1, 'isSynergy');
    
    $('#isSynergy1').radiobutton({
        onChange: function(check){
            if(check){
                $(".synergy-cascade").show();
                $('#synergyOrgan').textbox({disabled:false,required:true, missingMessage:'请填写协同相关机构', validType:'length[0,50]'});
                $.parser.parse($('.synergy-cascade'));
            }else{
                $('#synergyOrgan').textbox({disabled:true,required:false});
                $(".synergy-cascade").hide();
                
            }
        }
    });
    //事项状态
    var currentState = $('#currentState').val();
    initJsonListRadio(radioJson2, 'currentState');
    
}




/**
 * 初始化下拉框
 * @param id
 * @param codeNo
 */
function initCombobox(id,codeNo){
    var ids = $("#"+id).val();
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action",
            {
                codeNo : codeNo
            });
    if (json.rtState) {
        $('#'+id).combobox({
            data : json.rtData,
            valueField : 'codeNo',
            textField : 'codeName',
            panelHeight : '100px',
            prompt : '请选择',
            onLoadSuccess : function() {
                if(ids != 0){
                    $(this).combobox('setValue',ids);
                }
            },
            editable : false
        });
    }else{
        $('#'+id).combobox({
            panelHeight : 'auto',
            prompt : '请选择',
            editable : false
        });
    }
}


/*********************************************初始化基础信息***************************************************/
function initInspList(){
	var json = tools.requestJsonRs("/inspListBaseCtrl/inspListInput.action?id=" + listId);
	var powerJsonStr = '';
	if(json.rtState){
		//控制选项卡
		isSuperviseList = json.rtData.isSuperviseList;
		var tab = $('#ins_list_tabs').tabs('getSelected');
	    var index = $('#ins_list_tabs').tabs('getTabIndex',tab);
	    if(isSuperviseList==1){
	    	 $('#ins_list_tabs').tabs('close','基础信息');
	    	 $('#ins_list_tabs').tabs('close','检查项');
	    	 $('#ins_list_tabs').tabs('select','行政职权');
	    }else {
	    	code = json.rtData.code;
			applyHierarchy = json.rtData.applyHierarchy;
	        bindJsonObj2Easyui(json.rtData , "inspection_list_input_form");
	        //行使层级
	        initCombobox("applyHierarchy","DEPT_LEVEL");
	        //检查类型
	        initCombobox("inspType","INSPECTION_TYPE");
	        //检查方式
	        initCombobox("inspWay","INSPECTION_WAY");
	        
	        if(json.rtData.isDoubleRandom!=null){
	            $("#isDoubleRandom" + json.rtData.isDoubleRandom).radiobutton('check');
	        }
	        if(json.rtData.isSpotCheck!=null){
	            $("#isSpotCheck" + json.rtData.isSpotCheck).radiobutton('check');
	        }
	        if(json.rtData.isSynergy!=null){
	            $("#isSynergy" + json.rtData.isSynergy).radiobutton('check');
	        }
	    	if(json.rtData.currentState!=null){
	            $("#currentState" + json.rtData.currentState).radiobutton('check');
	        }
	    	initItemList();//初始化检查项
	    }
        powerJsonStr = json.rtData.powerJsonStr;
        gistJsonStr = json.rtData.gistJsonStr;
    }
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
        }, {
            field : 'inspItemName',
            title : '检查项',
            width : 60,
            halign : 'center',
            editor : {
                type : 'text',
                options : {
                    required : true,
                },
            },
        },{
            field : '__',
            title : '操作',
            formatter : function(value, rowData,rowIndex) {
                var optStr = "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='inspItemDel(\""
                        + rowData.id + "\",\""+rowIndex
                        + "\")'><i class='fa fa-trash'></i> </a>&nbsp;&nbsp;&nbsp;&nbsp;";
                return optStr;
            },
            width : 10,
            align : 'center'
        } 
        ] ],
        onLoadSuccess : function(data) {
        },
        onDblClickCell : function(index, field, value){
            editIndex = index;
            $(this).datagrid('beginEdit', index);
            var ed = $(this).datagrid('getEditor', {index:index,field:field});
            $(ed.target).focus().bind('blur', function(e){
                endEditing();
            })
        },
        onAfterEdit:function(index, row, changes){ // 关闭编辑器后触发
            if(editIndex != undefined && changes.inspItemName!=null&&changes.inspItemName!=''){
            	inspItemsStr.splice(index,1,changes);
            	initItemDatagrid();
            	editIndex = undefined; 
            }
        }
    });
}
/** 编辑单元格 */
function endEditing(){
    if (editIndex == undefined){return true}
    if ($('#inspItemDatagrid').datagrid('validateRow', editIndex)){
        $('#inspItemDatagrid').datagrid('endEdit', editIndex);
        return true;
    } else {
        $('#inspItemDatagrid').datagrid('endEdit', editIndex);
        return false;
    }
}
function onClickCell(index, field){
    $('#inspItemDatagrid').datagrid('beginEdit', index);
    if (endEditing()){
        editIndex = index;
        var ed = $('#inspItemDatagrid').datagrid('getEditor', {index:0,field:'inspItemName'});
        $(ed.target).focus().bind("blur", function(e){
            endEditing();
        });
    }
}
/**
 * 添加检查项
 */
function inspItemAdd(){
    if (this.editIndex == undefined) {
        //添加一行 
        $('#inspItemDatagrid').datagrid('insertRow', {
            index : 0,
            row : {
            },
        });
        onClickCell(0,'inspItemName');
    }
}

function inspItemDel(id,index){
	if (window.confirm("是否确认删除？")) {
        $('#inspItemDatagrid').datagrid('deleteRow',index);
        
        editIndex == undefined;
        initItemDatagrid();

    }
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
* 查询实施主体下的职权
* 
* @returns
*/
function findInspPower() {
   var params = {
       actSubject : loginSubId,
       powerType : '06'
   }
   var url = contextPath + "/caseCommonPowerCtrl/commonCaseAddPower.action?"
           + $.param(params);
   top.bsWindow(url, "选用检查职权", {
       width : "1000",
       height : "500",
       buttons : [  {
           name : "关闭",
           classStyle : "btn-alert-gray"
       },{
           name : "保存",
           classStyle : "btn-alert-blue"
       }],
       submit : function(v, h) {
           var cw = h[0].contentWindow;
           if (v == "保存") {
               var formalPowers = cw.getSelectedPower();
               if (addPower == null || addPower.length == 0) {
                   for ( var index in formalPowers) {
                       addPower.push(formalPowers[index].id);
                   }
               } else {
                   for ( var index in formalPowers) {
                       if (addPower.indexOf(formalPowers[index].id) == -1) {
                           addPower.push(formalPowers[index].id);
                       }
                   }
               }
               if(addPower != null && addPower.length >0){
                   var params = {
                           ids : addPower.join(","),
                           actSubject : loginSubId
                       };
                       
                       var paramsGist = {
                           powerId : addPower.join(","),
                           gistType : '03' // 依据类型（01 违法依据，02处罚依据，03设定依据）
                       }
                       initCoercionPowerTable(params);// 加载违法行为
                       initCoercionGistTable(paramsGist);// 加载违法依据
               }
               
               return true;
           } else if (v == "关闭") {
               return true;
           }
       }
   });
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
                               },
                               {
                                   field : '___',
                                   title : '操作',
                                   formatter : function(e, rowData) {
                                       var optStr = "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deleteAddPower(\""
                                               + rowData.id
                                               + "\")'><i class='fa fa-trash'></i> </a>&nbsp;&nbsp;&nbsp;&nbsp;";
                                       return optStr;
                                   },
                                   width : 20,
                                   align : 'center'
                               } ] ]
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
                   var rowData = data.rows;
                   $.each(rowData, function(index, val) {// 遍历JSON
                       if (gistJson != null && gistJson.length > 0) {
                           for (var i = 0; i < gistJson.length; i++) {
                               if (gistJson[i] == val.id) {
                                   $("#settingDatagrid").datagrid(
                                           "selectRow", index);// 如果数据行为已选中则选中改行
                                   break;
                               }
                           }
                       } else {
                           $("#settingDatagrid").datagrid("selectRow",
                                   index);// 如果数据行为已选中则选中改行
                       }
                   });
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

/**
* 删除添加的违法依据
* 
* @param id
*            违法依据ID
* @returns
*/
function deleteAddPower(id) {
   for ( var index in addPower) {
       if (addPower[index] == id) {
           addPower.splice(index, 1);
           break;
       }
   }
   var params;
   var paramsGist;
   if (addPower == null || addPower.length == 0) {
       params = {
           ids : "empty",
           actSubject : loginSubId
       };
       paramsGist = {
           powerId : "empty"
       };
   } else {
       params = {
           ids : addPower.join(","),
           actSubject : loginSubId
       };
       paramsGist = {
           powerId : addPower.join(","),
           gistType : '03' // 依据类型（01 违法依据，02处罚依据，02设定依据）
       };
   }
   initCoercionPowerTable(params);
   initCoercionGistTable(paramsGist);// 加载违法依据
}


/*****************************************************保存*********************************************************/

function saveInspList(){
	if($('#inspection_list_input_form').form('enableValidation').form('validate')==false){
		var tab = $('#ins_list_tabs').tabs('getSelected');
	    var index = $('#ins_list_tabs').tabs('getTabIndex',tab);
	    if(index!=0){
	    	$.MsgBox.Alert_auto("请填写基础信息");
	    }
	}else if($('#inspection_list_input_form').form('enableValidation').form('validate')){
		var finalParams ={};
		finalParams.isSuperviseList = isSuperviseList;
		if(isSuperviseList!=1){
			/*****基础信息*****/
	    	var vali = radioValidate("isDoubleRandomTd","isDoubleRandom","请选择是否纳入双随机");
	    	if(vali==false){
		    	return false;
	    	}
	    	vali = radioValidate("isSpotCheckTd","isSpotCheck","请选择是否抽查");
	    	if(vali==false){
		    	return false;
	    	}
	    	vali = radioValidate("isSynergyTd","isSynergy","请选择是否协同");
	    	if(vali==false){
		    	return false;
	    	}
	    	vali = radioValidate("currentStateTd","currentState","请选择事项状态");
	    	if(vali==false){
		    	return false;
	    	}
	    	//获取输入框中的值
	        finalParams = tools.formToJson($('#inspection_list_input_form'));
	        finalParams.planEffectDateStr = $("#planEffectDateStr").datebox('getValue');
	        finalParams.planCancelDateStr = $("#planCancelDateStr").datebox('getValue');
	        finalParams.id = listId;
	        finalParams.createSubjectId = loginSubId;
	        finalParams.createOrganizationId = loginDeptId;
	        finalParams.orgSys = orgSys;
	        finalParams.code = code;
	        finalParams.applyHierarchy = applyHierarchy;
	        /*****检查项*****/
	        var itemList = $('#inspItemDatagrid').datagrid("getRows");
	        if (itemList == null || itemList.length < 1) {
	            $.MsgBox.Alert_auto("请添加至少1条检查项");
	            return false;
	        }
	        var itemJson =[]
	        for (var i = 0; i < itemList.length; i++) {
	            var obj = {};
	            if(itemList[i].inspItemName!=null && itemList[i].inspItemName!='null' && itemList[i].inspItemName!=''){
	            	obj.inspItemName = itemList[i].inspItemName;
	            	itemJson.push(obj);
	            }
	        }
	        if(itemJson == null || itemJson.length < 1){
	        	$.MsgBox.Alert_auto("请添加至少1条检查项");
	            return false;
	        }
	        finalParams.itemJsonStr = tools.parseJson2String(itemJson);
		}
        /*****强制职权*****/
        // 强制职权
        var powerList = $('#powerDatagrid').datagrid("getRows");
        if (powerList == null || powerList.length < 1) {
            $.MsgBox.Alert_auto("请添加至少1条强制职权");
            return false;
        }
        powerJson = []// 违法依据对象
        for (var i = 0; i < powerList.length; i++) {
            var obj = {};
            obj.powerId = powerList[i].id;
            powerJson.push(obj);
        }
        // 违法行为对象转成json字符串
        finalParams.powerJsonStr = tools.parseJson2String(powerJson);
            
        var gistList = $('#settingDatagrid').datagrid("getRows");
        if (gistList == null || gistList.length < 1) {
            $.MsgBox.Alert_auto("请选择至少1条强制依据");
            return false;
        }
        gistJson = []// 违法依据对象
        for (var i = 0; i < gistList.length; i++) {
            var obj = {};
            obj.gistId = gistList[i].id;
            obj.lawName = gistList[i].lawName;
            obj.strip = gistList[i].gistStrip;
            if (gistList[i].gistFund == null || gistList[i].gistFund == '') {
                obj.fund = 0;
            } else {
                obj.fund = gistList[i].gistFund;
            }
            if (gistList[i].gistItem == null || gistList[i].gistItem == '') {
                obj.item = 0;
            } else {
                obj.item = gistList[i].gistItem;
            }
            if (gistList[i].gistCatalog == null || gistList[i].gistCatalog == '') {
                obj.gistCatalog = 0;
            } else {
                obj.gistCatalog = gistList[i].gistCatalog;
            }
            obj.content = gistList[i].content;
            gistJson.push(obj);
        }
        // 违法依据json
        finalParams.gistJsonStr = tools.parseJson2String(gistJson);
        
        var json = tools.requestJsonRs("/inspListBaseCtrl/save.action", finalParams);
        return json.rtState;
    }
}


/********************************************************公用组件**********************************************/


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
