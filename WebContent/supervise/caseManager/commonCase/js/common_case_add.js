/**
 * 默认加载方法
 * @returns
 */
function doInitAdd(){
    initTabs();//初始化tabs页签
}

/**
 * 初始化tabs页签
 * @returns
 */
function initTabs(){
	var commonCaseId = $('#common_case_id').val();
    var editFlag = $('#common_case_editFlag').val();
    var isNext = $('#common_case_isNext').val();
    var modelId = $('#common_case_modelId').val();
    //定义tabs
    var id = "common_case_add_tabs";
    var url = '/caseCommonBaseCtrl/commonCaseAddGrading.action';
    var pageUrl = [
        '/supervise/caseManager/commonCase/common_case_add_1_filing.jsp',
        '/supervise/caseManager/commonCase/common_case_add_2_research.jsp',
        '/supervise/caseManager/commonCase/common_case_add_3_correct.jsp',
        '/supervise/caseManager/commonCase/common_case_add_4_present.jsp',
        '/supervise/caseManager/commonCase/common_case_add_5_execute.jsp'
    ];
    var grading = [
        '01','02','03','04','05'
    ];
    var tabsJson = [
        {"title":"立案","url": url+"?pageUrl="+pageUrl[0]+"&grading="+grading[0]+"&caseId="+commonCaseId+"&editFlag="+editFlag+"&modelId="+modelId},
        {"title":"调查取证","url": url+"?pageUrl="+pageUrl[1]+"&grading="+grading[1]+"&caseId="+commonCaseId+"&editFlag="+editFlag+"&modelId="+modelId},
        {"title":"审查决定","url": url+"?pageUrl="+pageUrl[2]+"&grading="+grading[2]+"&caseId="+commonCaseId+"&editFlag="+editFlag+"&modelId="+modelId},
        {"title":"处罚执行","url": url+"?pageUrl="+pageUrl[3]+"&grading="+grading[3]+"&caseId="+commonCaseId+"&editFlag="+editFlag+"&modelId="+modelId},
        {"title":"结案","url": url+"?pageUrl="+pageUrl[4]+"&grading="+grading[4]+"&caseId="+commonCaseId+"&editFlag="+editFlag+"&modelId="+modelId}
    ];
    
    //循环加载
    for(var i=0; i<tabsJson.length; i++){  
        addTab(id, tabsJson[i].title,tabsJson[i].url);
        if('1' == editFlag){ //新增
            disabledTab(id,i); //禁用tabs
        }else if('2' == editFlag){
//            if(parseInt(isNext) < i){
//                disabledTab(id,i); //禁用tabs
//            }
        }
    }  

    //设置默认选中
    if('1' == editFlag){//新增到某一步，打开新增，直接跳转
        if(isNext == null || isNext == ''){
            isNext = 0;
        }
        setTab(id,parseInt(isNext));
    }else{
        if(isNext == null || isNext == ''){
            isNext = 0;
        }
        setTab(id,parseInt(isNext));
        updateTabs(parseInt(isNext));
    }
    /*$("#common_case_add_tabs").tabs({
    	onSelect:function(title,index){
    		var pageDoc = '<button class="btn-alert-blue" id="btn" style="margin-right:5px;" onclick="doSave(\''+index+'\');">保存</button>';
    		if(index == 2){
    			pageDoc += '<button  class="btn-alert-blue" id="btn" style="margin-right:5px;letter-spacing: 0; text-indent: 0;" onclick="deRevokeSave();">撤销立案</button>';
    		}else if(index == 3){
    			pageDoc += '<button  class="btn-alert-blue" id="btn" style="margin-right:5px;letter-spacing: 0; text-indent: 0;" onclick="doRevokePunishment();">撤销原处罚决定</button>';
    			pageDoc += '<button  class="btn-alert-blue" id="btn" style="margin-right:5px;" onclick="doCommonCaseEnd();">终结</button>';
    		}
    		pageDoc += '<button class="btn-alert-blue" style="" onclick="back()">返回</button>';
    		$("#simpleBtn").html(pageDoc);
            $.parser.parse($("#simpleBtn"));
    	}
    })*/
    $("#common_case_add_tabs").tabs({
    	onSelect:function(title,index){
    		updateTabs(index);
    	}
    })
}

