//字典表
var DICT_URL = "/xzfy/common/getDict.action";
//列表地址
var LIST_URL = "/xzfy/caseQuery/getCaseListByType.action"; 
var listdata = {
		isShowColumn: [
			{codeNo:'postType',codeName:'申请方式'},
			{codeNo:'v_name',codeName:'申请人'},
			{codeNo:'respondentName',codeName:'被申请人'},
			{codeNo:'departmentCode',codeName:'复议机关'},
			{codeNo:'caseStatus',codeName:'复议事项'},
			{codeNo:'caseChiledStatus',codeName:'结案类型'},
//			{codeNo:'opt_Time',codeName:'剩余时长'},
		]
	}
var i;
if(listdata.isShowColumn.length%7==0){
	i=listdata.isShowColumn.length/7;
}else {i=Math.ceil(listdata.isShowColumn.length/7)}

var widthnum = i*190+10;

var width = widthnum+'px';

$(".panList").css("width",width);

$(".isshow").on("click",function(){
	$(".panList").show();
	$("body").append('<div id="panListBack" class=""></div>');

});

$("body").delegate("#panListBack","click",function(){
	$(".panList").hide();
	$("#panListBack").remove();
	});

/** 初始化展示可选列 */
var temp = ['postType','v_name','respondentName','caseStatus','caseChiledStatus'];
function detail(code,th){
	if($(th).children('i').hasClass("fa-check")){
		$(th).children('i').removeClass("fa-check");
		$('#datagrid').datagrid('hideColumn', code);
		for(var i=0;i<temp.length;i++){
			if(temp[i] == code){
				temp.splice(i,1);
				break;
			}
		}
	}else{
		$(th).children('i').addClass("fa-check");
		$('#datagrid').datagrid('showColumn', code);
		temp.push(code);
	}
}

var tpl=[
	'{@each isShowColumn as it}',
	'<li onclick="detail(\'${it.codeNo}\',this)" id=\'${it.codeNo}\' ><i class="fa"></i>${it.codeName}</li>',
	'{@/each}'
   ].join('\n');
//类型
var type = "";

var datagrid = "";

function doInit(){
	
	type = getQueryString("type");
	var url = LIST_URL;
	//初始化
	init();
	//可选列
	$(".panList").append(juicer(tpl,listdata));
	//表格数据
	datagrid = $('#datagrid').datagrid({
		url:url,
		queryParams:{
			type:type,
			caseNum:$('#caseNum').val(),
			postType:$('#postType').val(),
			name:$('#name').val(),
			caseStatus:$('#caseStatus').val(),
			start:$('#beginTime').val(),
			end:$('#endTime').val()
		},
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'caseId',//主键列
		striped : true,
		fitColumns:true,//列是否进行自动宽度适应
		fitColumns : true,// 列是否进行自动宽度适应
        onLoadSuccess: function(data, rowData) {
//        	debugger;
            if(temp.length>0){
                for(var i = 0; i < temp.length; i++){
                    $('#datagrid').datagrid('showColumn', temp[i]);
                    if(!$('#'+temp[i]).children('i').hasClass("fa-check")){
                        $('#'+temp[i]).children('i').addClass("fa-check");
                    }
                }
            }
        },
		frozenColumns: [ [
			{field:'ID',title:'序号',align:'center',
			    formatter:function(value,rowData,rowIndex){
			        return rowIndex+1;
			    }
			},
			{field:'opt_',title:'案件编号',width:200,formatter:function(value,data,rowIndex){
                var  opt= "<a href=\"#\" onclick=\"query('"+data.caseId+"')\" title='"+data.caseNum+"'>"
                    + data.caseNum + "</a>&nbsp;&nbsp;";
                return  opt;
			}
			},
       ] ],
		columns:[[
//            {field:'caseId',checkbox:true,width:100,
//            	formatter:function(value,data,rowIndex){
//                    return data.caseId;
//	            }
//            },
//		   {field:'index',title:'序号',width:38, align: 'center',formatter:function(val,row,index){
//		     var options = $("#datagrid").datagrid('getPager').data("pagination").options; 
//		     console.log("options===",options,index);
//		     var currentPage = options.pageNumber+1;
//		     var pageSize = options.pageSize;
//		     return (pageSize * (currentPage -1))+(index+1);
//		    }},
//			{field:'opt_',title:'案件编号',width:200,formatter:function(value,data,rowIndex){
//                var  opt= "<a href=\"#\" onclick=\"query('"+data.caseId+"')\" title='"+data.caseNum+"'>"
//                    + data.caseNum + "</a>&nbsp;&nbsp;";
//                return  opt;
//			}},
			{field:'postType',title:'申请方式',width:200},
			{field:'v_name',title:'申请人',width:200,formatter:function(value,data,rowIndex){
				var v_name = "<span title='"+data.name+"'>"+data.name+"</span>";
				return v_name;
			}},
			{field:'respondentName',title:'被申请人',width:200},
			{field:'applicationDate',title:'复议日期',width:200},
			{field:'caseStatus',title:'案件状态',width:200},
			{field:'caseChiledStatus',title:'受理状态',width:200},
//			{field:'opt_Time',title:'剩余时长',width:200,formatter:function(value,data,rowIndex){
//                var opt_Time = "";
//                //登记中,案件已归档  没有剩余时间
//                if(data.caseStatusCode != "00" && data.caseStatusCode != "06"){
//                	//案件60天
//					opt_Time = getHours(data.remainderTime,60);
//                }
//                return  opt_Time;
//			}},
		]]
	
	})
}

