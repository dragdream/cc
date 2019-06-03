/**
 * 
 */
var optCtrl = [];
var coercionCaseId = '';
var measureDatagrid = null;
var performDatagrid = null;
var pageType = null;
var caseSourceId = '';
var isForce =null;
var punishDecisionExecuteWay='';
var caseSourceId='';

function doInit() {
    coercionCaseId = $('#coercionCaseId').val();
    caseSourceId = $('#caseSourceId').val();
    pageType = $('#pageType').val();
    if(pageType != null && pageType != ''){
        if(pageType == '02'){
        	$('#coercion_manage_page').tabs('select','强制措施');
        }else if(pageType == '03'){
        	$('#coercion_manage_page').tabs('select','行政机关执行');
        }
    }
    //初始化立案信息
    doOpenShowPage(caseSourceId);
    isEditCoercion();
    initMeasureDatagrid();
    initPerformDatagrid();
    initCourtPerform();
}

/***********************************************************案件信息************************************************/
function doOpenShowPage(srcCaseId) {
//  selectTabs();
  var params = {
          caseId: srcCaseId,
  }
  $.ajax({ 
      type:"post",
      url: "/caseCommonBaseCtrl/commonCaseBaseLook.action",//提交地址 
      data: params,//参数 
      async: false,
      dataType: "json",
      success: function(data){ //回调方法 
          srcCaseType = '200';
          isForce = data.rtData.isForce;
          punishDecisionExecuteWay = data.rtData.punishDecisionExecuteWay;
          bindJsonObj2Cntrl(data.rtData);
          if(data.rtData.isSelfemployed == 1){
              $('#isSelfemployed').show();
              $('#isSelfemployed_checked_table').show();
              $('#isSelfemployed_unchecked_tr').hide();
          }else{
              $('#isSelfemployed').hide();
              $('#isSelfemployed_checked_table').hide();
              $('#isSelfemployed_unchecked_tr').show();
          }
          if(data.rtData.partyType==1){
              $('#partyType1').show();
              if(data.rtData.citizenSex == 1){
                  $('#citizenSex').text("男");
              }else if(data.rtData.citizenSex == 2){
                  $('#citizenSex').text("女")
              }
              $('#partyType2').hide();
              $('#partyType9').hide();
          }else if(data.rtData.partyType==2){
              $('#partyType2').show();
              $('#partyType1').hide();
              $('#partyType9').hide();
          }else if(data.rtData.partyType==9){
              $('#partyType9').show();
              $('#partyType1').hide();
              $('#partyType2').hide();
          }
          //初始化案件基础信息
          initFiling(data.rtData);
          courtPerformId = $('#courtPerformId').val();
      },
  });  
}

//控制标签
function isEditCoercion(){
    if(isForce != 1){
        $('#coercion_manage_page').tabs('close','强制措施');
    }
    if(punishDecisionExecuteWay == '02'){
        $('#coercion_manage_page').tabs('close','申请法院强制执行');
    }else if(punishDecisionExecuteWay == '03'){
        $('#coercion_manage_page').tabs('close','行政机关执行');
    }else{
        $('#coercion_manage_page').tabs('close','行政机关执行');
        $('#coercion_manage_page').tabs('close','申请法院强制执行');
    }
}
/*
 * 立案阶段初始化
 * @returns
 */
function initFiling(data){
    var filingApprovalDocumentPath = data.filingApprovalDocumentPath;
    if(filingApprovalDocumentPath != null && filingApprovalDocumentPath != ''){
        $("#filingApprovalDocumentName").html("");
        // 修改回显立案呈批表
        initAttachmentInfo('commonCase', filingApprovalDocumentPath, 'filingApprovalDocumentName');
    }
    var partyType = data.partyType;
    if(partyType == '02' || partyType == "03"){
       $('#adressTr').show();
    }else{
        $('#adressTr').hide();
    }
    initPerson(data);//人员初始化
}
/**
 * 修改初始化附件列表
 * @param model
 * @param modelId
 * @param elemId
 * @returns
 */
