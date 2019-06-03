var caseId = $('#comm_case_add_correct_caseId').val(); //案件ID
var editFlag = $('#common_case_add_correct_editFlag').val(); //编辑标识（1新增，2修改，3查看）
var isNext = $('#common_case_add_correct_isNext').val();//tab页签标识（立案0,调查取证1,处罚决定2,处罚执行3,结案4）
var actSubject = $('#common_case_add_correct_subjectId').val();//实施主体ID
var caseName = $('#common_case_add_correct_caseName').val();//案件名称
var registerCode = $('#common_case_add_correct_registerCode').val();//立案号
var correctModelId = $('#common_case_add_correct_modelId').val();//弹框modelID
var punishFile = [];//处罚决定文书
var sendProofFile = [];//不予处罚决定文书
var addPower = []; //添加的违法行为
var powerJson = []; //违法行为对象
var gistJson = []; //违法依据对象
var punishJson = []//处罚依据对象
var discretionaryJson = [];//自由裁量标准
var nopunishmentJson = []; //不用处罚对象
var isTrueJson = [
	{codeNo:'1', codeName: '是'},
	{codeNo:'0', codeName: '否'}
]
/**
 * 默认加载方法
 */
function doInitCorrect(){
//    initCommonIsLegalQualifications();//有无法律职业资格证
	initJsonListRadio(isTrueJson, 'isCriminal');// 初始化是否涉刑案件
	initJsonListRadio(isTrueJson, 'isMajorCase');// 初始化是否重大案件
	initCommonIsLegalReview();// 初始化是否法制审核
	initCommonIsCollectiveDiscussion();// 初始化是否集体讨论
	initCommonIsHearing();// 初始化是否听证
	
    initCommonCaseIsPunishment();// 初始化作出处罚决定
    //上传处罚决定书
    doInitMultipleUploadPunish('commonCase', caseId+'punishmentDocumentId', 'punishmentDocument');
    
    //上传送达回证
    doInitMultipleUploadSendProof('commonCase', caseId+'sendProofDocumentId', 'sendProofDocument');
    
    initCommonIsWarn();// 是否警告
    initCommonIsFine();// 是否罚款
    initCommonIsConfiscate();//是否没收违法所得、没收非法财物
    
    initCommonIsOrderClosure();// 责令停产停业
    initCommonIsTdLicense();// 暂扣或者吊销许可证、暂扣或者吊销执照
    initCommonCaseIsTdOrRevokeLicense();
    initCommonIsDetain();// 是否行政拘留
    initCommonIsOther();//其他
    dateValidate('detainStartdateStr', 'detainEnddateStr');
    dateValidate('withholdingStartdateStr', 'withholdingEnddateStr');
    if('1' == editFlag){
    	initCodeListInput("COMMON_HEARING_WAY","hearingWay");// 初始化听证方式
    	initCodeListInputNoRequired("COMMON_HEARING_CONCLUSION","hearingConclusion");// 初始化听证结论
    	initCodeListInput("COMMON_SENT_WAY","punishmentSendWay");// 送达方式
    	initHandDatagrid({ids: 'empty'});//违法行为信息加载 
        initGistDatagrid({powerId: 'empty'}); //违法依据
        initPunishDatagrid({powerId: "empty"});//加载处罚依据
    }else{
        doInitEditCorrect(); //修改加载
    }
    
}

/**
 * 初始化是否法制审核
 */
function initCommonIsLegalReview(){
	initJsonListRadio(isTrueJson, 'isLegalReview');
	$('#isLegalReviewTr .case-handle-date').hide();
	$('#isLegalReview1').radiobutton({
        onChange: function(check){
            if(check){
                $('#isLegalReviewTr .case-handle-date').show();
                $('#legalReviewDateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择法制审核日期'});
                $('#legalExaminaSuggest').textbox({disabled:false, required:true, missingMessage:'请填写法制审核意见'});
            }else{
                var ids = ['legalReviewDateStr','legalExaminaSuggest'];
            	var types = ['textbox','textbox'];
            	inputRefresh(ids, types);
                $('#isLegalReviewTr .case-handle-date').hide();
            }
        }
    });
}

/**
 * 初始化是否集体讨论
 */
function initCommonIsCollectiveDiscussion(){
	initJsonListRadio(isTrueJson, 'isCollectiveDiscussion');
	$('#isCollectiveDiscussionTr .case-handle-date').hide();
	$('#isCollectiveDiscussion1').radiobutton({
        onChange: function(check){
            if(check){
            	$('#isCollectiveDiscussionTr .case-handle-date').show();
                $('#collectiveDiscussionDateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择集体讨论日期'});
                $('#collectiveDiscussionResult').textbox({disabled:false, required:true, validType:'length[0,2000]', missingMessage:'请填写集体讨论结论'});
            }else{
                var ids = ['collectiveDiscussionDateStr','collectiveDiscussionResult'];
            	var types = ['textbox','textbox'];
            	inputRefresh(ids, types);
                
                $('#isCollectiveDiscussionTr .case-handle-date').hide();
            }
        }
    });
}

/**
 * 初始化是否听证
 */
function initCommonIsHearing(){
	initJsonListRadio(isTrueJson, 'isHearing');
	$('#isHearingTr').hide();
	$('#isHearing1').radiobutton({
        onChange: function(check){
        	if(check){
            	$('#isHearingTr').show();
            	$('#hearingWay').textbox({disabled:false,required:true, missingMessage:'请选择听证方式'});// 听证方式
                $('#hearingHoldDateStr').textbox({disabled:false,required:true, validType:'date', missingMessage:'请选择听证举行日期'});// 听证举行日期
                $('#hearingApplyDateStr').textbox({disabled:false, validType:'date'});// 申请听证日期
                $('#hearingInformDateStr').textbox({disabled:false, validType:'date'});// 听证通知送达日期
                $('#hearingNoticeDateStr').textbox({disabled:false, validType:'date'});// 听证公告发布日期
                $('#hearingAddress').textbox({disabled:false});// 听证地点
                $('#hearingHost').textbox({disabled:false, validType:'length[0,50]'});// 主持人
                $('#hearingPerson').textbox({disabled:false, validType:'length[0,200]'});// 听证员
                $('#hearingRecorder').textbox({disabled:false, validType:'length[0,50]'});// 记录人
                $('#hearingParticipants').textbox({disabled:false, validType:'length[0,400]'});// 参加人
                $('#hearingConclusion').textbox({disabled:false});// 听证结论
                $('#hearingResult').textbox({disabled:false, validType:'length[0,2000]'});// 听证结果（报告）
            }else{
            	var ids = ['hearingWay','hearingHoldDateStr','hearingApplyDateStr','hearingInformDateStr',
            	           'hearingNoticeDateStr','hearingAddress','hearingHost','hearingPerson',
            	           'hearingRecorder','hearingParticipants','hearingConclusion','hearingResult'];
            	var types = ['textbox','textbox','textbox','textbox','textbox','textbox','textbox',
            	           'textbox','textbox','textbox','textbox','textbox'];
            	inputRefresh(ids, types);
            	// inputValidate(ids, types);
                $('#isHearingTr').hide();
            }
        }
    });
}

