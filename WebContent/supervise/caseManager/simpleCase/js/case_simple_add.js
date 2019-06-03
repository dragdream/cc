var subjectId = "";
var filingForm = "";
//定义tabs
var id = "common_case_add_tabs";
var editFlag = $("#common_case_editFlag").val();
var commonCaseId = $("#common_case_id").val();
var modelId = $('#common_case_modelId').val();
var button = '<button class="easyui-linkbutton" title="保存" onclick="nextPage(\'1\');">'
    +'<span style="padding-right: 2px; width: 40px;"><i class="fa fa-save"></i> 保存</span>'
    +'</button>'
    +'&nbsp;&nbsp;'
    +'<button class="easyui-linkbutton" title="返回" onclick="back();">'
    +'<span style="padding-right: 2px; width: 40px;"><i class="fa fa-reply"></i> 返回</span>'
    +'</button>';

/**
 * 默认加载方法
 * @returns
 */
function doInitAdd(){
    initTabs();//初始化tabs页签
    if ('2'==editFlag||'3'==editFlag) {
    	$("#simpleBtn").html(button);
        $.parser.parse($("#simpleBtn"));
    }
}

/**
 * 初始化tabs页签
 * @returns
 */
function initTabs(){
    var tabsJson = [
        {"title":"基本信息","url":"/supervise/caseManager/simpleCase/case_simple_add_1_filing.jsp?caseId="+commonCaseId+"&editFlag="+editFlag+"&modelId="+modelId},
        {"title":"违法行为及依据","url":"/supervise/caseManager/simpleCase/case_simple_add_5_execute.jsp?caseId="+commonCaseId+"&editFlag="+editFlag+"&modelId="+modelId}
    ];
    //循环加载
    for(var i=0; i<tabsJson.length; i++){  
        addTab(id, tabsJson[i].title,tabsJson[i].url);
        if ('2'==editFlag||'3'==editFlag) {
            continue;
        }
        disabledTab(id,i); //禁用tabs
    }  
    //设置默认选中
    setTab(id,0);
}

function addBackIndex(){
    {
        window.location.href = contextPath+"/caseSimpleBaseCtrl/simpleCaseIndex.action";
    }
}

/**
 * 关闭弹框
 * @param modelId
 * @returns
 */
function closeSimpleCaseWindow(modelId){
    parent.$(".iframeDiv"+modelId).hide();
    parent.$(".zhezhao"+modelId).remove();
    var cw;
    var iframe = parent.$(".body1").find('iframe');
    if(iframe != null && iframe.length > 0){
        cw = iframe[iframe.length-1].contentWindow;
        //cw.commonCaseSearch();
        cw.$("#case_simple_index_datagrid").datagrid("reload");
        cw.$('#case_simple_index_datagrid').datagrid("clearSelections");
    }
}

function back(){
	top.$.MsgBox.Confirm("提示","离开后未保存数据将丢失，确定返回？",function(){
		location.href = "/caseSimpleBaseCtrl/simpleCaseIndex.action";
    })
}

/**
 * 下一页/保存
 * @param abc
 */
function nextPage(abc){
    if(abc=='0'){// 点击下一步
        var isNext = $("iframe")[0].contentWindow.nextPage();
        if(isNext){
            var tabId="common_case_add_tabs";
            var title = "违法行为及依据";
            var pageUrl = '/supervise/caseManager/simpleCase/case_simple_add_5_execute.jsp?editFlag='+editFlag+"&modelId="+modelId;
            addTab(tabId, title, pageUrl);
            $("#simpleBtn").html(button);
            $.parser.parse($("#simpleBtn"));
        }
    }else{// 点击保存
        if('1' == editFlag){// 新增
            if($("iframe")[0].contentWindow.nextPage() && $("iframe")[1].contentWindow.doSave()){// 校验通过
                var json = tools.requestJsonRs("/caseSimpleBaseCtrl/saveCaseSimpleBase.action",filingForm);
                if (json.rtState) {
                    top.$.MsgBox.Alert_auto("保存成功");
                    location.href = "/caseSimpleBaseCtrl/simpleCaseIndex.action";
                }else{
                    top.$.MsgBox.Alert_auto("保存失败");
                }
            }
        }else if('2' == editFlag){// 修改
            var tab = $("#common_case_add_tabs").tabs('getSelected');
            var index = $("#common_case_add_tabs").tabs('getTabIndex', tab);
            if(index == 0 && $("iframe")[0].contentWindow.nextPage()){// 基本信息页保存
                var json = tools.requestJsonRs("/caseSimpleBaseCtrl/updateCaseSimpleBase.action",filingForm);
                if (json.rtState) {
                    top.$.MsgBox.Alert_auto("修改成功");
                    // 基本信息页保存成功后跳转下一页签
                    var tabId="common_case_add_tabs";
                    var title = "违法行为及依据";
                    var pageUrl = '/supervise/caseManager/simpleCase/case_simple_add_5_execute.jsp?editFlag='+editFlag+"&modelId="+modelId;
                    addTab(tabId, title, pageUrl);
                }else{
                    top.$.MsgBox.Alert_auto("修改失败");
                }
                
            }else if(index == 1 && $("iframe")[0].contentWindow.nextPage() && $("iframe")[1].contentWindow.doSave()){// 违法行为及依据保存
                var json = tools.requestJsonRs("/caseSimpleBaseCtrl/updateCaseSimpleBase.action",filingForm);
                if (json.rtState) {
                    top.$.MsgBox.Alert_auto("修改成功");
                    // 违法行为及依据页签保存，保存内容为整个简易案件内容，保存后关闭弹窗
                    location.href = "/caseSimpleBaseCtrl/simpleCaseIndex.action";
                }else{
                    top.$.MsgBox.Alert_auto("修改失败");
                }
            }
        }
    }
}