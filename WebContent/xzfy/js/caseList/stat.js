var columns=['category','lastPeriod','thisPeriod','accepted','applicantTotal','respondentTotal08','respondentTotal07','respondentTotal06','respondentTotal05','respondentTotal04','respondentTotal03','respondentTotal02','respondentTotal01','respondentTotal99','fyOrgTotal07','fyOrgTotal06','fyOrgTotal05','fyOrgTotal04','fyOrgTotal03','fyOrgTotal02','fyOrgTotal01','fyOrgTotal00','itemTotal01','itemTotal02','itemTotal03','itemTotal04','itemTotal05','itemTotal08','itemTotal09','itemTotal06','itemTotal07','itemTotal99','trialTotal', 'trialTotal01','trialTotal02','trialTotal03','trialTotal04','trialTotal05','trialTotal06','trialTotal07','trialTotal08','trialTotal99','notTrialTotal','fileCheckTotal','compensationTotalJs','compensationTotalJe','fileTotal01', 'fileTotal02'];

function doInit(){
	
	init();
}

//初始化
function init(){
	
}

//创建下拉单选框HTML
function createSelectHtml(json){
	var html = "<option value=''>--请选择--</option>";
	if( json.rtState == true ){
		var list = json.rtData;
		for(var i=0;i<list.length;i++){
			html = html + "<option value='"+list[i].code+"'>"+list[i].codeDesc+"</option>";
		}
	}
	return html;
}

/********************************************************************/
function count(){
	var table=document.getElementById("table");//获取table对象
	var rows=table.rows;//获取行对象
	var cells=table.cells;//获取列对象
	var columsLen = columns.length;//获取列数	(第四行开始)
//	$("#tbody").empty();
	$("#count").empty();//每次加载时清空最后一列，防止二次加载数据时出现多行合计
	$("#count").append("<td class=\"firstColTh\">合计</td>");
	//这里从列开始遍历，得到的就是每一列的数据
	//如果从行开始遍历，得到的就是每行的数据
	for(var j=1;j<columsLen;j++){
		var sum=0;
		for(var i=3;i<rows.length-1;i++){//从i=1第二行开始去掉表头，rows.length-1结束，去掉合计行
			var _idx = i-3;
//			document.getElementById(columns[j]+"_"+_idx).innerHTML;
			var a =parseInt(document.getElementById(columns[j]+"_"+_idx).innerHTML);//获取每一列的值
			a=isNaN(a)?0:a;	
			sum=sum+a; //计算
		}
//		sum=sum==0?"":sum;
		$("#count").append("<td class='firstColTh'>"+sum+"</td>");//给最后一行添加计算结果列
	}
 }

//生成统计图
function search(){

//	$('#tablediv').hide();
	setTimeout(function(){$('#tablediv').show(); }, 300);
	
	var para = {
	   "endTime":$('#endTime').val()||'2019-06-01',
	   "beginTime":$('#beginTime').val()||'2019-04-01',
	}  //定义好你需要传的参数
	$.ajax({
        url: '/xzfy/caseStatAna/getCaseStat.action', //url地址
        type: "POST",
        data: para,//模拟个数据
        success: function (result) {
        	console.log(JSON.parse(result));
        	var object = JSON.parse(result);
        	var rtData = object.rtData;
        	$("#tbody").empty();
        	if(rtData){
        		for(var j=0;j<rtData.length;j++){
//        		console.log(rtData[j]);
        			var obj = rtData[j];
        			$("#tbody").append("<tr>");
        			for(var i=0;i<columns.length;i++){
        				$("#tbody").append("<td id='"+columns[i]+"_"+j+"' class='firstColTh'>"+obj[columns[i]]+"</td>");
//        			console.log(rtData);
        			}
        			$("#tbody").append("</tr>");
        		}
        		count();//算合计
        	}else{
        		$("#count").empty();
        		$("#tbody").append("<tr><td colspan='"+columns.length+"' class='firstColTh'>没有统计结果</td></tr>");
        	}
        	$("#dateTxt").html('('+para.beginTime+'至'+para.endTime+')');
         }, 
         error: function (err) {
        	 console.log(err);
         }
	    });
	
}