/**
 * 修改 回显方法
 * @returns
 */
function doInitEditCorrect(){
    var grading = '03';//处罚决定阶段
    var json = tools.requestJsonRs("/caseCommonBaseCtrl/findCaseCommonBaseById.action?id=" + caseId +"&grading="+grading);
    if(json.rtState){
    	
    	var punishmentDocumentPath = json.rtData.punishmentDocumentPath;
    	if(punishmentDocumentPath != null && punishmentDocumentPath != ''){
    		// 修改回显处罚决定书
    	    //initAttachmentInfoPunish('commonCase', punishmentDocumentPath, 'punishmentDocumentName');
    		punishFile = initAttachmentInfo('commonCase', punishmentDocumentPath, 'punishmentDocument');
    	}
    	
    	var sendProofDocumentPath = json.rtData.sendProofDocumentPath;
    	if(sendProofDocumentPath != null && sendProofDocumentPath != ''){
    		// 修改回显送达回证
    		sendProofFile = initAttachmentInfo('commonCase', sendProofDocumentPath, 'sendProofDocument');
    	}
    	
    	// 违法行为、违法依据、处罚依据加载begin
    	var powerJsonStr = json.rtData.powerJsonStr;
        var powerJson;
        if(powerJsonStr != null && powerJsonStr != '' && powerJsonStr != 'null'){
            powerJson = powerJsonStr.split(",");
            var params;
            var paramsGist;
            var paramsPunish;
            var paramsDiscretionary;
            for (var i = 0; i < powerJson.length; i++){
                addPower.push(powerJson[i]);
            }
            if(addPower == null || addPower.length < 1){
                params = {ids: 'empty',actSubject: actSubject};
                paramsGist = {powerId: 'empty',gistType: '01'};
                paramsPunish = {powerId: 'empty',gistType: '02'};//依据类型（01 违法依据，02处罚依据，02设定依据）
                paramsDiscretionary = {powerId: 'empty'}
            } else {
                params = {
                    ids : addPower.join(","),
                    actSubject : actSubject
                };
                paramsGist = {
                    powerId : addPower.join(","),
                    gistType : '01'
                };
                paramsPunish = {
                    powerId : addPower.join(","),
                    gistType : '02' // 依据类型（01 违法依据，02处罚依据，02设定依据）
                };
                paramsDiscretionary = {
                    powerId : addPower.join(",")
                };
            }
            initHandDatagrid(params);//违法行为信息加载 
            initGistDatagrid(paramsGist);  //违法依据信息加载
            initPunishDatagrid(paramsPunish);//加载处罚依据
            initDiscretionaryDatagrid(paramsDiscretionary);//加载自由裁量权
            
            var gistJsonStr = json.rtData.gistJsonStr;
            gistJson = [];
            if(gistJsonStr != null && gistJsonStr != '' && gistJsonStr != 'null'){
                gistJson = gistJsonStr.split(",");
            }
            var punishJsonStr = json.rtData.punishJsonStr;
            punishJson = [];
            if(punishJsonStr != null && punishJsonStr.length > 0){
                punishJson = punishJsonStr.split(',');
            }
            var discretionaryJsonStr = json.rtData.discretionaryJsonStr;
            discretionaryJson = [];
            if(discretionaryJsonStr != null && discretionaryJsonStr.length > 0){
                discretionaryJson = discretionaryJsonStr.split(',');
            }
        }else{
            initHandDatagrid({ids: 'empty'});//违法行为信息加载 
            initGistDatagrid({powerId: 'empty'}); //违法依据
            initPunishDatagrid({powerId: "empty",gistType: '02'});//加载处罚依据
            initDiscretionaryDatagrid({powerId: 'empty'});//加载自由裁量权
        }
        // 违法行为、违法依据、处罚依据加载end
        
        // 是否涉刑案件
        $('#isCriminal'+json.rtData.isCriminal).radiobutton({checked: true});
        // 是否重大案件
        $('#isMajorCase'+json.rtData.isMajorCase).radiobutton({checked: true});
        // 是否法制审核
        $('#isLegalReview'+json.rtData.isLegalReview).radiobutton({checked: true});
        var isLegalReview = json.rtData.isLegalReview;
        if (isLegalReview == '1') {
            $('#isLegalReviewTr .case-handle-date').show();
            $('#legalReviewDateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择法制审核日期'});
            $('#legalExaminaSuggest').textbox({disabled:false, required:true, missingMessage:'请填写法制审核意见'});
        }
        // 是否集体讨论
        $('#isCollectiveDiscussion'+json.rtData.isCollectiveDiscussion).radiobutton({checked: true});
        var isCollectiveDiscussion = json.rtData.isCollectiveDiscussion;
        if (isCollectiveDiscussion == '1') {
            $('#isCollectiveDiscussionTr .case-handle-date').show();
            $('#collectiveDiscussionDateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择集体讨论日期'});
            $('#collectiveDiscussionResult').textbox({disabled:false, required:true, validType:'length[0,2000]', missingMessage:'请填写集体讨论结论'});
        }
        // 是否听证
        $('#isHearing'+json.rtData.isHearing).radiobutton({checked: true});
        var isHearing = json.rtData.isHearing;
        if (isHearing == '1') {
            
            $('#isHearingTr').show();
        	$('#hearingWay').textbox({disabled:false,required:true, missingMessage:'请选择听证方式'});// 听证方式
            $('#hearingHoldDateStr').textbox({disabled:false,required:true, validType:'date', missingMessage:'请选择听证举行日期'});// 听证举行日期
            $('#hearingApplyDateStr').textbox({disabled:false, validType:'date'});// 申请听证日期
            $('#hearingInformDateStr').textbox({disabled:false, validType:'date'});// 听证通知送达日期
            $('#hearingNoticeDateStr').textbox({disabled:false, validType:'date'});// 听证公告发布日期
            $('#hearingAddress').textbox({disabled:false});// 听证地点
            $('#hearingHost').textbox({disabled:false, validType:'length[0,50]'});// 主持人
            $('#hearingPerson').textbox({disabled:false, validType:'length[0,200]'});// 听证员
            $('#hearingRecorder').textbox({disabled:false, validType:'length[0,50]'});// 记录人
            $('#hearingParticipants').textbox({disabled:false, validType:'length[0,400]'});// 参加人
            $('#hearingConclusion').textbox({disabled:false});// 听证结论
            $('#hearingResult').textbox({disabled:false, validType:'length[0,2000]'});// 听证结果（报告）
        }
        
        // 下拉框初始化
        initCodeListInput("COMMON_HEARING_WAY","hearingWay",json.rtData.hearingWay);// 初始化听证方式
        initCodeListInputNoRequired("COMMON_HEARING_CONCLUSION","hearingConclusion",json.rtData.hearingConclusion);// 初始化听证结论
    	initCodeListInput("COMMON_SENT_WAY","punishmentSendWay",json.rtData.punishmentSendWay);// 送达方式
        
        // 作出处罚or不予处罚
        var isPunishment = json.rtData.isPunishment;
        if(isPunishment == '1'){
            $('#isPunishment1').radiobutton({checked:true});
            $("#common_case_is_punishment_div").show();
            $('#common_case_is_punishment_punish_div').show();
            $.parser.parse('#common_case_is_punishment_div');
            $.parser.parse('#common_case_is_punishment_punish_div');
            
            // 警告
            var isWarn = json.rtData.isWarn;
            if(isWarn == '1'){
                $('#isWarn').checkbox({checked: true});
            }
            // 罚款
            var isFine = json.rtData.isFine;
            if(isFine == '1'){
                $('#isFine').checkbox({checked: true});
                $('#isFine_td .text-input-punish').show();
                $('#fineSum').textbox({disabled:false, required:true, missingMessage:'请填写罚款金额'});
            }
            // 没收违法所得、没收非法财物
            var isConfiscate = json.rtData.isConfiscate;
            if(isConfiscate == '1'){
                $('#isConfiscate').checkbox({checked: true});
                $('#isConfiscate_td .text-input-punish').show();
                $('#confiscateMoney').textbox({disabled:false, missingMessage:'请填写没收违法所得金额'});
                $('#isConfiscate_td .text-input-punish').show();
                $('#confiscateProMon').textbox({disabled:false, missingMessage:'请填写没收非法财物金额'});
            }
            // 责令停产停业
            var isOrderClosure = json.rtData.isOrderClosure;
            if(isOrderClosure == '1'){
                $('#isOrderClosure').checkbox({checked: true});
            }
            // 暂扣许可证或营业执照
            var isTdLicense = json.rtData.isTdLicense;
            if(isTdLicense == '1'){
                $('#isTdLicense').checkbox({checked: true});
                $('#isTdLicense_td .text-input-punish-choose').show();
                $('#isTdOrRevokeLicense1').radiobutton({checked:true});
                $('#isTdLicense_td .text-input-punish').show();
            	$('#withholdingStartdateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择暂扣许可证或营业执照起止日期' });
                $('#withholdingEnddateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择暂扣许可证或营业执照结束日期' });
            }
            // 吊销许可证或营业执照
            var isRevokeLicense = json.rtData.isRevokeLicense;
            if(isRevokeLicense == '1'){
                $('#isTdLicense').checkbox({checked: true});
                $('#isTdLicense_td .text-input-punish-choose').show();
                $('#isTdOrRevokeLicense2').radiobutton({checked:true});
            }
            // 行政拘留
            var isDetain = json.rtData.isDetain;
            if(isDetain == '1'){
                $('#isDetain').checkbox({checked: true});
                $('#isDetain_td .text-input-punish').show();
                $('#detainDays').textbox({disabled:false, required:true, missingMessage:'请填写行政拘留天数'});
                $('#detainStartdateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择行政拘留起止日期' });
                $('#detainEnddateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择行政拘留结束日期' });
            }
            
            //其他
            var isOther = json.rtData.isOther;
            if(isOther == '1'){
                $('#isOther').checkbox({checked: true});
                $('#isOther_td .text-input-punish').show();
                $('#otherDetailContent').textbox({disabled:false, required:true, missingMessage:'请填写其他说明'});
            }
        }else if(isPunishment == '2'){
            $('#isPunishment2').radiobutton({checked:true});
        }
        // 绑定
        bindJsonObj2Easyui(json.rtData, 'common_case_add_3_correct_form','hearingWay,hearingConclusion,punishmentSendWay');
    }
}

