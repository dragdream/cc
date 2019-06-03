//字典表
var DICT_URL = "/xzfy/common/getDict.action";
//列表地址
var LIST_URL = "/xzfy/caseAccept/getCaseListByType.action"; 
//类型
var type = "1";
//是否待办列表
var isNeedDeal = 0;

var datagrid = "";

function doInit(){
	
	var url = LIST_URL;
	//初始化
	init();
	
	//表格数据
	datagrid = $('#datagrid').datagrid({
		url:url,
		queryParams:{
			type:type,
			isNeedDeal:isNeedDeal,
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
		columns:[[
            {field:'caseId',checkbox:true,width:100,
            	formatter:function(value,data,rowIndex){
                    return data.caseId;
	            }
            },
			{field:'opt_',title:'案件编号',width:200,formatter:function(value,data,rowIndex){
                var  opt= "<a href=\"#\" onclick=\"query('"+data.caseId+"')\" title='"+data.caseNum+"'>"
                    + data.caseNum + "</a>&nbsp;&nbsp;";
                return  opt;
			}},
			{field:'postType',title:'申请方式',width:200},
			{field:'v_name',title:'申请人',width:200,formatter:function(value,data,rowIndex){
				var v_name = "<span title='"+data.name+"'>"+data.name+"</span>";
				return v_name;
			}},
			{field:'respondentName',title:'被申请人',width:200},
			{field:'applicationDate',title:'复议日期',width:200},
			{field:'caseStatus',title:'案件状态',width:200},
			{field:'caseChiledStatus',title:'受理状态',width:200},
			{field:'opt_Time',title:'剩余时长',width:200,formatter:function(value,data,rowIndex){
                var opt_Time = "";
                //登记中,案件已归档  没有剩余时间
                if(data.caseStatusCode != "00" && data.caseStatusCode != "06"){
                	//案件60天
					opt_Time = getHours(data.remainderTime,60);
                }
                return  opt_Time;
			}},
			{field:'opv_',title:'操作',width:200,formatter:function(value,data,rowIndex){
				var opv="";
				//案件登记中
				if(data.caseStatusCode == "00"){
					opv ="<a href=\"#\" onclick=\"edit('"+data.caseId+"')\">编辑</a>&nbsp;&nbsp;" 
					+"<a href=\"#\" onclick=\"delete('"+data.caseId+"')\">删除</a>&nbsp;";
				}
				//案件已登记
				else if(data.caseStatusCode == "01"){
					opv = "<a href=\"#\" onclick=\"edit('"+data.caseId+"')\">编辑</a>&nbsp;&nbsp;" 
					+"<a href=\"#\" onclick=\"delete('"+data.caseId+"')\">删除</a>&nbsp;";
					+"<a href=\"#\" onclick=\"extract('"+data.caseId+"')\">提取</a>&nbsp;";
				}
                return  opv;
			}},
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
	var title = "登记列表";
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
		isNeedDeal:isNeedDeal,
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
//	var title = "案件详情";
//	var url='/xzfy/jsp/organperson/queryDetail.jsp?personId='+caseId;
//	bsWindow(url ,title,{width:"600",height:"320",
//		buttons:[],
//		submit:function(v,h){}
//	});
	$.MsgBox.Alert_auto("正在开发中！");
}

// 获取剩余日期
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