function addBackIndex(){
    {
        window.location.href = contextPath+"/caseCommonBaseCtrl/commonCaseIndex.action";
    }
}

/**
 * 修改底部按钮
 * @param index 索引
 */
function updateTabs(index){
	var pageDoc = '<button class="easyui-linkbutton" title="保存" onclick="doSave(\''+index+'\');">'
					+'<span style="padding-right: 2px; width: 40px;"><i class="fa fa-save"></i> 保存</span>'
				 +'</button>';
	if(index == 2){
		pageDoc += '&nbsp;&nbsp;<button class="easyui-linkbutton" title="撤销立案" onclick="deRevokeSave();">'
					+'<span style="padding-right: 2px; width: 40px;"><i class="fa fa-wrench"></i> 撤销立案</span>'
				  +'</button>';
	}else if(index == 3){
		pageDoc += '&nbsp;&nbsp;<button class="easyui-linkbutton" title="撤销原处罚决定" onclick="doRevokePunishment();">'
					+'<span style="padding-right: 2px; width: 40px;"><i class="fa fa-gavel"></i> 撤销原处罚决定</span>'
				  +'</button>';
		pageDoc += '&nbsp;&nbsp;<button class="easyui-linkbutton" title="终结" onclick="doCommonCaseEnd();">'
					+'<span style="padding-right: 2px; width: 40px;"><i class="fa fa-stop"></i> 终结</span>'
				  +'</button>';
	}
	pageDoc += '&nbsp;&nbsp;<button class="easyui-linkbutton" title="返回" onclick="back();">'
			 +'<span style="padding-right: 2px; width: 40px;"><i class="fa fa-reply"></i> 返回</span>'
			+'</button>';
	$("#simpleBtn").html(pageDoc);
	$.parser.parse($("#simpleBtn"));
}

/**
 * 关闭弹框
 * @param modelId
 * @returns
 */
function closeCommonCaseWindow(modelId){
    parent.$(".iframeDiv"+modelId).hide();
    parent.$(".zhezhao"+modelId).remove();
    var cw;
    var iframe = parent.$(".body1").find('iframe');
    if(iframe != null && iframe.length > 0){
        cw = iframe[iframe.length-1].contentWindow;
        cw.commonCaseSearch();
    }
}

/**
 * 返回维护页
 */
function back(){
	top.$.MsgBox.Confirm("提示","离开后未保存数据将丢失，确定返回？",function(){
		location.href = "/caseCommonBaseCtrl/commonCaseIndex.action";
    })
}

/**
 * 保存
 * @param abc
 */
function doSave(index){
	var i = parseInt(index);
	switch(i){
    case 0:
    	$("iframe")[0].contentWindow.doFilingSave();
    	break;
    case 1:
    	$("iframe")[1].contentWindow.doResearchSave();
    	break;
    case 2:
    	$("iframe")[2].contentWindow.doCorrectSave();
    	break;
    case 3:
    	$("iframe")[3].contentWindow.doPresentSave();
    	break;
    case 4:
    	$("iframe")[4].contentWindow.doExecuteSave(0);
    	break;
    }
}

/**
 * 撤销立案
 */
function deRevokeSave(){
	$("iframe")[2].contentWindow.deRevokeSave();
}

/**
 * 撤销原处罚决定
 */
function doRevokePunishment(){
	$("iframe")[3].contentWindow.doRevokePunishment();
}

/**
 * 终结
 */
function doCommonCaseEnd(){
	$("iframe")[3].contentWindow.doCommonCaseEnd();
}