/**
 * 附件上传
 * @param model 后台文件路径文件夹
 * @param modelId 查询文件关键字
 * @param elemId 前台展示元素ID
 * @returns
 */
function doInitMultipleUploadPunish(model, modelId, elemId) {
  //多附件快速上传
    swfUploadObj = new TeeSWFUpload({
        fileContainer:"fileContainerPunish",//文件列表容器
        uploadHolder:"uploadHolderPunish",//上传按钮放置容器
        valuesHolder:"attachmentSidStrPunish",//附件主键返回值容器，是个input
        quickUpload:true,//快速上传
        showUploadBtn:false,//不显示上传按钮
        queueComplele:function(files){//队列上传成功回调函数，可有可无
            
        },
        uploadSuccess:function(files){//上传成功
            punishFile.push(files.sid);//存放上传文件ID
            punishFile = addAttachmentProp(files, elemId, punishFile);
        },
        renderFiles:false,//渲染附件
        post_params:{model:model, modelId: modelId}//后台传入值，model为模块标志
    });
}
/**
 * 上传完毕后加载已经上传的文件
 * @param obj 文件对象
 * @param elemId 展示元素ID
 * @returns
 */
function addAttachmentPropPunish(obj, elemId) {
    var mainObj = $('#' + elemId);
    var page = "";
    page = page + '<div style="float: left; margin-right:20px" id="flowsheet' + obj.sid + '">';
    page = page + '<input type="hidden" name="punishFileId" id="punishFileId" value="' + obj.sid + '" />';
    page = page + '<input type="hidden" name="punishFileName" id="punishFileName" value="' + obj.fileName + '" />';
    page = page + '<a href="javascript:void(0);" sid="FILE' + obj.sid + '"></a>';
    page = page + '</div>';
    mainObj.append(page);
    
    obj.priv = 1+2+4; // 阅读、下载、删除
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
 * 修改初始化附件列表
 * @param model
 * @param modelId
 * @param elemId
 * @returns
 */
function initAttachmentInfoPunish(model, sid, elemId) {
    var json = tools.requestJsonRs("/attachmentController/getAttachmentModelsByIds.action?attachIds="+sid);
    if(json.rtData != null && json.rtData.length > 0) {
        punishFile = [];
        var attachList = json.rtData;
        $.each(attachList, function(i, o){
            // 添加附件属性
            punishFile.push(o.sid);//存放上传文件ID
            addAttachmentPropPunish(o, elemId);
        });
    }
}

/**
 * 初始化作出处罚决定
 * @returns
 */
function initCommonCaseIsPunishment(){
    var json = [
        {codeNo:1, codeName: '行政处罚'},
        {codeNo:2, codeName: '不予处罚'}
    ]
    var page = "";
    for(var i=0 ; i < json.length; i++){
        page = page + '<input type="radio" name="isPunishment" id="isPunishment'+json[i].codeNo+'" class="easyui-radiobutton" '
        	+ 'style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="110px"'
        	+ 'required value="'+json[i].codeNo+'" label="'+json[i].codeName+'"/>';
        
    }
    $('#isPunishmentTd').html(page);
    $.parser.parse($('#isPunishmentTd'));
    $('#isPunishment1').radiobutton({
        onChange: function(check){
            //var isPunishment = $('#common_case_add_3_correct_form input[name="isPunishment"]:checked').val();
            if(check){
                $("#common_case_is_punishment_div").show();
                $('#common_case_is_punishment_punish_div').show();
                $.parser.parse($('#common_case_is_punishment_punish_div'));
                // 罚款
                var isFine = $("#common_case_add_3_correct_form input[name='isFine']").is(':checked');
                if(isFine == '1'){
                    $('#isFine_td .text-input-punish').show();
                    $('#fineSum').textbox({disabled:false, required:true, missingMessage:'请填写罚款金额'});
                }
                // 没收违法所得、没收非法财物
                var isConfiscate = $("#common_case_add_3_correct_form input[name='isConfiscate']").is(':checked');
                if(isConfiscate == '1'){
                    $('#isConfiscate_td .text-input-punish').show();
                    $('#confiscateMoney').textbox({disabled:false, missingMessage:'请填写没收违法所得金额'});
                    $('#isConfiscate_td .text-input-punish').show();
                    $('#confiscateProMon').textbox({disabled:false, missingMessage:'请填写没收非法财物金额'});
                }
                // 暂扣许可证或营业执照
                var isTdLicense = $("#common_case_add_3_correct_form input[name='isTdLicense']").is(':checked');
                if(isTdLicense){
                    $('#isTdLicense_td .text-input-punish-choose').show();
                    if($("#isTdOrRevokeLicenseTd input[name='isTdOrRevokeLicense']:checked").val()=='1'){
                    	$('#isTdLicense_td .text-input-punish').show();
                    	$('#withholdingStartdateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择暂扣许可证或营业执照起止日期' });
                        $('#withholdingEnddateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择暂扣许可证或营业执照结束日期' });
                    }
                }
                // 行政拘留
                var isDetain = $("#common_case_add_3_correct_form input[name='isDetain']").is(':checked');
                if(isDetain == '1'){
                    $('#isDetain_td .text-input-punish').show();
                    $('#detainDays').textbox({disabled:false, required:true, missingMessage:'请填写行政拘留天数'});
                    $('#detainStartdateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择行政拘留起止日期' });
                    $('#detainEnddateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择行政拘留结束日期' });
                }
                
                //其他
                var isOther = $("#common_case_add_3_correct_form input[name='isOther']").is(':checked');
                if(isOther == '1'){
                    $('#isOther_td .text-input-punish').show();
                    $('#otherDetailContent').textbox({disabled:false, required:true, missingMessage:'请填写其他说明'});
                }
            }else{
            	var ids = ['fineSum','withholdingStartdateStr','withholdingEnddateStr','detainDays','detainStartdateStr',
            	           'detainEnddateStr','confiscateMoney','confiscateProMon','otherDetailContent'];
            	var types = ['textbox','textbox','textbox','textbox','textbox','textbox','textbox','textbox','textbox'];
            	inputRefresh(ids, types);
                $('#common_case_is_punishment_div').hide();
                $('#common_case_is_punishment_punish_div').hide();
            }
        }
    });
}

