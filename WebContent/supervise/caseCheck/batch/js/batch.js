//下一步   index当前所在页
function nextStep(index){
	var i = parseInt(index);
	switch(i){
    case 0:
    	location.href = "/supervise/caseCheck/batch/batch_add_2_choose_case.jsp";
    	break;
    case 1:
    	location.href = "/jdCasecheckMissionTempCtrl/2.action";
    	break;
    case 2:
    	location.href = "/jdCasecheckMissionTempCtrl/3.action";
    	break;
    }
}
//上一步 index当前所在页，batchId批次ID
function beforeStep(index,batchId){
	var i = parseInt(index);
	switch(i){
    case 1:
    	location.href = "/jdCasecheckMissionTempCtrl/batchTempAdd.action?id="+batchId;
    	break;
    case 2:
    	location.href = "/supervise/caseCheck/batch/batch_add_2_choose_case.jsp?batchId="+batchId;
    	break;
    case 3:
    	location.href = "/jdCasecheckMissionTempCtrl/2.action?batchId="+batchId;
    	break;
    }
}
//跳转  index当前所在页，savedIndex 保存页，jumpIndex跳转页，batchId批次ID
function jump(index,savedIndex,jumpIndex,batchId){
	var index = parseInt(index);
	var savedIndex = parseInt(savedIndex);
	var jumpIndex = parseInt(jumpIndex);
	//保存页小于跳转页，无法跳转
	if(savedIndex < jumpIndex){
		$.MsgBox.Alert_auto("不支持的操作");
	}else{
		switch(jumpIndex){
	    case 0:
	    	location.href = "/jdCasecheckMissionTempCtrl/batchTempAdd.action?id="+batchId;
	    	break;
	    case 1:
	    	location.href = "/supervise/caseCheck/batch/batch_add_2_choose_case.jsp?batchId="+batchId;
	    	break;
	    case 2:
	    	location.href = "/jdCasecheckMissionTempCtrl/2.action?batchId="+batchId;
	    	break;
	    case 3:
	    	location.href = "/jdCasecheckMissionTempCtrl/3.action?batchId="+batchId;
	    	break;
	    }
	}
}
