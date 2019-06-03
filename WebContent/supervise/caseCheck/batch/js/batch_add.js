var id = $("#id").val();
//保存标识
var isSaved = false;
var param = "";
//区级显隐标识
var isExists = false;
function doInit(){
	//案卷类型
	caseTypeInit();
	//区级
	qujiInit();
	//所属领域
	batch_orgsysInit();
	//区级显示框
	batchAreaInit();
	if(id.length > 0){//编辑
		var json = tools.requestJsonRs("/jdCasecheckMissionTempCtrl/get.action",{id:id});
		bindJsonObj2Easyui(json.rtData , "form1");
	}
}
//保存前校验
function next(){
	if(isSaved){
		nextStep(0);
	}else{
		$.MsgBox.Alert_auto("请先进行保存");
	}
}
//保存
function save(){
	if($('#form1').form('enableValidation').form('validate')){
		param = tools.formToJson($("#form1"));
		//校验日期
		var oDate1 = new Date(param.beginDateStr);
	    var oDate2 = new Date(param.endDateStr);
	    if(oDate1.getTime() > oDate2.getTime()){
			$.MsgBox.Alert_auto("评查开始日期不能大于评查结束日期");
			return false;
	    }
		if(param.caseType == '01'){
			param.coercionMeasureBeginDateStr = "";
			param.coercionMeasureEndDateStr = "";
			param.coercionPerformBeginDateStr = "";
			param.coercionPerformEndDateStr = "";
			param.faForceBeginDateStr = "";
			param.faForceEndDateStr = "";
			param.filingBeginDateStr = "";
			param.filingBeginDateStr = "";
			param.desBeginDateStr = "";
			param.desEndDateStr = "";
			param.executionBeginDateStr = "";
			param.executionEndDateStr = "";
			param.closeBeginDateStr = "";
			param.closeEndDateStr = "";
			if(param.inspBeginDateStr =="" || param.inspEndDateStr == ""){
				$.MsgBox.Alert_auto("请选择起始日期和截止日期");
				return false;
			}else{
				//校验日期
				var oDate1 = new Date(param.inspBeginDateStr);
			    var oDate2 = new Date(param.inspEndDateStr);
			    if(oDate1.getTime() > oDate2.getTime()){
					$.MsgBox.Alert_auto("起始日期不能大于截止日期");
					return false;
			    }
			}
		}else if(param.caseType == '02'){
			param.inspBeginDateStr = "";
			param.inspEndDateStr = "";
			param.filingBeginDateStr = "";
			param.filingBeginDateStr = "";
			param.desBeginDateStr = "";
			param.desEndDateStr = "";
			param.executionBeginDateStr = "";
			param.executionEndDateStr = "";
			param.closeBeginDateStr = "";
			param.closeEndDateStr = "";
			if(param.coercionMeasureBeginDateStr =="" || param.coercionMeasureEndDateStr == "" || param.coercionPerformBeginDateStr == "" || param.coercionPerformEndDateStr == "" || param.faForceBeginDateStr == "" || param.faForceEndDateStr == "" ){
				$.MsgBox.Alert_auto("请选择起始日期和截止日期");
				return false;
			}else{
				//校验日期
				var oDate1 = new Date(param.coercionMeasureBeginDateStr);
			    var oDate2 = new Date(param.coercionMeasureEndDateStr);
			    if(oDate1.getTime() > oDate2.getTime()){
					$.MsgBox.Alert_auto("起始日期不能大于截止日期");
					return false;
			    }
			    var oDate1 = new Date(param.coercionPerformBeginDateStr);
			    var oDate2 = new Date(param.coercionPerformEndDateStr);
			    if(oDate1.getTime() > oDate2.getTime()){
					$.MsgBox.Alert_auto("起始日期不能大于截止日期");
					return false;
			    }
			    var oDate1 = new Date(param.faForceBeginDateStr);
			    var oDate2 = new Date(param.faForceEndDateStr);
			    if(oDate1.getTime() > oDate2.getTime()){
					$.MsgBox.Alert_auto("起始日期不能大于截止日期");
					return false;
			    }
			}
		}else if(param.caseType == '03'){
			param.inspBeginDateStr = "";
			param.inspEndDateStr = "";
			param.coercionMeasureBeginDateStr = "";
			param.coercionMeasureEndDateStr = "";
			param.coercionPerformBeginDateStr = "";
			param.coercionPerformEndDateStr = "";
			param.faForceBeginDateStr = "";
			param.faForceEndDateStr = "";
			if(param.filingBeginDateStr =="" || param.filingBeginDateStr == "" || param.desBeginDateStr == "" || param.desEndDateStr == "" || param.executionBeginDateStr == "" || param.executionEndDateStr == "" || param.closeBeginDateStr == "" || param.closeEndDateStr == ""){
				$.MsgBox.Alert_auto("请选择起始日期和截止日期");
				return false;
			}else{
				//校验日期
				var oDate1 = new Date(param.filingBeginDateStr);
			    var oDate2 = new Date(param.filingBeginDateStr);
			    if(oDate1.getTime() > oDate2.getTime()){
					$.MsgBox.Alert_auto("起始日期不能大于截止日期");
					return false;
			    }
			    var oDate1 = new Date(param.desBeginDateStr);
			    var oDate2 = new Date(param.desEndDateStr);
			    if(oDate1.getTime() > oDate2.getTime()){
					$.MsgBox.Alert_auto("起始日期不能大于截止日期");
					return false;
			    }
			    var oDate1 = new Date(param.executionBeginDateStr);
			    var oDate2 = new Date(param.executionEndDateStr);
			    if(oDate1.getTime() > oDate2.getTime()){
					$.MsgBox.Alert_auto("起始日期不能大于截止日期");
					return false;
			    }
			    var oDate1 = new Date(param.closeBeginDateStr);
			    var oDate2 = new Date(param.closeEndDateStr);
			    if(oDate1.getTime() > oDate2.getTime()){
					$.MsgBox.Alert_auto("起始日期不能大于截止日期");
					return false;
			    }
			}
		}
		param.batchOrgsys = $('#batchOrgsys').combobox('getValues')+"";
		param.batchArea = $('#batchArea').combobox('getValues')+"";
		if(isExists){
			param.batchQuji = $('#batchQuji').combobox('getValues')+"";
		}else{
			param.batchQuji = "";
		}
		var json = tools.requestJsonRs("/jdCasecheckMissionTempCtrl/save.action",param);
		if(json.rtState){
			isSaved = true;
			$.MsgBox.Alert_auto("保存成功");
		}else{
			$.MsgBox.Alert_auto("保存失败");
		}
	}
}
/**
 * 返回维护页
 */