/**
 * 送达回证 附件上传
 * @param model 后台文件路径文件夹
 * @param modelId 查询文件关键字
 * @param elemId 前台展示元素ID
 * @returns
 */
function doInitMultipleUploadSendProof(model, modelId, elemId) {
  //多附件快速上传
    swfUploadObj = new TeeSWFUpload({
        fileContainer:"fileContainerSendProof",//文件列表容器
        uploadHolder:"uploadHolderSendProof",//上传按钮放置容器
        valuesHolder:"attachmentSidStrSendProof",//附件主键返回值容器，是个input
        quickUpload:true,//快速上传
        showUploadBtn:false,//不显示上传按钮
        queueComplele:function(files){//队列上传成功回调函数，可有可无
            
        },
        uploadSuccess:function(files){//上传成功
            sendProofFile.push(files.sid);//存放上传文件ID
            sendProofFile = addAttachmentProp(files, elemId, sendProofFile);
        },
        renderFiles:false,//渲染附件
        post_params:{model:model, modelId: modelId}//后台传入值，model为模块标志
    });
}
/**
 * 上传完毕后加载已经上传的文件
 * @param obj 文件对象
 * @param elemId 展示元素ID
 * @returns
 */
function addAttachmentPropSendProof(obj, elemId) {
    var mainObj = $('#' + elemId);
    var page = "";
    page = page + '<div style="float: left; margin-right:20px" id="flowsheet' + obj.sid + '">';
    page = page + '<input type="hidden" name="sendProofFileId" id="sendProofFileId" value="' + obj.sid + '" />';
    page = page + '<input type="hidden" name="sendProofFileName" id="sendProofFileName" value="' + obj.fileName + '" />';
    page = page + '<a href="javascript:void(0);" sid="FILE' + obj.sid + '"></a>';
    page = page + '</div>';
    mainObj.append(page);
    
    obj.priv = 1+2+4; // 阅读、下载、删除
    var attach = tools.getAttachElement(obj, {deleteEvent:function(attachModel){
        $("#flowsheet" + attachModel.sid).remove();
        for(var index in sendProofFile) {
            if(sendProofFile[index] == attachModel.sid) {
                sendProofFile.splice(index, 1);
                break;
            }
        }
        
    }});
    $("[sid='FILE" + obj.sid + "']").append(attach);
}

/**
 * 修改初始化附件列表
 * @param model
 * @param modelId
 * @param elemId
 * @returns
 */
function initAttachmentInfoSendProof(model, sid, elemId) {
    var json = tools.requestJsonRs("/attachmentController/getAttachmentModelsByIds.action?attachIds="+sid);
    if(json.rtData != null && json.rtData.length > 0) {
        var attachList = json.rtData;
        $.each(attachList, function(i, o){
            // 添加附件属性
            sendProofFile.push(o.sid);//存放上传文件ID
            addAttachmentPropSendProof(o, elemId);
        });
    }
}


/**
 * 是否警告
 * @returns
 */
function initCommonIsWarn(){
    $('#isWarn').checkbox({
        label: '警告',
        value: 1,
        width: 15,
        height: 15,
        labelAlign: 'left',
        labelPosition: 'after',
        labelWidth: '50',
    });
}

/**
 * 是否罚款
 * @returns
 */
function initCommonIsFine(){
	$('#isFine_td .text-input-punish').hide();
	$('#isFine').checkbox({
        label: '罚款',
        value: 1,
        width: 15,
        height: 15,
        labelAlign: 'left',
        labelPosition: 'after',
        labelWidth: '50',
        onChange: function(check){
            //var isFine = $("#common_case_add_3_correct_form input[name='isFine']").is(':checked');
            if(check){
                $('#isFine_td .text-input-punish').show();
                $('#fineSum').textbox({disabled:false, required:true, missingMessage:'请填写罚款金额'});
            }else{
                $('#fineSum').textbox({disabled:true, required:false});
                $('#fineSum').form('disableValidation');
                $('#isFine_td .text-input-punish').hide();
            }
        }
    });
}

/**
 * 责令停产停业
 * @returns
 */