function initAttachmentInfo(model, sid, elemId) {
    var json = tools.requestJsonRs("/attachmentController/getAttachmentModelsByIds.action?attachIds="+sid);
    if(json.rtData != null && json.rtData.length > 0) {
        var punishFile = [];
        var attachList = json.rtData;
        $.each(attachList, function(i, o){
            // 添加附件属性
            punishFile.push(o.sid);//存放上传文件ID
            addAttachmentProp(o, elemId, punishFile, 1+2);
        });
    }
}
/**
 * 上传完毕后加载已经上传的文件
 * @param obj 文件对象
 * @param elemId 展示元素ID
 * @returns
 */
function addAttachmentProp(obj, elemId, punishFile) {
    var mainObj = $('#' + elemId);
    var page = "";
    page = page + '<div style="float: left; margin-right:20px" id="flowsheet' + obj.sid + '">';
    page = page + '<input type="hidden" name="'+elemId+'FileId" id="'+elemId+'FileId" value="' + obj.sid + '" />';
    page = page + '<input type="hidden" name="'+elemId+'FileName" id="'+elemId+'FileName" value="' + obj.fileName + '" />';
    page = page + '<a href="javascript:void(0);" sid="FILE' + obj.sid + '"></a>';
    page = page + '</div>';
    mainObj.append(page);
    
    obj.priv = 1+2; // 阅读、下载、删除
    var attach = tools.getAttachElement(obj, {deleteEvent:function(attachModel){
        $("#flowsheet" + attachModel.sid).remove();
        for(var index in punishFile) {
            if(punishFile[index] == attachModel.sid) {
                punishFile.splice(index, 1);
                break;
            }
        }
        
    }});
    $("[sid='FILE" + obj.sid + "']").append(attach);
}
/**
* 初始化人员
* @returns
*/
function initPerson(data){
   var personJsonStr = data.personJsonStr;
   var params;
   if(personJsonStr != null && personJsonStr != '' && personJsonStr != 'null'){
       params = {ids: personJsonStr};
   }else{
       params = {ids: 'empty'};
   }
   initPersonDatagrid(params);
}

/**
* 人员表格加载函数
* @returns
*/
function initPersonDatagrid(params){
   datagrid = $('#common_case_add_person_datagrid').datagrid({
       url: contextPath + '/caseCommonStaffCtrl/findListByPersonIds.action',
       queryParams: params,
       pagination: false,
       view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
       checkbox: true,
       border: false,
       striped: true,//隔行变色
       fitColumns: true, // 列是否进行自动宽度适应
       singleSelect: true, //为true只能选择一行
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
               field: 'subjectName', title: '所属主体', width: 30, halign: 'center', align: 'left',
               formatter: function(value, rowData) {
                   if(value == null || value == 'null') {
                       value = "";
                   }
                   var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</lable>"
                   return lins;
               }
           },
           {
               field: 'name', title: '执法人员 ', width: 12, halign: 'center', align: 'center'
           },
           {
               field: 'code', title: '执法证号 ', width: 15, halign: 'center', align: 'center'
           }
       ]]
   });
}