//初始化
function init(){
	//初始化申请方式
	var url = DICT_URL;
	var param = {type:"APPLICATION_TYPE_CODE"};
	var json = tools.requestJsonRs(url,param);
	var html = createSelectHtml(json);
	$("#postType").html(html);
	
	//初始化案件受理状态
	param = {type:"CASE_STATUS_CODE"};
	json = tools.requestJsonRs(url,param);
	html = createSelectHtml(json);
	$("#caseStatus").html(html);
	
	//title 
	var title = "";
	if(type == 1){
		title = "登记列表";
		$("#register").css('display','block'); 
	}
	else if( type == 2){
		title = "受理列表";
		$("#accept").css('display','block'); 
	}
	else if( type == 3){
		title = "审理列表";
		$("#trial").css('display','block'); 
	}
	else if( type == 4){
		title = "结案列表";
	}
	else if( type == 5){
		title = "归档列表";
	}else{
		title = "综合查询"
	}
	$("#title").html(title);
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

//查询
function search(){
	$('#datagrid').datagrid('load',{
		type:type,
		caseNum:$('#caseNum').val(),
		postType:$('#postType').val(),
		name:$('#name').val(),
		caseStatus:$('#caseStatus').val(),
		start:$('#beginTime').val(),
		end:$('#endTime').val()
	});
}

//案件详情
function query(caseId){
	//地址
	var title = "案件详情";
	var url='/xzfy/jsp/organperson/queryDetail.jsp?personId='+caseId;
	bsWindow(url ,title,{width:"600",height:"320",
		buttons:[],
		submit:function(v,h){}
	});
}

//获取剩余日期
function getHours(time,day){
	if(time !="" && time !=null){
		var times = time + " 00:00:00";
		var begin = new Date(times);
		var now = new Date();
		
		//当前时间到登记时间的小时数
		var timehours = (parseInt(now - begin) / 1000 / 60 / 60);
		//获取登记时间后5天的剩余时间
		var days = parseInt((day*24-timehours)/24) ;
		var hours = parseInt((day*24-timehours)%24);
		return days+"天"+hours+"小时";
	}
	else{
		return "";
	}
}
function accSearch(){
	if($("#accSerach").html()=='精确查询'){
		$("#accSerach").text('模糊查询');
	}else{
		$("#accSerach").text('精确查询');
	}
	 $("#accsearchTr1").toggle();
	 $("#accsearchTr2").toggle();
	 $("#keysearchTr").toggle();
}