function initCommonIsOrderClosure(){
    $('#isOrderClosure').checkbox({
        label: '责令停产停业',
        value: 1,
        width: 15,
        height: 15,
        labelAlign: 'left',
        labelPosition: 'after',
        labelWidth: '100',
    });
}

/**
 * 暂扣或者吊销许可证、暂扣或者吊销执照
 * @returns
 */
function initCommonIsTdLicense(){
	$('#isTdLicense_td .text-input-punish').hide();
    $('#isTdLicense_td .text-input-punish-choose').hide();
	$('#isTdLicense').checkbox({
        label: '暂扣或者吊销许可证、暂扣或者吊销执照',
        value: 1,
        width: 15,
        height: 15,
        labelAlign: 'left',
        labelPosition: 'after',
        labelWidth: '260',
        onChange: function(){
            var isTdLicense = $("#common_case_add_3_correct_form input[name='isTdLicense']").is(':checked');
            if(isTdLicense){
                $('#isTdLicense_td .text-input-punish-choose').show();
                if($("#isTdOrRevokeLicenseTd input[name='isTdOrRevokeLicense']:checked").val()=='1'){
                	$('#isTdLicense_td .text-input-punish').show();
                	$('#withholdingStartdateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择暂扣许可证或营业执照起止日期' });
                    $('#withholdingEnddateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择暂扣许可证或营业执照结束日期' });
                }
            }else{
            	var ids = ['withholdingStartdateStr','withholdingEnddateStr'];
            	var types = ['textbox','textbox'];
            	inputRefresh(ids, types);
            	$('#isTdLicense_td .text-input-punish').hide();
                $('#isTdLicense_td .text-input-punish-choose').hide();
            }
            /*$.parser.parse($('#isTdLicense_td .text-input-punish-choose'));
            $.parser.parse($('#isTdLicense_td .text-input-punish'));*/
        }
    });
}

/**
 * 初始化暂扣或吊销
 * @returns
 */
function initCommonCaseIsTdOrRevokeLicense(){
    var json = [
        {codeNo:1, codeName: '暂扣'},
        {codeNo:2, codeName: '吊销'}
    ]
    var page = "";
    for(var i=0 ; i < json.length; i++){
        page = page + '<input type="radio" name="isTdOrRevokeLicense" id="isTdOrRevokeLicense'+json[i].codeNo+'" class="easyui-radiobutton" '
        + 'style="width: 15px; height: 15px;" labelAlign="left" nowrap="nowrap" labelPosition="after" labelWidth="50px"'	
        + 'required value="'+json[i].codeNo+'" label="'+json[i].codeName+'"/>';
    }
    $('#isTdOrRevokeLicenseTd').html(page);
    initCommonCaseIsTdOrRevokeLicenseJson(json);
}

/**
 * 许可证/执照状态改变事件
 * @param data
 * @returns
 */
function initCommonCaseIsTdOrRevokeLicenseJson(data){
	//$('#isTdLicense_td .text-input-punish').hide();
	for(var i=0 ; i < data.length; i++){
        $('#isTdOrRevokeLicense'+data[i].codeNo).radiobutton({
            label: data[i].codeName,
            value: data[i].codeNo,
            width: 15,
            height: 15,
            labelAlign: 'left',
            labelPosition: 'after',
            labelWidth: '50',
            onChange: function(){
                var isTdOrRevokeLicense = $('#common_case_add_3_correct_form input[name="isTdOrRevokeLicense"]:checked').val();
                if('1' == isTdOrRevokeLicense){
                	$('#isTdLicense_td .text-input-punish').show();
                	$('#withholdingStartdateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择暂扣许可证或营业执照起止日期' });
                    $('#withholdingEnddateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择暂扣许可证或营业执照结束日期' });
                }else if('2' == isTdOrRevokeLicense){
                    var ids = ['withholdingStartdateStr','withholdingEnddateStr'];
                	var types = ['textbox','textbox'];
                	inputRefresh(ids, types);
                    $('#isTdLicense_td .text-input-punish').hide();
                }
                /*$.parser.parse($('#isDetain_td .text-input-punish'));
                $.parser.parse($('#isTdLicense_td .text-input-punish'));
                $.parser.parse($('#isDetain_td .text-input-punish'));
                $.parser.parse($('#isTdLicense_td .text-input-punish'));
                $.parser.parse($('#isDetain_td .text-input-punish'));*/
            }
        });
    }
}

/**
 * 是否行政拘留
 * @returns
 */
function initCommonIsDetain(){
	$('#isDetain_td .text-input-punish').hide();
	$('#isDetain').checkbox({
        label: '行政拘留',
        value: 1,
        width: 15,
        height: 15,
        labelAlign: 'left',
        labelPosition: 'after',
        labelWidth: '110',
        onChange: function(){
            var isDetain = $("#common_case_add_3_correct_form input[name='isDetain']").is(':checked');
            if(isDetain){
                $('#isDetain_td .text-input-punish').show();
                $('#detainDays').textbox({disabled:false, required:true, missingMessage:'请填写行政拘留天数'});
                $('#detainStartdateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择行政拘留起止日期' });
                $('#detainEnddateStr').textbox({disabled:false, validType:'date', required:true, missingMessage:'请选择行政拘留结束日期' });
                
            }else{
                var ids = ['detainDays','detainStartdateStr','detainEnddateStr'];
            	var types = ['textbox','textbox','textbox'];
            	inputRefresh(ids, types);
                
                $('#isDetain_td .text-input-punish').hide();
            }
            
            /*$.parser.parse($('#isTdLicense_td .text-input-punish'));
            $.parser.parse($('#isDetain_td .text-input-punish'));
            $.parser.parse($('#isTdLicense_td .text-input-punish'));
            $.parser.parse($('#isDetain_td .text-input-punish'));
            $.parser.parse($('#isTdLicense_td .text-input-punish'));*/
        }
    });
}

/**
 * 是否没收违法所得、是否没收非法财物
 * @returns
 */
function initCommonIsConfiscate(){
	$('#isConfiscate_td .text-input-punish').hide();
	$('#isConfiscate').checkbox({
        label: '没收违法所得、没收非法财物',
        value: 1,
        width: 15,
        height: 15,
        labelAlign: 'left',
        labelPosition: 'after',
        labelWidth: '200',
        onChange: function(){
            var isConfiscate = $("#common_case_add_3_correct_form input[name='isConfiscate']").is(':checked');
            if(isConfiscate){
                $('#isConfiscate_td .text-input-punish').show();
                $('#confiscateMoney').textbox({disabled:false, missingMessage:'请填写没收违法所得金额'});
                $('#isConfiscate_td .text-input-punish').show();
                $('#confiscateProMon').textbox({disabled:false, missingMessage:'请填写没收非法财物金额'});
            }else{
                var ids = ['confiscateMoney','confiscateProMon'];
            	var types = ['textbox','textbox'];
            	inputRefresh(ids, types);
                
                $('#isConfiscate_td .text-input-punish').hide();
                $('#isConfiscate_td .text-input-punish').hide();
            }
        }
    });
}