/****************************************************强制措施*******************************************************/
function initMeasureDatagrid() {
    measureDatagrid = $('#measureSee_datagrid')
            .datagrid(
                    {
                        url : contextPath
                                + '/coercionCaseSearchCtrl/measureListByPage.action',
                        queryParams : {
                        	coercionCaseId : coercionCaseId,
                        },
                        pagination : true,
                        singleSelect : true,
                        pageSize : 15,
                        pageList : [ 15, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
                        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
                        toolbar : '#toolbar',
                        // 工具条对象
                        // checkbox: true,
                        border : false,
                        /* idField:'formId',//主键列 */
                        fitColumns : true,
                        // 列是否进行自动宽度适应
                        nowrap : true,
                        onLoadSuccess : function(data) {
                        },
                        columns : [ [
                                {
                                    field : 'measureTypeStr',
                                    title : '行政强制措施种类',
                                    halign : 'center',
                                    align : 'center',
                                    width : 30
                                },
                                {
                                    field : 'createDateStr',
                                    title : '录入日期',
                                    halign : 'center',
                                    align : 'center',
                                    width : 20

                                },
                                {
                                    field : 'applyDateStr',
                                    title : '申请日期 ',
                                    halign : 'center',
                                    align : 'center',
                                    width : 20
                                },
                                {
                                    field : 'approveDateStr',
                                    title : '批准日期',
                                    halign : 'center',
                                    align : 'center',
                                    width : 20
                                },
                                {
                                    field : '____',
                                    title : '流程状态',
                                    halign : 'center',
                                    align : 'center',
                                    width : 15,
                                    formatter : function(e, rowData) {
                                        var textStr = "";
                                        if (rowData.enforceStep == 0) {
                                            textStr = "未知";
                                        }
                                        if (rowData.enforceStep == 1) {
                                            textStr = "确定强制措施种类";
                                        }
                                        if (rowData.enforceStep == 2) {
                                            textStr = "已批准申请";
                                        }
                                        if (rowData.enforceStep == 3) {
                                            textStr = "已作出处理决定";
                                        }
                                        return textStr;
                                    }
                                },
                                {
                                    field : '___',
                                    title : '操作',
                                    halign : 'center',
                                    align : 'center',
                                    formatter : function(e, rowData) {
                                        var optStr = "&nbsp;&nbsp;<span title='查看'><a href='javaScript:void(0);' onclick='doOpenMeasureShowPage(\"" + rowData.id + "\")'><i class='fa fa-eye'></i></a></span>&nbsp;&nbsp;";
                                        return optStr;
                                    }
                                } ] ]
                    });
}

function doOpenMeasureShowPage(id) {
    var params = {
            id: id,
            coercionCaseId: coercionCaseId,
            caseSourceId: caseSourceId
    };
    var url = contextPath
            + "/coercionCaseSearchCtrl/measuresSeeInfoInput.action?" + $.param(params);
    top.bsWindow(url, "行政强制措施", {
	    width : "1000",
	    height : "500",
	    buttons : [ {
	        name : "关闭",
	        classStyle : "btn-alert-gray"
	    } ],
	    submit : function(v, h) {
	        var cw = h[0].contentWindow;
	        if(v=="关闭"){
	        	return true;
	        }
	    }
	});
}

function initPerformDatagrid() {
    performDatagrid = $('#self_performSee_datagrid')
            .datagrid(
                    {
                        url : contextPath
                                + '/coercionCaseSearchCtrl/performListByPage.action',
                        queryParams : {
                        	coercionCaseId : coercionCaseId,
                        },
                        pagination : true,
                        singleSelect : true,
                        pageSize : 15,
                        pageList : [ 15, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
                        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
                        toolbar : '#toolbar',
                        // 工具条对象
                        // checkbox: true,
                        border : false,
                        /* idField:'formId',//主键列 */
                        fitColumns : true,
                        // 列是否进行自动宽度适应
                        nowrap : true,
                        onLoadSuccess : function(data) {
                        },
                        columns : [ [
                                {
                                    field : 'performTypeStr',
                                    title : '行政强制执行方式',
                                    halign : 'center',
                                    align : 'center',
                                    width : 30
                                },
                                {
                                    field : 'createDateStr',
                                    title : '录入日期',
                                    halign : 'center',
                                    align : 'center',
                                    width : 20

                                },
                                {
                                    field : 'applyDateStr',
                                    title : '申请日期 ',
                                    halign : 'center',
                                    align : 'center',
                                    formatter : function(e, rowData) {
                                        var textStr = "";
                                        if (rowData.applyDateStr != 'null') {
                                            textStr = rowData.applyDateStr;
                                        } else {
                                            textStr = "未申请";
                                        }
                                        return textStr;
                                    },
                                    width : 20
                                },
                                {
                                    field : 'approveDateStr',
                                    title : '批准日期',
                                    halign : 'center',
                                    align : 'center',
                                    formatter : function(e, rowData) {
                                        var textStr = "";
                                        if (rowData.applyDateStr != 'null') {
                                            textStr = rowData.approveDateStr;
                                        } else {
                                            textStr = "未批准";
                                        }
                                        return textStr;
                                    },
                                    width : 20
                                },
                                {
                                    field : '____',
                                    title : '流程状态',
                                    halign : 'center',
                                    align : 'center',
                                    width : 15,
                                    formatter : function(e, rowData) {
                                        var textStr = "";
                                        if (rowData.enforceStep == 0) {
                                            textStr = "未知";
                                        }
                                        if (rowData.enforceStep == 1) {
                                            textStr = "确定强制措施种类";
                                        }
                                        if (rowData.enforceStep == 2) {
                                            textStr = "已批准申请";
                                        }
                                        if (rowData.enforceStep == 3) {
                                            textStr = "已作出处理决定";
                                        }
                                        return textStr;
                                    }
                                },
                                {
                                    field : '___',
                                    title : '操作',
                                    halign : 'center',
                                    align : 'center',
                                    formatter : function(e, rowData) {
                                        var optStr = "&nbsp;&nbsp;<span title='查看'><a href='javaScript:void(0);' onclick='doOpenPerformShowPage(\"" + rowData.id + "\")'><i class='fa fa-eye'></i></a></span>&nbsp;&nbsp;";
                                    return optStr;
                                    }
                                } ] ]
                    });
}

function doOpenPerformShowPage(id) {
    var params = {
        id: id,
        coercionCaseId: coercionCaseId,
        caseSourceId: caseSourceId
    };
    var url = contextPath
            + "/coercionCaseSearchCtrl/performsSeeInfoInput.action?" + $.param(params);
    top.bsWindow(url, "行政强制行为", {
	    width : "1000",
	    height : "500",
	    buttons : [ {
	        name : "关闭",
	        classStyle : "btn-alert-gray"
	    } ],
	    submit : function(v, h) {
	        var cw = h[0].contentWindow;
	        if(v=="关闭"){
	        	return true;
	        }
	    }
	});
}
/*
 * 初始化申请法院强制执行
 */
function initCourtPerform(){
	var params = {
	        caseSourceId : caseSourceId,
	        id : coercionCaseId
	    }
	$.ajax({ 
    	type:"post",
    	url: "/coercionCaseCtrl/courtPerformsInput.action",//提交地址 
    	data: params,//参数 
    	dataType: "json",
    	success: function(data){ //回调方法 
    		bindJsonObj2Cntrl(data,caseCode);
    		initComboBox(data);
    		if(data.isSecondPress == 1){
    			//初始化二次催告
    			$('.secondPress-info-tr').show();
    			$.parser.parse($('.secondPress-info-tr'));

    		}else{
    			$('.secondPress-info-tr').hide();
    		}
    		
    	},
    });

}
/**
 * 初始化系统编码
 */
function initComboBox(data) {
    var result = tools.requestJsonRs("/sysCode/getSysCodeNameByParentCodeNo.action",{
        parentCodeNo : "COMMON_SENT_WAY",
        codeNo : data.pressSendType,
    });
    $('#pressSendType').text(result.rtData);
    var result1 = tools.requestJsonRs("/sysCode/getSysCodeNameByParentCodeNo.action",{
        parentCodeNo : "COMMON_SENT_WAY",
        codeNo : data.secondPressType,
    });
    $('#secondPressType').text(result1.rtData);
    var result2 = tools.requestJsonRs("/sysCode/getSysCodeNameByParentCodeNo.action",{
        parentCodeNo : "COURT_ENFORCE_TYPE",
        codeNo : data.performType,
    });
    $('#performType').text(result2.rtData);
}

function back(){
	var parentPage = $("#parentPage").val();
    location.href = contextPath + parentPage;
}