function back(){
	top.$.MsgBox.Confirm("提示","离开后未保存数据将丢失，确定返回？",function(){
		location.href = "/supervise/caseCheck/batch/batch_index.jsp";
    })
}
//按区级
function qujiInit(){
	$('#batchQuji').combobox({
		prompt:'请选择',
		mode:'remote',
		url:contextPath + '/adminDivisionManageCtrl/getAreaBatch.action',
		valueField:'ID',
		textField:'NAME',
		method:'post',
		panelHeight:'100px',
		labelPosition: 'top',
		multiple:true,
        formatter: function (row) {
            var opts = $(this).combobox('options');
          	 $('#batchQuji').combobox('select',row[opts.valueField]);
            return '<input type="checkbox" class="combobox-checkbox" style="margin-right:5px;cursor:pointer;">' + row[opts.textField]
        },

        onShowPanel: function () {
            var opts = $(this).combobox('options');
            var target = this;
            var values = $(target).combobox('getValues');
            $.map(values, function (value) {
                var el = opts.finder.getEl(target, value);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
            })
        },
        onLoadSuccess: function () {
            var opts = $(this).combobox('options');
            var target = this;
            var values = $(target).combobox('getValues');
            $.map(values, function (value) {
                var el = opts.finder.getEl(target, value);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
            })
        },
        onSelect: function (row) {
            //console.log(row);
            var opts = $(this).combobox('options');
            var el = opts.finder.getEl(this, row[opts.valueField]);
            el.find('input.combobox-checkbox')._propAttr('checked', true);
        },
        onUnselect: function (row) {
            var opts = $(this).combobox('options');
            var el = opts.finder.getEl(this, row[opts.valueField]);
            el.find('input.combobox-checkbox')._propAttr('checked', false);
        }
	});
}
//案卷类型
function caseTypeInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "CASE_TYPE"});
    if(json.rtState) {
        $('#caseType').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'auto',
            onChange:function(row){
            	if(row == '01'){
            		document.getElementById("jiancha").style.display="";
            		qiangzhinone();
            		chufanone();
            	}else if (row == '02'){
            		qiangzhi();
            		chufanone();
            		document.getElementById("jiancha").style.display="none";
            	}else if (row == '03'){
            		document.getElementById("jiancha").style.display="none";
            		qiangzhinone();
            		chufa();
            	}
            }
        });
    }
}
//强制显隐
function qiangzhi(){
	var trs = $("tr[class='qiangzhi']");
	for(i = 0; i < trs.length; i++){
	        trs[i].style.display = "";
	}
}
function qiangzhinone(){
	var trs = $("tr[class='qiangzhi']");
	for(i = 0; i < trs.length; i++){
	        trs[i].style.display = "none";
	}
}
//处罚显隐
function chufa(){
	var trs = $("tr[class='chufa']");
	for(i = 0; i < trs.length; i++){
	        trs[i].style.display = "";
	}
}
function chufanone(){
	var trs = $("tr[class='chufa']");
	for(i = 0; i < trs.length; i++){
	        trs[i].style.display = "none";
	}
}
//所属领域
function batch_orgsysInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "LAW_ENFORCEMENT_FIELD"});
	if(json.rtState) {
        $('#batchOrgsys').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
    		panelHeight:'150px',
    		multiple:true,
            formatter: function (row) {
                var opts = $(this).combobox('options');
                return '<input type="checkbox"  class="combobox-checkbox" style="margin-right:5px;cursor:pointer;">' + row[opts.textField]
            },

            onShowPanel: function () {
                var opts = $(this).combobox('options');
                var target = this;
                var values = $(target).combobox('getValues');
                $.map(values, function (value) {
                    var el = opts.finder.getEl(target, value);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                })
            },
            onLoadSuccess: function () {
                var opts = $(this).combobox('options');
                var target = this;
                var values = $(target).combobox('getValues');
                $.map(values, function (value) {
                    var el = opts.finder.getEl(target, value);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                })
                //全选
                for(var i=0;i<json.rtData.length;i++){
                	 $('#batchOrgsys').combobox('select',json.rtData[i].codeNo);
                }
            },
            onSelect: function (row) {
                //console.log(row);
                var opts = $(this).combobox('options');
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
            },
            onUnselect: function (row) {
                var opts = $(this).combobox('options');
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', false);
            }
        });
    }
}
//区级显示框
function batchAreaInit(){
	$('#batchArea').combobox({
		onChange:function(row){
			//包含区级则显示 区级框
			if(row.indexOf('2') == 0 || row.indexOf('2') == 1){
				document.getElementById("quji").style.display="";
				isExists = true;
			}else{
				document.getElementById("quji").style.display="none";
				isExists = false;
			}
		}
	});
}