/**
 * 其他
 * @returns
 */
function initCommonIsOther(){
	$('#isOther_td .text-input-punish').hide();
    $('#isOther').checkbox({
        label: '其他',
        value: 1,
        width: 15,
        height: 15,
        labelAlign: 'left',
        labelPosition: 'after',
        labelWidth: '100',
        onChange: function(){
            var isOther = $("#common_case_add_3_correct_form input[name='isOther']").is(':checked');
            if(isOther){
                $('#isOther_td .text-input-punish').show();
                $('#otherDetailContent').textbox({disabled:false, required:true, missingMessage:'请填写其他说明'});
            }else{
                $('#otherDetailContent').textbox({disabled:true});
                $('#otherDetailContent').form('disableValidation');
                
                $('#isOther_td .text-input-punish').hide();
            }
        }
    });
}


/**
 * 查询实施主体下的职权
 * @returns
 */
function commonFindPower(){
    var powerType = '01';
    var url=contextPath+"/caseCommonPowerCtrl/commonCaseAddPower.action?actSubject="+actSubject+"&powerType="+powerType;
    top.bsWindow(url ,"添加违法行为",{width:"1000",height:"450",buttons:
        [
            {name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "保存") {
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
                var paramsPunish;
                var paramsDiscretionary;
                if(addPower == null || addPower.length == 0) {
                    params = {ids: 'empty', actSubject: actSubject};
                    paramsGist = {powerId: 'empty',gistType: '01'};
                    paramsPunish = {powerId: "empty",gistType: '02'};//依据类型（01 违法依据，02处罚依据，02设定依据）
                    paramsDiscretionary = {powerId: 'empty'}
                }else {
                    params = {
                        ids : addPower.join(","),
                        actSubject : actSubject
                    };
                    paramsGist = {
                        powerId : addPower.join(","),
                        gistType : '01' // 依据类型（01 违法依据，02处罚依据，02设定依据）
                    };
                    paramsPunish = {
                        powerId : addPower.join(","),
                        gistType : '02' // 依据类型（01 违法依据，02处罚依据，02设定依据）
                    };
                    paramsDiscretionary = {
                        powerId : addPower.join(",")
                    };
                }
                initHandDatagrid(params);//加载违法行为
                initGistDatagrid(paramsGist);//加载违法依据
                initPunishDatagrid(paramsPunish);//加载处罚依据
                initDiscretionaryDatagrid(paramsDiscretionary);//加载自由裁量权
                return true;
            } else if(v=="关闭"){
                return true;
            }
        }
    });
}

/**
 * 违法行为信息加载
 * @returns
 */
function initHandDatagrid(params){
    datagrid = $('#common_case_illegal_hand_datagrid').datagrid({
        url: contextPath + '/powerCtrl/getPowerByActSubject.action',
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
            { 
                field: 'id', checkbox: true, title: "ID", width: 10, hidden: true, halign: 'center', align: 'center'
            },
            {field:'ID',title:'序号',align:'center', width: 10,
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field: 'name', title: '违法行为', width: 200, halign: 'center', align: 'left',
                formatter: function(e, rowData) {
                    var name = rowData.name
                    if(name == null || name == 'null') {
                        name = "";
                    }
                    var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+name+"'>"+name+"</lable>"
                    return lins;
                }
            },
            {
                field: 'code', title: '职权编号 ', width: 80, halign: 'center', align: 'center'
                
            },
            {
                field: '___',
                title: '操作',
                formatter: function(e, rowData) {
                    var optStr = "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deleteAddPower(\"" + rowData.id + "\")'><i class='fa fa-trash'></i> </a>&nbsp;&nbsp;&nbsp;&nbsp;";
                    return optStr;
                },
                width: 20, halign: 'center', align: 'center'
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
    var paramsPunish;
    var paramsDiscretionary;
    if(addPower == null || addPower.length == 0) {
        params = {ids: 'empty', actSubject: actSubject};
        paramsGist = {powerId: 'empty',gistType: '01'};
        paramsPunish = {powerId: 'empty',gistType: '02'};//依据类型（01 违法依据，02处罚依据，02设定依据）
        paramsDiscretionary = {powerId: 'empty'}
    }else {
        params = {
            ids: addPower.join(","),
            actSubject: actSubject
        };
        paramsGist = {
            powerId: addPower.join(","),
            gistType: '01' 
        };//依据类型（01 违法依据，02处罚依据，02设定依据）
        paramsPunish = {
            powerId: addPower.join(","),gistType: '02'
        };//依据类型（01 违法依据，02处罚依据，02设定依据）
        paramsDiscretionary = {
            powerId: addPower.join(",")
        };
    }
    initHandDatagrid(params);
    initGistDatagrid(paramsGist);//加载违法依据
    initPunishDatagrid(paramsPunish);//加载处罚依据
    initDiscretionaryDatagrid(paramsDiscretionary);//加载自由裁量权
}
/**
 * 违法依据信息加载
 * @returns
 */
function initGistDatagrid(params){
    datagrid = $('#common_case_illegal_gist_datagrid').datagrid({
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
                            $("#common_case_illegal_gist_datagrid").datagrid("selectRow", index);//如果数据行为已选中则选中该行
                            break;
                        }
                    }
                }else{
                    $("#common_case_illegal_gist_datagrid").datagrid("selectRow", index);//如果数据行为已选中则选中该行
                }
            });   
        },
        columns: [[
            {
                field: 'id', checkbox: true, title: "ID", width: 20, halign: 'center', align: 'center'
            },
            {field:'ID',title:'序号',align:'center', width: 20,
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
 * 处罚依据信息加载
 * @returns
 */
function initPunishDatagrid(params){
    /*var powerJsonStr = $('#common_case_add_correct_powerJsonStr').val();
    var powerJson = [];
    if(powerJsonStr != null && powerJsonStr != '' && powerJsonStr != 'null'){
        powerJson = powerJsonStr.split(",");
    }else{
        powerJson.push('empty');
    }
    var params = {
        powerId: powerJson.join(','),
        gistType: '02'
    }*/
    datagrid = $('#common_case_punish_datagrid').datagrid({
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
                if(punishJson != null && punishJson.length > 0){
                    for(var i = 0; i < punishJson.length; i++){
                        if(punishJson[i] == val.id){
                            $("#common_case_punish_datagrid").datagrid("selectRow", index);//如果数据行为已选中则选中改行
                            break;
                        }
                    }
                }else{
                    $("#common_case_punish_datagrid").datagrid("selectRow", index);//如果数据行为已选中则选中改行
                }
            });   
        },
        columns: [[
            {
                field: 'id', checkbox: true, title: "ID", width: 20, halign: 'center', align: 'center'
            },
            {field:'ID',title:'序号',align:'center', width: 20,
                formatter:function(value,rowData,rowIndex){
                    return rowIndex+1;
                }
            },
            {
                field: 'lawName', title: '法律名称', halign: 'center', width: 120,
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
                field: 'content', title: '内容', halign: 'center', width: 200,
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
 * 自由裁量权
 * @param params
 * @returns
 */
function initDiscretionaryDatagrid(params) {
    /*var powerJsonStr = $('#common_case_add_correct_powerJsonStr').val();
    var powerJson = [];
    if(powerJsonStr != null && powerJsonStr != '' && powerJsonStr != 'null'){
        powerJson = powerJsonStr.split(",");
    }else{
        powerJson.push('empty');
    }
    var params = {
        powerId: powerJson.join(',')
    }*/
    datagrid = $('#common_case_discretionary_power_datagrid').datagrid({
        url: contextPath + '/discretionaryCtrl/listDiscretionaryByPowerIds.action',
        queryParams: params,
        pagination: false,
        singleSelect: true,
//        pageSize : 20,
//        pageList : [ 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar: '#toolbar',
        // 工具条对象
        checkbox: true,
        border: false,
        striped: true,//隔行变色
        /* idField:'formId',//主键列 */
        fitColumns: true,
        // 列是否进行自动宽度适应
        nowrap: true,
        onLoadSuccess: function(data) {
            var rowData = data.rows;
            $.each(rowData, function(index,val){//遍历JSON
                if(discretionaryJson != null && discretionaryJson.length > 0){
                    for(var i = 0; i < discretionaryJson.length; i++){
                        if(discretionaryJson[i] == val.id){
                            $("#common_case_discretionary_power_datagrid").datagrid("selectRow", index);//如果数据行为已选中则选中改行
                            break;
                        }
                    }
                }else{
                    $("#common_case_discretionary_power_datagrid").datagrid("selectRow", index);//如果数据行为已选中则选中改行
                }
            });
        },
        columns: [[{
            field: 'id',
            checkbox: true,
            title: "ID",
            halign: 'center', 
            align: 'center',
            width: 20
        },
        {
        	field:'ID',
        	title:'序号',
        	align:'center',
            formatter:function(value,rowData,rowIndex){
                return rowIndex+1;
            }
        },
        {
            field: 'illegalFact',
            title: '违法事实',
            width: 50,
            halign: 'center', 
            align: 'left',
            formatter: function(value, rowData) {
                if(value == null || value == 'null') {
                    value = "";
                }
                var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</lable>"
                return lins;
            }
        },
        {
            field: 'punishStandard',
            title: '裁量标准',
            width: 50,
            halign: 'center', 
            align: 'left',
            formatter: function(value, rowData) {
                if(value == null || value == 'null') {
                    value = "";
                }
                var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</lable>"
                return lins;
            }
        }]]
    });
}


function doCorrectSave(){
	// 必填项校验
	var validate = $("#common_case_add_3_correct_form").form('enableValidation').form('validate')
		&& radioValidate("isCriminalTd","isCriminal","请选择是否涉刑案件")
		&& radioValidate("isMajorCaseTd","isMajorCase","请选择是否重大案件")
		&& radioValidate("isLegalReviewTd","isLegalReview","请选择是否法制审核")
		&& radioValidate("isCollectiveDiscussionTd","isCollectiveDiscussion","请选择是否集体讨论")
		&& radioValidate("isHearingTd","isHearing","请选择是否听证")
		&& radioValidate("isPunishmentTd","isPunishment","请选择作出处罚决定");
    if(validate){
    	var params = tools.formToJson($("#common_case_add_3_correct_form"));
    	// 作出处罚信息保存
        if(punishFile != null && punishFile.length > 1){
            $.MsgBox.Alert_auto("请仅上传一个决定书");
            $('html, body').animate({scrollTop: $("#punishmentDocument").offset().top}, 500);
            return false;
        }
        
        if(sendProofFile != null && sendProofFile.length > 1){
            $.MsgBox.Alert_auto("请仅上传一个送达回证");
            $('html, body').animate({scrollTop: $("#sendProofDocument").offset().top}, 500);
            return false;
        }
    	
    	// 作出处罚决定
    	if('1' == params.isPunishment){
    		// 警告
            var isWarn = $("#common_case_add_3_correct_form input[name='isWarn']").is(':checked');
            // 罚款
            var isFine = $("#common_case_add_3_correct_form input[name='isFine']").is(':checked');
            // 是否没收违法所得、没收非法财物
            var isConfiscate = $("#common_case_add_3_correct_form input[name='isConfiscate']").is(':checked');
            // 责令停产停业
            var isOrderClosure = $("#common_case_add_3_correct_form input[name='isOrderClosure']").is(':checked');
            // 暂扣或吊销许可证或营业执照
            var isTdLicense = $("#common_case_add_3_correct_form input[name='isTdLicense']").is(':checked');
            // 行政拘留
            var isDetain = $("#common_case_add_3_correct_form input[name='isDetain']").is(':checked');
            // 其他
            var isOther = $("#common_case_add_3_correct_form input[name='isOther']").is(':checked');
            if(!(isWarn || isFine || isConfiscate || isOrderClosure || isTdLicense || isDetain || isOther)){
                $.MsgBox.Alert_auto("请勾选至少一种处罚行为（警告、罚款等等）");
                $('html, body').animate({scrollTop: $("#isWarn_tr").offset().top}, 500);
                return false;
            }
            if(isTdLicense == 1){
            	if(radioValidate("isTdOrRevokeLicenseTd","isTdOrRevokeLicense","请选择暂扣或吊销许可证")){
            		if(params.isTdOrRevokeLicense == 1){
            			params.isTdLicense = 1;
            			params.isRevokeLicense = 0;
            		}else if(params.isTdOrRevokeLicense == 2){
            			params.isTdLicense = 0;
            			params.isRevokeLicense = 1;
            		}
            	}else{
            		return false;
            	}
            }else{
            	params.isTdLicense = 0;
    			params.isRevokeLicense = 0;
            }
            if(isConfiscate == 1){
            	if(($.trim(params.confiscateMoney) == '' || $.trim(params.confiscateMoney) <=0)
            			&& ($.trim(params.confiscateProMon) == '' || $.trim(params.confiscateProMon) <=0)){
            		$.MsgBox.Alert_auto("请填写至少一种没收金额（违法所得、非法财物）");
                    $('html, body').animate({scrollTop: $("#isConfiscate_td").offset().top}, 500);
                    return false;
            	}
            }
            //违法行为
            var powerList = $("#common_case_illegal_hand_datagrid").datagrid("getRows");
            if(powerList == null || powerList.length < 1){
                $.MsgBox.Alert_auto("请添加至少1条违法行为");
                $('html, body').animate({scrollTop: $("#common_case_illegal_hand_datagrid_div").offset().top}, 500);
                return false;
            }
            
            powerJson = []//违法依据对象
            for(var i=0;i<powerList.length;i++){
                var obj = {};
                obj.powerId = powerList[i].id;
                obj.powerCode = powerList[i].code;
                obj.powerName = powerList[i].name;
                powerJson.push(obj);
            }
            //违法行为对象转成json字符串
            params.powerJsonStr = tools.parseJson2String(powerJson);
            
            var gistList = $("#common_case_illegal_gist_datagrid").datagrid("getSelections");
            if(gistList == null || gistList.length < 1){
                $.MsgBox.Alert_auto("请添加至少1条违法依据");
                $('html, body').animate({scrollTop: $("#common_case_illegal_gist_datagrid_div").offset().top}, 500);
                return false;
            }
            gistJson = []//违法依据对象
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
            //违法依据json
            params.gistJsonStr = tools.parseJson2String(gistJson);
            
            var punishList = $("#common_case_punish_datagrid").datagrid("getSelections");
            if(punishList == null || punishList.length < 1){
                $.MsgBox.Alert_auto("请添加至少1条处罚依据");
                $('html, body').animate({scrollTop: $("#common_case_punish_datagrid_div").offset().top}, 500);
                return false;
            }
            punishJson = []//违法依据对象
            for(var i=0;i<punishList.length;i++){
                var obj = {};
                obj.gistId = punishList[i].id;
                obj.lawName = punishList[i].lawName;
                obj.strip = punishList[i].gistStrip;
                if(punishList[i].gistFund == null || punishList[i].gistFund == ''){
                    obj.fund = 0;
                }else{
                    obj.fund = punishList[i].gistFund;
                }
                if(punishList[i].gistItem == null || punishList[i].gistItem == ''){
                    obj.item = 0;
                }else{
                    obj.item = punishList[i].gistItem;
                }
                if(punishList[i].gistCatalog == null || punishList[i].gistCatalog == ''){
                    obj.gistCatalog = 0;
                }else{
                    obj.gistCatalog = punishList[i].gistCatalog;
                }
                obj.content = punishList[i].content;
                punishJson.push(obj);
            }
            //处罚依据json
            params.punishJsonStr = tools.parseJson2String(punishJson);
            params.id = caseId;
            params.isNext = 3; //处罚决定保存tabs页签index
            params.editFlag = parseInt(editFlag);
            params.grading = '03';
            params.currentState = '03';
            
            var json = tools.requestJsonRs("/caseCommonBaseCtrl/saveFilingStage.action", params);
            if(json.rtState){
                //保存成功，进行下一步
                var tabId="common_case_add_tabs";
                var title = "处罚执行";
                var url = '/caseCommonBaseCtrl/commonCaseAddGrading.action';
                var pageUrl = '/supervise/caseManager/commonCase/common_case_add_4_present.jsp';
                var grading = '04';
                var paramsObj = {
                        pageUrl: pageUrl,
                        grading: grading,
                        caseId: caseId,
                        editFlag: editFlag,
                        saveEvent: '01', //点击保存事件
                        modelId: correctModelId
                }
                url = url + "?"+$.param(paramsObj);
                parent.addTab(tabId, title, url);
                editFlag = '2';
                return json.rtState;
            }else{
                $.MsgBox.Alert_auto("保存失败！");
                return json.rtState;
            }
    	}else if('2' == params.isPunishment){
    		
    		params.id = caseId;
    		params.isNext = 4; //结案保存tabs页签index
            params.editFlag = parseInt(editFlag);
            params.grading = '03';
            params.currentState = '03';
            params.closedState = '03';	
    		var json = tools.requestJsonRs("/caseCommonBaseCtrl/saveFilingStage.action", params);
                if(json.rtState){
                    //保存成功，进行下一步
                    var tabId="common_case_add_tabs";
                    var title = "结案";
                    var url = '/caseCommonBaseCtrl/commonCaseAddGrading.action';
                    var pageUrl = '/supervise/caseManager/commonCase/common_case_add_5_execute.jsp';
                    var grading = '05';
                    var paramsObj = {
                            pageUrl: pageUrl,
                            grading: grading,
                            caseId: caseId,
                            editFlag: editFlag,
                            saveEvent: '01', //点击保存事件
                            modelId: correctModelId
                    }
                    url = url + "?"+$.param(paramsObj);
                    parent.addTab(tabId, title, url);
                    editFlag = '2';
                    return json.rtState;
                }else{
                    $.MsgBox.Alert_auto("保存失败！");
                    return json.rtState;
                }
            }
    }else{
    	return false;
    }
}


/**
 * 撤销立案
 * @returns
 */
function deRevokeSave(){
	var caseName = $('#common_case_add_correct_caseName').val();
    var registerCode = $('#common_case_add_correct_registerCode').val();
    
    var params = {
            caseId: caseId,
            subjectId: actSubject,
            isNext: isNext,
            editFlag: editFlag,
            caseName: caseName,
            caseCode: registerCode,
        }
    if('2' == editFlag || '3' == editFlag){
        var revokeJsonStr = $('#common_case_add_correct_revokeJsonStr').val();
        if (revokeJsonStr != null && revokeJsonStr != ''){
            revokeJson = JSON.parse(revokeJsonStr);
            params.revokeRegisterDateStr = revokeJson.revokeRegisterDateStr;
            params.approvePerson = revokeJson.approvePerson;
            params.approveDateStr = revokeJson.approveDateStr;
            params.revokeRegisterReason = revokeJson.revokeRegisterReason;
        }
    }
    var url=contextPath+"/caseCommonRevokeCtrl/commonCaseAddRevoke.action?"+$.param(params);
    top.bsWindow(url ,"撤销立案",{width:"600",height:"450",buttons:
        [
            {name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "保存") {
                var params = cw.initReovkeForm();
                if(params != null){
                    var obj = {};
                    revokeJson = [];
                    obj.revokeRegisterDateStr = params.revokeRegisterDateStr;
                    obj.approvePerson = params.approvePerson;
                    obj.approveDateStr = params.approveDateStr;
                    obj.revokeRegisterReason = params.revokeRegisterReason;
                    obj.caseName = caseName;
                    obj.registerCode = registerCode;
                    revokeJson.push(obj);
                    //撤销立案json字符串
                    params.revokeJsonStr = tools.parseJson2String(revokeJson);
                    params.id = caseId;
                    params.isNext = 2; //撤销立案保存后tabs页签index
                    params.editFlag = parseInt(editFlag);
                    params.grading = '03';
                    params.currentState = '06';
                    params.closedState = '02';
                    params.closedCaseReason = params.revokeRegisterReason;
                    var json = tools.requestJsonRs("/caseCommonBaseCtrl/saveClosedState.action", params);
                    if(json.rtState){
                      //撤销立案成功 返回首页
                        parent.location.href = contextPath+"/caseCommonBaseCtrl/commonCaseIndex.action";
//                        window.parent.location.href = contextPath+"/caseCommonBaseCtrl/commonCaseIndex.action";
                        return json.rtState;
                    }else{
                        $.MsgBox.Alert_auto("保存失败！");
                        return json.rtState;
                    }
                }
            } else if(v=="关闭"){
                return true;
            }
        }
    });
}